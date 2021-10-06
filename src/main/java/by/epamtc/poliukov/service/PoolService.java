package by.epamtc.poliukov.service;

import by.epamtc.poliukov.controller.listener.ServiceException;



public interface PoolService {


    void init() throws  ServiceException;


    void destroy() throws ServiceException;
}
