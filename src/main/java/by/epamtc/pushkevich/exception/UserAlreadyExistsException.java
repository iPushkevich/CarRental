package by.epamtc.pushkevich.exception;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(Exception exception) {
        super(exception);
    }

    public UserAlreadyExistsException(String message, Exception exception) {
        super(message, exception);
    }
}
