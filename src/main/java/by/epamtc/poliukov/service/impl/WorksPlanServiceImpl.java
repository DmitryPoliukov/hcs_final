package by.epamtc.poliukov.service.impl;

import by.epamtc.poliukov.dao.DaoFactory;
import by.epamtc.poliukov.dao.UtilDao;
import by.epamtc.poliukov.dao.WorkRequestDao;
import by.epamtc.poliukov.dao.WorksPlanDao;
import by.epamtc.poliukov.exception.DaoException;
import by.epamtc.poliukov.exception.ServiceException;
import by.epamtc.poliukov.service.WorksPlanService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class WorksPlanServiceImpl implements WorksPlanService {
    private WorksPlanServiceImpl(){}
    private static final WorksPlanServiceImpl INSTANCE = new WorksPlanServiceImpl();
    public static WorksPlanServiceImpl getInstance() {
        return INSTANCE;
    }

    private final Logger logger = LogManager.getLogger(WorksPlanServiceImpl.class);
    @Override
    public boolean addWorkRequestToPlan(int workRequestId, int employeeId) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorksPlanDao worksPlanDao= daoFactory.getWorksPlanDao();
        WorkRequestDao workRequestDao = daoFactory.getWorkRequestDao();
        boolean isUpdate;
        try {
            workRequestDao.updateWorkRequestStatus(workRequestId, "2"); //workStatusId = 2  --  "In process"
           isUpdate = worksPlanDao.addWorkRequestToPlan(workRequestId, employeeId);
            logger.log(Level.INFO, "update work request id = " + workRequestId + " status");
        } catch (DaoException e) {
            throw new ServiceException("Failed to add work request to plan", e);
        }
        return isUpdate;
    }

    @Override
    public List<Integer> getEmployeeIdByRequestId(int workRequestId) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorksPlanDao worksPlanDao= daoFactory.getWorksPlanDao();
        List<Integer> employeesForRequest;

        try {
            employeesForRequest = worksPlanDao.getEmployeeIdByRequestId(workRequestId);
            logger.log(Level.INFO, "Get employees id for work request id = " + workRequestId);
        } catch (DaoException e) {
            throw new ServiceException("Failed to get employees ID by request ID", e);
        }
        return employeesForRequest;
    }

    @Override
    public List<Integer> getRequestsIdByEmployeeIdCompletionDate(int employeeId, String completionDate) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorksPlanDao worksPlanDao= daoFactory.getWorksPlanDao();
        List<Integer> requestsIdByEmployeeIdDate;
        try {
            requestsIdByEmployeeIdDate = worksPlanDao.getRequestsIdByEmployeeIdCompletionDate(employeeId, completionDate);
            logger.log(Level.INFO, "Get request id by employee id and completion date");
        } catch (DaoException e) {
            throw new ServiceException("Failed to get request id by employee id and completion date", e);
        }
        return requestsIdByEmployeeIdDate;
    }


}
