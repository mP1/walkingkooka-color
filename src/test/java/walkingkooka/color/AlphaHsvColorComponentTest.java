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

public final class AlphaHsvColorComponentTest extends HsvColorComponentTestCase<AlphaHsvColorComponent> {

    @Test
    public void testEqualsDifferentLightness() {
        this.checkNotEquals(AlphaHsvColorComponent.with(0));
    }

    @Test
    public void testToString0() {
        this.toStringAndCheck(AlphaHsvColorComponent.with(0), "0%");
    }

    @Test
    public void testToStringHalf() {
        this.toStringAndCheck(AlphaHsvColorComponent.with(0.5f), "50%");
    }

    @Test
    public void testToStringOne() {
        this.toStringAndCheck(AlphaHsvColorComponent.with(1.0f), "100%");
    }

    @Override
    AlphaHsvColorComponent createHsvComponent(final float value) {
        return AlphaHsvColorComponent.with(value);
    }

    @Override
    float value() {
        return 0.25f;
    }

    @Override
    float min() {
        return AlphaHsvColorComponent.MIN;
    }

    @Override
    float max() {
        return AlphaHsvColorComponent.MAX;
    }

    @Override
    public Class<AlphaHsvColorComponent> type() {
        return AlphaHsvColorComponent.class;
    }
}
