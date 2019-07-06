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
import walkingkooka.test.ParseStringTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.type.JavaVisibility;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class HsvTestCase<H extends Hsv> extends ColorHslOrHsvTestCase<Hsv> implements ParseStringTesting<H> {

    HsvTestCase() {
        super();
    }

    // constants

    final static HueHsvComponent HUE = HueHsvComponent.with(359);
    final static SaturationHsvComponent SATURATION = SaturationHsvComponent.with(0.5f);
    final static ValueHsvComponent VALUE = ValueHsvComponent.with(0.25f);

    // tests

    @Test
    public final void testSetNullComponentFails() {
        assertThrows(NullPointerException.class, () -> {
            Hsv.with(HUE, SATURATION, VALUE).set(null);
        });
    }

    @Test
    public final void testSetSameHue() {
        final Hsv hsl = this.createHsv();
        assertSame(hsl, hsl.set(HUE));
        assertSame(HUE, hsl.hue, "hue");
        assertSame(SATURATION, hsl.saturation, "saturation");
        assertSame(VALUE, hsl.value, "value");
        assertSame(this.alphaHsvComponent(), hsl.alpha(), "alpha");
    }

    @Test
    public final void testSetDifferentHue() {
        final HueHsvComponent different = HueHsvComponent.with(180);
        final Hsv hsl = this.createHsv().set(different);
        assertSame(different, hsl.hue, "hue");
        assertSame(SATURATION, hsl.saturation, "saturation");
        assertSame(VALUE, hsl.value, "value");
        assertSame(this.alphaHsvComponent(), hsl.alpha(), "alpha");
    }

    @Test
    public final void testSetSameSaturation() {
        final Hsv hsl = this.createHsv();
        assertSame(hsl, hsl.set(SATURATION));
        assertSame(HUE, hsl.hue, "hue");
        assertSame(SATURATION, hsl.saturation, "saturation");
        assertSame(VALUE, hsl.value, "value");
        assertSame(this.alphaHsvComponent(), hsl.alpha(), "alpha");
    }

    @Test
    public final void testSetDifferentSaturation() {
        final HsvComponent different = SaturationHsvComponent.with(0.99f);
        final Hsv hsl = this.createHsv().set(different);
        assertSame(HUE, hsl.hue, "hue");
        assertSame(different, hsl.saturation, "saturation");
        assertSame(VALUE, hsl.value, "value");
        assertSame(this.alphaHsvComponent(), hsl.alpha(), "alpha");
    }

    @Test
    public final void testSetSameLightness() {
        final Hsv hsl = this.createHsv();
        assertSame(hsl, hsl.set(VALUE));
        assertSame(HUE, hsl.hue, "hue");
        assertSame(SATURATION, hsl.saturation, "saturation");
        assertSame(VALUE, hsl.value, "value");
        assertSame(this.alphaHsvComponent(), hsl.alpha(), "alpha");
    }

    @Test
    public final void testSetDifferentLightness() {
        final HsvComponent different = ValueHsvComponent.with(0.5f);
        final Hsv hsl = this.createHsv().set(different);
        assertSame(HUE, hsl.hue, "hue");
        assertSame(SATURATION, hsl.saturation, "saturation");
        assertSame(different, hsl.value, "value");
        assertSame(this.alphaHsvComponent(), hsl.alpha(), "alpha");
    }

    @Test
    public final void testSetAlphaSame() {
        final Hsv hsl = this.createHsv();
        assertSame(hsl, hsl.set(hsl.alpha()));
    }

    @Test
    public final void testSetAlphaDifferent() {
        final HsvComponent different = AlphaHsvComponent.with(0.25f);
        final Hsv hsl = this.createHsv().set(different);
        assertSame(HUE, hsl.hue, "hue");
        assertSame(SATURATION, hsl.saturation, "saturation");
        assertSame(VALUE, hsl.value, "value");
        assertSame(different, hsl.alpha(), "alpha");
    }

    // toHsv http://web.forret.com/tools/color.asp
    // http://serennu.com/colour/hsltorgb.php

    @Test
    public final void testBlackToColor() {
        this.toColorAndCheck(0, 0, 0, 0);
    }

    @Test
    public final void testWhiteToColor() {
        this.toColorAndCheck(0, 0, 1.0f, 0xFFFFFF);
    }

    @Test
    public final void testGrayToColor() {
        this.toColorAndCheck(0, 0, 0.533f, 0x888888);
    }

    @Test
    public final void testRedToHsv() {
        this.toColorAndCheck(0f, 1.0f, 1.0f, 0xFF0000);
    }

    @Test
    public final void testReddishToHsv() {
        this.toColorAndCheck(352f, 0.99f, 0.5f, 0x800112);
    }

    @Test
    public final void testGreenToHsv() {
        this.toColorAndCheck(120f, 1.0f, 1.0f, 0x00FF00);
    }

    @Test
    public final void testGreenishToHsv() {
        this.toColorAndCheck(133f, 0.93f, 0.96f, 0x11f542);
    }

    @Test
    public final void testBlueToHsv() {
        this.toColorAndCheck(240f, 1.0f, 1.0f, 0x0000FF);
    }

    @Test
    public final void testBlueishToHsv() {
        this.toColorAndCheck(231f, 0.89f, 0.94f, 0x1a3af0);
    }

    @Test
    public final void testYellowToHsv() {
        this.toColorAndCheck(60f, 1.0f, 1.0f, 0xFFFF00);
    }

    @Test
    public final void testYellowishToHsv() {
        this.toColorAndCheck(52f, 0.99f, 0.5f, 0x806f01);
    }

    @Test
    public final void testPurpleToHsv() {
        this.toColorAndCheck(300f, 1.0f, 1.0f, 0xFF00FF);
    }

    @Test
    public final void testPurplishToHsv() {
        this.toColorAndCheck(309f, 0.99f, 0.53f, 0x870173);
    }

    @Test
    public final void testCyanToHsv() {
        this.toColorAndCheck(180f, 1.0f, 1.0f, 0x00FFFF);
    }

    @Test
    public final void testCyanishToHsv() {
        this.toColorAndCheck(189f, 1, 0.4f, 0x005766);
    }

    abstract void toColorAndCheck(final float hue,
                                  final float saturation,
                                  final float value,
                                  final int rgb);

    final boolean isEquals(final ColorComponent expected, final ColorComponent actual) {
        return Math.abs(expected.value - actual.value) < 5;
    }

    // HashCodeEqualsDefined ..................................................................................................

    @Test
    public final void testEqualsDifferentHue() {
        this.checkNotEquals(Hsv.with(HueHsvComponent.with(99), SATURATION, VALUE));
    }

    @Test
    public final void testEqualsDifferentSaturation() {
        this.checkNotEquals(Hsv.with(HUE, SaturationHsvComponent.with(0.99f), VALUE));
    }

    @Test
    public final void testEqualsDifferentLightness() {
        this.checkNotEquals(Hsv.with(HUE, SATURATION, ValueHsvComponent.with(0.99f)));
    }

    @Override final Hsv createColorHslOrHsv() {
        return this.createHsv();
    }

    final Hsv createHsv() {
        return this.createHsv(HUE, SATURATION, VALUE);
    }

    abstract H createHsv(final HueHsvComponent hue,
                         final SaturationHsvComponent saturation,
                         final ValueHsvComponent value);

    abstract AlphaHsvComponent alphaHsvComponent();

    // ClassTesting ...................................................................................................

    @Override
    public final Class<Hsv> type() {
        return Cast.to(this.hsvType());
    }

    abstract Class<H> hsvType();

    @Override
    public final JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }

    // HasJsonNodeTesting..............................................................................................

    @Override
    public final Hsv fromJsonNode(final JsonNode from) {
        return Hsv.fromJsonNodeHsv(from);
    }

    // ParseStringTesting .............................................................................................

    @Override
    public final RuntimeException parseFailedExpected(final RuntimeException expected) {
        return expected;
    }

    @Override
    public final Class<? extends RuntimeException> parseFailedExpected(final Class<? extends RuntimeException> expected) {
        return expected;
    }

    // SerializableTesting ............................................................................................

    @Override
    public final Hsv serializableInstance() {
        return this.createHsv(HueHsvComponent.with(180),
                SaturationHsvComponent.with(0.5f),
                ValueHsvComponent.with(0.5f));
    }

    @Override
    public final boolean serializableInstanceIsSingleton() {
        return false;
    }
}
