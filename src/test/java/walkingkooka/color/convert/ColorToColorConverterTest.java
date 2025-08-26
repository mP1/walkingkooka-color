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
import walkingkooka.convert.ConverterTesting2;
import walkingkooka.convert.Converters;
import walkingkooka.convert.FakeConverterContext;

public final class ColorToColorConverterTest implements ConverterTesting2<ColorToColorConverter<FakeConverterContext>, FakeConverterContext> {

    @Test
    public void testConvertInvalidStringToColorFails() {
        this.convertFails(
            "Invalid!",
            Color.class
        );
    }

    @Test
    public void testConvertStringToColorWithHashRgb6Fails() {
        this.convertFails(
            "#123456",
            Color.class
        );
    }

    @Test
    public void testConvertStringToHslColorFails() {
        this.convertFails(
            Color.BLACK.toHsl().toString(),
            HslColor.class
        );
    }

    @Test
    public void testConvertStringToHsvColorFails() {
        this.convertFails(
            Color.BLACK.toHsv().toString(),
            HsvColor.class
        );
    }

    @Test
    public void testConvertStringHash6ToRgbColorFails() {
        this.convertFails(
            "#123456",
            RgbColor.class
        );
    }

    @Test
    public void testConvertWebColorNameToRgbColorFails() {
        this.convertFails(
            WebColorName.RED,
            RgbColor.class
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
        this.convertAndCheck(
            Color.parseRgb("#123456"),
            RgbColor.class
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

    @Override
    public ColorToColorConverter<FakeConverterContext> createConverter() {
        return ColorToColorConverter.instance();
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
            ColorToColorConverter.instance(),
            "Color to Color"
        );
    }

    // class............................................................................................................

    @Override
    public Class<ColorToColorConverter<FakeConverterContext>> type() {
        return Cast.to(ColorToColorConverter.class);
    }
}
