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
import walkingkooka.test.ParseStringTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class HslColorTestCase<H extends HslColor> extends ColorTestCase<HslColor> implements ParseStringTesting<H> {

    HslColorTestCase() {
        super();
    }

    // constants

    final static HueHslColorComponent HUE = HueHslColorComponent.with(359);
    final static SaturationHslColorComponent SATURATION = SaturationHslColorComponent.with(0.5f);
    final static LightnessHslColorComponent LIGHTNESS = LightnessHslColorComponent.with(0.25f);

    // tests

    @Test
    public final void testSetNullComponentFails() {
        assertThrows(NullPointerException.class, () -> HslColor.with(HUE, SATURATION, LIGHTNESS).set(null));
    }

    @Test
    public final void testSetSameHue() {
        final HslColor hsl = this.createHsl();
        assertSame(hsl, hsl.set(HUE));
        assertSame(HUE, hsl.hue, "hue");
        assertSame(SATURATION, hsl.saturation, "saturation");
        assertSame(LIGHTNESS, hsl.lightness, "lightness");
        assertSame(this.alphaHslComponent(), hsl.alpha(), "alpha");
    }

    @Test
    public final void testSetDifferentHue() {
        final HueHslColorComponent different = HueHslColorComponent.with(180);
        final HslColor hsl = this.createHsl().set(different);
        assertSame(different, hsl.hue, "hue");
        assertSame(SATURATION, hsl.saturation, "saturation");
        assertSame(LIGHTNESS, hsl.lightness, "lightness");
        assertSame(this.alphaHslComponent(), hsl.alpha(), "alpha");
    }

    @Test
    public final void testSetSameSaturation() {
        final HslColor hsl = this.createHsl();
        assertSame(hsl, hsl.set(SATURATION));
        assertSame(HUE, hsl.hue, "hue");
        assertSame(SATURATION, hsl.saturation, "saturation");
        assertSame(LIGHTNESS, hsl.lightness, "lightness");
        assertSame(this.alphaHslComponent(), hsl.alpha(), "alpha");
    }

    @Test
    public final void testSetDifferentSaturation() {
        final HslColorComponent different = SaturationHslColorComponent.with(0.99f);
        final HslColor hsl = this.createHsl().set(different);
        assertSame(HUE, hsl.hue, "hue");
        assertSame(different, hsl.saturation, "saturation");
        assertSame(LIGHTNESS, hsl.lightness, "lightness");
        assertSame(this.alphaHslComponent(), hsl.alpha(), "alpha");
    }

    @Test
    public final void testSetSameLightness() {
        final HslColor hsl = this.createHsl();
        assertSame(hsl, hsl.set(LIGHTNESS));
        assertSame(HUE, hsl.hue, "hue");
        assertSame(SATURATION, hsl.saturation, "saturation");
        assertSame(LIGHTNESS, hsl.lightness, "lightness");
        assertSame(this.alphaHslComponent(), hsl.alpha(), "alpha");
    }

    @Test
    public final void testSetDifferentLightness() {
        final HslColorComponent different = LightnessHslColorComponent.with(0.5f);
        final HslColor hsl = this.createHsl().set(different);
        assertSame(HUE, hsl.hue, "hue");
        assertSame(SATURATION, hsl.saturation, "saturation");
        assertSame(different, hsl.lightness, "lightness");
        assertSame(this.alphaHslComponent(), hsl.alpha(), "alpha");
    }

    @Test
    public final void testSetAlphaSame() {
        final HslColor hsl = this.createHsl();
        assertSame(hsl, hsl.set(hsl.alpha()));
    }

    @Test
    public final void testSetAlphaDifferent() {
        final HslColorComponent different = AlphaHslColorComponent.with(0.25f);
        final HslColor hsl = this.createHsl().set(different);
        assertSame(HUE, hsl.hue, "hue");
        assertSame(SATURATION, hsl.saturation, "saturation");
        assertSame(LIGHTNESS, hsl.lightness, "lightness");
        assertSame(different, hsl.alpha(), "alpha");
    }

    // toHsl http://web.forret.com/tools/color.asp
    // http://serennu.com/colour/hsltorgb.php

    @Test
    public final void testToRgbColorBlack() {
        this.toRgbColorAndCheck(0, 0, 0, 0);
    }

    @Test
    public final void testToRgbColorColorWhite() {
        this.toRgbColorAndCheck(0, 0, 1.0f, 0xFFFFFF);
    }

    @Test
    public final void testToRgbColorColorGray() {
        this.toRgbColorAndCheck(0, 0, 0.533f, 0x888888);
    }

    @Test
    public final void testToRgbColorRed() {
        this.toRgbColorAndCheck(0f, 1.0f, 1.0f, 0xFF0000);
    }

    @Test
    public final void testToRgbColorReddish() {
        this.toRgbColorAndCheck(352f, 0.996f, 0.502f, 0xFF0123);
    }

    @Test
    public final void testToRgbColorGreen() {
        this.toRgbColorAndCheck(120f, 1.0f, 1.0f, 0x00FF00);
    }

    @Test
    public final void testToRgbColorGreenish() {
        this.toRgbColorAndCheck(133f, 0.925f, 0.533f, 0x19F549);
    }

    @Test
    public final void testToRgbColorBlue() {
        this.toRgbColorAndCheck(240f, 1.0f, 1.0f, 0x0000FF);
    }

    @Test
    public final void testToRgbColorBlueish() {
        this.toRgbColorAndCheck(231f, 0.89f, 0.516f, 0x1838F2);
    }

    @Test
    public final void testToRgbColorYellow() {
        this.toRgbColorAndCheck(60f, 1.0f, 1.0f, 0xFFFF00);
    }

    @Test
    public final void testToRgbColorYellowish() {
        this.toRgbColorAndCheck(52f, 0.992f, 0.5f, 0xFEDC01);
    }

    @Test
    public final void testToRgbColorPurple() {
        this.toRgbColorAndCheck(300f, 1.0f, 1.0f, 0xFF00FF);
    }

    @Test
    public final void testToRgbColorPurplish() {
        this.toRgbColorAndCheck(309f, 0.99f, 0.533f, 0xFE12DC);
    }

    @Test
    public final void testToRgbColorCyan() {
        this.toRgbColorAndCheck(180f, 1.0f, 1.0f, 0x00FFFF);
    }

    @Test
    public final void testToRgbColorCyanish() {
        this.toRgbColorAndCheck(189f, 1, 0.435f, 0x00BCDE);
    }

    abstract void toRgbColorAndCheck(final float hue,
                                     final float saturation,
                                     final float value,
                                     final int rgb);

    final boolean isEquals(final RgbColorComponent expected, final RgbColorComponent actual) {
        return Math.abs(expected.value - actual.value) < 5;
    }

    @Test
    public final void testToWebColorName() {
        this.toWebNameAndCheck(
                this.createColor()
        );
    }

    // mix..............................................................................................................

    final void mixAndCheck(final String color,
                           final String other,
                           final float amount,
                           final String expected) {
        this.mixAndCheck(
                Color.parseHsl(color),
                Color.parse(other),
                amount,
                Color.parseHsl(expected)
        );
    }

    // HashCodeEqualsDefined ..................................................................................................

    @Test
    public final void testEqualsDifferentHue() {
        this.checkNotEquals(HslColor.with(HueHslColorComponent.with(99), SATURATION, LIGHTNESS));
    }

    @Test
    public final void testEqualsDifferentSaturation() {
        this.checkNotEquals(HslColor.with(HUE, SaturationHslColorComponent.with(0.99f), LIGHTNESS));
    }

    @Test
    public final void testEqualsDifferentLightness() {
        this.checkNotEquals(HslColor.with(HUE, SATURATION, LightnessHslColorComponent.with(0.99f)));
    }

    @Override
    final HslColor createColor() {
        return this.createHsl();
    }

    final HslColor createHsl() {
        return this.createHsl(HUE, SATURATION, LIGHTNESS);
    }

    abstract H createHsl(final HueHslColorComponent hue,
                         final SaturationHslColorComponent saturation,
                         final LightnessHslColorComponent lightness);

    abstract AlphaHslColorComponent alphaHslComponent();

    // ClassTesting ...................................................................................................

    @Override
    public final Class<HslColor> type() {
        return Cast.to(this.hslType());
    }

    abstract Class<H> hslType();

    @Override
    public final JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }

    // HasJsonNodeTesting..............................................................................................

    @Override
    public final HslColor unmarshall(final JsonNode from,
                                       final JsonNodeUnmarshallContext context) {
        return HslColor.unmarshallHsl(from, context);
    }
}
