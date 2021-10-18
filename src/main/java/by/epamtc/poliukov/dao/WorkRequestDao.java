package by.epamtc.poliukov.dao;

import by.epamtc.poliukov.entity.Subquery;
import by.epamtc.poliukov.entity.WorkRequest;
import by.epamtc.poliukov.exception.DaoException;

import java.util.List;

public interface WorkRequestDao {

    boolean addWorkRequest(WorkRequest request) throws DaoException;

    boolean addSubqueries(Subquery subquery, int requestID) throws DaoException;

    List<Subquery> getAllSubqueriesForRequest(int requestId) throws DaoException;

    List<WorkRequest> getAllRequestForTenantByLogin(String login) throws DaoException;

    List<WorkRequest> getAllRequestForTenantByLogin(String login, int offset, int noOfRecords) throws DaoException;

    List<WorkRequest> getNewRequestsForOneWorkType(int workTypeId, int offset, int noOfRecords) throws DaoException;

    List<WorkRequest> getAllNewRequests(int offset, int noOfRecords) throws DaoException;

    boolean updateWorkRequestStatus(int workRequestId, String updatedStatus) throws DaoException;

    int allRequestsByLoginCount(String login) throws DaoException;

    int newRequestsByTypeCount(int workTypeId) throws DaoException;

    int allNewRequestsCount() throws DaoException;

    Subquery getSubqueryByRequestIdType(int workRequestId, int workTypeId) throws DaoException;
}
