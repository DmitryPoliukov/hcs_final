package by.epamtc.poliukov.dao;

import by.epamtc.poliukov.dao.impl.UserDaoImpl;
import by.epamtc.poliukov.dao.impl.UtilDaoImpl;
import by.epamtc.poliukov.dao.impl.WorkRequestDaoImpl;
import by.epamtc.poliukov.dao.pool.ConnectionPool;

public class DaoFactory {

    private static final DaoFactory INSTANCE = new DaoFactory();
    private UserDao userDao = UserDaoImpl.getInstance();
    private UtilDao utilDao = UtilDaoImpl.getInstance();
    private WorkRequestDao workRequestDao = WorkRequestDaoImpl.getInstance();

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public UtilDao getUtilDao() {
        return utilDao;
    }

    public WorkRequestDao getWorkRequestDao() {
        return workRequestDao;
    }

    public ConnectionPool getConnectionPool() {
        return ConnectionPool.getInstance();
    }


}
