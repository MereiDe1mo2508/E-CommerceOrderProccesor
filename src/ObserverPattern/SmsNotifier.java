package ObserverPattern;

import model.Order;

public class SmsNotifier implements OrderObserver {

    @Override
    public void update(Order order) {
        System.out.println("[SMS] To " + order.getCustomer().getPhone()
                + ": order " + order.getId() + " -> " + order.getStatus());
    }
}
