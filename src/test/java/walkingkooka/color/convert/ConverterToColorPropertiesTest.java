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
import walkingkooka.color.ColorProperties;
import walkingkooka.convert.Converter;
import walkingkooka.convert.Converters;
import walkingkooka.convert.FakeConverterContext;

public final class ConverterToColorPropertiesTest extends ConverterColorTestCase<ConverterToColorProperties<FakeConverterContext>, FakeConverterContext> {

    @Test
    public void testConvertStringToColorFails() {
        this.convertFails(
            "#123",
            Color.class
        );
    }

    @Test
    public void testConvertStringToColorProperties() {
        final String text = "RED=#123456";

        this.convertAndCheck(
            text,
            ColorProperties.parse(text)
        );
    }

    @Override
    public ConverterToColorProperties<FakeConverterContext> createConverter() {
        return ConverterToColorProperties.instance();
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
                    Converters.textToProperties()
                )
            );
        };
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            ConverterToColorProperties.instance(),
            "to ColorProperties"
        );
    }

    // class............................................................................................................

    @Override
    public Class<ConverterToColorProperties<FakeConverterContext>> type() {
        return Cast.to(ConverterToColorProperties.class);
    }
}
