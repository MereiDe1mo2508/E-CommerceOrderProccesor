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
  -Address shippingAddress
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
class Address {
  -String city
  -String street
  -String zipCode
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

note for OrderFacade "FACADE: один простой вход для клиента"
note for ProductFactory "FACTORY: создаёт нужный тип Product"
note for OrderBuilder "BUILDER: собирает Order по шагам"
note for PaymentStrategy "STRATEGY: разные способы оплаты"
note for GatewayPaymentAdapter "ADAPTER: подгоняет чужой API под PaymentStrategy"
note for OrderObserver "OBSERVER: уведомления при смене статуса"
```

## Как паттерны работают вместе

1. `Client` вызывает только `OrderFacade.placeOrder(...)`.
2. Facade использует **Factory**, чтобы создать `Product`.
3. Facade использует **Builder**, чтобы собрать `Order`.
4. Facade выбирает **Strategy** оплаты (или **Adapter**, если это внешний gateway).
5. Facade подключает **Observer**-ов (Email, SMS, Inventory).
6. Когда `Order.setStatus(...)` меняется, все Observer-ы получают `update()`.