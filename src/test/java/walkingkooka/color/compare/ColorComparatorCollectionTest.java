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

package walkingkooka.color.compare;

import org.junit.jupiter.api.Test;
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.ToStringTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.color.Color;
import walkingkooka.test.ParseStringTesting;

import java.util.List;

public final class ColorComparatorCollectionTest extends ColorComparatorTestCase<ColorComparatorCollection>
    implements ParseStringTesting<ColorComparatorCollection>,
    HashCodeEqualsDefinedTesting2<ColorComparatorCollection>,
    ToStringTesting<ColorComparatorCollection> {

    @Override
    public ColorComparatorCollection createComparator() {
        return new ColorComparatorCollection(
            "red",
            ColorComparators.red()
        );
    }

    // parse............................................................................................................

    @Test
    public void testParseSpacesFails() {
        this.parseStringFails(
            " ",
            new IllegalArgumentException("Missing color components")
        );
    }

    @Test
    public void testParseSpacesFails2() {
        this.parseStringFails(
            "  ",
            new IllegalArgumentException("Missing color components")
        );
    }

    private final static Color RED1 = Color.parse("#1FF");

    private final static Color RED2 = Color.parse("#2FF");

    private final static Color RED3 = Color.parse("#3FF");

    private final static Color GREEN1 = Color.parse("#F1F");

    private final static Color GREEN2 = Color.parse("#F2F");

    private final static Color GREEN3 = Color.parse("#F3F");

    private final static Color BLUE1 = Color.parse("#FF1");

    private final static Color BLUE2 = Color.parse("#FF2");

    private final static Color BLUE3 = Color.parse("#FF3");

    @Test
    public void testParseRed() {
        this.parseStringAndSortColors(
            "red",
            Lists.of(
                RED3,
                RED1,
                RED2
            ),
            Lists.of(
                RED1,
                RED2,
                RED3
            )
        );
    }

    @Test
    public void testParseRedReversed() {
        this.parseStringAndSortColors(
            "redReversed",
            Lists.of(
                RED3,
                RED1,
                RED2
            ),
            Lists.of(
                RED3,
                RED2,
                RED1
            )
        );
    }

    @Test
    public void testParseGreen() {
        this.parseStringAndSortColors(
            "green",
            Lists.of(
                GREEN3,
                GREEN1,
                GREEN2
            ),
            Lists.of(
                GREEN1,
                GREEN2,
                GREEN3
            )
        );
    }

    @Test
    public void testParseBlue() {
        this.parseStringAndSortColors(
            "blue",
            Lists.of(
                BLUE3,
                BLUE1,
                BLUE2
            ),
            Lists.of(
                BLUE1,
                BLUE2,
                BLUE3
            )
        );
    }

    @Test
    public void testParseRedThenGreen() {
        final Color color1 = Color.parse("#12F");
        final Color color2 = Color.parse("#13F");
        final Color color3 = Color.parse("#21F");

        this.parseStringAndSortColors(
            "red green",
            Lists.of(
                color2,
                color1,
                color3
            ),
            Lists.of(
                color1,
                color2,
                color3
            )
        );
    }

    @Test
    public void testParseRedThenGreenThenBlue() {
        final Color color1 = Color.parse("#123");
        final Color color2 = Color.parse("#124");
        final Color color3 = Color.parse("#125");

        this.parseStringAndSortColors(
            "red green blue",
            Lists.of(
                color2,
                color1,
                color3
            ),
            Lists.of(
                color1,
                color2,
                color3
            )
        );
    }

    @Test
    public void testParseRedThenGreenThenBlueReversed() {
        final Color color1 = Color.parse("#123");
        final Color color2 = Color.parse("#124");
        final Color color3 = Color.parse("#125");

        this.parseStringAndSortColors(
            "red green blueReversed",
            Lists.of(
                color2,
                color1,
                color3
            ),
            Lists.of(
                color3,
                color2,
                color1
            )
        );
    }

    @Test
    public void testParseHslHue() {
        final Color color1 = Color.parse("hsl(0,0%,0%)");
        final Color color2 = Color.parse("hsl(1,0%,0%)");
        final Color color3 = Color.parse("hsl(2,0%,0%)");

        this.parseStringAndSortColors(
            "hslHue",
            Lists.of(
                color2,
                color1,
                color3
            ),
            Lists.of(
                color1,
                color2,
                color3
            )
        );
    }

    @Test
    public void testParseHslSaturation() {
        final Color color1 = Color.parse("hsl(1,2%,0%)");
        final Color color2 = Color.parse("hsl(1,3%,0%)");
        final Color color3 = Color.parse("hsl(1,4%,0%)");

        this.parseStringAndSortColors(
            "hslSaturation",
            Lists.of(
                color2,
                color1,
                color3
            ),
            Lists.of(
                color1,
                color2,
                color3
            )
        );
    }

    @Test
    public void testParseHslLightness() {
        final Color color1 = Color.parse("hsl(1,2%,3%)");
        final Color color2 = Color.parse("hsl(1,2%,4%)");
        final Color color3 = Color.parse("hsl(1,2%,5%)");

        this.parseStringAndSortColors(
            "hslLightness",
            Lists.of(
                color2,
                color1,
                color3
            ),
            Lists.of(
                color1,
                color2,
                color3
            )
        );
    }

    @Test
    public void testParseHsvHue() {
        final Color color1 = Color.parse("hsv(0,0%,0%)");
        final Color color2 = Color.parse("hsv(1,0%,0%)");
        final Color color3 = Color.parse("hsv(2,0%,0%)");

        this.parseStringAndSortColors(
            "hsvHue",
            Lists.of(
                color2,
                color1,
                color3
            ),
            Lists.of(
                color1,
                color2,
                color3
            )
        );
    }

    @Test
    public void testParseHsvSaturation() {
        final Color color1 = Color.parse("hsv(1,2%,0%)");
        final Color color2 = Color.parse("hsv(1,3%,0%)");
        final Color color3 = Color.parse("hsv(1,4%,0%)");

        this.parseStringAndSortColors(
            "hsvSaturation",
            Lists.of(
                color2,
                color1,
                color3
            ),
            Lists.of(
                color1,
                color2,
                color3
            )
        );
    }

    @Test
    public void testParseHsvValue() {
        final Color color1 = Color.parse("hsv(1,2%,3%)");
        final Color color2 = Color.parse("hsv(1,2%,4%)");
        final Color color3 = Color.parse("hsv(1,2%,5%)");

        this.parseStringAndSortColors(
            "hsvValue",
            Lists.of(
                color2,
                color1,
                color3
            ),
            Lists.of(
                color1,
                color2,
                color3
            )
        );
    }

    private void parseStringAndSortColors(final String text,
                                          final List<Color> unsorted,
                                          final List<Color> expected) {
        final List<Color> sorted = Lists.array();
        sorted.addAll(unsorted);
        sorted.sort(
            ColorComparatorCollection.parse(text)
        );

        this.checkEquals(
            expected,
            sorted,
            text
        );
    }

    @Override
    public ColorComparatorCollection parseString(final String text) {
        return ColorComparatorCollection.parse(text);
    }

    @Override
    public Class<? extends RuntimeException> parseStringFailedExpected(final Class<? extends RuntimeException> thrown) {
        return thrown;
    }

    @Override
    public RuntimeException parseStringFailedExpected(final RuntimeException thrown) {
        return thrown;
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferentTextExtraSpaces() {
        this.checkNotEquals(
            ColorComparatorCollection.parse("red  green  blue  ")
        );
    }

    @Test
    public void testEqualsDifferentText() {
        this.checkNotEquals(
            ColorComparatorCollection.parse("red")
        );
    }

    @Override
    public ColorComparatorCollection createObject() {
        return ColorComparatorCollection.parse("red green blue");
    }

    // class............................................................................................................

    @Override
    public Class<ColorComparatorCollection> type() {
        return ColorComparatorCollection.class;
    }

    @Override
    public void testAllConstructorsVisibility() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void testIfClassIsFinalIfAllConstructorsArePrivate() {
        throw new UnsupportedOperationException();
    }
}
