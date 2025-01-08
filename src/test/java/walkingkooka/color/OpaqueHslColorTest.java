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

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class OpaqueHslColorTest extends HslColorTestCase<OpaqueHslColor> {

    // tests

    @Test
    public void testWithNullHueFails() {
        assertThrows(NullPointerException.class, () -> OpaqueHslColor.with(null, SATURATION, LIGHTNESS));
    }

    @Test
    public void testWithNullSaturationFails() {
        assertThrows(NullPointerException.class, () -> OpaqueHslColor.with(HUE, null, LIGHTNESS));
    }

    @Test
    public void testWithNullLightnessFails() {
        assertThrows(NullPointerException.class, () -> OpaqueHslColor.with(HUE, SATURATION, null));
    }

    @Test
    public void testWith() {
        final OpaqueHslColor hsl = OpaqueHslColor.withOpaque(HUE, SATURATION, LIGHTNESS);
        assertSame(HUE, hsl.hue, "hue");
        assertSame(SATURATION, hsl.saturation, "saturation");
        assertSame(LIGHTNESS, hsl.lightness, "lightness");
        assertSame(this.alphaHslComponent(), hsl.alpha(), "alpha");
    }

    @Override
    void toRgbColorAndCheck(final float hue,
                            final float saturation,
                            final float value,
                            final int rgb) {
        final OpaqueHslColor hsl = OpaqueHslColor.withOpaque(HslColorComponent.hue(hue), HslColorComponent.saturation(saturation),
            HslColorComponent.lightness(value));
        final RgbColor expected = RgbColor.fromRgb0(rgb);
        final RgbColor actual = hsl.toRgb();
        if ((false == this.isEquals(expected.red, actual.red)) || //
            (false == this.isEquals(expected.green, actual.green)) || //
            (false == this.isEquals(expected.blue, actual.blue))) {
            this.checkEquals(expected, actual, "failed to convert " + hsl + " to a RgbColor");
        }
    }

    @Test
    public void testParse() {
        this.parseStringAndCheck("hsl(359,100%,50%)",
            OpaqueHslColor.withOpaque(HslColorComponent.hue(359),
                HslColorComponent.saturation(1.0f),
                HslColorComponent.lightness(0.5f)));
    }

    // text............................................................................................................

    @Test
    public void testText() {
        final String css = "hsl(359,50%,25%)";
        this.textAndCheck(
            Color.parse(css),
            css
        );
    }

    // mix..............................................................................................................

    @Test
    public void testMixHalf() {
        this.mixAndCheck(
            "hsl(359,0%,0%)",
            "hsl(0,50%,50%)",
            0.5f,
            "hsl(180,25%,25%)"
        );
    }

    @Test
    public void testMixHalf2() {
        this.mixAndCheck(
            "hsl(0,0%,0%)",
            "hsl(359,70%,50%)",
            0.5f,
            "hsl(180,35%,25%)"
        );
    }

    @Test
    public void testMixQuarter() {
        this.mixAndCheck(
            "hsl(0,0%,0%)",
            "hsl(359,80%,20%)",
            0.25f,
            "hsl(90,20%,5%)"
        );
    }

    @Test
    public void testMixThreeQuarter() {
        this.mixAndCheck(
            "hsl(0,0%,0%)",
            "hsl(359,100%,50%)",
            0.75f,
            "hsl(269,75%,38%)"
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(OpaqueHslColor.withOpaque(HUE, SATURATION, LIGHTNESS),
            "hsl(359,50%,25%)");
    }

    @Test
    public void testToStringZeroes() {
        this.toStringAndCheck(OpaqueHslColor.withOpaque(HslColorComponent.hue(0), HslColorComponent.saturation(0), HslColorComponent.lightness(0)),
            "hsl(0,0%,0%)");
    }

    @Override
    OpaqueHslColor createHsl(final HueHslColorComponent hue,
                             final SaturationHslColorComponent saturation,
                             final LightnessHslColorComponent lightness) {
        return OpaqueHslColor.withOpaque(hue, saturation, lightness);
    }

    @Override
    AlphaHslColorComponent alphaHslComponent() {
        return AlphaHslColorComponent.OPAQUE;
    }

    @Override
    Class<OpaqueHslColor> hslType() {
        return OpaqueHslColor.class;
    }

    // ParseStringTesting .............................................................................................

    @Override
    public OpaqueHslColor parseString(final String text) {
        return Cast.to(HslColor.parseHsl(text));
    }
}
