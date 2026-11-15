package FacadePattern;

import AdapterPattern.ExternalPaymentGateway;
import AdapterPattern.GatewayPaymentAdapter;
import BuilderPattern.OrderBuilder;
import FactoryPattern.ProductFactory;
import ObserverPattern.EmailNotifier;
import ObserverPattern.InventoryObserver;
import ObserverPattern.InventoryService;
import ObserverPattern.SmsNotifier;
import StrategyPattern.CashOnDeliveryPayment;
import StrategyPattern.PaymentStrategy;
import model.Customer;
import model.Order;
import model.OrderStatus;
import model.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public final class OrderFacade {
    private final ProductFactory productFactory = new ProductFactory();
    private final InventoryService inventory = new InventoryService();
    private final Map<String, Order> orders = new HashMap<>();
    private final Map<String, PaymentStrategy> paymentMethods = new HashMap<>();

    public OrderFacade() {
        registerPaymentMethod("cash", new CashOnDeliveryPayment());
        registerPaymentMethod("external", new GatewayPaymentAdapter(new ExternalPaymentGateway(), "KZT"));
    }

    public void registerPaymentMethod(String type, PaymentStrategy strategy) {
        paymentMethods.put(type.toLowerCase(), strategy);
    }

    public Product createProduct(String type, String id, String name, BigDecimal price) {
        return productFactory.createProduct(type, id, name, price);
    }

    public void addStock(Product product, int quantity) {
        inventory.addStock(product, quantity);
    }

    public Order placeOrder(String orderId, Customer customer, Product product, int quantity, String paymentType) {
        PaymentStrategy strategy = findPaymentMethod(paymentType);
        Order order = new OrderBuilder(orderId, customer)
                .addItem(product, quantity)
                .addObserver(new EmailNotifier())
                .addObserver(new SmsNotifier())
                .addObserver(new InventoryObserver(inventory))
                .setPaymentStrategy(strategy)
                .build();

        System.out.println("Order total: " + order.calculateTotal().toPlainString());
        if (!order.pay()) {
            System.out.println("Payment failed!");
            return null;
        }
        orders.put(orderId, order);
        System.out.println("Order placed successfully!");
        return order;
    }

    public void shipOrder(String orderId) {
        Order order = getOrderOrReport(orderId);
        if (order == null) {
            return;
        }
        if (order.getStatus() != OrderStatus.PAID) {
            System.out.println("Order " + orderId + " cannot be shipped in status " + order.getStatus());
            return;
        }
        order.setStatus(OrderStatus.SHIPPED);
    }

    public void cancelOrder(String orderId) {
        Order order = getOrderOrReport(orderId);
        if (order == null) {
            return;
        }
        if (order.getStatus() != OrderStatus.PAID) {
            System.out.println("Order " + orderId + " cannot be cancelled in status " + order.getStatus());
            return;
        }
        order.setStatus(OrderStatus.CANCELLED);
        System.out.println("Order " + orderId + " cancelled.");
    }

    public void trackOrder(String orderId) {
        Order order = getOrderOrReport(orderId);
        if (order == null) {
            return;
        }
        System.out.println("Order ID: " + orderId);
        System.out.println("Current status: " + order.getStatus());
    }

    private PaymentStrategy findPaymentMethod(String paymentType) {
        PaymentStrategy strategy = paymentMethods.get(paymentType.toLowerCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Unknown payment type: " + paymentType);
        }
        return strategy;
    }

    private Order getOrderOrReport(String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Order not found!");
        }
        return order;
    }
}

