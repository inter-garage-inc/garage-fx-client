package app.data.address;

public enum Country {
    BRAZIL("Brazil"),
    OTHER("other");

    private final String value;

    Country(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}