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
import walkingkooka.color.ColorLike;
import walkingkooka.color.RgbColorComponent;
import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.ShortCircuitingConverter;

/**
 * A {@link Converter} that converts a {@link Number} to another {@link ColorLike}.
 */
final class NumberToColorConverter<C extends ConverterContext> implements ShortCircuitingConverter<C> {

    /**
     * Type safe singleton getter.
     */
    static <C extends ConverterContext> NumberToColorConverter<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static NumberToColorConverter<ConverterContext> INSTANCE = new NumberToColorConverter<>();

    /**
     * Private ctor use {@link #INSTANCE}.
     */
    private NumberToColorConverter() {
        super();
    }

    @Override
    public boolean canConvert(final Object value,
                              final Class<?> type,
                              final C context) {
        return value instanceof Number &&
            (Color.isRgbColorClass(type) ||
                (RgbColorComponent.isRgbColorComponentClass(type) && RgbColorComponent.class != type));
    }

    @Override
    public <T> Either<T, String> doConvert(final Object value,
                                           final Class<T> type,
                                           final C context) {
        Object colorLike = null;

        switch (type.getSimpleName()) {
            case "AlphaRgbColorComponent":
                colorLike = RgbColorComponent.alpha(
                    context.convertOrFail(
                        value,
                        Byte.class
                    )
                );
                break;
            case "RedRgbColorComponent":
                colorLike = RgbColorComponent.red(
                    context.convertOrFail(
                        value,
                        Byte.class
                    )
                );
                break;
            case "GreenRgbColorComponent":
                colorLike = RgbColorComponent.green(
                    context.convertOrFail(
                        value,
                        Byte.class
                    )
                );
                break;
            case "BlueRgbColorComponent":
                colorLike = RgbColorComponent.blue(
                    context.convertOrFail(
                        value,
                        Byte.class
                    )
                );
                break;
            case "RgbColor":
            case "OpaqueRgbColor":
                colorLike = Color.fromRgb(
                    context.convertOrFail(
                        value,
                        Integer.class
                    )
                );
                break;
            case "AlphaRgbColor":
                colorLike = Color.fromArgb(
                    context.convertOrFail(
                        value,
                        Integer.class
                    )
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown color type: " + type.getName());
        }

        return this.successfulConversion(
            colorLike,
            type
        );
    }

    // Object...........................................................................................................

    @Override
    public String toString() {
        return "Number to Color";
    }
}
