package by.epamtc.poliukov.service.impl;

import by.epamtc.poliukov.dao.DaoFactory;
import by.epamtc.poliukov.dao.UserDao;
import by.epamtc.poliukov.dao.UtilDao;
import by.epamtc.poliukov.entity.User;
import by.epamtc.poliukov.exception.*;
import by.epamtc.poliukov.service.PasswordEncryption;
import by.epamtc.poliukov.service.UserService;
import by.epamtc.poliukov.service.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;


public class UserServiceImpl implements UserService {
    private UserServiceImpl(){}
    private static final UserServiceImpl INSTANCE = new UserServiceImpl();
    public static UserServiceImpl getInstance() {
        return INSTANCE;
    }

    private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    public User createUser(String login, byte[] password,byte[] password2, String email, String name,
                           String secondName, String surName, String phone, String role) throws ServiceAuthorizationException, NotUniqueLoginEmailException {
        if (!Validator.validate(login, email) ||
                !Validator.validateLogin(login) ||
                !Validator.validatePassword(password, password2) ||
                !Validator.validateEmail(email) ||
                !Validator.validate(name, secondName, surName, phone, role)) {
            throw new ServiceAuthorizationException("Check input parameters");
        }
        try {
            boolean isUnique = isLoginEmailUnique(login, email);
            if (!isUnique) {
                throw new NotUniqueLoginEmailException("Login or email not unique");
            }
        } catch (ServiceException e) {
            throw new NotUniqueLoginEmailException("Login or email not unique");
        }

        String pass =  new String(password);
        String encodedPassword = PasswordEncryption.encrypt(pass);

        User user = new User();
        user.setLogin(login);
        user.setPassword(encodedPassword);
        user.setName(name);
        user.setSecondName(secondName);
        user.setSurname(surName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setRole(role);
        logger.log(Level.INFO, "User " + user.getLogin() + " created");

        return user;
    }

    @Override
    public User addUser(User user) throws ServiceException {
        boolean isAdded;
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
    public User addTenantInfo(String login, String city, String address) throws ServiceException, ServiceAuthorizationException {
        if (!Validator.validateLogin(login) ||
                !Validator.validate(city, address)) {
            throw new ServiceAuthorizationException("Check input parameters");
        }
        User user;
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao daoUser = daoFactory.getUserDao();
        UtilDao utilDao = daoFactory.getUtilDao();

        try {
            user = daoUser.getUserByLogin(login);
            utilDao.updateUserRole(login, 2); //roleId for tenant = 2
            daoUser.addTenantInfo(user.getUserId(), city, address);
            user = daoUser.getUserByLogin(login);
            logger.log(Level.INFO, "Tenant info was added for " + user.getLogin());
        } catch (DaoException e) {
            throw new ServiceException("Failed to add tenant info", e);
        }
        return user;
    }

    @Override
    public User authorise(String login, byte[] password) throws ServiceException, ServiceAuthorizationException {
        User user;
        if (!Validator.validateLogin(login)
                || !Validator.validatePassword(password)) {
            throw new ServiceAuthorizationException("Wrong login or password");
        }
        String pass =  new String(password);
        user = getUserByLogin(login);
        if (user == null || !PasswordEncryption.isMatch(pass , user.getPassword())) {
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
            if (employees.size() == 0) {
                throw new ServiceException("No employees matching your query");
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception in getAllEmployee", e);
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
            if (employees.size() == 0) {
                throw new ServiceException("No employees matching your query");
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception in getAllEmployee", e);
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

            if (employees.size() == 0) {
                throw new ServiceException("No employees matching your query");
            }
            logger.log(Level.INFO,
                    "All employees: " + employees.toString() + "for work type - " + workType);
        } catch (DaoException e) {
            throw new ServiceException("Exception in getAllEmployee_paginationType", e);
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
            throw new ServiceException("Exception in allEmployeesCount", e);
        }
        logger.log(Level.INFO, "allEmployeesCount = " + amount);
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
            throw new ServiceException("Exception in allEmployeesCount", e);
        }
        logger.log(Level.INFO, "allEmployeesCount = " + amount + " for work type " + workTypeName);

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
            logger.log(Level.INFO, "Get user by login");
        } catch (DaoException e) {
            throw new ServiceException("Failed to get user by login", e);
        }
        return user;
    }

    @Override
    public User getUserByUserId(int userId) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao dao = daoFactory.getUserDao();
        User user;
        try {
            user = dao.getUserByUserId(userId);
            logger.log(Level.INFO, "Get user " + user.getLogin());
        } catch (DaoException e) {
            throw new ServiceException("Failed to get user by user id", e);
        }
        return user;
    }

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

    @Override
    public boolean updateUserRole(String login, String roleName) throws ServiceException, ServiceAuthorizationException {
        if(!Validator.validate(login)) {
            throw new ServiceAuthorizationException("Wrong login");
        }
        boolean isUpdate;
        UtilDao utilDao = DaoFactory.getInstance().getUtilDao();
        int roleId;
        try {
            roleId = Integer.parseInt(utilDao.takeRoleIdByRoleName(roleName));
            isUpdate = utilDao.updateUserRole(login, roleId);
        } catch (DaoException e) {
            throw new ServiceException("Failed update user role", e);
        }
        logger.log(Level.INFO,  "user " + login + " updated as a " + roleName);
        return isUpdate;

    }
    private boolean isLoginEmailUnique(String login, String email) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao dao = daoFactory.getUserDao();
        boolean isUnique;
        try {
            isUnique = dao.isLoginEmailUnique(login, email);
        } catch (DaoException e) {
            throw new ServiceException("Login or email is not unique", e);
        }
        return isUnique;
    }

    public List<String> getTenantInfo(int userId) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao dao = daoFactory.getUserDao();
        List<String> tenantInfo;
        try {
            tenantInfo = dao.getTenantInfo(userId);
        } catch (DaoException e) {
            throw new ServiceException("Failed to get tenant info", e);
        }
        return tenantInfo;
    }

    @Override
    public boolean addEmployeeWorkType(int employeeId, String[] employeeWorkTypeName) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao dao = daoFactory.getUserDao();
        UtilDao utilDao = daoFactory.getUtilDao();
        int workTypeId;
        try {
            for (String workTypeName : employeeWorkTypeName) {
                workTypeId = utilDao.takeWorkTypeIdByName(workTypeName);
                dao.addEmployeeWorkType(employeeId, workTypeId);
                logger.log(Level.INFO, "Employee work types were added for " + employeeId);
            }
            return true;
        } catch (DaoException e) {
           throw new ServiceException("Failed to add employee work type", e);
        }
    }

    @Override
    public User addEmployeeInfo(int employeeId, int valuePersonHour, String information) throws ServiceException, IncorrectDateException {
        if (!Validator.validate(information) ||
                !Validator.validate(valuePersonHour)) {
            throw new IncorrectDateException("Check input parameters");
        }
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao dao = daoFactory.getUserDao();
        UtilDao utilDao = daoFactory.getUtilDao();
        User user;
        try {
            dao.addEmployeeInfo(employeeId, valuePersonHour, information);
            user = dao.getUserByUserId(employeeId);
            utilDao.updateUserRole(user.getLogin(), 3); //roleId for employee = 3;
            user = dao.getUserByUserId(employeeId);
            logger.log(Level.INFO, "Employee info was added for " + user.getLogin());
        } catch (DaoException e) {
            throw new ServiceException("Failed to add employee information", e);
        }
        return user;
    }

    @Override
    public String takeMainInformation() throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UtilDao utilDao = daoFactory.getUtilDao();
        String information;
        try {
            information = utilDao.takeMainInformation();
            logger.log(Level.INFO, "Take main information");
        } catch (DaoException e) {
            throw new ServiceException("Failed to take main information", e);
        }
        return information;
    }
}
