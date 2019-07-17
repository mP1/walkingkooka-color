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

package walkingkooka.color;

import java.util.Objects;

/**
 * Provides functionality to convert a {@link RgbColor} to a {@link String} that resembles a rgb function with parameters.
 * The parameters can be decimal or percentage values.
 */
public enum RgbColorString {

    /**
     * Returns a {@link String} that looks like <code>#012345</code>. If the rgb contains an alpha
     * component it will be added..
     */
    HASH {
        @Override
        String toString0(final RgbColor color) {
            return color.toString();
        }

        @Override
        String componentToString(final RgbColorComponent component) {
            throw new UnsupportedOperationException();
        }
    },

    /**
     * Returns a {@link String} that looks like <code>rgb(100,200,99)</code>. If the rgb contains an alpha
     * component it will become the 4th parameter.
     */
    RGB_DECIMAL {
        @Override
        String toString0(final RgbColor color) {
            return this.rgbFunction(color);
        }

        @Override
        String componentToString(final RgbColorComponent component) {
            return component.toDecimalString();
        }
    },

    /**
     * Returns a {@link String} that looks like <code>rgb(100%,50%,25%)</code>. If the rgb contains an alpha
     * component it will become the 4th parameter.
     */
    RGB_PERCENTAGE {
        @Override
        String toString0(final RgbColor color) {
            return this.rgbFunction(color);
        }

        @Override
        String componentToString(final RgbColorComponent component) {
            return component.toPercentageString();
        }
    };

    public final String toString(final RgbColor color) {
        Objects.requireNonNull(color, "rgb");
        return this.toString0(color);
    }

    abstract String toString0(final RgbColor color);

    final String rgbFunction(final RgbColor color) {
        final StringBuilder b = new StringBuilder();
        b.append(color.rgbFunctionName());
        b.append('(');

        b.append(this.componentToString(color.red));
        b.append(RgbColorComponent.SEPARATOR);

        b.append(this.componentToString(color.green));
        b.append(RgbColorComponent.SEPARATOR);

        b.append(this.componentToString(color.blue));

        color.alphaComponentToString(b, this);
        b.append(')');

        return b.toString();
    }

    abstract String componentToString(final RgbColorComponent color);
}
