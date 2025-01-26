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

import walkingkooka.Cast;
import walkingkooka.collect.map.Maps;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserContext;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.text.cursor.parser.StringParserToken;
import walkingkooka.text.cursor.parser.ebnf.AlternativeEbnfParserToken;
import walkingkooka.text.cursor.parser.ebnf.ConcatenationEbnfParserToken;
import walkingkooka.text.cursor.parser.ebnf.EbnfIdentifierName;
import walkingkooka.text.cursor.parser.ebnf.ExceptionEbnfParserToken;
import walkingkooka.text.cursor.parser.ebnf.GroupEbnfParserToken;
import walkingkooka.text.cursor.parser.ebnf.IdentifierEbnfParserToken;
import walkingkooka.text.cursor.parser.ebnf.OptionalEbnfParserToken;
import walkingkooka.text.cursor.parser.ebnf.RangeEbnfParserToken;
import walkingkooka.text.cursor.parser.ebnf.RepeatedEbnfParserToken;
import walkingkooka.text.cursor.parser.ebnf.RuleEbnfParserToken;
import walkingkooka.text.cursor.parser.ebnf.TerminalEbnfParserToken;
import walkingkooka.text.cursor.parser.ebnf.combinator.EbnfParserCombinatorGrammarTransformer;

import java.util.Map;
import java.util.function.BiFunction;

final class ColorParsersEbnfParserCombinatorGrammarTransformer implements EbnfParserCombinatorGrammarTransformer<ParserContext> {

    static ColorParsersEbnfParserCombinatorGrammarTransformer create() {
        return new ColorParsersEbnfParserCombinatorGrammarTransformer();
    }

    private ColorParsersEbnfParserCombinatorGrammarTransformer() {
        super();

        final Map<EbnfIdentifierName, BiFunction<ParserToken, ParserContext, ParserToken>> identifierToTransformer = Maps.sorted();

        identifierToTransformer.put(
            PARENTHESIS_OPEN,
            ColorParsersEbnfParserCombinatorGrammarTransformer::transformParenthesisOpen
        );
        identifierToTransformer.put(
            PARENTHESIS_CLOSE,
            ColorParsersEbnfParserCombinatorGrammarTransformer::transformParenthesisClose
        );
        identifierToTransformer.put(
            PERCENTAGE,
            ColorParsersPercentageColorFunctionParserTokenVisitor::transform
        );
        identifierToTransformer.put(
            RGB_RGBA,
            ColorParsersEbnfParserCombinatorGrammarTransformer::transformFunctionName
        );
        identifierToTransformer.put(
            HSL_HSLA,
            ColorParsersEbnfParserCombinatorGrammarTransformer::transformFunctionName
        );
        identifierToTransformer.put(
            HSV_HSVA,
            ColorParsersEbnfParserCombinatorGrammarTransformer::transformFunctionName
        );

        identifierToTransformer.put(
            RGB_RGBA_FUNCTION,
            ColorParsersColorFunctionFunctionParserTokenColorFunctionParserTokenVisitor::transform
        );
        identifierToTransformer.put(
            HSL_HSLA_FUNCTION,
            ColorParsersColorFunctionFunctionParserTokenColorFunctionParserTokenVisitor::transform
        );
        identifierToTransformer.put(
            HSV_HSVA_FUNCTION,
            ColorParsersColorFunctionFunctionParserTokenColorFunctionParserTokenVisitor::transform
        );

        identifierToTransformer.put(
            SEPARATOR,
            ColorParsersEbnfParserCombinatorGrammarTransformer::transformSeparator
        );

        identifierToTransformer.put(
            WHITESPACE,
            ColorParsersEbnfParserCombinatorGrammarTransformer::transformWhitespace
        );

        this.identifierToTransformer = identifierToTransformer;
    }

    private final static EbnfIdentifierName PARENTHESIS_CLOSE = EbnfIdentifierName.with("PARENTHESIS_CLOSE");
    private final static EbnfIdentifierName PARENTHESIS_OPEN = EbnfIdentifierName.with("PARENTHESIS_OPEN");

    private final static EbnfIdentifierName PERCENTAGE = EbnfIdentifierName.with("PERCENTAGE");

    private final static EbnfIdentifierName RGB_RGBA = EbnfIdentifierName.with("RGB_RGBA");
    private final static EbnfIdentifierName HSL_HSLA = EbnfIdentifierName.with("HSL_HSLA");
    private final static EbnfIdentifierName HSV_HSVA = EbnfIdentifierName.with("HSV_HSVA");

    private final static EbnfIdentifierName RGB_RGBA_FUNCTION = EbnfIdentifierName.with("RGB_RGBA_FUNCTION");
    private final static EbnfIdentifierName HSL_HSLA_FUNCTION = EbnfIdentifierName.with("HSL_HSLA_FUNCTION");
    private final static EbnfIdentifierName HSV_HSVA_FUNCTION = EbnfIdentifierName.with("HSV_HSVA_FUNCTION");

    private final static EbnfIdentifierName SEPARATOR = EbnfIdentifierName.with("SEPARATOR");
    private final static EbnfIdentifierName WHITESPACE = EbnfIdentifierName.with("WHITESPACE");

    @Override
    public Parser<ParserContext> rule(final RuleEbnfParserToken token,
                                      final Parser<ParserContext> parser) {
        return this.transformIdentifier(
            token.identifier(),
            parser
        );
    }

    @Override
    public Parser<ParserContext> alternatives(final AlternativeEbnfParserToken token, final Parser<ParserContext> parser) {
        return parser;
    }

    @Override
    public Parser<ParserContext> concatenation(final ConcatenationEbnfParserToken token, final Parser<ParserContext> parser) {
        return parser;
    }

    @Override
    public Parser<ParserContext> exception(final ExceptionEbnfParserToken token, final Parser<ParserContext> parser) {
        return parser;
    }

    @Override
    public Parser<ParserContext> group(final GroupEbnfParserToken token, final Parser<ParserContext> parser) {
        return parser;
    }

    @Override
    public Parser<ParserContext> identifier(final IdentifierEbnfParserToken token, final Parser<ParserContext> parser) {
        return this.transformIdentifier(
            token,
            parser
        );
    }

    private Parser<ParserContext> transformIdentifier(final IdentifierEbnfParserToken token,
                                                      final Parser<ParserContext> parser) {
        Parser<ParserContext> transformed = parser;

        final EbnfIdentifierName identifierName = token.value();

        // #remove and not #get used to ensure parser is only transformed ONCE
        final BiFunction<ParserToken, ParserContext, ParserToken> transformer = this.identifierToTransformer.remove(identifierName);
        if (null != transformer) {
            transformed = parser.transform(transformer);
        }

        return transformed;
    }

    private final Map<EbnfIdentifierName, BiFunction<ParserToken, ParserContext, ParserToken>> identifierToTransformer;

    private static ParserToken transformFunctionName(final ParserToken token,
                                                     final ParserContext context) {
        final StringParserToken string = Cast.to(token);
        return ColorFunctionParserToken.functionName(string.value(), string.text());
    }

    private static ParserToken transformParenthesisClose(final ParserToken token,
                                                         final ParserContext context) {
        final StringParserToken string = Cast.to(token);
        return ColorFunctionParserToken.parenthesisCloseSymbol(string.value(), string.text());
    }

    private static ParserToken transformParenthesisOpen(final ParserToken token,
                                                        final ParserContext context) {
        final StringParserToken string = Cast.to(token);
        return ColorFunctionParserToken.parenthesisOpenSymbol(string.value(), string.text());
    }

    private static ParserToken transformSeparator(final ParserToken token,
                                                  final ParserContext context) {
        final StringParserToken string = Cast.to(token);
        return ColorFunctionParserToken.separatorSymbol(string.value(), string.text());
    }

    private static ParserToken transformWhitespace(final ParserToken token,
                                                   final ParserContext context) {
        final StringParserToken string = Cast.to(token);
        return ColorFunctionParserToken.whitespace(string.value(), string.text());
    }

    @Override
    public Parser<ParserContext> optional(final OptionalEbnfParserToken token,
                                          final Parser<ParserContext> parser) {
        return parser;
    }

    @Override
    public Parser<ParserContext> range(final RangeEbnfParserToken token,
                                       final String beginText,
                                       final String endText) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Parser<ParserContext> repeated(final RepeatedEbnfParserToken token,
                                          final Parser<ParserContext> parser) {
        return parser;
    }

    @Override
    public Parser<ParserContext> terminal(final TerminalEbnfParserToken token,
                                          final Parser<ParserContext> parser) {
        return parser;
    }
}
