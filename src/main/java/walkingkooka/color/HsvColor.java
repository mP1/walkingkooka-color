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
 * Holds the hue, saturation and value which describe a rgb.
 */
@SuppressWarnings("lgtm[java/inconsistent-equals-and-hashcode]")
public abstract class HsvColor extends Color {

    /**
     * Factory that creates a new {@link HsvColor}
     */
    static HsvColor with(final HueHsvColorComponent hue,
                         final SaturationHsvColorComponent saturation,
                         final ValueHsvColorComponent value) {
        Objects.requireNonNull(hue, "hue");
        Objects.requireNonNull(saturation, "saturation");
        Objects.requireNonNull(value, "value");

        return OpaqueHsvColor.withOpaque(hue, saturation, value);
    }

    /**
     * Package private to limit sub classing.
     */
    HsvColor(final HueHsvColorComponent hue,
             final SaturationHsvColorComponent saturation,
             final ValueHsvColorComponent value) {
        this.hue = hue;
        this.saturation = saturation;
        this.value = value;
    }

    /**
     * Would be setter that returns a {@link HsvColor} holding the new component. If the component is not new this will be returned.
     */
    public final HsvColor set(final HsvColorComponent component) {
        Objects.requireNonNull(component, "component");

        return component.setComponent(this);
    }

    /**
     * Factory that creates a new {@link HsvColor} with the new {@link HueHsvColorComponent}.
     */
    final HsvColor setHue(final HueHsvColorComponent hue) {
        return this.hue.equals(hue) ?
                this :
                this.replace(hue, this.saturation, this.value);
    }

    /**
     * Factory that creates a new {@link HsvColor} with the new {@link SaturationHsvColorComponent}.
     */
    final HsvColor setSaturation(final SaturationHsvColorComponent saturation) {
        return this.saturation.equals(saturation) ?
                this :
                this.replace(this.hue, saturation, this.value);
    }

    /**
     * Factory that creates a new {@link HsvColor} with the new {@link ValueHsvColorComponent}.
     */
    final HsvColor setValue(final ValueHsvColorComponent value) {

        return this.value.equals(value) ?
                this :
                this.replace(this.hue, this.saturation, value);
    }

    /**
     * Factory that creates a new {@link HsvColor} with the new {@link AlphaHsvColorComponent}.
     */
    final HsvColor setAlpha(final AlphaHsvColorComponent alpha) {

        return this.alpha().equals(alpha) ?
                this :
                AlphaHsvColor.withAlpha(this.hue, this.saturation, this.value, alpha);
    }

    /**
     * Factory that creates a {@link HsvColor} with the given {@link HsvColorComponent components}.
     */
    abstract HsvColor replace(final HueHsvColorComponent hue,
                              final SaturationHsvColorComponent saturation,
                              final ValueHsvColorComponent value);

    // properties

    /**
     * Getter that returns only the {@link HueHsvColorComponent}
     */
    public final HueHsvColorComponent hue() {
        return this.hue;
    }

    final HueHsvColorComponent hue;

    /**
     * Getter that returns only the {@link SaturationHsvColorComponent}
     */
    public final SaturationHsvColorComponent saturation() {
        return this.saturation;
    }

    final SaturationHsvColorComponent saturation;

    /**
     * Getter that returns only the {@link ValueHsvColorComponent}
     */
    public final ValueHsvColorComponent value() {
        return this.value;
    }

    final ValueHsvColorComponent value;

    /**
     * Getter that returns only the {@link AlphaHsvColorComponent}
     */
    public abstract AlphaHsvColorComponent alpha();

    // Color............................................................................................................

    @Override
    public final HslColor toHsl() {
        return this.toRgb().toHsl();
    }

    @Override
    public final HsvColor toHsv() {
        return this;
    }

    /**
     * Returns the equivalent {@link Color}.<br>
     * <A>http://en.wikipedia.org/wiki/HSL_and_HSV</A>
     */
    @Override
    public RgbColor toRgb() {
        final float value = this.value.value;
        final float chroma = this.saturation.value * value;
        final float q = this.hue.value / 60.0f;
        final float x = chroma * (1.0f - Math.abs((q % 2.0f) - 1.0f));
        float red = 0;
        float green = 0;
        float blue = 0;

        switch ((int) q) {
            case 0:
                red = chroma;
                green = x;
                break;
            case 1:
                red = x;
                green = chroma;
                break;
            case 2:
                green = chroma;
                blue = x;
                break;
            case 3:
                green = x;
                blue = chroma;
                break;
            case 4:
                red = x;
                blue = chroma;
                break;
            case 5:
                red = chroma;
                blue = x;
                break;
        }

        final float min = value - chroma;
        red += min;
        green += min;
        blue += min;

        return this.toRgb0(RgbColor.with(red, green, blue));
    }

    abstract RgbColor toRgb0(final RgbColor color);

    // invert...........................................................................................................

    @Override
    public final HsvColor invert() {
        return this.setHue(
                (HueHsvColorComponent) this.hue().invert()
        ).setSaturation(
                (SaturationHsvColorComponent) this.saturation().invert()
        ).setValue(
                (ValueHsvColorComponent) this.value().invert()
        );
    }

    // Object...........................................................................................................

    @Override
    public final int hashCode() {
        return Objects.hash(this.hue, this.saturation, this.value);
    }

    @Override final boolean equals0(final Object other) {
        return this.equals1(Cast.to(other));
    }

    private boolean equals1(final HsvColor other) {
        return this.hue.equals(other.hue) &&
                this.saturation.equals(other.saturation) &&
                this.value.equals(other.value) &&
                this.equals2(other);
    }

    abstract boolean equals2(final HsvColor other);

    @Override
    public final void buildToString(final ToStringBuilder builder) {
        builder.separator(",")
                .append(this.functionName())
                .value(this.hue)
                .value(this.saturation)
                .value(this.value);
        this.buildToStringAlpha(builder);
        builder.append(')');
    }

    abstract String functionName();

    abstract void buildToStringAlpha(final ToStringBuilder builder);
}
