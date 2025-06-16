package walkingkooka.color.expression.function;

import walkingkooka.Cast;
import walkingkooka.color.Color;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;

import java.util.List;

/**
 * A function that accepts a {@link String} and creates a {@link Color}.
 */
final class ColorExpressionFunctionColor<C extends ExpressionEvaluationContext> extends ColorExpressionFunction<Color, C> {

    static <C extends ExpressionEvaluationContext> ColorExpressionFunctionColor<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ColorExpressionFunctionColor<ExpressionEvaluationContext> INSTANCE = new ColorExpressionFunctionColor<>();

    private ColorExpressionFunctionColor() {
        super("color");
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
        return COLOR.getOrFail(parameters, 0);
    }
}
