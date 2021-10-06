package by.epamtc.poliukov.dao.impl;

import by.epamtc.poliukov.dao.ColumnName;
import by.epamtc.poliukov.dao.UserDao;
import by.epamtc.poliukov.exception.ConnectionPoolException;
import by.epamtc.poliukov.exception.DaoException;
import by.epamtc.poliukov.dao.pool.ConnectionPool;
import by.epamtc.poliukov.entity.Employee;
import by.epamtc.poliukov.entity.Tenant;
import by.epamtc.poliukov.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final UserDao INSTANCE = new UserDaoImpl();
    private UserDaoImpl(){
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
    private final static String SQL_REGISTER_TENANT= "INSERT INTO users_part_tenant " +
            "(part_user_id, city, address) VALUES (?, ?, ?)";


    private final Logger logger = LogManager.getLogger(UserDaoImpl.class);

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
                // Set role as roleID;
                statementUser.setString(8, user.getRole());
                statementUser.execute();
            } catch (SQLException e) {
                throw new DaoException("Registered sql exception ", e);
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
            throw new DaoException("Pool connection exception ", e);
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception ", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public List<Tenant> getAllTenants() throws DaoException {
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.createStatement();
            rs = st.executeQuery(SQL_VIEW_ALL_TENANT);
            List<Tenant> allTenants = new ArrayList<>();
            Tenant tenant = null;
            while (rs.next()) {
                tenant = new Tenant();
                tenant.setUserId(rs.getInt(ColumnName.USER_ID));
                tenant.setLogin(rs.getString(ColumnName.USERNAME));
                tenant.setPassword(rs.getString(ColumnName.PASSWORD));
                tenant.setName(rs.getString(ColumnName.NAME));
                tenant.setSecondName(rs.getString(ColumnName.SECOND_NAME));
                tenant.setSurname(rs.getString(ColumnName.SURNAME));
                tenant.setEmail(rs.getString(ColumnName.EMAIL));
                tenant.setPhone(rs.getString(ColumnName.PHONE));
                tenant.setRole(rs.getString(ColumnName.ROLE_NAME));
                tenant.setAddress(rs.getString(ColumnName.ADDRESS));
                tenant.setCity(rs.getString(ColumnName.CITY));

                allTenants.add(tenant);
            }
            return allTenants;
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception ", e);
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception ", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public List<Employee> getAllEmployee() throws DaoException {
        Connection connection = null;
        Statement st = null;
        ResultSet rs;

        try {
            connection = ConnectionPool.getInstance().takeConnection();
            st = connection.createStatement();
            rs = st.executeQuery(SQL_VIEW_ALL_EMPLOYEE);
            List<Employee> allEmployees = new ArrayList<>();
            Employee employee;
            while (rs.next()) {
                employee = new Employee();
                employee.setUserId(rs.getInt(ColumnName.USER_ID));
                employee.setLogin(rs.getString(ColumnName.USERNAME));
                employee.setPassword(rs.getString(ColumnName.PASSWORD));
                employee.setName(rs.getString(ColumnName.NAME));
                employee.setSecondName(rs.getString(ColumnName.SECOND_NAME));
                employee.setSurname(rs.getString(ColumnName.SURNAME));
                employee.setEmail(rs.getString(ColumnName.EMAIL));
                employee.setPhone(rs.getString(ColumnName.PHONE));
                employee.setRole(rs.getString(ColumnName.ROLE_NAME));
                employee.setValuePersonHour(rs.getInt(ColumnName.VALUE_PERSON_HOUR));
                employee.setInformation(rs.getString(ColumnName.INFORMATION));
                employee.setBlocked(rs.getInt(ColumnName.IS_BLOCKED) == 1);

                allEmployees.add(employee);
            }
            return allEmployees;
        } catch (ConnectionPoolException e) {
            throw new DaoException("Pool connection exception ", e);
        } catch (SQLException e) {
            throw new DaoException("Registered sql exception ", e);
        } finally {
            ConnectionPool.closeResource(connection, st);
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
            if(rs.next()) {
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
            throw new DaoException("sql error", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("pool connection error");
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
            if (update > 0) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new DaoException("sql error", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("pool connection error");
        } finally {
            ConnectionPool.closeResource(connection, st);
        }
    }

    @Override
    public boolean updateEmployeeStatus(String login, boolean is_Blocked) throws DaoException {
        boolean isUpdate = false;
        Connection connection = null;
        PreparedStatement st = null;
        int block = is_Blocked? 1 : 0;
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
            throw new DaoException("sql error", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Failed to change employee status");
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
                logger.log(Level.WARN, "Login is not unique");
                isUnique = false;
            }
            if (emailResultSet.next()) {
                logger.log(Level.WARN, "Email is not unique");
                isUnique = false;
            }
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DaoException("Failed to check login/email for unique value",
                    ex);
        }
        return isUnique;
    }

    @Override
    public boolean addTenant(int userId, String city, String address) throws DaoException {
        boolean isAdded = true;
        Connection connection = null;
        PreparedStatement statementTenant = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            statementTenant = connection.prepareStatement(SQL_REGISTER_TENANT);
            statementTenant.setInt(1, userId);
            statementTenant.setString(2, city);
            statementTenant.setString(3, address);
            statementTenant.execute();
            } catch (SQLException e) {
                throw new DaoException("Registered sql exception ", e);
            } catch (ConnectionPoolException e) {
                throw new DaoException("Pool connection exception ", e);
            } finally {
                ConnectionPool.closeResource(connection, statementTenant);
            }
        return isAdded;
    }
}
