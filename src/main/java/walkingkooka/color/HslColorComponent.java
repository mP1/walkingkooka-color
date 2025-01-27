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

/**
 * Common base for all {@link HslColor} components.
 */
abstract public class HslColorComponent extends HslOrHsvColorComponent {

    /**
     * {@see HueHslColorComponent}
     */
    public static HueHslColorComponent hue(final float value) {
        return HueHslColorComponent.with(value);
    }

    /**
     * {@see SaturationHslColorComponent}
     */
    public static SaturationHslColorComponent saturation(final float value) {
        return SaturationHslColorComponent.with(value);
    }

    /**
     * {@see LightnessHslColorComponent}
     */
    public static LightnessHslColorComponent lightness(final float value) {
        return LightnessHslColorComponent.with(value);
    }

    /**
     * {@see AlphaHslColorComponent}
     */
    public static AlphaHslColorComponent alpha(final float value) {
        return AlphaHslColorComponent.with(value);
    }

    /**
     * Package private to limit sub classing
     */
    HslColorComponent(final float value) {
        super(value);
    }

    /**
     * Performs a saturated add returning a new {@link HslColorComponent} if the value changed.
     */
    abstract public HslColorComponent add(float value);

    /**
     * Would be setter that returns a {@link HslColorComponent} with the new value.
     */
    @Override //
    abstract public HslColorComponent setValue(float value);

    /**
     * Factory that creates a new {@link HslColorComponent} of the same type with the given value.
     */
    abstract HslColorComponent replace(float value);

    /**
     * Returns true if this is a {@link HueHslColorComponent}.
     */
    public final boolean isHue() {
        return this instanceof HueHslColorComponent;
    }

    /**
     * Returns true if this is a {@link SaturationHslColorComponent}.
     */
    public final boolean isSaturation() {
        return this instanceof SaturationHslColorComponent;
    }

    /**
     * Returns true if this is a {@link LightnessHslColorComponent}.
     */
    public final boolean isLightness() {
        return this instanceof LightnessHslColorComponent;
    }

    /**
     * Returns true if this is a {@link AlphaHslColorComponent}.
     */
    public final boolean isAlpha() {
        return this instanceof AlphaHslColorComponent;
    }

    /**
     * Setter used to create a new {@link HslColor} with this component replaced if different
     */
    abstract HslColor setComponent(HslColor hsl);
}
