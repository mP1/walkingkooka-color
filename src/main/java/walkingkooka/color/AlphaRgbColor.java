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

import javaemul.internal.annotations.GwtIncompatible;
import walkingkooka.ToStringBuilder;

/**
 * A {@link RgbColor} that includes an alpha property.
 */
final class AlphaRgbColor extends RgbColor {

    /**
     * Factory that creates a {@link AlphaRgbColor} with the given argb value.
     */
    static AlphaRgbColor createAlphaColorFromArgb(final int argb) {
        return new AlphaRgbColor(argb);
    }

    /**
     * Private constructor use factory.
     */
    private AlphaRgbColor(final int argb) {
        super(RedRgbColorComponent.with(RgbColor.shiftRight(argb, RgbColor.RED_SHIFT)), //
                GreenRgbColorComponent.with(RgbColor.shiftRight(argb, RgbColor.GREEN_SHIFT)), //
                BlueRgbColorComponent.with(RgbColor.shiftRight(argb, RgbColor.BLUE_SHIFT)));
        this.alpha = AlphaRgbColorComponent.with(RgbColor.shiftRight(argb, RgbColor.ALPHA_SHIFT));
        this.argb = argb;
    }

    /**
     * Factory that creates a {@link AlphaRgbColor}
     */
    static AlphaRgbColor with(final RedRgbColorComponent red,
                              final GreenRgbColorComponent green,
                              final BlueRgbColorComponent blue,
                              final AlphaRgbColorComponent alpha) {
        return new AlphaRgbColor(red, green, blue, alpha);
    }

    /**
     * Private constructor use factory. Note the argb is calculated and cached.
     */
    private AlphaRgbColor(final RedRgbColorComponent red,
                          final GreenRgbColorComponent green,
                          final BlueRgbColorComponent blue,
                          final AlphaRgbColorComponent alpha) {
        super(red, green, blue);
        this.alpha = alpha;
        this.argb = (alpha.unsignedIntValue << RgbColor.ALPHA_SHIFT) | //
                (red.unsignedIntValue << RgbColor.RED_SHIFT) | //
                (green.unsignedIntValue << RgbColor.GREEN_SHIFT) | //
                (blue.unsignedIntValue << RgbColor.BLUE_SHIFT);
    }

    /**
     * Always returns true.
     */
    @Override
    public boolean hasAlpha() {
        return true;
    }

    /**
     * Unconditionally creates a new a {@link AlphaRgbColor}
     */
    @Override
    RgbColor replace(final RedRgbColorComponent red,
                     final GreenRgbColorComponent green,
                     final BlueRgbColorComponent blue) {
        return new AlphaRgbColor(red, green, blue, this.alpha);
    }

    /**
     * Factory that creates a new {@link RgbColor} with the new {@link AlphaRgbColorComponent}.
     */
    @Override
    RgbColor setAlpha(final AlphaRgbColorComponent alpha) {
        return this.alpha.equals(alpha) ? this//
                : alpha == AlphaRgbColorComponent.OPAQUE ? //
                OpaqueRgbColor.computeRgbAndCreate(this.red, this.green, this.blue) : //
                new AlphaRgbColor(this.red, this.green, this.blue, alpha);
    }

    /**
     * Returns the actual alpha component
     */
    @Override
    public AlphaRgbColorComponent alpha() {
        return this.alpha;
    }

    private final AlphaRgbColorComponent alpha;

    /**
     * Returns the ARGB less the alpha component.
     */
    @Override
    public int rgb() {
        return AlphaRgbColor.MASK & this.argb;
    }

    private final static int MASK = 0x00FFFFFF;

    /**
     * Returns all the components into a single int as ARGB
     */
    @Override
    public int argb() {
        return this.argb;
    }

    private final int argb;

    /**
     * Returns the ARGB
     */
    @Override
    public int value() {
        return this.argb();
    }

    // AWT..............................................................................................................

    @GwtIncompatible
    @Override
    public java.awt.Color toAwtColor() {
        return new java.awt.Color(this.red.unsignedIntValue,
                this.green.unsignedIntValue,
                this.blue.unsignedIntValue,
                this.alpha.unsignedIntValue);
    }

    // toCss............................................................................................................

    @Override
    public String toCss() {
        return "rgba(" +
                this.red.unsignedIntValue +
                ", " +
                this.green.unsignedIntValue +
                ", " +
                this.blue.unsignedIntValue +
                ", " +
                this.alpha.floatValue +
                ')';
    }

    // Object..........................................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof AlphaRgbColor;
    }

    // UsesToStringBuilder

    @Override
    void buildColorComponentsToString(final ToStringBuilder builder) {
        this.addRedGreenBlueComponents(builder);
        builder.value(this.alpha);
    }

    // RgbColorString................................................................................

    /**
     * Always rgba
     */
    @Override
    String rgbFunctionName() {
        return "rgba";
    }

    @Override
    void alphaComponentToString(final StringBuilder b,
                                final RgbColorString format) {
        b.append(RgbColorComponent.SEPARATOR);
        b.append(format.componentToString(this.alpha));
    }
}
