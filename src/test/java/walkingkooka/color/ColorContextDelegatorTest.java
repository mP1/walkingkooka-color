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
import walkingkooka.color.ColorContextDelegatorTest.TestColorContextDelegator;

import java.util.Objects;
import java.util.Optional;

public final class ColorContextDelegatorTest implements ColorContextTesting2<TestColorContextDelegator> {

    private final static Color NAMED = Color.named("Red");
    private final static Color LOOKUP = Color.parse("red");

    @Test
    public void testLookup() {
        this.lookupColorAndCheck(
            this.createContext(),
            NAMED,
            LOOKUP
        );
    }

    @Override
    public TestColorContextDelegator createContext() {
        return new TestColorContextDelegator();
    }

    @Override
    public Class<TestColorContextDelegator> type() {
        return TestColorContextDelegator.class;
    }

    @Override
    public void testTestNaming() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }

    final static class TestColorContextDelegator implements ColorContextDelegator {

        @Override
        public ColorContext colorContext() {
            return new ColorContext() {
                @Override
                public IndexedColor indexedColor(final int index) {
                    return Color.indexed(index);
                }

                @Override
                public Optional<Color> lookupColor(final Color color) {
                    Objects.requireNonNull(color, "color");

                    return Optional.ofNullable(
                        NAMED.equals(color) ?
                            LOOKUP :
                            null
                    );
                }

                @Override
                public NamedColor namedColor(final String name) {
                    return Color.named(name);
                }

                @Override
                public Color parseColor(final String color) {
                    return Color.parse(color);
                }
            };
        }

        @Override
        public String toString() {
            return this.getClass()
                .getSimpleName();
        }
    }
}
