package by.epamtc.poliukov.dao.impl;

import by.epamtc.poliukov.dao.ColumnName;
import by.epamtc.poliukov.dao.UserDao;
import by.epamtc.poliukov.exception.ConnectionPoolException;
import by.epamtc.poliukov.exception.DaoException;
import by.epamtc.poliukov.dao.pool.ConnectionPool;
import by.epamtc.poliukov.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final UserDao INSTANCE = new UserDaoImpl();
    private UserDaoImpl() {
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    private final static String SQL_REGISTER_USER = "INSERT INTO users (login, password, name, second_name," +
            "surname, email, phone, role_id_fk) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

    private final static String SQL_LOG_IN = "SELECT * FROM users JOIN roles on users.role_id_fk = roles.role_id " +
            "WHERE `login`= ? and `password` = ?";

    private final static String SQL_VIEW_ALL_TENANT =
            "SELECT * FROM users JOIN users_part_tenant upt on users.user_id = upt.part_user_id " +
                    "JOIN roles on users.role_id_fk = roles.role_id";

    private final static String SQL_VIEW_ALL_EMPLOYEE =
            "SELECT * FROM users JOIN users_part_employee upe on users.user_id = upe.part_user_id " +
                    "JOIN roles on users.role_id_fk = roles.role_id ";

    private final static String SQL_VIEW_ALL_EMPLOYEE_PAGINATION =
            "SELECT * FROM users JOIN users_part_employee upe on users.user_id = upe.part_user_id " +
                    "JOIN roles on users.role_id_fk = roles.role_id WHERE upe.is_blocked = 0 ORDER BY upe.part_user_id DESC LIMIT ?, ?";

    private final static String SQL_VIEW_ALL_EMPLOYEE_PAGINATION_BY_TYPE =
            "SELECT * FROM users JOIN users_part_employee upe on users.user_id = upe.part_user_id " +
                    "JOIN roles on users.role_id_fk = roles.role_id " +
                    "JOIN employee_work_types ewt ON users.user_id = ewt_user_id_fk " +
                    "WHERE upe.is_blocked = 0 AND ewt_work_type_id = ? ORDER BY upe.part_user_id DESC LIMIT ?, ?";

    private static final String COUNT_ALL_EMPLOYEES = "SELECT COUNT(part_user_id) AS amount FROM users_part_employee " +
            "WHERE is_blocked = 0 ";

    private static final String COUNT_ALL_EMPLOYEES_BY_TYPE = "SELECT COUNT(part_user_id) AS amount FROM users_part_employee upe " +
            "JOIN employee_work_types ewt ON ewt_user_id_fk = part_user_id " +
            "WHERE is_blocked = 0 AND ewt_work_type_id = ?";

    private static final String SQL_DELETE_BY_USERNAME =
            "DELETE FROM users WHERE login = ?";

    private final static String SQL_GET_USER_BY_USERNAME =
            "SELECT * FROM users JOIN roles on users.role_id_fk = roles.role_id WHERE login = ?";

    private final static String SQL_UPDATE_EMPLOYEE_STATUS =
            "UPDATE users_part_employee upe JOIN users on " +
                    "users.user_id = upe.part_user_id SET is_blocked = ? WHERE login = ?";

    private static final String SQL_UNIQUE_LOGIN = "SELECT login "
            + "FROM users WHERE login = ?";

    private static final String SQL_UNIQUE_EMAIL = "SELECT email "
            + "FROM users WHERE email = ?";

    private final static String SQL_REGISTER_TENANT = "INSERT INTO users_part_tenant " +
            "(part_user_id, city, address) VALUES (?, ?, ?)";

    private final static String SQL_GET_TENANT_INFO = "SELECT * FROM users_part_tenant WHERE part_user_id = ?";

    private final static String SQL_REGISTER_EMPLOYEE = "INSERT INTO users_part_employee " +
            "(part_user_id, value_person_hour, information) VALUES (?, ?, ?)";

    private final static String SQL_GET_EMPLOYEE_INFO = "SELECT * FROM users_part_employee WHERE part_user_id = ?";

    private final static String AMOUNT = "amount";

    @Override
    public boolean addUser(User user) throws DaoException {
        boolean isAdded = true;
        if (isLoginEmailUnique(user)) {
            Connection connection = null;
            PreparedStatement statementUser = null;
            try {
                connection = ConnectionPool.getInstance().takeConnection();
                statementUser = connection.prepareStatement(SQL_REGISTER_USER);
                statementUser.setString(1, user.getLogin());
                statementUser.setString(2, user.getPassword());
                statementUser.setString(3, user.getName());
                statementUser.setString(4, user.getSecondName());
                statementUser.setString(5, user.getSurname());
                statementUser.setString(6, user.getEmail());
                statementUser.setString(7, user.getPhone());
                statementUser.setString(8, user.getRole());
                statementUser.execute();
            } catch (SQLException e) {
                throw new DaoException("Registered sql exception in AddUser ", e);
            } catch (ConnectionPoolException e) {
                throw new DaoException("Pool connection exception ", e);
            } finally {
                ConnectionPool.closeResource(connection, statementUser);
            }
        } else {
            isAdded = false;
        }
        return isAdded;
    }

    @Override
    public User authorise(String login, String password) throws DaoException {
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs;

        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_LOG_IN);
            st.setString(1, login);
            st.setString(2, password);
            rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            User user = new User();
            user.setUserId(rs.getInt(ColumnName.USER_ID));
            user.setLogin(rs.getString(ColumnName.USERNAME));
            user.setPassword(rs.getString(ColumnName.PASSWORD));
            user.setName(rs.getString(ColumnName.NAME));
            user.setSecondName(rs.getString(ColumnName.SECOND_NAME));
            user.setSurname(rs.getString(ColumnName.SURNAME));
            user.setEmail(rs.getString(ColumnName.EMAIL));
            user.setPhone(rs.getString(ColumnName.PHONE));
            user.setRole(rs.getString(ColumnName.ROLE_NAME));
            return user;
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception in authorise", e);
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception ", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public List<User> getAllTenants() throws DaoException {
        Connection connection = null;
        Statement st = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.createStatement();
            rs = st.executeQuery(SQL_VIEW_ALL_TENANT);
            List<User> allTenants = new ArrayList<>();
            User user;
            while (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt(ColumnName.USER_ID));
                user.setLogin(rs.getString(ColumnName.USERNAME));
                user.setPassword(rs.getString(ColumnName.PASSWORD));
                user.setName(rs.getString(ColumnName.NAME));
                user.setSecondName(rs.getString(ColumnName.SECOND_NAME));
                user.setSurname(rs.getString(ColumnName.SURNAME));
                user.setEmail(rs.getString(ColumnName.EMAIL));
                user.setPhone(rs.getString(ColumnName.PHONE));
                user.setRole(rs.getString(ColumnName.ROLE_NAME));
                user.setAddress(rs.getString(ColumnName.ADDRESS));
                user.setCity(rs.getString(ColumnName.CITY));
                allTenants.add(user);
            }
            return allTenants;
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception in getAllTenants", e);
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception ", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public List<User> getAllEmployee() throws DaoException {
        Connection connection = null;
        Statement st = null;
        ResultSet rs;

        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.createStatement();
            rs = st.executeQuery(SQL_VIEW_ALL_EMPLOYEE);
            List<User> allEmployees = new ArrayList<>();
            User user;
            while (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt(ColumnName.USER_ID));
                user.setLogin(rs.getString(ColumnName.USERNAME));
                user.setPassword(rs.getString(ColumnName.PASSWORD));
                user.setName(rs.getString(ColumnName.NAME));
                user.setSecondName(rs.getString(ColumnName.SECOND_NAME));
                user.setSurname(rs.getString(ColumnName.SURNAME));
                user.setEmail(rs.getString(ColumnName.EMAIL));
                user.setPhone(rs.getString(ColumnName.PHONE));
                user.setRole(rs.getString(ColumnName.ROLE_NAME));
                user.setValuePersonHour(rs.getInt(ColumnName.VALUE_PERSON_HOUR));
                user.setInformation(rs.getString(ColumnName.INFORMATION));
                user.setBlocked(rs.getInt(ColumnName.IS_BLOCKED) == 1);

                allEmployees.add(user);
            }
            return allEmployees;
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception in getAllEmployee", e);
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception ", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public List<User> getAllEmployee(int offset, int noOfRecords) throws DaoException {
        PreparedStatement st = null;
        ResultSet rs;
        Connection connection = null;

        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_VIEW_ALL_EMPLOYEE_PAGINATION);
            st.setInt(1, offset);
            st.setInt(2, noOfRecords);
            rs = st.executeQuery();
            List<User> allEmployees = new ArrayList<>();
            User user;
            while (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt(ColumnName.USER_ID));
                user.setLogin(rs.getString(ColumnName.USERNAME));
                user.setPassword(rs.getString(ColumnName.PASSWORD));
                user.setName(rs.getString(ColumnName.NAME));
                user.setSecondName(rs.getString(ColumnName.SECOND_NAME));
                user.setSurname(rs.getString(ColumnName.SURNAME));
                user.setEmail(rs.getString(ColumnName.EMAIL));
                user.setPhone(rs.getString(ColumnName.PHONE));
                user.setRole(rs.getString(ColumnName.ROLE_NAME));
                user.setValuePersonHour(rs.getInt(ColumnName.VALUE_PERSON_HOUR));
                user.setInformation(rs.getString(ColumnName.INFORMATION));
                user.setBlocked(rs.getInt(ColumnName.IS_BLOCKED) == 1);
                allEmployees.add(user);
            }
            return allEmployees;
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception in getAllEmployeePagination", e);
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception ", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public List<User> getAllEmployee(int offset, int noOfRecords, int typeID) throws DaoException {
        PreparedStatement st = null;
        ResultSet rs;
        Connection connection = null;

        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_VIEW_ALL_EMPLOYEE_PAGINATION_BY_TYPE);
            st.setInt(1, typeID);
            st.setInt(2, offset);
            st.setInt(3, noOfRecords);
            rs = st.executeQuery();
            List<User> allEmployees = new ArrayList<>();
            User user;
            while (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt(ColumnName.USER_ID));
                user.setLogin(rs.getString(ColumnName.USERNAME));
                user.setPassword(rs.getString(ColumnName.PASSWORD));
                user.setName(rs.getString(ColumnName.NAME));
                user.setSecondName(rs.getString(ColumnName.SECOND_NAME));
                user.setSurname(rs.getString(ColumnName.SURNAME));
                user.setEmail(rs.getString(ColumnName.EMAIL));
                user.setPhone(rs.getString(ColumnName.PHONE));
                user.setRole(rs.getString(ColumnName.ROLE_NAME));
                user.setValuePersonHour(rs.getInt(ColumnName.VALUE_PERSON_HOUR));
                user.setInformation(rs.getString(ColumnName.INFORMATION));
                user.setBlocked(rs.getInt(ColumnName.IS_BLOCKED) == 1);
                allEmployees.add(user);
            }
            return allEmployees;
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception ", e);
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in getAllEmployee_ByTypePagination ", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    public int allEmployeesCount() throws DaoException {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = ConnectionPool.getInstance().takeConnection();
            st = con.prepareStatement(COUNT_ALL_EMPLOYEES);
            int amount = 0;
            rs = st.executeQuery();
            if (rs.next()) {
                amount = rs.getInt(AMOUNT);
            }
            return amount;

        } catch (SQLException e) {
            throw new DaoException("allEmployeesCount sql exception", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception", e);
        } finally {
            ConnectionPool.closeResource(con, st, rs);
        }
    }

    public int allEmployeesCount(int workTypeId) throws DaoException {
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(COUNT_ALL_EMPLOYEES_BY_TYPE);
            st.setInt(1, workTypeId);
            rs = st.executeQuery();
            int amount = 0;
            rs = st.executeQuery();
            if (rs.next()) {
                amount = rs.getInt(AMOUNT);
            }
            return amount;

        } catch (SQLException e) {
            throw new DaoException("allEmployeesCount_ByType sql exception ", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception", e);
        } finally {
            ConnectionPool.closeResource(connection, st, rs);
        }
    }

    @Override
    public User getUserByLogin(String login) throws DaoException {
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_GET_USER_BY_USERNAME);
            st.setString(1, login);
            rs = st.executeQuery();
            User user = null;
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt(ColumnName.USER_ID));
                user.setLogin(rs.getString(ColumnName.USERNAME));
                user.setPassword(rs.getString(ColumnName.PASSWORD));
                user.setName(rs.getString(ColumnName.NAME));
                user.setSecondName(rs.getString(ColumnName.SECOND_NAME));
                user.setSurname(rs.getString(ColumnName.SURNAME));
                user.setEmail(rs.getString(ColumnName.EMAIL));
                user.setPhone(rs.getString(ColumnName.PHONE));
                user.setRole(rs.getString(ColumnName.ROLE_NAME));
            }
            return user;
        } catch (SQLException e) {
            throw new DaoException("getUserByLogin sql exception", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("pool connection exception");
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }


    @Override
    public boolean deleteUser(String login) throws DaoException {
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_DELETE_BY_USERNAME);
            st.setString(1, login);
            int update = st.executeUpdate();
            return update > 0;
        } catch (SQLException e) {
            throw new DaoException("sql exception in deleteUser", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("pool connection exception");
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public boolean updateEmployeeStatus(String login, boolean is_Blocked) throws DaoException {
        boolean isUpdate = false;
        Connection connection = null;
        PreparedStatement st = null;
        int block = is_Blocked ? 1 : 0;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_UPDATE_EMPLOYEE_STATUS);
            st.setInt(1, block);
            st.setString(2, login);
            int i = st.executeUpdate();
            if (i > 0) {
                isUpdate = true;
            }
        } catch (SQLException e) {
            throw new DaoException("updateEmployeeStatus sql exception", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("pool connection exception");
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
        return isUpdate;
    }

    private boolean isLoginEmailUnique(User user) throws DaoException {
        boolean isUnique = true;
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement loginStatement = connection
                     .prepareStatement(SQL_UNIQUE_LOGIN);
             PreparedStatement emailStatement = connection
                     .prepareStatement(SQL_UNIQUE_EMAIL)) {
            loginStatement.setString(1, user.getLogin());
            emailStatement.setString(1, user.getEmail());
            ResultSet loginResultSet = loginStatement.executeQuery();
            ResultSet emailResultSet = emailStatement.executeQuery();
            if (loginResultSet.next()) {
                isUnique = false;
            }
            if (emailResultSet.next()) {
                isUnique = false;
            }
        } catch (SQLException ex) {
            throw new DaoException("Failed to check login/email for unique value", ex);
        } catch (ConnectionPoolException ex) {
            throw new DaoException("pool connection exception", ex);
        }
        return isUnique;
    }

    @Override
    public boolean addTenantInfo(int userId, String city, String address) throws DaoException {
        boolean isAdded;
        Connection connection = null;
        PreparedStatement statementTenant = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statementTenant = connection.prepareStatement(SQL_REGISTER_TENANT);
            statementTenant.setInt(1, userId);
            statementTenant.setString(2, city);
            statementTenant.setString(3, address);
            statementTenant.execute();
            isAdded = true;
            } catch (SQLException e) {
                throw new DaoException("Registered sql exception in addTenantInfo", e);
            } catch (ConnectionPoolException e) {
                throw new DaoException("Pool connection exception ", e);
            } finally {
                ConnectionPool.closeResource(connection, statementTenant);
            }
        return isAdded;
    }

    @Override
    public List<String> getTenantInfo(int userId) throws DaoException {
        List<String> tenantInfo = new ArrayList<>();
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_GET_TENANT_INFO);
            st.setInt(1, userId);
            rs = st.executeQuery();
            while (rs.next()) {
                tenantInfo.add(rs.getString(ColumnName.CITY));
                tenantInfo.add(rs.getString(ColumnName.ADDRESS));
            }
            return tenantInfo;

        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in getTenantInfo", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("pool connection exception");
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public boolean addEmployeeInfo(int userId, int value_person_hour, String information) throws DaoException {
        boolean isAdded = true;
        Connection connection = null;
        PreparedStatement statementEmployee = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statementEmployee = connection.prepareStatement(SQL_REGISTER_EMPLOYEE);
            statementEmployee.setInt(1, userId);
            statementEmployee.setInt(2, value_person_hour);
            statementEmployee.setString(3, information);
            statementEmployee.execute();
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception in addEmployeeInfo", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception ", e);
        } finally {
            ConnectionPool.closeResource(connection, statementEmployee);
        }
        return isAdded;
    }

    @Override
    public List<String> getEmployeeInfo(int userId) throws DaoException {
        List<String> employeeInfo = new ArrayList<>();
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.prepareStatement(SQL_GET_EMPLOYEE_INFO);
            st.setInt(1, userId);
            rs = st.executeQuery();
            while (rs.next()) {
                employeeInfo.add(String.valueOf(rs.getInt(ColumnName.VALUE_PERSON_HOUR)));
                employeeInfo.add(rs.getString(ColumnName.INFORMATION));
                employeeInfo.add(String.valueOf(rs.getInt(ColumnName.IS_BLOCKED)));
            }
            return employeeInfo;
        } catch (SQLException e) {
            throw new DaoException("sql exception in getEmployeeInfo", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("pool connection exception");
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }
}
