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
import walkingkooka.text.cursor.parser.ParserToken;

import java.util.Optional;
import java.util.function.Predicate;

public final class ColorFunctionPercentageParserToken extends ColorFunctionNonSymbolParserToken<Double> {

    static ColorFunctionPercentageParserToken with(final Double value, final String text) {
        check(value, text);

        return new ColorFunctionPercentageParserToken(value, text);
    }

    private ColorFunctionPercentageParserToken(final Double value, final String text) {
        super(value, text);
    }

    // removeFirstIf....................................................................................................

    @Override
    public Optional<ColorFunctionPercentageParserToken> removeFirstIf(final Predicate<ParserToken> predicate) {
        return ParserToken.removeFirstIfLeaf(
                this,
                predicate,
                ColorFunctionPercentageParserToken.class
        );
    }

    // replaceFirstIf...................................................................................................

    @Override
    public ColorFunctionPercentageParserToken replaceFirstIf(final Predicate<ParserToken> predicate,
                                                             final ParserToken token) {
        return ParserToken.replaceFirstIf(
                this,
                predicate,
                token,
                ColorFunctionPercentageParserToken.class
        );
    }

    // replaceIf........................................................................................................

    @Override
    public ColorFunctionPercentageParserToken replaceIf(final Predicate<ParserToken> predicate,
                                                        final ParserToken token) {
        return ParserToken.replaceIf(
                this,
                predicate,
                token,
                ColorFunctionPercentageParserToken.class
        );
    }

    // ColorFunctionParserTokenVisitor..................................................................................

    @Override
    void accept(final ColorFunctionParserTokenVisitor visitor) {
        visitor.visit(this);
    }

    // Object...........................................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof ColorFunctionPercentageParserToken;
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
        return (byte)Math.round(this.value() * RgbColorComponent.MAX_VALUE / 100);
    }

    @Override
    HueHslColorComponent hslHue() {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
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
        return this.value().floatValue() / 100;
    }
}
