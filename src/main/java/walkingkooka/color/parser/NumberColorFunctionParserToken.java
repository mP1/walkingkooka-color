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
import walkingkooka.color.HslColorComponent;
import walkingkooka.color.HsvColorComponent;
import walkingkooka.color.HueHslColorComponent;
import walkingkooka.color.HueHsvColorComponent;
import walkingkooka.color.LightnessHslColorComponent;
import walkingkooka.color.RedRgbColorComponent;
import walkingkooka.color.RgbColorComponent;
import walkingkooka.color.SaturationHslColorComponent;
import walkingkooka.color.SaturationHsvColorComponent;
import walkingkooka.color.ValueHsvColorComponent;

public final class NumberColorFunctionParserToken extends NonSymbolColorFunctionParserToken<Double> {

    static NumberColorFunctionParserToken with(final Double value, final String text) {
        check(value, text);

        return new NumberColorFunctionParserToken(value, text);
    }

    private NumberColorFunctionParserToken(final Double value, final String text) {
        super(value, text);
    }

    // ColorFunctionParserTokenVisitor..................................................................................

    @Override
    void accept(final ColorFunctionParserTokenVisitor visitor) {
        visitor.visit(this);
    }

    // Object...........................................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof NumberColorFunctionParserToken;
    }

    // ColorFunctionTransformer.........................................................................................

    @Override
    RedRgbColorComponent colorRed() {
        return RgbColorComponent.red(this.byteValue());
    }

    @Override
    BlueRgbColorComponent colorBlue() {
        return RgbColorComponent.blue(this.byteValue());
    }

    @Override
    GreenRgbColorComponent colorGreen() {
        return RgbColorComponent.green(this.byteValue());
    }

    @Override
    AlphaRgbColorComponent colorAlpha() {
        return RgbColorComponent.alpha(this.byteValue());
    }

    private byte byteValue() {
        return this.value().byteValue();
    }

    @Override
    HueHslColorComponent hslHue() {
        return HslColorComponent.hue(this.floatValue());
    }

    @Override
    SaturationHslColorComponent hslSaturation() {
        return HslColorComponent.saturation(this.floatValue());
    }

    @Override
    LightnessHslColorComponent hslLightness() {
        return HslColorComponent.lightness(this.floatValue());
    }

    @Override
    AlphaHslColorComponent hslAlpha() {
        return HslColorComponent.alpha(this.floatValue());
    }

    @Override
    HueHsvColorComponent hsvHue() {
        return HsvColorComponent.hue(this.floatValue());
    }

    @Override
    SaturationHsvColorComponent hsvSaturation() {
        return HsvColorComponent.saturation(this.floatValue());
    }

    @Override
    ValueHsvColorComponent hsvValue() {
        return HsvColorComponent.value(this.floatValue());
    }

    @Override
    AlphaHsvColorComponent hsvAlpha() {
        return HsvColorComponent.alpha(this.floatValue());
    }

    private float floatValue() {
        return this.value().floatValue();
    }
}
