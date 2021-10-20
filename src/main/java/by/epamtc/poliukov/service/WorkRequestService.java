package by.epamtc.poliukov.service;

import by.epamtc.poliukov.entity.Subquery;
import by.epamtc.poliukov.entity.WorkRequest;
import by.epamtc.poliukov.exception.DaoException;
import by.epamtc.poliukov.exception.IncorrectDateException;
import by.epamtc.poliukov.exception.ServiceAuthorizationException;
import by.epamtc.poliukov.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Provider;
import java.util.List;

public interface WorkRequestService {

    WorkRequest createWorkRequest (HttpServletRequest request) throws IOException, IncorrectDateException;

    Subquery createSubquery (HttpServletRequest request);

    WorkRequest addWorkRequest(WorkRequest request) throws ServiceException;

    boolean addSubqueries(Subquery subquery, int requestID) throws ServiceException;

    List<WorkRequest> getAllRequestForTenantByLogin(String login) throws ServiceException, ServiceAuthorizationException;

    List<WorkRequest> getAllRequestForTenantByLogin(String login, int offset, int noOfRecords) throws ServiceException, ServiceAuthorizationException;

    List<WorkRequest> getNewRequestsForOneWorkType(String workTypeName, int offset, int noOfRecords) throws ServiceException;

    List<WorkRequest> getAllNewRequests(int offset, int noOfRecords) throws ServiceException;

    boolean updateWorkRequestStatus(int workRequestId, String updatedStatus) throws ServiceException;

    int allRequestsByLoginCount(String login) throws ServiceException, ServiceAuthorizationException;

    int newRequestsByTypeCount(String workTypeName) throws ServiceException;

    int allNewRequestsCount() throws ServiceException;



}
