package app.data.catalog;

public enum CatalogType {

    PARKING("estacionamento"),
    OTHER("outros");

    private final String value;

    CatalogType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
