package app.data.plan;

public enum Type {
    MONTHLY("mensal"),
    BIWEEKLY("quinzenal");

    private final String value;

    Type(final String value) {
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