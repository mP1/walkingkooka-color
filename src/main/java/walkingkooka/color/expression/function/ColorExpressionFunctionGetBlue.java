package walkingkooka.color.expression.function;

import walkingkooka.Cast;
import walkingkooka.color.BlueRgbColorComponent;
import walkingkooka.color.RgbColor;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;

import java.util.List;

/**
 * A function that accepts a {@link RgbColor} and returns its blue component.
 */
final class ColorExpressionFunctionGetBlue<C extends ExpressionEvaluationContext> extends ColorExpressionFunction<BlueRgbColorComponent, C> {

    static <C extends ExpressionEvaluationContext> ColorExpressionFunctionGetBlue<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ColorExpressionFunctionGetBlue<ExpressionEvaluationContext> INSTANCE = new ColorExpressionFunctionGetBlue<>();

    private ColorExpressionFunctionGetBlue() {
        super("getBlue");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(
        RGBCOLOR
    );

    @Override
    public Class<BlueRgbColorComponent> returnType() {
        return BlueRgbColorComponent.class;
    }

    @Override
    public BlueRgbColorComponent apply(final List<Object> parameters,
                                       final C context) {
        return RGBCOLOR.getOrFail(parameters, 0, context)
            .blue();
    }
}
