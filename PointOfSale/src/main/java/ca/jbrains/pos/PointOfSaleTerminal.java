package ca.jbrains.pos;

import com.google.common.collect.ImmutableMap;

import java.io.OutputStreamWriter;

public class PointOfSaleTerminal {
    public static void main(String[] args) {
        final SellOneItemController controller = new SellOneItemController(
                new InMemoryCatalog(
                        ImmutableMap.of("12345", Price.cents(795))
                ),
                new WriterDisplay(new OutputStreamWriter(System.out))
        );

        controller.onBarcode("34756");
        controller.onBarcode("12345");
        controller.onBarcode("");
    }
}
