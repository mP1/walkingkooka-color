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

import walkingkooka.ToStringBuilder;

/**
 * A {@link Color} that includes an alpha property.
 */
final class AlphaHslColor extends HslColor {

    static AlphaHslColor withAlpha(final HueHslColorComponent hue,
                                   final SaturationHslColorComponent saturation,
                                   final LightnessHslColorComponent lightness,
                                   final AlphaHslColorComponent alpha) {
        return new AlphaHslColor(hue, saturation, lightness, alpha);
    }

    private AlphaHslColor(final HueHslColorComponent hue,
                          final SaturationHslColorComponent saturation,
                          final LightnessHslColorComponent lightness,
                          final AlphaHslColorComponent alpha) {
        super(hue, saturation, lightness);

        this.alpha = alpha;
    }

    @Override
    public AlphaHslColorComponent alpha() {
        return this.alpha;
    }

    private final AlphaHslColorComponent alpha;

    @Override
    HslColor replace(final HueHslColorComponent hue,
                     final SaturationHslColorComponent saturation,
                     final LightnessHslColorComponent lightness) {
        return new AlphaHslColor(hue, saturation, lightness, this.alpha);
    }

    @Override
    RgbColor rgbColor(final RgbColor color) {
        return color.set(this.alpha.toAlphaColorComponent());
    }

    // Object..........................................................................................................

    @Override
    int hashCodeAlpha() {
        return this.alpha.hashCode();
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof AlphaHslColor;
    }

    @Override
    boolean equals2(final HslColor other) {
        return this.alpha.equals(other.alpha());
    }

    // UsesToStringBuilder..............................................................................................

    @Override
    String functionName() {
        return "hsla(";
    }

    @Override
    void buildToStringAlpha(final ToStringBuilder builder) {
        builder.value(this.alpha);
    }
}
