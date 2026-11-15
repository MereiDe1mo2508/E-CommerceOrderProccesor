package AdapterPattern;

public class ExternalPaymentGateway {

    public String makeCharge(int cents, String currency) {
        return "Successfully charged " + cents + " cents in " + currency;
    }
}
