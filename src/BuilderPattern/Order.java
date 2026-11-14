package BuilderPattern;

import StrategyPattern.PaymentStrategy;
import model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private int orderId;
    private Customer customer;
    private List<OrderItems> items = new ArrayList<>();
    private Address shippingAddress;
    private OrderStatus status;
    private PaymentStrategy paymentStrategy;
    private List<OrderObserver> observer = new ArrayList<>();
    public Order(int orderId, Customer customer, OrderItems items, Address shippingAddress, OrderStatus status, PaymentStrategy paymentStrategy, OrderObserver observer) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = Collections.unmodifiableList(this.items);
        this.shippingAddress = shippingAddress;
        this.status = status;
        this.paymentStrategy = paymentStrategy;
        this.observers = Collections.unmodifiableList(this.observer);
    }
    public Order(){}
    public int getOrderId() {
        return orderId;
    }
    public Customer getCustomer() {
        return customer;
    }
    public List<OrderItems> getItems() {
        return items;
    }
    public Address getShippingAddress() {
        return shippingAddress;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public PaymentStrategy getPaymentStrategy() {
        return paymentStrategy;
    }
    public List<OrderObserver> getObservers() {
        return observers;
    }
    public double calculateTotal() {
        double total = 0;
        for (OrderItems item : items) {
            total += item.getSubtotal();
        }
        return total;
    }
    public boolean pay() {
        if (paymentStrategy == null) {
            return false;
        }
        boolean success = paymentStrategy.pay(BigDecimal.valueOf(calculateTotal()));
        if (success) {
            setStatus(OrderStatus.PAID);
            notifyObservers();
        }
        return success;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public void attach(OrderObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }
    public void detach(OrderObserver observer) {
        observers.remove(observer);
    }
    public void notifyObservers() {
        for (OrderObserver observer : observers) {
            observer.update(this);
        }
    }
    @Override
    public String toString() {
        return "Order{orderId='" + orderId + "', customer='" + customer + "', items='" + items +  "', total=" + calculateTotal() + "', status='" + getStatus() + "', shipping address='" + shippingAddress + "', payment method='" + paymentStrategy + "}";
    }
}