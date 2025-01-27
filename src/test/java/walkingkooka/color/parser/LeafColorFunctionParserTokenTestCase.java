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
import walkingkooka.reflect.IsMethodTesting;
import walkingkooka.text.cursor.parser.ParserTokenTesting;

public abstract class LeafColorFunctionParserTokenTestCase<T extends LeafColorFunctionParserToken<?>> extends ColorFunctionParserTokenTestCase3<T>
    implements ParserTokenTesting<T>,
    IsMethodTesting<T> {

    LeafColorFunctionParserTokenTestCase() {
        super();
    }

    @Test
    public final void testAccept2() {
        new ColorFunctionParserTokenVisitor() {
        }.accept(this.createToken());
    }

    @Override
    public final T createDifferentToken() {
        return this.createToken("different");
    }
}
