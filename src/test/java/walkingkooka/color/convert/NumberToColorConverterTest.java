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
import walkingkooka.color.RgbColorComponent;
import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterTesting2;
import walkingkooka.convert.Converters;
import walkingkooka.convert.FakeConverterContext;

public final class NumberToColorConverterTest implements ConverterTesting2<NumberToColorConverter<FakeConverterContext>, FakeConverterContext> {

    @Test
    public void testConvertStringToColorFails() {
        this.convertFails(
            "#123456",
            Color.class
        );
    }

    private final static byte BYTE = 123;

    @Test
    public void testConvertAlphaRgbColorComponentFails() {
        final Object value = RgbColorComponent.alpha(BYTE);

        this.convertFails(
            value,
            value.getClass()
        );
    }

    @Test
    public void testConvertRedRgbColorComponentFails() {
        final Object value = RgbColorComponent.red(BYTE);

        this.convertFails(
            value,
            value.getClass()
        );
    }

    @Test
    public void testConvertGreenRgbColorComponentFails() {
        final Object value = RgbColorComponent.green(BYTE);

        this.convertFails(
            value,
            value.getClass()
        );
    }

    @Test
    public void testConvertBlueRgbColorComponentFails() {
        final Object value = RgbColorComponent.blue(BYTE);

        this.convertFails(
            value,
            value.getClass()
        );
    }

    @Test
    public void testConvertByteToAlphaRgbColorComponent() {
        this.convertAndCheck(
            BYTE,
            RgbColorComponent.alpha(BYTE)
        );
    }

    @Test
    public void testConvertByteToRedRgbColorComponent() {
        this.convertAndCheck(
            BYTE,
            RgbColorComponent.red(BYTE)
        );
    }

    @Test
    public void testConvertByteToGreenRgbColorComponent() {
        this.convertAndCheck(
            BYTE,
            RgbColorComponent.green(BYTE)
        );
    }

    @Test
    public void testConvertByteToBlueRgbColorComponent() {
        this.convertAndCheck(
            BYTE,
            RgbColorComponent.blue(BYTE)
        );
    }

    private final static int INTEGER = 0x123456;

    @Test
    public void testConvertIntegerToHslColorFails() {
        this.convertFails(
            INTEGER,
            HslColor.class
        );
    }

    @Test
    public void testConvertIntegerToHsvColorFails() {
        this.convertFails(
            INTEGER,
            HsvColor.class
        );
    }

    @Test
    public void testConvertIntegerToOpaqueRgbColor() {
        this.convertAndCheck(
            INTEGER,
            Color.fromRgb(INTEGER)
        );
    }

    @Test
    public void testConvertIntegerToRgbColor() {
        this.convertAndCheck(
            INTEGER,
            RgbColor.class,
            Color.fromRgb(INTEGER)
        );
    }

    @Test
    public void testConvertIntegerToAlphaRgbColor() {
        this.convertAndCheck(
            INTEGER,
            Color.fromArgb(INTEGER)
        );
    }

    @Override
    public NumberToColorConverter<FakeConverterContext> createConverter() {
        return NumberToColorConverter.instance();
    }

    @Override
    public FakeConverterContext createContext() {
        return new FakeConverterContext() {
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
                    Converters.numberToNumber()
                )
            );
        };
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            NumberToColorConverter.instance(),
            "Number to Color"
        );
    }

    // class............................................................................................................

    @Override
    public Class<NumberToColorConverter<FakeConverterContext>> type() {
        return Cast.to(NumberToColorConverter.class);
    }
}
