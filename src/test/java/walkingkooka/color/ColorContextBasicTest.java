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

public final class ColorContextBasicTest implements ColorContextTesting2<ColorContextBasic> {

    @Test
    public void testIndexedColor() {
        final int index = 123;

        this.indexedColorAndCheck(
            this.createContext(),
            index,
            Color.indexed(index)
        );
    }

    @Test
    public void testLookupColorWithIndexedColor() {
        this.lookupColorAndCheck(
            this.createContext(),
            Color.indexed(123)
        );
    }

    @Test
    public void testLookupColorWithNamedColor() {
        this.lookupColorAndCheck(
            this.createContext(),
            Color.named("RED")
        );
    }

    @Test
    public void testNamedColor() {
        final String name = "Red";

        this.namedColorAndCheck(
            this.createContext(),
            name,
            Color.named(name)
        );
    }

    @Test
    public void testParseColor() {
        this.parseColorAndCheck(
            this.createContext(),
            "BLACK",
            Color.BLACK
        );
    }

    @Override
    public ColorContextBasic createContext() {
        return ColorContextBasic.INSTANCE;
    }

    @Override
    public Class<ColorContextBasic> type() {
        return ColorContextBasic.class;
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
