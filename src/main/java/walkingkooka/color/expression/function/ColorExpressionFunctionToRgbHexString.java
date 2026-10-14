package walkingkooka.color.expression.function;

import walkingkooka.Cast;
import walkingkooka.color.Color;
import walkingkooka.color.expression.ColorExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;

import java.util.List;

/**
 * A function that returns the color in HEX string form.
 */
final class ColorExpressionFunctionToRgbHexString<C extends ColorExpressionEvaluationContext> extends ColorExpressionFunction<String, C> {

    static <C extends ColorExpressionEvaluationContext> ColorExpressionFunctionToRgbHexString<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ColorExpressionFunctionToRgbHexString INSTANCE = new ColorExpressionFunctionToRgbHexString<>();

    private ColorExpressionFunctionToRgbHexString() {
        super("toRgbHexString");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(
        COLOR
    );

    @Override
    public Class<String> returnType() {
        return String.class;
    }

    @Override
    public String apply(final List<Object> parameters,
                       final C context) {
        final Color color = COLOR.getOrFail(parameters, 0);

        return color.toRgb()
            .toHexString();
    }
}
