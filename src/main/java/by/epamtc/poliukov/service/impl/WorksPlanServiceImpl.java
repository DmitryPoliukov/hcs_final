package by.epamtc.poliukov.service.impl;

import by.epamtc.poliukov.dao.DaoFactory;
import by.epamtc.poliukov.dao.WorkRequestDao;
import by.epamtc.poliukov.dao.WorksPlanDao;
import by.epamtc.poliukov.exception.DaoException;
import by.epamtc.poliukov.exception.IncorrectDateException;
import by.epamtc.poliukov.exception.ServiceException;
import by.epamtc.poliukov.service.Validator;
import by.epamtc.poliukov.service.WorksPlanService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class WorksPlanServiceImpl implements WorksPlanService {
    private WorksPlanServiceImpl(){}
    private static final WorksPlanServiceImpl INSTANCE = new WorksPlanServiceImpl();
    public static WorksPlanServiceImpl getInstance() {
        return INSTANCE;
    }

    private final Logger logger = LogManager.getLogger(WorksPlanServiceImpl.class);
    @Override
    public boolean addWorkRequestToPlan(int workRequestId, int subqueryId, int employeeId) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorksPlanDao worksPlanDao= daoFactory.getWorksPlanDao();
        WorkRequestDao workRequestDao = daoFactory.getWorkRequestDao();
        boolean isAdd;
        try {
            workRequestDao.updateWorkRequestStatus(workRequestId, "2"); //workStatusId = 2  --  "In process"
           isAdd = worksPlanDao.addWorkRequestToPlan(subqueryId, employeeId);
            logger.log(Level.INFO, "update work request id = " + workRequestId + " status");
        } catch (DaoException e) {
            throw new ServiceException("Failed to add work request to plan", e);
        }
        return isAdd;
    }

    @Override
    public List<Integer> getRequestsIdByEmployeeIdCompletionDate(int employeeId, String completionDate) throws ServiceException, IncorrectDateException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorksPlanDao worksPlanDao= daoFactory.getWorksPlanDao();
        List<Integer> subqueriesIdByEmployeeIdDate;

        if (!Validator.validateDate(completionDate)) {
            throw new IncorrectDateException("Incorrect planned date ");
        }
        completionDate = inputDateParsing(completionDate);

        try {
            subqueriesIdByEmployeeIdDate = worksPlanDao.getSubqueryIdByEmployeeIdCompletionDate(employeeId, completionDate);
            logger.log(Level.INFO, "Get request id by employee id and completion date");
        } catch (DaoException e) {
            throw new ServiceException("Failed to get request id by employee id and completion date", e);
        }
        return subqueriesIdByEmployeeIdDate;
    }
    private String inputDateParsing(String inputDate) {
        String[] arr = inputDate.split("\\D");
        StringBuilder dateSB = new StringBuilder();
        dateSB.append(arr[0]).append(".").append(arr[1]).append(".").append(arr[2]);
        return dateSB.toString();
    }

    @Override
    public boolean isFreeEmployeeOnDate(int employeeId, String completionDate) throws ServiceException, IncorrectDateException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorksPlanDao worksPlanDao= daoFactory.getWorksPlanDao();
        List<Integer> subqueriesIdByEmployeeIdDate;
        boolean isFreeEmployeeOnDate;
        if (!Validator.validateDate(completionDate)) {
            throw new IncorrectDateException("Incorrect planned date ");
        }
        completionDate = inputDateParsing(completionDate);

        try {
            isFreeEmployeeOnDate = worksPlanDao.isFreeEmployeeOnDate(employeeId, completionDate);
            logger.log(Level.INFO, "Get information about is free employee on date");
        } catch (DaoException e) {
            throw new ServiceException("Failed to get information about is free employee on date", e);
        }
        return isFreeEmployeeOnDate;
    }
}
