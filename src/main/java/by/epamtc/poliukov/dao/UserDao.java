package by.epamtc.poliukov.dao;

import by.epamtc.poliukov.exception.DaoException;
import by.epamtc.poliukov.entity.User;

import java.util.List;

public interface UserDao {

    boolean addUser(User user) throws DaoException;

    User authorise(String login, String password) throws DaoException;

    List<User> getAllEmployee() throws DaoException;

    List<User> getAllEmployee(int offset, int noOfRecords) throws DaoException;

    List<User> getAllEmployee(int offset, int noOfRecords, int typeID) throws DaoException;

    int allEmployeesCount() throws DaoException;

    int allEmployeesCount(int workTypeId) throws DaoException;

    User getUserByLogin(String login) throws DaoException;

    User getUserByUserId(int userId) throws DaoException;

    boolean updateEmployeeStatus(String login, boolean is_Blocked) throws DaoException;

    boolean addTenantInfo(int userId, String city, String address) throws DaoException;

    boolean isLoginEmailUnique(String login, String email) throws DaoException;

    boolean addEmployeeInfo(int userId, int value_person_hour, String information) throws DaoException;

    List<String> getTenantInfo(int userId) throws DaoException;

    List<User> getAllTenants() throws DaoException;















}
