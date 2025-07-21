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
import walkingkooka.color.RgbColor;
import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterTesting2;
import walkingkooka.convert.Converters;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.convert.ExpressionNumberConverterContext;
import walkingkooka.tree.expression.convert.ExpressionNumberConverters;
import walkingkooka.tree.expression.convert.FakeExpressionNumberConverterContext;

public final class ColorToNumberConverterTest implements ConverterTesting2<ColorToNumberConverter<ExpressionNumberConverterContext>, ExpressionNumberConverterContext> {

    @Test
    public void testConvertStringToNumberFails() {
        this.convertFails(
            "#123456",
            Color.class
        );
    }

    @Test
    public void testConvertRgbColorToByteFails() {
        this.convertFails(
            Color.parseRgb("#123"),
            Short.class
        );
    }

    @Test
    public void testConvertHslColorToIntegerFails() {
        this.convertFails(
            Color.parseRgb("#123").toHsl(),
            Integer.class
        );
    }

    @Test
    public void testConvertHsvColorToIntegerFails() {
        this.convertFails(
            Color.parseRgb("#123").toHsv(),
            Integer.class
        );
    }

    @Test
    public void testConvertRgbColorToNumber() {
        final RgbColor color = Color.parseRgb("#123456");

        this.convertAndCheck(
            color,
            Number.class,
            color.value()
        );
    }

    @Test
    public void testConvertRgbColorToInteger() {
        final RgbColor color = Color.parseRgb("#123456");

        this.convertAndCheck(
            color,
            Integer.class,
            color.value()
        );
    }

    @Test
    public void testConvertRgbColorToLong() {
        final RgbColor color = Color.parseRgb("#123456");

        this.convertAndCheck(
            color,
            Long.class,
            (long) color.value()
        );
    }

    @Test
    public void testConvertRgbColorToFloat() {
        final RgbColor color = Color.parseRgb("#123456");

        this.convertAndCheck(
            color,
            Float.class,
            (float) color.value()
        );
    }

    @Test
    public void testConvertRgbColorToDouble() {
        final RgbColor color = Color.parseRgb("#123456");

        this.convertAndCheck(
            color,
            Double.class,
            (double) color.value()
        );
    }

    @Test
    public void testConvertRedRgbColorComponentToByte() {
        final RgbColor color = Color.parseRgb("#123456");

        this.convertAndCheck(
            color.red(),
            Byte.class,
            color.red().value()
        );
    }

    @Test
    public void testConvertRedRgbColorComponentToShort() {
        final RgbColor color = Color.parseRgb("#123456");

        this.convertAndCheck(
            color.red(),
            Short.class,
            (short) color.red().value()
        );
    }

    @Test
    public void testConvertRedRgbColorComponentToLong() {
        final RgbColor color = Color.parseRgb("#123456");

        this.convertAndCheck(
            color.red(),
            Long.class,
            (long) color.red().value()
        );
    }

    @Test
    public void testConvertGreenRgbColorComponentToLong() {
        final RgbColor color = Color.parseRgb("#123456");

        this.convertAndCheck(
            color.green(),
            Long.class,
            (long) color.green().value()
        );
    }

    @Test
    public void testConvertBlueRgbColorComponentToLong() {
        final RgbColor color = Color.parseRgb("#123456");

        this.convertAndCheck(
            color.blue(),
            Long.class,
            (long) color.blue().value()
        );
    }

    @Test
    public void testConvertAlphaRgbColorComponentToLong() {
        final RgbColor color = Color.parseRgb("#12345678");

        this.convertAndCheck(
            color.alpha(),
            Long.class,
            (long) color.alpha().value()
        );
    }

    private final static ExpressionNumberKind KIND = ExpressionNumberKind.DEFAULT;

    @Test
    public void testConvertRgbColorToExpressionNumber() {
        final RgbColor color = Color.parseRgb("#123456");

        this.convertAndCheck(
            color,
            KIND.create(0).getClass(),
            Cast.to(
                KIND.create(color.value())
            )
        );
    }

    @Override
    public ColorToNumberConverter<ExpressionNumberConverterContext> createConverter() {
        return ColorToNumberConverter.instance();
    }

    @Override
    public ExpressionNumberConverterContext createContext() {
        return new FakeExpressionNumberConverterContext() {

            @Override
            public ExpressionNumberKind expressionNumberKind() {
                return KIND;
            }

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

            private final Converter<ExpressionNumberConverterContext> converter = Converters.collection(
                Lists.of(
                    ExpressionNumberConverters.numberToNumber()
                )
            );
        };
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            ColorToNumberConverter.instance(),
            "Color to Number"
        );
    }

    // class............................................................................................................

    @Override
    public Class<ColorToNumberConverter<ExpressionNumberConverterContext>> type() {
        return Cast.to(ColorToNumberConverter.class);
    }
}
