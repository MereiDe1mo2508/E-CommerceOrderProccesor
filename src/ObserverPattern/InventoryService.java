package ObserverPattern;

import model.Product;

import java.util.HashMap;
import java.util.Map;

public class InventoryService implements Inventory {
    private final Map<String, Integer> stock = new HashMap<>();

    public void addStock(Product product, int qty) {
        stock.merge(product.getId(), qty, Integer::sum);
    }

    public int getStock(Product product) {
        return stock.getOrDefault(product.getId(), 0);
    }

    @Override
    public boolean reserve(Product product, int qty) {
        int current = getStock(product);
        if (current < qty) {
            return false;
        }
        stock.put(product.getId(), current - qty);
        return true;
    }

    @Override
    public void release(Product product, int qty) {
        stock.merge(product.getId(), qty, Integer::sum);
    }
}