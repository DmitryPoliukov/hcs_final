package by.epamtc.poliukov.exception;

public class ServiceAuthorizationException extends Exception {
    public ServiceAuthorizationException() {
        super();
    }

    public ServiceAuthorizationException(String message) {
        super(message);
    }

    public ServiceAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceAuthorizationException(Throwable cause) {
        super(cause);
    }
}
