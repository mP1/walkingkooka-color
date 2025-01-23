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
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class RgbColorStringTest implements ClassTesting2<RgbColorString> {

    @Test
    public void testToStringNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> RgbColorString.RGB_DECIMAL.toString(null)
        );
    }

    // hash..........................................................................................................

    @Test
    public void testToStringHashOpaqueColor() {
        this.toStringAndCheck(
            RgbColorString.HASH,
            RgbColor.fromRgb0(0x12345),
            "#012345"
        );
    }

    @Test
    public void testToStringHashAlphaColor() {
        this.toStringAndCheck(
            RgbColorString.HASH,
            RgbColor.fromRgb0(0x12345).set(RgbColorComponent.alpha((byte) 0xfe)),
            "#012345fe"
        );
    }

    // decimal..........................................................................................................

    @Test
    public void testToStringRgbFunctionDecimalOpaqueColor() {
        this.toStringAndCheck(
            RgbColorString.RGB_DECIMAL,
            RgbColor.fromRgb0(0),
            "rgb(0,0,0)"
        );
    }

    @Test
    public void testToStringRgbFunctionDecimalOpaqueColor2() {
        this.toStringAndCheck(
            RgbColorString.RGB_DECIMAL,
            RgbColor.fromRgb0(0xFFFEFD),
            "rgb(255,254,253)"
        );
    }

    @Test
    public void testToStringRgbFunctionDecimalOpaqueColor3() {
        this.toStringAndCheck(
            RgbColorString.RGB_DECIMAL,
            RgbColor.fromRgb0(0x010203),
            "rgb(1,2,3)"
        );
    }

    @Test
    public void testToStringRgbFunctionDecimalAlphaColor() {
        this.toStringAndCheck(
            RgbColorString.RGB_DECIMAL,
            RgbColor.fromRgb0(0).set(RgbColorComponent.alpha((byte) 0)),
            "rgba(0,0,0,0)"
        );
    }

    @Test
    public void testToStringRgbFunctionDecimalAlphaColor2() {
        this.toStringAndCheck(
            RgbColorString.RGB_DECIMAL,
            RgbColor.fromRgb0(0xFFFEFD).set(RgbColorComponent.alpha((byte) 0xfc)),
            "rgba(255,254,253,252)"
        );
    }

    @Test
    public void testToStringRgbFunctionDecimalAlphaColor3() {
        this.toStringAndCheck(
            RgbColorString.RGB_DECIMAL,
            RgbColor.fromRgb0(0x010203).set(RgbColorComponent.alpha((byte) 4)),
            "rgba(1,2,3,4)"
        );
    }

    // percentage.......................................................................................................

    @Test
    public void testToStringRgbFunctionPercentageOpaqueColor() {
        this.toStringAndCheck(
            RgbColorString.RGB_PERCENTAGE,
            RgbColor.fromRgb0(0),
            "rgb(0%,0%,0%)"
        );
    }

    @Test
    public void testToStringRgbFunctionPercentageOpaqueColor2() {
        this.toStringAndCheck(
            RgbColorString.RGB_PERCENTAGE,
            RgbColor.fromRgb0(0xFFFEFD),
            "rgb(100%,100%,99%)"
        );
    }

    @Test
    public void testToStringRgbFunctionPercentageOpaqueColor3() {
        this.toStringAndCheck(
            RgbColorString.RGB_PERCENTAGE,
            RgbColor.fromRgb0(0x010203),
            "rgb(0%,1%,1%)"
        );
    }

    @Test
    public void testToStringRgbFunctionPercentageAlphaColor() {
        this.toStringAndCheck(
            RgbColorString.RGB_PERCENTAGE,
            RgbColor.fromRgb0(0).set(RgbColorComponent.alpha((byte) 0)),
            "rgba(0%,0%,0%,0%)"
        );
    }

    @Test
    public void testToStringRgbFunctionPercentageAlphaColor2() {
        this.toStringAndCheck(
            RgbColorString.RGB_PERCENTAGE,
            RgbColor.fromRgb0(0xFFFEFD).set(RgbColorComponent.alpha((byte) 0xfc)),
            "rgba(100%,100%,99%,99%)"
        );
    }

    @Test
    public void testToStringRgbFunctionPercentageAlphaColor3() {
        this.toStringAndCheck(
            RgbColorString.RGB_PERCENTAGE,
            RgbColor.fromRgb0(0x010203).set(RgbColorComponent.alpha((byte) 4)),
            "rgba(0%,1%,1%,2%)"
        );
    }

    // helper..........................................................................................................

    private void toStringAndCheck(final RgbColorString format,
                                  final RgbColor color,
                                  final String toString) {
        this.checkEquals(
            toString,
            format.toString(color),
            () -> "format " + format + " rgb=" + color
        );
    }

    // ClassTesting.....................................................................................................

    @Override
    public Class<RgbColorString> type() {
        return RgbColorString.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
