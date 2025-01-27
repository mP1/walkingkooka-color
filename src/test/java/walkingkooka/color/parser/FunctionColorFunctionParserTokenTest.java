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
import walkingkooka.collect.list.Lists;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.visit.Visiting;

import static org.junit.jupiter.api.Assertions.assertSame;

public final class FunctionColorFunctionParserTokenTest extends ColorFunctionParserTokenTestCase3<FunctionColorFunctionParserToken> {

    @Override
    public void testEmptyTextFails() {
    }

    @Test
    public void testAccept() {
        final StringBuilder b = new StringBuilder();
        final FunctionColorFunctionParserToken token = this.createToken();

        new FakeColorFunctionParserTokenVisitor() {
            @Override
            protected Visiting startVisit(final ParserToken t) {
                b.append("1");
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final ParserToken t) {
                b.append("2");
            }

            @Override
            protected Visiting startVisit(final ColorFunctionParserToken t) {
                b.append("3");
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final ColorFunctionParserToken t) {
                b.append("4");
            }

            @Override
            protected Visiting startVisit(final FunctionColorFunctionParserToken t) {
                assertSame(token, t);
                b.append("5");
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final FunctionColorFunctionParserToken t) {
                assertSame(token, t);
                b.append("6");
            }

            @Override
            protected void visit(final NameColorFunctionParserToken t) {
                b.append("7");
            }

            @Override
            protected void visit(final NumberColorFunctionParserToken t) {
                b.append("8");
            }

            @Override
            protected void visit(final ParenthesisCloseSymbolColorFunctionParserToken t) {
                b.append("9");
            }

            @Override
            protected void visit(final ParenthesisOpenSymbolColorFunctionParserToken t) {
                b.append("A");
            }

            @Override
            protected void visit(final SeparatorSymbolColorFunctionParserToken t) {
                b.append("B");
            }

            @Override
            protected void visit(final WhitespaceColorFunctionParserToken t) {
                b.append("C");
            }
        }.accept(token);

        this.checkEquals("1351374213A421384213B421384213B421384213942642", b.toString());
    }

    @Override
    public FunctionColorFunctionParserToken createToken(final String text) {
        return this.createToken(text,
            functionName("rgb"),
            parenthesisOpen(),
            number(1),
            separator(),
            number(2),
            separator(),
            number(3),
            parenthesisClose());
    }

    @Override
    public String text() {
        return "rgb(1,2,3)";
    }

    @Override
    public FunctionColorFunctionParserToken createDifferentToken() {
        return this.createToken("rgba(9,8,7)",
            functionName("rgba"),
            parenthesisOpen(),
            number(9),
            separator(),
            number(8),
            separator(),
            number(7),
            parenthesisClose());
    }

    private FunctionColorFunctionParserToken createToken(final String text, final ColorFunctionParserToken... tokens) {
        return FunctionColorFunctionParserToken.with(Lists.of(tokens), text);
    }

    private ColorFunctionParserToken functionName(final String name) {
        return ColorFunctionParserToken.name(name, name);
    }

    private ColorFunctionParserToken number(final double value) {
        return ColorFunctionParserToken.number(value, Math.round(value) == value ? String.valueOf(Math.round(value)) : String.valueOf(value));
    }

    private ColorFunctionParserToken parenthesisOpen() {
        return ColorFunctionParserToken.parenthesisOpenSymbol("(", "(");
    }

    private ColorFunctionParserToken parenthesisClose() {
        return ColorFunctionParserToken.parenthesisCloseSymbol(")", ")");
    }

    private ColorFunctionParserToken separator() {
        return ColorFunctionParserToken.separatorSymbol(",", ",");
    }

    @Override
    public Class<FunctionColorFunctionParserToken> type() {
        return FunctionColorFunctionParserToken.class;
    }
}
