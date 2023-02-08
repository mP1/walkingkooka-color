package test;

import com.google.gwt.junit.client.GWTTestCase;

import walkingkooka.color.Color;
import walkingkooka.j2cl.locale.LocaleAware;

@LocaleAware
public class TestGwtTest extends GWTTestCase {

    @Override
    public String getModuleName() {
        return "test.Test";
    }

    public void testAssertEquals() {
        assertEquals(
                1,
                1
        );
    }

    public void testParseRgbThreeValuesCsv() {
        assertEquals(
                "parse \"rgb(1,2,3)\"",
                Color.fromRgb(0x10203),
                Color.parse("rgb(1,2,3)")
        );
    }
}
