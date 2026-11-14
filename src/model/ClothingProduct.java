package model;

import java.math.BigDecimal;

public class ClothingProduct extends Product {
    public ClothingProduct(String id, String name, BigDecimal price) {
        super(id, name, price);
    }

    @Override
    public String getCategory() {
        return "Clothing";
    }
}