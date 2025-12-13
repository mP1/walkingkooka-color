package walkingkooka.color.expression.function;

import walkingkooka.Cast;
import walkingkooka.color.Color;
import walkingkooka.color.HslColor;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;

import java.util.List;

/**
 * A function that returns a {@link HslColor}.
 */
final class ColorExpressionFunctionToHslColor<C extends ExpressionEvaluationContext> extends ColorExpressionFunction<HslColor, C> {

    static <C extends ExpressionEvaluationContext> ColorExpressionFunctionToHslColor<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ColorExpressionFunctionToHslColor<ExpressionEvaluationContext> INSTANCE = new ColorExpressionFunctionToHslColor<>();

    private ColorExpressionFunctionToHslColor() {
        super("toHslColor");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(
        COLOR
    );

    @Override
    public Class<HslColor> returnType() {
        return HslColor.class;
    }

    @Override
    public HslColor apply(final List<Object> parameters,
                          final C context) {
        final Color color = COLOR.getOrFail(parameters, 0);

        return color.toHsl();
    }
}
