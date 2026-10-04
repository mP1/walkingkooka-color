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
import walkingkooka.collect.set.Sets;
import walkingkooka.reflect.ConstantsTesting;
import walkingkooka.reflect.PublicClassTesting;
import walkingkooka.test.ParseStringTesting;
import walkingkooka.text.printer.TreePrintableTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallerTesting;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.Set;

public final class ColorTest implements PublicClassTesting<Color>,
    JsonNodeMarshallerTesting<Color>,
    ParseStringTesting<Color>,
    TreePrintableTesting,
    ConstantsTesting<Color> {

    // constants........................................................................................................

    @Test
    public void testConstantsBlackAndWhite() {
        this.checkNotEquals(
            Color.BLACK,
            Color.WHITE
        );
    }

    @Test
    public void testConstantsRgbBlackAndHslBlack() {
        this.checkNotEquals(
            Color.BLACK,
            Color.BLACK_HSL
        );
    }

    @Test
    public void testConstantsRgbBlackAndHsvBlack() {
        this.checkNotEquals(
            Color.BLACK,
            Color.BLACK_HSV
        );
    }

    @Test
    public void testConstantsHslBlackAndHsvBlack() {
        this.checkNotEquals(
            Color.BLACK_HSL,
            Color.BLACK_HSV
        );
    }

    @Test
    public void testConstantsRgbBlackAndRgbBlackHalf() {
        this.checkNotEquals(
            Color.BLACK,
            Color.BLACK_50_ALPHA
        );
    }

    @Test
    public void testConstantsHslBlackAndRgbBlackHalf() {
        this.checkNotEquals(
            Color.BLACK_HSL,
            Color.BLACK_HSL_50_ALPHA
        );
    }

    @Test
    public void testConstantsHsvBlackAndRgbBlackHalf() {
        this.checkNotEquals(
            Color.BLACK_HSV,
            Color.BLACK_HSV_50_ALPHA
        );
    }

    @Override
    public Set<Color> intentionalDuplicateConstants() {
        return Sets.empty();
    }

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

    // isHslColorClass..................................................................................................

    @Test
    public void testIsHslColorClassWithNull() {
        this.isHslColorClassAndCheck(
            null,
            false
        );
    }

    @Test
    public void testIsHslColorClassWithThis() {
        this.isHslColorClassAndCheck(
            this.getClass(),
            false
        );
    }

    @Test
    public void testIsHslColorClassWithColor() {
        this.isHslColorClassAndCheck(
            Color.class,
            false
        );
    }

    @Test
    public void testIsHslColorClassWithRgbColor() {
        this.isHslColorClassAndCheck(
            RgbColor.class,
            false
        );
    }

    @Test
    public void testIsHslColorClassWithOpaqueHslColor() {
        this.isHslColorClassAndCheck(
            Color.parseRgb("#123")
                .toHsl()
                .getClass(),
            true
        );
    }

    @Test
    public void testIsHslColorClassWithAlphaHslColor() {
        this.isHslColorClassAndCheck(
            Color.parseRgb("#112233ff")
                .toHsl()
                .getClass(),
            true
        );
    }

    @Test
    public void testIsHslColorClassWithHslColor() {
        this.isHslColorClassAndCheck(
            HslColor.class,
            true
        );
    }

    private void isHslColorClassAndCheck(final Class<?> type,
                                         final boolean expected) {
        this.checkEquals(
            Color.isHslColorClass(type),
            expected
        );
    }

    // isHsvColorClass..................................................................................................

    @Test
    public void testIsHsvColorClassWithNull() {
        this.isHsvColorClassAndCheck(
            null,
            false
        );
    }

    @Test
    public void testIsHsvColorClassWithThis() {
        this.isHsvColorClassAndCheck(
            this.getClass(),
            false
        );
    }

    @Test
    public void testIsHsvColorClassWithColor() {
        this.isHsvColorClassAndCheck(
            Color.class,
            false
        );
    }

    @Test
    public void testIsHsvColorClassWithRgbColor() {
        this.isHsvColorClassAndCheck(
            RgbColor.class,
            false
        );
    }

    @Test
    public void testIsHsvColorClassWithHslColor() {
        this.isHsvColorClassAndCheck(
            Color.BLACK.toHsl().getClass(),
            false
        );
    }

    @Test
    public void testIsHsvColorClassWithOpaqueHsvColor() {
        this.isHsvColorClassAndCheck(
            Color.parseRgb("#123")
                .toHsv()
                .getClass(),
            true
        );
    }

    @Test
    public void testIsHsvColorClassWithAlphaHsvColor() {
        this.isHsvColorClassAndCheck(
            Color.parseRgb("#112233ff")
                .toHsv()
                .getClass(),
            true
        );
    }

    @Test
    public void testIsHsvColorClassWithHsvColor() {
        this.isHsvColorClassAndCheck(
            HsvColor.class,
            true
        );
    }

    private void isHsvColorClassAndCheck(final Class<?> type,
                                         final boolean expected) {
        this.checkEquals(
            Color.isHsvColorClass(type),
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
    public void testParseIndexedColor() {
        this.parseStringAndCheck(
            "123",
            Color.indexed(123)
        );
    }

    @Test
    public void testParseNamedColor() {
        this.parseStringAndCheck(
            "\"Red123\"",
            Color.named("Red123")
        );
    }

    @Test
    public void testParseLettersFails() {
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
    public void testJsonNodeUnmarshallIndexedColor() {
        final IndexedColor indexedColor = Color.indexed(123);

        this.unmarshallAndCheck(
            indexedColor.marshall(JSON_NODE_MARSHALL_CONTEXT),
            indexedColor
        );
    }

    @Test
    public void testJsonNodeUnmarshallNamedColor() {
        final NamedColor namedColor = Color.named("HelloWorld");

        this.unmarshallAndCheck(
            namedColor.marshall(JSON_NODE_MARSHALL_CONTEXT),
            namedColor
        );
    }

    @Test
    public void testJsonNodeUnmarshallRgbColor() {
        final RgbColor color = RgbColor.fromRgb0(0x123456);
        this.unmarshallAndCheck(
            color.marshall(JSON_NODE_MARSHALL_CONTEXT),
            color
        );
    }

    @Test
    public void testJsonNodeUnmarshallHsl() {
        final HslColor hsl = HslColor.with(HslColorComponent.hue(99),
            HslColorComponent.saturation(0.25f),
            HslColorComponent.lightness(0.75f));
        this.unmarshallAndCheck(
            hsl.marshall(JSON_NODE_MARSHALL_CONTEXT),
            hsl
        );
    }

    @Test
    public void testJsonNodeUnmarshallHsv() {
        final HsvColor hsv = HsvColor.with(HsvColorComponent.hue(99),
            HsvColorComponent.saturation(0.25f),
            HsvColorComponent.value(0.75f));
        this.unmarshallAndCheck(
            hsv.marshall(JSON_NODE_MARSHALL_CONTEXT),
            hsv
        );
    }

    @Override
    public Color unmarshall(final JsonNode from,
                            final JsonNodeUnmarshallContext context) {
        return Color.unmarshallColor(
            from,
            context
        );
    }

    @Override
    public Color createJsonNodeMarshallingValue() {
        return Color.fromRgb(0x123456);
    }

    // TreePrintable....................................................................................................

    @Test
    public void testTreePrintIndexedColor() {
        this.treePrintAndCheck(
            Color.indexed(123),
            "123\n"
        );
    }

    @Test
    public void testTreePrintRgbColor2() {
        this.treePrintAndCheck(
            Color.parse("#123"),
            "#123\n"
        );
    }

    // Class............................................................................................................

    @Override
    public Class<Color> type() {
        return Color.class;
    }
}
