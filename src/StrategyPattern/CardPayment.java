package StrategyPattern;

import java.math.BigDecimal;

public class CardPayment implements PaymentStrategy {
    private static final int CARD_LENGTH = 16;
    private static final int VISIBLE_DIGITS = 4;

    private final String cardNumber;

    public CardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean pay(BigDecimal amount) {
        if (cardNumber == null || cardNumber.length() != CARD_LENGTH) {
            System.out.println("[Card] Invalid card number");
            return false;
        }
        System.out.println("[Card] Paid " + amount.toPlainString() + " KZT with card " + mask());
        return true;
    }

    private String mask() {
        return "**** **** **** " + cardNumber.substring(CARD_LENGTH - VISIBLE_DIGITS);
    }
}