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
 * A {@link HsvColorComponent} holding the saturation component which is a value between 0 and 360 degrees.
 */
final public class SaturationHsvColorComponent extends AlphaSaturationOrValueHsvColorComponent {

    /**
     * Factory that creates a new {@link SaturationHsvColorComponent}
     */
    static SaturationHsvColorComponent with(final float value) {
        SaturationHsvColorComponent.check(value);
        return new SaturationHsvColorComponent(value);
    }

    /**
     * Private constructor use factory
     */
    private SaturationHsvColorComponent(final float value) {
        super(value);
    }

    @Override
    public SaturationHsvColorComponent add(final float value) {
        return 0 == value ? this
                : new SaturationHsvColorComponent(
                HsvColorComponent.add(value, SaturationHsvColorComponent.MIN, SaturationHsvColorComponent.MAX));
    }

    @Override
    public SaturationHsvColorComponent setValue(final float value) {
        SaturationHsvColorComponent.check(value);
        return this.value == value ? this : this.replace(value);
    }

    /**
     * Factory that creates a new {@link SaturationHsvColorComponent}
     */
    @Override
    SaturationHsvColorComponent replace(final float value) {
        return new SaturationHsvColorComponent(value);
    }

    @Override
    public boolean isSaturation() {
        return true;
    }

    @Override
    public boolean isValue() {
        return false;
    }

    @Override
    public boolean isAlpha() {
        return false;
    }

    @Override
    HsvColor setComponent(final HsvColor hsv) {
        return hsv.setSaturation(this);
    }

    // Object

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof SaturationHsvColorComponent;
    }
}
