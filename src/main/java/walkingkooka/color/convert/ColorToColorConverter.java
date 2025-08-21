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
 * A {@link Converter} that converts a {@link Color}, {@link walkingkooka.color.WebColorName} or {@link String} such as
 * <pre>#123456</pre> to another {@link Color}.
 */
final class ColorToColorConverter<C extends ConverterContext> implements ShortCircuitingConverter<C> {

    /**
     * Type safe singleton getter.
     */
    static <C extends ConverterContext> ColorToColorConverter<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ColorToColorConverter<ConverterContext> INSTANCE = new ColorToColorConverter<>();

    /**
     * Private ctor use {@link #INSTANCE}.
     */
    private ColorToColorConverter() {
        super();
    }

    @Override
    public boolean canConvert(final Object value,
                              final Class<?> type,
                              final C context) {
        return Color.isColorClass(type) &&
            (value instanceof Color || context.canConvert(value, type));
    }

    @Override
    public <T> Either<T, String> doConvert(final Object value,
                                           final Class<T> type,
                                           final C context) {
        Either<T, String> result;

        if (false == value instanceof Color) {
            result = context.convert(
                value,
                type
            );
            if (result.isLeft()) {
                result = this.convertColor(
                    (Color) result.leftValue(),
                    type
                );
            }
        } else {
            result = this.convertColor(
                (Color) value,
                type
            );
        }

        return result;
    }

    private <T> Either<T, String> convertColor(final Color color,
                                               final Class<T> type) {
        final Color result;

        if (Color.isRgbColorClass(type)) {
            result = color.toRgb();
        } else {
            if (Color.isHslColorClass(type)) {
                result = color.toHsl();
            } else {
                if (Color.isHsvColorClass(type)) {
                    result = color.toHsv();
                } else {
                    // any Color subclass to Color.class gives color
                    if (Color.class == type) {
                        result = color;
                    } else {
                        result = null;
                    }
                }
            }
        }

        return null != color ?
            this.successfulConversion(
                result,
                type
            ) :
            this.failConversion(
                result,
                type
            );
    }

    // Object...........................................................................................................

    @Override
    public String toString() {
        return "Color to Color";
    }
}
