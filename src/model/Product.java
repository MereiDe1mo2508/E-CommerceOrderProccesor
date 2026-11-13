package model;

import java.math.BigDecimal;

public abstract class Product {
    private final String id;
    private final String name;
    private final BigDecimal price;

    protected Product(String id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }

    public abstract String getCategory();

    public String getDescription() {
        return getCategory() + ": "+ name + " (" + price.toPlainString() + " KZT";
    }
}
