package FacadePattern;

import FactoryPattern.*;
import model.*;
import ObserverPattern.*;
import StrategyPattern.*;
import BuilderPattern.*;
import AdapterPattern.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class orderFacade {

    private ProductFactory factory;
    public InventoryService inventory;

    public Map<String, Order> orders;

    public orderFacade() {
        factory = new ProductFactory();
        inventory = new InventoryService();
        orders = new HashMap<>();
    }

    public Order placeOrder(String orderId, Customer customer, Product product, int quantity, String paymentType) {
        Order order = new Order(orderId, customer);
        order.addItem(product, quantity);
        order.attach(new EmailNotifier());
        order.attach(new SmsNotifier());
        order.attach(new InventoryObserver(inventory));
        switch (paymentType.toLowerCase()) {
            case "card":
                order.setPaymentStrategy(new CardPayment("1234567812345678"));
                break;

            case "paypal":
                order.setPaymentStrategy(new PayPalPayment("aibek@mail.com"));
                break;

            case "cash":
                order.setPaymentStrategy(new CashOnDeliveryPayment());
                break;

            case "external":
                order.setPaymentStrategy(new GatewayPaymentAdapter("KZT"));
                break;

            default:
                throw new IllegalArgumentException("Unknown payment type");
        }
        System.out.println("Order total: " + order.calculateTotal().toPlainString());
        if (order.pay()) {
            orders.put(orderId, order);
            System.out.println("Order placed successfully!");
            return order;
        }
        System.out.println("Payment failed!");
        return null;
    }

    public void cancelOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Order not found!");
            return;
        }
        order.setStatus(OrderStatus.CANCELLED);
        System.out.println("Order " + orderId + " cancelled.");
    }
    public void trackOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Order not found!");
            return;
        }
        System.out.println("Order ID: " + orderId);
        System.out.println("Current status: " + order.getStatus());
    }
    private BigDecimal money(String value) {
        return new BigDecimal(value);
    }
}
