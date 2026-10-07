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

import walkingkooka.Context;

import java.util.Optional;

/**
 * A {@link Context} that includes further operations including a palette lookup for {@link IndexedColor} and similar.
 */
public interface ColorContext extends Context {

    Optional<Color> NO_LOOKUP_COLOR = Optional.empty();

    /**
     * Creates a {@link IndexedColor}.
     */
    IndexedColor indexedColor(final int index);

    /**
     * Used to resolve {@link IndexedColor} and {@link NamedColor} into an actual {@link Color}
     */
    Optional<Color> lookupColor(final Color color);

    /**
     * Creates a {@link NamedColor}.
     */
    NamedColor namedColor(final String name);

    /**
     * Parsers the color into its {@link Color} equivalent.
     */
    Color parseColor(final String color);
}
