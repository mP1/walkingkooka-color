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
import walkingkooka.convert.ConverterContext;
import walkingkooka.reflect.PublicStaticHelper;

public final class ColorConverters implements PublicStaticHelper {

    /**
     * {@see ColorToColorConverter}
     */
    public static <C extends ConverterContext> Converter<C> colorToColor() {
        return ColorToColorConverter.instance();
    }

    /**
     * {@see ColorToNumberConverter}
     */
    public static <C extends ConverterContext> Converter<C> colorToNumber() {
        return ColorToNumberConverter.instance();
    }

    /**
     * {@see NumberToColorConverter}
     */
    public static <C extends ConverterContext> Converter<C> numberToColor() {
        return NumberToColorConverter.instance();
    }

    /**
     * {@see TextToColorConverter}
     */
    public static <C extends ConverterContext> Converter<C> textToColor() {
        return TextToColorConverter.instance();
    }

    /**
     * Stop creation
     */
    private ColorConverters() {
        throw new UnsupportedOperationException();
    }
}
