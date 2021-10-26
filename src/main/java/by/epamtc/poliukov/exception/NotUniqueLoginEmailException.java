package by.epamtc.poliukov.exception;

public class NotUniqueLoginEmailException extends Exception {
    public NotUniqueLoginEmailException() {
        super();
    }

    public NotUniqueLoginEmailException(String message) {
        super(message);
    }

    public NotUniqueLoginEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotUniqueLoginEmailException(Throwable cause) {
        super(cause);
    }
}
