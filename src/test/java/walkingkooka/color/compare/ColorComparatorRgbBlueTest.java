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
import walkingkooka.color.Color;

public final class ColorComparatorRgbBlueTest extends ColorComparatorRgbTestCase<ColorComparatorRgbBlue> {

    @Test
    public void testCompare() {
        this.compareAndCheckLess(
            Color.parseRgb("#FFFF01"),
            Color.parseRgb("#FFFF02")
        );
    }

    @Override
    public ColorComparatorRgbBlue createComparator() {
        return ColorComparatorRgbBlue.INSTANCE;
    }

    @Override
    public Class<ColorComparatorRgbBlue> type() {
        return ColorComparatorRgbBlue.class;
    }
}
