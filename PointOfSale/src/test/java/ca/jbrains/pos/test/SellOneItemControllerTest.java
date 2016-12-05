package ca.jbrains.pos.test;

import ca.jbrains.pos.Catalog;
import ca.jbrains.pos.Display;
import ca.jbrains.pos.Price;
import ca.jbrains.pos.SellOneItemController;
import javaslang.control.Option;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SellOneItemControllerTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    private Catalog catalog;
    private Display display;
    private SellOneItemController controller;

    @Before
    public void setUp() throws Exception {
        catalog = context.mock(Catalog.class);
        display = context.mock(Display.class);
        controller = new SellOneItemController(catalog, display);
    }

    @Test
    public void productFound() throws Exception {
        context.checking(new Expectations() {{
            allowing(catalog).findPrice(with("12345"));
            will(returnValue(Option.some(Price.cents(795))));

            oneOf(display).displayPrice(with(Price.cents(795)));
        }});

        controller.onBarcode("12345");
    }

    @Test
    public void productNotFound() throws Exception {
        context.checking(new Expectations() {{
            allowing(catalog).findPrice(with("::barcode not found::"));
            will(returnValue(Option.none()));

            oneOf(display).displayProductNotFoundMessage(with("::barcode not found::"));
        }});

        controller.onBarcode("::barcode not found::");
    }

    @Test
    public void emptyBarcode() throws Exception {
        context.checking(new Expectations() {{
            oneOf(display).displayScannedEmptyBarcodeMessage();
        }});

        // REFACTOR Move this test to a separate class _or_
        // move this behavior to another location, because
        // only this test wants to use a different Catalog
        // (_or_, if you prefer, a different Controller).
        controller = new SellOneItemController(null, display);

        controller.onBarcode("");
    }

}
