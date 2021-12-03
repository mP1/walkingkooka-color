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

package walkingkooka.color.parser;

import org.junit.jupiter.api.Test;
import walkingkooka.color.Color;
import walkingkooka.color.HslColor;
import walkingkooka.color.HslColorComponent;
import walkingkooka.color.HsvColor;
import walkingkooka.color.HsvColorComponent;
import walkingkooka.color.RgbColor;
import walkingkooka.reflect.JavaVisibility;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ColorFunctionTransformerTest extends ColorFunctionTestCase<ColorFunctionTransformer> {

    @Test
    public void testFunctionNameInvalidFails() {
        assertThrows(IllegalArgumentException.class, () -> ColorFunctionTransformer.functionName(ColorFunctionParserToken.functionName("unknown", "unknown")));
    }

    // hsl..............................................................................................................

    @Test
    public void testHslNumberNumberNumber() {
        this.colorCheck("hsl",
                hsl(359, 0.25f, 0.5f),
                number(359),
                number(0.25),
                number(0.5));
    }

    @Test
    public void testHslNumberNumberNumberNumber() {
        this.colorCheck("hsl",
                hsl(359, 0.25f, 0.5f, 0.75f),
                number(359),
                number(0.25),
                number(0.5),
                number(0.75));
    }

    @Test
    public void testHslNumberNumberNumberNumber2() {
        this.colorCheck("hsl",
                hsl(359, 0.25f, 0.5f, 0.75f),
                number(359),
                number(0.25),
                number(0.5),
                number(0.75));
    }

    @Test
    public void testHslNumberNumberNumberPercentage() {
        this.colorCheck("hsl",
                hsl(359, 0.25f, 0.5f, 0.75f),
                number(359),
                number(0.25),
                number(0.5),
                percentage(75));
    }

    @Test
    public void testHslPercentagePercentagePercentagePercentage() {
        this.colorCheck("hsl",
                hsl(359, 0.25f, 0.5f, 0.75f),
                number(359),
                percentage(25),
                percentage(50),
                percentage(75));
    }

    @Test
    public void testHslPercentagePercentagePercentagePercentage2() {
        this.colorCheck("hsl",
                hsl(359, 0.25f, 0.5f, 0.75f),
                number(359),
                percentage(25),
                percentage(50),
                percentage(75));
    }

    @Test
    public void testHslaNumberNumberNumber() {
        this.colorCheck("hsla",
                hsl(359, 0.25f, 0.5f),
                number(359),
                number(0.25),
                number(0.5));
    }

    private HslColor hsl(final float hue,
                         final float saturation,
                         final float lightness) {
        return Color.hsl(HslColorComponent.hue(hue),
                HslColorComponent.saturation(saturation),
                HslColorComponent.lightness(lightness));
    }

    private HslColor hsl(final float hue,
                         final float saturation,
                         final float lightness,
                         final float alpha) {
        return hsl(hue, saturation, lightness).set(HslColorComponent.alpha(alpha));
    }

    // hsv..............................................................................................................

    @Test
    public void testHsvNumberNumberNumber() {
        this.colorCheck("hsv",
                hsv(359, 0.25f, 0.5f),
                number(359),
                number(0.25),
                number(0.5));
    }

    @Test
    public void testHsvNumberNumberNumberNumber() {
        this.colorCheck("hsv",
                hsv(359, 0.25f, 0.5f, 0.75f),
                number(359),
                number(0.25),
                number(0.5),
                number(0.75));
    }

    @Test
    public void testHsvNumberNumberNumberNumber2() {
        this.colorCheck("hsv",
                hsv(359, 0.25f, 0.5f, 0.75f),
                number(359),
                number(0.25),
                number(0.5),
                number(0.75));
    }

    @Test
    public void testHsvNumberNumberNumberPercentage() {
        this.colorCheck("hsv",
                hsv(359, 0.25f, 0.5f, 0.75f),
                number(359),
                number(0.25),
                number(0.5),
                percentage(75));
    }

    @Test
    public void testHsvPercentagePercentagePercentagePercentage() {
        this.colorCheck("hsv",
                hsv(359, 0.25f, 0.5f, 0.75f),
                number(359),
                percentage(25),
                percentage(50),
                percentage(75));
    }

    @Test
    public void testHsvPercentagePercentagePercentagePercentage2() {
        this.colorCheck("hsv",
                hsv(359, 0.25f, 0.5f, 0.75f),
                number(359),
                percentage(25),
                percentage(50),
                percentage(75));
    }

    @Test
    public void testHsvaNumberNumberNumber() {
        this.colorCheck("hsva",
                hsv(359, 0.25f, 0.5f),
                number(359),
                number(0.25),
                number(0.5));
    }

    private HsvColor hsv(final float hue,
                         final float saturation,
                         final float value) {
        return Color.hsv(HsvColorComponent.hue(hue),
                HsvColorComponent.saturation(saturation),
                HsvColorComponent.value(value));
    }

    private HsvColor hsv(final float hue,
                         final float saturation,
                         final float value,
                         final float alpha) {
        return hsv(hue, saturation, value).set(HsvColorComponent.alpha(alpha));
    }

    // rgb..............................................................................................................

    @Test
    public void testRgb() {
        this.colorCheck("rgb",
                RgbColor.parseRgb("#123456"),
                number(0x12),
                number(0x34),
                number(0x56));
    }

    @Test
    public void testRgbWithAlpha() {
        this.colorCheck("rgb",
                RgbColor.parseRgb("#1234567f"),
                number(0x12),
                number(0x34),
                number(0x56),
                number(0x7f));
    }

    @Test
    public void testRgbWithAlphaPercentage() {
        this.colorCheck("rgb",
                RgbColor.parseRgb("#12345680"),
                number(0x12),
                number(0x34),
                number(0x56),
                percentage(50));
    }

    @Test
    public void testRgba() {
        this.colorCheck("rgba",
                RgbColor.parseRgb("#123456"),
                number(0x12),
                number(0x34),
                number(0x56));
    }

    @Test
    public void testRgbaWithAlpha() {
        this.colorCheck("rgba",
                RgbColor.parseRgb("#1234567f"),
                number(0x12),
                number(0x34),
                number(0x56),
                number(0x7f));
    }

    @Test
    public void testRgbaWithAlphaPercentage() {
        this.colorCheck("rgba",
                RgbColor.parseRgb("#12345680"),
                number(0x12),
                number(0x34),
                number(0x56),
                percentage(50));
    }

    private ColorFunctionParserToken number(final double value) {
        return ColorFunctionParserToken.number(value, String.valueOf(value));
    }

    private ColorFunctionParserToken percentage(final double value) {
        return ColorFunctionParserToken.percentage(value, value + "%");
    }

    private void colorCheck(final String name,
                            final Color color,
                            final ColorFunctionParserToken... values) {
        this.checkEquals(color,
                ColorFunctionTransformer.functionName(ColorFunctionFunctionNameParserToken.with(name, name))
                        .color(
                                values[0],
                                values[1],
                                values[2],
                                Optional.ofNullable(values.length == 4 ? values[3] : null)),
                () -> name + " " + color);
    }

    // ClassTesting.....................................................................................................

    @Override
    public Class<ColorFunctionTransformer> type() {
        return ColorFunctionTransformer.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }

    // TypeNameTesting..................................................................................................

    @Override
    public String typeNameSuffix() {
        return "";
    }
}
