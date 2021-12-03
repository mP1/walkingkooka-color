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
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.visit.Visiting;

import static org.junit.jupiter.api.Assertions.assertSame;

public final class ColorFunctionPercentageParserTokenTest extends ColorFunctionNonSymbolParserTokenTestCase<ColorFunctionPercentageParserToken, Double> {

    @Test
    public void testAccept() {
        final StringBuilder b = new StringBuilder();
        final ColorFunctionPercentageParserToken token = this.createToken();

        new FakeColorFunctionParserTokenVisitor() {
            @Override
            protected Visiting startVisit(final ParserToken t) {
                assertSame(token, t);
                b.append("1");
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final ParserToken t) {
                assertSame(token, t);
                b.append("2");
            }

            @Override
            protected Visiting startVisit(final ColorFunctionParserToken t) {
                assertSame(token, t);
                b.append("3");
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final ColorFunctionParserToken t) {
                assertSame(token, t);
                b.append("4");
            }

            @Override
            protected void visit(final ColorFunctionPercentageParserToken t) {
                assertSame(token, t);
                b.append("5");
            }
        }.accept(token);
        this.checkEquals("13542", b.toString());
    }

    @Override
    ColorFunctionPercentageParserToken createToken(final String text, final Double value) {
        return ColorFunctionPercentageParserToken.with(value, text);
    }

    @Override
    public Class<ColorFunctionPercentageParserToken> type() {
        return ColorFunctionPercentageParserToken.class;
    }

    @Override
    public String text() {
        return "99%";
    }

    Double value() {
        return 999.0;
    }

    Double differentValue() {
        return 99999.0;
    }
}
