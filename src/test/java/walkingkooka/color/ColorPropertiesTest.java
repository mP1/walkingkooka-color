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

package walkingkooka.color;

import org.junit.jupiter.api.Test;
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.ToStringTesting;
import walkingkooka.props.Properties;
import walkingkooka.props.PropertiesLikeTesting2;
import walkingkooka.props.PropertiesName;
import walkingkooka.props.PropertiesPath;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeMarshallerTesting;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static walkingkooka.color.Color.BLACK;

public final class ColorPropertiesTest implements PropertiesLikeTesting2<ColorProperties, Color>,
    HashCodeEqualsDefinedTesting2<ColorProperties>,
    JsonNodeMarshallerTesting<ColorProperties>,
    ToStringTesting<ColorProperties> {

    private final static PropertiesPath HELLO = PropertiesPath.parse("hello");

    private final static PropertiesPath WORLD = PropertiesPath.parse("world");

    @Test
    public void testWithNullPropertiesFails() {
        assertThrows(
            NullPointerException.class,
            () -> ColorProperties.with(null)
        );
    }

    @Test
    public void testGet() {
        this.getAndCheck(
            this.createPropertiesLike(),
            HELLO,
            BLACK
        );
    }

    @Test
    public void testGetOrTryAncestor() {
        this.getOrTryAncestorsAndCheck(
            this.createPropertiesLike(),
            HELLO.append(PropertiesName.with("1")),
            BLACK
        );
    }

    @Test
    public void testSet() {
        this.checkEquals(
            this.createPropertiesLike()
                .set(
                    HELLO,
                    Color.parseRgb("#123")
                ),
            ColorProperties.with(
                Properties.parse("hello=#123\nworld=WHITE")
            )
        );
    }

    @Test
    public void testRemoveUnknown() {
        final ColorProperties color = this.createPropertiesLike();

        assertSame(
            color.remove(
                PropertiesPath.parse("unknown")
            ),
            color
        );
    }

    @Test
    public void testRemove() {
        this.checkEquals(
            this.createPropertiesLike()
                .remove(
                    HELLO
                ),
            ColorProperties.with(
                Properties.parse("world=WHITE")
            )
        );
    }

    @Test
    public void testKeys() {
        this.keysAndCheck(
            this.createPropertiesLike(),
            HELLO,
            WORLD
        );
    }

    @Test
    public void testValues() {
        this.valuesAndCheck(
            this.createPropertiesLike(),
            BLACK,
            Color.WHITE
        );
    }

    @Override
    public ColorProperties createPropertiesLike() {
        return ColorProperties.with(
            Properties.parse("hello=BLACK\nworld=WHITE")
        );
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferentProperties() {
        this.checkNotEquals(
            ColorProperties.with(
                Properties.parse("hello=RED")
            )
        );
    }

    @Override
    public ColorProperties createObject() {
        return ColorProperties.with(
            Properties.parse("hello=BLACK\nworld=WHITE"));
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createObject(),
            "hello=BLACK\r\n" +
                "world=WHITE\r\n"
        );
    }

    // json.............................................................................................................

    @Override
    public ColorProperties unmarshall(final JsonNode jsonNode,
                                      final JsonNodeUnmarshallContext context) {
        return ColorProperties.unmarshall(jsonNode, context);
    }

    @Override
    public ColorProperties createJsonNodeMarshallingValue() {
        return this.createObject();
    }

    // class............................................................................................................

    @Override
    public Class<ColorProperties> type() {
        return ColorProperties.class;
    }
}
