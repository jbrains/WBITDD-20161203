package ca.jbrains.pos;

public class SellOneItemController {
    private final Catalog catalog;
    private final Display display;

    public SellOneItemController(Catalog catalog, Display display) {
        this.catalog = catalog;
        this.display = display;
    }

    public void onBarcode(String barcode) {
        if ("".equals(barcode)) {
            display.displayScannedEmptyBarcodeMessage();
            return;
        }

        catalog.findPrice(barcode)
                .toRight(() -> barcode)
                .bimap(
                        (barcodeNotFound) -> {
                            display.displayProductNotFoundMessage(barcodeNotFound);
                            return true;
                        },
                        (price) -> {
                            display.displayPrice(price);
                            return true;
                        });

    }
}
