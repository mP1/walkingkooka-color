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

final class ColorComparatorHslOrHsvSharedHsvSaturation extends ColorComparatorHslOrHsvShared {

    final static ColorComparatorHslOrHsvSharedHsvSaturation INSTANCE = new ColorComparatorHslOrHsvSharedHsvSaturation();

    private ColorComparatorHslOrHsvSharedHsvSaturation() {
        super();
    }

    @Override
    float getComponentValue(final Color color) {
        return color.toHsv()
            .saturation()
            .value();
    }

    @Override
    public String toString() {
        return "hsvSaturation";
    }
}
