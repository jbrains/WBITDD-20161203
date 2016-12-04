package ca.jbrains.pos.test;

import io.atlassian.fugue.Option;
import org.junit.Assert;
import org.junit.Test;

public abstract class FindPriceInCatalogContract {
    @Test
    public void productFound() throws Exception {
        Assert.assertEquals(
                Option.some(Price.cents(1250)),
                catalogWith(
                        "::barcode::", Price.cents(1250)
                ).findPrice("::barcode::")
        );
    }

    protected abstract Catalog catalogWith(String barcode, Price matchingPrice);

    @Test
    public void productNotFound() throws Exception {
        Assert.assertEquals(
                Option.none(),
                catalogWithout("::barcode not found::").findPrice("::barcode not found::")
        );
    }

    protected abstract Catalog catalogWithout(String barcodeToAvoid);
}
