package by.epamtc.poliukov.service.impl;

import by.epamtc.poliukov.dao.DaoFactory;
import by.epamtc.poliukov.dao.UtilDao;
import by.epamtc.poliukov.dao.WorkRequestDao;
import by.epamtc.poliukov.entity.Subquery;
import by.epamtc.poliukov.entity.User;
import by.epamtc.poliukov.entity.WorkRequest;
import by.epamtc.poliukov.exception.DaoException;
import by.epamtc.poliukov.exception.IncorrectDateException;
import by.epamtc.poliukov.exception.ServiceAuthorizationException;
import by.epamtc.poliukov.exception.ServiceException;
import by.epamtc.poliukov.service.Validator;
import by.epamtc.poliukov.service.WorkRequestService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static by.epamtc.poliukov.dao.ColumnName.*;



public class WorkRequestServiceImpl implements WorkRequestService {
    private final Logger logger = LogManager.getLogger(WorkRequestServiceImpl.class);
    private static final String USER = "user";
    private static final String WORK_REQUEST_ID = "workRequestId";
    private static final String AMOUNT = "amount";
    private static final String WORK_TYPE = "workType";


    public WorkRequest createWorkRequest (HttpServletRequest request) throws IncorrectDateException {

        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.M.yyyy HH:mm:ss");
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(USER);
        int tenantId = user.getUserId();
        String fillingDate = formatForDateNow.format(dateNow);
        String inputPlannedDate = request.getParameter("plannedDate");

        if (!Validator.validateDate(inputPlannedDate)) {
           throw new IncorrectDateException("Incorrect planned date ");
        }
        String plannedDate = inputDateParsing(inputPlannedDate);
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.M.yyyy");
        try {
            Date docDate= format.parse(plannedDate);
        } catch (ParseException e) {
            logger.log(Level.ERROR,  "planned date parsing");
        }
        // добавить сравнение дат

        WorkRequest workRequest = new WorkRequest();
        workRequest.setFillingDate(fillingDate);
        workRequest.setPlannedDate(plannedDate);
        workRequest.setTenantUserId(tenantId);
        logger.log(Level.INFO,  "Work request created");
        return workRequest;
    }

    public Subquery createSubquery (HttpServletRequest request) {
        int workRequestID = Integer.parseInt(request.getParameter(WORK_REQUEST_ID));
        int amountOfWorkInHours = Integer.parseInt(request.getParameter(AMOUNT));
        String information = request.getParameter(INFORMATION);
        String workType = request.getParameter(WORK_TYPE);

        Subquery subquery = new Subquery();
        subquery.setMainRequestId(workRequestID);
        subquery.setAmountOfWorkInHours(amountOfWorkInHours);
        subquery.setInformation(information);
        subquery.setWorkType(workType);
        logger.log(Level.INFO,  "Subquery for requstID = " + workRequestID + "created");
        return subquery;
    }



    @Override
    public WorkRequest addWorkRequest(WorkRequest request) throws ServiceException {
        boolean isAdded;
        WorkRequest workRequest;
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorkRequestDao workRequestDao = daoFactory.getWorkRequestDao();
        UtilDao utilDao = daoFactory.getUtilDao();

        try {
            isAdded = workRequestDao.addWorkRequest(request);
            workRequest = utilDao.takeWorkRequestByFillingDateUserId(request.getFillingDate(), request.getTenantUserId());
        } catch (DaoException e) {
            throw new ServiceException("Failed to add request", e);
        }
        logger.log(Level.INFO,
                "Request" + request.getRequestID() + "' was added: " + isAdded);
        return workRequest;
    }

    @Override
    public boolean addSubqueries(Subquery subquery, int requestID) throws ServiceException {
        boolean isAdded;
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
    public List<WorkRequest> getAllRequestForTenantByLogin(String login) throws ServiceException, ServiceAuthorizationException {
        if (!Validator.validateLogin(login)) {
            throw new ServiceAuthorizationException("Check input parameters");
        }
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorkRequestDao workRequestDao = daoFactory.getWorkRequestDao();
        List<WorkRequest> allRequestForOneTenant;
        List<Subquery> allSubqueriesForRequest;
        try {
            allRequestForOneTenant = workRequestDao.getAllRequestForTenantByLogin(login);
            for (WorkRequest request : allRequestForOneTenant) {
                allSubqueriesForRequest = workRequestDao.getAllSubqueriesForRequest(request.getRequestID());
                request.setSubqueryList(allSubqueriesForRequest);
            }
        } catch (DaoException e) {
            throw new ServiceException("Failed to add request", e);
        }
        return allRequestForOneTenant;
    }

    @Override
    public List<WorkRequest> getAllRequestForTenantByLogin(String login, int offset, int noOfRecords) throws ServiceException, ServiceAuthorizationException {
        if (!Validator.validateLogin(login)) {
            throw new ServiceAuthorizationException("Check input parameters");
        }
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorkRequestDao workRequestDao = daoFactory.getWorkRequestDao();
        UtilDao utilDao = daoFactory.getUtilDao();
        List<WorkRequest> allRequestForOneTenant;
        List<Subquery> allSubqueriesForRequest;
        try {
            allRequestForOneTenant = workRequestDao.getAllRequestForTenantByLogin(login, offset, noOfRecords);
            for (WorkRequest request : allRequestForOneTenant) {
                request.setRequestStatus(utilDao.takeRequestStatusNameByStatusId(Integer.parseInt(request.getRequestStatus())));
                allSubqueriesForRequest = workRequestDao.getAllSubqueriesForRequest(request.getRequestID());
                for (Subquery subquery: allSubqueriesForRequest) {
                    subquery.setWorkType(utilDao.takeWorkTypeName(Integer.parseInt(subquery.getWorkType())));
                }
                request.setSubqueryList(allSubqueriesForRequest);
            }
        } catch (DaoException e) {
            throw new ServiceException("Failed to get all requests for tenant by login", e);
        }
        return allRequestForOneTenant;
    }

    @Override
    public List<WorkRequest> getNewRequestsForOneWorkType(String workTypeName, int offset, int noOfRecords) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorkRequestDao workRequestDao = daoFactory.getWorkRequestDao();
        UtilDao utilDao = daoFactory.getUtilDao();
        List<WorkRequest> requestsForOneWorkType;
        Subquery subquery;
        List<Subquery> subqueriesList;
        try {
            int workTypeId = utilDao.takeWorkTypeIdByName(workTypeName);
            requestsForOneWorkType = workRequestDao.getNewRequestsForOneWorkType(workTypeId, offset, noOfRecords);
            for (WorkRequest workRequest : requestsForOneWorkType) {
                workRequest.setRequestStatus(utilDao.takeRequestStatusNameByStatusId(Integer.parseInt(workRequest.getRequestStatus())));
                subquery = workRequestDao.getSubqueryByRequestIdType(workRequest.getRequestID(), workTypeId);
                subquery.setWorkType(utilDao.takeWorkTypeName(Integer.parseInt(subquery.getWorkType())));
                subqueriesList = new ArrayList<>();
                subqueriesList.add(subquery);
                workRequest.setSubqueryList(subqueriesList);
            }
        } catch (DaoException e) {
            throw new ServiceException("Failed to get new requests for work type = " + workTypeName, e);
        }
        logger.log(Level.INFO,
                "Take new requests for work type = " + workTypeName);

        return requestsForOneWorkType;
    }

    @Override
    public List<WorkRequest> getAllNewRequests(int offset, int noOfRecords) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorkRequestDao workRequestDao = daoFactory.getWorkRequestDao();
        UtilDao utilDao = daoFactory.getUtilDao();
        List<WorkRequest> requestsForOneWorkType;
        List<Subquery> subqueriesList;
        try {
            requestsForOneWorkType = workRequestDao.getAllNewRequests(offset, noOfRecords);
            for (WorkRequest workRequest : requestsForOneWorkType) {
                workRequest.setRequestStatus(utilDao.takeRequestStatusNameByStatusId(Integer.parseInt(workRequest.getRequestStatus())));
                subqueriesList = workRequestDao.getAllSubqueriesForRequest(workRequest.getRequestID());
                for (Subquery subquery: subqueriesList) {
                    subquery.setWorkType(utilDao.takeWorkTypeName(Integer.parseInt(subquery.getWorkType())));
                }
                workRequest.setSubqueryList(subqueriesList);
            }
        } catch (DaoException e) {
            throw new ServiceException("Failed to get all new requests", e);
        }
        logger.log(Level.INFO, "Take all new requests");
        return requestsForOneWorkType;
    }

    @Override
    public boolean updateWorkRequestStatus(int workRequestId, String updatedStatus) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorkRequestDao workRequestDao = daoFactory.getWorkRequestDao();
        UtilDao utilDao = daoFactory.getUtilDao();
        boolean isUpdate;
        try {
            Integer statusID = utilDao.takeRequestStatusIdByStatusName(updatedStatus);
            isUpdate = workRequestDao.updateWorkRequestStatus(workRequestId, statusID.toString());
            logger.log(Level.INFO, "update work request id = " + workRequestId + " status");
        } catch (DaoException e) {
            throw new ServiceException("Failed to update work request status", e);
        }

        return isUpdate;
    }

    public int allRequestsByLoginCount(String login) throws ServiceException, ServiceAuthorizationException {
        if (!Validator.validateLogin(login)) {
            throw new ServiceAuthorizationException("Check input parameters");
        }
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorkRequestDao workRequestDao = daoFactory.getWorkRequestDao();
        int requestCount;
        try {
            requestCount = workRequestDao.allRequestsByLoginCount(login);
            logger.log(Level.INFO, "all requests by login = " + login + " count = " + requestCount);
            return requestCount;
        } catch (DaoException e) {
           throw new ServiceException("Failed to count all requests by login", e);
        }
    }

    public int allNewRequestsCount() throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorkRequestDao workRequestDao = daoFactory.getWorkRequestDao();
        int requestCount;
        try {
            requestCount = workRequestDao.allNewRequestsCount();
            logger.log(Level.INFO, "All new requests count = " + allNewRequestsCount());

        } catch (DaoException e) {
            throw new ServiceException("Failed to count all new requests", e);
        }
        return requestCount;

    }

    public int newRequestsByTypeCount(String workTypeName) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorkRequestDao workRequestDao = daoFactory.getWorkRequestDao();
        UtilDao utilDao = daoFactory.getUtilDao();
        int workTypeId;
        int requestCount;
        try {
            workTypeId = utilDao.takeWorkTypeIdByName(workTypeName);
            requestCount = workRequestDao.newRequestsByTypeCount(workTypeId);
            logger.log(Level.INFO, "All new requests  for type = " + workTypeName + " count = " + allNewRequestsCount());
            return requestCount;
        } catch (DaoException e) {
            throw new ServiceException("Failed to count actual requests by work type name", e);
        }
    }

    private String inputDateParsing(String inputDate) {
        String[] arr = inputDate.split("\\D");
        StringBuilder dateSB = new StringBuilder();
        dateSB.append(arr[0]).append(".").append(arr[1]).append(".").append(arr[2]);
        return dateSB.toString();
    }
}

