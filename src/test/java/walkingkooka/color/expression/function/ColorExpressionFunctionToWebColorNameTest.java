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
import walkingkooka.color.RgbColor;
import walkingkooka.color.WebColorName;
import walkingkooka.tree.expression.FakeExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunctionTesting;

import java.util.Optional;

public final class ColorExpressionFunctionToWebColorNameTest implements ExpressionFunctionTesting<ColorExpressionFunctionToWebColorName<FakeExpressionEvaluationContext>, WebColorName, FakeExpressionEvaluationContext>,
    ToStringTesting<ColorExpressionFunctionToWebColorName<FakeExpressionEvaluationContext>> {

    @Test
    public void testApplyWithColorWithWebColorName() {
        final WebColorName name = WebColorName.CYAN;

        this.applyAndCheck(
            Lists.of(
                name.color()
            ),
            name
        );
    }

    @Test
    public void testApplyWithColorWithWithoutWebColorName() {
        final RgbColor color = Color.parseRgb("#123");

        this.checkEquals(
            Optional.empty(),
            color.toWebColorName()
        );

        this.applyAndCheck(
            Lists.of(color),
            null
        );
    }

    @Override
    public ColorExpressionFunctionToWebColorName<FakeExpressionEvaluationContext> createBiFunction() {
        return ColorExpressionFunctionToWebColorName.instance();
    }

    @Override
    public FakeExpressionEvaluationContext createContext() {
        return new FakeExpressionEvaluationContext() {

        };
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
            "toWebColorName"
        );
    }

    // class............................................................................................................

    @Override
    public Class<ColorExpressionFunctionToWebColorName<FakeExpressionEvaluationContext>> type() {
        return Cast.to(WebColorName.class);
    }

    @Override
    public void testTypeNaming() {
        throw new UnsupportedOperationException();
    }
}
