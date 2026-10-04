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

package walkingkooka.color;

import org.junit.jupiter.api.Test;
import walkingkooka.InvalidCharacterException;
import walkingkooka.reflect.PublicClassTesting;
import walkingkooka.test.ParseStringTesting;
import walkingkooka.text.CharSequences;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class NamedColorTest extends ColorTestCase<NamedColor> implements ParseStringTesting<NamedColor>,
    PublicClassTesting<NamedColor> {

    private final static String VALUE = "black";

    // tests

    @Test
    public void testWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> NamedColor.with(null)
        );
    }

    @Test
    public void testWithEmptyStringFails() {
        assertThrows(
            IllegalArgumentException.class,
            () -> NamedColor.with("")
        );
    }

    @Test
    public void testWith() {
        final NamedColor namedColor = this.createColor();
        this.valueAndCheck(
            namedColor,
            VALUE
        );
    }

    @Override
    NamedColor createColor() {
        return NamedColor.with(VALUE);
    }

    @Test
    public void testParseMissingOpeningQuoteFails() {
        this.parseStringFails(
            "HelloWorld\"",
            InvalidCharacterException.class
        );
    }

    @Test
    public void testParseMissingClosingQuoteFails() {
        this.parseStringFails(
            "\"HelloWorld",
            InvalidCharacterException.class
        );
    }

    @Test
    public void testParse() {
        this.parseStringAndCheck(
            CharSequences.quoteAndEscape(VALUE)
                .toString(),
            NamedColor.with(VALUE)
        );
    }

    // parse............................................................................................................

    @Override
    public NamedColor parseString(final String text) {
        return Color.parseNamed(text);
    }

    // Class ...........................................................................................................

    @Override
    public Class<NamedColor> type() {
        return NamedColor.class;
    }

    @Override
    public void testAllConstructorsVisibility() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testIfClassIsFinalIfAllConstructorsArePrivate() {
        throw new UnsupportedOperationException();
    }

    // Json.............................................................................................................

    @Override
    public NamedColor unmarshall(final JsonNode from,
                                 final JsonNodeUnmarshallContext context) {
        return Color.unmarshallNamed(
            from,
            context
        );
    }
}
