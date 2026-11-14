package StrategyPattern;

import java.math.BigDecimal;

public class CashOnDeliveryPayment implements PaymentStrategy {

    @Override
    public boolean pay(BigDecimal amount) {
        System.out.println("[Cash] Customer will pay " + amount.toPlainString() + " KZT to the courier");
        return true;
    }
}