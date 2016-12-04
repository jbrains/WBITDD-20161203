package ca.jbrains.pos;

import ca.jbrains.pos.Display;
import ca.jbrains.pos.Price;

import java.io.PrintWriter;
import java.io.Writer;

public class WriterDisplay implements Display {
    private final PrintWriter canvas;

    public WriterDisplay(Writer out) {
        this.canvas = new PrintWriter(out, true);
    }

    @Override
    public void displayPrice(Price price) {
        canvas.println(String.format("EUR %.2f", price.euro()));
    }

    @Override
    public void displayProductNotFoundMessage(String barcodeNotFound) {
        canvas.println(String.format("Product not found for %s", barcodeNotFound));
    }

    @Override
    public void displayScannedEmptyBarcodeMessage() {
        canvas.println("Scanning error: empty barcode");
    }
}
