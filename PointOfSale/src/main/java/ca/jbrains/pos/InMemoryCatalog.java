package ca.jbrains.pos;

import io.atlassian.fugue.Option;

import java.util.Map;

public class InMemoryCatalog implements Catalog {
    private final Map<String, Price> pricesByBarcode;

    public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
        this.pricesByBarcode = pricesByBarcode;
    }

    public Option<Price> findPrice(String barcode) {
        return Option.option(pricesByBarcode.get(barcode));
    }
}
