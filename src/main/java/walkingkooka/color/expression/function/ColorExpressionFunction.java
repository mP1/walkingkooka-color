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

import walkingkooka.color.AlphaRgbColorComponent;
import walkingkooka.color.BlueRgbColorComponent;
import walkingkooka.color.Color;
import walkingkooka.color.GreenRgbColorComponent;
import walkingkooka.color.RedRgbColorComponent;
import walkingkooka.color.RgbColor;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.ExpressionFunctionName;
import walkingkooka.tree.expression.ExpressionPurityContext;
import walkingkooka.tree.expression.function.ExpressionFunction;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterKind;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterName;

import java.util.Optional;

/**
 * A {@link ExpressionFunction} that involves {@link Color}
 */
abstract class ColorExpressionFunction<T, C extends ExpressionEvaluationContext> implements ExpressionFunction<T, C> {

    ColorExpressionFunction(final String name) {
        super();
        this.name = Optional.of(
            ExpressionFunctionName.with(name)
        );
    }

    @Override
    public final Optional<ExpressionFunctionName> name() {
        return this.name;
    }

    private final Optional<ExpressionFunctionName> name;

    /**
     * All functions are pure except for cell.
     */
    @Override
    public final boolean isPure(final ExpressionPurityContext context) {
        return true;
    }

    @Override
    public final String toString() {
        return this.name()
            .get()
            .toString();
    }

    final static ExpressionFunctionParameter<Color> COLOR = ExpressionFunctionParameterName.with("color")
        .required(Color.class)
        .setKinds(ExpressionFunctionParameterKind.CONVERT_EVALUATE);

    final static ExpressionFunctionParameter<RgbColor> RGBCOLOR = ExpressionFunctionParameterName.with("rgbColor")
        .required(RgbColor.class)
        .setKinds(ExpressionFunctionParameterKind.CONVERT_EVALUATE);

    final static ExpressionFunctionParameter<AlphaRgbColorComponent> RGB_ALPHA = ExpressionFunctionParameterName.with("alpha")
        .required(AlphaRgbColorComponent.class)
        .setKinds(ExpressionFunctionParameterKind.CONVERT_EVALUATE);

    final static ExpressionFunctionParameter<BlueRgbColorComponent> RGB_BLUE = ExpressionFunctionParameterName.with("blue")
        .required(BlueRgbColorComponent.class)
        .setKinds(ExpressionFunctionParameterKind.CONVERT_EVALUATE);

    final static ExpressionFunctionParameter<GreenRgbColorComponent> RGB_GREEN = ExpressionFunctionParameterName.with("green")
        .required(GreenRgbColorComponent.class)
        .setKinds(ExpressionFunctionParameterKind.CONVERT_EVALUATE);

    final static ExpressionFunctionParameter<RedRgbColorComponent> RGB_RED = ExpressionFunctionParameterName.with("red")
        .required(RedRgbColorComponent.class)
        .setKinds(ExpressionFunctionParameterKind.CONVERT_EVALUATE);
}
