/*
 * Copyright 2020 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.color.expression.function;

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.color.Color;
import walkingkooka.color.RgbColor;
import walkingkooka.color.convert.ColorConverters;
import walkingkooka.convert.ConverterContexts;
import walkingkooka.convert.Converters;
import walkingkooka.datetime.DateTimeContexts;
import walkingkooka.locale.LocaleContexts;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.PublicStaticHelperTesting;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.tree.expression.Expression;
import walkingkooka.tree.expression.ExpressionEvaluationContexts;
import walkingkooka.tree.expression.ExpressionFunctionName;
import walkingkooka.tree.expression.ExpressionNumberKind;
import walkingkooka.tree.expression.ExpressionReference;
import walkingkooka.tree.expression.function.UnknownExpressionFunctionException;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public final class ColorExpressionFunctionsTest implements PublicStaticHelperTesting<ColorExpressionFunctions> {

    @Test
    public void testColorWithColor() {
        final Color color = Color.parse("#123");

        this.evaluateAndCheck(
            "color",
            Lists.of(
                color
            ),
            color
        );
    }

    @Test
    public void testColorWithText() {
        final Color color = Color.parse("#123");

        this.evaluateAndCheck(
            "color",
            Lists.of(
                color.toRgb()
                    .text()
            ),
            color
        );
    }

    @Test
    public void testGetAlphaWithRgbColor() {
        final RgbColor rgb = Color.parseRgb("#123");

        this.evaluateAndCheck(
            "getAlpha",
            Lists.of(
                rgb
            ),
            rgb.alpha()
        );
    }

    @Test
    public void testInvertColorWithColor() {
        final Color color = Color.parse("#123");

        this.evaluateAndCheck(
            "invertColor",
            Lists.of(
                color
            ),
            color.invert()
        );
    }

    @Test
    public void testInvertColorWithText() {
        final Color color = Color.parse("#123");

        this.evaluateAndCheck(
            "invertColor",
            Lists.of(
                color.toRgb()
                    .text()
            ),
            color.invert()
        );
    }

    @Test
    public void testMixColorWithColorColorAndFloat() {
        final Color color = Color.parse("#123");
        final Color other = Color.parse("#FFF");
        final float amount = 0.5f;

        this.evaluateAndCheck(
            "mixColor",
            Lists.of(
                color,
                other,
                amount
            ),
            color.mix(
                other,
                amount
            )
        );
    }

    @Test
    public void testToGrayWithString() {
        final String text = "#123";

        this.evaluateAndCheck(
            "toGray",
            Lists.of(
                text
            ),
            Color.parseRgb(text).toGray()
        );
    }

    @Test
    public void testToGrayWithColor() {
        final RgbColor color = Color.parseRgb("#456");

        this.evaluateAndCheck(
            "toGray",
            Lists.of(
                color
            ),
            color.toGray()
        );
    }

    @Test
    public void testToRgbHexStringWithColor() {
        final Color color = Color.parse("#123");

        this.evaluateAndCheck(
            "toRgbHexString",
            Lists.of(
                color
            ),
            "#112233"
        );
    }

    @Test
    public void testToRgbHexStringWithString() {
        this.evaluateAndCheck(
            "toRgbHexString",
            Lists.of(
                "#123"
            ),
            "#112233"
        );
    }

    private void evaluateAndCheck(final String functionName,
                                  final List<Object> parameters,
                                  final Object expected) {
        this.checkEquals(
            expected,
            Expression.call(
                Expression.namedFunction(
                    ExpressionFunctionName.with(functionName)
                ),
                parameters.stream()
                    .map(Expression::value)
                    .collect(Collectors.toList())
            ).toValue(
                ExpressionEvaluationContexts.basic(
                    ExpressionNumberKind.BIG_DECIMAL,
                    (name) -> {
                        switch (name.value()) {
                            case "color":
                                return ColorExpressionFunctions.color();
                            case "getAlpha":
                                return ColorExpressionFunctions.getAlpha();
                            case "invertColor":
                                return ColorExpressionFunctions.invertColor();
                            case "mixColor":
                                return ColorExpressionFunctions.mixColor();
                            case "toGray":
                                return ColorExpressionFunctions.toGray();
                            case "toRgbHexString":
                                return ColorExpressionFunctions.toRgbHexString();
                            default:
                                throw new UnknownExpressionFunctionException(name);
                        }
                    }, // name -> function
                    (final RuntimeException cause) -> {
                        throw cause;
                    },
                    (ExpressionReference reference) -> {
                        throw new UnsupportedOperationException();
                    },
                    (ExpressionReference reference) -> {
                        throw new UnsupportedOperationException();
                    },
                    CaseSensitivity.SENSITIVE,
                    ConverterContexts.basic(
                        Converters.EXCEL_1900_DATE_SYSTEM_OFFSET, // dateTimeOffset
                        Converters.collection(
                            Lists.of(
                                Converters.characterOrCharSequenceOrHasTextOrStringToCharacterOrCharSequenceOrString(),
                                Converters.simple(), // handles Text -> TextNode
                                ColorConverters.textToColor()
                            )
                        ),
                        DateTimeContexts.fake(),
                        DecimalNumberContexts.fake()
                    ),
                    LocaleContexts.fake()
                )
            )
        );
    }

    // class............................................................................................................

    @Override
    public Class<ColorExpressionFunctions> type() {
        return ColorExpressionFunctions.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }

    @Override
    public boolean canHavePublicTypes(final Method method) {
        return false;
    }
}
