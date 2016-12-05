package ca.jbrains.pos;

import javaslang.API;

import static javaslang.API.*;
import static javaslang.Patterns.None;
import static javaslang.Patterns.Some;

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

        Match(catalog.findPrice(barcode)).of(
                Case(Some($()), price -> run(() -> display.displayPrice(price))),
                Case(None(), o -> run(() -> display.displayProductNotFoundMessage(barcode)))
        );
    }
}
