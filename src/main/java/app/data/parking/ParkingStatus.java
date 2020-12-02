package app.data.parking;

public enum ParkingStatus {
    OCCUPIED("ocupado"),
    VACANT("vazio");

    private final String value;

    ParkingStatus(final String value) {
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
