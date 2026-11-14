package AdapterPattern;

public class ExternalPaymentGateway {
    private int cents;
    private String currency;
    public ExternalPaymentGateway(int cents, String currency) {
        this.cents = cents;
        this.currency = currency;
    }
    public ExternalPaymentGateway() {}
    public int getCents() {
        return cents;
    }
    public void setCents(int cents) {
        this.cents = cents;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String makeCharge(int cents, String currency) {
        return "Successfully charged " + cents + " cents in " + currency;
    }
}
