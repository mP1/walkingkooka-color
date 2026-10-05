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

import walkingkooka.props.Properties;
import walkingkooka.props.PropertiesLike;
import walkingkooka.props.PropertiesPath;
import walkingkooka.text.MultiLineText;
import walkingkooka.text.TextContext;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Wraps a {@link Properties} so all values are actually {@link Color}.
 * Note that equality is done using the backing {@link Properties} which is case-sensitive unlike colors which may be
 * case-insensitive.
 */
public final class ColorProperties implements PropertiesLike<Color> {

    public static ColorProperties with(final Properties properties) {
        return new ColorProperties(
            Objects.requireNonNull(properties, "properties")
        );
    }

    private ColorProperties(final Properties properties) {
        super();

        this.properties = properties;
    }

    @Override
    public Optional<Color> get(final PropertiesPath path) {
        return this.properties.get(path)
            .map(Color::parse);
    }

    @Override
    public Optional<Color> getOrTryAncestors(final PropertiesPath path) {
        return this.properties.getOrTryAncestors(path)
            .map(Color::parse);
    }

    @Override
    public ColorProperties set(final PropertiesPath path,
                               final Color color) {
        return this.setProperties(
            this.properties.set(
                path,
                color.text()
            )
        );
    }

    @Override
    public ColorProperties remove(final PropertiesPath path) {
        return this.setProperties(
            this.properties.remove(path)
        );
    }

    @Override
    public Set<Entry<PropertiesPath, Color>> entries() {
        return Set.of();
    }

    @Override
    public Set<PropertiesPath> keys() {
        return this.properties.keys();
    }

    @Override
    public Collection<Color> values() {
        return ColorPropertiesValuesCollection.with(
            this.properties.values()
        );
    }

    @Override
    public int size() {
        return this.properties.size();
    }

    @Override
    public boolean isEmpty() {
        return this.properties.isEmpty();
    }

    @Override
    public String text() {
        return this.properties.toString();
    }

    private ColorProperties setProperties(final Properties properties) {
        return this.properties.equals(properties) ?
            this :
            new ColorProperties(properties);
    }

    // HasProperties....................................................................................................

    @Override
    public Properties properties() {
        return this.properties;
    }

    private final Properties properties;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.properties.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof ColorProperties && this.equals0((ColorProperties) other);
    }

    private boolean equals0(final ColorProperties other) {
        return this.properties.equals(other.properties);
    }

    @Override
    public String toString() {
        return this.properties.toString();
    }

    // HasMultiLineText.................................................................................................

    @Override
    public MultiLineText multiLineText(final TextContext context) {
        return this.properties.multiLineText(context);
    }

    // Json.............................................................................................................

    static ColorProperties unmarshall(final JsonNode jsonNode,
                                      final JsonNodeUnmarshallContext context) {
        Objects.requireNonNull(jsonNode, "jsonNode");

        return with(
            context.unmarshall(jsonNode, Properties.class)
        );
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return context.marshall(this.properties);
    }

    static {
        //noinspection unchecked
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(ColorProperties.class),
            ColorProperties::unmarshall,
            ColorProperties::marshall,
            ColorProperties.class
        );
    }
}
