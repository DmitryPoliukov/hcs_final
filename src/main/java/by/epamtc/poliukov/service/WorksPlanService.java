package by.epamtc.poliukov.service;

import by.epamtc.poliukov.exception.DaoException;
import by.epamtc.poliukov.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface WorksPlanService {

    boolean addWorkRequestToPlan(int workRequestId, int subqueryId, int employeeId) throws ServiceException;

    List<Integer> getEmployeeIdByRequestId(int workRequesId) throws ServiceException;

    List<Integer> getRequestsIdByEmployeeIdCompletionDate(int employeeId, String completionDate) throws ServiceException;
}
