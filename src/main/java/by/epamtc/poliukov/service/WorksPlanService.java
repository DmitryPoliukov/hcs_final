package by.epamtc.poliukov.service;

import by.epamtc.poliukov.exception.IncorrectDateException;
import by.epamtc.poliukov.exception.ServiceException;

import java.util.List;

public interface WorksPlanService {

    boolean addWorkRequestToPlan(int workRequestId, int subqueryId, int employeeId) throws ServiceException;

    List<Integer> getRequestsIdByEmployeeIdCompletionDate(int employeeId, String completionDate) throws ServiceException, IncorrectDateException;

    List<Integer> getEmployeeIdByRequestId(int workRequesId) throws ServiceException;
}
