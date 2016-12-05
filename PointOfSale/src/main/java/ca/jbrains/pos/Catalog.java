package ca.jbrains.pos;

import javaslang.control.Option;

public interface Catalog {
    Option<Price> findPrice(String barcode);
}
