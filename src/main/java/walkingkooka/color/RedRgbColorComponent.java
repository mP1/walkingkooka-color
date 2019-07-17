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
 * A {@link RgbColorComponent} that holds a red value.
 */
final public class RedRgbColorComponent extends RgbColorComponent {

    /**
     * Factory that creates a {@link RgbColorComponent}
     */
    static RedRgbColorComponent with(final byte value) {
        return RedRgbColorComponent.CONSTANTS[RgbColorComponent.mask(value)];
    }

    private static final RedRgbColorComponent[] CONSTANTS = createConstants(new RedRgbColorComponent[256], RedRgbColorComponent::new);

    /**
     * Private ctor use factory
     */
    private RedRgbColorComponent(final int value) {
        super(value);
    }

    @Override
    public RedRgbColorComponent add(final int value) {
        return RedRgbColorComponent.with(addUnsignedSaturated(this.unsignedIntValue, value));
    }

    /**
     * Creates a {@link RedRgbColorComponent}.
     */
    @Override
    RedRgbColorComponent replace(final int value) {
        return RedRgbColorComponent.CONSTANTS[value];
    }

    @Override
    RgbColor setComponent(final RgbColor color) {
        return color.setRed(this);
    }

    @Override
    int value(final RgbColor color) {
        return color.red().unsignedIntValue;
    }

    @Override
    RgbColor setComponent(final RgbColor color, final int value) {
        return color.setRed(RedRgbColorComponent.CONSTANTS[RgbColorComponent.mask(value)]);
    }

    @Override
    public boolean isRed() {
        return true;
    }

    @Override
    public boolean isGreen() {
        return false;
    }

    @Override
    public boolean isBlue() {
        return false;
    }

    @Override
    public boolean isAlpha() {
        return false;
    }

    // Serializable

    private static final long serialVersionUID = 1;

    /**
     * Handles keeping instances singletons.
     */
    private Object readResolve() {
        return RedRgbColorComponent.CONSTANTS[this.unsignedIntValue];
    }
}
