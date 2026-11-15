package BuilderPattern;

import ObserverPattern.OrderObserver;
import StrategyPattern.PaymentStrategy;
import model.*;

import java.util.ArrayList;
import java.util.List;
public class OrderBuilder {
    private String orderId;
    private Customer customer;
    private List<OrderItem> items = new ArrayList<>();
    private List<OrderObserver> observers = new ArrayList<>();
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