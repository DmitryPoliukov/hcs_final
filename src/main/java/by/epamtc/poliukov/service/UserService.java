package by.epamtc.poliukov.service;

import by.epamtc.poliukov.entity.User;
import by.epamtc.poliukov.exception.ServiceAuthorizationException;
import by.epamtc.poliukov.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    User create(HttpServletRequest request) throws ServiceAuthorizationException;

    User addUser(User user) throws ServiceException, ServiceAuthorizationException;

    User authorise(String login, byte[] password) throws ServiceException, ServiceAuthorizationException;

    User getUserByLogin(String login) throws ServiceException, ServiceAuthorizationException;

    void updateBlockingEmployee(String login, boolean isBlocked) throws ServiceException, ServiceAuthorizationException;

    //List<String> getTenantInfo(String login) throws ServiceException, ServiceAuthorizationException;

    User addTenantInfo(String login, String city, String address) throws ServiceException;

    //  List<User> getAllTenants() throws ServiceException;

    List<User> getAllEmployee() throws ServiceException;

    List<User> getAllEmployee(int offset, int recordsPerPage) throws ServiceException;

    int allEmployeesCount() throws ServiceException;

    List<User> getAllEmployee(int offset, int recordsPerPage, String workType) throws ServiceException;

    // boolean deleteUser(String login) throws ServiceException, ServiceAuthorizationException;

}
