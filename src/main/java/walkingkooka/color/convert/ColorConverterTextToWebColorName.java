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
import walkingkooka.color.WebColorName;
import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.TextToTryingShortCircuitingConverter;
import walkingkooka.text.CharSequences;

/**
 * A {@link Converter} that handles parsing any given text into a {@link WebColorName}.
 */
final class ColorConverterTextToWebColorName<C extends ConverterContext> extends ColorConverter<C>
    implements TextToTryingShortCircuitingConverter<C> {

    /**
     * Type safe singleton getter.
     */
    static <C extends ConverterContext> ColorConverterTextToWebColorName<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ColorConverterTextToWebColorName<ConverterContext> INSTANCE = new ColorConverterTextToWebColorName<>();

    /**
     * Private ctor use {@link #INSTANCE}.
     */
    private ColorConverterTextToWebColorName() {
        super();
    }

    @Override
    public boolean isTargetType(final Object value,
                                final Class<?> type,
                                final C context) {
        return WebColorName.class == type;
    }

    @Override
    public WebColorName parseText(final String text,
                                  final Class<?> type,
                                  final C context) {
        return WebColorName.with(text)
            .orElseThrow(() -> new IllegalArgumentException("Unknown WebColorName: " + CharSequences.quoteAndEscape(text)));
    }

    // Object...........................................................................................................

    @Override
    public String toString() {
        return TEXT + " to " + WebColorName.class.getSimpleName();
    }
}
