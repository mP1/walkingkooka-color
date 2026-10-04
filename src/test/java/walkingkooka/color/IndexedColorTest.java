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
import walkingkooka.reflect.PublicClassTesting;
import walkingkooka.test.ParseStringTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class IndexedColorTest extends ColorTestCase<IndexedColor> implements ParseStringTesting<IndexedColor>,
    PublicClassTesting<IndexedColor> {

    // tests

    @Test
    public void testWithNegativeIndexFails() {
        assertThrows(
            IllegalArgumentException.class,
            () -> IndexedColor.with(-1)
        );
    }

    @Test
    public void testWith() {
        final IndexedColor indexedColor = this.createColor();
        this.checkEquals(
            1,
            indexedColor.index()
        );
    }

    @Override
    IndexedColor createColor() {
        return IndexedColor.with(1);
    }

    @Test
    public void testParseNegativeIntegerFails() {
        this.parseStringFails(
            "-1",
            IllegalArgumentException.class
        );
    }

    @Test
    public void testParse() {
        this.parseStringAndCheck(
            "0",
            IndexedColor.with(0)
        );
    }

    @Test
    public void testParse2() {
        this.parseStringAndCheck(
            "12",
            IndexedColor.with(12)
        );
    }

    // parse............................................................................................................

    @Override
    public IndexedColor parseString(final String text) {
        return Color.parseIndexed(text);
    }

    // Class ...........................................................................................................

    @Override
    public Class<IndexedColor> type() {
        return IndexedColor.class;
    }

    // Json.............................................................................................................

    @Override
    public IndexedColor unmarshall(final JsonNode from,
                                   final JsonNodeUnmarshallContext context) {
        return Color.unmarshallIndexed(
            from,
            context
        );
    }
}
