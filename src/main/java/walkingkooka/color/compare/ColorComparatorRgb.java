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

import walkingkooka.color.Color;
import walkingkooka.color.RgbColor;

abstract class ColorComparatorRgb extends ColorComparator {

    ColorComparatorRgb() {
        super();
    }

    @Override
    public final int compare(final Color left,
                             final Color right) {
        return this.getComponentValue(
            left.toRgb()
        ) -
            this.getComponentValue(
                right.toRgb()
            );
    }

    abstract int getComponentValue(final RgbColor rgbColor);
}
