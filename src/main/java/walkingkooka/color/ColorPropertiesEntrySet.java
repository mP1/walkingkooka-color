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
import walkingkooka.props.PropertiesPath;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

final class ColorPropertiesEntrySet extends AbstractSet<Entry<PropertiesPath, Color>> {

    static ColorPropertiesEntrySet with(final Set<Entry<PropertiesPath, String>> entries) {
        return new ColorPropertiesEntrySet(entries);
    }

    private ColorPropertiesEntrySet(final Set<Entry<PropertiesPath, String>> entries) {
        super();
        this.entries = entries;
    }

    @Override
    public Iterator<Entry<PropertiesPath, Color>> iterator() {
        return Iterators.mapping(
            this.entries.iterator(),
            (final Entry<PropertiesPath, String> entry) -> new Entry<PropertiesPath, Color>() {
                @Override
                public PropertiesPath getKey() {
                    return entry.getKey();
                }

                @Override
                public Color getValue() {
                    return Color.parse(entry.getValue());
                }

                @Override
                public Color setValue(final Color value) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public String toString() {
                    return entry.toString();
                }
            }
        );
    }

    @Override
    public int size() {
        return this.entries.size();
    }

    @Override
    public String toString() {
        return this.entries.toString();
    }

    private final Set<Entry<PropertiesPath, String>> entries;
}
