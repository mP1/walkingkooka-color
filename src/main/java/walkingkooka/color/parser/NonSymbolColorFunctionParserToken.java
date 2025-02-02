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

import walkingkooka.text.cursor.parser.ParserToken;

/**
 * Base class for a non symbol {@link ParserToken}.
 */
abstract class NonSymbolColorFunctionParserToken<V> extends LeafColorFunctionParserToken<V> {

    static void check(final Object value, final String text) {
        checkValue(value);
        checkText(text);
    }

    /**
     * Package private ctor to limit subclassing.
     */
    NonSymbolColorFunctionParserToken(final V value, final String text) {
        super(value, text);
    }
}
