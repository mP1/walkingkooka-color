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
 * A {@link HslColorComponent} holding the hue component which is a value between <code>0</code> and <code>1.0</code>.
 */
final public class LightnessHslColorComponent extends AlphaLightnessOrSaturationHslColorComponent {

    /**
     * Factory that creates a new {@link LightnessHslColorComponent}
     */
    static LightnessHslColorComponent with(final float value) {
        LightnessHslColorComponent.check(value);
        return new LightnessHslColorComponent(value);
    }

    /**
     * Private constructor use factory
     */
    private LightnessHslColorComponent(final float value) {
        super(value);
    }

    @Override
    public LightnessHslColorComponent add(final float value) {
        return 0 == value ? this
                : new LightnessHslColorComponent(HslColorComponent.add(value, LightnessHslColorComponent.MIN, LightnessHslColorComponent.MAX));
    }

    @Override
    public LightnessHslColorComponent setValue(final float value) {
        LightnessHslColorComponent.check(value);
        return this.value == value ? this : this.replace(value);
    }

    /**
     * Factory that creates a new {@link LightnessHslColorComponent}
     */
    @Override
    LightnessHslColorComponent replace(final float value) {
        return new LightnessHslColorComponent(value);
    }

    @Override
    HslColor setComponent(final HslColor hsl) {
        return hsl.setLightness(this);
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof LightnessHslColorComponent;
    }
}
