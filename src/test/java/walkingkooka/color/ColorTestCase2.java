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
import walkingkooka.reflect.ThrowableTesting;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class ColorTestCase2<C extends Color> extends ColorTestCase<C>
    implements ThrowableTesting {

    ColorTestCase2() {
        super();
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

    // mix..............................................................................................................

    @Test
    public final void testMixWithNullColorFails() {
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
    public final void testMixWithAmountLessThanZeroFails() {
        this.getMessageAndCheck(
            assertThrows(
                IllegalArgumentException.class,
                () -> this.createColor()
                    .mix(
                        Color.BLACK,
                        -0.01f
                    )
            ),
            "Invalid amount -0.01 not between 0.0 and 1.0"
        );
    }

    @Test
    public final void testMixWithAmountGreaterThanOneFails() {
        this.getMessageAndCheck(
            assertThrows(
                IllegalArgumentException.class,
                () -> this.createColor()
                    .mix(
                        Color.BLACK,
                        1.01f
                    )
            ),
            "Invalid amount 1.01 not between 0.0 and 1.0"
        );
    }

    @Test
    public final void testMixZeroAmount() {
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
    public final void testMixZeroOne() {
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
}
