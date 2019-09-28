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

public final class GreenRgbColorComponentTest extends RgbColorComponentTestCase<GreenRgbColorComponent> {

    @Test
    public void testEqualsDifferentValue() {
        this.checkNotEquals(RgbColorComponent.green(VALUE2));
    }

    @Test
    public void testRed() {
        this.checkNotEquals(RgbColorComponent.red(VALUE));
    }

    @Test
    public void testBlue() {
        this.checkNotEquals(RgbColorComponent.blue(VALUE));
    }

    @Test
    public void testAlpha() {
        this.checkNotEquals(RgbColorComponent.alpha(VALUE));
    }

    @Override
    GreenRgbColorComponent createColorComponent(final byte value) {
        return GreenRgbColorComponent.with(value);
    }

    @Override
    public Class<GreenRgbColorComponent> type() {
        return GreenRgbColorComponent.class;
    }
}
