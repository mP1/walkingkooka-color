/*
 * Copyright 2020 Miroslav Pokorny (github.com/mP1)
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

package walkingkooka.color.compare;

import walkingkooka.color.Color;
import walkingkooka.text.cursor.TextCursor;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserContext;
import walkingkooka.text.cursor.parser.ParserToken;

import java.util.Comparator;
import java.util.Optional;

final class ColorComparatorCollectionParserAndComparator {

    static ColorComparatorCollectionParserAndComparator with(final Parser<ParserContext> parser,
                                                             final Comparator<Color> comparator) {
        return new ColorComparatorCollectionParserAndComparator(
            parser,
            comparator
        );
    }

    private ColorComparatorCollectionParserAndComparator(final Parser<ParserContext> parser,
                                                         final Comparator<Color> comparator) {
        super();

        this.parser = parser;
        this.comparator = comparator;
    }

    Optional<Comparator<Color>> parse(final TextCursor cursor,
                                      final ParserContext context) {
        return this.parser.parse(
            cursor,
            context
        ).map((ParserToken ignored) -> this.comparator);
    }

    final Parser<ParserContext> parser;
    final Comparator<Color> comparator;

    // Object...........................................................................................................

    @Override
    public String toString() {
        return this.comparator.toString();
    }
}
