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

public final class AlphaRgbColorComponentTest extends RgbColorComponentTestCase<AlphaRgbColorComponent> {

    @Test
    public void testEqualsDifferentValue() {
        this.checkNotEquals(AlphaRgbColorComponent.with(VALUE2));
    }

    @Test
    public void testEqualsDifferentRed() {
        this.checkNotEquals(RgbColorComponent.red(VALUE));
    }

    @Test
    public void testEqualsDifferentGreen() {
        this.checkNotEquals(RgbColorComponent.green(VALUE));
    }

    @Test
    public void testEqualsDifferentBlue() {
        this.checkNotEquals(RgbColorComponent.blue(VALUE));
    }

    @Override
    AlphaRgbColorComponent createColorComponent(final byte value) {
        return AlphaRgbColorComponent.with(value);
    }

    @Override
    public AlphaRgbColorComponent parseString(final String text) {
        return RgbColorComponent.parseAlpha(text);
    }

    @Override
    public Class<AlphaRgbColorComponent> type() {
        return AlphaRgbColorComponent.class;
    }
}
