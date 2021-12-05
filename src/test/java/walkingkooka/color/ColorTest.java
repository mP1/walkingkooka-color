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

    // parse...........................................................................................................

    @Test
    public void testParseFails() {
        this.parseStringFails("abc", IllegalArgumentException.class);
    }

    @Test
    public void testParseHsl() {
        this.parseStringAndCheck("hsl(359, 0%, 25%)",
                walkingkooka.color.HslColor.with(walkingkooka.color.HslColorComponent.hue(359f),
                        walkingkooka.color.HslColorComponent.saturation(0.0f),
                        walkingkooka.color.HslColorComponent.lightness(0.25f)));
    }

    @Test
    public void testParseHsla() {
        this.parseStringAndCheck("hsla(359, 0%, 25%, 50%)",
                walkingkooka.color.HslColor.with(walkingkooka.color.HslColorComponent.hue(359f),
                        walkingkooka.color.HslColorComponent.saturation(0.0f),
                        walkingkooka.color.HslColorComponent.lightness(0.25f))
                        .set(walkingkooka.color.HslColorComponent.alpha(0.5f)));
    }

    @Test
    public void testParseHsv() {
        this.parseStringAndCheck("hsv(359, 0%, 25%)",
                walkingkooka.color.HsvColor.with(walkingkooka.color.HsvColorComponent.hue(359f),
                        walkingkooka.color.HsvColorComponent.saturation(0.0f),
                        walkingkooka.color.HsvColorComponent.value(0.25f)));
    }

    @Test
    public void testParseRgb() {
        this.parseStringAndCheck("rgb(12,34,56)",
                walkingkooka.color.RgbColor.with(walkingkooka.color.RgbColorComponent.red((byte) 12),
                        walkingkooka.color.RgbColorComponent.green((byte) 34),
                        walkingkooka.color.RgbColorComponent.blue((byte) 56)));
    }

    // unmarshall.....................................................................................................

    @Test
    public void testJsonNodeUnmarshallInvalidStringFails() {
        this.unmarshallFails("\"abc\"");
    }

    @Test
    public void testJsonNodeUnmarshallColor() {
        final walkingkooka.color.RgbColor color = walkingkooka.color.RgbColor.fromRgb0(0x123456);
        this.unmarshallAndCheck(color.marshall(this.marshallContext()), color);
    }

    @Test
    public void testJsonNodeUnmarshallHsl() {
        final walkingkooka.color.HslColor hsl = walkingkooka.color.HslColor.with(walkingkooka.color.HslColorComponent.hue(99),
                walkingkooka.color.HslColorComponent.saturation(0.25f),
                walkingkooka.color.HslColorComponent.lightness(0.75f));
        this.unmarshallAndCheck(hsl.marshall(this.marshallContext()), hsl);
    }

    @Test
    public void testJsonNodeUnmarshallHsv() {
        final walkingkooka.color.HsvColor hsv = walkingkooka.color.HsvColor.with(walkingkooka.color.HsvColorComponent.hue(99),
                walkingkooka.color.HsvColorComponent.saturation(0.25f),
                walkingkooka.color.HsvColorComponent.value(0.75f));
        this.unmarshallAndCheck(hsv.marshall(this.marshallContext()), hsv);
    }

    // HasJsonNode.....................................................................................................

    @Override
    public Color unmarshall(final JsonNode from,
                              final JsonNodeUnmarshallContext context) {
        return Color.unmarshall(from, context);
    }

    @Override
    public Color createJsonNodeMarshallingValue() {
        return walkingkooka.color.RgbColor.fromArgb0(0x123456);
    }

    // ParseStringTesting...............................................................................................

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

    // ClassTesting....................................................................................................

    @Override
    public Class<Color> type() {
        return Color.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
