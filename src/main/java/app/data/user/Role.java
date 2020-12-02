package app.data.user;

public enum Role {
    ADMIN("admin"),
    MANAGER("gerente"),
    EMPLOYEE("funcionário");

    private String value;

    Role(final String value) {
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
