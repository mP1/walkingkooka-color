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
import walkingkooka.tree.json.JsonNode;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class AlphaRgbColorTest extends RgbColorTestCase<AlphaRgbColor> {

    @Test
    public void testFromArgb() {
        final RgbColor rgb = RgbColor.fromArgb0(0x04010203);
        assertSame(RED, rgb.red(), "red");
        assertSame(GREEN, rgb.green(), "green");
        assertSame(BLUE, rgb.blue(), "blue");
        assertSame(RgbColorComponent.alpha((byte) 0x4), rgb.alpha(), "alpha");
        this.checkEquals(0x04010203, rgb.argb(), "argb");
    }

    @Test
    public void testFromArgbWhenOpaque() {
        final RgbColor rgb = RgbColor.fromArgb0(0xFF010203);
        assertSame(RED, rgb.red(), "red");
        assertSame(GREEN, rgb.green(), "green");
        assertSame(BLUE, rgb.blue(), "blue");
        this.checkEquals(OpaqueRgbColor.class, rgb.getClass(), "rgb must not be a AlphaRgbColor");
        this.checkEquals(0xFF010203, rgb.argb(), "argb");
    }

    @Test
    public void testWithRgbAndArgbAndValue() {
        final RgbColor rgb = RgbColor.with(
                RgbColorComponent.red((byte) 0x80),
                RgbColorComponent.green((byte) 0x81),
                RgbColorComponent.blue((byte) 0x82)
        ).set(RgbColorComponent.alpha((byte) 0x84));

        this.checkEquals(0x808182, rgb.rgb(), "rgb");
        this.checkEquals(0x84808182, rgb.argb(), "argb");
        this.checkEquals(0x84808182, rgb.value(), "value");
    }

    @Test
    @Override
    public void testHasAlpha() {
        final AlphaRgbColor rgb = AlphaRgbColor.with(RED, GREEN, BLUE, ALPHA);
        assertTrue(rgb.hasAlpha(), rgb + " has alpha");
    }

    @Test
    public void testSetSameAlpha() {
        final AlphaRgbColor rgb = this.createColor();
        assertSame(rgb, rgb.set(ALPHA));
    }

    @Test
    public void testSetOpaqueAlphaBecomesOpaqueColor() {
        final AlphaRgbColor rgb = this.createColor();
        final RgbColorComponent replacement = AlphaRgbColorComponent.OPAQUE;
        final OpaqueRgbColor opaque = (OpaqueRgbColor) rgb.set(replacement);
        assertSame(rgb.red(), opaque.red(), "red");
        assertSame(rgb.green(), opaque.green(), "green");
        assertSame(rgb.blue(), opaque.blue(), "blue");
        assertSame(replacement, opaque.alpha(), "alpha");
    }

    @Test
    public void testMixComponentDifferentAlphaVeryLargeAmount() {
        final AlphaRgbColorComponent replacement = AlphaRgbColorComponent.with(DIFFERENT);
        this.mixComponentAndCheck(this.createColor(), replacement, LARGE_AMOUNT, replacement);
    }

    @Test
    public void testInvert2() {
        this.invertAndCheck(
                Color.fromArgb(0xff010203),
                Color.fromArgb(0xfffefdfc)
        );
    }

    // mix..............................................................................................................

    @Test
    public void testMixHalf() {
        this.mixAndCheck(
                "#00000000",
                "#ffffffff",
                0.5f,
                "#80808080"
        );
    }

    @Test
    public void testMixHalf2() {
        this.mixAndCheck(
                "#01234567",
                "#ffffffff",
                0.5f,
                "#8091a2b3"
        );
    }

    @Test
    public void testMixQuarter() {
        this.mixAndCheck(
                "#00000000",
                "#ffffffff",
                0.25f,
                "#40404040"
        );
    }

    @Test
    public void testMixThreeQuarter() {
        this.mixAndCheck(
                "#00000000",
                "#ffffffff",
                0.75f,
                "#bfbfbfbf"
        );
    }

    // toAwt............................................................................................................

    @Test
    public void testToAwtColor() {
        final java.awt.Color rgb = this.createColor().toAwtColor();
        this.checkEquals(1, rgb.getRed(), "red");
        this.checkEquals(2, rgb.getGreen(), "green");
        this.checkEquals(3, rgb.getBlue(), "blue");
        this.checkEquals(4, rgb.getAlpha(), "alpha");
    }

    @Test
    public void testToCss() {
        this.toCssAndCheck(
                Color.parse("#12345681"),
                "rgba(18, 52, 86, 0.5019608)"
        );
    }

    @Test
    public void testWebNameCyan() {
        this.webNameAndCheck(WebColorName.CYAN.color().setAlpha(AlphaRgbColorComponent.with((byte) 0x50)), null);
    }

    @Test
    public void testEqualsDifferentAlpha() {
        final RgbColor rgb = this.createColor();
        this.checkNotEquals(AlphaRgbColor.with(rgb.red(), rgb.green(), rgb.blue(), AlphaRgbColorComponent.with((byte) 0xff)));
    }

    // HasJsonNode............................................................................................

    @Test
    public void testJsonNodeUnmarshall() {
        this.unmarshallAndCheck(JsonNode.string("#01020304"), RgbColor.fromArgb0(0x04010203));
    }

    @Test
    public void testJsonNodeUnmarshallFEDCBA98() {
        this.unmarshallAndCheck(JsonNode.string("#fedcba98"), RgbColor.fromArgb0(0x98FEDCBA));
    }


    @Test
    public void testJsonNodeMarshall() {
        this.marshallAndCheck(RgbColor.fromArgb0(0x04010203), JsonNode.string("#01020304"));
    }

    @Test
    public void testJsonNodeMarshallFEDCBA98() {
        this.marshallAndCheck(RgbColor.fromArgb0(0x98FEDCBA), JsonNode.string("#fedcba98"));
    }

    // Object............................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(RgbColor.fromArgb0(0x04010203), "#01020304");
    }

    @Override
    AlphaRgbColor createColor(final RedRgbColorComponent red,
                              final GreenRgbColorComponent green,
                              final BlueRgbColorComponent blue) {
        return AlphaRgbColor.with(red, green, blue, ALPHA);
    }

    @Override
    public Class<AlphaRgbColor> type() {
        return AlphaRgbColor.class;
    }
}
