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

import java.util.Optional;

public interface ColorContextDelegator extends ColorContext {

    @Override
    default IndexedColor indexedColor(final int index) {
        return this.colorContext()
            .indexedColor(index);
    }

    @Override
    default Optional<Color> lookupColor(final Color color) {
        return this.colorContext()
            .lookupColor(color);
    }

    @Override
    default NamedColor namedColor(final String name) {
        return this.colorContext()
            .namedColor(name);
    }

    @Override
    default Color parseColor(final String color) {
        return this.colorContext()
            .parseColor(color);
    }

    ColorContext colorContext();
}
