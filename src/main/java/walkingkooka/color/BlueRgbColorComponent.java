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
 * A {@link RgbColorComponent} that holds a blue value.
 */
final public class BlueRgbColorComponent extends RgbColorComponent {

    /**
     * Factory that creates a {@link RgbColorComponent}
     */
    static BlueRgbColorComponent with(final byte value) {
        return BlueRgbColorComponent.CONSTANTS[RgbColorComponent.mask(value)];
    }

    private static final BlueRgbColorComponent[] CONSTANTS = createConstants(new BlueRgbColorComponent[256], BlueRgbColorComponent::new);

    /**
     * Private ctor use factory
     */
    private BlueRgbColorComponent(final int value) {
        super(value);
    }

    @Override
    public BlueRgbColorComponent add(final int value) {
        return BlueRgbColorComponent.with(addUnsignedSaturated(this.unsignedIntValue, value));
    }

    /**
     * Creates a {@link BlueRgbColorComponent}.
     */
    @Override
    BlueRgbColorComponent replace(final int value) {
        return BlueRgbColorComponent.CONSTANTS[value];
    }

    @Override
    RgbColor setComponent(final RgbColor color) {
        return color.setBlue(this);
    }

    @Override
    int value(final RgbColor color) {
        return color.blue().unsignedIntValue;
    }

    @Override
    RgbColor setComponent(final RgbColor color, final int value) {
        return color.setBlue(BlueRgbColorComponent.CONSTANTS[RgbColorComponent.mask(value)]);
    }
}
