package app.data.order;

public enum Status {

    PAID("paid"),
    CANCELED("canceled"),
    OPEN("open");

    private String value;

    Status(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
