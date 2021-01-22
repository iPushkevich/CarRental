package by.epamtc.pushkevich.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Exception exception) {
        super(exception);
    }

    public UserNotFoundException(String message, Exception exception) {
        super(message, exception);
    }
}
