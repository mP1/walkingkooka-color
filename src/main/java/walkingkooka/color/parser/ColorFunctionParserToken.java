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

import walkingkooka.Cast;
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
import walkingkooka.text.Whitespace;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.text.cursor.parser.ParserTokenVisitor;
import walkingkooka.visit.Visiting;

import java.util.List;

/**
 * Base class for a rgb function {@link ParserToken}.
 */
public abstract class ColorFunctionParserToken implements ParserToken {

    /**
     * {@see DegreesUnitSymbolColorFunctionParserToken}
     */
    public static DegreesUnitSymbolColorFunctionParserToken degreesUnitSymbol(final String value, final String text) {
        return DegreesUnitSymbolColorFunctionParserToken.with(value, text);
    }

    /**
     * {@see FunctionColorFunctionParserToken}
     */
    public static FunctionColorFunctionParserToken function(final List<ParserToken> value, final String text) {
        return FunctionColorFunctionParserToken.with(value, text);
    }

    /**
     * {@see NameColorFunctionParserToken}
     */
    public static NameColorFunctionParserToken name(final String value, final String text) {
        return NameColorFunctionParserToken.with(value, text);
    }

    /**
     * {@see NumberColorFunctionParserToken}
     */
    public static NumberColorFunctionParserToken number(final Double value, final String text) {
        return NumberColorFunctionParserToken.with(value, text);
    }

    /**
     * {@see ParenthesisCloseSymbolColorFunctionParserToken}
     */
    public static ParenthesisCloseSymbolColorFunctionParserToken parenthesisCloseSymbol(final String value, final String text) {
        return ParenthesisCloseSymbolColorFunctionParserToken.with(value, text);
    }

    /**
     * {@see ParenthesisOpenSymbolColorFunctionParserToken}
     */
    public static ParenthesisOpenSymbolColorFunctionParserToken parenthesisOpenSymbol(final String value, final String text) {
        return ParenthesisOpenSymbolColorFunctionParserToken.with(value, text);
    }

    /**
     * {@see PercentageColorFunctionParserToken}
     */
    public static PercentageColorFunctionParserToken percentage(final Double value, final String text) {
        return PercentageColorFunctionParserToken.with(value, text);
    }

    /**
     * {@see SeparatorSymbolColorFunctionParserToken}
     */
    public static SeparatorSymbolColorFunctionParserToken separatorSymbol(final String value, final String text) {
        return SeparatorSymbolColorFunctionParserToken.with(value, text);
    }

    /**
     * {@see WhitespaceColorFunctionParserToken}
     */
    public static WhitespaceColorFunctionParserToken whitespace(final String value, final String text) {
        return WhitespaceColorFunctionParserToken.with(value, text);
    }

    static String checkText(final String text) {
        return Whitespace.failIfNullOrEmptyOrWhitespace(text, "text");
    }

    /**
     * Package private ctor to limit subclassing.
     */
    ColorFunctionParserToken(final String text) {
        super();
        this.text = text;
    }

    /**
     * The text matched by the {@link Parser}.
     */
    public final String text() {
        return this.text;
    }

    private final String text;

    abstract Object value();

    // isXXX............................................................................................................

    public final boolean isDegreesUnitSymbol() {
        return this instanceof DegreesUnitSymbolColorFunctionParserToken;
    }

    public final boolean isFunction() {
        return this instanceof FunctionColorFunctionParserToken;
    }

    public final boolean isLeaf() {
        return this instanceof LeafColorFunctionParserToken;
    }

    public final boolean isName() {
        return this instanceof NameColorFunctionParserToken;
    }

    @Override
    public final boolean isNoise() {
        return this.isSymbol();
    }

    public final boolean isNumber() {
        return this instanceof NumberColorFunctionParserToken;
    }

    @Override
    public final boolean isParent() {
        return false == this.isLeaf();
    }

    public final boolean isParenthesisCloseSymbol() {
        return this instanceof ParenthesisCloseSymbolColorFunctionParserToken;
    }

    public final boolean isParenthesisOpenSymbol() {
        return this instanceof ParenthesisOpenSymbolColorFunctionParserToken;
    }

    public final boolean isPercentage() {
        return this instanceof PercentageColorFunctionParserToken;
    }

    public final boolean isSeparatorSymbol() {
        return this instanceof SeparatorSymbolColorFunctionParserToken;
    }

    @Override
    public final boolean isSymbol() {
        return this instanceof SymbolColorFunctionParserToken;
    }

    @Override
    public final boolean isWhitespace() {
        return this instanceof WhitespaceColorFunctionParserToken;
    }

    // ColorFunctionParserTokenVisitor..................................................................................

    @Override
    public final void accept(final ParserTokenVisitor visitor) {
        if (visitor instanceof ColorFunctionParserTokenVisitor) {
            final ColorFunctionParserTokenVisitor visitor2 = (ColorFunctionParserTokenVisitor) visitor;
            if (Visiting.CONTINUE == visitor2.startVisit(this)) {
                this.accept(visitor2);
            }
            visitor2.endVisit(this);
        }
    }

    abstract void accept(final ColorFunctionParserTokenVisitor visitor);

    // ColorFunctionTransformer.........................................................................................

    abstract RedRgbColorComponent colorRed();

    abstract BlueRgbColorComponent colorBlue();

    abstract GreenRgbColorComponent colorGreen();

    abstract AlphaRgbColorComponent colorAlpha();

    abstract HueHslColorComponent hslHue();

    abstract SaturationHslColorComponent hslSaturation();

    abstract LightnessHslColorComponent hslLightness();

    abstract AlphaHslColorComponent hslAlpha();

    abstract HueHsvColorComponent hsvHue();

    abstract SaturationHsvColorComponent hsvSaturation();

    abstract ValueHsvColorComponent hsvValue();

    abstract AlphaHsvColorComponent hsvAlpha();

    // Object...........................................................................................................

    @Override
    public final int hashCode() {
        return this.text().hashCode();
    }

    @Override
    public final boolean equals(final Object other) {
        return this == other || this.canBeEqual(other) && this.equals0(Cast.to(other));
    }

    abstract boolean canBeEqual(final Object other);

    private boolean equals0(final ColorFunctionParserToken other) {
        return this.text.equals(other.text) && this.value().equals(other.value());
    }

    @Override
    public final String toString() {
        return this.text();
    }
}
