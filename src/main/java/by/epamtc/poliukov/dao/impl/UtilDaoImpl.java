package by.epamtc.poliukov.dao.impl;

import by.epamtc.poliukov.dao.ColumnName;
import by.epamtc.poliukov.dao.UtilDao;
import by.epamtc.poliukov.dao.pool.ConnectionPool;
import by.epamtc.poliukov.entity.WorkRequest;
import by.epamtc.poliukov.exception.ConnectionPoolException;
import by.epamtc.poliukov.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private final static String SQL_GET_REQUEST_STATUS_ID_BY_NAME =
            "SELECT request_status_id FROM request_statuses " +
                    "WHERE request_status_name = ?";
    private final static String SQL_UPDATE_ROLE_BY_LOGIN = "UPDATE users SET role_id_fk = ? WHERE login = ?";

    private final static String SQL_GET_REQUEST_BY_DATE_ID = "SELECT * FROM work_requests " +
            "WHERE filling_date = ? and tenant_user_id_fk = ?";


    @Override
    public String takeRoleIdByRoleName(String role) throws DaoException {
        String roleId = null;
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
        throw new DaoException("Registered sql exception ", e);
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
        ResultSet rs = null;
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
            throw new DaoException("Registered sql exception ", e);
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
        ResultSet rs = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_GET_EMPLOYEE_STATUS_BY_LOGIN);
            st.setString(1, login);
            rs = st.executeQuery();

            if(rs.next()) {
                isBlocked = rs.getInt(ColumnName.IS_BLOCKED) == 1;
            }
            return isBlocked;

        } catch (SQLException e) {
            throw new DaoException("sql error", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("pool connection error");
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public Integer takeWorkTypeIdByName(String workTypeName) throws DaoException {
        int workTypeId = -1;
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
            throw new DaoException("sql error", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("pool connection error");
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public List<String> takeEmployeeWorkType(String login) throws DaoException {
        List<String> employeeWorkTypeName = new ArrayList<>();
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
            throw new DaoException("sql error", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("pool connection error");
        } finally {
            ConnectionPool.closeResource(connection, st);
        }

    }

    @Override
    public Integer takeRequestStatusIdByStatusName(String statusName) throws DaoException {
        int requestStatusId = -1;
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
            throw new DaoException("sql error", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("pool connection error");
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
            throw new DaoException("sql error", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("pool connection error");
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public WorkRequest takeWorkRequestByFillingDateUserId(String fillingDate, int tenantId) throws DaoException {
        WorkRequest workRequest = null;
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs = null;
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
            throw new DaoException("sql error", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("pool connection error");
        } finally {
            ConnectionPool.closeResource(connection, st);
        }

    }

    /*
    @Override
    public List<String> takeTenantInfo(String login) throws DaoException {
        List<String> tenantInfo = new ArrayList<>();
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_GET_TENANT_INFO);
            st.setString(1, login);
            rs = st.executeQuery();
            while (rs.next()) {
                tenantInfo.add(rs.getString(ColumnName.CITY));
                tenantInfo.add(rs.getString(ColumnName.ADDRESS));

            }
            return tenantInfo;

        } catch (SQLException e) {
            throw new DaoException("sql error", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("pool connection error");
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

 */


}
