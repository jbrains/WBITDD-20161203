package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SellOneItemTest {
    @Test
    public void productFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(new Catalog(new HashMap<String, String>() {{
            put("12345", "EUR 7.95");
        }}), display);

        sale.onBarcode("12345");

        Assert.assertEquals("EUR 7.95", display.getText());
    }

    @Test
    public void anotherProductFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(new Catalog(new HashMap<String, String>() {{
            put("23456", "EUR 12.50");
        }}), display);

        sale.onBarcode("23456");

        Assert.assertEquals("EUR 12.50", display.getText());
    }

    @Test
    public void productNotFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(new Catalog(new HashMap<String, String>() {{
        }}), display);

        sale.onBarcode("99999");

        Assert.assertEquals("Product not found for 99999", display.getText());
    }

    @Test
    public void emptyBarcode() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(new Catalog(new HashMap<String, String>() {{
        }}), display);

        sale.onBarcode("");

        Assert.assertEquals("Scanning error: empty barcode", display.getText());
    }

    public static class Sale {
        private final Catalog catalog;
        private final Display display;

        public Sale(Catalog catalog, Display display) {
            this.catalog = catalog;
            this.display = display;
        }

        public void onBarcode(String barcode) {
            if ("".equals(barcode)) {
                display.displayScannedEmptyBarcodeMessage();
                return;
            }

            final String price = catalog.findPrice(barcode);
            if (price == null) {
                display.displayProductNotFoundMessage(barcode);
            } else {
                display.displayPrice(price);
            }
        }

    }

    public static class Display {
        private String text;

        public String getText() {
            return text;
        }

        public void displayPrice(String price) {
            this.text = price;
        }

        public void displayProductNotFoundMessage(String barcode) {
            this.text = String.format("Product not found for %s", barcode);
        }

        public void displayScannedEmptyBarcodeMessage() {
            this.text = "Scanning error: empty barcode";
        }
    }
}
