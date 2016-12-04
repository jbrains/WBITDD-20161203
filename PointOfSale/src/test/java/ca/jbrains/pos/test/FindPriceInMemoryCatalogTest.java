package ca.jbrains.pos.test;

import com.google.common.collect.ImmutableMap;
import io.atlassian.fugue.Option;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

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

    private static class InMemoryCatalog implements Catalog {
        private final Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Option<Price> findPrice(String barcode) {
            return Option.option(pricesByBarcode.get(barcode));
        }
    }
}
