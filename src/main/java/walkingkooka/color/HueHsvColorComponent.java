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
 * A {@link HsvColorComponent} holding the hue component which is a value between 0 and 360 degrees.
 */
final public class HueHsvColorComponent extends HsvColorComponent {

    /**
     * The lowest possible legal value.
     */
    public final static float MIN = 0.0f;

    /**
     * The highest possible legal value.
     */
    public final static float MAX = 360.0f;

    /**
     * Factory that creates a new {@link HueHsvColorComponent}
     */
    static HueHsvColorComponent with(final float value) {
        HueHsvColorComponent.check(value);
        return new HueHsvColorComponent(value);
    }

    /**
     * Verifies that the value is within the acceptable range.
     */
    private static void check(final float value) {
        check(value, MIN, MAX);
    }

    /**
     * Private constructor use factory
     */
    private HueHsvColorComponent(final float value) {
        super(value);
    }

    @Override
    public HueHsvColorComponent add(final float value) {
        return 0 == value ? this : new HueHsvColorComponent(HsvColorComponent.add(value, HueHsvColorComponent.MIN, HueHsvColorComponent.MAX));
    }

    @Override
    public HueHsvColorComponent setValue(final float value) {
        HueHsvColorComponent.check(value);
        return this.value == value ? this : this.replace(value);
    }

    /**
     * Factory that creates a new {@link HueHsvColorComponent}
     */
    @Override
    HueHsvColorComponent replace(final float value) {
        return new HueHsvColorComponent(value);
    }

    @Override
    HsvColor setComponent(final HsvColor hsv) {
        return hsv.setHue(this);
    }

    @Override
    public HueHsvColorComponent invert() {
        return this.setValue(
                MAX - this.value()
        );
    }

    @Override
    protected boolean canBeEqual(final Object other) {
        return other instanceof HueHsvColorComponent;
    }

    @Override
    public String toString() {
        return String.valueOf(Math.round(this.value));
    }
}
