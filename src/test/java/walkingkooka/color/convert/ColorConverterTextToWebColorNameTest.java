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
import walkingkooka.color.WebColorName;
import walkingkooka.convert.Converter;
import walkingkooka.convert.Converters;

public final class ColorConverterTextToWebColorNameTest extends ColorConverterColorTestCase<ColorConverterTextToWebColorName<FakeColorConverterContext>, FakeColorConverterContext> {

    @Test
    public void testConvertWithRgbColorWithWebColorNameToWebColorName() {
        this.convertAndCheck(
            Color.BLACK,
            WebColorName.class,
            WebColorName.BLACK
        );
    }

    // success because WebColorName is converted to a String and then lookup to WebColorName is successful.
    @Test
    public void testConvertWithWebColorNameToWebColorName() {
        this.convertAndCheck(
            WebColorName.BLACK,
            WebColorName.class,
            WebColorName.BLACK
        );
    }

    @Test
    public void testConvertWithStringToWebColorNameUnknown() {
        this.convertFails(
            "UnknownWebColorName",
            WebColorName.class
        );
    }

    @Test
    public void testConvertWithWebColorNameStringToWebColorName() {
        this.convertAndCheck(
            WebColorName.BLACK.value(),
            WebColorName.BLACK
        );
    }

    @Override
    public ColorConverterTextToWebColorName<FakeColorConverterContext> createConverter() {
        return ColorConverterTextToWebColorName.instance();
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

            private final Converter<FakeColorConverterContext> converter = Converters.characterOrCharSequenceOrHasTextOrStringToCharacterOrCharSequenceOrString();
        };
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            ColorConverterTextToWebColorName.instance(),
            "TEXT to WebColorName"
        );
    }

    // class............................................................................................................

    @Override
    public Class<ColorConverterTextToWebColorName<FakeColorConverterContext>> type() {
        return Cast.to(ColorConverterTextToWebColorName.class);
    }
}
