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
import walkingkooka.Either;
import walkingkooka.color.Color;
import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.ShortCircuitingConverter;

/**
 * A {@link Converter} that converts a {@link Color} to a {@link walkingkooka.color.RgbColor}.
 */
final class ColorToRgbColorConverter<C extends ConverterContext> implements ShortCircuitingConverter<C> {

    /**
     * Type safe singleton getter.
     */
    static <C extends ConverterContext> ColorToRgbColorConverter<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ColorToRgbColorConverter<ConverterContext> INSTANCE = new ColorToRgbColorConverter<>();

    /**
     * Private ctor use {@link #INSTANCE}.
     */
    private ColorToRgbColorConverter() {
        super();
    }

    @Override
    public boolean canConvert(final Object value,
                              final Class<?> type,
                              final C context) {
        return Color.isRgbColorClass(type) &&
            (value instanceof Color || context.canConvert(value, type));
    }

    @Override
    public <T> Either<T, String> doConvert(final Object value,
                                           final Class<T> type,
                                           final C context) {
        Object temp = value;
        if (false == temp instanceof Color) {
            temp = context.convertOrFail(
                value,
                type
            );
        }

        final Color color = (Color) temp;
        return this.successfulConversion(
            color.toRgb(),
            type
        );
    }

    // Object...........................................................................................................

    @Override
    public String toString() {
        return "Color to RgbColor";
    }
}
