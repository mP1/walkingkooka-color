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
import walkingkooka.color.parser.ColorParsers;
import walkingkooka.color.parser.FunctionColorFunctionParserToken;
import walkingkooka.datetime.DateTimeContexts;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.text.CharSequences;
import walkingkooka.text.HasText;
import walkingkooka.text.cursor.parser.InvalidCharacterExceptionFactory;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserContext;
import walkingkooka.text.cursor.parser.ParserContexts;
import walkingkooka.text.cursor.parser.ParserReporters;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallException;

import java.math.MathContext;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Base class for all rgb like value classes.
 */
public abstract class Color implements HasText,
    UsesToStringBuilder {

    /**
     * A constant holding black
     */
    @SuppressWarnings("StaticInitializerReferencesSubClass")
    public final static RgbColor BLACK = RgbColor.fromRgb0(0);

    /**
     * A constant holding white
     */
    @SuppressWarnings("StaticInitializerReferencesSubClass")
    public final static RgbColor WHITE = RgbColor.fromRgb0(0xFFFFFF);

    /**
     * Tests if the given type is a {@link Color} sub class.
     * This is useful in GWT where {@link Class#isAssignableFrom(Class)} is not supported.
     */
    public static boolean isColorClass(final Class<?> type) {
        return Color.class == type ||
            isHslColorClass(type) ||
            isHsvColorClass(type) ||
            isRgbColorClass(type);
    }

    /**
     * Tests if the given type is {@link HslColor} or a sub-class.
     * This is useful in GWT where {@link Class#isAssignableFrom(Class)} is not supported.
     */
    public static boolean isHslColorClass(final Class<?> type) {
        return HslColor.class == type ||
            OpaqueHslColor.class == type ||
            AlphaHslColor.class == type;
    }

    /**
     * Tests if the given type is {@link HsvColor} or a sub-class.
     * This is useful in GWT where {@link Class#isAssignableFrom(Class)} is not supported.
     */
    public static boolean isHsvColorClass(final Class<?> type) {
        return HsvColor.class == type ||
            OpaqueHsvColor.class == type ||
            AlphaHsvColor.class == type;
    }

    /**
     * Tests if the given type is {@link RgbColor} or a sub-class.
     * This is useful in GWT where {@link Class#isAssignableFrom(Class)} is not supported.
     */
    public static boolean isRgbColorClass(final Class<?> type) {
        return RgbColor.class == type ||
            OpaqueRgbColor.class == type ||
            AlphaRgbColor.class == type;
    }

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
     * Examples of supported text forms include.
     * <pre>
     * rgb(0, 11, 22)
     * #123 // equivalent to #112233
     * #123456
     * RED // web color names
     * hsl(359, 100%, 99%)
     * hsv(359, 100%, 99%)
     *
     * </pre>
     */
    public static Color parse(final String text) {
        checkText(text);

        final Color color;

        if (text.startsWith("hsl")) {
            color = parseHsl(text);
        } else {
            if (text.startsWith("hsv")) {
                color = parseHsv(text);
            } else {
                color = RgbColor.parseRgbOrHash(text);
            }
        }

        return color;
    }

    /**
     * Parses a {@link RgbColor}, currently only #RGB, #RRGGBB, rgb(), rgba() and web rgb names formats are supported<br>
     * <a href="https://en.wikipedia.org/wiki/Web_colors#CSS_colors"></a>
     */
    public static RgbColor parseRgb(final String text) {
        checkText(text);
        return RgbColor.parseRgbOrHash(text);
    }

    private static String checkText(final String text) {
        return CharSequences.failIfNullOrEmpty(text, "text");
    }

    static Color parseColorParserToken(final String text,
                                       final Parser<ParserContext> parser) {
        return parser.parseText(
                text,
                PARSER_CONTEXT
            ).cast(FunctionColorFunctionParserToken.class)
            .toColorHslOrHsv();
    }

    private final static ParserContext PARSER_CONTEXT = ParserContexts.basic(
        InvalidCharacterExceptionFactory.POSITION,
        DateTimeContexts.fake(),
        DecimalNumberContexts.american(MathContext.DECIMAL32)
    );

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

    public final boolean isHsl() {
        return this instanceof HslColor;
    }

    public final boolean isHsv() {
        return this instanceof HsvColor;
    }

    public final boolean isRgb() {
        return this instanceof RgbColor;
    }

    public abstract HslColor toHsl();

    public abstract HsvColor toHsv();

    public abstract RgbColor toRgb();

    // WebColorName..........................................................................................................

    /**
     * Returns the {@link WebColorName} for this color if one exists and it is a {@link RgbColor}.
     */
    abstract public Optional<WebColorName> toWebColorName();

    // css..............................................................................................................

    /**
     * Mixes this and the other color using the amount.
     */
    public abstract Color mix(final Color other,
                              final float amount);

    static float checkAmount(final float amount) {
        if ((amount < 0f) || (amount > 1.0f)) {
            throw new IllegalArgumentException("amount must be between 0.0 and 1.0 but was " + amount);
        }
        return amount;
    }

    static boolean isMixSmall(final float amount) {
        return amount <= MIX_SMALL_AMOUNT;
    }

    static boolean isMixLarge(final float amount) {
        return amount >= MIX_LARGE_AMOUNT;
    }

    /**
     * Any attempt to mix an amount less than this will return the original {@link Color}.
     */
    private final static float MIX_SMALL_AMOUNT = 1.0f / 512f;

    /**
     * Any attempt to mix an amount greater than this will return the new {@link Color} skipping the attempt to mix.
     */
    private final static float MIX_LARGE_AMOUNT = 1.0f - MIX_SMALL_AMOUNT;

    /**
     * Adds both int values and returns the average
     */
    static byte mixIntValue(final int value,
                            final int otherValue,
                            final float amount) {
        final int difference = RgbColorComponent.mask(value) - RgbColorComponent.mask(otherValue);

        return (byte)
            RgbColorComponent.mask(
                Math.round(value - difference * amount)
            );
    }

    /**
     * Adds both float values and returns the average
     */
    static float mixFloatValue(final float value,
                               final float otherValue,
                               final float amount) {
        return value -
            (
                (value - otherValue)
                    * amount
            );
    }

    // css..............................................................................................................

    @Override
    public final String text() {
        return this.toString();
    }

    // invert...........................................................................................................

    /**
     * Inverts the {@link Color}, giving the opposite, eg blue will give orange, black white etc.
     */
    public abstract Color invert();

    // HasJsonNode......................................................................................................

    /**
     * Creates a {@link Color} from a {@link JsonNode}.
     */
    static Color unmarshall(final JsonNode from,
                            final JsonNodeUnmarshallContext context) {
        return unmarshall0(from, Color::parse);
    }

    /**
     * Creates a {@link RgbColor} from a {@link JsonNode}.
     */
    static RgbColor unmarshallRgb(final JsonNode from,
                                  final JsonNodeUnmarshallContext context) {
        return unmarshall0(from, Color::parseRgb);
    }

    /**
     * Creates a {@link HslColor} from a {@link JsonNode}.
     */
    static HslColor unmarshallHsl(final JsonNode from,
                                  final JsonNodeUnmarshallContext context) {
        return unmarshall0(from, Color::parseHsl);
    }

    /**
     * Creates a {@link HsvColor} from a {@link JsonNode}.
     */
    static HsvColor unmarshallHsv(final JsonNode from,
                                  final JsonNodeUnmarshallContext context) {
        return unmarshall0(from, Color::parseHsv);
    }

    private static <C extends Color> C unmarshall0(final JsonNode from,
                                                   final Function<String, C> parse) {
        Objects.requireNonNull(from, "from");

        try {
            return parse.apply(from.stringOrFail());
        } catch (final JsonNodeUnmarshallException cause) {
            throw cause;
        } catch (final RuntimeException cause) {
            throw new JsonNodeUnmarshallException(cause.getMessage(), from, cause);
        }
    }

    final JsonNode marshall(final JsonNodeMarshallContext context) {
        return JsonNode.string(this.toString());
    }

    static {
        //noinspection unchecked
        register(
            "rgb-hsl-hsv",
            Color::unmarshall,
            Color.class
        );

        //noinspection unchecked
        register(
            Color::unmarshallRgb,
            RgbColor.class, AlphaRgbColor.class, OpaqueRgbColor.class
        );

        //noinspection unchecked
        register(
            Color::unmarshallHsl,
            HslColor.class, AlphaHslColor.class, OpaqueHslColor.class
        );

        //noinspection unchecked
        register(
            Color::unmarshallHsv,
            HsvColor.class, AlphaHsvColor.class, OpaqueHsvColor.class
        );
    }

    @SafeVarargs
    private static <T extends Color> void register(
        final BiFunction<JsonNode, JsonNodeUnmarshallContext, T> unmarshaller,
        final Class<T> type,
        final Class<? extends T>... types) {
        register(
            CharSequences.subSequence(
                JsonNodeContext.computeTypeName(type), 0, -"color-".length()
            ).toString(), // drop the leading "color-".
            unmarshaller,
            type,
            types
        );
    }

    @SafeVarargs
    private static <T extends Color> void register(
        final String typeName,
        final BiFunction<JsonNode, JsonNodeUnmarshallContext, T> unmarshaller,
        final Class<T> type,
        final Class<? extends T>... types) {
        JsonNodeContext.register(
            typeName,
            unmarshaller,
            Color::marshall,
            type,
            types
        );
    }

    // Object .........................................................................................................

    @Override
    abstract public int hashCode();

    @Override //
    final public boolean equals(final Object other) {
        return this == other ||
            null != other &&
                this.getClass() == other.getClass() &&
                this.equals0(
                    Cast.to(other)
                );
    }

    abstract boolean equals0(final Object other);

    @Override
    public final String toString() {
        return ToStringBuilder.buildFrom(this);
    }
}
