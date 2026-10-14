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

import walkingkooka.convert.Converter;
import walkingkooka.reflect.PublicStaticHelper;

public final class ColorConverters implements PublicStaticHelper {

    /**
     * {@see ColorConverterColorToNumber}
     */
    public static <C extends ColorConverterContext> Converter<C> colorToNumber() {
        return ColorConverterColorToNumber.instance();
    }

    /**
     * {@see ColorConverterNumberToColor}
     */
    public static <C extends ColorConverterContext> Converter<C> numberToColor() {
        return ColorConverterNumberToColor.instance();
    }

    /**
     * {@see ColorConverterTextToColor}
     */
    public static <C extends ColorConverterContext> Converter<C> textToColor() {
        return ColorConverterTextToColor.instance();
    }

    /**
     * {@see ColorConverterTextToWebColorName}
     */
    public static <C extends ColorConverterContext> Converter<C> textToWebColorName() {
        return ColorConverterTextToWebColorName.instance();
    }

    /**
     * {@see ColorConverterToColor}
     */
    public static <C extends ColorConverterContext> Converter<C> toColor() {
        return ColorConverterToColor.instance();
    }

    /**
     * {@see ColorConverterToColorProperties}
     */
    public static <C extends ColorConverterContext> Converter<C> toColorProperties() {
        return ColorConverterToColorProperties.instance();
    }

    /**
     * Stop creation
     */
    private ColorConverters() {
        throw new UnsupportedOperationException();
    }
}
