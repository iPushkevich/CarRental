package by.epamtc.pushkevich.exception;

public class RepositoryException extends Exception{
    public RepositoryException() {
        super();
    }

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(Exception exception) {
        super(exception);
    }

    public RepositoryException(String message, Exception exception) {
        super(message, exception);
    }
}
