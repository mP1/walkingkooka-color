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

import java.util.Objects;

/**
 * A {@link Converter} that handles parsing any given text using the Color type to select the right parseXXX method.
 */
final class StringToColorConverter<C extends ConverterContext> implements Converter<C> {

    /**
     * Type safe singleton getter.
     */
    static <C extends ConverterContext> StringToColorConverter<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static StringToColorConverter<ConverterContext> INSTANCE = new StringToColorConverter<>();

    /**
     * Private ctor use {@link #INSTANCE}.
     */
    private StringToColorConverter() {
        super();
    }

    @Override
    public boolean canConvert(final Object value,
                              final Class<?> type,
                              final C context) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(context, "context");

        return value instanceof String && isColorSubclass(type);
    }

    private static boolean isColorSubclass(final Class<?> type) {
        boolean is = false;

        Class<?> temp = type;

        while (Object.class != temp) {
            is = Color.class == temp;
            if (is) {
                break;
            }
            temp = temp.getSuperclass();
        }

        return is;
    }

    @Override
    public <T> Either<T, String> convert(final Object value,
                                         final Class<T> type,
                                         final C context) {
        return this.canConvert(
            value,
            type,
            context
        ) ?
            this.successfulConversion(
                this.parse(
                    (String) value,
                    type
                ),
                type
            ) :
            this.failConversion(
                value,
                type
            );
    }

    private static Color parse(final String text,
                               final Class<?> type) {
        final Color color;

        switch (type.getSimpleName()) {
            case "Color":
                color = Color.parse(text);
                break;
            case "RgbColor":
            case "OpaqueRgbColor":
            case "AlphaRgbColor":
                color = Color.parseRgb(text);
                break;
            case "HslColor":
            case "OpaqueHslColor":
            case "AlphaHslColor":
                color = Color.parseHsl(text);
                break;
            case "HsvColor":
            case "OpaqueHsvColor":
            case "AlphaHsvColor":
                color = Color.parseHsv(text);
                break;
            default:
                throw new IllegalArgumentException("Unknown color " + type.getName());
        }

        return color;
    }

    // Object...........................................................................................................

    @Override
    public String toString() {
        return "String to Color";
    }
}
