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


import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.test.ParseStringTesting;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

abstract public class RgbColorComponentTestCase<C extends RgbColorComponent> extends ColorComponentTestCase<C>
    implements ParseStringTesting<C> {

    RgbColorComponentTestCase() {
        super();
    }

    // constants

    final static byte VALUE = 0x11;
    final static byte VALUE2 = 0x22;

    // tests

    @Test
    public final void testWith() {
        this.createComponentAndCheck(VALUE, VALUE, VALUE / 255f);
    }

    @Test
    public final void testWith2() {
        this.createComponentAndCheck(VALUE2, VALUE2, VALUE2 / 255f);
    }

    @Test
    public final void testWithZero() {
        this.createComponentAndCheck((byte) 0, 0x0, 0.0f);
    }

    @Test
    public final void testWith0xFF() {
        this.createComponentAndCheck((byte) 0xFF, 0xFF, 1.0f);
    }

    private void createComponentAndCheck(final byte value, final int unsigned, final float floatValue) {
        final C component = this.createColorComponent(value);
        this.checkEquals(value, component.value(), "value");
        this.checkEquals(unsigned, component.unsignedIntValue, "unsignedIntValue");
        assertEquals(floatValue, component.floatValue, 0.1f, "floatValue");
    }

    // add
    @Test
    public final void testAddZero() {
        final C component = this.createColorComponent(VALUE);
        assertSame(component, component.add(0));
    }

    @Test
    public final void testAddZero2() {
        final C component = this.createColorComponent(VALUE2);
        assertSame(component, component.add(0));
    }

    @Test
    public final void testAddOne() {
        this.addAndCheck(this.createColorComponent(VALUE), 1, VALUE + 1);
    }

    @Test
    public final void testAddOne2() {
        this.addAndCheck(this.createColorComponent(VALUE2), 1, VALUE2 + 1);
    }

    @Test
    public final void testAddNegativeOne() {
        this.addAndCheck(this.createColorComponent(VALUE), -1, VALUE - 1);
    }

    @Test
    public final void testAddNegativeOne2() {
        this.addAndCheck(this.createColorComponent(VALUE2), -1, VALUE2 - 1);
    }

    @Test
    public final void testAddSaturatedOverflows() {
        final C component = this.createColorComponent(VALUE);
        final C added = Cast.to(component.add(512));
        this.checkEquals(255, added.unsignedIntValue);
    }

    @Test
    public final void testAddSaturatedUnderflow() {
        final C component = this.createColorComponent(VALUE);
        final C added = Cast.to(component.add(-512));
        this.checkEquals(0, added.unsignedIntValue);
    }

    private void addAndCheck(final RgbColorComponent component,
                             final int add,
                             final int value) {
        final RgbColorComponent added = Cast.to(component.add(add));
        assertNotSame(component, added);
        this.checkEquals(component.getClass(), added.getClass(), "result of add was not the same component type");
        this.checkEquals(value, added.unsignedIntValue, "component " + component + " add " + add + " =" + value);
    }

    // invert

    @Test
    public final void testInvert1() {
        this.invertAndCheck(this.createColorComponent((byte) 0), 255);
    }

    @Test
    public final void testInvert2() {
        this.invertAndCheck(this.createColorComponent((byte) 1), 254);
    }

    @Test
    public final void testInvertAll() {
        for (int i = 0; i < 255; i++) {
            this.invertAndCheck(this.createColorComponent((byte) i), ~i);
        }
    }

    private void invertAndCheck(final RgbColorComponent component, final int value) {
        final RgbColorComponent inverted = component.invert();
        assertNotSame(component, inverted, "invert should not return this");
        this.checkEquals((byte) value, inverted.value(), "value");
    }

    @Test
    public final void testSameValueSameInstance() {
        assertSame(this.createColorComponent(VALUE), this.createColorComponent(VALUE));
    }

    @Test
    public final void testToString() {
        this.toStringAndCheck(this.createColorComponent(VALUE), Integer.toHexString(VALUE).toUpperCase());
    }

    @Test
    public final void testToStringFF() {
        this.toStringAndCheck(this.createColorComponent((byte) 0xFF), "ff");
    }

    @Test
    public final void testToStringLessThan16() {
        this.toStringAndCheck(this.createColorComponent((byte) 0xF), "0f");
    }

    final C createColorComponent() {
        return this.createColorComponent(VALUE);
    }

    abstract C createColorComponent(byte value);

    @Override
    public final C createObject() {
        return this.createColorComponent();
    }

    // parse............................................................................................................

    @Test
    public final void testParseInvalidNumberFails() {
        this.parseStringFails(
            "invalid1",
            NumberFormatException.class
        );
    }

    @Test
    public final void testParseNegativeNumberFails() {
        this.parseStringFails(
            "-1",
            new IllegalArgumentException("Invalid value -1 < 0 or > 255")
        );
    }

    @Test
    public final void testParsePositiveNumberFails() {
        this.parseStringFails(
            "256",
            new IllegalArgumentException("Invalid value 256 < 0 or > 255")
        );
    }

    @Test
    public final void testParseZero() {
        this.parseStringAndCheck(
            "0",
            this.createColorComponent((byte) 0)
        );
    }

    @Test
    public final void testParse255() {
        this.parseStringAndCheck(
            "255",
            this.createColorComponent((byte) 255)
        );
    }

    @Override
    public final Class<? extends RuntimeException> parseStringFailedExpected(final Class<? extends RuntimeException> thrown) {
        return thrown;
    }

    @Override
    public final RuntimeException parseStringFailedExpected(final RuntimeException thrown) {
        return thrown;
    }

    // IsMethodTesting..................................................................................................

    @Override
    public final Predicate<String> isMethodIgnoreMethodFilter() {
        return (m) -> false;
    }

    @Override
    public final String toIsMethodName(final String typeName) {
        return this.toIsMethodNameWithPrefixSuffix(
            typeName,
            "",
            RgbColorComponent.class.getSimpleName()
        );
    }

    // HasText..........................................................................................................

    @Test
    public final void testTextWith0() {
        this.textAndCheck(
            this.createColorComponent((byte) 0),
            "0"
        );
    }

    @Test
    public final void testTextWith255() {
        this.textAndCheck(
            this.createColorComponent((byte) 255),
            "255"
        );
    }

    // TypeNameTesting .................................................................................................

    @Override
    public final String typeNameSuffix() {
        return RgbColorComponent.class.getSimpleName();
    }
}
