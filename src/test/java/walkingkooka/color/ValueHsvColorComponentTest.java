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

public final class ValueHsvColorComponentTest extends HsvColorComponentTestCase<ValueHsvColorComponent> {

    @Test
    public void testEqualsDifferentValue() {
        this.checkNotEquals(ValueHsvColorComponent.with(0));
    }

    @Test
    public void testToString0() {
        this.toStringAndCheck(ValueHsvColorComponent.with(0), "0%");
    }

    @Test
    public void testToStringHalf() {
        this.toStringAndCheck(ValueHsvColorComponent.with(0.5f), "50%");
    }

    @Test
    public void testToStringOne() {
        this.toStringAndCheck(ValueHsvColorComponent.with(1.0f), "100%");
    }

    @Override
    ValueHsvColorComponent createHsvComponent(final float value) {
        return ValueHsvColorComponent.with(value);
    }

    @Override
    float value() {
        return 0.75f;
    }

    @Override
    float min() {
        return ValueHsvColorComponent.MIN;
    }

    @Override
    float max() {
        return ValueHsvColorComponent.MAX;
    }

    @Override
    public Class<ValueHsvColorComponent> type() {
        return ValueHsvColorComponent.class;
    }
}
