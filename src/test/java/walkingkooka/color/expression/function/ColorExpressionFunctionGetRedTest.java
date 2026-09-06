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

package walkingkooka.color.expression.function;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.color.Color;
import walkingkooka.color.RedRgbColorComponent;
import walkingkooka.color.RgbColor;
import walkingkooka.tree.expression.FakeExpressionEvaluationContext;

public final class ColorExpressionFunctionGetRedTest extends ColorExpressionFunctionTestCase<ColorExpressionFunctionGetRed<FakeExpressionEvaluationContext>, RedRgbColorComponent> {

    @Test
    public void testApply() {
        final RgbColor rgb = Color.parse("#123456")
            .toRgb();

        this.applyAndCheck(
            Lists.of(
                rgb
            ),
            rgb.red()
        );
    }

    @Override
    public ColorExpressionFunctionGetRed<FakeExpressionEvaluationContext> createBiFunction() {
        return ColorExpressionFunctionGetRed.instance();
    }

    @Override
    public int minimumParameterCount() {
        return 1;
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createBiFunction(),
            "getRed"
        );
    }

    // class............................................................................................................

    @Override
    public Class<ColorExpressionFunctionGetRed<FakeExpressionEvaluationContext>> type() {
        return Cast.to(ColorExpressionFunctionGetRed.class);
    }
}
