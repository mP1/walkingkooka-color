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
import walkingkooka.test.ClassTesting2;
import walkingkooka.type.JavaVisibility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class RgbColorStringTest implements ClassTesting2<RgbColorString> {

    @Test
    public void testToStringNullFails() {
        assertThrows(NullPointerException.class, () -> {
            RgbColorString.RGB_DECIMAL.toString(null);
        });
    }

    // hash..........................................................................................................

    @Test
    public void testHashOpaqueColor() {
        this.toStringAndCheck(RgbColorString.HASH,
                RgbColor.fromRgb0(0x12345),
                "#012345");
    }

    @Test
    public void testHashAlphaColor() {
        this.toStringAndCheck(RgbColorString.HASH,
                RgbColor.fromRgb0(0x12345).set(RgbColorComponent.alpha((byte) 0xfe)),
                "#012345fe");
    }

    // decimal..........................................................................................................

    @Test
    public void testRgbFunctionDecimalOpaqueColor() {
        this.toStringAndCheck(RgbColorString.RGB_DECIMAL,
                RgbColor.fromRgb0(0),
                "rgb(0,0,0)");
    }

    @Test
    public void testRgbFunctionDecimalOpaqueColor2() {
        this.toStringAndCheck(RgbColorString.RGB_DECIMAL,
                RgbColor.fromRgb0(0xFFFEFD),
                "rgb(255,254,253)");
    }

    @Test
    public void testRgbFunctionDecimalOpaqueColor3() {
        this.toStringAndCheck(RgbColorString.RGB_DECIMAL,
                RgbColor.fromRgb0(0x010203),
                "rgb(1,2,3)");
    }

    @Test
    public void testRgbFunctionDecimalAlphaColor() {
        this.toStringAndCheck(RgbColorString.RGB_DECIMAL,
                RgbColor.fromRgb0(0).set(RgbColorComponent.alpha((byte) 0)),
                "rgba(0,0,0,0)");
    }

    @Test
    public void testRgbFunctionDecimalAlphaColor2() {
        this.toStringAndCheck(RgbColorString.RGB_DECIMAL,
                RgbColor.fromRgb0(0xFFFEFD).set(RgbColorComponent.alpha((byte) 0xfc)),
                "rgba(255,254,253,252)");
    }

    @Test
    public void testRgbFunctionDecimalAlphaColor3() {
        this.toStringAndCheck(RgbColorString.RGB_DECIMAL,
                RgbColor.fromRgb0(0x010203).set(RgbColorComponent.alpha((byte) 4)),
                "rgba(1,2,3,4)");
    }

    // percentage..........................................................................................................

    @Test
    public void testRgbFunctionPercentageOpaqueColor() {
        this.toStringAndCheck(RgbColorString.RGB_PERCENTAGE,
                RgbColor.fromRgb0(0),
                "rgb(0%,0%,0%)");
    }

    @Test
    public void testRgbFunctionPercentageOpaqueColor2() {
        this.toStringAndCheck(RgbColorString.RGB_PERCENTAGE,
                RgbColor.fromRgb0(0xFFFEFD),
                "rgb(100%,100%,99%)");
    }

    @Test
    public void testRgbFunctionPercentageOpaqueColor3() {
        this.toStringAndCheck(RgbColorString.RGB_PERCENTAGE,
                RgbColor.fromRgb0(0x010203),
                "rgb(0%,1%,1%)");
    }

    @Test
    public void testRgbFunctionPercentageAlphaColor() {
        this.toStringAndCheck(RgbColorString.RGB_PERCENTAGE,
                RgbColor.fromRgb0(0).set(RgbColorComponent.alpha((byte) 0)),
                "rgba(0%,0%,0%,0%)");
    }

    @Test
    public void testRgbFunctionPercentageAlphaColor2() {
        this.toStringAndCheck(RgbColorString.RGB_PERCENTAGE,
                RgbColor.fromRgb0(0xFFFEFD).set(RgbColorComponent.alpha((byte) 0xfc)),
                "rgba(100%,100%,99%,99%)");
    }

    @Test
    public void testRgbFunctionPercentageAlphaColor3() {
        this.toStringAndCheck(RgbColorString.RGB_PERCENTAGE,
                RgbColor.fromRgb0(0x010203).set(RgbColorComponent.alpha((byte) 4)),
                "rgba(0%,1%,1%,2%)");
    }

    // helper..........................................................................................................

    private void toStringAndCheck(final RgbColorString format,
                                  final RgbColor color,
                                  final String toString) {
        assertEquals(toString,
                format.toString(color),
                () -> "format " + format + " rgb=" + color);
    }

    // ClassTesting....................................................................................................

    @Override
    public Class<RgbColorString> type() {
        return RgbColorString.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
