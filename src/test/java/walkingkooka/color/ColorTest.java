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
import walkingkooka.test.ParseStringTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallingTesting;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

public final class ColorTest implements ClassTesting2<Color>,
    JsonNodeMarshallingTesting<Color>,
    ParseStringTesting<Color> {

    // isColorClass.....................................................................................................

    @Test
    public void testIsColorClassWithNull() {
        this.isColorClassAndCheck(
            null,
            false
        );
    }

    @Test
    public void testIsColorClassWithThis() {
        this.isColorClassAndCheck(
            this.getClass(),
            false
        );
    }

    @Test
    public void testIsColorClassWithColor() {
        this.isColorClassAndCheck(
            Color.class,
            true
        );
    }

    @Test
    public void testIsColorClassWithRgbColor() {
        this.isColorClassAndCheck(
            RgbColor.class,
            true
        );
    }

    private void isColorClassAndCheck(final Class<?> type,
                                      final boolean expected) {
        this.checkEquals(
            Color.isColorClass(type),
            expected
        );
    }

    // isRgbColorClass..................................................................................................

    @Test
    public void testIsRgbColorClassWithNull() {
        this.isRgbColorClassAndCheck(
            null,
            false
        );
    }

    @Test
    public void testIsRgbColorClassWithThis() {
        this.isRgbColorClassAndCheck(
            this.getClass(),
            false
        );
    }

    @Test
    public void testIsRgbColorClassWithColor() {
        this.isRgbColorClassAndCheck(
            Color.class,
            false
        );
    }

    @Test
    public void testIsRgbColorClassWithRgbColor() {
        this.isRgbColorClassAndCheck(
            RgbColor.class,
            true
        );
    }

    @Test
    public void testIsRgbColorClassWithOpaqueRgbColor() {
        this.isRgbColorClassAndCheck(
            Color.parseRgb("#123").getClass(),
            true
        );
    }

    @Test
    public void testIsRgbColorClassWithAlphaRgbColor() {
        this.isRgbColorClassAndCheck(
            Color.parseRgb("#112233ff").getClass(),
            true
        );
    }

    @Test
    public void testIsRgbColorClassWithHslColor() {
        this.isRgbColorClassAndCheck(
            HslColor.class,
            false
        );
    }

    private void isRgbColorClassAndCheck(final Class<?> type,
                                         final boolean expected) {
        this.checkEquals(
            Color.isRgbColorClass(type),
            expected
        );
    }

    // parse............................................................................................................

    @Test
    public void testParseFails() {
        this.parseStringFails(
            "abc",
            IllegalArgumentException.class
        );
    }

    @Test
    public void testParseHsl() {
        this.parseStringAndCheck(
            "hsl(359, 0%, 25%)",
            HslColor.with(
                HslColorComponent.hue(359f),
                HslColorComponent.saturation(0.0f),
                HslColorComponent.lightness(0.25f)
            )
        );
    }

    @Test
    public void testParseHsla() {
        this.parseStringAndCheck(
            "hsla(359, 0%, 25%, 50%)",
            HslColor.with(
                HslColorComponent.hue(359f),
                HslColorComponent.saturation(0.0f),
                HslColorComponent.lightness(0.25f)
            ).set(HslColorComponent.alpha(0.5f)
            )
        );
    }

    @Test
    public void testParseHsv() {
        this.parseStringAndCheck(
            "hsv(359, 0%, 25%)",
            HsvColor.with(
                HsvColorComponent.hue(359f),
                HsvColorComponent.saturation(0.0f),
                HsvColorComponent.value(0.25f)
            )
        );
    }

    @Test
    public void testParseRgb() {
        this.parseStringAndCheck(
            "rgb(12,34,56)",
            RgbColor.with(
                RgbColorComponent.red((byte) 12),
                RgbColorComponent.green((byte) 34),
                RgbColorComponent.blue((byte) 56)
            )
        );
    }

    @Test
    public void testParseWebColorName() {
        this.parseStringAndCheck(
            "red",
            Color.parse("#ff0000")
        );
    }

    @Override
    public Color parseString(final String text) {
        return Color.parse(text);
    }

    @Override
    public Class<? extends RuntimeException> parseStringFailedExpected(final Class<? extends RuntimeException> expected) {
        return expected;
    }

    @Override
    public RuntimeException parseStringFailedExpected(final RuntimeException expected) {
        return expected;
    }

    // unmarshall.......................................................................................................

    @Test
    public void testJsonNodeUnmarshallInvalidStringFails() {
        this.unmarshallFails("\"abc\"");
    }

    @Test
    public void testJsonNodeUnmarshallColor() {
        final RgbColor color = RgbColor.fromRgb0(0x123456);
        this.unmarshallAndCheck(color.marshall(this.marshallContext()), color);
    }

    @Test
    public void testJsonNodeUnmarshallHsl() {
        final HslColor hsl = HslColor.with(HslColorComponent.hue(99),
            HslColorComponent.saturation(0.25f),
            HslColorComponent.lightness(0.75f));
        this.unmarshallAndCheck(hsl.marshall(this.marshallContext()), hsl);
    }

    @Test
    public void testJsonNodeUnmarshallHsv() {
        final HsvColor hsv = HsvColor.with(HsvColorComponent.hue(99),
            HsvColorComponent.saturation(0.25f),
            HsvColorComponent.value(0.75f));
        this.unmarshallAndCheck(hsv.marshall(this.marshallContext()), hsv);
    }

    @Override
    public Color unmarshall(final JsonNode from,
                            final JsonNodeUnmarshallContext context) {
        return Color.unmarshall(from, context);
    }

    @Override
    public Color createJsonNodeMarshallingValue() {
        return RgbColor.fromArgb0(0x123456);
    }

    // Class............................................................................................................

    @Override
    public Class<Color> type() {
        return Color.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
