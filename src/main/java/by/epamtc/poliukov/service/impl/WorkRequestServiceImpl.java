package by.epamtc.poliukov.service.impl;

import by.epamtc.poliukov.dao.DaoFactory;
import by.epamtc.poliukov.dao.UserDao;
import by.epamtc.poliukov.dao.UtilDao;
import by.epamtc.poliukov.dao.WorkRequestDao;
import by.epamtc.poliukov.entity.Subquery;
import by.epamtc.poliukov.entity.WorkRequest;
import by.epamtc.poliukov.exception.DaoException;
import by.epamtc.poliukov.exception.ServiceAuthorizationException;
import by.epamtc.poliukov.exception.ServiceException;
import by.epamtc.poliukov.service.Encryption;
import by.epamtc.poliukov.service.Validator;
import by.epamtc.poliukov.service.WorkRequestService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class WorkRequestServiceImpl implements WorkRequestService {
    private final Logger logger = LogManager.getLogger(WorkRequestServiceImpl.class);

    @Override
    public boolean addWorkRequest(WorkRequest request) throws ServiceException {
        boolean isAdded = false;
        // валидация реквеста
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorkRequestDao workRequestDao = daoFactory.getWorkRequestDao();
        UtilDao utilDao = daoFactory.getUtilDao();

        try {
            isAdded = workRequestDao.addWorkRequest(request);
        } catch (DaoException e) {
            throw new ServiceException("Failed to add request", e);
        }
        logger.log(Level.INFO,
                "Request" + request.getRequestID() + "' was added: " + isAdded);

        return isAdded;
    }

    @Override
    public boolean addSubqueries(Subquery subquery, int requestID) throws ServiceException {
        boolean isAdded = false;
        // валидация подзапроса
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorkRequestDao workRequestDao = daoFactory.getWorkRequestDao();
        UtilDao utilDao = daoFactory.getUtilDao();

        try {
            subquery.setWorkType(utilDao.takeWorkTypeIdByName(subquery.getWorkType()).toString());
            isAdded = workRequestDao.addSubqueries(subquery, requestID);
        } catch (DaoException e) {
            throw new ServiceException("Failed to add request", e);
        }
        logger.log(Level.INFO,
                "Subquery" + subquery.getSubId() + "' was added: " + isAdded);

        return isAdded;
    }

    @Override
    public List<WorkRequest> getAllRequestForTenantByLogin(String login) throws ServiceException {
        // валидация логина
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorkRequestDao workRequestDao = daoFactory.getWorkRequestDao();
        UtilDao utilDao = daoFactory.getUtilDao();

        List<WorkRequest> allRequestForOneTenant = null;
        List<Subquery> allSubqueriesForRequest;
        try {
            allRequestForOneTenant = workRequestDao.getAllRequestForTenantByLogin(login);
            for (WorkRequest request : allRequestForOneTenant) {
                allSubqueriesForRequest = workRequestDao.getAllSubqueriesForRequest(request.getRequestID());
                request.setSubqueryList(allSubqueriesForRequest);
            }

        } catch (DaoException e) {
            e.printStackTrace();
        }
        return allRequestForOneTenant;
    }

    @Override
    public List<WorkRequest> getNewRequestsForOneWorkType(int workTypeId) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorkRequestDao workRequestDao = daoFactory.getWorkRequestDao();
        UtilDao utilDao = daoFactory.getUtilDao();
        List<WorkRequest> requestsForOneWorkType;
        List<Subquery> allSubqueriesForRequest;
        try {
            requestsForOneWorkType = workRequestDao.getNewRequestsForOneWorkType(workTypeId);
            for (WorkRequest workRequest : requestsForOneWorkType) {
                allSubqueriesForRequest = workRequestDao.getAllSubqueriesForRequest(workRequest.getRequestID());
                workRequest.setSubqueryList(allSubqueriesForRequest);
            }
        } catch (DaoException e) {
            throw new ServiceException("Failed to add request", e);
        }
        logger.log(Level.INFO,
                "Take new requests for work type id = " + workTypeId);

        return requestsForOneWorkType;
    }

    @Override
    public boolean updateWorkRequestStatus(int workRequestId, String updatedStatus) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorkRequestDao workRequestDao = daoFactory.getWorkRequestDao();
        UtilDao utilDao = daoFactory.getUtilDao();
        boolean isUpdate = false;
        try {
            Integer statusID = utilDao.takeRequestStatusIdByStatusName(updatedStatus);
            isUpdate = workRequestDao.updateWorkRequestStatus(workRequestId, statusID.toString());
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return isUpdate;
    }
}

