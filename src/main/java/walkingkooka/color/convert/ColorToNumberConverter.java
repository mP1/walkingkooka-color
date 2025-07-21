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
import walkingkooka.Value;
import walkingkooka.color.Color;
import walkingkooka.color.ColorComponent;
import walkingkooka.color.ColorLike;
import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.ShortCircuitingConverter;
import walkingkooka.tree.expression.ExpressionNumber;

/**
 * A {@link Converter} that converts a {@link Color} to a number.
 */
final class ColorToNumberConverter<C extends ConverterContext> implements ShortCircuitingConverter<C> {

    /**
     * Type safe singleton getter.
     */
    static <C extends ConverterContext> ColorToNumberConverter<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ColorToNumberConverter<ConverterContext> INSTANCE = new ColorToNumberConverter<>();

    /**
     * Private ctor use {@link #INSTANCE}.
     */
    private ColorToNumberConverter() {
        super();
    }

    @Override
    public boolean canConvert(final Object value,
                              final Class<?> type,
                              final C context) {
        // RgbColor OR RgbColorComponent
        return value instanceof ColorLike &&
            // must have a value getter
            value instanceof Value &&
            (
                value instanceof ColorComponent ||
                    // Color cannot be converted to Byte or Short
                    (value instanceof Color && Byte.class != type && Short.class != type)
            ) &&
            // target can be any number
            (ExpressionNumber.isClass(type) || Number.class == type);
    }

    @Override
    public <T> Either<T, String> doConvert(final Object value,
                                           final Class<T> type,
                                           final C context) {
        Number number = null;

        // get the number
        if (value instanceof Value) {
            number = ((Value<Number>) value).value();
        }

        return null != number ?
            // convert to the requested number type
            this.successfulConversion(
                context.convertOrFail(
                    number,
                    type
                ),
                type
            ) :
            this.failConversion(
                value,
                type
            );
    }

    // Object...........................................................................................................

    @Override
    public String toString() {
        return "Color to Number";
    }
}
