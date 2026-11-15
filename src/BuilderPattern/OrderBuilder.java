package BuilderPattern;

import ObserverPattern.OrderObserver;
import StrategyPattern.PaymentStrategy;
import model.Customer;
import model.Order;
import model.OrderItem;
import model.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {
    private final String orderId;
    private final Customer customer;
    private final List<OrderItem> items = new ArrayList<>();
    private final List<OrderObserver> observers = new ArrayList<>();
    private PaymentStrategy paymentStrategy;

    public OrderBuilder(String orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
    }

    public OrderBuilder addItem(Product product, int quantity) {
        items.add(new OrderItem(product, quantity));
        return this;
    }

    public OrderBuilder addObserver(OrderObserver observer) {
        observers.add(observer);
        return this;
    }

    public OrderBuilder setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
        return this;
    }

    public Order build() {
        if (items.isEmpty()) {
            throw new IllegalStateException("Order must contain at least one item");
        }
        if (paymentStrategy == null) {
            throw new IllegalStateException("Payment strategy is required");
        }
        Order order = new Order(orderId, customer);
        for (OrderItem item : items) {
            order.addItem(item.getProduct(), item.getQuantity());
        }
        for (OrderObserver observer : observers) {
            order.attach(observer);
        }
        order.setPaymentStrategy(paymentStrategy);
        return order;
    }
}
