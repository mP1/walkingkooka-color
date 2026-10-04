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

import walkingkooka.HasValue;
import walkingkooka.ToStringBuilder;
import walkingkooka.predicate.character.CharPredicate;
import walkingkooka.predicate.character.CharPredicates;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.text.CharSequences;
import walkingkooka.text.HasCaseSensitivity;

import java.util.Optional;

/**
 * Represents the name of a color. This is not to be confused with a {@link WebColorName}.
 */
public final class NamedColor extends Color implements HasValue<String>,
    HasCaseSensitivity {

    private final static CaseSensitivity CASE_SENSITIVITY = CaseSensitivity.INSENSITIVE;

    private final static CharPredicate INITIAL = CharPredicates.letter();
    private final static CharPredicate PART = CharPredicates.letterOrDigit();

    static NamedColor with(final String name) {
        CharPredicates.failIfNullOrEmptyOrInitialAndPartFalse(
            name,
            "name",
            INITIAL,
            PART
        );
        return new NamedColor(name);
    }

    NamedColor(final String name) {
        super();
        this.name = name;
    }

    // HasValue.........................................................................................................

    @Override
    public String value() {
        return this.name;
    }

    private final String name;

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
        return CASE_SENSITIVITY.hash(this.name);
    }

    @Override
    boolean equals0(final Object other) {
        return CASE_SENSITIVITY.equals(
            this.name,
            ((NamedColor) other).name
        );
    }

    @Override
    public void buildToString(final ToStringBuilder toStringBuilder) {
        toStringBuilder.append(
            CharSequences.quoteAndEscape(this.name)
        );
    }

    // HasCaseSensitivity................................................................................................

    @Override
    public CaseSensitivity caseSensitivity() {
        return CASE_SENSITIVITY;
    }
}
