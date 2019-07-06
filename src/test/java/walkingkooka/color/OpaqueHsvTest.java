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

public final class OpaqueHsvTest extends HsvTestCase<OpaqueHsv> {

    // tests

    @Test
    public void testWithNullHueFails() {
        assertThrows(NullPointerException.class, () -> {
            OpaqueHsv.with(null, SATURATION, VALUE);
        });
    }

    @Test
    public void testWithNullSaturationFails() {
        assertThrows(NullPointerException.class, () -> {
            OpaqueHsv.with(HUE, null, VALUE);
        });
    }

    @Test
    public void testWithNullValueFails() {
        assertThrows(NullPointerException.class, () -> {
            OpaqueHsv.with(HUE, SATURATION, null);
        });
    }

    @Test
    public void testWith() {
        final OpaqueHsv hsv = OpaqueHsv.withOpaque(HUE, SATURATION, VALUE);
        assertSame(HUE, hsv.hue, "hue");
        assertSame(SATURATION, hsv.saturation, "saturation");
        assertSame(VALUE, hsv.value, "value");
        assertSame(this.alphaHsvComponent(), hsv.alpha(), "alpha");
    }

    @Override
    void toColorAndCheck(final float hue, final float saturation, final float value, final int rgb) {
        final OpaqueHsv hsv = OpaqueHsv.withOpaque(HsvComponent.hue(hue), HsvComponent.saturation(saturation),
                HsvComponent.value(value));
        final Color expected = Color.fromRgb(rgb);
        final Color actual = hsv.toColor();
        if ((false == this.isEquals(expected.red, actual.red)) || //
                (false == this.isEquals(expected.green, actual.green)) || //
                (false == this.isEquals(expected.blue, actual.blue))) {
            assertEquals(expected, actual, "failed to convert " + hsv + " to a Color");
        }
    }

    @Test
    public void testParse() {
        this.parseAndCheck("hsv(359,100%,50%)",
                OpaqueHsv.withOpaque(HsvComponent.hue(359),
                        HsvComponent.saturation(1.0f),
                        HsvComponent.value(0.5f)));
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(OpaqueHsv.withOpaque(HUE, SATURATION, VALUE),
                "hsv(359,50%,25%)");
    }

    @Test
    public void testToStringZeroes() {
        this.toStringAndCheck(OpaqueHsv.withOpaque(HsvComponent.hue(0), HsvComponent.saturation(0), HsvComponent.value(0)),
                "hsv(0,0%,0%)");
    }

    @Override
    OpaqueHsv createHsv(final HueHsvComponent hue,
                        final SaturationHsvComponent saturation,
                        final ValueHsvComponent value) {
        return OpaqueHsv.withOpaque(hue, saturation, value);
    }

    @Override
    AlphaHsvComponent alphaHsvComponent() {
        return AlphaHsvComponent.OPAQUE;
    }

    @Override
    Class<OpaqueHsv> hsvType() {
        return OpaqueHsv.class;
    }

    // ParseStringTesting .............................................................................................

    @Override
    public OpaqueHsv parse(final String text) {
        return Cast.to(Hsv.parseHsv(text));
    }
}
