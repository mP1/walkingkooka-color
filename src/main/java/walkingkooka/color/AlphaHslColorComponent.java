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
 * A {@link HslColorComponent} holding the alpha component which is a value between 0.0 and 1.0
 */
final public class AlphaHslColorComponent extends AlphaLightnessOrSaturationHslColorComponent {

    /**
     * An opaque alpha component returned by {@link OpaqueHslColor#alpha()}.
     */
    final static AlphaHslColorComponent OPAQUE = AlphaHslColorComponent.with(MAX);

    /**
     * Factory that creates a new {@link AlphaHslColorComponent}
     */
    static AlphaHslColorComponent with(final float value) {
        AlphaHslColorComponent.check(value);
        return new AlphaHslColorComponent(value);
    }

    /**
     * Private constructor use factory
     */
    private AlphaHslColorComponent(final float value) {
        super(value);
    }

    @Override
    public AlphaHslColorComponent add(final float value) {
        return 0 == value ? this : new AlphaHslColorComponent(HslColorComponent.add(value, AlphaHslColorComponent.MIN, AlphaHslColorComponent.MAX));
    }

    @Override
    public AlphaHslColorComponent setValue(final float value) {
        AlphaHslColorComponent.check(value);
        return this.value == value ? this : this.replace(value);
    }

    /**
     * Factory that creates a new {@link AlphaHslColorComponent}
     */
    @Override
    AlphaHslColorComponent replace(final float value) {
        return new AlphaHslColorComponent(value);
    }

    AlphaRgbColorComponent toAlphaColorComponent() {
        return AlphaRgbColorComponent.with(RgbColorComponent.toByte(this.value));
    }

    @Override
    HslColor setComponent(final HslColor hsl) {
        return hsl.setAlpha(this);
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof AlphaHslColorComponent;
    }
}
