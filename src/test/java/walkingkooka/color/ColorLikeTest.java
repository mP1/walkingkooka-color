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
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;

public final class ColorLikeTest implements ClassTesting2<ColorLike> {

    @Test
    public void testIsColorLikeClassWithNull() {
        this.isColorLikeClassAndCheck(
            null,
            false
        );
    }

    @Test
    public void testIsColorLikeClassWithUnrelatedType() {
        this.isColorLikeClassAndCheck(
            this.getClass(),
            false
        );
    }

    @Test
    public void testIsColorLikeClassWithHslColor() {
        this.isColorLikeClassAndCheck(
            HslColor.class,
            false
        );
    }

    @Test
    public void testIsColorLikeClassWithHsvColor() {
        this.isColorLikeClassAndCheck(
            HsvColor.class,
            false
        );
    }

    @Test
    public void testIsColorLikeClassWithRgbColor() {
        this.isColorLikeClassAndCheck(
            RgbColor.class,
            true
        );
    }

    @Test
    public void testIsColorLikeClassWithAlphaRgbColor() {
        this.isColorLikeClassAndCheck(
            AlphaRgbColor.class,
            true
        );
    }

    @Test
    public void testIsColorLikeClassWithOpaqueRgbColor() {
        this.isColorLikeClassAndCheck(
            OpaqueRgbColor.class,
            true
        );
    }

    @Test
    public void testIsColorLikeClassWithOpaqueRgbColorComponents() {
        final RgbColor rgb = Color.parseRgb("#1234");

        this.isColorLikeClassAndCheck(
            rgb.red().getClass(),
            true
        );

        this.isColorLikeClassAndCheck(
            rgb.blue().getClass(),
            true
        );

        this.isColorLikeClassAndCheck(
            rgb.green().getClass(),
            true
        );

        this.isColorLikeClassAndCheck(
            rgb.alpha().getClass(),
            true
        );
    }

    @Test
    public void testIsColorLikeClassWithHslColorComponents() {
        final HslColor hsl = Color.parseRgb("#1234").toHsl();

        this.isColorLikeClassAndCheck(
            hsl.getClass(),
            false
        );

        this.isColorLikeClassAndCheck(
            hsl.hue().getClass(),
            false
        );

        this.isColorLikeClassAndCheck(
            hsl.saturation().getClass(),
            false
        );

        this.isColorLikeClassAndCheck(
            hsl.lightness().getClass(),
            false
        );

        this.isColorLikeClassAndCheck(
            hsl.alpha().getClass(),
            false
        );
    }

    @Test
    public void testIsColorLikeClassWithHsvColorComponents() {
        final HsvColor hsv = Color.parseRgb("#1234").toHsv();

        this.isColorLikeClassAndCheck(
            hsv.getClass(),
            false
        );

        this.isColorLikeClassAndCheck(
            hsv.hue().getClass(),
            false
        );

        this.isColorLikeClassAndCheck(
            hsv.saturation().getClass(),
            false
        );

        this.isColorLikeClassAndCheck(
            hsv.value().getClass(),
            false
        );

        this.isColorLikeClassAndCheck(
            hsv.alpha().getClass(),
            false
        );
    }

    private void isColorLikeClassAndCheck(final Class<?> type,
                                          final boolean expected) {
        this.checkEquals(
            expected,
            ColorLike.isColorLikeClass(type)
        );
    }

    // class............................................................................................................

    @Override
    public Class<ColorLike> type() {
        return ColorLike.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
