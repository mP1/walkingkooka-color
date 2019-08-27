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
import walkingkooka.test.ParseStringTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.map.FromJsonNodeContext;
import walkingkooka.type.JavaVisibility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class HslColorTest extends ColorTestCase<HslColor> implements ParseStringTesting<HslColor> {

    // constants

    private final static HueHslColorComponent HUE = HueHslColorComponent.with(359);
    private final static SaturationHslColorComponent SATURATION = SaturationHslColorComponent.with(0.25f);
    private final static LightnessHslColorComponent LIGHTNESS = LightnessHslColorComponent.with(0.5f);

    // tests

    @Test
    public void testWithNullHueFails() {
        assertThrows(NullPointerException.class, () -> {
            HslColor.with(null, SATURATION, LIGHTNESS);
        });
    }

    @Test
    public void testWithNullSaturationFails() {
        assertThrows(NullPointerException.class, () -> {
            HslColor.with(HUE, null, LIGHTNESS);
        });
    }

    @Test
    public void testWithNullValueFails() {
        assertThrows(NullPointerException.class, () -> {
            HslColor.with(HUE, SATURATION, null);
        });
    }

    @Test
    public void testWith() {
        this.check(HslColor.with(HUE, SATURATION, LIGHTNESS), HUE, SATURATION, LIGHTNESS);
    }

    @Test
    public void testSetNullComponentFails() {
        assertThrows(NullPointerException.class, () -> {
            HslColor.with(HUE, SATURATION, LIGHTNESS).set(null);
        });
    }

    @Test
    public void testSetSameHue() {
        final HslColor hsl = HslColor.with(HUE, SATURATION, LIGHTNESS);

        this.check(HslColor.with(HUE, SATURATION, LIGHTNESS).set(HUE), HUE, SATURATION, LIGHTNESS);
        this.check(hsl, HUE, SATURATION, LIGHTNESS);
    }

    @Test
    public void testSetDifferentHue() {
        final HueHslColorComponent different = HueHslColorComponent.with(180);
        final HslColor hsl = HslColor.with(HUE, SATURATION, LIGHTNESS);

        this.check(HslColor.with(HUE, SATURATION, LIGHTNESS).set(different), different, SATURATION, LIGHTNESS);
        this.check(hsl, HUE, SATURATION, LIGHTNESS);
    }

    @Test
    public void testSetSameSaturation() {
        final HslColor hsl = HslColor.with(HUE, SATURATION, LIGHTNESS);
        assertSame(hsl, hsl.set(SATURATION));

        this.check(HslColor.with(HUE, SATURATION, LIGHTNESS).set(LIGHTNESS), HUE, SATURATION, LIGHTNESS);
        this.check(hsl, HUE, SATURATION, LIGHTNESS);
    }

    @Test
    public void testSetDifferentSaturation() {
        final SaturationHslColorComponent different = SaturationHslColorComponent.with(0.5f);
        final HslColor hsl = HslColor.with(HUE, SATURATION, LIGHTNESS);

        this.check(HslColor.with(HUE, SATURATION, LIGHTNESS).set(different), HUE, different, LIGHTNESS);
        this.check(hsl, HUE, SATURATION, LIGHTNESS);
    }

    @Test
    public void testSetSameValue() {
        final HslColor hsl = HslColor.with(HUE, SATURATION, LIGHTNESS);
        this.check(HslColor.with(HUE, SATURATION, LIGHTNESS).set(LIGHTNESS), HUE, SATURATION, LIGHTNESS);
        this.check(hsl, HUE, SATURATION, LIGHTNESS);
    }

    @Test
    public void testSetDifferentValue() {
        final LightnessHslColorComponent different = LightnessHslColorComponent.with(0.99f);
        this.check(HslColor.with(HUE, SATURATION, LIGHTNESS).set(different), HUE, SATURATION, different);
    }

    private void check(final HslColor hsl,
                       final HueHslColorComponent hue,
                       final SaturationHslColorComponent saturation,
                       final LightnessHslColorComponent value) {
        assertSame(hue, hsl.hue, "hue");
        assertSame(saturation, hsl.saturation, "saturation");
        assertSame(value, hsl.lightness, "value");
    }

    @Test
    public void testBlackToColor() {
        this.toColorAndCheck(0, 0, 0, 0);
    }

    @Test
    public void testWhiteToColor() {
        this.toColorAndCheck(0, 0, 1.0f, 0xFFFFFF);
    }

    @Test
    public void testGrayToColor() {
        this.toColorAndCheck(0, 0, 0.533f, 0x888888);
    }

    @Test
    public void testRedToHsv() {
        this.toColorAndCheck(0f, 1.0f, 1.0f, 0xFF0000);
    }

    @Test
    public void testReddishToHsv() {
        this.toColorAndCheck(352f, 0.99f, 1.0f, 0xffffff);
    }

    @Test
    public void testGreenToHsv() {
        this.toColorAndCheck(120f, 1.0f, 1.0f, 0x00FF00);
    }

    @Test
    public void testGreenishToHsv() {
        this.toColorAndCheck(133f, 0.93f, 0.99f, 0xfafffb);
    }

    @Test
    public void testBlueToHsv() {
        this.toColorAndCheck(240f, 1.0f, 1.0f, 0x0000FF);
    }

    @Test
    public void testBlueishToHsv() {
        this.toColorAndCheck(231f, 0.92f, 0.96f, 0xebeefe);
    }

    @Test
    public void testYellowToHsv() {
        this.toColorAndCheck(60f, 1.0f, 1.0f, 0xFFFF00);
    }

    @Test
    public void testYellowishToHsv() {
        this.toColorAndCheck(52f, 0.99f, 0.99f, 0xfffefa);
    }

    @Test
    public void testPurpleToHsv() {
        this.toColorAndCheck(300f, 1.0f, 1.0f, 0xFF00FF);
    }

    @Test
    public void testPurplishToHsv() {
        this.toColorAndCheck(309f, 0.92f, 0.98f, 0xfff5fd);
    }

    @Test
    public void testCyanToHsv() {
        this.toColorAndCheck(180f, 1.0f, 1.0f, 0x00FFFF);
    }

    @Test
    public void testCyanishToHsv() {
        this.toColorAndCheck(189f, 1.0f, 0.87f, 0xbdf5ff);
    }

    private void toColorAndCheck(final float hue, final float saturation, final float value, final int rgb) {
        final HslColor hsl = HslColor.with(HslColorComponent.hue(hue), HslColorComponent.saturation(saturation), HslColorComponent.lightness(value));
        final RgbColor expected = RgbColor.fromRgb0(rgb);
        final RgbColor actual = hsl.toRgb();
        if ((false == this.isEquals(expected.red, actual.red)) || (false == this.isEquals(expected.green, actual.green))
                || (false == this.isEquals(expected.blue, actual.blue))) {
            assertEquals(expected, actual, () -> "failed to convert " + hsl + " to a RgbColor");
        }
    }

    private boolean isEquals(final RgbColorComponent expected, final RgbColorComponent actual) {
        return Math.abs(expected.value - actual.value) < 3;
    }

    @Test
    public void testEqualsDifferentHue() {
        this.checkNotEquals(HslColor.with(HueHslColorComponent.with(99), SATURATION, LIGHTNESS));
    }

    @Test
    public void testEqualsDifferentSaturation() {
        this.checkNotEquals(HslColor.with(HUE, SaturationHslColorComponent.with(0.99f), LIGHTNESS));
    }

    @Test
    public void testEqualsDifferentValue() {
        this.checkNotEquals(HslColor.with(HUE, SATURATION, LightnessHslColorComponent.with(0.99f)));
    }

    @Test
    public void testParse() {
        this.parseStringAndCheck("hsl(359,100%,50%)",
                HslColor.with(HslColorComponent.hue(359),
                        HslColorComponent.saturation(1.0f),
                        HslColorComponent.lightness(0.5f)));
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(HslColor.with(HUE, SATURATION, LIGHTNESS),
                "hsl(359,25%,50%)");
    }

    @Test
    public void testToStringZeroes() {
        this.toStringAndCheck(HslColor.with(HslColorComponent.hue(0),
                HslColorComponent.saturation(0),
                HslColorComponent.lightness(0)),
                "hsl(0,0%,0%)");
    }

    @Override
    HslColor createColor() {
        return HslColor.with(HUE, SATURATION, LIGHTNESS);
    }

    // ClassTesting ...................................................................................................

    @Override
    public Class<HslColor> type() {
        return HslColor.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }

    // JsonNodeMapTesting...............................................................................................

    @Override
    public HslColor fromJsonNode(final JsonNode from,
                                 final FromJsonNodeContext context) {
        return HslColor.fromJsonNodeHsl(from, context);
    }

    // ParseStringTesting .............................................................................................

    @Override
    public HslColor parseString(final String text) {
        return HslColor.parseHsl(text);
    }
}
