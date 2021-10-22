package by.epamtc.poliukov.service;

import by.epamtc.poliukov.service.impl.PoolServiceImpl;
import by.epamtc.poliukov.service.impl.UserServiceImpl;
import by.epamtc.poliukov.service.impl.WorkRequestServiceImpl;
import by.epamtc.poliukov.service.impl.WorksPlanServiceImpl;


public class ServiceFactory {
    private static final ServiceFactory INSTANCE = new ServiceFactory();

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    private UserService userService = UserServiceImpl.getInstance();
    private PoolService poolService = PoolServiceImpl.getInstance();
    private WorkRequestService workRequestService = WorkRequestServiceImpl.getInstance();
    private WorksPlanService worksPlanService = WorksPlanServiceImpl.getInstance();

    public UserService getUserService() {
        return userService;
    }
    public PoolService getPoolService() {
        return poolService;
    }
    public WorkRequestService getWorkRequestService() {return workRequestService;}
    public WorksPlanService getWorksPlanService() {
        return worksPlanService;
    }


}
