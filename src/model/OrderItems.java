package model;

import java.math.BigDecimal;

public class OrderItems {
    private final Product product;
    private final int qty;

    public OrderItems(Product product, int qty) {
        if (qty <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.product = product;
        this.qty = qty;
    }

    public Product getProduct() {
        return product;
    }
    public int getQuantity() {
        return qty;
    }

    public BigDecimal getSubtotal() {
        return product.getPrice().multiply(BigDecimal.valueOf(qty));
    }
}