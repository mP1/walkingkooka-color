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

import walkingkooka.test.StandardThrowableTesting;
import walkingkooka.type.JavaVisibility;

public final class ColorParserExceptionTest implements StandardThrowableTesting<ColorParserException> {

    @Override
    public ColorParserException createThrowable(final String message) {
        return new ColorParserException(message);
    }

    @Override
    public ColorParserException createThrowable(final String message, final Throwable cause) {
        return new ColorParserException(message, cause);
    }

    @Override
    public Class<ColorParserException> type() {
        return ColorParserException.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
