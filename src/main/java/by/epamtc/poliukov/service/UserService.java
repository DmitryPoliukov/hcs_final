package by.epamtc.poliukov.service;

import by.epamtc.poliukov.entity.User;
import by.epamtc.poliukov.exception.DaoException;
import by.epamtc.poliukov.exception.NotUniqueLoginEmailException;
import by.epamtc.poliukov.exception.ServiceAuthorizationException;
import by.epamtc.poliukov.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    User createUser(HttpServletRequest request) throws ServiceAuthorizationException, NotUniqueLoginEmailException;

    User addUser(User user) throws ServiceException, ServiceAuthorizationException;

    User authorise(String login, byte[] password) throws ServiceException, ServiceAuthorizationException;

    User getUserByLogin(String login) throws ServiceException, ServiceAuthorizationException;

    User getUserByUserId(int userId) throws ServiceException;

    void updateBlockingEmployee(String login, boolean isBlocked) throws ServiceException, ServiceAuthorizationException;

    User addTenantInfo(String login, String city, String address) throws ServiceException, ServiceAuthorizationException;

    //  List<User> getAllTenants() throws ServiceException;

    List<User> getAllEmployee() throws ServiceException;

    List<User> getAllEmployee(int offset, int recordsPerPage) throws ServiceException;

    int allEmployeesCount() throws ServiceException;

    int allEmployeesCount(String workType) throws ServiceException;

    List<User> getAllEmployee(int offset, int recordsPerPage, String workType) throws ServiceException;

    boolean updateUserRole(String login, String role) throws ServiceException, ServiceAuthorizationException;

    List<String> getTenantInfo(int userId) throws ServiceException;

    boolean addEmployeeWorkType(int employeeId, String[] employeeWorkTypeName) throws ServiceException;

    User addEmployeeInfo(int userId, int valuePersonHour, String information) throws ServiceException;

    String takeMainInformation() throws ServiceException;
}
