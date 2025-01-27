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
 * Common base for all {@link HsvColor} components.
 */
abstract public class HsvColorComponent extends HslOrHsvColorComponent {

    /**
     * {@see HueHsvColorComponent}
     */
    public static HueHsvColorComponent hue(final float value) {
        return HueHsvColorComponent.with(value);
    }

    /**
     * {@see SaturationHsvColorComponent}
     */
    public static SaturationHsvColorComponent saturation(final float value) {
        return SaturationHsvColorComponent.with(value);
    }

    /**
     * {@see ValueHsvColorComponent}
     */
    public static ValueHsvColorComponent value(final float value) {
        return ValueHsvColorComponent.with(value);
    }

    /**
     * {@see AlphaHsvColorComponent}
     */
    public static AlphaHsvColorComponent alpha(final float value) {
        return AlphaHsvColorComponent.with(value);
    }

    /**
     * Package private to limit sub classing
     */
    HsvColorComponent(final float value) {
        super(value);
    }

    /**
     * Performs a saturated add returning a new {@link HsvColorComponent} if the value changed.
     */
    abstract public HsvColorComponent add(float value);

    /**
     * Would be setter that returns a {@link HsvColorComponent} with the new value.
     */
    @Override //
    abstract public HsvColorComponent setValue(float value);

    /**
     * Factory that creates a new {@link HsvColorComponent} of the same type with the given value.
     */
    abstract HsvColorComponent replace(float value);

    /**
     * Returns true if this is a {@link HueHsvColorComponent}
     */
    public final boolean isHue() {
        return this instanceof HueHsvColorComponent;
    }

    /**
     * Returns true if this is a {@link SaturationHsvColorComponent}
     */
    public final boolean isSaturation() {
        return this instanceof SaturationHsvColorComponent;
    }

    /**
     * Returns true if this is a {@link ValueHsvColorComponent}
     */
    public final boolean isValue() {
        return this instanceof ValueHsvColorComponent;
    }

    /**
     * Returns true if this is a {@link AlphaHsvColorComponent}
     */
    public final boolean isAlpha() {
        return this instanceof AlphaHsvColorComponent;
    }

    /**
     * Setter used to create a new {@link HsvColor} with this component replaced if different
     */
    abstract HsvColor setComponent(final HsvColor hsv);

    // Object...........................................................................................................

    @Override abstract boolean canBeEqual(Object other);
}
