package by.epamtc.poliukov.service.impl;

import by.epamtc.poliukov.dao.DaoFactory;
import by.epamtc.poliukov.dao.pool.ConnectionPool;
import by.epamtc.poliukov.exception.ConnectionPoolException;
import by.epamtc.poliukov.exception.ServiceException;
import by.epamtc.poliukov.service.PoolService;


public class PoolServiceImpl implements PoolService {
    private PoolServiceImpl(){}
    private static final PoolServiceImpl INSTANCE = new PoolServiceImpl();
    public static PoolServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void init() throws ServiceException {
        try {
            DaoFactory daoFactory = DaoFactory.getInstance();
            ConnectionPool pool = daoFactory.getConnectionPool();
            pool.init();
        } catch (ConnectionPoolException e) {
            throw new ServiceException("Cannot init the pool", e);
        }
    }

    @Override
    public void destroy() throws ServiceException {
        try {
            DaoFactory daoFactory = DaoFactory.getInstance();
            ConnectionPool pool = daoFactory.getConnectionPool();
            pool.destroy();
        } catch (ConnectionPoolException e) {
            throw new ServiceException("Cannot destroy the pool", e);
        }
    }
}
