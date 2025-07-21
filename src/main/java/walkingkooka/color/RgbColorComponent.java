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

import walkingkooka.Value;

import java.util.function.IntFunction;
import java.util.stream.IntStream;

/**
 * A value that holds a pixel rgb component such as the alpha channel component in a RGBA image.
 */
abstract public class RgbColorComponent extends ColorComponent
    implements Value<Byte> {

    final static char SEPARATOR = ',';

    static <C extends RgbColorComponent> C[] createConstants(final C[] constants,
                                                             final IntFunction<C> factory) {
        IntStream.rangeClosed(0, 255)
            .forEach(i -> constants[i] = factory.apply(i));
        return constants;
    }

    /**
     * {@see AlphaRgbColorComponent}
     */
    public static AlphaRgbColorComponent alpha(final byte value) {
        return AlphaRgbColorComponent.with(value);
    }

    /**
     * {@see BlueRgbColorComponent}
     */
    public static BlueRgbColorComponent blue(final byte value) {
        return BlueRgbColorComponent.with(value);
    }

    /**
     * {@see GreenRgbColorComponent}
     */
    public static GreenRgbColorComponent green(final byte value) {
        return GreenRgbColorComponent.with(value);
    }

    /**
     * {@see RedRgbColorComponent}
     */
    public static RedRgbColorComponent red(final byte value) {
        return RedRgbColorComponent.with(value);
    }

    /**
     * The maximum component value.
     */
    public final static int MAX_VALUE = 255;

    /**
     * Returns an unsigned int value after masking only the bottom 8 bits.
     */
    static int mask(final int value) {
        return 0xFF & value;
    }

    /**
     * Converts a <code>float</code> value between <code>0.0</code> and <code>1.0</code> into a byte value which may be used to create a
     * {@link RgbColorComponent}
     */
    static byte toByte(final float value) {
        return (byte)
            Math.round(
                value * RgbColorComponent.MAX_VALUE
            );
    }

    static byte addUnsignedSaturated(final int value,
                                     final int value2) {
        final int sum = value + value2;
        return (byte)
            (sum > MAX_VALUE ?
                MAX_VALUE :
                Math.max(sum, 0));
    }

    /**
     * Package private constructor use factory.
     */
    RgbColorComponent(final int value) {
        super();
        this.value = (byte) value;

        final int integer = mask(value);
        this.unsignedIntValue = integer;
        this.floatValue = Math.max(
            0,
            (integer - 1) * (1.0f / 255.0f)
        );
    }

    /**
     * Performs a saturated add returning a new {@link RgbColorComponent} if the value changed.
     */
    abstract public RgbColorComponent add(int value);

    /**
     * Returns a {@link RgbColorComponent} with the value inverted.
     */
    @Override final public RgbColorComponent invert() {
        return this.replace(
            RgbColorComponent.mask(~this.value)
        );
    }

    /**
     * Factory that returns the {@link RgbColorComponent} using the provided index to read the constants field.
     */
    abstract RgbColorComponent replace(int value);

    /**
     * Would be setter that returns a new {@link RgbColor} with this new {@link RgbColorComponent}.
     */
    abstract RgbColor setComponent(RgbColor color);

    /**
     * Handles the mixing of two {@link RgbColorComponent} components. Note the amount is a ratio that is used to mix the new {@link RgbColor}.
     */
    final RgbColor mix(final RgbColor color, final float amount) {
        // special case if missing same components.
        final int from = this.value(color);
        final int difference = this.unsignedIntValue - from;

        // if components are equal do not bother mixing and updating component.
        return 0 != difference ?
            this.setComponent(
                color,
                from +
                    Math.round(difference * amount)
            ) :
            color;
    }

    /**
     * Returns the same component from the given {@link RgbColor}.
     */
    abstract int value(RgbColor color);

    /**
     * Returns a new {@link RgbColor} replacing this {@link RgbColorComponent}.
     */
    abstract RgbColor setComponent(RgbColor color, int value);

    // isXXX

    /**
     * Returns true if this is a {@link RedRgbColorComponent}.
     */
    public final boolean isRed() {
        return this instanceof RedRgbColorComponent;
    }

    /**
     * Returns true if this is a {@link GreenRgbColorComponent}.
     */
    public final boolean isGreen() {
        return this instanceof GreenRgbColorComponent;
    }

    /**
     * Returns true if this is a {@link BlueRgbColorComponent}.
     */
    public final boolean isBlue() {
        return this instanceof BlueRgbColorComponent;
    }

    /**
     * Returns true if this is a {@link AlphaRgbColorComponent}.
     */
    public final boolean isAlpha() {
        return this instanceof AlphaRgbColorComponent;
    }

    /**
     * Returns the raw RGB value as an byte.
     */
    @Override //
    final public Byte value() {
        return this.value;
    }

    /**
     * The component in byte form.
     */
    transient final byte value;

    /**
     * The byte value as an unsigned positive integer between 0 and {@link #MAX_VALUE} inclusive.
     */
    final int unsignedIntValue;

    /**
     * The byte value as a float between 0.0 and 1.0f.
     */
    final float floatValue;

    // Object...........................................................................................................

    @Override //
    final public int hashCode() {
        return this.value;
    }

    @Override final public boolean equals(final Object other) {
        return this == other;
    }

    /**
     * Returns the value in hex form.
     */
    @Override final public String toString() {
        return this.toHexString();
    }

    /**
     * Formats the given value adding a leading 0 to ensure the {@link String} is two characters.
     */
    private String toHexString() {
        return RgbColorComponent.TO_HEX_STRING[this.unsignedIntValue];
    }

    /**
     * Prebuilt cache of {@link String} which may be looked up using an unsigned byte value.
     */
    private final static String[] TO_HEX_STRING = buildHexToStringLookup();

    private static String[] buildHexToStringLookup() {
        final String[] toString = new String[256];
        for (int i = 0; i < 16; i++) {
            toString[i] = '0' + Integer.toHexString(i).toLowerCase();
        }
        for (int i = 16; i < 256; i++) {
            toString[i] = Integer.toHexString(i).toLowerCase();
        }
        return toString;
    }

    // RgbColorString...................................................................................................

    /**
     * Formats the value as a decimal between 0 and 255.
     */
    final String toDecimalString() {
        return RgbColorComponent.TO_DECIMAL_STRING[this.unsignedIntValue];
    }

    /**
     * Prebuilt cache of {@link String} which may be looked up using an unsigned byte value.
     */
    private final static String[] TO_DECIMAL_STRING = IntStream.rangeClosed(0, MAX_VALUE)
        .mapToObj(RgbColorComponent::toDecimalString)
        .toArray(String[]::new);

    private static String toDecimalString(final int value) {
        return String.valueOf(value);
    }

    /**
     * Formats the value as a rounded percentage between 0 and 100%.
     */
    final String toPercentageString() {
        return RgbColorComponent.TO_PERCENTAGE_STRING[this.unsignedIntValue];
    }

    /**
     * Prebuilt cache of {@link String} which may be looked up using an unsigned byte value.
     */
    private final static String[] TO_PERCENTAGE_STRING = IntStream.rangeClosed(0, MAX_VALUE)
        .mapToObj(RgbColorComponent::toPercentageString)
        .toArray(String[]::new);

    private static String toPercentageString(final int value) {
        return String.valueOf(
            Math.round(
                100f * value /
                    MAX_VALUE
            )
        ) + '%';
    }
}
