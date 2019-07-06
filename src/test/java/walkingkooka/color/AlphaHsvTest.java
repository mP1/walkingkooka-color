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

public final class AlphaHsvTest extends HsvTestCase<AlphaHsv> {

    @Test
    public void testWith() {
        final AlphaHsvComponent alpha = this.alphaHsvComponent();
        final Hsv hsv = AlphaHsv.withAlpha(HUE, SATURATION, VALUE, alpha);
        assertSame(HUE, hsv.hue, "hue");
        assertSame(SATURATION, hsv.saturation, "saturation");
        assertSame(VALUE, hsv.value, "value");
        assertSame(alpha, hsv.alpha(), "alpha");
    }

    @Override
    void toColorAndCheck(final float hue, final float saturation, final float value, final int rgb) {
        final float alpha = 0.0f;

        final AlphaHsv hsva = AlphaHsv.withAlpha(HsvComponent.hue(hue),
                HsvComponent.saturation(saturation),
                HsvComponent.value(value),
                HsvComponent.alpha(alpha));
        final Color expected = Color.fromRgb(rgb)
                .set(ColorComponent.alpha(ColorComponent.toByte(alpha)));
        final Color actual = hsva.toColor();
        if ((false == this.isEquals(expected.red, actual.red)) || //
                (false == this.isEquals(expected.green, actual.green)) || //
                (false == this.isEquals(expected.blue, actual.blue) ||
                        (false == this.isEquals(expected.alpha(), actual.alpha())))) {
            assertEquals(expected, actual, "failed to convert " + hsva + " to a Color");
        }
    }

    @Test
    public void testParse() {
        this.parseAndCheck("hsva(359,100%,50%,25%)",
                AlphaHsv.withAlpha(HsvComponent.hue(359),
                        HsvComponent.saturation(1.0f),
                        HsvComponent.value(0.5f),
                        HsvComponent.alpha(0.25f)));
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(AlphaHsv.withAlpha(HUE, SATURATION, VALUE, HsvComponent.alpha(0.75f)),
                "hsva(359,50%,25%,75%)");
    }

    @Test
    public void testToStringZeroes() {
        this.toStringAndCheck(AlphaHsv.withAlpha(HsvComponent.hue(0),
                HsvComponent.saturation(0),
                HsvComponent.value(0),
                HsvComponent.alpha(0)),
                "hsva(0,0%,0%,0%)");
    }

    @Override
    AlphaHsv createHsv(final HueHsvComponent hue,
                       final SaturationHsvComponent saturation,
                       final ValueHsvComponent value) {
        return AlphaHsv.withAlpha(hue, saturation, value, this.alphaHsvComponent());
    }

    @Override
    AlphaHsvComponent alphaHsvComponent() {
        return this.alphaHsvComponent;
    }

    private final AlphaHsvComponent alphaHsvComponent = AlphaHsvComponent.with(0.5f);

    @Override
    Class<AlphaHsv> hsvType() {
        return AlphaHsv.class;
    }

    // ParseStringTesting .............................................................................................

    @Override
    public AlphaHsv parse(final String text) {
        return Cast.to(Hsv.parseHsv(text));
    }
}
