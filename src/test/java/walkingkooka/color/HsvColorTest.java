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
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.test.ParseStringTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class HsvColorTest extends ColorTestCase<HsvColor> implements ParseStringTesting<HsvColor> {

    // constants

    private final static HueHsvColorComponent HUE = HueHsvColorComponent.with(359);
    private final static SaturationHsvColorComponent SATURATION = SaturationHsvColorComponent.with(0.25f);
    private final static ValueHsvColorComponent VALUE = ValueHsvColorComponent.with(0.5f);

    // tests

    @Test
    public void testWithNullHueFails() {
        assertThrows(NullPointerException.class, () -> HsvColor.with(null, SATURATION, VALUE));
    }

    @Test
    public void testWithNullSaturationFails() {
        assertThrows(NullPointerException.class, () -> HsvColor.with(HUE, null, VALUE));
    }

    @Test
    public void testWithNullValueFails() {
        assertThrows(NullPointerException.class, () -> HsvColor.with(HUE, SATURATION, null));
    }

    @Test
    public void testWith() {
        this.check(HsvColor.with(HUE, SATURATION, VALUE), HUE, SATURATION, VALUE);
    }

    @Test
    public void testSetNullComponentFails() {
        assertThrows(NullPointerException.class, () -> HsvColor.with(HUE, SATURATION, VALUE).set(null));
    }

    @Test
    public void testSetSameHue() {
        final HsvColor hsv = HsvColor.with(HUE, SATURATION, VALUE);

        this.check(HsvColor.with(HUE, SATURATION, VALUE).set(HUE), HUE, SATURATION, VALUE);
        this.check(hsv, HUE, SATURATION, VALUE);
    }

    @Test
    public void testSetDifferentHue() {
        final HueHsvColorComponent different = HueHsvColorComponent.with(180);
        final HsvColor hsv = HsvColor.with(HUE, SATURATION, VALUE);

        this.check(HsvColor.with(HUE, SATURATION, VALUE).set(different), different, SATURATION, VALUE);
        this.check(hsv, HUE, SATURATION, VALUE);
    }

    @Test
    public void testSetSameSaturation() {
        final HsvColor hsv = HsvColor.with(HUE, SATURATION, VALUE);
        assertSame(hsv, hsv.set(SATURATION));

        this.check(HsvColor.with(HUE, SATURATION, VALUE).set(VALUE), HUE, SATURATION, VALUE);
        this.check(hsv, HUE, SATURATION, VALUE);
    }

    @Test
    public void testSetDifferentSaturation() {
        final SaturationHsvColorComponent different = SaturationHsvColorComponent.with(0.5f);
        final HsvColor hsv = HsvColor.with(HUE, SATURATION, VALUE);

        this.check(HsvColor.with(HUE, SATURATION, VALUE).set(different), HUE, different, VALUE);
        this.check(hsv, HUE, SATURATION, VALUE);
    }

    @Test
    public void testSetSameValue() {
        final HsvColor hsv = HsvColor.with(HUE, SATURATION, VALUE);
        this.check(HsvColor.with(HUE, SATURATION, VALUE).set(VALUE), HUE, SATURATION, VALUE);
        this.check(hsv, HUE, SATURATION, VALUE);
    }

    @Test
    public void testSetDifferentValue() {
        final ValueHsvColorComponent different = ValueHsvColorComponent.with(0.99f);
        this.check(HsvColor.with(HUE, SATURATION, VALUE).set(different), HUE, SATURATION, different);
    }

    private void check(final HsvColor hsv,
                       final HueHsvColorComponent hue,
                       final SaturationHsvColorComponent saturation,
                       final ValueHsvColorComponent value) {
        assertSame(hue, hsv.hue, "hue");
        assertSame(saturation, hsv.saturation, "saturation");
        assertSame(value, hsv.value, "value");
    }

    @Test
    public void testToRgbBlack() {
        this.toRgbColorAndCheck(0, 0, 0, 0);
    }

    @Test
    public void testToRgbWhite() {
        this.toRgbColorAndCheck(0, 0, 1.0f, 0xFFFFFF);
    }

    @Test
    public void testToRgbGray() {
        this.toRgbColorAndCheck(0, 0, 0.533f, 0x888888);
    }

    @Test
    public void testToRgbRed() {
        this.toRgbColorAndCheck(0f, 1.0f, 1.0f, 0xFF0000);
    }

    @Test
    public void testToRgbReddish() {
        this.toRgbColorAndCheck(352f, 0.996f, 1.0f, 0xFF0123);
    }

    @Test
    public void testToRgbGreen() {
        this.toRgbColorAndCheck(120f, 1.0f, 1.0f, 0x00FF00);
    }

    @Test
    public void testToRgbGreenish() {
        this.toRgbColorAndCheck(133f, 0.929f, 0.996f, 0x12FE45);
    }

    @Test
    public void testToRgbBlue() {
        this.toRgbColorAndCheck(240f, 1.0f, 1.0f, 0x0000FF);
    }

    @Test
    public void testToRgbBlueish() {
        this.toRgbColorAndCheck(231f, 0.927f, 0.961f, 0x1234F5);
    }

    @Test
    public void testToRgbYellow() {
        this.toRgbColorAndCheck(60f, 1.0f, 1.0f, 0xFFFF00);
    }

    @Test
    public void testToRgbYellowish() {
        this.toRgbColorAndCheck(52f, 0.996f, 0.996f, 0xFEDC01);
    }

    @Test
    public void testToRgbPurple() {
        this.toRgbColorAndCheck(300f, 1.0f, 1.0f, 0xFF00FF);
    }

    @Test
    public void testToRgbPurplish() {
        this.toRgbColorAndCheck(309f, 0.929f, 0.996f, 0xFE12DC);
    }

    @Test
    public void testToRgbCyan() {
        this.toRgbColorAndCheck(180f, 1.0f, 1.0f, 0x00FFFF);
    }

    @Test
    public void testToRgbCyanish() {
        this.toRgbColorAndCheck(189f, 1.0f, 0.871f, 0x00BCDE);
    }

    private void toRgbColorAndCheck(final float hue,
                                    final float saturation,
                                    final float value,
                                    final int rgb) {
        final HsvColor hsv = HsvColor.with(HsvColorComponent.hue(hue), HsvColorComponent.saturation(saturation), HsvColorComponent.value(value));
        final RgbColor expected = RgbColor.fromRgb0(rgb);
        final RgbColor actual = hsv.toRgb();
        if ((false == this.isEquals(expected.red, actual.red)) || (false == this.isEquals(expected.green, actual.green))
            || (false == this.isEquals(expected.blue, actual.blue))) {
            this.checkEquals(expected, actual, () -> "failed to convert " + hsv + " to a RgbColor");
        }
    }

    private boolean isEquals(final RgbColorComponent expected, final RgbColorComponent actual) {
        return Math.abs(expected.value - actual.value) < 3;
    }

    @Test
    public void testEqualsDifferentHue() {
        this.checkNotEquals(HsvColor.with(HueHsvColorComponent.with(99), SATURATION, VALUE));
    }

    @Test
    public void testEqualsDifferentSaturation() {
        this.checkNotEquals(HsvColor.with(HUE, SaturationHsvColorComponent.with(0.99f), VALUE));
    }

    @Test
    public void testEqualsDifferentValue() {
        this.checkNotEquals(HsvColor.with(HUE, SATURATION, ValueHsvColorComponent.with(0.99f)));
    }

    @Test
    public void testParse() {
        this.parseStringAndCheck("hsv(359,100%,50%)",
            HsvColor.with(HsvColorComponent.hue(359),
                HsvColorComponent.saturation(1.0f),
                HsvColorComponent.value(0.5f)));
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(HsvColor.with(HUE, SATURATION, VALUE),
            "hsv(359,25%,50%)");
    }

    @Test
    public void testToStringZeroes() {
        this.toStringAndCheck(HsvColor.with(HsvColorComponent.hue(0),
                HsvColorComponent.saturation(0),
                HsvColorComponent.value(0)),
            "hsv(0,0%,0%)");
    }

    @Override
    HsvColor createColor() {
        return HsvColor.with(HUE, SATURATION, VALUE);
    }

    // ClassTesting ...................................................................................................

    @Override
    public Class<HsvColor> type() {
        return HsvColor.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }

    // JsonNodeMapTesting...............................................................................................

    @Override
    public HsvColor unmarshall(final JsonNode from,
                               final JsonNodeUnmarshallContext context) {
        return HsvColor.unmarshallHsv(from, context);
    }

    // ParseStringTesting .............................................................................................

    @Override
    public HsvColor parseString(final String text) {
        return HsvColor.parseHsv(text);
    }
}
