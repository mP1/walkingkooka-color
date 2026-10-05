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
import walkingkooka.ContextTesting;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface ColorContextTesting2<C extends ColorContext> extends ContextTesting<C>,
    ColorContextTesting {

    @Test
    default void testIndexedColorWithNegativeFails() {
        assertThrows(
            IllegalArgumentException.class,
            () -> this.createContext()
                .indexedColor(-1)
        );
    }

    @Test
    default void testLookupColorWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .lookupColor(null)
        );
    }

    @Test
    default void testNamedColorWithNullStringFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .namedColor(null)
        );
    }

    @Test
    default void testNamedColorWithEmptyStringFails() {
        assertThrows(
            IllegalArgumentException.class,
            () -> this.createContext()
                .namedColor("")
        );
    }

    @Test
    default void testParseColorWithNullStringFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createContext()
                .parseColor(null)
        );
    }

    @Test
    default void testParseColorWithEmptyStringFails() {
        assertThrows(
            IllegalArgumentException.class,
            () -> this.createContext()
                .parseColor("")
        );
    }

    // class............................................................................................................

    @Override
    default String typeNameSuffix() {
        return ColorContext.class.getSimpleName();
    }
}
