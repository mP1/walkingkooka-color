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

public final class AlphaHslColorTest extends HslColorTestCase<AlphaHslColor> {

    @Test
    public void testWith() {
        final AlphaHslColorComponent alpha = this.alphaHslComponent();
        final HslColor hsl = AlphaHslColor.withAlpha(HUE, SATURATION, LIGHTNESS, alpha);
        assertSame(HUE, hsl.hue, "hue");
        assertSame(SATURATION, hsl.saturation, "saturation");
        assertSame(LIGHTNESS, hsl.lightness, "lightness");
        assertSame(alpha, hsl.alpha(), "alpha");
    }

    @Override
    void toRgbColorAndCheck(final float hue, final float saturation, final float value, final int rgb) {
        final float alpha = 0.5f;

        final AlphaHslColor hsla = AlphaHslColor.withAlpha(HslColorComponent.hue(hue),
                HslColorComponent.saturation(saturation),
                HslColorComponent.lightness(value),
                HslColorComponent.alpha(alpha));
        final RgbColor expected = RgbColor.fromRgb0(rgb)
                .set(RgbColorComponent.alpha(RgbColorComponent.toByte(alpha)));
        final RgbColor actual = hsla.toRgb();
        if ((false == this.isEquals(expected.red, actual.red)) || //
                (false == this.isEquals(expected.green, actual.green)) || //
                (false == this.isEquals(expected.blue, actual.blue) ||
                        (false == this.isEquals(expected.alpha(), actual.alpha())))) {
            this.checkEquals(expected, actual, "failed to convert " + hsla + " to a RgbColor");
        }
    }

    @Test
    public void testParse() {
        this.parseStringAndCheck("hsla(359,100%,50%,25%)",
                AlphaHslColor.withAlpha(HslColorComponent.hue(359),
                        HslColorComponent.saturation(1.0f),
                        HslColorComponent.lightness(0.5f),
                        HslColorComponent.alpha(0.25f)));
    }

    // toCss............................................................................................................

    @Test
    public void testToCss() {
        final String css = "hsla(359,100%,50%,25%)";
        this.toCssAndCheck(
                Color.parse(css),
                css
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(AlphaHslColor.withAlpha(HUE, SATURATION, LIGHTNESS, HslColorComponent.alpha(0.75f)),
                "hsla(359,50%,25%,75%)");
    }

    @Test
    public void testToStringZeroes() {
        this.toStringAndCheck(AlphaHslColor.withAlpha(HslColorComponent.hue(0),
                HslColorComponent.saturation(0),
                HslColorComponent.lightness(0),
                HslColorComponent.alpha(0)),
                "hsla(0,0%,0%,0%)");
    }

    @Override
    AlphaHslColor createHsl(final HueHslColorComponent hue,
                            final SaturationHslColorComponent saturation,
                            final LightnessHslColorComponent lightness) {
        return AlphaHslColor.withAlpha(hue, saturation, lightness, this.alphaHslComponent());
    }

    @Override
    AlphaHslColorComponent alphaHslComponent() {
        return this.alphaHslComponent;
    }

    private final AlphaHslColorComponent alphaHslComponent = AlphaHslColorComponent.with(0.5f);

    @Override
    Class<AlphaHslColor> hslType() {
        return AlphaHslColor.class;
    }

    // ParseStringTesting .............................................................................................

    @Override
    public AlphaHslColor parseString(final String text) {
        return Cast.to(HslColor.parseHsl(text));
    }
}
