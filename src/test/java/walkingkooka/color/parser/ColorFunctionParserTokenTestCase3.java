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

package walkingkooka.color.parser;

import org.junit.jupiter.api.Test;
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.reflect.IsMethodTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.PublicStaticFactoryTesting;
import walkingkooka.text.cursor.parser.ParserTokenTesting;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class ColorFunctionParserTokenTestCase3<T extends ColorFunctionParserToken> extends ColorFunctionParserTokenTestCase<T>
    implements ParserTokenTesting<T>,
    HashCodeEqualsDefinedTesting2<T>,
    IsMethodTesting<T> {

    ColorFunctionParserTokenTestCase3() {
        super();
    }

    @Override
    @Test
    public final void testPublicStaticFactoryMethod() {
        PublicStaticFactoryTesting.checkFactoryMethods(
            ColorFunctionParserToken.class,
            "",
            ColorFunctionParserToken.class.getSimpleName(),
            this.type()
        );
    }

    @Test
    public void testEmptyTextFails() {
        assertThrows(IllegalArgumentException.class, () -> this.createToken(""));
    }

    // HashCodeEqualsDefinedTesting.....................................................................................

    @Override public final T createObject() {
        return this.createToken();
    }

    // isMethodTesting2.................................................................................................

    @Override
    public T createIsMethodObject() {
        return this.createToken(this.text());
    }

    @Override
    public final Predicate<String> isMethodIgnoreMethodFilter() {
        return (m) -> m.equals("isLeaf") ||
            m.equals("isNoise") ||
            m.equals("isParent") ||
            m.equals("isSymbol") ||
            m.equals("isEmpty") ||
            m.equals("isNotEmpty"); // skip isNoise
    }


    @Override
    public final String toIsMethodName(final String typeName) {
        return this.toIsMethodNameWithPrefixSuffix(
            typeName,
            "",
            ColorFunctionParserToken.class.getSimpleName()
        );
    }

    // class............................................................................................................

    @Override
    public final JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
