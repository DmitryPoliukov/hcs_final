package by.epamtc.poliukov.dao;

import by.epamtc.poliukov.exception.DaoException;

import java.util.List;
import java.util.Map;

public interface WorksPlanDao {

    boolean addWorkRequestToPlan(int subqueryId, int employeeId) throws DaoException;

  // String getCompletionDateByRequestId(int workRequesId) throws DaoException;

    List<Integer> getEmployeeIdByRequestId(int workRequesId) throws DaoException;

    List<Integer> getSubqueryIdByEmployeeIdCompletionDate(int employeeId, String completionDate) throws DaoException;



}
