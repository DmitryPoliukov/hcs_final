package by.epamtc.poliukov.dao;

import by.epamtc.poliukov.exception.DaoException;
import by.epamtc.poliukov.entity.User;

import java.util.List;

public interface UserDao {

    boolean addUser(User user) throws DaoException; //голый юзер

    User authorise(String login, String password) throws DaoException; //голый юзер

    List<User> getAllTenants() throws DaoException; //ок

    List<User> getAllEmployee() throws DaoException; // ок

    List<User> getAllEmployee(int offset, int noOfRecords) throws DaoException;

    public List<User> getAllEmployee(int offset, int noOfRecords, int typeID) throws DaoException;

    public int allEmployeesCount() throws DaoException;

    User getUserByLogin(String login) throws DaoException; // голый юзер

    boolean deleteUser(String login) throws DaoException; // голый юзер

    boolean updateEmployeeStatus(String login, boolean is_Blocked) throws DaoException;

    boolean addTenantInfo(int userId, String city, String address) throws DaoException;

    List<String> getTenantInfo(int userId) throws DaoException;

    boolean addEmployeeInfo(int userId, int value_person_hour, String information) throws DaoException;

    List<String> getEmployeeInfo(int userId) throws DaoException;















}
