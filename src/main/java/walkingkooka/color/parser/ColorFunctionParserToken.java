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
import walkingkooka.test.HashCodeEqualsDefined;
import walkingkooka.text.Whitespace;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.text.cursor.parser.ParserTokenVisitor;
import walkingkooka.visit.Visiting;

import java.util.List;

/**
 * Base class for a rgb function {@link ParserToken}.
 */
public abstract class ColorFunctionParserToken implements ParserToken, HashCodeEqualsDefined {

    /**
     * {@see ColorFunctionDegreesUnitSymbolParserToken}
     */
    public static ColorFunctionDegreesUnitSymbolParserToken degreesUnitSymbol(final String value, final String text) {
        return ColorFunctionDegreesUnitSymbolParserToken.with(value, text);
    }

    /**
     * {@see ColorFunctionFunctionParserToken}
     */
    public static ColorFunctionFunctionParserToken function(final List<ParserToken> value, final String text) {
        return ColorFunctionFunctionParserToken.with(value, text);
    }

    /**
     * {@see ColorFunctionFunctionNameParserToken}
     */
    public static ColorFunctionFunctionNameParserToken functionName(final String value, final String text) {
        return ColorFunctionFunctionNameParserToken.with(value, text);
    }

    /**
     * {@see ColorFunctionNumberParserToken}
     */
    public static ColorFunctionNumberParserToken number(final Double value, final String text) {
        return ColorFunctionNumberParserToken.with(value, text);
    }

    /**
     * {@see ColorFunctionParenthesisCloseSymbolParserToken}
     */
    public static ColorFunctionParenthesisCloseSymbolParserToken parenthesisCloseSymbol(final String value, final String text) {
        return ColorFunctionParenthesisCloseSymbolParserToken.with(value, text);
    }
    
    /**
     * {@see ColorFunctionParenthesisOpenSymbolParserToken}
     */
    public static ColorFunctionParenthesisOpenSymbolParserToken parenthesisOpenSymbol(final String value, final String text) {
        return ColorFunctionParenthesisOpenSymbolParserToken.with(value, text);
    }

    /**
     * {@see ColorFunctionPercentageParserToken}
     */
    public static ColorFunctionPercentageParserToken percentage(final Double value, final String text) {
        return ColorFunctionPercentageParserToken.with(value, text);
    }

    /**
     * {@see ColorFunctionSeparatorSymbolParserToken}
     */
    public static ColorFunctionSeparatorSymbolParserToken separatorSymbol(final String value, final String text) {
        return ColorFunctionSeparatorSymbolParserToken.with(value, text);
    }

    /**
     * {@see ColorFunctionWhitespaceParserToken}
     */
    public static ColorFunctionWhitespaceParserToken whitespace(final String value, final String text) {
        return ColorFunctionWhitespaceParserToken.with(value, text);
    }

    static String checkText(final String text) {
        Whitespace.failIfNullOrEmptyOrWhitespace(text, "text");
        return text;
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

    public abstract boolean isDegreesUnitSymbol();

    public abstract boolean isFunction();

    public abstract boolean isFunctionName();

    public abstract boolean isNumber();

    public abstract boolean isParenthesisCloseSymbol();

    public abstract boolean isParenthesisOpenSymbol();

    public abstract boolean isPercentage();

    public abstract boolean isSeparatorSymbol();

    public abstract boolean isWhitespace();

    // ColorFunctionParserTokenVisitor..................................................................................

    @Override
    public final void accept(final ParserTokenVisitor visitor) {
        if(visitor instanceof ColorFunctionParserTokenVisitor) {
            final ColorFunctionParserTokenVisitor visitor2 = (ColorFunctionParserTokenVisitor)visitor;
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
