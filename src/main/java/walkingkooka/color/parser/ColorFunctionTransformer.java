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

import walkingkooka.color.Color;
import walkingkooka.color.HslColor;
import walkingkooka.color.HsvColor;
import walkingkooka.color.RgbColor;
import walkingkooka.text.CharSequences;

import java.util.Optional;

enum ColorFunctionTransformer {

    RGB {
        @Override
        Color color(final ColorFunctionParserToken first,
                    final ColorFunctionParserToken second,
                    final ColorFunctionParserToken third,
                    final Optional<ColorFunctionParserToken> alpha) {
            final RgbColor color = Color.rgb(first.colorRed(),
                    second.colorGreen(),
                    third.colorBlue());
            return alpha.map(a -> color.set(a.colorAlpha()))
                    .orElse(color);

        }
    },
    HSL {
        @Override
        Color color(final ColorFunctionParserToken first,
                    final ColorFunctionParserToken second,
                    final ColorFunctionParserToken third,
                    final Optional<ColorFunctionParserToken> alpha) {
            final HslColor hsl = Color.hsl(first.hslHue(),
                    second.hslSaturation(),
                    third.hslLightness());
            return alpha.map(a -> hsl.set(a.hslAlpha()))
                    .orElse(hsl);

        }
    },
    HSV {
        @Override
        Color color(final ColorFunctionParserToken first,
                    final ColorFunctionParserToken second,
                    final ColorFunctionParserToken third,
                    final Optional<ColorFunctionParserToken> alpha) {
            final HsvColor hsv = Color.hsv(first.hsvHue(),
                    second.hsvSaturation(),
                    third.hsvValue());
            return alpha.map(a -> hsv.set(a.hsvAlpha()))
                    .orElse(hsv);

        }
    };

    /**
     * Creates a {@link Color} using the provided component tokens.
     */
    abstract Color color(final ColorFunctionParserToken first,
                         final ColorFunctionParserToken second,
                         final ColorFunctionParserToken third,
                         final Optional<ColorFunctionParserToken> alpha);

    /**
     * Returns the {@link ColorFunctionTransformer} by name, failing if the name is unknown.
     */
    static ColorFunctionTransformer functionName(final ColorFunctionFunctionNameParserToken functionName) {
        ColorFunctionTransformer transformer;

        switch(functionName.value()) {
            case "rgb":
            case "rgba":
                transformer = ColorFunctionTransformer.RGB;
                break;
            case "hsl":
            case "hsla":
                transformer = ColorFunctionTransformer.HSL;
                break;
            case "hsv":
            case "hsva":
                transformer = ColorFunctionTransformer.HSV;
                break;
            default:
                throw new IllegalArgumentException("Unknown function " + functionName + " in " + CharSequences.quoteAndEscape(functionName.text()));
        }
        return transformer;
    }
}
