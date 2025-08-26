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
import walkingkooka.color.Color;
import walkingkooka.color.RgbColorComponent;
import walkingkooka.color.WebColorName;
import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterTesting2;
import walkingkooka.convert.Converters;
import walkingkooka.convert.FakeConverterContext;

import java.util.List;
import java.util.function.Function;

public final class TextToColorConverterTest implements ConverterTesting2<TextToColorConverter<FakeConverterContext>, FakeConverterContext> {

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
    public void testConvertWithRgbColorStringHash3DigitsAndColor() {
        this.convertAndCheck2(
            "#123",
            Color.class,
            Color::parse
        );
    }

    @Test
    public void testConvertWithRgbColorStringHash6DigitsAndColor() {
        this.convertAndCheck2(
            "#123456",
            Color.class,
            Color::parse
        );
    }

    @Test
    public void testConvertWithRgbColorStringWebColorNameAndColor() {
        this.convertAndCheck2(
            WebColorName.RED.text(),
            Color.class,
            Color::parse
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
            Color::parseRgb
        );
    }

    @Test
    public void testConvertWithStringAndAlphaRgbColor() {
        this.convertAndCheck2(
            "#12345678",
            Color::parseRgb
        );
    }

    @Test
    public void testConvertWithStringAndHslColor() {
        this.convertAndCheck2(
            "hsl(359,100%,50%)",
            Color::parseHsl
        );
    }

    @Test
    public void testConvertWithStringAndAlphaHslColor() {
        this.convertAndCheck2(
            "hsl(359,100%,50%,100%)",
            Color::parseHsl
        );
    }

    @Test
    public void testConvertWithStringAndHsvColor() {
        this.convertAndCheck2(
            "hsv(359,100%,50%)",
            Color::parseHsv
        );
    }

    @Test
    public void testConvertWithStringAndAlphaHsvColor() {
        this.convertAndCheck2(
            "hsv(359,100%,50%, 100%)",
            Color::parseHsv
        );
    }

    @Test
    public void testConvertWithStringNumberToAlphaRgbColorComponent() {
        this.convertAndCheck2(
            "111",
            RgbColorComponent::parseAlpha
        );
    }

    @Test
    public void testConvertWithStringNumberToBlueRgbColorComponent() {
        this.convertAndCheck2(
            "22",
            RgbColorComponent::parseBlue
        );
    }

    @Test
    public void testConvertWithStringNumberToGreenRgbColorComponent() {
        this.convertAndCheck2(
            "33",
            RgbColorComponent::parseGreen
        );
    }

    @Test
    public void testConvertWithStringNumberToRedRgbColorComponent() {
        this.convertAndCheck2(
            "44",
            RgbColorComponent::parseRed
        );
    }

    @Test
    public void testConvertWithStringWithWebColorNameToRgbColor() {
        final WebColorName webColorName = WebColorName.HOTPINK;
        this.convertAndCheck(
            webColorName.text(),
            webColorName.color()
        );
    }

    private void convertAndCheck2(final String text,
                                  final Function<String, Object> parser) {
        this.convertAndCheck(
            text,
            parser.apply(text)
        );
    }

    private void convertAndCheck2(final String text,
                                  final Class<? extends Color> type,
                                  final Function<String, Color> parser) {
        this.convertAndCheck(
            text,
            (Class<Color>) type,
            parser.apply(text)
        );
    }

    @Override
    public TextToColorConverter<FakeConverterContext> createConverter() {
        return TextToColorConverter.instance();
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

            private final Converter<FakeConverterContext> converter = Converters.characterOrCharSequenceOrHasTextOrStringToCharacterOrCharSequenceOrString();
        };
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            TextToColorConverter.instance(),
            "String to Color"
        );
    }

    // class............................................................................................................

    @Override
    public Class<TextToColorConverter<FakeConverterContext>> type() {
        return Cast.to(TextToColorConverter.class);
    }
}
