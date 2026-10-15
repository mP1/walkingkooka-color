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
import walkingkooka.color.IndexedColor;
import walkingkooka.color.NamedColor;
import walkingkooka.color.RgbColor;
import walkingkooka.color.WebColorName;
import walkingkooka.convert.Converter;
import walkingkooka.convert.Converters;

public final class ColorConverterToColorTest extends ColorConverterColorTestCase<ColorConverterToColor<FakeColorConverterContext>, FakeColorConverterContext> {

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
    public void testConvertNumberToIndexedColorFails() {
        this.convertFails(
            123,
            IndexedColor.class
        );
    }

    @Test
    public void testConvertStringToNamedColorFails() {
        this.convertFails(
            "RED",
            NamedColor.class
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
    public void testConvertWebColorNameToRgbColor() {
        this.convertAndCheck(
            WebColorName.RED,
            RgbColor.class,
            WebColorName.RED.color()
                .toRgb()
        );
    }

    @Test
    public void testConvertIndexedColorToColor() {
        final IndexedColor indexedColor = Color.indexed(123);
        this.convertAndCheck(
            indexedColor,
            Color.class,
            indexedColor
        );
    }

    @Test
    public void testConvertIndexedColorToIndexedColor() {
        final IndexedColor indexedColor = Color.indexed(123);
        this.convertAndCheck(
            indexedColor,
            IndexedColor.class,
            indexedColor
        );
    }

    @Test
    public void testConvertNamedColorToColor() {
        final NamedColor namedColor = Color.named("RED");
        this.convertAndCheck(
            namedColor,
            Color.class,
            namedColor
        );
    }

    @Test
    public void testConvertNamedColorToNamedColor() {
        final NamedColor namedColor = Color.named("RED");
        this.convertAndCheck(
            namedColor,
            NamedColor.class,
            namedColor
        );
    }

    @Test
    public void testConvertRgbColorToIndexedColorFails() {
        this.convertFails(
            Color.BLACK,
            IndexedColor.class
        );
    }

    @Test
    public void testConvertRgbColorToINamedColorFails() {
        this.convertFails(
            Color.BLACK,
            NamedColor.class
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
    public ColorConverterToColor<FakeColorConverterContext> createConverter() {
        return ColorConverterToColor.instance();
    }

    @Override
    public FakeColorConverterContext createContext() {
        return new FakeColorConverterContext() {
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

            private final Converter<FakeColorConverterContext> converter = Converters.collection(
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
            ColorConverterToColor.instance(),
            "to Color"
        );
    }

    // class............................................................................................................

    @Override
    public Class<ColorConverterToColor<FakeColorConverterContext>> type() {
        return Cast.to(ColorConverterToColor.class);
    }
}
