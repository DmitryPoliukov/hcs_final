package by.epamtc.poliukov.service;

import by.epamtc.poliukov.entity.Subquery;
import by.epamtc.poliukov.entity.WorkRequest;
import by.epamtc.poliukov.exception.DaoException;
import by.epamtc.poliukov.exception.ServiceException;

import java.util.List;

public interface WorkRequestService {

    boolean addWorkRequest(WorkRequest request) throws ServiceException;

    boolean addSubqueries(Subquery subquery, int requestID) throws ServiceException;

    List<WorkRequest> getAllRequestForTenantByLogin(String login) throws ServiceException;

    List<WorkRequest> getNewRequestsForOneWorkType(int workTypeId) throws ServiceException;

    boolean updateWorkRequestStatus(int workRequestId, String updatedStatus) throws ServiceException;
}
