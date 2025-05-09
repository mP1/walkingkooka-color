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
 * A {@link HsvColor} that includes an alpha property.
 */
final class OpaqueHsvColor extends HsvColor {

    static OpaqueHsvColor withOpaque(final HueHsvColorComponent hue,
                                     final SaturationHsvColorComponent saturation,
                                     final ValueHsvColorComponent value) {
        return new OpaqueHsvColor(hue, saturation, value);
    }

    private OpaqueHsvColor(final HueHsvColorComponent hue,
                           final SaturationHsvColorComponent saturation,
                           final ValueHsvColorComponent value) {
        super(hue, saturation, value);
    }

    @Override
    public AlphaHsvColorComponent alpha() {
        return AlphaHsvColorComponent.OPAQUE;
    }

    /**
     * Factory that creates a {@link HsvColor} with the given {@link HsvColorComponent components}.
     */
    @Override
    HsvColor replace(final HueHsvColorComponent hue,
                     final SaturationHsvColorComponent saturation,
                     final ValueHsvColorComponent value) {
        return withOpaque(hue, saturation, value);
    }

    @Override
    RgbColor toRgb0(final RgbColor color) {
        return color;
    }

    // Object..........................................................................................................

    @Override
    int hashCodeAlpha() {
        return 0;
    }

    @Override
    boolean equalsAlpha(final HsvColor other) {
        return true; // no alpha component to compare.
    }

    // UsesToStringBuilder..............................................................................................

    @Override
    String functionName() {
        return "hsv(";
    }

    @Override
    void buildToStringAlpha(final ToStringBuilder builder) {
    }
}
