package walkingkooka.color.expression.function;

import walkingkooka.Cast;
import walkingkooka.color.RgbColor;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;

import java.util.List;

/**
 * A function that converts a given {@link walkingkooka.color.RgbColor} to gray
 */
final class ColorExpressionFunctionRgbColorToGray<C extends ExpressionEvaluationContext> extends ColorExpressionFunction<RgbColor, C> {

    static <C extends ExpressionEvaluationContext> ColorExpressionFunctionRgbColorToGray<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ColorExpressionFunctionRgbColorToGray<ExpressionEvaluationContext> INSTANCE = new ColorExpressionFunctionRgbColorToGray<>();

    private ColorExpressionFunctionRgbColorToGray() {
        super("toGray");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(
        RGBCOLOR
    );

    @Override
    public Class<RgbColor> returnType() {
        return RgbColor.class;
    }

    @Override
    public RgbColor apply(final List<Object> parameters,
                          final C context) {
        final RgbColor color = RGBCOLOR.getOrFail(parameters, 0, context);

        return color.toGray();
    }
}
