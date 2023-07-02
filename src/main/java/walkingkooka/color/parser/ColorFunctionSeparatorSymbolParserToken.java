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

import java.util.Optional;
import java.util.function.Predicate;

public final class ColorFunctionSeparatorSymbolParserToken extends ColorFunctionSymbolParserToken<String> {

    static ColorFunctionSeparatorSymbolParserToken with(final String value, final String text) {
        check(value, text);

        return new ColorFunctionSeparatorSymbolParserToken(value, text);
    }

    private ColorFunctionSeparatorSymbolParserToken(final String value, final String text) {
        super(value, text);
    }

    // removeFirstIf....................................................................................................

    @Override
    public Optional<ColorFunctionSeparatorSymbolParserToken> removeFirstIf(final Predicate<ParserToken> predicate) {
        return ParserToken.removeFirstIfLeaf(
                this,
                predicate,
                ColorFunctionSeparatorSymbolParserToken.class
        );
    }

    // removeIf.........................................................................................................

    @Override
    public Optional<ColorFunctionSeparatorSymbolParserToken> removeIf(final Predicate<ParserToken> predicate) {
        return ParserToken.removeIfLeaf(
                this,
                predicate,
                ColorFunctionSeparatorSymbolParserToken.class
        );
    }

    // replaceFirstIf...................................................................................................

    @Override
    public ColorFunctionSeparatorSymbolParserToken replaceFirstIf(final Predicate<ParserToken> predicate,
                                                                  final ParserToken token) {
        return ParserToken.replaceFirstIf(
                this,
                predicate,
                token,
                ColorFunctionSeparatorSymbolParserToken.class
        );
    }

    // replaceIf........................................................................................................

    @Override
    public ColorFunctionSeparatorSymbolParserToken replaceIf(final Predicate<ParserToken> predicate,
                                                             final ParserToken token) {
        return ParserToken.replaceIf(
                this,
                predicate,
                token,
                ColorFunctionSeparatorSymbolParserToken.class
        );
    }

    // ColorFunctionParserTokenVisitor..................................................................................

    @Override
    void accept(final ColorFunctionParserTokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof ColorFunctionSeparatorSymbolParserToken;
    }
}
