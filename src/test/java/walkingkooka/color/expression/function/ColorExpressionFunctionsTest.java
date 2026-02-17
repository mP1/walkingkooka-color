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
import walkingkooka.color.AlphaRgbColorComponent;
import walkingkooka.color.BlueRgbColorComponent;
import walkingkooka.color.Color;
import walkingkooka.color.GreenRgbColorComponent;
import walkingkooka.color.HslColor;
import walkingkooka.color.HsvColor;
import walkingkooka.color.RedRgbColorComponent;
import walkingkooka.color.RgbColor;
import walkingkooka.color.RgbColorComponent;
import walkingkooka.color.WebColorName;
import walkingkooka.color.convert.ColorConverters;
import walkingkooka.convert.ConverterContexts;
import walkingkooka.convert.Converters;
import walkingkooka.datetime.DateTimeContexts;
import walkingkooka.environment.EnvironmentContexts;
import walkingkooka.locale.LocaleContexts;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.PublicStaticHelperTesting;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.text.Indentation;
import walkingkooka.text.LineEnding;
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
    public void testColorWithRedColorCompionentAndGreenColorComponentAndBlueColorComponent() {
        final RgbColor rgb = Color.parseRgb("#123");

        this.evaluateAndCheck(
            "color",
            Lists.of(
                rgb.red(),
                rgb.green(),
                rgb.blue()
            ),
            rgb
        );
    }

    @Test
    public void testColorWithRedTextAndGreenTextAndBlueText() {
        final RgbColor rgb = Color.parseRgb("#090807");

        this.evaluateAndCheck(
            "color",
            Lists.of(
                "9",
                "8",
                "7"
            ),
            rgb
        );
    }

    @Test
    public void testColorWithRedTextAndGreenTextAndBlueText2() {
        final RgbColor rgb = Color.parseRgb("#123");

        this.evaluateAndCheck(
            "color",
            Lists.of(
                "" + rgb.red().value(),
                "" + rgb.green().value(),
                "" + rgb.blue().value()
            ),
            rgb
        );
    }

    @Test
    public void testColorWithRedTextAndGreenTextAndBlueTextAndAlphaText() {
        final RgbColor rgb = Color.parseRgb("#11223380");

        this.evaluateAndCheck(
            "color",
            Lists.of(
                Integer.parseInt("11", 16),
                Integer.parseInt("22", 16),
                Integer.parseInt("33", 16),
                Integer.parseInt("80", 16)
            ),
            rgb
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
    public void testGetBlueWithRgbColor() {
        final RgbColor rgb = Color.parseRgb("#123");

        this.evaluateAndCheck(
            "getBlue",
            Lists.of(
                rgb
            ),
            rgb.blue()
        );
    }

    @Test
    public void testGetGreenWithRgbColor() {
        final RgbColor rgb = Color.parseRgb("#123");

        this.evaluateAndCheck(
            "getGreen",
            Lists.of(
                rgb
            ),
            rgb.green()
        );
    }

    @Test
    public void testGetRedWithRgbColor() {
        final RgbColor rgb = Color.parseRgb("#123");

        this.evaluateAndCheck(
            "getRed",
            Lists.of(
                rgb
            ),
            rgb.red()
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
    public void testSetAlphaWithRgbColorAndAlphaRgbColorComponent() {
        final RgbColor rgb = Color.parseRgb("#123");
        final AlphaRgbColorComponent component = RgbColorComponent.alpha((byte) 0xff);

        this.evaluateAndCheck(
            "setAlpha",
            Lists.of(
                rgb,
                component
            ),
            rgb.set(component)
        );
    }

    @Test
    public void testSetAlphaWithRgbColorAndNumber() {
        final RgbColor rgb = Color.parseRgb("#123");
        final AlphaRgbColorComponent component = RgbColorComponent.alpha((byte) 0xff);

        this.evaluateAndCheck(
            "setAlpha",
            Lists.of(
                rgb,
                component.value()
            ),
            rgb.set(component)
        );
    }

    @Test
    public void testSetBlueWithRgbColorAndBlueRgbColorComponent() {
        final RgbColor rgb = Color.parseRgb("#123");
        final BlueRgbColorComponent component = RgbColorComponent.blue((byte) 0xff);

        this.evaluateAndCheck(
            "setBlue",
            Lists.of(
                rgb,
                component
            ),
            rgb.set(component)
        );
    }

    @Test
    public void testSetBlueWithRgbColorAndNumber() {
        final RgbColor rgb = Color.parseRgb("#123");
        final BlueRgbColorComponent component = RgbColorComponent.blue((byte) 0xff);

        this.evaluateAndCheck(
            "setBlue",
            Lists.of(
                rgb,
                component.value()
            ),
            rgb.set(component)
        );
    }

    @Test
    public void testSetGreenWithRgbColorAndGreenRgbColorComponent() {
        final RgbColor rgb = Color.parseRgb("#123");
        final GreenRgbColorComponent component = RgbColorComponent.green((byte) 0xff);

        this.evaluateAndCheck(
            "setGreen",
            Lists.of(
                rgb,
                component
            ),
            rgb.set(component)
        );
    }

    @Test
    public void testSetGreenWithRgbColorAndNumber() {
        final RgbColor rgb = Color.parseRgb("#123");
        final GreenRgbColorComponent component = RgbColorComponent.green((byte) 0xff);

        this.evaluateAndCheck(
            "setGreen",
            Lists.of(
                rgb,
                component.value()
            ),
            rgb.set(component)
        );
    }

    @Test
    public void testSetRedWithRgbColorAndRedRgbColorComponent() {
        final RgbColor rgb = Color.parseRgb("#123");
        final RedRgbColorComponent component = RgbColorComponent.red((byte) 0xff);

        this.evaluateAndCheck(
            "setRed",
            Lists.of(
                rgb,
                component
            ),
            rgb.set(component)
        );
    }

    @Test
    public void testSetRedWithRgbColorAndNumber() {
        final RgbColor rgb = Color.parseRgb("#123");
        final RedRgbColorComponent component = RgbColorComponent.red((byte) 0xff);

        this.evaluateAndCheck(
            "setRed",
            Lists.of(
                rgb,
                component.value()
            ),
            rgb.set(component)
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
    public void testToHslColorWithString() {
        final HslColor color = Color.BLACK.toHsl();

        this.evaluateAndCheck(
            "toHslColor",
            Lists.of(
                color.toString()
            ),
            color
        );
    }

    @Test
    public void testToHslColorWithColor() {
        final HslColor color = Color.parseRgb("#123")
            .toHsl();

        this.evaluateAndCheck(
            "toHslColor",
            Lists.of(color),
            color
        );
    }

    @Test
    public void testToHsvColorWithString() {
        final HsvColor color = Color.BLACK.toHsv();

        this.evaluateAndCheck(
            "toHsvColor",
            Lists.of(
                color.toString()
            ),
            color
        );
    }

    @Test
    public void testToHsvColorWithColor() {
        final HsvColor color = Color.parseRgb("#123")
            .toHsv();

        this.evaluateAndCheck(
            "toHsvColor",
            Lists.of(color),
            color
        );
    }

    @Test
    public void testToRgbColorWithString() {
        final RgbColor color = Color.parseRgb("#123");

        this.evaluateAndCheck(
            "toRgbColor",
            Lists.of(
                color.toHexString()
            ),
            color
        );
    }

    @Test
    public void testToRgbColorWithColor() {
        final Color color = Color.parse("#123");

        this.evaluateAndCheck(
            "toRgbColor",
            Lists.of(
                color
            ),
            color
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

    @Test
    public void testToWebColorNameWithRgbColor() {
        this.evaluateAndCheck(
            "toWebColorName",
            Lists.of(
                "blue"
            ),
            WebColorName.with("blue")
                .get()
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
                    (e, c) -> {
                        throw new UnsupportedOperationException();
                    },
                    (name) -> {
                        switch (name.value()) {
                            case "color":
                                return ColorExpressionFunctions.color();
                            case "getAlpha":
                                return ColorExpressionFunctions.getAlpha();
                            case "getBlue":
                                return ColorExpressionFunctions.getBlue();
                            case "getGreen":
                                return ColorExpressionFunctions.getGreen();
                            case "getRed":
                                return ColorExpressionFunctions.getRed();
                            case "invertColor":
                                return ColorExpressionFunctions.invertColor();
                            case "mixColor":
                                return ColorExpressionFunctions.mixColor();
                            case "setAlpha":
                                return ColorExpressionFunctions.setAlpha();
                            case "setBlue":
                                return ColorExpressionFunctions.setBlue();
                            case "setGreen":
                                return ColorExpressionFunctions.setGreen();
                            case "setRed":
                                return ColorExpressionFunctions.setRed();
                            case "toGray":
                                return ColorExpressionFunctions.toGray();
                            case "toHslColor":
                                return ColorExpressionFunctions.toHslColor();
                            case "toHsvColor":
                                return ColorExpressionFunctions.toHsvColor();
                            case "toRgbColor":
                                return ColorExpressionFunctions.toRgbColor();
                            case "toRgbHexString":
                                return ColorExpressionFunctions.toRgbHexString();
                            case "toWebColorName":
                                return ColorExpressionFunctions.toWebColorName();
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
                        (l) -> {
                            throw new UnsupportedOperationException();
                        }, // canDateTimeSymbolsForLocale
                        (l) -> {
                            throw new UnsupportedOperationException();
                        }, // canDecimalNumberSymbolsForLocale
                        false, // canNumbersHaveGroupSeparator
                        Converters.EXCEL_1900_DATE_SYSTEM_OFFSET, // dateTimeOffset
                        Indentation.SPACES2,
                        LineEnding.NL,
                        ',', // valueSeparator
                        Converters.collection(
                            Lists.of(
                                Converters.characterOrCharSequenceOrHasTextOrStringToCharacterOrCharSequenceOrString(),
                                Converters.numberToNumber(),
                                Converters.simple(), // handles Text -> TextNode
                                ColorConverters.textToColor(),
                                ColorConverters.colorToNumber(),
                                ColorConverters.numberToColor()
                            )
                        ),
                        DateTimeContexts.fake(),
                        DecimalNumberContexts.fake()
                    ),
                    EnvironmentContexts.fake(),
                    LocaleContexts.fake()
                )
            ),
            () -> functionName + " " + parameters
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
