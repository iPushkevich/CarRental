package by.epamtc.pushkevich.exception;

public class IncorrectLoginOrPasswordException extends Exception {
    public IncorrectLoginOrPasswordException() {
        super();
    }

    public IncorrectLoginOrPasswordException(String message) {
        super(message);
    }

    public IncorrectLoginOrPasswordException(Exception exception) {
        super(exception);
    }

    public IncorrectLoginOrPasswordException(String message, Exception exception) {
        super(message, exception);
    }
}
