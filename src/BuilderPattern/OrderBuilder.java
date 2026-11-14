package BuilderPattern;

import StrategyPattern.PaymentStrategy;
import model.*;

import java.util.ArrayList;
import java.util.List;
public class OrderBuilder {
    private final Order order;
    private final Customer customer;
    private List<OrderItems> items = new ArrayList<>();
    private final Address shippingAddress;
    private final OrderStatus status;
    private final PaymentStrategy paymentStrategy;
    private List<OrderObserver> observers = new ArrayList<>();
    public OrderBuilder(Builder builder) {
        this.order = builder.order;
        this.customer = builder.customer;
        this.items = builder.items;
        this.shippingAddress = builder.shippingAddress;
        this.status = builder.status;
        this.paymentStrategy = builder.paymentStrategy;
        this.observers = builder.observers;
    }
    public int getOrder() {
        return order;
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
    public static class Builder {
        private Order order;
        private Customer customer;
        private List<OrderItems> items = new ArrayList<>();
        private Address shippingAddress;
        private OrderStatus status;
        private PaymentStrategy paymentStrategy;
        private List<OrderObserver> observers = new ArrayList<>();
        public Builder setOrder(Order order) {
            this.order = order;
            return this;
        }
        public Builder setCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }
        public Builder setItems(List<OrderItems> items) {
            this.items = items;
            return this;
        }
        public Builder setShippingAddress(Address shippingAddress) {
            this.shippingAddress = shippingAddress;
            return this;
        }
        public Builder setStatus(OrderStatus status) {
            this.status = status;
            return this;
        }
        public Builder setPaymentStrategy(PaymentStrategy paymentStrategy) {
            this.paymentStrategy = paymentStrategy;
            return this;
        }
        public Builder setObservers(List<OrderObserver> observers) {
            this.observers = observers;
            return this;
        }
        public OrderBuilder addItem(Product product, int qty) {
            if (product == null) {
                throw new IllegalArgumentException("Product cannot be null");
            }
            if (qty <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than zero");
            }
            order.getItems().add(new OrderItems(product, qty));
            return this.build();
        }
        public OrderBuilder build() {
            if(customer == null) {
                throw new IllegalStateException("Customer shouldn't be empty. Please fill the main values");
            }
            if(items.isEmpty()) {
                throw new IllegalStateException("Items shouldn't be empty. Please add any items you want to pay and deliver");
            }
            if(shippingAddress == null) {
                throw new IllegalStateException("Address shouldn't be empty. Please add the address you want your items to be shipped");
            }
            return new OrderBuilder(this);
        }
    }
}
