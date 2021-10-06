package by.epamtc.poliukov.service;

import by.epamtc.poliukov.entity.Employee;
import by.epamtc.poliukov.entity.Tenant;
import by.epamtc.poliukov.entity.User;
import by.epamtc.poliukov.exception.ServiceAuthorizationException;
import by.epamtc.poliukov.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    User create(HttpServletRequest request) throws ServiceAuthorizationException;

    User addUser(User user) throws ServiceException, ServiceAuthorizationException;

    User addTenant(String login, String city, String address) throws ServiceException;

    User authorise(String login, byte[] password) throws ServiceException, ServiceAuthorizationException;

    List<Tenant> getAllTenants() throws ServiceException;

    List<Employee> getAllEmployee() throws ServiceException;

    User getUserByLogin(String login) throws ServiceException, ServiceAuthorizationException;

    boolean deleteUser(String login) throws ServiceException, ServiceAuthorizationException;

    void updateBlockingEmployee(String login, boolean isBlocked) throws ServiceException, ServiceAuthorizationException;

}
