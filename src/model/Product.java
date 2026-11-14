package model;

import java.math.BigDecimal;

public abstract class Product {
    private String id;
    private String name;
    private BigDecimal price;

    protected Product(String id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    protected Product() {}
    public String getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public abstract String getCategory();

    public String getDescription() {
        return getCategory() + ": "+ name + " (" + price.toPlainString() + " KZT";
    }
}