package app.data.order;

public enum PaymentMethod {

    CARD("cartão"),
    CASH("dinheiro");

    private String value;

    PaymentMethod(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
