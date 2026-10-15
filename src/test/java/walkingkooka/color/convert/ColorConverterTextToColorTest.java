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
import walkingkooka.color.AlphaRgbColorComponent;
import walkingkooka.color.BlueRgbColorComponent;
import walkingkooka.color.Color;
import walkingkooka.color.ColorComponent;
import walkingkooka.color.GreenRgbColorComponent;
import walkingkooka.color.HslColor;
import walkingkooka.color.HsvColor;
import walkingkooka.color.RedRgbColorComponent;
import walkingkooka.color.RgbColor;
import walkingkooka.color.RgbColorComponent;
import walkingkooka.color.WebColorName;
import walkingkooka.convert.Converter;
import walkingkooka.convert.Converters;

import java.util.List;
import java.util.function.Function;

public final class ColorConverterTextToColorTest extends ColorConverterColorTestCase<ColorConverterTextToColor<FakeColorConverterContext>, FakeColorConverterContext> {

    @Test
    public void testConvertWithInterfaceClassFails() {
        this.convertFails(
            "#123456",
            List.class
        );
    }

    @Test
    public void testConvertWithColorToStringFails() {
        this.convertFails(
            Color.parse("#123456"),
            String.class
        );
    }

    @Test
    public void testConvertWithIndexedColor() {
        this.convertAndCheck4(
            "111",
            Color.class,
            Color.indexed(999)
        );
    }

    @Test
    public void testConvertWithNamedColor() {
        this.convertAndCheck4(
            "\"Red\"",
            Color.class,
            Color.named("Blue")
        );
    }

    @Test
    public void testConvertWithRgbColorStringHash3DigitsAndColor() {
        this.convertAndCheck4(
            "#111",
            Color.class,
            Color.parse("#999")
        );
    }

    @Test
    public void testConvertWithRgbColorStringWebColorNameAndColor() {
        this.convertAndCheck4(
            WebColorName.RED.text(),
            Color.class,
            WebColorName.BLUE.color()
        );
    }

    @Test
    public void testConvertWithHslColorStringAndColor() {
        this.convertAndCheck2(
            "hsl(359,100%,50%)",
            Color.class,
            Color::parse
        );
    }

    @Test
    public void testConvertWithHsvColorStringAndColor() {
        this.convertAndCheck2(
            "hsv(359,100%,50%)",
            Color.class,
            Color::parse
        );
    }

    @Test
    public void testConvertWithStringAndRgbColor() {
        this.convertAndCheck2(
            "#123456",
            RgbColor.class,
            Color::parseRgb
        );
    }

    @Test
    public void testConvertWithStringAndAlphaRgbColor() {
        this.convertAndCheck2(
            "#12345678",
            RgbColor.class,
            Color::parseRgb
        );
    }

    @Test
    public void testConvertWithStringAndHslColor() {
        this.convertAndCheck2(
            "hsl(359,100%,50%)",
            HslColor.class,
            Color::parseHsl
        );
    }

    @Test
    public void testConvertWithStringAndAlphaHslColor() {
        this.convertAndCheck2(
            "hsl(359,100%,50%,100%)",
            HslColor.class,
            Color::parseHsl
        );
    }

    @Test
    public void testConvertWithStringAndHsvColor() {
        this.convertAndCheck2(
            "hsv(359,100%,50%)",
            HsvColor.class,
            Color::parseHsv
        );
    }

    @Test
    public void testConvertWithStringAndAlphaHsvColor() {
        this.convertAndCheck2(
            "hsv(359,100%,50%, 100%)",
            HsvColor.class,
            Color::parseHsv
        );
    }

    @Test
    public void testConvertWithStringNumberToAlphaRgbColorComponent() {
        this.convertAndCheck3(
            "111",
            AlphaRgbColorComponent.class,
            RgbColorComponent::parseAlpha
        );
    }

    @Test
    public void testConvertWithStringNumberToBlueRgbColorComponent() {
        this.convertAndCheck3(
            "22",
            BlueRgbColorComponent.class,
            RgbColorComponent::parseBlue
        );
    }

    @Test
    public void testConvertWithStringNumberToGreenRgbColorComponent() {
        this.convertAndCheck3(
            "33",
            GreenRgbColorComponent.class,
            RgbColorComponent::parseGreen
        );
    }

    @Test
    public void testConvertWithStringNumberToRedRgbColorComponent() {
        this.convertAndCheck3(
            "44",
            RedRgbColorComponent.class,
            RgbColorComponent::parseRed
        );
    }

    @Test
    public void testConvertWithStringWithWebColorNameToRgbColor() {
        this.convertAndCheck4(
            WebColorName.RED.text(),
            RgbColor.class,
            WebColorName.BLUE.color()
        );
    }

    private <T extends Color> void convertAndCheck2(final String text,
                                                    final Class<T> type,
                                                    final Function<String, T> parser) {
        final T expected = parser.apply(text);

        this.convertAndCheck(
            text,
            type,
            this.createContext(text, expected),
            expected
        );
    }

    private <T extends ColorComponent> void convertAndCheck3(final String text,
                                                             final Class<T> type,
                                                             final Function<String, T> parser) {
        final T expected = parser.apply(text);

        this.convertAndCheck(
            text,
            type,
            this.createContext(),
            expected
        );
    }

    private <T extends Color> void convertAndCheck4(final String text,
                                                    final Class<T> type,
                                                    final T expected) {
        this.convertAndCheck(
            text,
            type,
            this.createContext(text, expected),
            expected
        );
    }

    @Override
    public ColorConverterTextToColor<FakeColorConverterContext> createConverter() {
        return ColorConverterTextToColor.instance();
    }

    @Override
    public FakeColorConverterContext createContext() {
        return this.createContext(
            null,
            null
        );
    }

    private FakeColorConverterContext createContext(final String text,
                                                    final Color color) {
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

            private final Converter<FakeColorConverterContext> converter = Converters.characterOrCharSequenceOrHasTextOrStringToCharacterOrCharSequenceOrString();

            @Override
            public Color parseColor(final String t) {
                if (null == text) {
                    throw new UnsupportedOperationException();
                }
                checkEquals(
                    text,
                    t,
                    "parseColorText"
                );
                return color;
            }
        };
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            ColorConverterTextToColor.instance(),
            "TEXT to Color"
        );
    }

    // class............................................................................................................

    @Override
    public Class<ColorConverterTextToColor<FakeColorConverterContext>> type() {
        return Cast.to(ColorConverterTextToColor.class);
    }
}
