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

import javaemul.internal.annotations.GwtIncompatible;
import walkingkooka.Cast;
import walkingkooka.ToStringBuilder;
import walkingkooka.ToStringBuilderOption;
import walkingkooka.color.parser.ColorParsers;
import walkingkooka.text.CharSequences;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserContext;
import walkingkooka.text.cursor.parser.ParserReporters;

import java.util.Optional;

/**
 * Holds an immutable {@link RgbColor}.
 */
@SuppressWarnings("lgtm[java/inconsistent-equals-and-hashcode]")
abstract public class RgbColor extends Color {

    static RgbColor parseRgb0(final String text) {
        final RgbColor color;

        if (text.startsWith("rgb")) {
            color = parseRgbFunction(text);
        } else {
            if (Character.isLetter(text.charAt(0))) {
                color = parseWebColorName(text);
            } else {
                if (text.startsWith("#")) {
                    color = parseHash(text);
                } else {
                    throw new IllegalArgumentException("Invalid rgb " + CharSequences.quoteAndEscape(text));
                }
            }
        }
        return color;
    }

    // parseRgb rgb(12,34,56)..............................................................................................

    private static RgbColor parseRgbFunction(final String text) {
        return parseColorParserToken(text, RGB_FUNCTION_PARSER)
                .toRgb();
    }

    private final static Parser<ParserContext> RGB_FUNCTION_PARSER = ColorParsers.rgb()
            .orReport(ParserReporters.basic());

    // parseWebColorName................................................................................................

    /**
     * Looks up by the given name or fails.
     */
    private static RgbColor parseWebColorName(final String name) {
        return WebColorName.with(name)
                .map(WebColorName::color)
                .orElseThrow(() -> new IllegalArgumentException("Unknown rgb name " + CharSequences.quoteAndEscape(name)));
    }

    /**
     * Only parses several hex digit text, other forms will result in a {@link IllegalArgumentException}.
     */
    private static RgbColor parseHash(final String text) {
        RgbColor color;

        final int textLength = text.length();
        switch (textLength) {
            case 4:
                color = parseRgb3Hex(text);
                break;
            case 5:
                color = parseRgba4Hex(text);
                break;
            case 7:
                color = parseRrggbb(text);
                break;
            case 9:
                color = parseRrggbbaa(text);
                break;
            default:
                throw new IllegalArgumentException("Invalid text length " + CharSequences.quoteAndEscape(text));
        }
        return color;
    }

    /**
     * Handles parsing RGB 3 hex digits.
     */
    private static RgbColor parseRgb3Hex(final String text) {
        final int value = parseHashHexDigits(text);
        return fromRgb0((value & 0xF00) * 0x1100 +
                (value & 0xF0) * 0x110 +
                (value & 0xF) * 0x11);
    }

    /**
     * Handles parsing RGBA 4 hex digits.
     */
    private static RgbColor parseRgba4Hex(final String text) {
        final int value = parseHashHexDigits(text);

        final int red = ((value >> 12) & 0xf) * 0x11;
        final int green = ((value >> 8) & 0xf) * 0x11;
        final int blue = ((value >> 4) & 0xf) * 0x11;
        final int alpha = (value & 0xf) * 0x11;

        return fromArgb0((alpha << 24) +
                (red << 16) +
                (green << 8) +
                blue);
    }

    /**
     * Handles parsing RRGGBB 6 hex digits.
     */
    // WebColorName.registerConstant
    private static RgbColor parseRrggbb(final String text) {
        return fromRgb0(parseHashHexDigits(text));
    }

    /**
     * Handles parsing RRGGBBAA 8 hex digits.
     */
    private static RgbColor parseRrggbbaa(final String text) {
        final int value = parseHashHexDigits(text);

        final int red = (value >> 24) & 0xff;
        final int green = (value >> 16) & 0xff;
        final int blue = (value >>8) & 0xff;
        final int alpha = value & 0xff;

        return fromArgb0((alpha << 24) +
                (red << 16) +
                (green << 8) +
                blue);
    }

    private static int parseHashHexDigits(final String text) {
        try {
            // Integer.parseInt will fail when parsing 8 hex digits...
            return (int)Long.parseLong(text.substring(1), 16);
        } catch (final NumberFormatException cause) {
            throw new IllegalArgumentException("Invalid rgb " + CharSequences.quote(text), cause);
        }
    }

    /**
     * Creates a {@link RgbColor} from the given RGB value.
     */
    static RgbColor fromRgb0(final int rgb) {
        if (rgb < 0 || rgb > 0xFFFFFF) {
            throw new IllegalArgumentException("Invalid rgb value " + rgb);
        }

        return OpaqueRgbColor.with( //
                RgbColorComponent.red(RgbColor.shiftRight(rgb, RgbColor.RED_SHIFT)), //
                RgbColorComponent.green(RgbColor.shiftRight(rgb, RgbColor.GREEN_SHIFT)), //
                RgbColorComponent.blue(RgbColor.shiftRight(rgb, RgbColor.BLUE_SHIFT)), //
                rgb & RgbColor.WITHOUT_ALPHA);
    }

    private final static int WITHOUT_ALPHA = 0x00FFFFFF;

    /**
     * Creates a {@link RgbColor} from the given ARGB value.
     */
    static RgbColor fromArgb0(final int argb) {
        return (argb & RgbColor.ALPHA_MASK) == RgbColor.ALPHA_MASK ?
                RgbColor.fromRgb0(WITHOUT_ALPHA & argb) :
                AlphaRgbColor.createAlphaColorFromArgb(argb);
    }

    /**
     * Boring helper that shifts and returns the lowest 8 bits.
     */
    static byte shiftRight(final int value, final int shift) {
        return (byte) (value >> shift);
    }

    /**
     * Used to extract 8 alpha bits from a 32 bit ARGB value.
     */
    final static int ALPHA_SHIFT = 24;

    /**
     * A mask that may be used to extract only the alpha component of a ARGB value.
     */
    private final static int ALPHA_MASK = 0xFF << RgbColor.ALPHA_SHIFT;

    /**
     * Used to extract 8 red bits from a 32 bit ARGB value.
     */

    final static int RED_SHIFT = 16;

    /**
     * Used to extract 8 green bits from a 32 bit ARGB value.
     */
    final static int GREEN_SHIFT = 8;

    /**
     * Used to extract 8 blue bits from a 32 bit ARGB value.
     */
    final static int BLUE_SHIFT = 0;

    /**
     * Creates a new {@link RgbColor} with the provided components.
     */
    static RgbColor with(final RedRgbColorComponent red,
                         final GreenRgbColorComponent green,
                         final BlueRgbColorComponent blue) {
        return OpaqueRgbColor.withOpaque(red, green, blue);
    }

    /**
     * Factory used by {@link HslColor#toRgb()} and {@link HsvColor#toRgb()}
     */
    static RgbColor with(final float red, final float green, final float blue) {
        return RgbColor.with( //
                RedRgbColorComponent.with(RgbColorComponent.toByte(red)), //
                GreenRgbColorComponent.with(RgbColorComponent.toByte(green)), //
                BlueRgbColorComponent.with(RgbColorComponent.toByte(blue))//
        );
    }

    /**
     * Package private constructor to limit sub classing.
     */
    RgbColor(final RedRgbColorComponent red, final GreenRgbColorComponent green, final BlueRgbColorComponent blue) {
        super();
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Tests if this rgb has an alpha component.
     */
    abstract public boolean hasAlpha();

    /**
     * Would be setter that returns a {@link RgbColor} holding the new component. If the component is not new this will be returned.
     */
    public RgbColor set(final RgbColorComponent component) {
        checkComponent(component);

        return component.setComponent(this);
    }

    /**
     * Factory that creates a new {@link RgbColor} with the new {@link RedRgbColorComponent}.
     */
    RgbColor setRed(final RedRgbColorComponent red) {
        return this.red.equals(red) ?
                this :
                this.replace(red, this.green, this.blue);
    }

    /**
     * Factory that creates a new {@link RgbColor} with the new {@link GreenRgbColorComponent}.
     */
    RgbColor setGreen(final GreenRgbColorComponent green) {
        return this.green.equals(green) ?
                this :
                this.replace(this.red, green, this.blue);
    }

    /**
     * Factory that creates a new {@link RgbColor} with the new {@link BlueRgbColorComponent}.
     */
    RgbColor setBlue(final BlueRgbColorComponent blue) {
        return this.blue.equals(blue) ?
                this :
                this.replace(this.red, this.green, blue);
    }

    /**
     * Factory that creates a new {@link RgbColor} with the new {@link AlphaRgbColorComponent}.
     */
    RgbColor setAlpha(final AlphaRgbColorComponent alpha) {
        return this.alpha().equals(alpha) ?
                this :
                AlphaRgbColor.with(this.red, this.green, this.blue, alpha);
    }

    /**
     * Factory called by the various setters that creates a new {@link RgbColor} with the given components.
     */
    abstract RgbColor replace(final RedRgbColorComponent red,
                              final GreenRgbColorComponent green,
                              final BlueRgbColorComponent blue);

    @Override
    public RgbColor mix(final Color color,
                        final float amount) {
        checkColor(color);
        checkAmount(amount);

        return isMixSmall(amount) ? //
                this : // amount of new component is too small ignore
                isMixLarge(amount) ? // amount results in replace.
                        color.toRgb() :
                        mixRgb(
                                color.toRgb(),
                                amount
                        );
    }

    private RgbColor mixRgb(final RgbColor color,
                            final float amount) {
        return this.setRed(
                RgbColorComponent.red(
                        mixIntValue(
                                this.red().value,
                                color.red().value,
                                amount
                        )
                )
        ).setGreen(
                RgbColorComponent.green(
                        mixIntValue(
                                this.green().value,
                                color.green().value,
                                amount
                        )
                )
        ).setBlue(
                RgbColorComponent.blue(
                        mixIntValue(
                                this.blue().value,
                                color.blue().value,
                                amount
                        )
                )
        ).setAlpha(
                RgbColorComponent.alpha(
                        mixIntValue(
                                this.alpha().value,
                                color.alpha().value,
                                amount
                        )
                )
        );
    }

    /**
     * Mixes the given {@link RgbColorComponent} by the provided amount and returns a {@link RgbColor} with that amount.
     */
    public RgbColor mix(final RgbColorComponent component,
                        final float amount) {
        checkComponent(component);
        checkAmount(amount);

        return isMixSmall(amount) ? //
                this : // amount of new component is too small ignore
                isMixLarge(amount) ? // amount results in replace.
                        component.setComponent(this) : //
                        component.mix(this, amount); // mix
    }

    // getters

    /**
     * Getter that returns only the {@link RedRgbColorComponent}
     */
    public RedRgbColorComponent red() {
        return this.red;
    }

    final RedRgbColorComponent red;

    /**
     * Getter that returns only the {@link GreenRgbColorComponent}
     */
    public GreenRgbColorComponent green() {
        return this.green;
    }

    final GreenRgbColorComponent green;

    /**
     * Getter that returns only the {@link BlueRgbColorComponent}
     */
    public BlueRgbColorComponent blue() {
        return this.blue;
    }

    final BlueRgbColorComponent blue;

    /**
     * Always returns an opaque alpha.
     */
    abstract public AlphaRgbColorComponent alpha();

    /**
     * Returns an integer with the RGB value.
     */
    abstract public int rgb();

    /**
     * Returns an integer holding the ARGB value.
     */
    abstract public int argb();

    /**
     * Returns either RGB or ARGB value.
     */
    abstract public int value();

    /**
     * Returns the max of 3 floats.
     */
    private static float max(final float a, final float b, final float c) {
        return Math.max(a, Math.max(b, c));
    }

    /**
     * Returns the min of 3 floats.
     */
    private static float min(final float a, final float b, final float c) {
        return Math.min(a, Math.min(b, c));
    }

    // WebColorName..........................................................................................................

    /**
     * Returns a {@link WebColorName} for this rgb if one exists. Note that colors with alpha always returns nothing.
     */
    public final Optional<WebColorName> webColorName() {
        return Optional.ofNullable(WebColorName.RRGGBB_CONSTANTS.get(this.argb()));
    }

    // AWT..............................................................................................................

    /**
     * Factory that creates a {@link java.awt.Color} holding the same rgb value.
     */
    @GwtIncompatible
    abstract public java.awt.Color toAwtColor();

    // Color............................................................................................................

    /**
     * Returns a {@link HslColor} which is equivalent to this {@link Color} form, ignoring any {@link AlphaRgbColorComponent}.<br>
     *
     * <pre>
     * Converts an RGB color value to HSL. Conversion formula
     * adapted from http://en.wikipedia.org/wiki/HSL_color_space.
     * Assumes r, g, and b are contained in the set [0, 255] and
     * returns h, s, and l in the set [0, 1].
     *
     * &#64;param   Number  r       The red color value
     * &#64;param   Number  g       The green color value
     * &#64;param   Number  b       The blue color value
     * &#64;return  Array           The HSL representation
     * function rgbToHsl(r, g, b){
     *     r /= 255, g /= 255, b /= 255;
     *     var max = Math.max(r, g, b), min = Math.min(r, g, b);
     *     var h, s, l = (max + min) / 2;
     *
     *     if(max == min){
     *         h = s = 0; // achromatic
     *     }else{
     *         var d = max - min;
     *         s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
     *         switch(max){
     *             case r: h = (g - b) / d + (g < b ? 6 : 0); break;
     *             case g: h = (b - r) / d + 2; break;
     *             case b: h = (r - g) / d + 4; break;
     *         }
     *         h /= 6;
     *     }
     *
     *     return [h, s, l];
     * }
     * <a>http://axonflux.com/handy-rgb-to-hsl-and-rgb-to-hsv-color-model-c</a>
     * </pre>
     */
    public final HslColor toHsl() {
        final float red = this.red.floatValue;
        final float green = this.green.floatValue;
        final float blue = this.blue.floatValue;

        final float max = RgbColor.max(red, green, blue);
        final float min = RgbColor.min(red, green, blue);
        final float sumMaxMin = max + min;

        float hue = 0;
        float saturation = 0;
        final float lightness = sumMaxMin / 2;

        final float diff = max - min;
        if (0 != diff) {
            saturation = Math.min(1.0f, diff / (lightness > 0.5f ? (2f - sumMaxMin) : sumMaxMin));

            if (max == red) {
                hue = (green - (blue / diff)) + (green < blue ? 6 : 0);
            } else {
                if (max == green) {
                    hue = ((blue - red) / diff) + 2;
                } else {
                    if (max == blue) {
                        hue = ((red - green) / diff) + 4;
                    }
                }
            }

            hue /= 6;
        }

        return HslColor.with(//
                HslColorComponent.hue(Math.abs(hue * HueHslColorComponent.MAX)), //
                HslColorComponent.saturation(saturation), //
                HslColorComponent.lightness(lightness));
    }

    /**
     * Creates a {@link HsvColor} holding the equivalent rgb, ignoring any {@link AlphaRgbColorComponent}.
     */
    @Override
    public final HsvColor toHsv() {
        final float red = this.red.floatValue;
        final float green = this.green.floatValue;
        final float blue = this.blue.floatValue;

        final float max = RgbColor.max(red, green, blue);
        final float min = RgbColor.min(red, green, blue);

        float saturation = 0.0f;
        if (max != 0) {
            saturation = ((max - min)) / max;
        }
        float hue = 0;
        if (saturation != 0) {
            final float delta = max - min;
            final float maxRed = max - red;
            final float maxGreen = max - green;
            final float maxBlue = max - blue;

            if (red == max) {
                hue = ((maxBlue) - (maxGreen)) / delta;
            } else if (green == max) {
                hue = 2.0f + (((maxRed) - (maxBlue)) / delta);
            } else if (blue == max) {
                hue = 4.0f + (((maxGreen) - (maxRed)) / delta);
            }

            hue *= 60.0f;
            while (hue < 0.0f) {
                hue += HueHsvColorComponent.MAX;
            }
            while (hue >= HueHsvColorComponent.MAX) {
                hue -= HueHsvColorComponent.MAX;
            }
        }

        return HsvColor.with(
                HsvColorComponent.hue(hue),
                HsvColorComponent.saturation(saturation),
                HsvColorComponent.value(max)
        );
    }

    @Override
    public final RgbColor toRgb() {
        return this;
    }

    // invert...........................................................................................................

    @Override
    public final RgbColor invert() {
        return this.setRed(
                (RedRgbColorComponent) this.red().invert()
        ).setGreen(
                (GreenRgbColorComponent) this.green().invert()
        ).setBlue(
                (BlueRgbColorComponent) this.blue().invert()
        );
    }

    // Object..........................................................................................................

    /**
     * Lazily calculates the hash code and stores it for future retrieval.
     */
    @Override final public int hashCode() {
        return this.value();
    }

    /**
     * {@link RgbColor colors} are equal if their values are the same.
     */
    @Override
    final boolean equals0(final Object other) {
        return this.equals1(Cast.to(other));
    }

    private boolean equals1(final RgbColor other) {
        return this.value() == other.value();
    }

    @Override
    final public void buildToString(final ToStringBuilder builder) {
        builder.disable(ToStringBuilderOption.ESCAPE);
        builder.disable(ToStringBuilderOption.QUOTE);
        builder.disable(ToStringBuilderOption.SKIP_IF_DEFAULT_VALUE);
        builder.label("#");
        builder.labelSeparator("");
        builder.separator("");

        this.buildColorComponentsToString(builder);
    }

    abstract void buildColorComponentsToString(ToStringBuilder builder);

    final void addRedGreenBlueComponents(final ToStringBuilder builder) {
        builder.value(this.red);
        builder.value(this.green);
        builder.value(this.blue);
    }

    // RgbColorString................................................................................

    /**
     * Returns either <code>rgb</code> or <code>rgba</code>
     */
    abstract String rgbFunctionName();

    abstract void alphaComponentToString(final StringBuilder b,
                                         final RgbColorString format);
}
