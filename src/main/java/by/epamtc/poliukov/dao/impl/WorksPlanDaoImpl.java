package by.epamtc.poliukov.dao.impl;

import by.epamtc.poliukov.dao.ColumnName;
import by.epamtc.poliukov.dao.WorksPlanDao;
import by.epamtc.poliukov.dao.pool.ConnectionPool;
import by.epamtc.poliukov.exception.ConnectionPoolException;
import by.epamtc.poliukov.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorksPlanDaoImpl implements WorksPlanDao {
    private static final WorksPlanDao INSTANCE = new WorksPlanDaoImpl();
    private WorksPlanDaoImpl() {
    }

    public static WorksPlanDao getInstance() {
        return INSTANCE;
    }

    private static final String SQL_ADD_WORK_REQUEST_TO_PLAN = "INSERT INTO works_plan " +
        "(employee_user_id_fk, subquery_id) VALUES (?, ?)";

    private static final String SQL_GET_SUBQUERY_ID_BY_EMPLOYEE_ID_COMPLETION_DATE = "SELECT subquery_id FROM works_plan " +
            "JOIN subqueries ON sub_id = subquery_id " +
            "JOIN work_requests ON sub_work_request_id_fk = request_id " +
            "WHERE employee_user_id_fk = ? AND planned_date = ?";


    @Override
    public boolean addWorkRequestToPlan(int subqueryId, int employeeId) throws DaoException {
        boolean isAdded = true;
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_ADD_WORK_REQUEST_TO_PLAN);
            st.setInt(1, employeeId);
            st.setInt(2, subqueryId);
            st.execute();
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in addWorkRequestToPlan ", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception ", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
        return isAdded;
    }


    @Override
    public List<Integer> getSubqueryIdByEmployeeIdCompletionDate(int employeeId, String completionDate) throws DaoException {
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs;
        List<Integer> subqueriesIdList = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_GET_SUBQUERY_ID_BY_EMPLOYEE_ID_COMPLETION_DATE);
            st.setInt(1, employeeId);
            st.setString(2, completionDate);
            rs = st.executeQuery();
            while (rs.next()) {
                int subId = rs.getInt(ColumnName.SUBQUERY_ID);
                subqueriesIdList.add(subId);
            }
            return subqueriesIdList;
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception", e);
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in getEmployeeIdFromPlanByRequestId", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public boolean isFreeEmployeeOnDate(int employeeId, String completionDate) throws DaoException {
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_GET_SUBQUERY_ID_BY_EMPLOYEE_ID_COMPLETION_DATE);
            st.setInt(1, employeeId);
            st.setString(2, completionDate);
            rs = st.executeQuery();
            return !rs.next();
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception", e);
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in getEmployeeIdFromPlanByRequestId", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }
}
