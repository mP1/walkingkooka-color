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
import walkingkooka.convert.FakeConverterContext;

public final class ColorToRgbColorConverterTest implements ConverterTesting2<ColorToRgbColorConverter<FakeConverterContext>, FakeConverterContext> {

    @Test
    public void testConvertStringToColorFails() {
        this.convertFails(
            "#123456",
            Color.class
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
    public void testConvertRgbColorToRgbColor() {
        this.convertAndCheck(
            Color.parseRgb("#123456"),
            RgbColor.class
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

    @Override
    public ColorToRgbColorConverter<FakeConverterContext> createConverter() {
        return ColorToRgbColorConverter.instance();
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
                    Converters.characterOrCharSequenceOrHasTextOrStringToCharacterOrCharSequenceOrString(),
                    ColorConverters.textToColor()
                )
            );
        };
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            ColorToRgbColorConverter.instance(),
            "Color to RgbColor"
        );
    }

    // class............................................................................................................

    @Override
    public Class<ColorToRgbColorConverter<FakeConverterContext>> type() {
        return Cast.to(ColorToRgbColorConverter.class);
    }
}
