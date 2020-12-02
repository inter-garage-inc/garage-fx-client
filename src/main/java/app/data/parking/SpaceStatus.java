package app.data.parking;

public enum SpaceStatus {
    OCCUPIED("Ocupada"),
    VACANT("Livre"),
    DISABLED("Desativada");

    private final String value;

    SpaceStatus(final String value) {
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