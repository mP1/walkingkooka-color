package walkingkooka.color.expression.function;

import walkingkooka.Cast;
import walkingkooka.color.AlphaRgbColorComponent;
import walkingkooka.color.BlueRgbColorComponent;
import walkingkooka.color.Color;
import walkingkooka.color.GreenRgbColorComponent;
import walkingkooka.color.RedRgbColorComponent;
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
        final List<ExpressionFunctionParameter<?>> parameters;

        switch (count) {
            case 1:
                parameters = ONE_PARAMETER;
                break;
            case 3:
                parameters = THREE_PARAMETERS;
                break;
            case 4:
                parameters = FOUR_PARAMETERS;
                break;
            default:
                throw new IllegalArgumentException("Invalid parameter count " + count);
        }

        return parameters;
    }

    private final static List<ExpressionFunctionParameter<?>> ONE_PARAMETER = ExpressionFunctionParameter.list(
        COLOR
    );

    private final static List<ExpressionFunctionParameter<?>> THREE_PARAMETERS = ExpressionFunctionParameter.list(
        RGB_RED,
        RGB_GREEN,
        RGB_BLUE
    );

    private final static List<ExpressionFunctionParameter<?>> FOUR_PARAMETERS = ExpressionFunctionParameter.list(
        RGB_RED,
        RGB_GREEN,
        RGB_BLUE,
        RGB_ALPHA
    );

    @Override
    public Class<Color> returnType() {
        return Color.class;
    }

    @Override
    public Color apply(final List<Object> parameters,
                       final C context) {
        final Color color;

        final int count = parameters.size();
        switch (count) {
            case 1:
                color = COLOR.getOrFail(parameters, 0);
                break;
            case 3: {
                final RedRgbColorComponent red = RGB_RED.getOrFail(parameters, 0);
                final GreenRgbColorComponent green = RGB_GREEN.getOrFail(parameters, 1);
                final BlueRgbColorComponent blue = RGB_BLUE.getOrFail(parameters, 2);

                color = Color.rgb(
                    red,
                    green,
                    blue
                );
                break;
            }
            case 4: {
                final RedRgbColorComponent red = RGB_RED.getOrFail(parameters, 0);
                final GreenRgbColorComponent green = RGB_GREEN.getOrFail(parameters, 1);
                final BlueRgbColorComponent blue = RGB_BLUE.getOrFail(parameters, 2);
                final AlphaRgbColorComponent alpha = RGB_ALPHA.getOrFail(parameters, 3);

                color = Color.rgb(
                    red,
                    green,
                    blue
                ).set(alpha);

                break;
            }
            default:
                throw new IllegalArgumentException("Invalid parameter count " + count);
        }

        return color;
    }
}
