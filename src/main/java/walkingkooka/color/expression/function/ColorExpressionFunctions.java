/*
 * Copyright 2020 Miroslav Pokorny (github.com/mP1)
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
import walkingkooka.reflect.PublicStaticHelper;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunction;

/**
 * A collection of factory methods for {@link walkingkooka.color.Color}.
 */
public final class ColorExpressionFunctions implements PublicStaticHelper {

    /**
     * {@see ColorExpressionFunctionColor}
     */
    public static <C extends ExpressionEvaluationContext> ExpressionFunction<Color, C> color() {
        return ColorExpressionFunctionColor.instance();
    }

    /**
     * {@see ColorExpressionFunctionGetAlpha}
     */
    public static <C extends ExpressionEvaluationContext> ExpressionFunction<AlphaRgbColorComponent, C> getAlpha() {
        return ColorExpressionFunctionGetAlpha.instance();
    }

    /**
     * {@see ColorExpressionFunctionGetBlue}
     */
    public static <C extends ExpressionEvaluationContext> ExpressionFunction<BlueRgbColorComponent, C> getBlue() {
        return ColorExpressionFunctionGetBlue.instance();
    }

    /**
     * {@see ColorExpressionFunctionGetGreen}
     */
    public static <C extends ExpressionEvaluationContext> ExpressionFunction<GreenRgbColorComponent, C> getGreen() {
        return ColorExpressionFunctionGetGreen.instance();
    }
    
    /**
     * {@see ColorExpressionFunctionGetRed}
     */
    public static <C extends ExpressionEvaluationContext> ExpressionFunction<RedRgbColorComponent, C> getRed() {
        return ColorExpressionFunctionGetRed.instance();
    }

    /**
     * {@see ColorExpressionFunctionInvertColor}
     */
    public static <C extends ExpressionEvaluationContext> ExpressionFunction<Color, C> invertColor() {
        return ColorExpressionFunctionInvertColor.instance();
    }

    /**
     * {@see ColorExpressionFunctionMixColor}
     */
    public static <C extends ExpressionEvaluationContext> ExpressionFunction<Color, C> mixColor() {
        return ColorExpressionFunctionMixColor.instance();
    }

    /**
     * {@see ColorExpressionFunctionSetAlpha}
     */
    public static <C extends ExpressionEvaluationContext> ExpressionFunction<RgbColor, C> setAlpha() {
        return ColorExpressionFunctionSetAlpha.instance();
    }

    /**
     * {@see ColorExpressionFunctionSetBlue}
     */
    public static <C extends ExpressionEvaluationContext> ExpressionFunction<RgbColor, C> setBlue() {
        return ColorExpressionFunctionSetBlue.instance();
    }

    /**
     * {@see ColorExpressionFunctionSetGreen}
     */
    public static <C extends ExpressionEvaluationContext> ExpressionFunction<RgbColor, C> setGreen() {
        return ColorExpressionFunctionSetGreen.instance();
    }

    /**
     * {@see ColorExpressionFunctionSetRed}
     */
    public static <C extends ExpressionEvaluationContext> ExpressionFunction<RgbColor, C> setRed() {
        return ColorExpressionFunctionSetRed.instance();
    }

    /**
     * {@see ColorExpressionFunctionToRgbHexString}
     */
    public static <C extends ExpressionEvaluationContext> ExpressionFunction<String, C> toRgbHexString() {
        return ColorExpressionFunctionToRgbHexString.instance();
    }

    /**
     * {@see ColorExpressionFunctionRgbColorToGray}
     */
    public static <C extends ExpressionEvaluationContext> ExpressionFunction<RgbColor, C> toGray() {
        return ColorExpressionFunctionRgbColorToGray.instance();
    }

    /**
     * Prevent creation
     */
    private ColorExpressionFunctions() {
        throw new UnsupportedOperationException();
    }
}
