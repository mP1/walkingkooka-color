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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.TypeNameTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallingTesting;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract public class RgbColorTestCase<C extends RgbColor> extends ColorTestCase<C>
        implements JsonNodeMarshallingTesting<C>,
        TypeNameTesting<C> {

    RgbColorTestCase() {
        super();
    }

    // constants

    final static RedRgbColorComponent RED = RgbColorComponent.red((byte) 1);
    final static GreenRgbColorComponent GREEN = RgbColorComponent.green((byte) 2);
    final static BlueRgbColorComponent BLUE = RgbColorComponent.blue((byte) 3);
    final static AlphaRgbColorComponent ALPHA = AlphaRgbColorComponent.with((byte) 4);

    final static float AMOUNT = 0.25f;
    final static float REVERSE_AMOUNT = 1.0f - AMOUNT;

    final static byte FROM_COMPONENT = (byte) 16; // starting with
    final static byte TO_COMPONENT = (byte) 32; // mixed with
    final static byte MIXED_COMPONENT = (byte) 20; // expected result

    final static byte FROM_COMPONENT2 = (byte) RgbColorComponent.MAX_VALUE - 16; // starting with
    final static byte TO_COMPONENT2 = (byte) RgbColorComponent.MAX_VALUE - 32; // mixed with
    final static byte MIXED_COMPONENT2 = (byte) RgbColorComponent.MAX_VALUE - 20; // expected result

    final static byte DIFFERENT = 0;
    final static float SMALL_AMOUNT = 1.0f / 512f;
    final static float LARGE_AMOUNT = 1 - SMALL_AMOUNT;

    // tests ..................................................................................

    abstract public void testHasAlpha();

    @Test
    public final void testSetNullFails() {
        assertThrows(NullPointerException.class, () -> this.createColor().set(null));
    }

    // red

    @Test
    public final void testSetSameRed() {
        final C color = this.createColor();
        assertSame(color, color.set(RED));
    }

    @Test
    public final void testSetDifferentRed() {
        final C first = this.createColor(RED, GREEN, BLUE);

        final RedRgbColorComponent different = RgbColorComponent.red((byte) 0xFF);
        final RgbColor color = first.set(different);

        assertSame(different, color.red(), "red");
        assertSame(GREEN, color.green(), "green");
        assertSame(BLUE, color.blue(), "blue");
        assertSame(first.alpha(), color.alpha(), "alpha");
    }

    // green
    @Test
    public final void testSetSameGreen() {
        final C color = this.createColor();
        assertSame(color, color.set(GREEN));
    }

    @Test
    public final void testSetDifferentGreen() {
        final C color = this.createColor(RED, GREEN, BLUE);

        final GreenRgbColorComponent different = RgbColorComponent.green((byte) 0xFF);
        this.check(color.set(different), RED, different, BLUE, color.alpha());
        this.check(color, RED, GREEN, BLUE, color.alpha());
    }

    // blue
    @Test
    public final void testSetSameBlue() {
        final C color = this.createColor();
        assertSame(color, color.set(BLUE));
    }

    @Test
    public final void testSetDifferentBlue() {
        final C color = this.createColor(RED, GREEN, BLUE);

        final BlueRgbColorComponent different = RgbColorComponent.blue((byte) 0xFF);

        this.check(color.set(different),
                RED,
                GREEN,
                different,
                color.alpha());
        this.check(color, RED, GREEN, BLUE, color.alpha());
    }

    // alpha

    @Test
    public final void testSetDifferentAlpha() {
        final C color = this.createColor(RED, GREEN, BLUE);

        final AlphaRgbColorComponent different = AlphaRgbColorComponent.with((byte) 0xFF);

        this.check(color.set(different),
                RED,
                GREEN,
                BLUE,
                different);
        this.check(color, RED, GREEN, BLUE, color.alpha());
    }

    @Test
    public final void testSetRedGreenBlue() {
        final byte zero = 0;
        final C color = this.createColor(RgbColorComponent.red(zero),
                RgbColorComponent.green(zero),
                RgbColorComponent.blue(zero));
        this.check(color.set(RED).set(BLUE).setGreen(GREEN),
                RED,
                GREEN,
                BLUE,
                color.alpha());
    }

    @Test
    public final void testSetRedGreenBlueAlpha() {
        final byte zero = 0;
        final C color = this.createColor(RgbColorComponent.red(zero), RgbColorComponent.green(zero),
                RgbColorComponent.blue(zero));
        this.check(color.set(RED).set(BLUE).setGreen(GREEN)
                        .setAlpha(ALPHA),
                RED,
                GREEN,
                BLUE,
                ALPHA);
    }

    private void check(final RgbColor color,
                       final RedRgbColorComponent red,
                       final GreenRgbColorComponent green,
                       final BlueRgbColorComponent blue,
                       final AlphaRgbColorComponent alpha) {
        assertSame(red, color.red(), "red");
        assertSame(green, color.green(), "green");
        assertSame(blue, color.blue(), "blue");
        assertSame(alpha, color.alpha(), "alpha");
    }

    // mix

    @Test
    public void testMixNullComponentFails() {
        assertThrows(NullPointerException.class, () -> this.createColor().mix(null, 1.0f));
    }

    @Test
    public void testMixInvalidAmountBelowZeroFails() {
        assertThrows(IllegalArgumentException.class, () -> this.createColor().mix(RED, -0.1f));
    }

    @Test
    public void testMixInvalidAmountAboveOneFails() {
        assertThrows(IllegalArgumentException.class, () -> this.createColor().mix(RED, +1.1f));
    }

    // mix red

    @Test
    public final void testMixSameRed() {
        final C color = this.createColor();
        this.mixAndCheckSame(color, color.red(), AMOUNT);
    }

    @Test
    public final void testMixSameRedVerySmallAmount() {
        final C color = this.createColor();
        this.mixAndCheckSame(color, color.red(), SMALL_AMOUNT);
    }

    @Test
    public final void testMixDifferentRedVeryLargeAmount() {
        final RedRgbColorComponent replacement = RgbColorComponent.red(DIFFERENT);
        this.mixAndCheck(this.createColor(), replacement, LARGE_AMOUNT, replacement);
    }

    @Test
    public final void testMixRed1() {
        this.mixAndCheck(this.createColor().set(RgbColorComponent.red(FROM_COMPONENT)), //
                RgbColorComponent.red(TO_COMPONENT),//
                AMOUNT, //
                RgbColorComponent.red(MIXED_COMPONENT));
    }

    @Test
    public final void testMixRed2() {
        this.mixAndCheck(this.createColor().set(RgbColorComponent.red(TO_COMPONENT)), //
                RgbColorComponent.red(FROM_COMPONENT),//
                REVERSE_AMOUNT, //
                RgbColorComponent.red(MIXED_COMPONENT));
    }

    @Test
    public final void testMixRed3() {
        this.mixAndCheck(this.createColor().set(RgbColorComponent.red(FROM_COMPONENT2)), //
                RgbColorComponent.red(TO_COMPONENT2),//
                AMOUNT, //
                RgbColorComponent.red(MIXED_COMPONENT2));
    }

    @Test
    public final void testMixRed4() {
        this.mixAndCheck(this.createColor().set(RgbColorComponent.red(TO_COMPONENT2)), //
                RgbColorComponent.red(FROM_COMPONENT2),//
                REVERSE_AMOUNT, //
                RgbColorComponent.red(MIXED_COMPONENT2));
    }

    // mix green

    @Test
    public final void testMixSameGreen() {
        final C color = this.createColor();
        this.mixAndCheckSame(color, color.green(), AMOUNT);
    }

    @Test
    public final void testMixSameGreenVeryLargeAmount() {
        final C color = this.createColor();
        this.mixAndCheckSame(color, color.green(), LARGE_AMOUNT);
    }

    @Test
    public final void testMixDifferentGreenVeryLargeAmount() {
        final GreenRgbColorComponent replacement = RgbColorComponent.green(DIFFERENT);
        this.mixAndCheck(replacement, LARGE_AMOUNT, replacement);
    }

    @Test
    public final void testMixGreen1() {
        this.mixAndCheck(this.createColor().set(RgbColorComponent.green(FROM_COMPONENT)), //
                RgbColorComponent.green(TO_COMPONENT),//
                AMOUNT, //
                RgbColorComponent.green(MIXED_COMPONENT));
    }

    @Test
    public final void testMixGreen2() {
        this.mixAndCheck(this.createColor().set(RgbColorComponent.green(TO_COMPONENT)), //
                RgbColorComponent.green(FROM_COMPONENT),//
                REVERSE_AMOUNT, //
                RgbColorComponent.green(MIXED_COMPONENT));
    }

    @Test
    public final void testMixGreen3() {
        this.mixAndCheck(this.createColor().set(RgbColorComponent.green(FROM_COMPONENT2)), //
                RgbColorComponent.green(TO_COMPONENT2),//
                AMOUNT, //
                RgbColorComponent.green(MIXED_COMPONENT2));
    }

    @Test
    public final void testMixGreen4() {
        this.mixAndCheck(this.createColor().set(RgbColorComponent.green(TO_COMPONENT2)), //
                RgbColorComponent.green(FROM_COMPONENT2),//
                REVERSE_AMOUNT, //
                RgbColorComponent.green(MIXED_COMPONENT2));
    }

    // mix blue

    @Test
    public final void testMixSameBlue() {
        final C color = this.createColor();
        this.mixAndCheckSame(color, color.blue(), AMOUNT);
    }

    @Test
    public final void testMixSameBlueVerySmallAmount() {
        final C color = this.createColor();
        this.mixAndCheckSame(color, color.blue(), SMALL_AMOUNT);
    }

    @Test
    public final void testMixDifferentBlueVerySmallAmount() {
        this.mixAndCheckSame(RgbColorComponent.blue(DIFFERENT), SMALL_AMOUNT);
    }

    @Test
    public final void testMixDifferentBlueVeryLargeAmount() {
        final BlueRgbColorComponent replacement = RgbColorComponent.blue(DIFFERENT);
        this.mixAndCheck(replacement, LARGE_AMOUNT, replacement);
    }

    @Test
    public final void testMixBlue1() {
        this.mixAndCheck(this.createColor().set(RgbColorComponent.blue(FROM_COMPONENT)), //
                RgbColorComponent.blue(TO_COMPONENT),//
                AMOUNT, //
                RgbColorComponent.blue(MIXED_COMPONENT));
    }

    @Test
    public final void testMixBlue2() {
        this.mixAndCheck(this.createColor().set(RgbColorComponent.blue(TO_COMPONENT)), //
                RgbColorComponent.blue(FROM_COMPONENT),//
                REVERSE_AMOUNT, //
                RgbColorComponent.blue(MIXED_COMPONENT));
    }

    @Test
    public final void testMixBlue3() {
        this.mixAndCheck(this.createColor().set(RgbColorComponent.blue(FROM_COMPONENT2)), //
                RgbColorComponent.blue(TO_COMPONENT2),//
                AMOUNT, //
                RgbColorComponent.blue(MIXED_COMPONENT2));
    }

    @Test
    public final void testMixBlue4() {
        this.mixAndCheck(this.createColor().set(RgbColorComponent.blue(TO_COMPONENT2)), //
                RgbColorComponent.blue(FROM_COMPONENT2),//
                REVERSE_AMOUNT, //
                RgbColorComponent.blue(MIXED_COMPONENT2));
    }

    // mix alpha

    @Test
    public final void testMixSameAlpha() {
        final C color = this.createColor();
        this.mixAndCheckSame(color, color.alpha(), AMOUNT);
    }

    @Test
    public final void testMixSameAlphaVerySmallAmount() {
        final C color = this.createColor();
        this.mixAndCheckSame(color, color.alpha(), SMALL_AMOUNT);
    }

    @Test
    public final void testMixDifferentAlphaVerySmallAmount() {
        this.mixAndCheckSame(AlphaRgbColorComponent.with(DIFFERENT), SMALL_AMOUNT);
    }

    @Test
    public final void testMixAlpha1() {
        this.mixAndCheck(this.createColor().set(AlphaRgbColorComponent.with(FROM_COMPONENT)), //
                AlphaRgbColorComponent.with(TO_COMPONENT),//
                AMOUNT, //
                AlphaRgbColorComponent.with(MIXED_COMPONENT));
    }

    @Test
    public final void testMixAlpha2() {
        this.mixAndCheck(this.createColor().set(AlphaRgbColorComponent.with(TO_COMPONENT)), //
                AlphaRgbColorComponent.with(FROM_COMPONENT),//
                REVERSE_AMOUNT, //
                AlphaRgbColorComponent.with(MIXED_COMPONENT));
    }

    @Test
    public final void testMixAlpha3() {
        this.mixAndCheck(this.createColor().set(AlphaRgbColorComponent.with(FROM_COMPONENT2)), //
                AlphaRgbColorComponent.with(TO_COMPONENT2),//
                AMOUNT, //
                AlphaRgbColorComponent.with(MIXED_COMPONENT2));
    }

    @Test
    public final void testMixAlpha4() {
        this.mixAndCheck(this.createColor().set(AlphaRgbColorComponent.with(TO_COMPONENT2)), //
                AlphaRgbColorComponent.with(FROM_COMPONENT2),//
                REVERSE_AMOUNT, //
                AlphaRgbColorComponent.with(MIXED_COMPONENT2));
    }

    // toHsv http://web.forret.com/tools/color.asp

    @Test
    public final void testBlackToHsv() {
        this.toHsvAndCheck(0x000000, 0f, 0f, 0f);
    }

    @Test
    public final void testWhiteToHsv() {
        this.toHsvAndCheck(0xFFFFFF, 0f, 0f, 1.0f);
    }

    @Test
    public final void testGrayToHsv() {
        this.toHsvAndCheck(0x888888, 0f, 0f, 0.533f);
    }

    @Test
    public final void testRedToHsv() {
        this.toHsvAndCheck(0xFF0000, 0f, 1.0f, 1.0f);
    }

    @Test
    public final void testReddishToHsv() {
        this.toHsvAndCheck(0xFF0123, 352f, 0.996f, 1.0f);
    }

    @Test
    public final void testGreenToHsv() {
        this.toHsvAndCheck(0x00FF00, 120f, 1.0f, 1.0f);
    }

    @Test
    public final void testGreenishToHsv() {
        this.toHsvAndCheck(0x12FE45, 133f, 0.929f, 0.996f);
    }

    @Test
    public final void testBlueToHsv() {
        this.toHsvAndCheck(0x0000FF, 240f, 1.0f, 1.0f);
    }

    @Test
    public final void testBlueishToHsv() {
        this.toHsvAndCheck(0x1234F5, 231f, 0.927f, 0.961f);
    }

    @Test
    public final void testYellowToHsv() {
        this.toHsvAndCheck(0xFFFF00, 60f, 1.0f, 1.0f);
    }

    @Test
    public final void testYellowishToHsv() {
        this.toHsvAndCheck(0xFEDC01, 52f, 0.996f, 0.996f);
    }

    @Test
    public final void testPurpleToHsv() {
        this.toHsvAndCheck(0xFF00FF, 300f, 1.0f, 1.0f);
    }

    @Test
    public final void testPurplishToHsv() {
        this.toHsvAndCheck(0xFE12DC, 309f, 0.929f, 0.996f);
    }

    @Test
    public final void testCyanToHsv() {
        this.toHsvAndCheck(0x00FFFF, 180f, 1.0f, 1.0f);
    }

    @Test
    public final void testCyanishToHsv() {
        this.toHsvAndCheck(0x00BCDE, 189f, 1.0f, 0.871f);
    }

    // toHsl http://web.forret.com/tools/color.asp

    @Test
    public final void testBlackToHsl() {
        this.toHslAndCheck(0x000000, 0f, 0f, 0f);
    }

    @Test
    public final void testWhiteToHsl() {
        this.toHslAndCheck(0xFFFFFF, 0f, 0f, 1.0f);
    }

    @Test
    public final void testGrayToHsl() {
        this.toHslAndCheck(0x888888, 0f, 0f, 0.533f);
    }

    @Test
    public final void testRedToHsl() {
        this.toHslAndCheck(0xFF0000, 0f, 1.0f, 0.5f);
    }

    @Test
    public final void testReddishToHsl() {
        this.toHslAndCheck(0xFF0123, 352f, 0.996f, 0.502f);
    }

    @Test
    public final void testGreenToHsl() {
        this.toHslAndCheck(0x00FF00, 120f, 1.0f, 0.5f);
    }

    @Test
    public final void testGreenishToHsl() {
        this.toHslAndCheck(0x12FE45, 133f, 0.925f, 0.533f);
    }

    @Test
    public final void testBlueToHsl() {
        this.toHslAndCheck(0x0000FF, 240f, 1.0f, 0.5f);
    }

    @Test
    public final void testBlueishToHsl() {
        this.toHslAndCheck(0x1234F5, 231f, 0.89f, 0.516f);
    }

    @Test
    public final void testYellowToHsl() {
        this.toHslAndCheck(0xFFFF00, 60f, 1.0f, 0.5f);
    }

    @Test
    public final void testYellowishToHsl() {
        this.toHslAndCheck(0xFEDC01, 52f, 0.992f, 0.5f);
    }

    @Test
    public final void testPurpleToHsl() {
        this.toHslAndCheck(0xFF00FF, 300f, 1.0f, 0.5f);
    }

    @Test
    public final void testPurplishToHsl() {
        this.toHslAndCheck(0xFE12DC, 309f, 0.925f, 0.533f);
    }

    @Test
    public final void testCyanToHsl() {
        this.toHslAndCheck(0x00FFFF, 180f, 1.0f, 0.5f);
    }

    @Test
    public final void testCyanishToHsl() {
        this.toHslAndCheck(0x00BCDE, 189f, 0.871f, 0.435f);
    }

    // webColorName.....................................................................................................

    @Test
    public final void testWebColorNameUnknown() {
        final byte component = 1;
        this.webNameAndCheck(this.createColor(RgbColorComponent.red(component),
                RgbColorComponent.green(component),
                RgbColorComponent.blue(component)),
                null);
    }

    // HasJsonNode.......................................................................................................

    @Test
    public final void testJsonNodeUnmarshallBooleanFails() {
        this.unmarshallFails(JsonNode.booleanNode(true));
    }

    @Test
    public final void testJsonNodeUnmarshallNumberFails() {
        this.unmarshallFails(JsonNode.number(123));
    }

    @Test
    public final void testJsonNodeUnmarshallArrayFails() {
        this.unmarshallFails(JsonNode.array());
    }

    @Test
    public final void testJsonNodeUnmarshallObjectFails() {
        this.unmarshallFails(JsonNode.object());
    }

    // helpers..........................................................................................................

    @Override
    final C createColor() {
        return this.createColor(RED, GREEN, BLUE);
    }

    abstract C createColor(final RedRgbColorComponent red,
                           final GreenRgbColorComponent green,
                           final BlueRgbColorComponent blue);

    final void mixAndCheckSame(final RgbColorComponent mixed, final float amount) {
        this.mixAndCheckSame(this.createColor(), mixed, amount);
    }

    final void mixAndCheckSame(final RgbColor rgb,
                               final RgbColorComponent mixed,
                               final float amount) {
        final RgbColor result = rgb.mix(mixed, amount);
        if (rgb != result) {
            assertSame(
                    rgb,
                    result,
                    "mixing " + rgb + " with " + toString(mixed) + " amount=" + amount
                            + " did not return the original rgb");
        }
    }

    final void mixAndCheck(final RgbColor rgb,
                           final RgbColorComponent mixed,
                           final float amount,
                           final RedRgbColorComponent red) {
        this.mixAndCheck(rgb, mixed, amount, red, rgb.green(), rgb.blue(), rgb.alpha());
    }

    final void mixAndCheck(final RgbColorComponent mixed,
                           final float amount,
                           final GreenRgbColorComponent green) {
        this.mixAndCheck(this.createColor(), mixed, amount, green);
    }

    final void mixAndCheck(final RgbColor rgb,
                           final RgbColorComponent mixed,
                           final float amount,
                           final GreenRgbColorComponent green) {
        this.mixAndCheck(rgb, mixed, amount, rgb.red(), green, rgb.blue(), rgb.alpha());
    }

    final void mixAndCheck(final RgbColorComponent mixed,
                           final float amount,
                           final BlueRgbColorComponent blue) {
        this.mixAndCheck(this.createColor(), mixed, amount, blue);
    }

    final void mixAndCheck(final RgbColor rgb,
                           final RgbColorComponent mixed,
                           final float amount,
                           final BlueRgbColorComponent blue) {
        this.mixAndCheck(rgb, mixed, amount, rgb.red(), rgb.green(), blue, rgb.alpha());
    }

    final void mixAndCheck(final RgbColor rgb,
                           final RgbColorComponent mixed,
                           final float amount,
                           final AlphaRgbColorComponent alpha) {
        this.mixAndCheck(rgb, mixed, amount, rgb.red(), rgb.green(), rgb.blue(), alpha);
    }

    final void mixAndCheck(final RgbColor rgb,
                           final RgbColorComponent mixed,
                           final float amount,
                           final RedRgbColorComponent red,
                           final GreenRgbColorComponent green,
                           final BlueRgbColorComponent blue,
                           final AlphaRgbColorComponent alpha) {
        final RgbColor mixedColor = rgb.mix(mixed, amount);
        assertNotSame(rgb, mixedColor, "mix should not return this but another RgbColor");

        checkComponent(red, mixedColor.red(), "red", rgb, mixed, amount);
        checkComponent(green, mixedColor.green(), "green", rgb, mixed, amount);
        checkComponent(blue, mixedColor.blue(), "blue", rgb, mixed, amount);
        checkComponent(alpha, mixedColor.alpha(), "alpha", rgb, mixed, amount);
    }

    private void checkComponent(final RgbColorComponent expected,
                                final RgbColorComponent actual,
                                final String label,
                                final RgbColor rgb,
                                final RgbColorComponent mixed, final float amount) {
        if (expected != actual) {
            this.checkNotEquals(
                    toString(expected),
                    toString(actual),
                    label + " incorrect, failed to mix " + rgb + " with " + toString(mixed) + " amount=" + amount);
        }
    }

    final void webNameAndCheck(final RgbColor color, final WebColorName webColorName) {
        this.checkEquals(Optional.ofNullable(webColorName),
                color.webColorName(),
                () -> color + " web rgb name");
    }

    static private String toString(final RgbColorComponent component) {
        return component.getClass().getSimpleName() + "=" + component;
    }

    // toHsl

    final void toHslAndCheck(final int rgb, final float hue, final float saturation, final float lightness) {
        this.toHslAndCheck(RgbColor.fromRgb0(rgb), hue, saturation, lightness);
    }

    final void toHslAndCheck(final RgbColor rgb, final float hue, final float saturation, final float lightness) {
        this.toHslAndCheck(rgb,
                HslColor.with(HslColorComponent.hue(hue), HslColorComponent.saturation(saturation), HslColorComponent.lightness(lightness)));
    }

    final void toHslAndCheck(final RgbColor rgb, final HslColor hsl) {
        final HslColor result = rgb.toHsl();
        if ((false == this.isEqual(hsl.hue(), result.hue(), 0.9f)) || //
                (false == this.isEqual(hsl.saturation(), result.saturation(), 0.13f)) || //
                (false == this.isEqual(hsl.lightness(), result.lightness(), 0.05f))) {
            this.checkNotEquals("failed to convert " + rgb + " to hsl", hsl.toString(), result.toString());
        }
    }

    private boolean isEqual(final HslColorComponent component,
                            final HslColorComponent otherComponent,
                            final float epislon) {
        return isAlmostEquals(component.value(), otherComponent.value(), epislon);
    }

    // toHsv

    final void toHsvAndCheck(final int rgb, final float hue, final float saturation, final float value) {
        this.toHsvAndCheck(RgbColor.fromRgb0(rgb), hue, saturation, value);
    }

    final void toHsvAndCheck(final RgbColor rgb, final float hue, final float saturation, final float value) {
        this.toHsvAndCheck(rgb,
                HsvColor.with(HsvColorComponent.hue(hue), HsvColorComponent.saturation(saturation), HsvColorComponent.value(value)));
    }

    final void toHsvAndCheck(final RgbColor rgb, final HsvColor hsv) {
        final HsvColor result = rgb.toHsv();
        if ((false == this.isEqual(hsv.hue(), result.hue(), 0.5f)) || //
                (false == this.isEqual(hsv.saturation(), result.saturation(), 0.01f)) || //
                (false == this.isEqual(hsv.value(), result.value(), 0.01f))) {
            this.checkNotEquals("failed to convert " + rgb + " to hsv", hsv.toString(), result.toString());
        }
    }

    private boolean isEqual(final HsvColorComponent component,
                            final HsvColorComponent otherComponent,
                            final float epison) {
        return isAlmostEquals(component.value(), otherComponent.value(), epison);
    }

    private boolean isAlmostEquals(final float value,
                                  final float test,
                                  final float epsilon) {
        return value + epsilon >= test && value - epsilon <= test;
    }

    // ClassTesting ...................................................................................................

    @Override
    public final JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }

    // TypeNameTesting ................................................................................................

    @Override
    public final String typeNamePrefix() {
        return "";
    }

    @Override
    public final String typeNameSuffix() {
        return RgbColor.class.getSimpleName();
    }

    // HasJsonNodeTesting...............................................................................................

    @Override
    public final C unmarshall(final JsonNode from,
                                final JsonNodeUnmarshallContext context) {
        return Cast.to(RgbColor.unmarshallRgb(from, context));
    }
}
