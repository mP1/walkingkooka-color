package walkingkooka.color.expression.function;

import walkingkooka.Cast;
import walkingkooka.color.BlueRgbColorComponent;
import walkingkooka.color.RgbColor;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterKind;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterName;

import java.util.List;

/**
 * A function that accepts a {@link RgbColor} and returns sets blue component upon the given the result.
 */
final class ColorExpressionFunctionSetBlue<C extends ExpressionEvaluationContext> extends ColorExpressionFunction<RgbColor, C> {

    static <C extends ExpressionEvaluationContext> ColorExpressionFunctionSetBlue<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ColorExpressionFunctionSetBlue<ExpressionEvaluationContext> INSTANCE = new ColorExpressionFunctionSetBlue<>();

    private ColorExpressionFunctionSetBlue() {
        super("setBlue");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    private final static ExpressionFunctionParameter<BlueRgbColorComponent> RGB_BLUE = ExpressionFunctionParameterName.with("blue")
        .required(BlueRgbColorComponent.class)
        .setKinds(ExpressionFunctionParameterKind.CONVERT_EVALUATE);

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(
        RGBCOLOR,
        RGB_BLUE
    );

    @Override
    public Class<RgbColor> returnType() {
        return RgbColor.class;
    }

    @Override
    public RgbColor apply(final List<Object> parameters,
                          final C context) {
        final RgbColor color = RGBCOLOR.getOrFail(parameters, 0);
        final BlueRgbColorComponent component = RGB_BLUE.getOrFail(parameters, 1);

        return color.set(component);
    }
}
