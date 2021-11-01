package by.epamtc.poliukov.service;

import by.epamtc.poliukov.exception.IncorrectDateException;
import by.epamtc.poliukov.exception.ServiceException;

import java.util.List;

public interface WorksPlanService {

    boolean addWorkRequestToPlan(int workRequestId, int subqueryId, int employeeId) throws ServiceException;

    List<Integer> getSubqueryIdByEmployeeIdCompletionDate(int employeeId, String completionDate) throws ServiceException, IncorrectDateException;

    boolean isFreeEmployeeOnDate(int employeeId, String completionDate) throws ServiceException, IncorrectDateException;
}
