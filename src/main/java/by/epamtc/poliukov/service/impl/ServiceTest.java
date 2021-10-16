package by.epamtc.poliukov.service.impl;

import by.epamtc.poliukov.dao.pool.ConnectionPool;
import by.epamtc.poliukov.entity.*;
import by.epamtc.poliukov.exception.ConnectionPoolException;
import by.epamtc.poliukov.exception.ServiceAuthorizationException;
import by.epamtc.poliukov.exception.ServiceException;
import by.epamtc.poliukov.service.UserService;
import by.epamtc.poliukov.service.WorkRequestService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceTest {
    public static void main(String[] args) {
        ConnectionPool connectionPool = new ConnectionPool();
        try {
            connectionPool.init();
        } catch (ConnectionPoolException e) {
            System.out.println("connection");
        }


        WorkRequestService workRequestService = new WorkRequestServiceImpl();
        UserService userService = new UserServiceImpl();
        /*
        WorkRequest wrk = new WorkRequest();

        wrk.setFillingDate("29.09.2021");
        wrk.setPlannedDate("30.09.2021");
        wrk.setTenantUserId(9);
        try {
            boolean res = workRequestService.addWorkRequest(wrk);
        } catch (ServiceException e) {
            System.out.println("service no");
        }

         */
        /*
        Subquery sub = new Subquery();
        sub.setAmountOfWorkInHours(1);
        sub.setWorkType("Малярные работы");

        try {
            System.out.println(workRequestService.addSubqueries(sub, 7));
        } catch (ServiceException e) {
            System.out.println("service no");
        }

         */
        /*
        try {
            List<WorkRequest> list = workRequestService.getAllRequestForTenantByLogin("user10_vasya");
            System.out.println(list.toString());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        */
        /*
        try {
            List<WorkRequest> list = workRequestService.getNewRequestsForOneWorkType(3);
            System.out.println(list.toString());
        } catch (ServiceException e) {
            e.printStackTrace();
        }

         */
/*
        try {
            boolean isUpdate = workRequestService.updateWorkRequestStatus(8, "CLOSE");
            System.out.println(isUpdate);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

 */
        //____________________________________________________________ userService
        /*
        List<Employee> employees;
        try {
            employees = userService.getAllEmployee();
            System.out.println(employees.toString());
        } catch (ServiceException e) {
            e.printStackTrace();
        }

         */
        /*
        try {
            User user = userService.getUserByLogin("user10_vasya");
            System.out.println(user);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (ServiceAuthorizationException e) {
            e.printStackTrace();
        }

         *//*
        List<Tenant> tenants = null;
        try {
            tenants = userService.getAllTenants();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        System.out.println(tenants.toString());\
        */
/*
        User user10 = new User();
        user10.setLogin("levwa");
        user10.setPassword("123123");
        user10.setSurname("Ali");
        user10.setSecondName("vasi4");
        user10.setEmail("garudapw@gmail.com");
        user10.setPhone("331231212");
        user10.setName("Vasya");
        user10.setRole("tenant");
        try {
            userService.addUser(user10);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (ServiceAuthorizationException e) {
            e.printStackTrace();
        }

 */
/*
        try {
            List<User> userList = userService.getAllEmployee(0, 5);
            System.out.println(userList.toString());
        } catch (ServiceException e) {
            e.printStackTrace();
        }

 */
        Boolean isUpdate = null;
        try {
            isUpdate = workRequestService.updateWorkRequestStatus(42, "close");
        } catch (ServiceException e) {
            System.out.println("no");
        }
        System.out.println(isUpdate);

/*

        int page = 1;
        List<User> employees;

        try {
            employees = userService.getAllEmployee((page - 1) * 5, 5);
            int numberOfParticipants = userService.allEmployeesCount();
            int noOfPages = (int) Math.ceil(numberOfParticipants * 1.0 / 5);
            System.out.println(employees.toString());
        } catch (ServiceException e) {
            System.out.println("no1");
        }

 */
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.M.yyyy");
        String date = formatForDateNow.format(dateNow);
        System.out.println("Текущая дата " + date);











       /*


        try {
           User res = userService.authorise("garudapw", "123123".getBytes());
            System.out.println(res);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (ServiceAuthorizationException e) {
            e.printStackTrace();
        }

        */


        /*
        boolean res = false;
        try {
            res = userService.deleteUser("garudapw");
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (ServiceAuthorizationException e) {
            e.printStackTrace();
        }
        System.out.println(res);

         */
        /*
        try {
            userService.updateBlockingEmployee("user6_employee", false);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (ServiceAuthorizationException e) {
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
