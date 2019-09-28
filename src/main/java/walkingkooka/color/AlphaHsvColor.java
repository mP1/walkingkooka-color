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
final class AlphaHsvColor extends HsvColor {

    static AlphaHsvColor withAlpha(final HueHsvColorComponent hue,
                                   final SaturationHsvColorComponent saturation,
                                   final ValueHsvColorComponent value,
                                   final AlphaHsvColorComponent alpha) {
        return new AlphaHsvColor(hue, saturation, value, alpha);
    }

    private AlphaHsvColor(final HueHsvColorComponent hue,
                          final SaturationHsvColorComponent saturation,
                          final ValueHsvColorComponent value,
                          final AlphaHsvColorComponent alpha) {
        super(hue, saturation, value);

        this.alpha = alpha;
    }

    @Override
    public AlphaHsvColorComponent alpha() {
        return this.alpha;
    }

    private final AlphaHsvColorComponent alpha;

    @Override
    HsvColor replace(final HueHsvColorComponent hue,
                     final SaturationHsvColorComponent saturation,
                     final ValueHsvColorComponent value) {
        return new AlphaHsvColor(hue, saturation, value, this.alpha);
    }

    @Override
    RgbColor toRgb0(final RgbColor color) {
        return color.set(this.alpha.toAlphaColorComponent());
    }

    // Object..........................................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof AlphaHsvColor;
    }

    @Override
    boolean equals2(final HsvColor other) {
        return this.alpha.equals(other.alpha());
    }

    // UsesToStringBuilder..............................................................................................

    @Override
    String functionName() {
        return "hsva(";
    }

    @Override
    void buildToStringAlpha(final ToStringBuilder builder) {
        builder.value(this.alpha);
    }
}
