package app.data.plan;

public enum Status {
    ACTIVE("ativo"),
    DISABLE("inativo");

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
