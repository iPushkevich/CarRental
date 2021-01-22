package by.epamtc.pushkevich.exception;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
        super();
    }

    public WrongPasswordException(String message) {
        super(message);
    }

    public WrongPasswordException(Exception exception) {
        super(exception);
    }

    public WrongPasswordException(String message, Exception exception) {
        super(message, exception);
    }
}
