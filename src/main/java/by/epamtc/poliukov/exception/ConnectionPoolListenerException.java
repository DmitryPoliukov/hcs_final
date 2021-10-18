package by.epamtc.poliukov.exception;

public class ConnectionPoolListenerException extends Exception {
    public ConnectionPoolListenerException() {
        super();
    }

    public ConnectionPoolListenerException(String message) {
        super(message);
    }

    public ConnectionPoolListenerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolListenerException(Throwable cause) {
        super(cause);
    }
}
