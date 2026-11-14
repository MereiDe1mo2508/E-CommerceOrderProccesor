package model;

import ObserverPattern.OrderObserver;
import ObserverPattern.OrderSubject;
import StrategyPattern.PaymentStrategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Order implements OrderSubject {
    private final String id;
    private final Customer customer;
    private final List<OrderItems> items = new ArrayList<>();
    private final List<OrderObserver> observers = new ArrayList<>();
    private OrderStatus status = OrderStatus.CREATED;
    private PaymentStrategy paymentStrategy;

    public Order(String id, Customer customer) {
        this.id = Objects.requireNonNull(id);
        this.customer = Objects.requireNonNull(customer);
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() { return customer; }
    public OrderStatus getStatus() { return status; }

    public List<OrderItems> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addItem(Product product, int qty) {
        items.add(new OrderItems(product, qty));
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public BigDecimal calculateTotal() {
        return items.stream()
                .map(OrderItems::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean pay() {
        if (status != OrderStatus.CREATED || paymentStrategy == null) {
            System.out.println("Order " + id + " cannot be paid now");
            return false;
        }
        if (!paymentStrategy.pay(calculateTotal())) {
            return false;
        }
        setStatus(OrderStatus.PAID);
        return true;
    }

    public void setStatus(OrderStatus newStatus) {
        if (status == newStatus) {
            return;
        }
        status = newStatus;
        notifyObservers();
    }

    @Override
    public void attach(OrderObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(OrderObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (OrderObserver observer : new ArrayList<>(observers)) {
            observer.update(this);
        }
    }
}
