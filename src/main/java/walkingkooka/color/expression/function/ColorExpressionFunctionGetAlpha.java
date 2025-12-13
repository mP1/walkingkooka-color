package walkingkooka.color.expression.function;

import walkingkooka.Cast;
import walkingkooka.color.AlphaRgbColorComponent;
import walkingkooka.color.RgbColor;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;

import java.util.List;

/**
 * A function that accepts a {@link RgbColor} and returns its alpha component.
 */
final class ColorExpressionFunctionGetAlpha<C extends ExpressionEvaluationContext> extends ColorExpressionFunction<AlphaRgbColorComponent, C> {

    static <C extends ExpressionEvaluationContext> ColorExpressionFunctionGetAlpha<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ColorExpressionFunctionGetAlpha<ExpressionEvaluationContext> INSTANCE = new ColorExpressionFunctionGetAlpha<>();

    private ColorExpressionFunctionGetAlpha() {
        super("getAlpha");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(
        RGBCOLOR
    );

    @Override
    public Class<AlphaRgbColorComponent> returnType() {
        return AlphaRgbColorComponent.class;
    }

    @Override
    public AlphaRgbColorComponent apply(final List<Object> parameters,
                                        final C context) {
        return RGBCOLOR.getOrFail(parameters, 0)
            .alpha();
    }
}
