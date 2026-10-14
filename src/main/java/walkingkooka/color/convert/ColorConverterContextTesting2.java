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

import walkingkooka.color.ColorContextTesting2;
import walkingkooka.tree.expression.convert.ExpressionNumberConverterContextTesting2;

public interface ColorConverterContextTesting2<C extends ColorConverterContext> extends ExpressionNumberConverterContextTesting2<C>,
    ColorContextTesting2<C> {

    @Override
    default String typeNameSuffix() {
        return ColorConverterContext.class.getSimpleName();
    }
}
