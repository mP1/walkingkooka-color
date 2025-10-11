package walkingkooka.color.expression.function;

import walkingkooka.Cast;
import walkingkooka.color.Color;
import walkingkooka.color.HsvColor;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;

import java.util.List;

/**
 * A function that returns a {@link HsvColor}.
 */
final class ColorExpressionFunctionToHsvColor<C extends ExpressionEvaluationContext> extends ColorExpressionFunction<HsvColor, C> {

    static <C extends ExpressionEvaluationContext> ColorExpressionFunctionToHsvColor<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ColorExpressionFunctionToHsvColor<ExpressionEvaluationContext> INSTANCE = new ColorExpressionFunctionToHsvColor<>();

    private ColorExpressionFunctionToHsvColor() {
        super("toHsvColor");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(
        COLOR
    );

    @Override
    public Class<HsvColor> returnType() {
        return HsvColor.class;
    }

    @Override
    public HsvColor apply(final List<Object> parameters,
                          final C context) {
        final Color color = COLOR.getOrFail(parameters, 0);

        return color.toHsv();
    }
}
