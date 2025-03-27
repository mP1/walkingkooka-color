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
import walkingkooka.color.Color;
import walkingkooka.convert.ConverterTesting2;
import walkingkooka.convert.FakeConverterContext;

import java.util.function.Function;

public final class StringToColorConverterTest implements ConverterTesting2<StringToColorConverter<FakeConverterContext>, FakeConverterContext> {

    @Test
    public void testConvertWithRgbColorStringAndColor() {
        this.convertAndCheck2(
            "#123456",
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

    private void convertAndCheck2(final String text,
                                  final Function<String, Color> parser) {
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
    public StringToColorConverter<FakeConverterContext> createConverter() {
        return StringToColorConverter.instance();
    }

    @Override
    public FakeConverterContext createContext() {
        return new FakeConverterContext();
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            StringToColorConverter.instance(),
            "String to Color"
        );
    }

    // class............................................................................................................

    @Override
    public Class<StringToColorConverter<FakeConverterContext>> type() {
        return Cast.to(StringToColorConverter.class);
    }
}
