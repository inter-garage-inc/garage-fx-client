package app.data.catalog;

public enum CatalogType {

    PARKING("estacionamento"),
    OTHER("outros");

    private final String value;

    CatalogType(final String value) {
        this.value = value;
    }

<<<<<<< HEAD
    @Override
    public String toString() {
        return getValue();
    }

=======
>>>>>>> main
    public String getValue() {
        return value;
    }
}
