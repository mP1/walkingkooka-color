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

import walkingkooka.collect.iterator.Iterators;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

final class ColorPropertiesValuesCollection extends AbstractCollection<Color> {

    static ColorPropertiesValuesCollection with(final Collection<String> collection) {
        return new ColorPropertiesValuesCollection(collection);
    }

    private ColorPropertiesValuesCollection(final Collection<String> collection) {
        super();
        this.collection = collection;
    }

    @Override
    public Iterator<Color> iterator() {
        return Iterators.mapping(
            this.collection.iterator(),
            Color::parse
        );
    }

    @Override
    public int size() {
        return this.collection.size();
    }

    private final Collection<String> collection;

    @Override
    public String toString() {
        return this.collection.toString();
    }
}
