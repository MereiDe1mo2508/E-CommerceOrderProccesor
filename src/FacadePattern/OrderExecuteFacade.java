package FacadePattern;
import FacadePattern.*;
import FactoryPattern.ProductFactory;
import ObserverPattern.InventoryService;
import model.Customer;
import model.Order;
import model.OrderStatus;
import model.Product;

import java.math.BigDecimal;

public class OrderExecuteFacade {
    orderFacade facade = new orderFacade();
    ProductFactory factory = new ProductFactory();
    Product phone = factory.createProduct("electronics", "P1", "iPhone 15", new BigDecimal("450000"));
    Product book = factory.createProduct("book", "P3", "Clean Code", new BigDecimal("12000"));
    InventoryService inventory = facade.inventory;
    Customer customer = new Customer(1, "Aibek", "aibek@mail.com", "+77001234567");
    Customer customer2 = new Customer(2, "Dariya", "dariya@mail.com", "+77759876543");
    public void execute() {
        inventory.addStock(phone, 10);
        inventory.addStock(book, 20);

        System.out.println("\n=== PLACE ORDER ===");
        facade.placeOrder("O-1", customer, phone, 1, "card"
        );

        System.out.println("\n=== TRACK ORDER ===");
        facade.trackOrder("O-1");
        Order order = facade.orders.get("O-1");
        order.setStatus(OrderStatus.SHIPPED);
        facade.trackOrder("O-1");

        System.out.println("\n=== CANCEL ORDER ===");
        facade.cancelOrder("O-2");
        facade.trackOrder("O-2");

        System.out.println("\n=== PLACE ORDER ===");
        facade.placeOrder("O-2", customer2, book, 2, "external"
        );

        System.out.println("\n=== TRACK ORDER ===");
        facade.trackOrder("O-2");
        Order order2 = facade.orders.get("O-2");
        order.setStatus(OrderStatus.SHIPPED);
        facade.trackOrder("O-2");

        System.out.println("\n=== CANCEL ORDER ===");
        facade.cancelOrder("O-2");
        facade.trackOrder("O-2");
    }

}
