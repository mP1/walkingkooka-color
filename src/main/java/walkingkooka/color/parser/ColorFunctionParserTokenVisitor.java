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

import walkingkooka.text.cursor.parser.ParserTokenVisitor;
import walkingkooka.visit.Visiting;

public abstract class ColorFunctionParserTokenVisitor extends ParserTokenVisitor {

    protected ColorFunctionParserTokenVisitor() {
        super();
    }

    // ParserToken......................................................................................................

    protected Visiting startVisit(final ColorFunctionParserToken token) {
        return Visiting.CONTINUE;
    }

    protected void endVisit(final ColorFunctionParserToken token) {
    }

    protected Visiting startVisit(final FunctionColorFunctionParserToken token) {
        return Visiting.CONTINUE;
    }

    protected void endVisit(final FunctionColorFunctionParserToken token) {
    }

    protected void visit(final DegreesUnitSymbolColorFunctionParserToken token) {
        // nop
    }

    protected void visit(final NameColorFunctionParserToken token) {
        // nop
    }

    protected void visit(final NumberColorFunctionParserToken token) {
        // nop
    }

    protected void visit(final ParenthesisCloseSymbolColorFunctionParserToken token) {
        // nop
    }

    protected void visit(final ParenthesisOpenSymbolColorFunctionParserToken token) {
        // nop
    }

    protected void visit(final PercentageColorFunctionParserToken token) {
        // nop
    }

    protected void visit(final SeparatorSymbolColorFunctionParserToken token) {
        // nop
    }

    protected void visit(final WhitespaceColorFunctionParserToken token) {
        // nop
    }
}