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

import walkingkooka.Cast;
import walkingkooka.color.ColorProperties;
import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.TryingShortCircuitingConverter;
import walkingkooka.props.Properties;

/**
 * A {@link Converter} that handles parsing text into a {@link walkingkooka.color.ColorProperties}.
 */
final class ConverterToColorProperties<C extends ConverterContext> implements TryingShortCircuitingConverter<C> {

    /**
     * Type safe singleton getter.
     */
    static <C extends ConverterContext> ConverterToColorProperties<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ConverterToColorProperties<ConverterContext> INSTANCE = new ConverterToColorProperties<>();

    /**
     * Private ctor use {@link #INSTANCE}.
     */
    private ConverterToColorProperties() {
        super();
    }

    @Override
    public Object tryConvertOrFail(final Object value,
                                   final Class<?> type,
                                   final C context) {
        return ColorProperties.with(
            context.convertOrFail(
                value,
                Properties.class
            )
        );
    }

    @Override
    public boolean canConvert(final Object value,
                              final Class<?> type,
                              final C context) {
        return ColorProperties.class == type &&
            context.canConvert(value, Properties.class);
    }

    // Object...........................................................................................................

    @Override
    public String toString() {
        return "to " + ColorProperties.class.getSimpleName();
    }
}
