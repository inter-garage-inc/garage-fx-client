package app.data.user;

public enum Status {
    ACTIVE("active"),
    DISABLE("disable");

    private String value;

    Status(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
