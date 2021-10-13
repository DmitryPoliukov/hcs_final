package by.epamtc.poliukov.dao.impl;

import by.epamtc.poliukov.dao.DaoFactory;
import by.epamtc.poliukov.dao.UserDao;
import by.epamtc.poliukov.dao.UtilDao;
import by.epamtc.poliukov.entity.User;
import by.epamtc.poliukov.exception.ConnectionPoolException;
import by.epamtc.poliukov.dao.pool.ConnectionPool;
import by.epamtc.poliukov.exception.DaoException;

import java.util.List;

public class TestUserDao {
    public static void main(String[] args){
        ConnectionPool connectionPool = new ConnectionPool();
        try {
            connectionPool.init();
        } catch (ConnectionPoolException e) {
            System.out.println("connection");
        }
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao dao = daoFactory.getUserDao();
        UtilDao utilDao = daoFactory.getUtilDao();

/*
        User user10 = new User();
        user10.setLogin("uer");
        user10.setPassword("iestvasya");
        user10.setSurname("Ali");
        user10.setEmail("vasya@gmail.com");
        user10.setPhone("331231212");
        user10.setName("Vasya");
        user10.setRole("1");

        try {
            dao.addUser(user10);
        } catch (DaoException e) {
            System.out.println("не вышло");
        }

 */
        /*
        try {
            List<String> list = utilDao.takeTenantInfo(29);
            System.out.println(list.toString());
        } catch (DaoException e) {
            e.printStackTrace();
        }

         */
/*
        try {
            List<User> userList = dao.getAllEmployee(0, 5, 1);
            System.out.println(userList);
        } catch (DaoException e) {
            System.out.println("no");
        }

 */








/*
        List<Tenant> tenantList = null;
        try {
            tenantList = dao.getAllTenants();
        } catch (DaoException e) {
            System.out.println("выборка тенант ерор");
        }
        System.out.println(tenantList);
*/

/*
        List<Employee> employeeList = null;
            try {
                employeeList = dao.getAllEmployee();
            } catch (DaoException e) {
                System.out.println("выборка рабочих ерор");
            }
            System.out.println(employeeList);

 */



/*

        try {
            User newUser = dao.authorise("user7_tenant", "1121211user1");
            System.out.println(newUser);
        } catch (DaoException e) {
            System.out.println("авторизация ерор");
        }

 */


/*

        try {
            boolean deleteRes = userDao.deleteUser("user2_tenant");
            System.out.println(deleteRes);
        } catch (DaoException e) {
            System.out.println("delete error");
        }
*/
/*

        User getByLoginUser = null;
        try {
            getByLoginUser = dao.getUserByLogin("user3_tenant");
        } catch (DaoException e) {
            e.printStackTrace();
        }
        System.out.println(getByLoginUser);

 */



/*
        Boolean isUpdate = false;
        try {
            isUpdate = dao.updateEmployeeStatus("user6_employee", false);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        System.out.println(isUpdate);

 */
        /*
        try {
            List<String> employeeWT = utilDao.takeEmployeeWorkType("user6_employee");
            for(String wt: employeeWT) {
                System.out.println(wt);
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        */


        try {
            connectionPool.destroy();
        } catch (ConnectionPoolException e) {
            System.out.println("Неудачный дестрой");
        }




    }
}
