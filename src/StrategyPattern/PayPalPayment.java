package StrategyPattern;

import java.math.BigDecimal;

public class PayPalPayment implements PaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }
    public PayPalPayment() {
    }
    @Override
    public boolean pay(BigDecimal amount) {
        if (email == null || !email.contains("@")) {
            System.out.println("[PayPal] Invalid email");
            return false;
        }
        System.out.println("[PayPal] Paid " + amount.toPlainString() + " KZT from account " + email);
        return true;
    }
}