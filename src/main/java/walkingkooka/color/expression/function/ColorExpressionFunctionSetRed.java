package walkingkooka.color.expression.function;

import walkingkooka.Cast;
import walkingkooka.color.RedRgbColorComponent;
import walkingkooka.color.RgbColor;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;

import java.util.List;

/**
 * A function that accepts a {@link RgbColor} and returns sets red component upon the given the result.
 */
final class ColorExpressionFunctionSetRed<C extends ExpressionEvaluationContext> extends ColorExpressionFunction<RgbColor, C> {

    static <C extends ExpressionEvaluationContext> ColorExpressionFunctionSetRed<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ColorExpressionFunctionSetRed<ExpressionEvaluationContext> INSTANCE = new ColorExpressionFunctionSetRed<>();

    private ColorExpressionFunctionSetRed() {
        super("setRed");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(
        RGBCOLOR,
        RGB_RED
    );

    @Override
    public Class<RgbColor> returnType() {
        return RgbColor.class;
    }

    @Override
    public RgbColor apply(final List<Object> parameters,
                          final C context) {
        final RgbColor color = RGBCOLOR.getOrFail(parameters, 0);
        final RedRgbColorComponent component = RGB_RED.getOrFail(parameters, 1);

        return color.set(component);
    }
}
