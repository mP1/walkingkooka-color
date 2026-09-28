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

import walkingkooka.text.HasText;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallException;

import java.util.Objects;
import java.util.function.Function;

/**
 * Base class for all color components, including RGB, HSL and HSV.
 */
abstract public class ColorComponent implements HasText {

    ColorComponent() {
        super();
    }

    abstract public ColorComponent invert();

    // json.............................................................................................................

    static {
        //noinspection unchecked

        // XXXRgbColorComponent
        register(
            RgbColorComponent::parseHexAlpha,
            AlphaRgbColorComponent.class
        );

        register(
            RgbColorComponent::parseHexBlue,
            BlueRgbColorComponent.class
        );

        register(
            RgbColorComponent::parseHexGreen,
            GreenRgbColorComponent.class
        );

        register(
            RgbColorComponent::parseHexRed,
            RedRgbColorComponent.class
        );
    }

    private static <C extends ColorComponent> void register(final Function<String, C> parser,
                                                            final Class<C> type) {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(type),
            (final JsonNode jsonNode, final JsonNodeUnmarshallContext context) -> { // unmarshaller
                Objects.requireNonNull(jsonNode, "jsonNode");

                try {
                    return parser.apply(jsonNode.stringOrFail());
                } catch (final JsonNodeUnmarshallException cause) {
                    throw cause;
                } catch (final RuntimeException cause) {
                    throw new JsonNodeUnmarshallException(cause.getMessage(), jsonNode, cause);
                }
            },
            (final C colorComponent, final JsonNodeMarshallContext context) -> JsonNode.string(
                colorComponent.toString() // hex number 00, 01, ff
            ), // marshaller
            type
        );
    }
}
