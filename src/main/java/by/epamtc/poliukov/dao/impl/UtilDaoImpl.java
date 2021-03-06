package by.epamtc.poliukov.dao.impl;

import by.epamtc.poliukov.dao.ColumnName;
import by.epamtc.poliukov.dao.UtilDao;
import by.epamtc.poliukov.dao.pool.ConnectionPool;
import by.epamtc.poliukov.entity.User;
import by.epamtc.poliukov.entity.WorkRequest;
import by.epamtc.poliukov.exception.ConnectionPoolException;
import by.epamtc.poliukov.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilDaoImpl implements UtilDao {

    private UtilDaoImpl() {
    }

    private static final UtilDaoImpl INSTANCE = new UtilDaoImpl();

    public static UtilDaoImpl getInstance() {
        return INSTANCE;
    }

    private static final String SQL_GET_ROLE_ID_BY_ROLE = "SELECT role_id FROM roles WHERE " +
            "role_name = ? ";

    private static final String SQL_GET_ROLE_NAME_BY_ROLE_ID = "SELECT role_name FROM roles WHERE " +
            "role_id = ? ";

    private final static String SQL_GET_EMPLOYEE_STATUS_BY_LOGIN =
            "SELECT is_blocked from users_part_employee JOIN users " +
                    "on users.user_id=users_part_employee.part_user_id WHERE login = ?";

    private final static String SQL_GET_EMPLOYEE_WORK_TYPE_BY_LOGIN =
            "SELECT work_type_name FROM work_types wt " +
                    "JOIN employee_work_types ewt ON wt.work_type_id=ewt_work_type_id  " +
                    "JOIN users on users.user_id=ewt.ewt_user_id_fk WHERE login = ?";

    private final static String SQL_GET_WORK_TYPE_ID_BY_NAME =
            "SELECT work_type_id FROM work_types WHERE work_type_name = ?";

    private final static String SQL_GET_WORK_TYPE_NAME_BY_ID =
            "SELECT work_type_name FROM work_types WHERE work_type_id = ?";

    private final static String SQL_GET_REQUEST_STATUS_ID_BY_NAME =
            "SELECT request_status_id FROM request_statuses WHERE request_status_name = ?";

    private final static String SQL_GET_REQUEST_STATUS_NAME_BY_ID =
            "SELECT request_status_name FROM request_statuses WHERE request_status_id = ?";

    private final static String SQL_UPDATE_ROLE_BY_LOGIN = "UPDATE users SET role_id_fk = ? WHERE login = ?";

    private final static String SQL_GET_REQUEST_BY_DATE_ID = "SELECT * FROM work_requests " +
            "WHERE filling_date = ? and tenant_user_id_fk = ?";
    private final static String SQL_TAKE_MAIN_INFO = "SELECT * from about_us";
    private final static String SQL_GET_REQUEST_ID_BY_SUBQUERY_ID = "SELECT * FROM subqueries WHERE sub_id = ?";

    @Override
    public String takeRoleIdByRoleName(String role) throws DaoException {
        String roleId = null;
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_GET_ROLE_ID_BY_ROLE);
            st.setString(1, role);
            rs = st.executeQuery();
            if (rs.next()) {
                roleId = rs.getString(ColumnName.ROLE_ID);
            }
            return roleId;
        } catch (SQLException e) {
        throw new DaoException("Registered sql exception in takeRoleIdByRoleName ", e);
        } catch (ConnectionPoolException e) {
        throw new DaoException("Pool connection exception ", e);
        } finally {
        ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public String takeRoleNameByRoleId(int roleId) throws DaoException {
        String roleName = null;
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_GET_ROLE_NAME_BY_ROLE_ID);
            st.setInt(1, roleId);
            rs = st.executeQuery();
            if (rs.next()) {
                roleName = rs.getString(ColumnName.ROLE_NAME);
            }
            return roleName;
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in takeRoleNameByRoleId", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception ", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public Boolean takeEmployeeStatus(String login) throws DaoException {
        Boolean isBlocked = null;
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_GET_EMPLOYEE_STATUS_BY_LOGIN);
            st.setString(1, login);
            rs = st.executeQuery();

            if (rs.next()) {
                isBlocked = rs.getInt(ColumnName.IS_BLOCKED) == 1;
            }
            return isBlocked;

        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in takeEmployeeStatus", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public Integer takeWorkTypeIdByName(String workTypeName) throws DaoException {
        int workTypeId = -1;
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_GET_WORK_TYPE_ID_BY_NAME);
            st.setString(1, workTypeName);
            rs = st.executeQuery();
            if (rs.next()) {
                workTypeId = rs.getInt(ColumnName.WORK_TYPE_ID);
            }
            return workTypeId;
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in takeWorkTypeIdByName", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public String takeWorkTypeName(int workTypeId) throws DaoException {
        String workTypeName = null;
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_GET_WORK_TYPE_NAME_BY_ID);
            st.setInt(1, workTypeId);
            rs = st.executeQuery();
            if (rs.next()) {
                workTypeName = rs.getString(ColumnName.WORK_TYPE_NAME);
            }
            return workTypeName;
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in takeWorkTypeName", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception");
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public List<String> takeEmployeeWorkType(String login) throws DaoException {
        List<String> employeeWorkTypeName = new ArrayList<>();
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_GET_EMPLOYEE_WORK_TYPE_BY_LOGIN);
            st.setString(1, login);
            rs = st.executeQuery();
            while (rs.next()) {
                employeeWorkTypeName.add(rs.getString(ColumnName.WORK_TYPE_NAME));
            }
            return employeeWorkTypeName;
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in takeEmployeeWorkType", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception");
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public Integer takeRequestStatusIdByStatusName(String statusName) throws DaoException {
        int requestStatusId = -1;
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_GET_REQUEST_STATUS_ID_BY_NAME);
            st.setString(1, statusName);
            rs = st.executeQuery();
            while (rs.next()) {
                requestStatusId = rs.getInt(ColumnName.REQUEST_STATUS_ID);
            }
            return requestStatusId;
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in takeRequestStatusIdByStatusName", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception");
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public String takeRequestStatusNameByStatusId(int statusId) throws DaoException {
        String requestStatusName = null;
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_GET_REQUEST_STATUS_NAME_BY_ID);
            st.setInt(1, statusId);
            rs = st.executeQuery();
            while (rs.next()) {
                requestStatusName = rs.getString(ColumnName.REQUEST_STATUS_NAME);
            }
            return requestStatusName;
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in takeRequestStatusNameByStatusId", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception");
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public boolean updateUserRole(String login, Integer roleId) throws DaoException {
        boolean isUpdate;
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_UPDATE_ROLE_BY_LOGIN);
            st.setInt(1, roleId);
            st.setString(2, login);
            st.execute();
            isUpdate = true;
            return isUpdate;
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in updateUserRole", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception");
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public WorkRequest takeWorkRequestByFillingDateUserId(String fillingDate, int tenantId) throws DaoException {
        WorkRequest workRequest = null;
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_GET_REQUEST_BY_DATE_ID);
            st.setString(1, fillingDate);
            st.setInt(2, tenantId);
            rs = st.executeQuery();
            if (rs.next()) {
                workRequest = new WorkRequest();
                workRequest.setRequestID(rs.getInt(ColumnName.REQUEST_ID));
                workRequest.setFillingDate(rs.getString(ColumnName.FILLING_DATE));
                workRequest.setPlannedDate(rs.getString(ColumnName.PLANNED_DATE));
                workRequest.setTenantUserId(rs.getInt(ColumnName.TENANT_USER_ID_FK));
                workRequest.setRequestStatus(rs.getString(ColumnName.REQUEST_STATUS_ID_FK));
            }
            return workRequest;
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in takeWorkRequestByFillingDateUserId", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception");
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public String takeMainInformation() throws DaoException {
        Connection connection = null;
        Statement st = null;
        ResultSet rs;
        String information = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.createStatement();
            rs = st.executeQuery(SQL_TAKE_MAIN_INFO);
            if (rs.next()) {
                information = rs.getString(ColumnName.INFORMATION);
            }
            return information;
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception", e);
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in take main information", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public int takeWorkRequestIdBySubqueryId(int subqueryId) throws DaoException {
        int workRequestId = -1;
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_GET_REQUEST_ID_BY_SUBQUERY_ID);
            st.setInt(1, subqueryId);
            rs = st.executeQuery();
            if (rs.next()) {
                workRequestId = rs.getInt(ColumnName.SUB_WORK_REQUEST_ID_FK);
            }
            return workRequestId;
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in takeWorkRequestIdBySubqueryId", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception");
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }
}
