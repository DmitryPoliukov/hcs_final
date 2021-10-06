package by.epamtc.poliukov.dao;

import by.epamtc.poliukov.exception.DaoException;
import by.epamtc.poliukov.entity.Employee;
import by.epamtc.poliukov.entity.Tenant;
import by.epamtc.poliukov.entity.User;

import java.util.List;

public interface UserDao {

    boolean addUser(User user) throws DaoException;

    User authorise(String login, String password) throws DaoException;

    List<Tenant> getAllTenants() throws DaoException;

    List<Employee> getAllEmployee() throws DaoException;

    User getUserByLogin(String login) throws DaoException;

    boolean deleteUser(String login) throws DaoException;

    boolean updateEmployeeStatus(String login, boolean is_Blocked) throws DaoException;

    boolean addTenant(int userId, String city, String address) throws DaoException;
















}
