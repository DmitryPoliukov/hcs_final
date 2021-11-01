package by.epamtc.poliukov.service.impl;

import by.epamtc.poliukov.dao.DaoFactory;
import by.epamtc.poliukov.dao.pool.ConnectionPool;
import by.epamtc.poliukov.entity.User;
import by.epamtc.poliukov.exception.ConnectionPoolException;
import org.junit.Test;
import org.testng.annotations.BeforeMethod;

import static org.junit.Assert.*;

public class UserServiceImplTest {
    @BeforeMethod
    public void takeConnection() {
        DaoFactory factory = null;
        ConnectionPool pool = null;
        try {
            factory = DaoFactory.getInstance();
            pool = factory.getConnectionPool();
            pool.init();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addUser() {

    }

    @Test
    public void addTenantInfo() {
    }

    @Test
    public void authorise() {
    }

    @Test
    public void getAllEmployee() {
    }

    @Test
    public void testGetAllEmployee() {
    }

    @Test
    public void testGetAllEmployee1() {
    }

    @Test
    public void allEmployeesCount() {
    }

    @Test
    public void testAllEmployeesCount() {
    }

    @Test
    public void getUserByLogin() {
    }

    @Test
    public void getUserByUserId() {
    }

    @Test
    public void updateBlockingEmployee() {
    }

    @Test
    public void updateUserRole() {
    }

    @Test
    public void getTenantInfo() {
    }

    @Test
    public void addEmployeeWorkType() {
    }

    @Test
    public void addEmployeeInfo() {
    }

    @Test
    public void takeMainInformation() {
    }
}