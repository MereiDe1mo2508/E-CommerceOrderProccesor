package AdapterPattern;

import StrategyPattern.PaymentStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GatewayPaymentAdapter implements PaymentStrategy {
    private static final BigDecimal CENTS_IN_UNIT = BigDecimal.valueOf(100);
    private static final String SUCCESS_PREFIX = "Successfully";

    private final ExternalPaymentGateway gateway;
    private final String currency;

    public GatewayPaymentAdapter(ExternalPaymentGateway gateway, String currency) {
        this.gateway = gateway;
        this.currency = currency;
    }

    @Override
    public boolean pay(BigDecimal amount) {
        int cents = amount.multiply(CENTS_IN_UNIT)
                .setScale(0, RoundingMode.HALF_UP)
                .intValueExact();
        String response = gateway.makeCharge(cents, currency);
        System.out.println("[Gateway] " + response);
        return response.startsWith(SUCCESS_PREFIX);
    }
}
