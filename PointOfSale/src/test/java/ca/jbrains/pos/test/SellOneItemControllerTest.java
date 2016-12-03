package ca.jbrains.pos.test;

import io.atlassian.fugue.Option;
import lombok.Value;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class SellOneItemControllerTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void productFound() throws Exception {
        final Catalog catalog = context.mock(Catalog.class);
        final Display display = context.mock(Display.class);
        final SellOneItemController controller = new SellOneItemController(catalog, display);

        context.checking(new Expectations() {{
            allowing(catalog).findPrice(with("12345"));
            will(returnValue(Option.some(Price.cents(795))));

            oneOf(display).displayPrice(with(Price.cents(795)));
        }});

        controller.onBarcode("12345");
    }

    public interface Catalog {
        Option<Price> findPrice(String barcode);
    }

    public interface Display {
        void displayPrice(Price price);
    }

    public static class SellOneItemController {
        private final Catalog catalog;
        private final Display display;

        public SellOneItemController(Catalog catalog, Display display) {
            this.catalog = catalog;
            this.display = display;
        }

        public void onBarcode(String barcode) {
            display.displayPrice(catalog.findPrice(barcode).get());
        }
    }

    @Value(staticConstructor = "cents")
    public static class Price {
        private final int centsValue;
    }
}
