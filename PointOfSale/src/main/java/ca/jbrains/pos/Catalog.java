package ca.jbrains.pos;

import io.atlassian.fugue.Option;

public interface Catalog {
    Option<Price> findPrice(String barcode);
}
