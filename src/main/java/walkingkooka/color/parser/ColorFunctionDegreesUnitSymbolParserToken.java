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

import java.util.function.Function;
import java.util.function.Predicate;

public final class ColorFunctionDegreesUnitSymbolParserToken extends ColorFunctionSymbolParserToken<String> {

    static ColorFunctionDegreesUnitSymbolParserToken with(final String value, final String text) {
        check(value, text);

        return new ColorFunctionDegreesUnitSymbolParserToken(value, text);
    }

    private ColorFunctionDegreesUnitSymbolParserToken(final String value, final String text) {
        super(value, text);
    }

    // replaceFirstIf...................................................................................................

    @Override
    public ColorFunctionDegreesUnitSymbolParserToken replaceFirstIf(final Predicate<ParserToken> predicate,
                                                                    final Function<ParserToken, ParserToken> mapper) {
        return ParserToken.replaceFirstIf(
                this,
                predicate,
                mapper,
                ColorFunctionDegreesUnitSymbolParserToken.class
        );
    }

    // replaceIf........................................................................................................

    @Override
    public ColorFunctionDegreesUnitSymbolParserToken replaceIf(final Predicate<ParserToken> predicate,
                                                               final Function<ParserToken, ParserToken> mapper) {
        return ParserToken.replaceIf(
                this,
                predicate,
                mapper,
                ColorFunctionDegreesUnitSymbolParserToken.class
        );
    }

    // Visitor.........................................................................................................

    @Override
    void accept(final ColorFunctionParserTokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof ColorFunctionDegreesUnitSymbolParserToken;
    }
}
