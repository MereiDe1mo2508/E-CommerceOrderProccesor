package ObserverPattern;

import model.Order;

public class EmailNotifier implements OrderObserver {

    @Override
    public void update(Order order) {
        System.out.println("[Email] To" + order.getCustomer().getEmail() + ": your order " + order.getOrderId() + " is now " + order.getStatus());
    }
}