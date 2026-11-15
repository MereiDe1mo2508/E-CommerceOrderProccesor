import FacadePattern.OrderFacade;
import StrategyPattern.CardPayment;
import StrategyPattern.PayPalPayment;
import model.Customer;
import model.Product;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        OrderFacade facade = new OrderFacade();
        facade.registerPaymentMethod("card", new CardPayment("1234567812345678"));
        facade.registerPaymentMethod("paypal", new PayPalPayment("dariya@mail.com"));

        Product phone = facade.createProduct("electronics", "P1", "iPhone 15", new BigDecimal("450000"));
        Product book = facade.createProduct("book", "P2", "Clean Code", new BigDecimal("12000"));
        Product shirt = facade.createProduct("clothing", "P3", "T-shirt", new BigDecimal("8000"));
        Product pizza = facade.createProduct("food", "P4", "Pizza", new BigDecimal("3000"));

        System.out.println(phone.getDescription());
        System.out.println(book.getDescription());
        System.out.println(shirt.getDescription());
        System.out.println(pizza.getDescription());

        facade.addStock(phone, 10);
        facade.addStock(book, 20);
        facade.addStock(shirt, 50);
        facade.addStock(pizza, 30);

        Customer aibek = new Customer(1, "Aibek", "aibek@mail.com", "+77001234567");
        Customer dariya = new Customer(2, "Dariya", "dariya@mail.com", "+77759876543");

        printTitle("Order 1: card payment, then ship");
        facade.placeOrder("O-1", aibek, phone, 1, "card");
        facade.shipOrder("O-1");
        facade.trackOrder("O-1");

        printTitle("Order 1: cancel after shipping is not allowed");
        facade.cancelOrder("O-1");

        printTitle("Order 2: external gateway (Adapter), then cancel");
        facade.placeOrder("O-2", dariya, book, 2, "external");
        facade.cancelOrder("O-2");
        facade.trackOrder("O-2");

        printTitle("Order 3: PayPal");
        facade.placeOrder("O-3", dariya, shirt, 3, "paypal");

        printTitle("Order 4: cash on delivery");
        facade.placeOrder("O-4", aibek, pizza, 2, "cash");

        printTitle("Unknown order and unknown payment type");
        facade.trackOrder("O-99");
        try {
            facade.placeOrder("O-5", aibek, book, 1, "bitcoin");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void printTitle(String title) {
        System.out.println("\n=== " + title + " ===");
    }
}
