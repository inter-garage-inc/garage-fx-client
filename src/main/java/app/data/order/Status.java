package app.data.order;

public enum Status {

    PAID("pago"),
    CANCELED("cancelado"),
    OPEN("aberto");

    private String value;

    Status(final String value) {
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
