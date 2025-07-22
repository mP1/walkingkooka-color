package walkingkooka.color.expression.function;

import walkingkooka.Cast;
import walkingkooka.color.GreenRgbColorComponent;
import walkingkooka.color.RgbColor;
import walkingkooka.tree.expression.ExpressionEvaluationContext;
import walkingkooka.tree.expression.function.ExpressionFunctionParameter;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterKind;
import walkingkooka.tree.expression.function.ExpressionFunctionParameterName;

import java.util.List;

/**
 * A function that accepts a {@link RgbColor} and returns sets green component upon the given the result.
 */
final class ColorExpressionFunctionSetGreen<C extends ExpressionEvaluationContext> extends ColorExpressionFunction<RgbColor, C> {

    static <C extends ExpressionEvaluationContext> ColorExpressionFunctionSetGreen<C> instance() {
        return Cast.to(INSTANCE);
    }

    /**
     * Singleton
     */
    private final static ColorExpressionFunctionSetGreen<ExpressionEvaluationContext> INSTANCE = new ColorExpressionFunctionSetGreen<>();

    private ColorExpressionFunctionSetGreen() {
        super("setGreen");
    }

    @Override
    public List<ExpressionFunctionParameter<?>> parameters(final int count) {
        return PARAMETERS;
    }

    private final static ExpressionFunctionParameter<GreenRgbColorComponent> RGB_GREEN = ExpressionFunctionParameterName.with("green")
        .required(GreenRgbColorComponent.class)
        .setKinds(ExpressionFunctionParameterKind.CONVERT_EVALUATE);

    private final static List<ExpressionFunctionParameter<?>> PARAMETERS = ExpressionFunctionParameter.list(
        RGBCOLOR,
        RGB_GREEN
    );

    @Override
    public Class<RgbColor> returnType() {
        return RgbColor.class;
    }

    @Override
    public RgbColor apply(final List<Object> parameters,
                          final C context) {
        final RgbColor color = RGBCOLOR.getOrFail(parameters, 0);
        final GreenRgbColorComponent component = RGB_GREEN.getOrFail(parameters, 1);

        return color.set(component);
    }
}
