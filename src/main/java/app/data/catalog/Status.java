package app.data.catalog;

public enum Status {
    AVAILABLE("disponível"),
    UNAVAILABLE("indisponível");

    private final String value;

    Status(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getValue();
    }

    public String getValue() {
        return value;
    }
}
