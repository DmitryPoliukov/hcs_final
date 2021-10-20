package by.epamtc.poliukov.exception;

public class IncorrectDateException extends Exception {
    public IncorrectDateException() {
        super();
    }

    public IncorrectDateException(String message) {
        super(message);
    }

    public IncorrectDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectDateException(Throwable cause) {
        super(cause);
    }
}
