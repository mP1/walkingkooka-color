package walkingkooka.color.expression.function;

import walkingkooka.Cast;
import walkingkooka.color.Color;
import walkingkooka.color.RgbColor;
import walkingkooka.color.expression.ColorExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;

import java.util.List;

/**
 * A function that returns a {@link RgbColor}.
 */
final class ColorExpressionFunctionToRgbColor<C extends ColorExpressionEvaluationContext> extends ColorExpressionFunction<RgbColor, C> {

    static <C extends ColorExpressionEvaluationContext> ColorExpressionFunctionToRgbColor<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ColorExpressionFunctionToRgbColor INSTANCE = new ColorExpressionFunctionToRgbColor<>();

    private ColorExpressionFunctionToRgbColor() {
        super("toRgbColor");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(
        COLOR
    );

    @Override
    public Class<RgbColor> returnType() {
        return RgbColor.class;
    }

    @Override
    public RgbColor apply(final List<Object> parameters,
                          final C context) {
        final Color color = COLOR.getOrFail(parameters, 0);

        return color.toRgb();
    }
}
