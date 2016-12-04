package ca.jbrains.pos.test;

import com.google.common.collect.ImmutableMap;
import io.atlassian.fugue.Option;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class FindPriceInMemoryCatalogTest {
    @Test
    public void productFound() throws Exception {
        Assert.assertEquals(
                Option.some(Price.cents(1250)),
                new InMemoryCatalog(
                        ImmutableMap.of("12345", Price.cents(1250))
                ).findPrice("12345")
        );
    }

    @Test
    public void productNotFound() throws Exception {
        Assert.assertEquals(
                Option.none(),
                new InMemoryCatalog(ImmutableMap.of())
                        .findPrice("::barcode not found::")
        );
    }

    private static class InMemoryCatalog {
        private final Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Option<Price> findPrice(String barcode) {
            return Option.option(pricesByBarcode.get(barcode));
        }
    }
}
