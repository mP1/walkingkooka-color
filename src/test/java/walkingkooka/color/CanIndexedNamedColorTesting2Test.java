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

import walkingkooka.Cast;
import walkingkooka.color.CanIndexedNamedColorTesting2Test.TestCanIndexedNamedColor;
import walkingkooka.reflect.PublicClassTesting;

public final class CanIndexedNamedColorTesting2Test implements CanIndexedColorTesting2<TestCanIndexedNamedColor>,
    PublicClassTesting<CanIndexedNamedColorTesting2<TestCanIndexedNamedColor>> {

    @Override
    public TestCanIndexedNamedColor createCanIndexedColor() {
        return new TestCanIndexedNamedColor();
    }

    @Override
    public Class<CanIndexedNamedColorTesting2<TestCanIndexedNamedColor>> type() {
        return Cast.to(CanIndexedNamedColorTesting2.class);
    }

    final static class TestCanIndexedNamedColor implements CanIndexedNamedColor {
        @Override
        public IndexedColor indexedColor(final int index) {
            return Color.indexed(index);
        }

        @Override
        public NamedColor namedColor(final String name) {
            return Color.named(name);
        }
    }
}
