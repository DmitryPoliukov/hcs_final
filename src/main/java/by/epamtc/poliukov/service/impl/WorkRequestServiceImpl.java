package by.epamtc.poliukov.service.impl;

import by.epamtc.poliukov.dao.DaoFactory;
import by.epamtc.poliukov.dao.UtilDao;
import by.epamtc.poliukov.dao.WorkRequestDao;
import by.epamtc.poliukov.entity.Subquery;

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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;





public class WorkRequestServiceImpl implements WorkRequestService {
    private WorkRequestServiceImpl(){}
    private static final WorkRequestServiceImpl INSTANCE = new WorkRequestServiceImpl();
    public static WorkRequestServiceImpl getInstance() {
        return INSTANCE;
    }

    private final Logger logger = LogManager.getLogger(WorkRequestServiceImpl.class);



    public WorkRequest createWorkRequest (int tenantId, String inputPlannedDate) throws IncorrectDateException {
        if (!Validator.validateDate(inputPlannedDate)) {
            throw new IncorrectDateException("Incorrect planned date ");
        }
        LocalDateTime dateNow = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.M.yyyy HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String fillingDateDB = dateNow.format(formatter); // to DB
        String fillingDate = dateNow.format(formatter2);

        String plannedDate = inputDateParsing(inputPlannedDate);
        LocalDate parsedPlannedDate = LocalDate.parse(plannedDate, formatter2);
        LocalDate parsedFillingDate = LocalDate.parse(fillingDate,formatter2);
        if (parsedFillingDate.isAfter(parsedPlannedDate)) {
           throw new IncorrectDateException("Completion(planned) date cannot be earlier than tomorrow");
        }

        WorkRequest workRequest = new WorkRequest();
        workRequest.setFillingDate(fillingDateDB);
        workRequest.setPlannedDate(plannedDate);
        workRequest.setTenantUserId(tenantId);
        logger.log(Level.INFO,  "Work request created");
        return workRequest;
    }

    public Subquery createSubquery (int workRequestID, int amountOfWorkInHours, String information, String workType) throws IncorrectDateException {
        if (!Validator.validate(workRequestID, amountOfWorkInHours) ||
                !Validator.validate(information, workType)) {
            throw new IncorrectDateException("Incorrect planned date ");
        }
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
            logger.log(Level.INFO, "All new requests count = " + requestCount);
            return requestCount;
        } catch (DaoException e) {
            throw new ServiceException("Failed to count all new requests", e);
        }


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
            logger.log(Level.INFO, "All new requests  for type = " + workTypeName + " count = " + requestCount);
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

    @Override
    public WorkRequest getWorkRequestById(int workRequestId) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorkRequestDao workRequestDao = daoFactory.getWorkRequestDao();
        UtilDao utilDao = daoFactory.getUtilDao();
        WorkRequest workRequest;
        List<Subquery> subqueryList;
        try {
            workRequest = workRequestDao.getWorkRequestById(workRequestId);
            workRequest.setRequestStatus(utilDao.takeRequestStatusNameByStatusId(Integer.parseInt(workRequest.getRequestStatus())));
            subqueryList = workRequestDao.getAllSubqueriesForRequest(workRequestId);
            for(Subquery subquery : subqueryList) {
                subquery.setWorkType(utilDao.takeWorkTypeName(Integer.parseInt(subquery.getWorkType())));
            }
            workRequest.setSubqueryList(subqueryList);
        } catch (DaoException e) {
            throw new ServiceException("Failed to get work request by request id", e);
        }
        return workRequest;
    }

    @Override
    public int takeWorkRequestIdBySubqueryId(int subqueryId) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UtilDao utilDao = daoFactory.getUtilDao();
        int workRequestId;
        try {
            workRequestId = utilDao.takeWorkRequestIdBySubqueryId(subqueryId);
        } catch (DaoException e) {
            throw new ServiceException("Failed to get work request id by subquery id", e);
        }
        return workRequestId;
    }

    @Override
    public Subquery getSubqueryBySubId(int subqueryId) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorkRequestDao workRequestDao = daoFactory.getWorkRequestDao();
        UtilDao utilDao = daoFactory.getUtilDao();
        Subquery subquery;
        try {
            subquery = workRequestDao.getSubqueryBySubId(subqueryId);
            subquery.setWorkType(utilDao.takeWorkTypeName(Integer.parseInt(subquery.getWorkType())));

        } catch (DaoException e) {
            throw new ServiceException("Failed to get subquery by sub id", e);
        }
        return subquery;
    }
}

