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
import walkingkooka.color.Color;
import walkingkooka.tree.expression.FakeExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunctionTesting;

public final class ColorExpressionFunctionMixColorTest implements ExpressionFunctionTesting<ColorExpressionFunctionMixColor<FakeExpressionEvaluationContext>, Color, FakeExpressionEvaluationContext>,
    ToStringTesting<ColorExpressionFunctionMixColor<FakeExpressionEvaluationContext>> {

    @Test
    public void testApply() {
        final Color color = Color.parse("#123456");
        final Color other = Color.parse("#FEDCBA");
        final float amount = 0.5f;

        this.applyAndCheck(
            Lists.of(
                color,
                other,
                amount
            ),
            color.mix(
                other,
                amount
            )
        );
    }

    @Override
    public ColorExpressionFunctionMixColor<FakeExpressionEvaluationContext> createBiFunction() {
        return ColorExpressionFunctionMixColor.instance();
    }

    @Override
    public FakeExpressionEvaluationContext createContext() {
        return new FakeExpressionEvaluationContext() {

        };
    }

    @Override
    public int minimumParameterCount() {
        return 3;
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createBiFunction(),
            "mixColor"
        );
    }

    // class............................................................................................................

    @Override
    public Class<ColorExpressionFunctionMixColor<FakeExpressionEvaluationContext>> type() {
        return Cast.to(ColorExpressionFunctionMixColor.class);
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
