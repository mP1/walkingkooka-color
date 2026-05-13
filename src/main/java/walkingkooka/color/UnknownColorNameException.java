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

package walkingkooka.color;

import walkingkooka.HasShortMessage;
import walkingkooka.HasValue;
import walkingkooka.text.CharSequences;

import java.util.Objects;

/**
 * Used to report an unknown {@link WebColorName}.
 */
public final class UnknownColorNameException extends IllegalArgumentException implements HasShortMessage,
    HasValue<String> {

    private static final long serialVersionUID = 1L;

    public UnknownColorNameException(final String name) {
        super("");
        this.name = Objects.requireNonNull(name, "name");
    }

    @Override
    public String getMessage() {
        return this.getShortMessage() + " " + CharSequences.quoteAndEscape(this.name);
    }

    // HasShortMessage..................................................................................................

    @Override
    public String getShortMessage() {
        return "Unknown color name";
    }

    // HasValue.........................................................................................................

    @Override
    public String value() {
        return this.name;
    }

    public String name() {
        return this.name;
    }

    private String name;
}
