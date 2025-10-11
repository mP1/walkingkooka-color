package walkingkooka.color.expression.function;

import walkingkooka.Cast;
import walkingkooka.color.Color;
import walkingkooka.color.WebColorName;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;

import java.util.List;

/**
 * A function that accepts a {@link Color}, converts it to RGB and then takes the {@link WebColorName} if available or
 * returns null.
 */
final class ColorExpressionFunctionToWebColorName<C extends ExpressionEvaluationContext> extends ColorExpressionFunction<WebColorName, C> {

    static <C extends ExpressionEvaluationContext> ColorExpressionFunctionToWebColorName<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ColorExpressionFunctionToWebColorName<ExpressionEvaluationContext> INSTANCE = new ColorExpressionFunctionToWebColorName<>();

    private ColorExpressionFunctionToWebColorName() {
        super("toWebColorName");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(
        COLOR
    );

    @Override
    public Class<WebColorName> returnType() {
        return WebColorName.class;
    }

    @Override
    public WebColorName apply(final List<Object> parameters,
                              final C context) {
        final Color color = COLOR.getOrFail(parameters, 0);

        return color.toRgb()
            .toWebColorName()
            .orElse(null);
    }
}
