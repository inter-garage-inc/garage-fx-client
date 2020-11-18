package app.data.address;

import app.data.address.state.Brazil;

public enum Country {
    BRAZIL("Brasil", Brazil.values()),
    OTHER("Outro");

    private final String value;
    private final State[] states;

    Country(final String value) {
        this.value = value;
        this.states = null;
    }

    Country(final String value, State[] states) {
        this.value = value;
        this.states = states;
    }

    public String getValue() {
        return value;
    }

    public State[] getStates() {
        return states;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}