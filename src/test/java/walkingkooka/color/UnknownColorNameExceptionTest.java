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

import org.junit.jupiter.api.Test;
import walkingkooka.HasShortMessageTesting;
import walkingkooka.HasValueTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.ThrowableTesting2;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class UnknownColorNameExceptionTest implements ThrowableTesting2<UnknownColorNameException>,
    HasShortMessageTesting,
    HasValueTesting {

    @Test
    public void testWithNullNameFails() {
        assertThrows(
            NullPointerException.class,
            () -> new UnknownColorNameException(null)
        );
    }

    @Test
    public void testWith() {
        final String name = "XYZ";

        final UnknownColorNameException exception = new UnknownColorNameException(name);
        this.getMessageAndCheck(
            exception,
            "Unknown color name \"XYZ\"");
        this.getShortMessageAndCheck(
            exception,
            "Unknown color name"
        );
        this.valueAndCheck(
            exception,
            name
        );

        this.checkEquals(
            name,
            exception.name(),
            "name"
        );
    }

    // class............................................................................................................

    @Override
    public void testIfClassIsFinalIfAllConstructorsArePrivate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<UnknownColorNameException> type() {
        return UnknownColorNameException.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
