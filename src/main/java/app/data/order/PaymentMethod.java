package app.data.order;

public enum PaymentMethod {

    CARD("card"),
    CASH("cash");

    private String value;
    PaymentMethod(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
