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

import java.util.Objects;

/**
 * Holds the hue, saturation and lightness which describe a rgb.
 */
public abstract class HslColor extends Color {

    /**
     * Factory that creates a new {@link HslColor}
     */
    static HslColor with(final HueHslColorComponent hue,
                         final SaturationHslColorComponent saturation,
                         final LightnessHslColorComponent lightness) {
        Objects.requireNonNull(hue, "hue");
        Objects.requireNonNull(saturation, "saturation");
        Objects.requireNonNull(lightness, "lightness");

        return OpaqueHslColor.withOpaque(hue, saturation, lightness);
    }

    /**
     * Package private constructor to limit sub classing.
     */
    HslColor(final HueHslColorComponent hue,
             final SaturationHslColorComponent saturation,
             final LightnessHslColorComponent lightness) {
        super();

        this.hue = hue;
        this.saturation = saturation;
        this.lightness = lightness;
    }

    /**
     * Would be setter that returns a {@link HslColor} holding the new component. If the component is not new this will be returned.
     */
    public final HslColor set(final HslColorComponent component) {
        Objects.requireNonNull(component, "component");

        return component.setComponent(this);
    }

    /**
     * Factory that creates a new {@link HslColor} with the new {@link HueHslColorComponent}.
     */
    final HslColor setHue(final HueHslColorComponent hue) {
        return this.hue.equals(hue) ?
                this :
                this.replace(hue, this.saturation, this.lightness);
    }

    /**
     * Factory that creates a new {@link HslColor} with the new {@link SaturationHslColorComponent}.
     */
    final HslColor setSaturation(final SaturationHslColorComponent saturation) {
        return this.saturation.equals(saturation) ?
                this :
                this.replace(this.hue, saturation, this.lightness);
    }

    /**
     * Factory that creates a new {@link HslColor} with the new {@link LightnessHslColorComponent}.
     */
    final HslColor setValue(final LightnessHslColorComponent lightness) {
        return this.lightness.equals(lightness) ?
                this :
                this.replace(this.hue, this.saturation, lightness);
    }

    /**
     * Factory that creates a new {@link HslColor} with the new {@link LightnessHslColorComponent}.
     */
    final HslColor setAlpha(final AlphaHslColorComponent alpha) {
        return this.alpha().equals(alpha) ?
                this :
                AlphaHslColor.withAlpha(this.hue,
                        this.saturation,
                        this.lightness,
                        alpha);
    }

    /**
     * Factory that creates a {@link HslColor} with the given {@link HslColorComponent components}.
     */
    abstract HslColor replace(final HueHslColorComponent hue,
                              final SaturationHslColorComponent saturation,
                              final LightnessHslColorComponent lightness);

    // properties

    /**
     * Getter that returns only the {@link HueHslColorComponent}
     */
    public final HueHslColorComponent hue() {
        return this.hue;
    }

    final HueHslColorComponent hue;

    /**
     * Getter that returns only the {@link SaturationHslColorComponent}
     */
    public final SaturationHslColorComponent saturation() {
        return this.saturation;
    }

    final SaturationHslColorComponent saturation;

    /**
     * Getter that returns only the {@link LightnessHslColorComponent}
     */
    public final LightnessHslColorComponent lightness() {
        return this.lightness;
    }

    final LightnessHslColorComponent lightness;

    /**
     * Getter that returns the alpha component.
     */
    public abstract AlphaHslColorComponent alpha();

    // Color............................................................................................................

    @Override
    public final HslColor toHsl() {
        return this;
    }

    @Override
    public final HsvColor toHsv() {
        return this.toRgb().toHsv();
    }

    /**
     * Returns the equivalent {@link Color}.<br>
     * Converts an HSL color value to RGB. Conversion formula adapted from <a>http://en.wikipedia.org/wiki/HSL_color_space}</a>.
     * Assumes h, s, and l are contained in the set [0, 1] and returns r, g, and b in the set [0, 255].
     *
     * <pre>
     * function hslToRgb(h, s, l){
     *     var r, g, b;
     *
     *     if(s == 0){
     *         r = g = b = l; // achromatic
     *     }else{
     *         function hue2rgb(p, q, t){
     *             if(t < 0) t += 1;
     *             if(t > 1) t -= 1;
     *             if(t < 1/6) return p + (q - p) * 6 * t;
     *             if(t < 1/2) return q;
     *             if(t < 2/3) return p + (q - p) * (2/3 - t) * 6;
     *             return p;
     *         }
     *
     *         var q = l < 0.5 ? l * (1 + s) : l + s - l * s;
     *         var p = 2 * l - q;
     *         r = hue2rgb(p, q, h + 1/3);
     *         g = hue2rgb(p, q, h);
     *         b = hue2rgb(p, q, h - 1/3);
     *     }
     *
     *     return [r * 255, g * 255, b * 255];
     * }
     *         </pre>
     * <p>
     * <a>http://mjijackson.com/2008/02/rgb-to-hsl-and-rgb-to-hsv-color-model-conversion-algorithms-in-javascript</a>
     */
    @Override
    public final RgbColor toRgb() {
        // vars
        float red;
        float green;
        float blue;

        // constants
        final float saturation = this.saturation.value;
        final float lightness = this.lightness.value;

        if (0 == saturation) {
            red = lightness;
            green = lightness;
            blue = lightness;
        } else {
            final float q = lightness < 0.5f ? lightness * (1 + saturation)
                    : (lightness + saturation) - (lightness * saturation);
            final float p = (2 * lightness) - q;

            final float hue = this.hue.value / HueHslColorComponent.MAX; // now within 0..1
            red = HslColor.hue2rgb(p, q, hue + (1f / 3));
            green = HslColor.hue2rgb(p, q, hue);
            blue = HslColor.hue2rgb(p, q, hue - (1f / 3));
        }

        return this.rgbColor(RgbColor.with(red, green, blue));
    }

    /**
     * Provides an opportunity to add any alpha component.
     */
    abstract RgbColor rgbColor(final RgbColor color);

    /**
     * <pre>
     * function hue2rgb(p, q, t){
     *     if(t < 0) t += 1;
     *     if(t > 1) t -= 1;
     *     if(t < 1/6) return p + (q - p) * 6 * t;
     *     if(t < 1/2) return q;
     *     if(t < 2/3) return p + (q - p) * (2/3 - t) * 6;
     *     return p;
     * }
     * </pre>
     *
     * <a>http://mjijackson.com/2008/02/rgb-to-hsl-and-rgb-to-hsv-color-model-conversion-algorithms-in-javascript</a>
     */
    private static float hue2rgb(final float p,
                                 final float q,
                                 float t) {
        if (t < 0) {
            t += 1;
        }
        if (t > 1) {
            t -= 1;
        }
        if (t < (1f / 6)) {
            return p + ((q - p) * 6 * t);
        }
        if (t < (1f / 2)) {
            return q;
        }
        if (t < (2f / 3)) {
            return p + ((q - p) * ((2f / 3) - t) * 6);
        }
        return p;
    }

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return Objects.hash(this.hue, this.saturation, this.lightness);
    }

    @Override
    boolean equals0(final Object other) {
        return this.equals1(Cast.to(other));
    }

    private boolean equals1(final HslColor other) {
        return this.hue.equals(other.hue) &&
                this.saturation.equals(other.saturation) &&
                this.lightness.equals(other.lightness) &&
                this.equals2(other);
    }

    abstract boolean equals2(final HslColor other);

    @Override
    public final void buildToString(final ToStringBuilder builder) {
        builder.separator(",")
                .append(this.functionName())
                .value(this.hue)
                .value(this.saturation)
                .value(this.lightness);
        this.buildToStringAlpha(builder);
        builder.append(')');
    }

    abstract String functionName();

    abstract void buildToStringAlpha(final ToStringBuilder builder);
}
