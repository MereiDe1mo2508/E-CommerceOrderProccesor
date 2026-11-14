package AdapterPattern;

import StrategyPattern.PaymentStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GatewayPaymentAdapter extends ExternalPaymentGateway implements PaymentStrategy {
    private final String currency;
    public GatewayPaymentAdapter(String currency) {
        this.currency = currency;
    }
    @Override
    public boolean pay(BigDecimal amount) {
        int cents = amount.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP).intValueExact();
        String response = makeCharge(cents, currency);
        System.out.println(response);
        return true;
    }
}
