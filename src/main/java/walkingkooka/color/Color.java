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
import walkingkooka.datetime.DateTimeContexts;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.test.HashCodeEqualsDefined;
import walkingkooka.text.CharSequences;
import walkingkooka.text.cursor.TextCursors;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserContext;
import walkingkooka.text.cursor.parser.ParserContexts;
import walkingkooka.text.cursor.parser.ParserException;
import walkingkooka.text.cursor.parser.ParserReporters;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.map.FromJsonNodeContext;
import walkingkooka.tree.json.map.FromJsonNodeException;
import walkingkooka.tree.json.map.JsonNodeContext;
import walkingkooka.tree.json.map.ToJsonNodeContext;

import java.io.Serializable;
import java.math.MathContext;
import java.util.Objects;
import java.util.function.Function;

/**
 * Base class for all rgb like value classes.
 */
public abstract class Color implements HashCodeEqualsDefined,
        Serializable,
        UsesToStringBuilder {

    /**
     * A constant holding black
     */
    public final static RgbColor BLACK = RgbColor.fromRgb0(0);

    /**
     * A constant holding white
     */
    public final static RgbColor WHITE = RgbColor.fromRgb0(0xFFFFFF);

    /**
     * Creates a new {@link HslColor} with the given components.
     */
    public static HslColor hsl(final HueHslColorComponent hue,
                               final SaturationHslColorComponent saturation,
                               final LightnessHslColorComponent lightness) {
        return HslColor.with(hue, saturation, lightness);
    }

    /**
     * Factory that creates a new {@link HsvColor}
     */
    public static HsvColor hsv(final HueHsvColorComponent hue,
                               final SaturationHsvColorComponent saturation,
                               final ValueHsvColorComponent value) {
        return HsvColor.with(hue, saturation, value);
    }

    /**
     * Creates a new {@link RgbColor} with the provided components.
     */
    public static RgbColor rgb(final RedRgbColorComponent red,
                               final GreenRgbColorComponent green,
                               final BlueRgbColorComponent blue) {
        return RgbColor.with(red, green, blue);
    }

    /**
     * Creates a {@link RgbColor} from the given integer value.
     */
    public static RgbColor fromRgb(final int rgb) {
        return RgbColor.fromRgb0(rgb);
    }

    /**
     * Creates a {@link RgbColor} from the given ARGB value.
     */
    public static RgbColor fromArgb(final int argb) {
        return RgbColor.fromArgb0(argb);
    }

    /**
     * Parses the numerous supported {@link RgbColor}, {@link HslColor} and {@link HsvColor}.
     * This equivalent to calling any of each until success or failure.
     */
    public static Color parse(final String text) {
        return parse0(text, true, true, true);
    }

    /**
     * Parses a {@link RgbColor}, currently only #RGB, #RRGGBB, rgb(), rgba() and web rgb names formats are supported<br>
     * <a href="https://en.wikipedia.org/wiki/Web_colors#CSS_colors"></a>
     */
    public static RgbColor parseRgb(final String text) {
        checkText(text);
        return RgbColor.parseRgb0(text);
    }

    static Color parse0(final String text,
                        final boolean tryRgb,
                        final boolean tryHsl,
                        final boolean tryHsv) {
        checkText(text);

        Color color;
        do {
            if (tryHsl && text.startsWith("hsl")) {
                color = parseHsl(text);
                break;
            }
            if (tryHsv && text.startsWith("hsv")) {
                color = parseHsv(text);
                break;
            }
            if (tryRgb) {
                color = RgbColor.parseRgb0(text);
                break;
            }
            throw new IllegalArgumentException("Invalid rgb " + CharSequences.quoteAndEscape(text));
        } while (false);

        return color;
    }

    static void checkText(final String text) {
        CharSequences.failIfNullOrEmpty(text, "text");
    }

    static Color parseColorParserToken(final String text,
                                       final Parser<ParserContext> parser) {
        try {
            return parser.parse(TextCursors.charSequence(text), ParserContexts.basic(DateTimeContexts.fake(), DecimalNumberContexts.american(MathContext.DECIMAL32)))
                    .map(t -> ColorFunctionFunctionParserToken.class.cast(t).toColorHslOrHsv())
                    .orElseThrow(() -> new IllegalArgumentException("Parsing " + CharSequences.quoteAndEscape(text) + " failed."));
        } catch (final ParserException cause) {
            throw new IllegalArgumentException(cause.getMessage(), cause);
        }
    }

    // parseRgb hsl(359,100%,100%) / hsla(359,100%,100%)..............................................................

    public static HslColor parseHsl(final String text) {
        return parseColorParserToken(text, HSL_FUNCTION_PARSER)
                .toHsl();
    }

    private final static Parser<ParserContext> HSL_FUNCTION_PARSER = ColorParsers.hsl()
            .orReport(ParserReporters.basic());

    // parse hsv(359,100%,100%)..............................................................................................

    public static HsvColor parseHsv(final String text) {
        return parseColorParserToken(text, HSV_FUNCTION_PARSER)
                .toHsv();
    }

    private final static Parser<ParserContext> HSV_FUNCTION_PARSER = ColorParsers.hsv()
            .orReport(ParserReporters.basic());


    Color() {
        super();
    }

    public abstract boolean isHsl();

    public abstract boolean isHsv();

    public abstract boolean isRgb();

    public abstract HslColor toHsl();

    public abstract HsvColor toHsv();

    public abstract RgbColor toRgb();

    // HasJsonNode......................................................................................................

    /**
     * Creates a {@link Color} from a {@link JsonNode}.
     */
    static Color fromJsonNode(final JsonNode from,
                              final FromJsonNodeContext context) {
        return fromJsonNode0(from, Color::parse);
    }

    /**
     * Creates a {@link RgbColor} from a {@link JsonNode}.
     */
    static RgbColor fromJsonNodeRgb(final JsonNode from,
                                    final FromJsonNodeContext context) {
        return fromJsonNode0(from, Color::parseRgb);
    }

    /**
     * Creates a {@link HslColor} from a {@link JsonNode}.
     */
    static HslColor fromJsonNodeHsl(final JsonNode from,
                                    final FromJsonNodeContext context) {
        return fromJsonNode0(from, Color::parseHsl);
    }

    /**
     * Creates a {@link HsvColor} from a {@link JsonNode}.
     */
    static HsvColor fromJsonNodeHsv(final JsonNode from,
                                    final FromJsonNodeContext context) {
        return fromJsonNode0(from, Color::parseHsv);
    }

    private static <C extends Color> C fromJsonNode0(final JsonNode from,
                                                     final Function<String, C> parse) {
        Objects.requireNonNull(from, "from");

        try {
            return parse.apply(from.stringValueOrFail());
        } catch (final FromJsonNodeException cause) {
            throw cause;
        } catch (final RuntimeException cause) {
            throw new FromJsonNodeException(cause.getMessage(), from, cause);
        }
    }

    final JsonNode toJsonNode(final ToJsonNodeContext context) {
        return JsonNode.string(this.toString());
    }

    static {
        JsonNodeContext.register("rgb-hsl-hsv",
                Color::fromJsonNode,
                Color::toJsonNode,
                Color.class);

        JsonNodeContext.register("rgb",
                Color::fromJsonNodeRgb,
                Color::toJsonNode,
                RgbColor.class, AlphaRgbColor.class, OpaqueRgbColor.class);

        JsonNodeContext.register("hsl",
                Color::fromJsonNodeHsl,
                Color::toJsonNode,
                HslColor.class, AlphaHslColor.class, OpaqueHslColor.class);

        JsonNodeContext.register("hsv",
                Color::fromJsonNodeHsv,
                Color::toJsonNode,
                HsvColor.class, AlphaHsvColor.class, OpaqueHsvColor.class);
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
