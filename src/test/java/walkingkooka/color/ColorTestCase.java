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
import walkingkooka.text.HasTextTesting;
import walkingkooka.tree.json.marshall.JsonNodeMarshallingTesting;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class ColorTestCase<C extends Color> implements ClassTesting2<C>,
    HashCodeEqualsDefinedTesting2<C>,
    JsonNodeMarshallingTesting<C>,
    HasTextTesting,
    ToStringTesting<C> {

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

    // mix..............................................................................................................

    @Test
    public void testMixWithNullColorFails() {
        assertThrows(
            NullPointerException.class,
            () -> this.createColor()
                .mix(
                    null,
                    0
                )
        );
    }

    @Test
    public void testMixWithAmountLessThanZeroFails() {
        assertThrows(
            IllegalArgumentException.class,
            () -> this.createColor()
                .mix(
                    Color.BLACK,
                    -0.01f
                )
        );
    }

    @Test
    public void testMixWithAmountGreaterThanOneFails() {
        assertThrows(
            IllegalArgumentException.class,
            () -> this.createColor()
                .mix(
                    Color.BLACK,
                    1.01f
                )
        );
    }

    @Test
    public void testMixZeroAmount() {
        final C color = this.createColor();

        assertSame(
            color,
            color.mix(
                color.invert(),
                0
            )
        );
    }

    @Test
    public void testMixZeroOne() {
        final Color color = this.createColor()
            .invert();

        assertSame(
            color,
            this.createColor()
                .mix(
                    color,
                    1.0f
                )
        );
    }

    final void mixAndCheck(final Color color,
                           final Color other,
                           final float amount,
                           final Color expected) {
        // compare using toString because Hsl floats might be slightly different after mixing.
        this.checkEquals(
            expected.toString(),
            color.mix(
                other,
                amount
            ).toString(),
            color + " mix " + other + " " + amount
        );
    }

    // invert...........................................................................................................

    @Test
    public final void testInvert() {
        final C color = this.createColor();
        final Color inverted = color.invert();
        this.checkNotEquals(
            color,
            inverted
        );
    }

    @Test
    public final void testInvertSameType() {
        final C color = this.createColor();
        final Color inverted = color.invert();
        this.checkEquals(
            color.getClass(),
            inverted.getClass()
        );
    }

    @Test
    public final void testInvertInvertRoundtrip() {
        final C color = this.createColor();
        final Color inverted = color.invert();
        this.invertAndCheck(
            color,
            inverted
        );
    }

    final void invertAndCheck(final Color color,
                              final Color inverted) {
        this.checkEquals(
            color,
            inverted.invert(),
            () -> color + " invert"
        );
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
