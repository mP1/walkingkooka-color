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

import walkingkooka.ToStringBuilder;
import walkingkooka.collect.list.Lists;
import walkingkooka.color.Color;
import walkingkooka.text.cursor.parser.ParserToken;

import java.util.List;
import java.util.Optional;

/**
 * Creates a {@link Color} from a {@link ColorFunctionParserToken}.
 */
final class ColorParsersComponentsColorFunctionParserTokenVisitor extends ColorFunctionParserTokenVisitor {

    static Color transform(final ParserToken token) {
        final ColorParsersComponentsColorFunctionParserTokenVisitor visitor = new ColorParsersComponentsColorFunctionParserTokenVisitor();
        visitor.accept(token);

        final List<ColorFunctionParserToken> values = visitor.values;

        return visitor.transformer.color(values.get(0).cast(ColorFunctionParserToken.class),
            values.get(1).cast(ColorFunctionParserToken.class),
            values.get(2).cast(ColorFunctionParserToken.class),
            Optional.ofNullable(values.size() == 4 ?
                values.get(3).cast(ColorFunctionParserToken.class) :
                null));
    }

    ColorParsersComponentsColorFunctionParserTokenVisitor() {
        super();
    }


    // ColorFunctionParserTokenVisitor..................................................................................

    @Override
    protected void visit(final NameColorFunctionParserToken token) {
        this.functionName = token;

        this.transformer = ColorFunctionTransformer.functionName(token);
    }

    private NameColorFunctionParserToken functionName;
    private ColorFunctionTransformer transformer;

    @Override
    protected void visit(final NumberColorFunctionParserToken token) {
        this.values.add(token);
    }

    @Override
    protected void visit(final PercentageColorFunctionParserToken token) {
        this.values.add(token);
    }

    private final List<ColorFunctionParserToken> values = Lists.array();

    @Override
    public String toString() {
        return ToStringBuilder.empty()
            .label("name")
            .value(this.functionName)
            .label("transformer")
            .value(this.transformer)
            .label("values")
            .value(this.values)
            .build();
    }
}
