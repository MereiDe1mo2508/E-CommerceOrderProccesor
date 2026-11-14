package model;

public class OrderItems {
    private final Product product;
    private double qty;
    public OrderItems(Product product, int qty) {
        this.product = product;
        this.qty = qty;
    }
    public Product getProduct() {
        return product;
    }
    public double getQuantity() {
        return qty;
    }
    public void setQty(int qty) {
        this.qty = qty;
    }
    public double getSubtotal() {
        return product.getPrice() * qty;
    }
}