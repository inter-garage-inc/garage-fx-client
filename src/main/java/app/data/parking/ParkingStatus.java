package app.data.parking;

public enum ParkingStatus {
    OCCUPIED("occupied"),
    VACANT("vacant");

    private final String value;

    ParkingStatus(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
