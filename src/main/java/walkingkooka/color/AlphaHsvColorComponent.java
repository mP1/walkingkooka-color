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
 * A {@link HsvColorComponent} holding the alpha component which is a value between <code>0.0</code> and <code>1.0</code>
 */
final public class AlphaHsvColorComponent extends AlphaSaturationOrValueHsvColorComponent {

    /**
     * An opaque alpha component returned by {@link OpaqueHsvColor#alpha()}.
     */
    final static AlphaHsvColorComponent OPAQUE = AlphaHsvColorComponent.with(MAX);
    
    /**
     * Factory that creates a new {@link AlphaHsvColorComponent}
     */
    static AlphaHsvColorComponent with(final float value) {
        AlphaHsvColorComponent.check(value);
        return new AlphaHsvColorComponent(value);
    }

    /**
     * Private constructor use factory
     */
    private AlphaHsvColorComponent(final float value) {
        super(value);
    }

    @Override
    public AlphaHsvColorComponent add(final float value) {
        return 0 == value ? this
                : new AlphaHsvColorComponent(HsvColorComponent.add(value, AlphaHsvColorComponent.MIN, AlphaHsvColorComponent.MAX));
    }

    @Override
    public AlphaHsvColorComponent setValue(final float value) {
        AlphaHsvColorComponent.check(value);
        return this.value == value ? this : this.replace(value);
    }

    /**
     * Factory that creates a new {@link AlphaHsvColorComponent}
     */
    @Override
    AlphaHsvColorComponent replace(final float value) {
        return new AlphaHsvColorComponent(value);
    }

    AlphaRgbColorComponent toAlphaColorComponent() {
        return AlphaRgbColorComponent.with(RgbColorComponent.toByte(this.value));
    }

    @Override
    HsvColor setComponent(final HsvColor hsv) {
        return hsv.setAlpha(this);
    }

    // Object

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof AlphaHsvColorComponent;
    }
}
