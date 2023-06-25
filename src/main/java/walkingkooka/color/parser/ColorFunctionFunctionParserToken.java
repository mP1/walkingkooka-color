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

import walkingkooka.Value;
import walkingkooka.collect.list.Lists;
import walkingkooka.color.AlphaHslColorComponent;
import walkingkooka.color.AlphaHsvColorComponent;
import walkingkooka.color.AlphaRgbColorComponent;
import walkingkooka.color.BlueRgbColorComponent;
import walkingkooka.color.Color;
import walkingkooka.color.GreenRgbColorComponent;
import walkingkooka.color.HueHslColorComponent;
import walkingkooka.color.HueHsvColorComponent;
import walkingkooka.color.LightnessHslColorComponent;
import walkingkooka.color.RedRgbColorComponent;
import walkingkooka.color.SaturationHslColorComponent;
import walkingkooka.color.SaturationHsvColorComponent;
import walkingkooka.color.ValueHsvColorComponent;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.visit.Visiting;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Holds a json array which may contain further json values.
 */
public final class ColorFunctionFunctionParserToken extends ColorFunctionParserToken implements Value<List<ParserToken>> {

    static ColorFunctionFunctionParserToken with(final List<ParserToken> value,
                                                 final String text) {
        Objects.requireNonNull(value, "tokens");

        return new ColorFunctionFunctionParserToken(Lists.immutable(value),
                Objects.requireNonNull(text, "text"));
    }

    private ColorFunctionFunctionParserToken(final List<ParserToken> value,
                                             final String text) {
        super(text);
        this.value = value;
    }

    @Override
    public List<ParserToken> value() {
        return this.value;
    }

    final List<ParserToken> value;

    // toColorHslOrHsv..................................................................................................

    public Color toColorHslOrHsv() {
        return ColorParsersComponentsColorFunctionParserTokenVisitor.transform(this);
    }

    // children.........................................................................................................

    @Override
    public ColorFunctionFunctionParserToken setChildren(final List<ParserToken> children) {
        return ParserToken.parentSetChildren(
                this,
                children,
                ColorFunctionFunctionParserToken::new
        );
    }

    // removeFirstIf....................................................................................................

    @Override
    public ColorFunctionFunctionParserToken removeFirstIf(final Predicate<ParserToken> predicate) {
        return ParserToken.parentRemoveFirstIf(
                this,
                predicate,
                ColorFunctionFunctionParserToken.class
        );
    }

    // removeIf.........................................................................................................

    @Override
    public ColorFunctionFunctionParserToken removeIf(final Predicate<ParserToken> predicate) {
        return ParserToken.parentRemoveIf(
                this,
                predicate,
                ColorFunctionFunctionParserToken.class
        );
    }

    // replaceFirstIf...................................................................................................

    @Override
    public ColorFunctionFunctionParserToken replaceFirstIf(final Predicate<ParserToken> predicate,
                                                           final ParserToken token) {
        return ParserToken.replaceFirstIf(
                this,
                predicate,
                token,
                ColorFunctionFunctionParserToken.class
        );
    }

    // visitor .........................................................................................................

    @Override
    void accept(final ColorFunctionParserTokenVisitor visitor) {
        if (Visiting.CONTINUE == visitor.startVisit(this)) {
            this.acceptValues(visitor);
        }
        visitor.endVisit(this);
    }

    void acceptValues(final ColorFunctionParserTokenVisitor visitor) {
        for (ParserToken token : this.value()) {
            visitor.accept(token);
        }
    }

    // Object .........................................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof ColorFunctionFunctionParserToken;
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
