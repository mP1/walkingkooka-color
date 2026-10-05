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

import walkingkooka.text.CharSequences;
import walkingkooka.text.printer.TreePrintableTesting;

import java.util.Optional;

public interface ColorContextTesting extends TreePrintableTesting {

    default void indexedColorAndCheck(final ColorContext context,
                                      final int index,
                                      final IndexedColor expected) {
        this.checkEquals(
            expected,
            context.indexedColor(index),
            () -> "index: " + index
        );
    }

    default void lookupColorAndCheck(final ColorContext context,
                                     final Color color) {
        this.lookupColorAndCheck(
            context,
            color,
            Optional.empty()
        );
    }

    default void lookupColorAndCheck(final ColorContext context,
                                     final Color color,
                                     final Color expected) {
        this.lookupColorAndCheck(
            context,
            color,
            Optional.of(expected)
        );
    }

    default void lookupColorAndCheck(final ColorContext context,
                                     final Color color,
                                     final Optional<Color> expected) {
        this.checkEquals(
            expected,
            context.lookupColor(color),
            color::toString
        );
    }

    default void namedColorAndCheck(final ColorContext context,
                                    final String name,
                                    final NamedColor expected) {
        this.checkEquals(
            expected,
            context.namedColor(name),
            () -> "name: " + name
        );
    }

    default void parseColorAndCheck(final ColorContext context,
                                    final String color,
                                    final Color expected) {
        this.checkEquals(
            expected,
            context.parseColor(color),
            () -> "parseColor: " + CharSequences.quoteAndEscape(color)
        );
    }
}
