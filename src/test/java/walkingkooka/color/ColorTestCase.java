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
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.ToStringTesting;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.tree.json.marshall.JsonNodeMarshallingTesting;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

public abstract class ColorTestCase<C extends Color> implements ClassTesting2<C>,
        HashCodeEqualsDefinedTesting2<C>,
        JsonNodeMarshallingTesting<C>,
        ToStringTesting<C> {

    ColorTestCase() {
        super();
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
    public final void testIsRgb() {
        final C color = this.createColor();
        this.checkEquals(color instanceof RgbColor,
                color.isRgb(),
                () -> "isRgb " + color);
    }


    @Test
    public final void testToHsl() {
        final C color = this.createColor();
        final HslColor hsl = color.toHsl();

        if (color instanceof HslColor) {
            assertSame(color,
                    hsl,
                    () -> color + " toHsl()");
        } else {
            assertNotSame(color,
                    hsl,
                    () -> color + " toHsl()");
        }
    }

    @Test
    public final void testToHsv() {
        final C color = this.createColor();
        final HsvColor hsv = color.toHsv();

        if (color instanceof HsvColor) {
            assertSame(color,
                    hsv,
                    () -> color + " toHsv()");
        } else {
            assertNotSame(color,
                    hsv,
                    () -> color + " toHsv()");
        }
    }

    @Test
    public final void testToRgb() {
        final C color = this.createColor();
        final RgbColor rgb = color.toRgb();

        if (color instanceof RgbColor) {
            assertSame(color,
                    rgb,
                    () -> color + " toRgb()");
        } else {
            assertNotSame(color,
                    rgb,
                    () -> color + " toRgb()");
        }
    }

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
