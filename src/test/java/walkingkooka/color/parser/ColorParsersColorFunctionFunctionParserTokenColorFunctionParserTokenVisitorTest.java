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
import walkingkooka.text.cursor.parser.ParserContexts;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.text.cursor.parser.ParserTokens;

import java.util.List;

public final class ColorParsersColorFunctionFunctionParserTokenColorFunctionParserTokenVisitorTest extends ColorParsersParserTokenVisitorTestCase<ColorParsersColorFunctionFunctionParserTokenColorFunctionParserTokenVisitor> {

    @Test
    public void testTransform() {
        final List<ParserToken> tokens = Lists.array();
        tokens.add(functionName("rgb"));
        tokens.add(parenthesisOpenSymbol());

        tokens.add(number(1.25));
        tokens.add(separator());

        tokens.add(number(2.0));
        tokens.add(separator());

        tokens.add(number(3));

        tokens.add(parenthesisCloseSymbol());

        final String text = ParserToken.text(tokens);

        this.checkEquals(ColorFunctionParserToken.function(tokens, text),
            ColorParsersColorFunctionFunctionParserTokenColorFunctionParserTokenVisitor.transform(ParserTokens.sequence(tokens, text), ParserContexts.fake()),
            () -> "transform " + tokens);
    }

    @Test
    public void testTransformWithWhitespace() {
        final List<ParserToken> tokens = Lists.array();
        tokens.add(this.functionName("rgb"));
        tokens.add(parenthesisOpenSymbol());

        tokens.add(whitespace());
        tokens.add(number(1.25));
        tokens.add(whitespace());
        tokens.add(separator());

        tokens.add(whitespace());
        tokens.add(number(2.0));
        tokens.add(whitespace());
        tokens.add(separator());

        tokens.add(whitespace());
        tokens.add(number(3));
        tokens.add(whitespace());

        tokens.add(parenthesisCloseSymbol());

        final String text = ParserToken.text(tokens);

        this.checkEquals(ColorFunctionParserToken.function(tokens, text),
            ColorParsersColorFunctionFunctionParserTokenColorFunctionParserTokenVisitor.transform(ParserTokens.sequence(tokens, text), ParserContexts.fake()),
            () -> "transform " + tokens);
    }

    private ColorFunctionParserToken functionName(final String name) {
        return ColorFunctionParserToken.name(name, name);
    }

    private NumberColorFunctionParserToken number(final double value) {
        return ColorFunctionParserToken.number(value, String.valueOf(value));
    }

    private ParenthesisCloseSymbolColorFunctionParserToken parenthesisCloseSymbol() {
        return ColorFunctionParserToken.parenthesisCloseSymbol(")", ")");
    }

    private ParenthesisOpenSymbolColorFunctionParserToken parenthesisOpenSymbol() {
        return ColorFunctionParserToken.parenthesisOpenSymbol("(", "(");
    }

    private SeparatorSymbolColorFunctionParserToken separator() {
        return ColorFunctionParserToken.separatorSymbol(",", ",");
    }

    private WhitespaceColorFunctionParserToken whitespace() {
        return ColorFunctionParserToken.whitespace(" ", " ");
    }

    @Test
    public void testToString() {
        final ColorParsersColorFunctionFunctionParserTokenColorFunctionParserTokenVisitor visitor = new ColorParsersColorFunctionFunctionParserTokenColorFunctionParserTokenVisitor();
        visitor.accept(ColorFunctionParserToken.number(123.0, "123"));
        this.toStringAndCheck(visitor, "[123]");
    }

    @Override
    public ColorParsersColorFunctionFunctionParserTokenColorFunctionParserTokenVisitor createVisitor() {
        return new ColorParsersColorFunctionFunctionParserTokenColorFunctionParserTokenVisitor();
    }

    @Override
    public Class<ColorParsersColorFunctionFunctionParserTokenColorFunctionParserTokenVisitor> type() {
        return ColorParsersColorFunctionFunctionParserTokenColorFunctionParserTokenVisitor.class;
    }
}
