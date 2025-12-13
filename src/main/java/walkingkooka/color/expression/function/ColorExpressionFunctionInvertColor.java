package walkingkooka.color.expression.function;

import walkingkooka.Cast;
import walkingkooka.color.Color;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;

import java.util.List;

/**
 * A function that inverts using {@link Color#invert()}.
 */
final class ColorExpressionFunctionInvertColor<C extends ExpressionEvaluationContext> extends ColorExpressionFunction<Color, C> {

    static <C extends ExpressionEvaluationContext> ColorExpressionFunctionInvertColor<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ColorExpressionFunctionInvertColor<ExpressionEvaluationContext> INSTANCE = new ColorExpressionFunctionInvertColor<>();

    private ColorExpressionFunctionInvertColor() {
        super("invertColor");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(
        COLOR
    );

    @Override
    public Class<Color> returnType() {
        return Color.class;
    }

    @Override
    public Color apply(final List<Object> parameters,
                       final C context) {
        return COLOR.getOrFail(parameters, 0, context)
            .invert();
    }
}
