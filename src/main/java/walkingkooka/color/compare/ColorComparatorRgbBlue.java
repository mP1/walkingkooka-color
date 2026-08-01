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

import walkingkooka.color.RgbColor;

/**
 * A {@link java.util.Comparator} that compares the BLUE component of the given {@link walkingkooka.color.Color}.
 */
final class ColorComparatorRgbBlue extends ColorComparatorRgb {

    /**
     * Singleton
     */
    final static ColorComparatorRgbBlue INSTANCE = new ColorComparatorRgbBlue();

    private ColorComparatorRgbBlue() {
        super();
    }

    @Override
    int getComponentValue(final RgbColor rgbColor) {
        return rgbColor.blue()
            .value()
            .intValue();
    }

    @Override
    public String toString() {
        return "Blue";
    }
}
