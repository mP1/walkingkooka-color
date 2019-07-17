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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class OpaqueHsvColorTest extends HsvTestCase<OpaqueHsvColor> {

    // tests

    @Test
    public void testWithNullHueFails() {
        assertThrows(NullPointerException.class, () -> {
            OpaqueHsvColor.with(null, SATURATION, VALUE);
        });
    }

    @Test
    public void testWithNullSaturationFails() {
        assertThrows(NullPointerException.class, () -> {
            OpaqueHsvColor.with(HUE, null, VALUE);
        });
    }

    @Test
    public void testWithNullValueFails() {
        assertThrows(NullPointerException.class, () -> {
            OpaqueHsvColor.with(HUE, SATURATION, null);
        });
    }

    @Test
    public void testWith() {
        final OpaqueHsvColor hsv = OpaqueHsvColor.withOpaque(HUE, SATURATION, VALUE);
        assertSame(HUE, hsv.hue, "hue");
        assertSame(SATURATION, hsv.saturation, "saturation");
        assertSame(VALUE, hsv.value, "value");
        assertSame(this.alphaHsvColorComponent(), hsv.alpha(), "alpha");
    }

    @Override
    void toColorAndCheck(final float hue, final float saturation, final float value, final int rgb) {
        final OpaqueHsvColor hsv = OpaqueHsvColor.withOpaque(HsvColorComponent.hue(hue), HsvColorComponent.saturation(saturation),
                HsvColorComponent.value(value));
        final RgbColor expected = RgbColor.fromRgb0(rgb);
        final RgbColor actual = hsv.toRgb();
        if ((false == this.isEquals(expected.red, actual.red)) || //
                (false == this.isEquals(expected.green, actual.green)) || //
                (false == this.isEquals(expected.blue, actual.blue))) {
            assertEquals(expected, actual, "failed to convert " + hsv + " to a RgbColor");
        }
    }

    @Test
    public void testParse() {
        this.parseAndCheck("hsv(359,100%,50%)",
                OpaqueHsvColor.withOpaque(HsvColorComponent.hue(359),
                        HsvColorComponent.saturation(1.0f),
                        HsvColorComponent.value(0.5f)));
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(OpaqueHsvColor.withOpaque(HUE, SATURATION, VALUE),
                "hsv(359,50%,25%)");
    }

    @Test
    public void testToStringZeroes() {
        this.toStringAndCheck(OpaqueHsvColor.withOpaque(HsvColorComponent.hue(0), HsvColorComponent.saturation(0), HsvColorComponent.value(0)),
                "hsv(0,0%,0%)");
    }

    @Override
    OpaqueHsvColor createHsv(final HueHsvColorComponent hue,
                             final SaturationHsvColorComponent saturation,
                             final ValueHsvColorComponent value) {
        return OpaqueHsvColor.withOpaque(hue, saturation, value);
    }

    @Override
    AlphaHsvColorComponent alphaHsvColorComponent() {
        return AlphaHsvColorComponent.OPAQUE;
    }

    @Override
    Class<OpaqueHsvColor> hsvType() {
        return OpaqueHsvColor.class;
    }

    // ParseStringTesting .............................................................................................

    @Override
    public OpaqueHsvColor parse(final String text) {
        return Cast.to(HsvColor.parseHsv(text));
    }
}
