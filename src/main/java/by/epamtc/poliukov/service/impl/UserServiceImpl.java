package by.epamtc.poliukov.service.impl;

import by.epamtc.poliukov.dao.DaoFactory;
import by.epamtc.poliukov.dao.UserDao;
import by.epamtc.poliukov.dao.UtilDao;
import by.epamtc.poliukov.entity.User;
import by.epamtc.poliukov.exception.DaoException;
import by.epamtc.poliukov.exception.ServiceAuthorizationException;
import by.epamtc.poliukov.exception.ServiceException;
import by.epamtc.poliukov.service.Encryption;
import by.epamtc.poliukov.service.UserService;
import by.epamtc.poliukov.service.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static by.epamtc.poliukov.dao.ColumnName.*;

public class UserServiceImpl implements UserService {

    private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    public User createUser(HttpServletRequest request) throws ServiceAuthorizationException {
        String login = request.getParameter(USERNAME);
        String email = request.getParameter(EMAIL);
        String name = request.getParameter(NAME);
        String secondName = request.getParameter(SECOND_NAME);
        String surName = request.getParameter(SURNAME);
        String phone = request.getParameter(PHONE);
        String role = request.getParameter(ROLE_NAME);

        byte[] password = request.getParameter(PASSWORD).getBytes();
        byte[] password2 = request.getParameter("password2").getBytes();

        if (!Validator.validate(login, email) ||
                !Validator.validateLogin(login) ||
                !Validator.validatePassword(password, password2) ||
                !Validator.validateEmail(email)) {
            throw new ServiceAuthorizationException("Check input parameters");
        }
        String pass =  new String(password);
        String encodedPassword = Encryption.encrypt(pass);

        User user = new User();
        user.setLogin(login);
        user.setPassword(encodedPassword);
        user.setName(name);
        user.setSecondName(secondName);
        user.setSurname(surName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setRole(role);

        return user;
    }

    @Override
    public User addUser(User user) throws ServiceException, ServiceAuthorizationException {
        boolean isAdded;
        //if(!Validator.validate(user)) {
          //  throw new ServiceAuthorizationException("incorrect user data");
        //}
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao daoUser = daoFactory.getUserDao();
        UtilDao utilDao = daoFactory.getUtilDao();
        String password =  user.getPassword();
        try {
            user.setPassword(password);
            user.setRole(utilDao.takeRoleIdByRoleName(user.getRole()));
            isAdded = daoUser.addUser(user);
            logger.log(Level.INFO,
                    "User '" + user.getLogin() + "' was added: " + isAdded);
            return daoUser.authorise(user.getLogin(), user.getPassword());
        } catch (DaoException e) {
            throw new ServiceException("Failed to add user", e);
        }
    }

    @Override
    public User addTenantInfo(String login, String city, String address) throws ServiceException {
        //проверить
        User user;
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao daoUser = daoFactory.getUserDao();
        UtilDao utilDao = daoFactory.getUtilDao();

        try {
            user = daoUser.getUserByLogin(login);
            utilDao.updateUserRole(login, 2); //roleId for tenant = 2
            daoUser.addTenantInfo(user.getUserId(), city, address);
            user = daoUser.getUserByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException("Error in source", e);
        }
        return user;
    }



    @Override
    public User authorise(String login, byte[] password) throws ServiceException, ServiceAuthorizationException {
        User user = null;
        if (!Validator.validateLogin(login)
                || !Validator.validatePassword(password)) {
            throw new ServiceAuthorizationException("Wrong login or password");
        }
        String pass =  new String(password);

        user = getUserByLogin(login);

        if (user == null || !Encryption.isMatch(pass , user.getPassword())) {
                throw new ServiceAuthorizationException("Wrong login or password!");
        }
        logger.info(user.toString() + "was authorised");
        return user;
    }
/*
    @Override
    public List<Tenant> getAllTenants() throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao dao = daoFactory.getUserDao();
        List<Tenant> tenants;
        try {
            tenants = dao.getAllTenants();
            if (tenants == null || tenants.size() == 0) {
                throw new ServiceException("No tenants matching your query");
            }
        } catch (DaoException e) {
            throw new ServiceException("Error in source!", e);
        }
        return tenants;
    }
    */

    @Override
    public List<User> getAllEmployee() throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao dao = daoFactory.getUserDao();
        UtilDao utilDao = daoFactory.getUtilDao();
        List<User> employees;
        List<String> employeeWorkTypes;
        try {
            employees = dao.getAllEmployee();
            for (User employee: employees) {
                employeeWorkTypes = utilDao.takeEmployeeWorkType(employee.getLogin());
                employee.setEmployeeWorkTypeName(employeeWorkTypes);
            }

            if (employees == null || employees.size() == 0) {
                throw new ServiceException("No employees matching your query");
            }
        } catch (DaoException e) {
            throw new ServiceException("Error in source!", e);
        }
        return employees;
    }

    @Override
    public List<User> getAllEmployee(int offset, int recordsPerPage) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao dao = daoFactory.getUserDao();
        UtilDao utilDao = daoFactory.getUtilDao();
        List<User> employees;
        List<String> employeeWorkTypes;
        try {
            employees = dao.getAllEmployee(offset, recordsPerPage);
            for (User employee: employees) {
                employeeWorkTypes = utilDao.takeEmployeeWorkType(employee.getLogin());
                employee.setEmployeeWorkTypeName(employeeWorkTypes);
            }

            if (employees == null || employees.size() == 0) {
                throw new ServiceException("No employees matching your query");
            }
        } catch (DaoException e) {
            throw new ServiceException("Error in source!", e);
        }
        return employees;
    }

    @Override
    public List<User> getAllEmployee(int offset, int recordsPerPage, String workType) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao dao = daoFactory.getUserDao();
        UtilDao utilDao = daoFactory.getUtilDao();
        List<User> employees;
        List<String> employeeWorkTypes;
        try {
            int typeId = utilDao.takeWorkTypeIdByName(workType);
            employees = dao.getAllEmployee(offset, recordsPerPage, typeId);
            for (User employee: employees) {
                employeeWorkTypes = utilDao.takeEmployeeWorkType(employee.getLogin());
                employee.setEmployeeWorkTypeName(employeeWorkTypes);
            }

            if (employees == null || employees.size() == 0) {
                throw new ServiceException("No employees matching your query");
            }
        } catch (DaoException e) {
            throw new ServiceException("Error in source!", e);
        }
        return employees;
    }

    @Override
    public int allEmployeesCount() throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao dao = daoFactory.getUserDao();
        int amount;
        try {
            amount = dao.allEmployeesCount();
            if (amount == 0) {
                throw new ServiceException("No employees matching your query");
            }
        } catch (DaoException e) {
            throw new ServiceException("Error in source!", e);
        }
        return amount;
    }

    @Override
    public int allEmployeesCount(String workTypeName) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        UtilDao utilDao = daoFactory.getUtilDao();
        int amount;
        try {
            int workTypeId = utilDao.takeWorkTypeIdByName(workTypeName);
            amount = userDao.allEmployeesCount(workTypeId);
            if (amount == 0) {
                throw new ServiceException("No employees matching your query");
            }
        } catch (DaoException e) {
            throw new ServiceException("Error in source!", e);
        }
        return amount;
    }



    @Override
    public User getUserByLogin(String login) throws ServiceException, ServiceAuthorizationException {
        if(!Validator.validate(login)) {
            throw new ServiceAuthorizationException("Wrong login");
        }
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao dao = daoFactory.getUserDao();
        User user;
        try {
            user = dao.getUserByLogin(login);
            logger.info(user.toString());
        } catch (DaoException e) {
            throw new ServiceException("Error in source!", e);
        }
        return user;
    }
/*
    @Override
    public boolean deleteUser(String login) throws ServiceException, ServiceAuthorizationException {
        if(!Validator.validate(login)) {
            throw new ServiceAuthorizationException("Wrong login");
        }
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao dao = daoFactory.getUserDao();
        try {
            return dao.deleteUser(login);
        } catch (DaoException e) {
            throw new ServiceException("Error in source!", e);
        }
    }

 */

    public void updateBlockingEmployee(String login, boolean isBlocked) throws ServiceException, ServiceAuthorizationException {
        if(!Validator.validate(login)) {
            throw new ServiceAuthorizationException("Wrong login");
        }
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao dao = daoFactory.getUserDao();
        try {
            dao.updateEmployeeStatus(login, isBlocked);
            logger.log(Level.INFO,  isBlocked + "- Employee " + login + "is active: " + isBlocked);
        } catch (DaoException e) {
            throw new ServiceException("Failed update employee status", e);
        }
    }
/*
    @Override
    public List<String> getTenantInfo(String login) throws ServiceException, ServiceAuthorizationException {
        if(!Validator.validate(login)) {
            throw new ServiceAuthorizationException("Wrong login");
        }
        DaoFactory daoFactory = DaoFactory.getInstance();
        UtilDao utilDao = daoFactory.getUtilDao();
        List<String> result;
        try {
            result = utilDao.takeTenantInfo(login);
        } catch (DaoException e) {
            throw new ServiceException("Error in source!", e);
        }
        return result;
    }

 */
}
