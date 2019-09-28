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

import walkingkooka.ToStringBuilder;

import java.util.Objects;

/**
 * An {@link RgbColor} that is opaque with a constant alpha component.
 */
final class OpaqueRgbColor extends RgbColor {

    /**
     * Creates a new {@link RgbColor} with the provided components.
     */
    static OpaqueRgbColor withOpaque(final RedRgbColorComponent red,
                                     final GreenRgbColorComponent green,
                                     final BlueRgbColorComponent blue) {
        Objects.requireNonNull(red, "red");
        Objects.requireNonNull(green, "green");
        Objects.requireNonNull(blue, "blue");

        return OpaqueRgbColor.computeRgbAndCreate(red, green, blue);
    }

    /**
     * Computes the RGB from the given {@link RgbColorComponent components}.
     */
    static OpaqueRgbColor computeRgbAndCreate(final RedRgbColorComponent red, final GreenRgbColorComponent green,
                                              final BlueRgbColorComponent blue) {
        return OpaqueRgbColor.with(red, green, blue, //
                (red.unsignedIntValue << RgbColor.RED_SHIFT) | // red
                        (green.unsignedIntValue << RgbColor.GREEN_SHIFT) | // green
                        (blue.unsignedIntValue << RgbColor.BLUE_SHIFT)); // blue
    }

    /**
     * Factory that creates with out any parameter checking.
     */
    static OpaqueRgbColor with(final RedRgbColorComponent red,
                               final GreenRgbColorComponent green,
                               final BlueRgbColorComponent blue,
                               final int rgb) {
        return new OpaqueRgbColor(red, green, blue, rgb);
    }

    /**
     * Private constructor use factory.
     */
    private OpaqueRgbColor(final RedRgbColorComponent red,
                           final GreenRgbColorComponent green,
                           final BlueRgbColorComponent blue,
                           final int rgb) {
        super(red, green, blue);
        this.rgb = rgb;
        this.argb = OpaqueRgbColor.ALPHA | rgb;
    }

    /**
     * Always returns false.
     */
    @Override
    public boolean hasAlpha() {
        return false;
    }

    /**
     * Always returns an opaque alpha.
     */
    @Override
    public AlphaRgbColorComponent alpha() {
        return AlphaRgbColorComponent.OPAQUE;
    }

    /**
     * Factory called by the various setters that creates a new {@link OpaqueRgbColor} with the given components.
     */
    @Override
    RgbColor replace(final RedRgbColorComponent red,
                     final GreenRgbColorComponent green,
                     final BlueRgbColorComponent blue) {
        return OpaqueRgbColor.computeRgbAndCreate(red, green, blue);
    }

    /**
     * Returns an integer with the RGB value.
     */
    @Override
    final public int rgb() {
        return this.rgb;
    }

    private final int rgb;

    /**
     * Returns an integer holding the ARGB value.
     */
    @Override
    public int argb() {
        return this.argb;
    }

    private final int argb;

    /**
     * A pre-computed constant holding the constant alpha component used by {@link #argb()}.
     */
    private final static int ALPHA = AlphaRgbColorComponent.OPAQUE.value << 24;

    /**
     * Returns the RGB
     */
    @Override
    public int value() {
        return this.rgb();
    }

    @Override
    public java.awt.Color toAwtColor() {
        return new java.awt.Color(this.rgb());
    }

    // Object..........................................................................................................

    /**
     * Can only be equal to {@link RgbColor} without an alpha.
     */
    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof OpaqueRgbColor;
    }

    // UsesToStringBuilder

    @Override
    void buildColorComponentsToString(final ToStringBuilder builder) {
        this.addRedGreenBlueComponents(builder);
    }

    // RgbColorString................................................................................

    /**
     * Always rgb
     */
    @Override
    String rgbFunctionName() {
        return "rgb";
    }

    @Override
    void alphaComponentToString( final StringBuilder b,
                                 final RgbColorString format) {
        // no alpha component.
    }
}
