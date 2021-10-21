package by.epamtc.poliukov.dao.impl;

import by.epamtc.poliukov.dao.ColumnName;
import by.epamtc.poliukov.dao.WorkRequestDao;
import by.epamtc.poliukov.dao.pool.ConnectionPool;
import by.epamtc.poliukov.entity.Subquery;
import by.epamtc.poliukov.entity.WorkRequest;
import by.epamtc.poliukov.exception.ConnectionPoolException;
import by.epamtc.poliukov.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkRequestDaoImpl implements WorkRequestDao {

    private static final WorkRequestDao INSTANCE = new WorkRequestDaoImpl();
    private WorkRequestDaoImpl() {
    }

    public static WorkRequestDao getInstance() {
        return INSTANCE;
    }

    private final static String SQL_ADD_WORK_REQUEST = "INSERT INTO work_requests (filling_date," +
            "planned_date, tenant_user_id_fk) VALUES(?, ?, ?)";

    private final static String SQL_ADD_SUBQUERY = "INSERT INTO subqueries (amount_of_work_in_hours, " +
            "information, sub_work_request_id_fk, sub_work_type_id) VALUES(?, ?, ?, ?)";

    private final static String SQL_GET_ALL_REQUESTS_FOR_TENANT = "SELECT * FROM work_requests wr " +
            "JOIN users on users.user_id = wr.tenant_user_id_fk WHERE users.login = ?";

    private final static String SQL_VIEW_ALL_WORK_REQUEST_TENANT_PAGINATION =
            "SELECT * FROM work_requests JOIN users on users.user_id = tenant_user_id_fk " +
                    "WHERE login = ? ORDER BY filling_date DESC LIMIT ?, ?";

    private final static String SQL_GET_All_SUBQUERIES_FOR_REQUEST = "SELECT * from subqueries " +
            "WHERE sub_work_request_id_fk = ?";

    private final static String SQL_GET_NEW_REQUESTS_FOR_ONE_WORK_TYPE_PAGE = "SELECT * from work_requests " +
            "JOIN subqueries on sub_work_request_id_fk = request_id " +
            "WHERE request_status_id_fk = 1 AND subqueries.sub_work_type_id = ? ORDER BY filling_date DESC LIMIT ?, ?";

    private final static String SQL_GET_NEW_REQUESTS_PAGE = "SELECT * from work_requests " +
            "WHERE request_status_id_fk = 1  ORDER BY filling_date DESC LIMIT ?, ?";

    private final static String SQL_UPDATE_WORK_REQUEST_STATUS =
            "UPDATE work_requests SET request_status_id_fk = ? WHERE request_id = ?";

    private final static String SQL_UPDATE_WORK_REQUEST_PLANNED_DATE =
            "UPDATE work_requests SET planned_date = ? WHERE request_id = ?";

    private static final String SQL_GET_COUNT_ALL_REQUESTS_BY_LOGIN = "SELECT COUNT(request_id) AS amount FROM work_requests " +
            "JOIN users ON work_requests.tenant_user_id_fk = users.user_id WHERE users.login = ?";

    private static final String SQL_GET_ACTUAL_REQUESTS_TYPE_COUNT = "SELECT COUNT(request_id) AS amount FROM work_requests " +
            "JOIN subqueries ON request_id = sub_work_request_id_fk WHERE sub_work_type_id = ? AND request_status_id_fk = 1";

    private final static String SQL_GET_SUBQUERIY_FOR_REQUEST_BY_TYPE = "SELECT * from subqueries " +
            "WHERE sub_work_request_id_fk = ? AND sub_work_type_id = ?";

    private final static String SQL_GET_ALL_ACTUAL_REQUESTS_COUNT = "SELECT COUNT(request_id) AS amount FROM work_requests " +
            "WHERE request_status_id_fk = 1";

    private final static String AMOUNT = "amount";

    @Override
    public boolean addWorkRequest(WorkRequest request) throws DaoException {

        boolean isAdded = true;
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_ADD_WORK_REQUEST);
            st.setString(1, request.getFillingDate());
            st.setString(2, request.getPlannedDate());
            st.setInt(3, request.getTenantUserId());
            st.execute();
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in addWorkRequest ", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception ", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
        return isAdded;
    }

    @Override
    public boolean addSubqueries(Subquery subquery, int requestID) throws DaoException {

        boolean isAdded = true;
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_ADD_SUBQUERY);
            st.setInt(1, subquery.getAmountOfWorkInHours());
            st.setString(2, subquery.getInformation());
            st.setInt(3, requestID);
            st.setString(4, subquery.getWorkType());
            st.execute();
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in addSubqueries", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception ", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
        return isAdded;
    }

    @Override
    public List<WorkRequest> getAllRequestForTenantByLogin(String login, int offset, int noOfRecords) throws DaoException {
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_VIEW_ALL_WORK_REQUEST_TENANT_PAGINATION);
            st.setString(1, login);
            st.setInt(2, offset);
            st.setInt(3, noOfRecords);
            rs = st.executeQuery();
            List<WorkRequest> workRequestList = new ArrayList<>();
            WorkRequest workRequest;
            while (rs.next()) {
                workRequest = new WorkRequest();
                workRequest.setRequestID(rs.getInt(ColumnName.REQUEST_ID));
                workRequest.setFillingDate(rs.getString(ColumnName.FILLING_DATE));
                workRequest.setPlannedDate(rs.getString(ColumnName.PLANNED_DATE));
                workRequest.setTenantUserId(rs.getInt(ColumnName.TENANT_USER_ID_FK));
                workRequest.setRequestStatus(rs.getString(ColumnName.REQUEST_STATUS_ID_FK));
                workRequestList.add(workRequest);
            }
            return workRequestList;
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception ", e);
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in getAllRequestForTenantByLogin", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public List<WorkRequest> getAllRequestForTenantByLogin(String login) throws DaoException {
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_GET_ALL_REQUESTS_FOR_TENANT);
            st.setString(1, login);
            rs = st.executeQuery();
            List<WorkRequest> allRequestForTenant = new ArrayList<>();
            WorkRequest workRequest;
            while (rs.next()) {
                workRequest = new WorkRequest();
                workRequest.setRequestID(rs.getInt(ColumnName.REQUEST_ID));
                workRequest.setFillingDate(rs.getString(ColumnName.FILLING_DATE));
                workRequest.setPlannedDate(rs.getString(ColumnName.PLANNED_DATE));
                workRequest.setTenantUserId(rs.getInt(ColumnName.TENANT_USER_ID_FK));
                workRequest.setRequestStatus(rs.getString(ColumnName.REQUEST_STATUS_ID_FK));
                allRequestForTenant.add(workRequest);
            }
            return allRequestForTenant;
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception ", e);
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in getAllRequestForTenantByLogin", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public List<Subquery> getAllSubqueriesForRequest(int requestId) throws DaoException {
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_GET_All_SUBQUERIES_FOR_REQUEST);
            st.setInt(1, requestId);
            rs = st.executeQuery();
            List<Subquery> subqueries = new ArrayList<>();
            Subquery sub;
            while (rs.next()) {
                sub = new Subquery();
                sub.setSubId(rs.getInt(ColumnName.SUB_ID));
                sub.setAmountOfWorkInHours(rs.getInt(ColumnName.AMOUNT_OF_WORK_IN_HOURS));
                sub.setInformation(rs.getString(ColumnName.INFORMATION));
                sub.setMainRequestId(rs.getInt(ColumnName.SUB_WORK_REQUEST_ID_FK));
                sub.setWorkType(rs.getString(ColumnName.SUB_WORK_TYPE_ID));
                subqueries.add(sub);
                }
            return subqueries;
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception ", e);
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception getAllSubqueriesForRequest ", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public List<WorkRequest> getNewRequestsForOneWorkType(int workTypeId, int offset, int noOfRecords) throws DaoException {
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_GET_NEW_REQUESTS_FOR_ONE_WORK_TYPE_PAGE);
            st.setInt(1, workTypeId);
            st.setInt(2, offset);
            st.setInt(3, noOfRecords);
            rs = st.executeQuery();
            List<WorkRequest> allNewRequestsForOneWorkType = new ArrayList<>();
            WorkRequest workRequest;
            while (rs.next()) {
                workRequest = new WorkRequest();
                workRequest.setRequestID(rs.getInt(ColumnName.REQUEST_ID));
                workRequest.setFillingDate(rs.getString(ColumnName.FILLING_DATE));
                workRequest.setPlannedDate(rs.getString(ColumnName.PLANNED_DATE));
                workRequest.setTenantUserId(rs.getInt(ColumnName.TENANT_USER_ID_FK));
                workRequest.setRequestStatus("1");
                allNewRequestsForOneWorkType.add(workRequest);
            }
            return allNewRequestsForOneWorkType;
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception ", e);
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in getNewRequestsForOneWorkType", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    public List<WorkRequest> getAllNewRequests(int offset, int noOfRecords) throws DaoException {
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_GET_NEW_REQUESTS_PAGE);
            st.setInt(1, offset);
            st.setInt(2, noOfRecords);
            rs = st.executeQuery();
            List<WorkRequest> allNewRequests = new ArrayList<>();
            WorkRequest workRequest;
            while (rs.next()) {
                workRequest = new WorkRequest();
                workRequest.setRequestID(rs.getInt(ColumnName.REQUEST_ID));
                workRequest.setFillingDate(rs.getString(ColumnName.FILLING_DATE));
                workRequest.setPlannedDate(rs.getString(ColumnName.PLANNED_DATE));
                workRequest.setTenantUserId(rs.getInt(ColumnName.TENANT_USER_ID_FK));
                workRequest.setRequestStatus(rs.getString(ColumnName.REQUEST_STATUS_ID_FK));
                allNewRequests.add(workRequest);
            }
            return allNewRequests;
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception ", e);
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in getAllNewRequests ", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public boolean updateWorkRequestStatus(int workRequestId, String updatedStatusId) throws DaoException {
        Connection connection = null;
        PreparedStatement st = null;
        boolean isUpdate = false;

        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_UPDATE_WORK_REQUEST_STATUS);
            st.setString(1, updatedStatusId);
            st.setInt(2, workRequestId);
            int i = st.executeUpdate();
            if (i > 0) {
                isUpdate = true;
            }
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception ", e);
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in updateWorkRequestStatus ", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
        return isUpdate;
    }

    @Override
    public boolean updateWorkRequestCompletionDate(int workRequestId, String plannedDate) throws DaoException {
        Connection connection = null;
        PreparedStatement st = null;
        boolean isUpdate = false;

        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_UPDATE_WORK_REQUEST_PLANNED_DATE);
            st.setString(1, plannedDate);
            st.setInt(2, workRequestId);
            int i = st.executeUpdate();
            if (i > 0) {
                isUpdate = true;
            }
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception ", e);
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in updateWorkrequestCompletionDate" , e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
        return isUpdate;
    }

    @Override
    public int allRequestsByLoginCount(String login) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = ConnectionPool.getInstance().takeConnection();
            st = con.prepareStatement(SQL_GET_COUNT_ALL_REQUESTS_BY_LOGIN);
            st.setString(1, login);
            int amount = 0;
            rs = st.executeQuery();
            if (rs.next()) {
                amount = rs.getInt(AMOUNT);
            }
            return amount;
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in allRequestsByLoginCount", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception ", e);
        } finally {
            ConnectionPool.closeResource(con, st, rs);
        }
    }

    public int newRequestsByTypeCount(int workTypeId) throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = ConnectionPool.getInstance().takeConnection();
            st = con.prepareStatement(SQL_GET_ACTUAL_REQUESTS_TYPE_COUNT);
            st.setInt(1, workTypeId);
            int amount = 0;
            rs = st.executeQuery();
            if (rs.next()) {
                amount = rs.getInt(AMOUNT);
            }
            return amount;
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in newRequestsByTypeCount", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception ", e);
        } finally {
            ConnectionPool.closeResource(con, st, rs);
        }
    }

    public int allNewRequestsCount() throws DaoException {
        Connection connection = null;
        Statement st = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.createStatement();
            rs = st.executeQuery(SQL_GET_ALL_ACTUAL_REQUESTS_COUNT);
            int amount = 0;
            if (rs.next()) {
                amount = rs.getInt(AMOUNT);
            }
            return amount;
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in allNewRequestsCount", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception ", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    public Subquery getSubqueryByRequestIdType(int workRequestId, int workTypeId) throws DaoException {
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_GET_SUBQUERIY_FOR_REQUEST_BY_TYPE);
            st.setInt(1, workRequestId);
            st.setInt(2, workTypeId);
            rs = st.executeQuery();
            Subquery sub = new Subquery();
            if (rs.next()) {
                sub.setSubId(rs.getInt(ColumnName.SUB_ID));
                sub.setAmountOfWorkInHours(rs.getInt(ColumnName.AMOUNT_OF_WORK_IN_HOURS));
                sub.setInformation(rs.getString(ColumnName.INFORMATION));
                sub.setMainRequestId(rs.getInt(ColumnName.SUB_WORK_REQUEST_ID_FK));
                sub.setWorkType(rs.getString(ColumnName.SUB_WORK_TYPE_ID));
            }
            return sub;
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception ", e);
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in getSubqueryByRequestIdType", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

}
