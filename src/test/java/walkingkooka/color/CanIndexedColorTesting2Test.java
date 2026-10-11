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
import walkingkooka.color.CanIndexedColorTesting2Test.TestCanIndexedColor;
import walkingkooka.reflect.PublicClassTesting;

public final class CanIndexedColorTesting2Test implements CanIndexedColorTesting2<TestCanIndexedColor>,
    PublicClassTesting<CanIndexedColorTesting2<TestCanIndexedColor>> {

    @Override
    public TestCanIndexedColor createCanIndexedColor() {
        return new TestCanIndexedColor();
    }

    // class............................................................................................................

    @Override
    public Class<CanIndexedColorTesting2<TestCanIndexedColor>> type() {
        return Cast.to(CanIndexedColorTesting2.class);
    }

    final static class TestCanIndexedColor implements CanIndexedColor {
        @Override
        public IndexedColor indexedColor(final int index) {
            if (index < 0) {
                throw new IllegalArgumentException("Invalid index " + index);
            }
            throw new UnsupportedOperationException();
        }
    }
}
