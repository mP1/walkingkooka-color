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
 * A {@link RgbColorComponent} that holds a green value.
 */
final public class GreenRgbColorComponent extends RgbColorComponent {

    /**
     * Factory that creates a {@link RgbColorComponent}
     */
    static GreenRgbColorComponent with(final byte value) {
        return GreenRgbColorComponent.CONSTANTS[RgbColorComponent.mask(value)];
    }

    private static final GreenRgbColorComponent[] CONSTANTS = createConstants(new GreenRgbColorComponent[256], GreenRgbColorComponent::new);

    /**
     * Private ctor use factory
     */
    private GreenRgbColorComponent(final int value) {
        super(value);
    }

    @Override
    public GreenRgbColorComponent add(final int value) {
        return GreenRgbColorComponent.with(addUnsignedSaturated(this.unsignedIntValue, value));
    }

    /**
     * Creates a {@link GreenRgbColorComponent}.
     */
    @Override
    GreenRgbColorComponent replace(final int value) {
        return GreenRgbColorComponent.CONSTANTS[value];
    }

    @Override
    RgbColor setComponent(final RgbColor color) {
        return color.setGreen(this);
    }

    @Override
    int value(final RgbColor color) {
        return color.green().unsignedIntValue;
    }

    @Override
    RgbColor setComponent(final RgbColor color, final int value) {
        return color.setGreen(GreenRgbColorComponent.CONSTANTS[RgbColorComponent.mask(value)]);
    }
}
