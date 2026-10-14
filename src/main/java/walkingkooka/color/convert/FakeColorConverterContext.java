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

package walkingkooka.color.convert;

import walkingkooka.color.Color;
import walkingkooka.color.ColorContext;
import walkingkooka.color.IndexedColor;
import walkingkooka.color.NamedColor;
import walkingkooka.convert.FakeConverterContext;

import java.util.Optional;

public class FakeColorConverterContext extends FakeConverterContext
    implements ColorContext {

    public FakeColorConverterContext() {
        super();
    }

    @Override
    public IndexedColor indexedColor(final int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Color> lookupColor(final Color color) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NamedColor namedColor(final String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Color parseColor(final String color) {
        throw new UnsupportedOperationException();
    }
}
