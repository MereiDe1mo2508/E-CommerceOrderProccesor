package ObserverPattern;

import model.Order;
import model.OrderItems;
import model.OrderStatus;

public class InventoryObserver implements OrderObserver {
    private final Inventory inventory;

    public InventoryObserver(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void update(Order order) {
        if (order.getStatus() == OrderStatus.PAID) {
            reserveItems(order);
        } else if (order.getStatus() == OrderStatus.CANCELLED) {
            releaseItems(order);
        }
    }

    private void reserveItems(Order order) {
        for (OrderItems item : order.getItems()) {
            if(!inventory.reserve(item.getProduct(), item.getQuantity())) {
                System.out.println("[Inventory] Not enough stock for " + item.getProduct().getName());
            }
        }
        System.out.println("[Inventory] Stock reduced for order " + order.getId());
    }

    private void releaseItems(Order order) {
        for (OrderItems item : order.getItems()) {
            inventory.release(item.getProduct(), item.getQuantity());
        }
        System.out.println("[Inventory] Stock returned for order " + order.getId());
    }
}