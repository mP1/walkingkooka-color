/*
 * Copyright 2020 Miroslav Pokorny (github.com/mP1)
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

package walkingkooka.color.convert;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.Either;
import walkingkooka.collect.list.Lists;
import walkingkooka.color.Color;
import walkingkooka.color.HslColor;
import walkingkooka.color.HsvColor;
import walkingkooka.color.RgbColor;
import walkingkooka.color.WebColorName;
import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.ConverterTesting;
import walkingkooka.convert.Converters;
import walkingkooka.convert.FakeConverterContext;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.PublicStaticHelperTesting;

import java.lang.reflect.Method;

public final class ColorConvertersTest implements ConverterTesting,
    PublicStaticHelperTesting<ColorConverters> {

    @Test
    public void testConvertIntegerToRgbColor() {
        final RgbColor color = Color.parseRgb("#123456");

        this.convertAndCheck(
            color.value(),
            RgbColor.class,
            color
        );
    }

    @Test
    public void testConvertStringToColorWithHashRgb6() {
        final String text = "#123456";
        this.convertAndCheck(
            text,
            Color.class,
            Color.parse(text)
        );
    }

    @Test
    public void testConvertStringToHslColor() {
        final HslColor color = Color.BLACK.toHsl();

        this.convertAndCheck(
            color.toString(),
            HslColor.class,
            color
        );
    }

    @Test
    public void testConvertStringToHsvColor() {
        final HsvColor color = Color.BLACK.toHsv();

        this.convertAndCheck(
            color.toString(),
            HsvColor.class,
            color
        );
    }

    @Test
    public void testConvertStringToRgbColor() {
        this.convertAndCheck(
            "#123456",
            RgbColor.class,
            Color.parseRgb("#123456")
        );
    }

    @Test
    public void testConvertWebColorNameToRgbColor() {
        final WebColorName webColorName = WebColorName.RED;

        this.convertAndCheck(
            webColorName,
            RgbColor.class,
            webColorName.color()
        );
    }

    @Test
    public void testConvertRgbColorToColor() {
        final Color color = Color.BLACK;
        this.convertAndCheck(
            color,
            Color.class,
            color
        );
    }

    @Test
    public void testConvertRgbColorToHslColor() {
        final Color color = Color.BLACK;
        this.convertAndCheck(
            color,
            HslColor.class,
            color.toHsl()
        );
    }

    @Test
    public void testConvertRgbColorToHsvColor() {
        final Color color = Color.BLACK;
        this.convertAndCheck(
            color,
            HsvColor.class,
            color.toHsv()
        );
    }

    @Test
    public void testConvertRgbColorToRgbColor() {
        final RgbColor rgb = Color.parseRgb("#123456");

        this.convertAndCheck(
            rgb,
            RgbColor.class,
            rgb
        );
    }

    @Test
    public void testConvertHsvColorToColor() {
        final HsvColor hsv = Color.parseRgb("#123456")
            .toHsv();

        this.convertAndCheck(
            hsv,
            Color.class,
            hsv
        );
    }

    @Test
    public void testConvertHsvColorToRgbColor() {
        final RgbColor rgb = Color.parseRgb("#123456");

        this.convertAndCheck(
            rgb.toHsv(),
            RgbColor.class,
            rgb.toHsv().toRgb()
        );
    }

    private void convertAndCheck(final Object value,
                                 final Class<?> expected) {
        this.convertAndCheck(
            value,
            expected.getClass(),
            Cast.to(expected)
        );
    }

    private <T> void convertAndCheck(final Object value,
                                     final Class<T> type,
                                     final T expected) {
        this.convertAndCheck(
            Converters.collection(
                Lists.of(
                    new Converter<ConverterContext>() {
                        @Override
                        public boolean canConvert(final Object value,
                                                  final Class<?> type,
                                                  final ConverterContext context) {
                            return (value instanceof Integer || value instanceof String) && Integer.class == type;
                        }

                        @Override
                        public <T> Either<T, String> convert(final Object value,
                                                             final Class<T> type,
                                                             final ConverterContext context) {
                            if (value instanceof Integer && Integer.class == type) {
                                return this.successfulConversion(
                                    type.cast(value),
                                    type
                                );
                            }
                            if (value instanceof Integer && String.class == type) {
                                return this.successfulConversion(
                                    Integer.parseInt(
                                        ((String) value)
                                            .substring(1)),
                                    type
                                );
                            }

                            return this.failConversion(value, type);
                        }
                    },
                    Converters.characterOrCharSequenceOrHasTextOrStringToCharacterOrCharSequenceOrString(),
                    Converters.numberToNumber(),
                    ColorConverters.colorToColor(),
                    ColorConverters.numberToColor(),
                    ColorConverters.textToColor()
                )
            ),
            value,
            type,
            new FakeConverterContext() {
                @Override
                public boolean canConvert(final Object value,
                                          final Class<?> type) {
                    return this.converter.canConvert(
                        value,
                        type,
                        this
                    );
                }

                @Override
                public <T> Either<T, String> convert(final Object value,
                                                     final Class<T> type) {
                    return this.converter.convert(
                        value,
                        type,
                        this
                    );
                }

                private final Converter<FakeConverterContext> converter = Converters.collection(
                    Lists.of(
                        Converters.characterOrCharSequenceOrHasTextOrStringToCharacterOrCharSequenceOrString(),
                        Converters.numberToNumber(),
                        ColorConverters.textToColor()
                    )
                );
            },
            expected
        );
    }

    // class............................................................................................................

    @Override
    public Class<ColorConverters> type() {
        return ColorConverters.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }

    @Override
    public boolean canHavePublicTypes(final Method method) {
        return false;
    }
}
