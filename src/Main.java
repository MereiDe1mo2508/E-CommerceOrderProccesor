import FactoryPattern.ProductFactory;
import model.FoodProduct;
import model.Customer;
import model.Order;
import model.OrderStatus;
import model.Product;
import ObserverPattern.EmailNotifier;
import ObserverPattern.InventoryObserver;
import ObserverPattern.InventoryService;
import ObserverPattern.SmsNotifier;
import StrategyPattern.CardPayment;
import StrategyPattern.CashOnDeliveryPayment;
import StrategyPattern.PayPalPayment;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        ProductFactory factory = new ProductFactory();
        Product phone = factory.createProduct("electronics", "P1", "iPhone 15", money("450000"));
        Product shirt = factory.createProduct("clothing", "P2", "T-shirt", money("8000"));
        Product book = factory.createProduct("book", "P3", "Clean Code", money("12000"));

        System.out.println(phone.getDescription());
        System.out.println(shirt.getDescription());
        System.out.println(book.getDescription());

        InventoryService inventory = new InventoryService();
        inventory.addStock(phone, 10);
        inventory.addStock(shirt, 50);
        inventory.addStock(book, 20);

        Customer customer = new Customer(1, "Aibek", "aibek@mail.com", "+77001234567");

        System.out.println("\n=== Order 1: Card ===");
        Order order1 = new Order("O-1", customer);
        order1.addItem(phone, 1);
        order1.addItem(book, 2);
        order1.attach(new EmailNotifier());
        order1.attach(new SmsNotifier());
        order1.attach(new InventoryObserver(inventory));
        order1.setPaymentStrategy(new CardPayment("1234567812345678"));
        System.out.println("Total: " + order1.calculateTotal().toPlainString());
        order1.pay();
        order1.setStatus(OrderStatus.SHIPPED);
        System.out.println("Phone stock: " + inventory.getStock(phone));

        System.out.println("\n=== Order 2: PayPal fails, switch strategy ===");
        Order order2 = new Order("O-2", customer);
        order2.addItem(shirt, 3);
        order2.attach(new EmailNotifier());
        order2.setPaymentStrategy(new PayPalPayment("wrong-email"));
        if (!order2.pay()) {
            order2.setPaymentStrategy(new CashOnDeliveryPayment());
            order2.pay();
        }

        System.out.println("\n=== Order 3: Cancel ===");
        Order order3 = new Order("O-3", customer);
        order3.addItem(book, 5);
        order3.attach(new InventoryObserver(inventory));
        order3.setPaymentStrategy(new CashOnDeliveryPayment());
        order3.pay();
        System.out.println("Book stock after pay: " + inventory.getStock(book));
        order3.setStatus(OrderStatus.CANCELLED);
        System.out.println("Book stock after cancel: " + inventory.getStock(book));

        System.out.println("\n=== Factory: new type without changing ProductFactory ===");
        factory.register("food", FoodProduct::new);
        System.out.println(factory.createProduct("food", "P4", "Pizza", money("3000")).getDescription());

        System.out.println("\n=== Factory: unknown type ===");
        try {
            factory.createProduct("toys", "P5", "Robot", money("5000"));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static BigDecimal money(String value) {
        return new BigDecimal(value);
    }
}