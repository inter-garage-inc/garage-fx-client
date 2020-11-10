package app.data.user;

public enum Role {
    ADMIN("admin"),
    MANAGER("manager"),
    EMPLOYEE("employee");

    private String value;

    Role(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
