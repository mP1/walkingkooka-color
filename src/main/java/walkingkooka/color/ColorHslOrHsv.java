/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.color;

import walkingkooka.Cast;
import walkingkooka.ToStringBuilder;
import walkingkooka.UsesToStringBuilder;
import walkingkooka.color.parser.ColorFunctionFunctionParserToken;
import walkingkooka.color.parser.ColorParsers;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.test.HashCodeEqualsDefined;
import walkingkooka.text.CharSequences;
import walkingkooka.text.cursor.TextCursors;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserContext;
import walkingkooka.text.cursor.parser.ParserContexts;
import walkingkooka.text.cursor.parser.ParserException;
import walkingkooka.text.cursor.parser.ParserReporters;
import walkingkooka.tree.json.HasJsonNode;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonNodeException;

import java.io.Serializable;
import java.math.MathContext;
import java.util.Objects;
import java.util.function.Function;

/**
 * Base class for all color like value classes.
 */
public abstract class ColorHslOrHsv implements HashCodeEqualsDefined,
        HasJsonNode,
        Serializable,
        UsesToStringBuilder {

    /**
     * Creates a new {@link Color} with the provided components.
     */
    public static Color color(final RedColorComponent red,
                              final GreenColorComponent green,
                              final BlueColorComponent blue) {
        return Color.with(red, green, blue);
    }

    /**
     * Creates a new {@link Hsl} with the given components.
     */
    public static Hsl hsl(final HueHslComponent hue,
                          final SaturationHslComponent saturation,
                          final LightnessHslComponent lightness) {
        return Hsl.with(hue, saturation, lightness);
    }

    /**
     * Factory that creates a new {@link Hsv}
     */
    public static Hsv hsv(final HueHsvComponent hue,
                          final SaturationHsvComponent saturation,
                          final ValueHsvComponent value) {
        return Hsv.with(hue, saturation, value);
    }

    /**
     * Parses the numerous supported {@link Color}, {@link Hsl} and {@link Hsv}.
     * This equivalent to calling any of each until success or failure.
     */
    public static ColorHslOrHsv parse(final String text) {
        return parse0(text, true, true, true);
    }

    /**
     * Parses a {@link Color}, currently only #RGB, #RRGGBB, rgb(), rgba() and web color names formats are supported<br>
     * <a href="https://en.wikipedia.org/wiki/Web_colors#CSS_colors"></a>
     */
    public static Color parseColor(final String text) {
        checkText(text);
        return Color.parseColor0(text);
    }

    static ColorHslOrHsv parse0(final String text,
                                final boolean tryColor,
                                final boolean tryHsl,
                                final boolean tryHsv) {
        checkText(text);

        ColorHslOrHsv color;
        do {
            if (tryHsl && text.startsWith("hsl")) {
                color = parseHsl(text);
                break;
            }
            if (tryHsv && text.startsWith("hsv")) {
                color = parseHsv(text);
                break;
            }
            if (tryColor) {
                color = Color.parseColor0(text);
                break;
            }
            throw new IllegalArgumentException("Invalid color " + CharSequences.quoteAndEscape(text));
        } while (false);

        return color;
    }

    static void checkText(final String text) {
        CharSequences.failIfNullOrEmpty(text, "text");
    }

    static ColorHslOrHsv parseColorHslOrHsvParserToken(final String text,
                                                       final Parser<ParserContext> parser) {
        try {
            return parser.parse(TextCursors.charSequence(text), ParserContexts.basic(DecimalNumberContexts.american(MathContext.DECIMAL32)))
                    .map(t -> ColorFunctionFunctionParserToken.class.cast(t).toColorHslOrHsv())
                    .orElseThrow(() -> new IllegalArgumentException("Parsing " + CharSequences.quoteAndEscape(text) + " failed."));
        } catch (final ParserException cause) {
            throw new IllegalArgumentException(cause.getMessage(), cause);
        }
    }

    // parseColor hsl(359,100%,100%) / hsla(359,100%,100%)..............................................................

    public static Hsl parseHsl(final String text) {
        return parseColorHslOrHsvParserToken(text, HSL_FUNCTION_PARSER)
                .toHsl();
    }

    private final static Parser<ParserContext> HSL_FUNCTION_PARSER = ColorParsers.hsl()
            .orReport(ParserReporters.basic());

    // parse hsv(359,100%,100%)..............................................................................................

    public static Hsv parseHsv(final String text) {
        return parseColorHslOrHsvParserToken(text, HSV_FUNCTION_PARSER)
                .toHsv();
    }

    private final static Parser<ParserContext> HSV_FUNCTION_PARSER = ColorParsers.hsv()
            .orReport(ParserReporters.basic());


    ColorHslOrHsv() {
        super();
    }

    public abstract boolean isColor();

    public abstract boolean isHsl();

    public abstract boolean isHsv();

    public abstract Color toColor();

    public abstract Hsl toHsl();

    public abstract Hsv toHsv();

    // HasJsonNode......................................................................................................

    /**
     * Creates a {@link ColorHslOrHsv} from a {@link JsonNode}.
     */
    public static ColorHslOrHsv fromJsonNode(final JsonNode from) {
        return fromJsonNode0(from, ColorHslOrHsv::parse);
    }

    /**
     * Creates a {@link Color} from a {@link JsonNode}.
     */
    public static Color fromJsonNodeColor(final JsonNode from) {
        return fromJsonNode0(from, ColorHslOrHsv::parseColor);
    }

    /**
     * Creates a {@link Hsl} from a {@link JsonNode}.
     */
    public static Hsl fromJsonNodeHsl(final JsonNode from) {
        return fromJsonNode0(from, ColorHslOrHsv::parseHsl);
    }

    /**
     * Creates a {@link Hsv} from a {@link JsonNode}.
     */
    public static Hsv fromJsonNodeHsv(final JsonNode from) {
        return fromJsonNode0(from, ColorHslOrHsv::parseHsv);
    }

    private static <C extends ColorHslOrHsv> C fromJsonNode0(final JsonNode from,
                                                             final Function<String, C> parse) {
        Objects.requireNonNull(from, "from");

        try {
            return parse.apply(from.stringValueOrFail());
        } catch (final JsonNodeException cause) {
            throw new IllegalArgumentException(cause.getMessage(), cause);
        }
    }

    @Override
    public final JsonNode toJsonNode() {
        return JsonNode.string(this.toString());
    }

    static {
        HasJsonNode.register("color",
                ColorHslOrHsv::fromJsonNodeColor,
                Color.class, AlphaColor.class, OpaqueColor.class);

        HasJsonNode.register("hsl",
                ColorHslOrHsv::fromJsonNodeHsl,
                Hsl.class, AlphaHsl.class, OpaqueHsl.class);

        HasJsonNode.register("hsv",
                ColorHslOrHsv::fromJsonNodeHsv,
                Hsv.class, AlphaHsv.class, OpaqueHsv.class);
    }

    // Object .........................................................................................................

    @Override
    abstract public int hashCode();

    @Override
    final public boolean equals(final Object other) {
        return this == other ||
                this.canBeEqual(other) &&
                        this.equals0(Cast.to(other));
    }

    abstract boolean canBeEqual(final Object other);

    abstract boolean equals0(final Object other);

    @Override
    public final String toString() {
        return ToStringBuilder.buildFrom(this);
    }

    // Serializable.....................................................................................................

    private static final long serialVersionUID = 1;
}
