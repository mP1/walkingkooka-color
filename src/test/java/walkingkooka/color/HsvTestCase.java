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

public abstract class HsvTestCase<H extends HsvColor> extends ColorTestCase<HsvColor> implements ParseStringTesting<H> {

    HsvTestCase() {
        super();
    }

    // constants

    final static HueHsvColorComponent HUE = HueHsvColorComponent.with(359);
    final static SaturationHsvColorComponent SATURATION = SaturationHsvColorComponent.with(0.5f);
    final static ValueHsvColorComponent VALUE = ValueHsvColorComponent.with(0.25f);

    // tests

    @Test
    public final void testSetNullComponentFails() {
        assertThrows(NullPointerException.class, () -> HsvColor.with(HUE, SATURATION, VALUE).set(null));
    }

    @Test
    public final void testSetSameHue() {
        final HsvColor hsl = this.createHsv();
        assertSame(hsl, hsl.set(HUE));
        assertSame(HUE, hsl.hue, "hue");
        assertSame(SATURATION, hsl.saturation, "saturation");
        assertSame(VALUE, hsl.value, "value");
        assertSame(this.alphaHsvColorComponent(), hsl.alpha(), "alpha");
    }

    @Test
    public final void testSetDifferentHue() {
        final HueHsvColorComponent different = HueHsvColorComponent.with(180);
        final HsvColor hsl = this.createHsv().set(different);
        assertSame(different, hsl.hue, "hue");
        assertSame(SATURATION, hsl.saturation, "saturation");
        assertSame(VALUE, hsl.value, "value");
        assertSame(this.alphaHsvColorComponent(), hsl.alpha(), "alpha");
    }

    @Test
    public final void testSetSameSaturation() {
        final HsvColor hsl = this.createHsv();
        assertSame(hsl, hsl.set(SATURATION));
        assertSame(HUE, hsl.hue, "hue");
        assertSame(SATURATION, hsl.saturation, "saturation");
        assertSame(VALUE, hsl.value, "value");
        assertSame(this.alphaHsvColorComponent(), hsl.alpha(), "alpha");
    }

    @Test
    public final void testSetDifferentSaturation() {
        final HsvColorComponent different = SaturationHsvColorComponent.with(0.99f);
        final HsvColor hsl = this.createHsv().set(different);
        assertSame(HUE, hsl.hue, "hue");
        assertSame(different, hsl.saturation, "saturation");
        assertSame(VALUE, hsl.value, "value");
        assertSame(this.alphaHsvColorComponent(), hsl.alpha(), "alpha");
    }

    @Test
    public final void testSetSameLightness() {
        final HsvColor hsl = this.createHsv();
        assertSame(hsl, hsl.set(VALUE));
        assertSame(HUE, hsl.hue, "hue");
        assertSame(SATURATION, hsl.saturation, "saturation");
        assertSame(VALUE, hsl.value, "value");
        assertSame(this.alphaHsvColorComponent(), hsl.alpha(), "alpha");
    }

    @Test
    public final void testSetDifferentLightness() {
        final HsvColorComponent different = ValueHsvColorComponent.with(0.5f);
        final HsvColor hsl = this.createHsv().set(different);
        assertSame(HUE, hsl.hue, "hue");
        assertSame(SATURATION, hsl.saturation, "saturation");
        assertSame(different, hsl.value, "value");
        assertSame(this.alphaHsvColorComponent(), hsl.alpha(), "alpha");
    }

    @Test
    public final void testSetAlphaSame() {
        final HsvColor hsl = this.createHsv();
        assertSame(hsl, hsl.set(hsl.alpha()));
    }

    @Test
    public final void testSetAlphaDifferent() {
        final HsvColorComponent different = AlphaHsvColorComponent.with(0.25f);
        final HsvColor hsl = this.createHsv().set(different);
        assertSame(HUE, hsl.hue, "hue");
        assertSame(SATURATION, hsl.saturation, "saturation");
        assertSame(VALUE, hsl.value, "value");
        assertSame(different, hsl.alpha(), "alpha");
    }

    // toHsv http://web.forret.com/tools/color.asp
    // http://serennu.com/colour/hsltorgb.php

    @Test
    public final void testToRgbBlack() {
        this.toRgbColorAndCheck(0, 0, 0, 0);
    }

    @Test
    public final void testToRgbWhite() {
        this.toRgbColorAndCheck(0, 0, 1.0f, 0xFFFFFF);
    }

    @Test
    public final void testToRgbGray() {
        this.toRgbColorAndCheck(0, 0, 0.533f, 0x888888);
    }

    @Test
    public final void testToRgbRed() {
        this.toRgbColorAndCheck(0f, 1.0f, 1.0f, 0xFF0000);
    }

    @Test
    public final void testToRgbReddish() {
        this.toRgbColorAndCheck(352f, 0.99f, 0.5f, 0x800112);
    }

    @Test
    public final void testToRgbGreen() {
        this.toRgbColorAndCheck(120f, 1.0f, 1.0f, 0x00FF00);
    }

    @Test
    public final void testToRgbGreenish() {
        this.toRgbColorAndCheck(133f, 0.93f, 0.96f, 0x11f542);
    }

    @Test
    public final void testToRgbBlue() {
        this.toRgbColorAndCheck(240f, 1.0f, 1.0f, 0x0000FF);
    }

    @Test
    public final void testToRgbBlueish() {
        this.toRgbColorAndCheck(231f, 0.89f, 0.94f, 0x1a3af0);
    }

    @Test
    public final void testToRgbYellow() {
        this.toRgbColorAndCheck(60f, 1.0f, 1.0f, 0xFFFF00);
    }

    @Test
    public final void testToRgbYellowish() {
        this.toRgbColorAndCheck(52f, 0.99f, 0.5f, 0x806f01);
    }

    @Test
    public final void testToRgbPurple() {
        this.toRgbColorAndCheck(300f, 1.0f, 1.0f, 0xFF00FF);
    }

    @Test
    public final void testToRgbPurplish() {
        this.toRgbColorAndCheck(309f, 0.99f, 0.53f, 0x870173);
    }

    @Test
    public final void testToRgbCyan() {
        this.toRgbColorAndCheck(180f, 1.0f, 1.0f, 0x00FFFF);
    }

    @Test
    public final void testToRgbCyanish() {
        this.toRgbColorAndCheck(189f, 1, 0.4f, 0x005766);
    }

    abstract void toRgbColorAndCheck(final float hue,
                                     final float saturation,
                                     final float value,
                                     final int rgb);

    final boolean isEquals(final RgbColorComponent expected, final RgbColorComponent actual) {
        return Math.abs(expected.value - actual.value) < 5;
    }

    // toCss............................................................................................................

    @Test
    public void testToCssFails() {
        assertThrows(
                UnsupportedOperationException.class,
                () -> this.createHsv().toCss()
        );
    }

    // HashCodeEqualsDefined ..................................................................................................

    @Test
    public final void testEqualsDifferentHue() {
        this.checkNotEquals(HsvColor.with(HueHsvColorComponent.with(99), SATURATION, VALUE));
    }

    @Test
    public final void testEqualsDifferentSaturation() {
        this.checkNotEquals(HsvColor.with(HUE, SaturationHsvColorComponent.with(0.99f), VALUE));
    }

    @Test
    public final void testEqualsDifferentLightness() {
        this.checkNotEquals(HsvColor.with(HUE, SATURATION, ValueHsvColorComponent.with(0.99f)));
    }

    @Override
    final HsvColor createColor() {
        return this.createHsv();
    }

    final HsvColor createHsv() {
        return this.createHsv(HUE, SATURATION, VALUE);
    }

    abstract H createHsv(final HueHsvColorComponent hue,
                         final SaturationHsvColorComponent saturation,
                         final ValueHsvColorComponent value);

    abstract AlphaHsvColorComponent alphaHsvColorComponent();

    // ClassTesting ...................................................................................................

    @Override
    public final Class<HsvColor> type() {
        return Cast.to(this.hsvType());
    }

    abstract Class<H> hsvType();

    @Override
    public final JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }

    // JsonNodeMapTesting...............................................................................................

    @Override
    public final HsvColor unmarshall(final JsonNode from,
                                       final JsonNodeUnmarshallContext context) {
        return HsvColor.unmarshallHsv(from, context);
    }
}
