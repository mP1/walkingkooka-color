/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
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
import walkingkooka.HasValueTesting;
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.ToStringTesting;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.text.HasTextTesting;
import walkingkooka.tree.json.marshall.JsonNodeMarshallerTesting;

import java.util.Optional;

public abstract class ColorTestCase<C extends Color> implements ClassTesting2<C>,
    HashCodeEqualsDefinedTesting2<C>,
    JsonNodeMarshallerTesting<C>,
    HasTextTesting,
    ToStringTesting<C>,
    HasValueTesting {

    ColorTestCase() {
        super();
    }

    @Test
    public final void testIsColorClass() {
        this.checkEquals(
            Color.isColorClass(this.createColor().getClass()),
            true
        );
    }

    @Test
    public final void testIsHsl() {
        final C color = this.createColor();
        this.checkEquals(color instanceof HslColor,
            color.isHsl(),
            () -> "isHsl " + color);
    }

    @Test
    public final void testIsHsv() {
        final C color = this.createColor();
        this.checkEquals(color instanceof HsvColor,
            color.isHsv(),
            () -> "isHsv " + color);
    }

    @Test
    public final void testIsIndexed() {
        final C color = this.createColor();
        this.checkEquals(
            color instanceof IndexedColor,
            color.isIndexed(),
            () -> "isIndexed " + color
        );
    }

    @Test
    public final void testIsRgb() {
        final C color = this.createColor();
        this.checkEquals(color instanceof RgbColor,
            color.isRgb(),
            () -> "isRgb " + color);
    }

    final void toWebNameAndCheck(final Color color) {
        this.toWebNameAndCheck(
            color,
            Optional.empty()
        );
    }

    final void toWebNameAndCheck(final Color color,
                                 final WebColorName expected) {
        this.toWebNameAndCheck(
            color,
            Optional.of(expected)
        );
    }

    final void toWebNameAndCheck(final Color color,
                                 final Optional<WebColorName> expected) {
        this.checkEquals(
            expected,
            color.toWebColorName(),
            () -> color + " web rgb name");
    }

    // factory..........................................................................................................

    abstract C createColor();

    @Override
    public final C createObject() {
        return this.createColor();
    }

    // HasJsonNodeTesting..............................................................................................

    @Override
    public final C createJsonNodeMarshallingValue() {
        return this.createColor();
    }

    // ParseStringTesting .............................................................................................

    public final RuntimeException parseStringFailedExpected(final RuntimeException expected) {
        return expected;
    }

    public final Class<? extends RuntimeException> parseStringFailedExpected(final Class<? extends RuntimeException> expected) {
        return expected;
    }
}
