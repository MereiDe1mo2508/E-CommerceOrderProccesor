package ObserverPattern;

import model.Product;

public interface Inventory {
    boolean reserve(Product product, int qty);

    void release(Product product, int qty);
}
