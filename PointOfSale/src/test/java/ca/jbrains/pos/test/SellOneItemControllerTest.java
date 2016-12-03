package ca.jbrains.pos.test;

import io.atlassian.fugue.Option;
import lombok.Value;
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

    public interface Catalog {
        Option<Price> findPrice(String barcode);
    }

    public interface Display {
        void displayPrice(Price price);

        void displayProductNotFoundMessage(String barcodeNotFound);
    }

    public static class SellOneItemController {
        private final Catalog catalog;
        private final Display display;

        public SellOneItemController(Catalog catalog, Display display) {
            this.catalog = catalog;
            this.display = display;
        }

        public void onBarcode(String barcode) {
            final Option<Price> maybePrice = catalog.findPrice(barcode);
            if (maybePrice.isEmpty())
                display.displayProductNotFoundMessage(barcode);
            else
                display.displayPrice(maybePrice.get());
        }
    }

    @Value(staticConstructor = "cents")
    public static class Price {
        private final int centsValue;
    }
}
