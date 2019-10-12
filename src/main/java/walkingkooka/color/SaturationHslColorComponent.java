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
 * A {@link HslColorComponent} holding the hue component which is a value between 0 and 360 degrees.
 */
final public class SaturationHslColorComponent extends AlphaLightnessOrSaturationHslColorComponent {

    /**
     * Factory that creates a new {@link SaturationHslColorComponent}
     */
    static SaturationHslColorComponent with(final float value) {
        SaturationHslColorComponent.check(value);
        return new SaturationHslColorComponent(value);
    }

    /**
     * Private constructor use factory
     */
    private SaturationHslColorComponent(final float value) {
        super(value);
    }

    @Override
    public SaturationHslColorComponent add(final float value) {
        return 0 == value ? this
                : new SaturationHslColorComponent(
                HslColorComponent.add(value, SaturationHslColorComponent.MIN, SaturationHslColorComponent.MAX));
    }

    @Override
    public SaturationHslColorComponent setValue(final float value) {
        SaturationHslColorComponent.check(value);
        return this.value == value ? this : this.replace(value);
    }

    /**
     * Factory that creates a new {@link SaturationHslColorComponent}
     */
    @Override
    SaturationHslColorComponent replace(final float value) {
        return new SaturationHslColorComponent(value);
    }

    @Override
    HslColor setComponent(final HslColor hsl) {
        return hsl.setSaturation(this);
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof SaturationHslColorComponent;
    }
}
