package by.epamtc.poliukov.service;

import by.epamtc.poliukov.service.impl.PoolServiceImpl;
import by.epamtc.poliukov.service.impl.UserServiceImpl;
import by.epamtc.poliukov.service.impl.WorkRequestServiceImpl;


public class ServiceFactory {
    private static final ServiceFactory INSTANCE = new ServiceFactory();

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    private UserService userService = new UserServiceImpl();
    private PoolService poolService = new PoolServiceImpl();
    private WorkRequestService workRequestService = new WorkRequestServiceImpl();

    public UserService getUserService() {
        return userService;
    }
    public PoolService getPoolService() {
        return poolService;
    }
    public WorkRequestService getWorkRequestService() {return workRequestService;}


}
