package ca.jbrains.pos.test;

import ca.jbrains.pos.Catalog;
import ca.jbrains.pos.InMemoryCatalog;
import ca.jbrains.pos.Price;
import com.google.common.collect.ImmutableMap;

public class FindPriceInMemoryCatalogTest extends FindPriceInCatalogContract {
    @Override
    protected Catalog catalogWith(String barcode, Price matchingPrice) {
        return new InMemoryCatalog(
                ImmutableMap.of(barcode, matchingPrice)
        );
    }

    @Override
    protected Catalog catalogWithout(String barcodeToAvoid) {
        return new InMemoryCatalog(ImmutableMap.of());
    }

}
