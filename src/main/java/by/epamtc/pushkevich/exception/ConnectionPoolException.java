package by.epamtc.pushkevich.exception;

public class ConnectionPoolException extends Exception {
    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(Exception exception) {
        super(exception);
    }

    public ConnectionPoolException(String message, Exception exception) {
        super(message, exception);
    }
}
