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

import walkingkooka.ToStringBuilder;

import java.util.Optional;

/**
 * Represents a single index color that belongs to a palette which contains the realised colors. Color operations
 * are not possible.
 */
public final class IndexedColor extends Color {

    static IndexedColor with(final int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Invalid index " + index + " < 0");
        }
        return new IndexedColor(index);
    }

    private IndexedColor(final int index) {
        super();
        this.index = index;
    }

    public int index() {
        return this.index;
    }

    private final int index;

    // Color............................................................................................................

    @Override
    public HslColor toHsl() {
        throw new UnsupportedOperationException();
    }

    @Override
    public HsvColor toHsv() {
        throw new UnsupportedOperationException();
    }

    @Override
    public RgbColor toRgb() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<WebColorName> toWebColorName() {
        return Optional.empty();
    }

    @Override
    public Color mix(final Color other,
                     float amount) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Color invert() {
        throw new UnsupportedOperationException();
    }

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.index;
    }

    @Override
    boolean equals0(final Object other) {
        return this.index == ((IndexedColor) other).index;
    }

    @Override
    public void buildToString(final ToStringBuilder toStringBuilder) {
        toStringBuilder.append(this.index);
    }
}
