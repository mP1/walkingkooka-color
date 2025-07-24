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
import walkingkooka.color.Color;
import walkingkooka.color.RgbColorComponent;
import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.TextToTryingShortCircuitingConverter;

/**
 * A {@link Converter} that handles parsing any given text using the Color type to select the right parseXXX method.
 * Examples.
 * <pre>
 * #123456 // rgb
 * #12345680 // rgb alpha
 * hsl(359,100%,50%) // hsl
 * hsv(359,100%,50%) // hsv
 * RED // WebColorName -> rgb
 * </pre>
 */
final class TextToColorConverter<C extends ConverterContext> implements TextToTryingShortCircuitingConverter<C> {

    /**
     * Type safe singleton getter.
     */
    static <C extends ConverterContext> TextToColorConverter<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static TextToColorConverter<ConverterContext> INSTANCE = new TextToColorConverter<>();

    /**
     * Private ctor use {@link #INSTANCE}.
     */
    private TextToColorConverter() {
        super();
    }

    @Override
    public boolean isTargetType(final Object value,
                                final Class<?> type,
                                final C context) {
        return Color.isColorClass(type) ||
            RgbColorComponent.isRgbColorComponentClass(type);
    }

    @Override
    public Object parseText(final String text,
                            final Class<?> type,
                            final C context) {
        final Object color;

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
            case "AlphaRgbColorComponent":
                color = RgbColorComponent.parseAlpha(text);
                break;
            case "BlueRgbColorComponent":
                color = RgbColorComponent.parseBlue(text);
                break;
            case "GreenRgbColorComponent":
                color = RgbColorComponent.parseGreen(text);
                break;
            case "RedRgbColorComponent":
                color = RgbColorComponent.parseRed(text);
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
