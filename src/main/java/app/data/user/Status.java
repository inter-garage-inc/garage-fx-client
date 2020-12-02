package app.data.user;

public enum Status {
    ACTIVE("ativo"),
    DISABLE("desativado");

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
