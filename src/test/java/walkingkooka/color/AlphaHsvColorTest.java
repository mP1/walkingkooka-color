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

public final class AlphaHsvColorTest extends HsvTestCase<AlphaHsvColor> {

    @Test
    public void testWith() {
        final AlphaHsvColorComponent alpha = this.alphaHsvColorComponent();
        final HsvColor hsv = AlphaHsvColor.withAlpha(HUE, SATURATION, VALUE, alpha);
        assertSame(HUE, hsv.hue, "hue");
        assertSame(SATURATION, hsv.saturation, "saturation");
        assertSame(VALUE, hsv.value, "value");
        assertSame(alpha, hsv.alpha(), "alpha");
    }

    @Override
    void toColorAndCheck(final float hue, final float saturation, final float value, final int rgb) {
        final float alpha = 0.0f;

        final AlphaHsvColor hsva = AlphaHsvColor.withAlpha(HsvColorComponent.hue(hue),
                HsvColorComponent.saturation(saturation),
                HsvColorComponent.value(value),
                HsvColorComponent.alpha(alpha));
        final RgbColor expected = RgbColor.fromRgb0(rgb)
                .set(RgbColorComponent.alpha(RgbColorComponent.toByte(alpha)));
        final RgbColor actual = hsva.toRgb();
        if ((false == this.isEquals(expected.red, actual.red)) || //
                (false == this.isEquals(expected.green, actual.green)) || //
                (false == this.isEquals(expected.blue, actual.blue) ||
                        (false == this.isEquals(expected.alpha(), actual.alpha())))) {
            assertEquals(expected, actual, "failed to convert " + hsva + " to a RgbColor");
        }
    }

    @Test
    public void testParse() {
        this.parseAndCheck("hsva(359,100%,50%,25%)",
                AlphaHsvColor.withAlpha(HsvColorComponent.hue(359),
                        HsvColorComponent.saturation(1.0f),
                        HsvColorComponent.value(0.5f),
                        HsvColorComponent.alpha(0.25f)));
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(AlphaHsvColor.withAlpha(HUE, SATURATION, VALUE, HsvColorComponent.alpha(0.75f)),
                "hsva(359,50%,25%,75%)");
    }

    @Test
    public void testToStringZeroes() {
        this.toStringAndCheck(AlphaHsvColor.withAlpha(HsvColorComponent.hue(0),
                HsvColorComponent.saturation(0),
                HsvColorComponent.value(0),
                HsvColorComponent.alpha(0)),
                "hsva(0,0%,0%,0%)");
    }

    @Override
    AlphaHsvColor createHsv(final HueHsvColorComponent hue,
                            final SaturationHsvColorComponent saturation,
                            final ValueHsvColorComponent value) {
        return AlphaHsvColor.withAlpha(hue, saturation, value, this.alphaHsvColorComponent());
    }

    @Override
    AlphaHsvColorComponent alphaHsvColorComponent() {
        return this.alphaHsvComponent;
    }

    private final AlphaHsvColorComponent alphaHsvComponent = AlphaHsvColorComponent.with(0.5f);

    @Override
    Class<AlphaHsvColor> hsvType() {
        return AlphaHsvColor.class;
    }

    // ParseStringTesting .............................................................................................

    @Override
    public AlphaHsvColor parse(final String text) {
        return Cast.to(HsvColor.parseHsv(text));
    }
}
