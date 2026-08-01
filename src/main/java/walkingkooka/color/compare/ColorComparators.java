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
import walkingkooka.reflect.PublicStaticHelper;

import java.util.Comparator;

/**
 * A collection of {@link walkingkooka.color.Color} related {@link java.util.Comparator}.
 */
public final class ColorComparators implements PublicStaticHelper {

    /**
     * {@link ColorComparatorRgbBlue}
     */
    public static Comparator<Color> blue() {
        return ColorComparatorRgbBlue.INSTANCE;
    }

    /**
     * {@link ColorComparatorRgbGreen}
     */
    public static Comparator<Color> green() {
        return ColorComparatorRgbGreen.INSTANCE;
    }

    /**
     * {@link ColorComparatorHslOrHsvSharedHslHue}
     */
    public static Comparator<Color> hslHue() {
        return ColorComparatorHslOrHsvSharedHslHue.INSTANCE;
    }

    /**
     * {@link ColorComparatorHslOrHsvSharedHslLightness}
     */
    public static Comparator<Color> hslLightness() {
        return ColorComparatorHslOrHsvSharedHslLightness.INSTANCE;
    }
    
    /**
     * {@link ColorComparatorHslOrHsvSharedHslSaturation}
     */
    public static Comparator<Color> hslSaturation() {
        return ColorComparatorHslOrHsvSharedHslSaturation.INSTANCE;
    }

    /**
     * {@link ColorComparatorHslOrHsvSharedHsvHue}
     */
    public static Comparator<Color> hsvHue() {
        return ColorComparatorHslOrHsvSharedHsvHue.INSTANCE;
    }

    /**
     * {@link ColorComparatorRgbRed}
     */
    public static Comparator<Color> red() {
        return ColorComparatorRgbRed.INSTANCE;
    }

    private ColorComparators() {
        throw new UnsupportedOperationException();
    }
}
