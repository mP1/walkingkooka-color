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
import walkingkooka.ToStringTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.color.AlphaRgbColorComponent;
import walkingkooka.color.Color;
import walkingkooka.color.RgbColor;
import walkingkooka.color.RgbColorComponent;
import walkingkooka.tree.expression.FakeExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunctionTesting;

public final class ColorExpressionFunctionSetAlphaTest implements ExpressionFunctionTesting<ColorExpressionFunctionSetAlpha<FakeExpressionEvaluationContext>, RgbColor, FakeExpressionEvaluationContext>,
    ToStringTesting<ColorExpressionFunctionSetAlpha<FakeExpressionEvaluationContext>> {

    @Test
    public void testApply() {
        final RgbColor rgb = Color.parse("#123456")
            .toRgb();
        final AlphaRgbColorComponent component = RgbColorComponent.alpha((byte) 0xff);

        this.applyAndCheck(
            Lists.of(
                rgb,
                component
            ),
            rgb.set(component)
        );
    }

    @Override
    public ColorExpressionFunctionSetAlpha<FakeExpressionEvaluationContext> createBiFunction() {
        return ColorExpressionFunctionSetAlpha.instance();
    }

    @Override
    public FakeExpressionEvaluationContext createContext() {
        return new FakeExpressionEvaluationContext() {

        };
    }

    @Override
    public int minimumParameterCount() {
        return 2;
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createBiFunction(),
            "setAlpha"
        );
    }

    // class............................................................................................................

    @Override
    public Class<ColorExpressionFunctionSetAlpha<FakeExpressionEvaluationContext>> type() {
        return Cast.to(ColorExpressionFunctionSetAlpha.class);
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
