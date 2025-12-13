package walkingkooka.color.expression.function;

import walkingkooka.Cast;
import walkingkooka.color.GreenRgbColorComponent;
import walkingkooka.color.RgbColor;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;

import java.util.List;

/**
 * A function that accepts a {@link RgbColor} and returns its green component.
 */
final class ColorExpressionFunctionGetGreen<C extends ExpressionEvaluationContext> extends ColorExpressionFunction<GreenRgbColorComponent, C> {

    static <C extends ExpressionEvaluationContext> ColorExpressionFunctionGetGreen<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ColorExpressionFunctionGetGreen<ExpressionEvaluationContext> INSTANCE = new ColorExpressionFunctionGetGreen<>();

    private ColorExpressionFunctionGetGreen() {
        super("getGreen");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(
        RGBCOLOR
    );

    @Override
    public Class<GreenRgbColorComponent> returnType() {
        return GreenRgbColorComponent.class;
    }

    @Override
    public GreenRgbColorComponent apply(final List<Object> parameters,
                                        final C context) {
        return RGBCOLOR.getOrFail(parameters, 0, context)
            .green();
    }
}
