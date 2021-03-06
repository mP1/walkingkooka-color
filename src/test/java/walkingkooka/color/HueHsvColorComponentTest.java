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

public final class HueHsvColorComponentTest extends HsvColorComponentTestCase<HueHsvColorComponent> {

    @Test
    public void testEqualsDifferentValue() {
        this.checkNotEquals(HueHsvColorComponent.with(0));
    }

    @Test
    public void testToString0() {
        this.toStringAndCheck(HueHsvColorComponent.with(0), "0");
    }

    @Test
    public void testToStringTwoHalf() {
        this.toStringAndCheck(HueHsvColorComponent.with(2.5f), "3");
    }

    @Test
    public void testToString359() {
        this.toStringAndCheck(HueHsvColorComponent.with(359), "359");
    }

    @Override
    HueHsvColorComponent createHsvComponent(final float value) {
        return HueHsvColorComponent.with(value);
    }

    @Override
    float value() {
        return 359;
    }

    @Override
    float min() {
        return HueHsvColorComponent.MIN;
    }

    @Override
    float max() {
        return HueHsvColorComponent.MAX;
    }

    @Override
    public Class<HueHsvColorComponent> type() {
        return HueHsvColorComponent.class;
    }
}
