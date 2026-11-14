package model;

import java.math.BigDecimal;

public class ElectronicsProduct extends Product {
    public ElectronicsProduct(String id, String name, BigDecimal price) {
        super(id, name, price);
    }

    @Override
    public String getCategory() {
        return "Electronics";
    }
}
