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
abstract class AlphaLightnessOrSaturationHslColorComponent extends HslColorComponent {

    /**
     * The lowest possible legal value.
     */
    public final static float MIN = 0.0f;

    /**
     * The highest possible legal value.
     */
    public final static float MAX = 1.0f;

    /**
     * Verifies that the value is within the acceptable range.
     */
    static void check(final float value) {
        check(value, MIN, MAX);
    }

    /**
     * Package private to limit sub classing.
     */
    AlphaLightnessOrSaturationHslColorComponent(final float value) {
        super(value);
    }

    @Override
    public final HslColorComponent invert() {
        return this.setValue(
            MAX - this.value()
        );
    }

    @Override
    public final String toString() {
        return this.toStringPercentage();
    }
}
