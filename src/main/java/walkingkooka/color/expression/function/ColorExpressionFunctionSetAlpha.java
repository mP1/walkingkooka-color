package walkingkooka.color.expression.function;

import walkingkooka.Cast;
import walkingkooka.color.AlphaRgbColorComponent;
import walkingkooka.color.RgbColor;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;

import java.util.List;

/**
 * A function that accepts a {@link RgbColor} and returns sets alpha component upon the given the result.
 */
final class ColorExpressionFunctionSetAlpha<C extends ExpressionEvaluationContext> extends ColorExpressionFunction<RgbColor, C> {

    static <C extends ExpressionEvaluationContext> ColorExpressionFunctionSetAlpha<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ColorExpressionFunctionSetAlpha<ExpressionEvaluationContext> INSTANCE = new ColorExpressionFunctionSetAlpha<>();

    private ColorExpressionFunctionSetAlpha() {
        super("setAlpha");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(
        RGBCOLOR,
        RGB_ALPHA
    );

    @Override
    public Class<RgbColor> returnType() {
        return RgbColor.class;
    }

    @Override
    public RgbColor apply(final List<Object> parameters,
                          final C context) {
        final RgbColor color = RGBCOLOR.getOrFail(parameters, 0);
        final AlphaRgbColorComponent component = RGB_ALPHA.getOrFail(parameters, 1);

        return color.set(component);
    }
}
