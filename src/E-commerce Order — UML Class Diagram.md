# E-commerce Order — UML Class Diagram

Patterns: **Facade, Builder, Factory, Strategy, Adapter, Observer**

```mermaid
classDiagram
direction TB

class Client

%% ===== FACADE =====
class OrderFacade {
  -ProductFactory productFactory
  -InventoryService inventory
  +placeOrder(customer, cart, paymentType) Order
  +cancelOrder(orderId) void
  +trackOrder(orderId) OrderStatus
}

%% ===== FACTORY =====
class ProductFactory {
  +createProduct(type, id, name, price) Product
}
class Product {
  <<abstract>>
  -String id
  -String name
  -double price
  +getPrice() double
  +getDescription() String
}
class ElectronicsProduct
class ClothingProduct
class BookProduct

%% ===== BUILDER =====
class OrderBuilder {
  -Order order
  +setCustomer(Customer) OrderBuilder
  +addItem(Product, qty) OrderBuilder
  +setShippingAddress(Address) OrderBuilder
  +setPaymentStrategy(PaymentStrategy) OrderBuilder
  +setPromoCode(String) OrderBuilder
  +build() Order
}

%% ===== DOMAIN =====
class Order {
  -String id
  -Customer customer
  -List~OrderItem~ items
  -OrderStatus status
  -PaymentStrategy paymentStrategy
  -List~OrderObserver~ observers
  +calculateTotal() double
  +pay() boolean
  +setStatus(OrderStatus) void
  +attach(OrderObserver) void
  +detach(OrderObserver) void
  +notifyObservers() void
}
class OrderItem {
  -Product product
  -int quantity
  +getSubtotal() double
}
class Customer {
  -String id
  -String name
  -String email
  -String phone
}
class OrderStatus {
  <<enumeration>>
  CREATED
  PAID
  SHIPPED
  DELIVERED
  CANCELLED
}
class InventoryService {
  +reserve(Product, qty) boolean
  +release(Product, qty) void
}

%% ===== STRATEGY =====
class PaymentStrategy {
  <<interface>>
  +pay(double amount) boolean
}
class CardPayment {
  -String cardNumber
  +pay(double amount) boolean
}
class CashOnDeliveryPayment {
  +pay(double amount) boolean
}
class PayPalPayment {
  -String email
  +pay(double amount) boolean
}

%% ===== ADAPTER =====
class ExternalPaymentGateway {
  <<external API>>
  +makeCharge(int cents, String currency) String
}
class GatewayPaymentAdapter {
  -ExternalPaymentGateway gateway
  +pay(double amount) boolean
}

%% ===== OBSERVER =====
class OrderObserver {
  <<interface>>
  +update(Order order) void
}
class EmailNotifier {
  +update(Order order) void
}
class SmsNotifier {
  +update(Order order) void
}
class InventoryObserver {
  +update(Order order) void
}

%% ===== RELATIONSHIPS =====
Client --> OrderFacade
OrderFacade ..> ProductFactory : uses
OrderFacade ..> OrderBuilder : uses
OrderFacade ..> InventoryService : uses
OrderFacade ..> PaymentStrategy : picks
OrderFacade ..> OrderObserver : attaches

ProductFactory ..> Product : creates
Product <|-- ElectronicsProduct
Product <|-- ClothingProduct
Product <|-- BookProduct

OrderBuilder ..> Order : builds
Order "1" *-- "1..*" OrderItem
OrderItem --> Product
Order --> Customer
Order --> Address
Order --> OrderStatus

PaymentStrategy <|.. CardPayment
PaymentStrategy <|.. CashOnDeliveryPayment
PaymentStrategy <|.. PayPalPayment
PaymentStrategy <|.. GatewayPaymentAdapter
Order o-- PaymentStrategy : strategy

GatewayPaymentAdapter --> ExternalPaymentGateway : adapts

OrderObserver <|.. EmailNotifier
OrderObserver <|.. SmsNotifier
OrderObserver <|.. InventoryObserver
Order o-- "0..*" OrderObserver : observers
InventoryObserver ..> InventoryService

note for OrderFacade "FACADE: one simple execution to client"
note for ProductFactory "FACTORY: creates necessary type of Product"
note for OrderBuilder "BUILDER: collect Order step-by-step"
note for PaymentStrategy "STRATEGY: different types of payment"
note for GatewayPaymentAdapter "ADAPTER: calls external API for PaymentStrategy"
note for OrderObserver "OBSERVER: notification during status changes"
```

## How Patterns work together

1. `Client` calls only `OrderFacade` that will call `OrderFacade.placeOrder(...)`, `Product` and `Customer`.
2. Facade uses **Factory**, to create `Product`.
3. Facade uses **Builder**, to collect `Order`.
4. Facade chooses **Strategy** payment type (or **Adapter**, if it's external gateway).
5. Facade connects with **Observer**s (Email, SMS, Inventory).
6. When `Order.setStatus(...)` changes, all Observers get `update()`.
