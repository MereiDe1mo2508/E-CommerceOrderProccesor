package model;

import java.math.BigDecimal;

public class BookProduct extends Product {
    public  BookProduct(String id, String name, BigDecimal price) {
        super(id, name, price);
    }

    @Override
    public String getCategory() {
        return "Book";
    }
}
