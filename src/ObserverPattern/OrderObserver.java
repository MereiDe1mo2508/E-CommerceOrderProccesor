package ObserverPattern;

import model.Order;

public interface OrderObserver {
    void update(Order order);
}