/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
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

package walkingkooka.color.parser;

import walkingkooka.color.AlphaHslColorComponent;
import walkingkooka.color.AlphaHsvColorComponent;
import walkingkooka.color.AlphaRgbColorComponent;
import walkingkooka.color.BlueRgbColorComponent;
import walkingkooka.color.GreenRgbColorComponent;
import walkingkooka.color.HueHslColorComponent;
import walkingkooka.color.HueHsvColorComponent;
import walkingkooka.color.LightnessHslColorComponent;
import walkingkooka.color.RedRgbColorComponent;
import walkingkooka.color.SaturationHslColorComponent;
import walkingkooka.color.SaturationHsvColorComponent;
import walkingkooka.color.ValueHsvColorComponent;

public final class ColorFunctionFunctionNameParserToken extends ColorFunctionNonSymbolParserToken<String> {

    static ColorFunctionFunctionNameParserToken with(final String value, final String text) {
        check(value, text);

        return new ColorFunctionFunctionNameParserToken(value, text);
    }

    private ColorFunctionFunctionNameParserToken(final String value, final String text) {
        super(value, text);
    }

    // isXXX............................................................................................................

    @Override
    public boolean isFunctionName() {
        return true;
    }

    @Override
    public boolean isNumber() {
        return false;
    }

    @Override
    public boolean isPercentage() {
        return false;
    }

    // ColorFunctionParserTokenVisitor..................................................................................

    @Override
    void accept(final ColorFunctionParserTokenVisitor visitor) {
        visitor.visit(this);
    }

    // Object...........................................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof ColorFunctionFunctionNameParserToken;
    }

    // ColorFunctionTransformer.........................................................................................

    @Override
    RedRgbColorComponent colorRed() {
        throw new UnsupportedOperationException();
    }

    @Override
    BlueRgbColorComponent colorBlue() {
        throw new UnsupportedOperationException();
    }

    @Override
    GreenRgbColorComponent colorGreen() {
        throw new UnsupportedOperationException();
    }

    @Override
    AlphaRgbColorComponent colorAlpha() {
        throw new UnsupportedOperationException();
    }

    @Override
    HueHslColorComponent hslHue() {
        throw new UnsupportedOperationException();
    }

    @Override
    SaturationHslColorComponent hslSaturation() {
        throw new UnsupportedOperationException();
    }

    @Override
    LightnessHslColorComponent hslLightness() {
        throw new UnsupportedOperationException();
    }

    @Override
    AlphaHslColorComponent hslAlpha() {
        throw new UnsupportedOperationException();
    }

    @Override
    HueHsvColorComponent hsvHue() {
        throw new UnsupportedOperationException();
    }

    @Override
    SaturationHsvColorComponent hsvSaturation() {
        throw new UnsupportedOperationException();
    }

    @Override
    ValueHsvColorComponent hsvValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    AlphaHsvColorComponent hsvAlpha() {
        throw new UnsupportedOperationException();
    }
}
