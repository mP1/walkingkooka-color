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

package walkingkooka.color.compare;

import walkingkooka.color.Color;
import walkingkooka.compare.ComparatorTesting2;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.text.CharSequences;

public abstract class ColorComparatorTestCase<C extends ColorComparator> implements ComparatorTesting2<C, Color>,
    ClassTesting2<C> {

    ColorComparatorTestCase() {
        super();
    }

    // class............................................................................................................

    @Override
    public final String typeNamePrefix() {
        return CharSequences.subSequence(
            this.getClass()
                .getSuperclass()
                .getSimpleName(),
            0,
            -"TestCase".length()
        ).toString();
    }

    @Override
    public final String typeNameSuffix() {
        return "";
    }

    @Override
    public final JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
