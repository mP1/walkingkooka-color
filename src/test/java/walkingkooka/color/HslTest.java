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
import walkingkooka.type.JavaVisibility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class HslTest extends ColorHslOrHsvTestCase<Hsl> implements ParseStringTesting<Hsl> {

    // constants

    private final static HueHslComponent HUE = HueHslComponent.with(359);
    private final static SaturationHslComponent SATURATION = SaturationHslComponent.with(0.25f);
    private final static LightnessHslComponent LIGHTNESS = LightnessHslComponent.with(0.5f);

    // tests

    @Test
    public void testWithNullHueFails() {
        assertThrows(NullPointerException.class, () -> {
            Hsl.with(null, SATURATION, LIGHTNESS);
        });
    }

    @Test
    public void testWithNullSaturationFails() {
        assertThrows(NullPointerException.class, () -> {
            Hsl.with(HUE, null, LIGHTNESS);
        });
    }

    @Test
    public void testWithNullValueFails() {
        assertThrows(NullPointerException.class, () -> {
            Hsl.with(HUE, SATURATION, null);
        });
    }

    @Test
    public void testWith() {
        this.check(Hsl.with(HUE, SATURATION, LIGHTNESS), HUE, SATURATION, LIGHTNESS);
    }

    @Test
    public void testSetNullComponentFails() {
        assertThrows(NullPointerException.class, () -> {
            Hsl.with(HUE, SATURATION, LIGHTNESS).set(null);
        });
    }

    @Test
    public void testSetSameHue() {
        final Hsl hsl = Hsl.with(HUE, SATURATION, LIGHTNESS);

        this.check(Hsl.with(HUE, SATURATION, LIGHTNESS).set(HUE), HUE, SATURATION, LIGHTNESS);
        this.check(hsl, HUE, SATURATION, LIGHTNESS);
    }

    @Test
    public void testSetDifferentHue() {
        final HueHslComponent different = HueHslComponent.with(180);
        final Hsl hsl = Hsl.with(HUE, SATURATION, LIGHTNESS);

        this.check(Hsl.with(HUE, SATURATION, LIGHTNESS).set(different), different, SATURATION, LIGHTNESS);
        this.check(hsl, HUE, SATURATION, LIGHTNESS);
    }

    @Test
    public void testSetSameSaturation() {
        final Hsl hsl = Hsl.with(HUE, SATURATION, LIGHTNESS);
        assertSame(hsl, hsl.set(SATURATION));

        this.check(Hsl.with(HUE, SATURATION, LIGHTNESS).set(LIGHTNESS), HUE, SATURATION, LIGHTNESS);
        this.check(hsl, HUE, SATURATION, LIGHTNESS);
    }

    @Test
    public void testSetDifferentSaturation() {
        final SaturationHslComponent different = SaturationHslComponent.with(0.5f);
        final Hsl hsl = Hsl.with(HUE, SATURATION, LIGHTNESS);

        this.check(Hsl.with(HUE, SATURATION, LIGHTNESS).set(different), HUE, different, LIGHTNESS);
        this.check(hsl, HUE, SATURATION, LIGHTNESS);
    }

    @Test
    public void testSetSameValue() {
        final Hsl hsl = Hsl.with(HUE, SATURATION, LIGHTNESS);
        this.check(Hsl.with(HUE, SATURATION, LIGHTNESS).set(LIGHTNESS), HUE, SATURATION, LIGHTNESS);
        this.check(hsl, HUE, SATURATION, LIGHTNESS);
    }

    @Test
    public void testSetDifferentValue() {
        final LightnessHslComponent different = LightnessHslComponent.with(0.99f);
        this.check(Hsl.with(HUE, SATURATION, LIGHTNESS).set(different), HUE, SATURATION, different);
    }

    private void check(final Hsl hsl,
                       final HueHslComponent hue,
                       final SaturationHslComponent saturation,
                       final LightnessHslComponent value) {
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
        final Hsl hsl = Hsl.with(HslComponent.hue(hue), HslComponent.saturation(saturation), HslComponent.lightness(value));
        final Color expected = Color.fromRgb(rgb);
        final Color actual = hsl.toColor();
        if ((false == this.isEquals(expected.red, actual.red)) || (false == this.isEquals(expected.green, actual.green))
                || (false == this.isEquals(expected.blue, actual.blue))) {
            assertEquals(expected, actual, () -> "failed to convert " + hsl + " to a Color");
        }
    }

    private boolean isEquals(final ColorComponent expected, final ColorComponent actual) {
        return Math.abs(expected.value - actual.value) < 3;
    }

    @Test
    public void testEqualsDifferentHue() {
        this.checkNotEquals(Hsl.with(HueHslComponent.with(99), SATURATION, LIGHTNESS));
    }

    @Test
    public void testEqualsDifferentSaturation() {
        this.checkNotEquals(Hsl.with(HUE, SaturationHslComponent.with(0.99f), LIGHTNESS));
    }

    @Test
    public void testEqualsDifferentValue() {
        this.checkNotEquals(Hsl.with(HUE, SATURATION, LightnessHslComponent.with(0.99f)));
    }

    @Test
    public void testParse() {
        this.parseAndCheck("hsl(359,100%,50%)",
                Hsl.with(HslComponent.hue(359),
                        HslComponent.saturation(1.0f),
                        HslComponent.lightness(0.5f)));
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(Hsl.with(HUE, SATURATION, LIGHTNESS),
                "hsl(359,25%,50%)");
    }

    @Test
    public void testToStringZeroes() {
        this.toStringAndCheck(Hsl.with(HslComponent.hue(0),
                HslComponent.saturation(0),
                HslComponent.lightness(0)),
                "hsl(0,0%,0%)");
    }

    @Override
    Hsl createColorHslOrHsv() {
        return Hsl.with(HUE, SATURATION, LIGHTNESS);
    }

    // ClassTesting ...................................................................................................

    @Override
    public Class<Hsl> type() {
        return Hsl.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }

    // HasJsonNodeTesting..............................................................................................

    @Override
    public Hsl fromJsonNode(final JsonNode from) {
        return Hsl.fromJsonNodeHsl(from);
    }

    // ParseStringTesting .............................................................................................

    @Override
    public Hsl parse(final String text) {
        return Hsl.parseHsl(text);
    }
}
