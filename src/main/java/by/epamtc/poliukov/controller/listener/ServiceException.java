package by.epamtc.poliukov.controller.listener;

import by.epamtc.poliukov.exception.ConnectionPoolException;

public class ServiceException extends Throwable {
    public ServiceException(String cannot_init_the_pool, ConnectionPoolException e) {
    }
}
