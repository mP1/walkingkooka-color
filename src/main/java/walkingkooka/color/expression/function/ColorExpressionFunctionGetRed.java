package walkingkooka.color.expression.function;

import walkingkooka.Cast;
import walkingkooka.color.RedRgbColorComponent;
import walkingkooka.color.RgbColor;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;

import java.util.List;

/**
 * A function that accepts a {@link RgbColor} and returns its red component.
 */
final class ColorExpressionFunctionGetRed<C extends ExpressionEvaluationContext> extends ColorExpressionFunction<RedRgbColorComponent, C> {

    static <C extends ExpressionEvaluationContext> ColorExpressionFunctionGetRed<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ColorExpressionFunctionGetRed<ExpressionEvaluationContext> INSTANCE = new ColorExpressionFunctionGetRed<>();

    private ColorExpressionFunctionGetRed() {
        super("getRed");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(
        RGBCOLOR
    );

    @Override
    public Class<RedRgbColorComponent> returnType() {
        return RedRgbColorComponent.class;
    }

    @Override
    public RedRgbColorComponent apply(final List<Object> parameters,
                                      final C context) {
        return RGBCOLOR.getOrFail(parameters, 0, context)
            .red();
    }
}
