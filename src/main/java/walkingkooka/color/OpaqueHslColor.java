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
 * A {@link HslColor} that includes an alpha property.
 */
final class OpaqueHslColor extends HslColor {

    static OpaqueHslColor withOpaque(final HueHslColorComponent hue,
                                     final SaturationHslColorComponent saturation,
                                     final LightnessHslColorComponent lightness) {
        return new OpaqueHslColor(hue, saturation, lightness);
    }

    private OpaqueHslColor(final HueHslColorComponent hue,
                           final SaturationHslColorComponent saturation,
                           final LightnessHslColorComponent lightness) {
        super(hue, saturation, lightness);
    }

    @Override
    public AlphaHslColorComponent alpha() {
        return AlphaHslColorComponent.OPAQUE;
    }

    /**
     * Factory that creates a {@link HslColor} with the given {@link HslColorComponent components}.
     */
    @Override
    HslColor replace(final HueHslColorComponent hue,
                     final SaturationHslColorComponent saturation,
                     final LightnessHslColorComponent lightness) {
        return withOpaque(hue, saturation, lightness);
    }

    @Override
    RgbColor rgbColor(final RgbColor color) {
        return color;
    }

    // Object..........................................................................................................

    @Override
    int hashCodeAlpha() {
        return 0;
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof OpaqueHslColor;
    }

    @Override
    boolean equals2(final HslColor other) {
        return true; // no alpha component to compare.
    }

    // UsesToStringBuilder..............................................................................................

    @Override
    String functionName() {
        return "hsl(";
    }

    @Override
    void buildToStringAlpha(final ToStringBuilder builder) {
    }
}
