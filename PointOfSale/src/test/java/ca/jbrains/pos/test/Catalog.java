package ca.jbrains.pos.test;

import io.atlassian.fugue.Option;

public interface Catalog {
    Option<Price> findPrice(String barcode);
}
