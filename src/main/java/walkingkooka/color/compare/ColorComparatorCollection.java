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
import walkingkooka.predicate.character.CharPredicates;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.text.CharSequences;
import walkingkooka.text.cursor.TextCursor;
import walkingkooka.text.cursor.TextCursors;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserContext;
import walkingkooka.text.cursor.parser.ParserContexts;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.text.cursor.parser.Parsers;

import java.util.Comparator;

/**
 * A collection of {@link Color} {@link Comparator} that may be selected by name separated by spaces.
 * <pre>
 * redReversed
 * red green blue hslHue hslSaturation hslLightness hsvHue hsvSaturation hsvValue
 * </pre>
 */
final class ColorComparatorCollection extends ColorComparator {

    static ColorComparatorCollection parse(final String text) {
        final TextCursor cursor = TextCursors.charSequence(text);

        Comparator<Color> comparator = null;

        while (cursor.isNotEmpty()) {
            skipSpaces(cursor);
            if (cursor.isEmpty()) {
                break;
            }

            final Comparator<Color> next = comparatorName(cursor);
            if (null == comparator) {
                comparator = next;
            } else {
                comparator = comparator.thenComparing(next);
            }
        }

        if (comparator == null) {
            throw new IllegalArgumentException("Missing color components");
        }

        return new ColorComparatorCollection(
            text,
            comparator
        );
    }

    private final static ParserContext CONTEXT = ParserContexts.fake();

    /**
     * Consumes any spaces that may be at the current position.
     */
    private static void skipSpaces(final TextCursor cursor) {
        SPACES.parse(
            cursor,
            CONTEXT
        );
    }

    private final static Parser<ParserContext> SPACES = Parsers.charPredicateString(
        CharPredicates.whitespace(),
        1,
        65536
    );

    private static Comparator<Color> comparatorName(final TextCursor cursor) {
        final String comparatorNameMaybeReversed = COMPARATOR_NAME.parse(
                cursor,
                CONTEXT
            ).map(ParserToken::text)
            .orElse("");

        if (comparatorNameMaybeReversed.isEmpty()) {
            throw cursor.lineInfo()
                .invalidCharacterException()
                .get();
        }

        final String comparatorName = CaseSensitivity.INSENSITIVE.endsWith(comparatorNameMaybeReversed, REVERSED) ?
            CharSequences.subSequence(
                comparatorNameMaybeReversed,
                0,
                -REVERSED.length()
            ).toString() :
            comparatorNameMaybeReversed;

        Comparator<Color> comparator;

        switch (comparatorName) {
            case "red":
                comparator = ColorComparators.red();
                break;
            case "green":
                comparator = ColorComparators.green();
                break;
            case "blue":
                comparator = ColorComparators.blue();
                break;
            case "hslHue":
                comparator = ColorComparators.hslHue();
                break;
            case "hslSaturation":
                comparator = ColorComparators.hslSaturation();
                break;
            case "hslLightness":
                comparator = ColorComparators.hslLightness();
                break;
            case "hsvHue":
                comparator = ColorComparators.hsvHue();
                break;
            case "hsvSaturation":
                comparator = ColorComparators.hsvSaturation();
                break;
            case "hsvValue":
                comparator = ColorComparators.hsvValue();
                break;
            default:
                throw new IllegalArgumentException("Unknown color component " + CharSequences.quoteAndEscape(comparatorName));
        }

        if (false == comparatorName.equals(comparatorNameMaybeReversed)) {
            comparator = comparator.reversed();
        }

        return comparator;
    }

    private final static Parser<ParserContext> COMPARATOR_NAME = Parsers.charPredicateString(
        CharPredicates.letter(),
        1,
        20
    );

    private final static String REVERSED = "reversed";

    // @VisibleForTesting
    ColorComparatorCollection(final String text,
                              final Comparator<Color> comparator) {
        super();

        this.text = text;
        this.comparator = comparator;
    }

    // Comparator.......................................................................................................

    @Override
    public int compare(final Color left,
                       final Color right) {
        return this.comparator.compare(
            left,
            right
        );
    }

    private final Comparator<Color> comparator;

    // Object...........................................................................................................

//    @Override
//    public int hashCode() {
//        return Objects.hash(
//            this.text,
//            this.comparator
//        );
//    }
//
//    @Override
//    public boolean equals(final Object other) {
//        return this == other ||
//            other instanceof SpreadsheetCell &&
//                this.equals0(Cast.to(other));
//    }
//
//    private boolean equals0(final SpreadsheetCell other) {
//        return this.reference.equals(other.reference()) &&
//            this.formula.equals(other.formula()) &&
//            this.currency.equals(other.currency) &&
//            this.dateTimeSymbols.equals(other.dateTimeSymbols) &&
//            this.decimalNumberSymbols.equals(other.decimalNumberSymbols) &&
//            this.locale.equals(other.locale) &&
//            this.style.equals(other.style) &&
//            this.parser.equals(other.parser) &&
//            this.formatter.equals(other.formatter) &&
//            this.formattedValue.equals(other.formattedValue) &&
//            this.validator.equals(other.validator);
//    }

    @Override
    public String toString() {
        return this.text;
    }

    private final String text;
}
