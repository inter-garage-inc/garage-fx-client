package app.client;

public class ConnectionFailureException extends Exception {
    public ConnectionFailureException() {
    }

    public ConnectionFailureException(Exception exception) {
        super(exception);
    }
}