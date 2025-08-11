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
import walkingkooka.tree.json.JsonNode;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class OpaqueRgbColorTest extends RgbColorTestCase<OpaqueRgbColor> {

    @Test
    public void testWithNullRedFails() {
        assertThrows(
            NullPointerException.class,
            () -> OpaqueRgbColor.withOpaque(
                null,
                GREEN,
                BLUE
            )
        );
    }

    @Test
    public void testWithNullGreenFails() {
        assertThrows(
            NullPointerException.class,
            () -> OpaqueRgbColor.withOpaque(
                RED,
                null,
                BLUE
            )
        );
    }

    @Test
    public void testWithNullBlueFails() {
        assertThrows(
            NullPointerException.class,
            () -> OpaqueRgbColor.withOpaque(
                RED,
                GREEN,
                null
            )
        );
    }

    @Test
    public void testWith() {
        final OpaqueRgbColor rgb = OpaqueRgbColor.withOpaque(RED, GREEN, BLUE);
        assertSame(RED, rgb.red(), "red");
        assertSame(GREEN, rgb.green(), "green");
        assertSame(BLUE, rgb.blue(), "blue");
        assertSame(AlphaRgbColorComponent.OPAQUE, rgb.alpha(), "alpha");

        this.checkEquals(
            (RED.value << 16) + (GREEN.value << 8) + BLUE.value,
            rgb.rgb(),
            "rgb"
        );
    }

    @Test
    public void testFromRgb() {
        final RgbColor rgb = RgbColor.fromRgb0(0x010203);
        assertSame(RED, rgb.red(), "red");
        assertSame(GREEN, rgb.green(), "green");
        assertSame(BLUE, rgb.blue(), "blue");
        assertSame(AlphaRgbColorComponent.OPAQUE, rgb.alpha(), "alpha");

        this.checkEquals(
            0x010203,
            rgb.rgb(),
            "rgb"
        );
    }

    @Test
    public void testWithRgbAndArgbAndValue() {
        final RgbColor rgb = OpaqueRgbColor.withOpaque(
            RedRgbColorComponent.with((byte) 0x80),
            GreenRgbColorComponent.with((byte) 0x81),
            BlueRgbColorComponent.with((byte) 0x82)
        );
        this.checkEquals(0x808182, rgb.rgb(), "rgb");
        this.checkEquals(0xFF808182, rgb.argb(), "argb");
        this.checkEquals(0x808182, rgb.value(), "value");
    }

    @Override
    public void testHasAlpha() {
        final OpaqueRgbColor rgb = OpaqueRgbColor.withOpaque(RED, GREEN, BLUE);
        this.checkEquals(
            false,
            rgb.hasAlpha(),
            () -> rgb + " has no alpha"
        );
    }

    @Test
    public void testWithAlpha() {
        final RgbColor rgb = this.createColor();
        this.checkNotEquals(
            AlphaRgbColor.with(
                rgb.red(),
                rgb.green(),
                rgb.blue(),
                AlphaRgbColorComponent.with((byte) 4))
        );
    }

    @Test
    public void testToAwtColor() {
        final java.awt.Color rgb = RgbColor.fromRgb0(0x010203)
            .toAwtColor();

        this.checkEquals(
            1,
            rgb.getRed(),
            "red"
        );
        this.checkEquals(
            2,
            rgb.getGreen(),
            "green"
        );
        this.checkEquals(
            3,
            rgb.getBlue(),
            "blue"
        );
    }

    // mix..............................................................................................................

    @Test
    public void testMixHalf() {
        this.mixAndCheck(
            "#000000",
            "#ffffff",
            0.5f,
            "#808080"
        );
    }

    @Test
    public void testMixHalf2() {
        this.mixAndCheck(
            "#012345",
            "#ffffff",
            0.5f,
            "#8091a2"
        );
    }

    @Test
    public void testMixQuarter() {
        this.mixAndCheck(
            "#000000",
            "#ffffff",
            0.25f,
            "#404040"
        );
    }

    @Test
    public void testMixThreeQuarter() {
        this.mixAndCheck(
            "#000000",
            "#ffffff",
            0.75f,
            "#bfbfbf"
        );
    }

    // webName..........................................................................................................

    @Test
    public void testWebNameCyan() {
        this.toWebNameAndCheck(
            WebColorName.CYAN.color(),
            WebColorName.CYAN
        );
    }

    @Test
    public void testWebNameConstants() {
        WebColorName.RRGGBB_CONSTANTS.values()
            .forEach(n -> {
                final RgbColor color = n.color();
                this.toWebNameAndCheck(color, n);
            });
    }

    // toGray...........................................................................................................

    @Test
    public void testToGray() {
        this.toGrayAndCheck(
            RgbColor.parseRgb("#18f"),
            RgbColor.parseRgb("#717171")
        );
    }

    // toHexString......................................................................................................

    @Test
    public void testToHexString() {
        this.toHexStringAndCheck(
            RgbColor.fromRgb0(0x123456),
            "#123456"
        );
    }

    @Test
    public void testToHexString2() {
        this.toHexStringAndCheck(
            RgbColor.fromRgb0(0),
            "#000000"
        );
    }

    @Override
    OpaqueRgbColor createColor(final RedRgbColorComponent red,
                               final GreenRgbColorComponent green,
                               final BlueRgbColorComponent blue) {
        return OpaqueRgbColor.withOpaque(
            red,
            green,
            blue
        );
    }

    // json.............................................................................................................

    @Test
    public void testJsonNodeUnmarshallString() {
        this.unmarshallAndCheck(JsonNode.string("#123456"),
            Cast.to(RgbColor.fromRgb0(0x123456)));
    }

    @Test
    public void testJsonNodeMarshall() {
        this.marshallAndCheck(RgbColor.fromRgb0(0x123456),
            JsonNode.string("#123456"));
    }

    // Object...........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            RgbColor.fromRgb0(0x010203),
            "#010203"
        );
    }

    @Test
    public void testToStringWebColorBlack() {
        final WebColorName webColorName = WebColorName.BLACK;

        this.toStringAndCheck(
            webColorName.color(),
            webColorName.text()
        );
    }

    @Test
    public void testToStringWebColor() {
        final WebColorName webColorName = WebColorName.RED;

        this.toStringAndCheck(
            webColorName.color(),
            webColorName.text()
        );
    }

    // ClassTesting ....................................................................................................

    @Override
    public Class<OpaqueRgbColor> type() {
        return OpaqueRgbColor.class;
    }
}
