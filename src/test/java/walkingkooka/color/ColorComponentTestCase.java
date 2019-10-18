/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
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

package walkingkooka.color;

import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.ToStringTesting;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.IsMethodTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.TypeNameTesting;

public abstract class ColorComponentTestCase<C extends ColorComponent> implements ClassTesting2<C>,
        HashCodeEqualsDefinedTesting2<C>,
        IsMethodTesting<C>,
        ToStringTesting<C>,
        TypeNameTesting<C> {

    ColorComponentTestCase() {
        super();
    }

    // ClassTesting....................................................................................................

    @Override
    public final JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }

    // IsMethodTesting.................................................................................................

    @Override
    public final C createIsMethodObject() {
        return this.createObject();
    }

    @Override
    public final String isMethodTypeNamePrefix() {
        return "";
    }

    // TypeNameTesting ................................................................................................

    @Override
    public final String typeNamePrefix() {
        return "";
    }
}
