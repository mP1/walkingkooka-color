package walkingkooka.color.expression.function;

import walkingkooka.Cast;
import walkingkooka.color.Color;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterKind;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterName;

import java.util.List;

/**
 * A function that mixes two colors together using a third value as the amount parameter.
 */
final class ColorExpressionFunctionMixColor<C extends ExpressionEvaluationContext> extends ColorExpressionFunction<Color, C> {

    static <C extends ExpressionEvaluationContext> ColorExpressionFunctionMixColor<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ColorExpressionFunctionMixColor<ExpressionEvaluationContext> INSTANCE = new ColorExpressionFunctionMixColor<>();

    private ColorExpressionFunctionMixColor() {
        super("mixColor");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    final static ExpressionFunctionParameter<Color> OTHER_COLOR = COLOR.setName(ExpressionFunctionParameterName.with("otherColor"));

    final static ExpressionFunctionParameter<Float> AMOUNT = ExpressionFunctionParameterName.with("amount")
        .required(Float.class)
        .setKinds(ExpressionFunctionParameterKind.CONVERT_EVALUATE);

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(
        COLOR,
        OTHER_COLOR,
        AMOUNT
    );

    @Override
    public Class<Color> returnType() {
        return Color.class;
    }

    @Override
    public Color apply(final List<Object> parameters,
                       final C context) {
        final Color color = COLOR.getOrFail(parameters, 0);
        final Color other = OTHER_COLOR.getOrFail(parameters, 1);
        final Float amount = AMOUNT.getOrFail(parameters, 2);

        return color.mix(
            other,
            amount
        );
    }
}
