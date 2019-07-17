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
 * A {@link RgbColorComponent} that holds an alpha value.
 */
final public class AlphaRgbColorComponent extends RgbColorComponent {

    /**
     * Factory that creates a {@link RgbColorComponent}
     */
    static AlphaRgbColorComponent with(final byte value) {
        return AlphaRgbColorComponent.CONSTANTS[RgbColorComponent.mask(value)];
    }

    private static final AlphaRgbColorComponent[] CONSTANTS = createConstants(new AlphaRgbColorComponent[256], AlphaRgbColorComponent::new);

    final static AlphaRgbColorComponent OPAQUE = AlphaRgbColorComponent.CONSTANTS[255];

    /**
     * Private ctor use factory
     */
    private AlphaRgbColorComponent(final int value) {
        super(value);
    }

    @Override
    public AlphaRgbColorComponent add(final int value) {
        return AlphaRgbColorComponent.with(addUnsignedSaturated(this.unsignedIntValue, value));
    }

    /**
     * Creates a {@link AlphaRgbColorComponent}.
     */
    @Override
    AlphaRgbColorComponent replace(final int value) {
        return AlphaRgbColorComponent.CONSTANTS[value];
    }

    @Override
    RgbColor setComponent(final RgbColor color) {
        return color.setAlpha(this);
    }

    @Override
    int value(final RgbColor color) {
        return color.alpha().unsignedIntValue;
    }

    @Override
    RgbColor setComponent(final RgbColor color, final int value) {
        return color.setAlpha(AlphaRgbColorComponent.CONSTANTS[value]);
    }

    @Override
    public boolean isRed() {
        return false;
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
        return true;
    }

    // Serializable

    private static final long serialVersionUID = 1;

    /**
     * Handles keeping instances singletons.
     */
    private Object readResolve() {
        return AlphaRgbColorComponent.CONSTANTS[this.unsignedIntValue];
    }
}
