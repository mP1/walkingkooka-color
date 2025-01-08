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
 * A {@link HsvColorComponent} holding the value component which is a value between <code>0.0</code> and <code>1.0</code>
 */
final public class ValueHsvColorComponent extends AlphaSaturationOrValueHsvColorComponent {

    /**
     * Factory that creates a new {@link ValueHsvColorComponent}
     */
    static ValueHsvColorComponent with(final float value) {
        ValueHsvColorComponent.check(value);
        return new ValueHsvColorComponent(value);
    }

    /**
     * Private constructor use factory
     */
    private ValueHsvColorComponent(final float value) {
        super(value);
    }

    @Override
    public ValueHsvColorComponent add(final float value) {
        return 0 == value ? this
            : new ValueHsvColorComponent(HsvColorComponent.add(value, ValueHsvColorComponent.MIN, ValueHsvColorComponent.MAX));
    }

    @Override
    public ValueHsvColorComponent setValue(final float value) {
        ValueHsvColorComponent.check(value);
        return this.value == value ? this : this.replace(value);
    }

    /**
     * Factory that creates a new {@link ValueHsvColorComponent}
     */
    @Override
    ValueHsvColorComponent replace(final float value) {
        return new ValueHsvColorComponent(value);
    }

    @Override
    HsvColor setComponent(final HsvColor hsv) {
        return hsv.setValue(this);
    }

    // Object

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof ValueHsvColorComponent;
    }
}
