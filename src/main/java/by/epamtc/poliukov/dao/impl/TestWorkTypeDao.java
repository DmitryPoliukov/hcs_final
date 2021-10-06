package by.epamtc.poliukov.dao.impl;

import by.epamtc.poliukov.dao.DaoFactory;
import by.epamtc.poliukov.dao.UtilDao;
import by.epamtc.poliukov.dao.WorkRequestDao;
import by.epamtc.poliukov.dao.pool.ConnectionPool;
import by.epamtc.poliukov.entity.Subquery;
import by.epamtc.poliukov.entity.WorkRequest;
import by.epamtc.poliukov.exception.ConnectionPoolException;
import by.epamtc.poliukov.exception.DaoException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestWorkTypeDao {
    public static void main(String[] args) {
        ConnectionPool connectionPool = new ConnectionPool();
        try {
            connectionPool.init();
        } catch (ConnectionPoolException e) {
            System.out.println("connection");
        }
        DaoFactory daoFactory = DaoFactory.getInstance();
        WorkRequestDao wrd = daoFactory.getWorkRequestDao();
        UtilDao utilDao = daoFactory.getUtilDao();
/*
        WorkRequest wrk = new WorkRequest();
        String fillDate = "11.08.2021";
        String planned = "12.08.2021";

        wrk.setFillingDate(fillDate);
        wrk.setPlannedDate(planned);
        wrk.setTenantUserId(3);
        try {

            System.out.println(wrd.addWorkRequest(wrk));
        } catch (DaoException e) {
            System.out.println("рекверст не добавлен");
        }

 */

/*
        Subquery subquery = new Subquery();
        int mainRequestID = 2;
        subquery.setAmountOfWorkInHours(4);
        subquery.setInformation("уточнение по подзапросу: 38м3");
        subquery.setWorkType("1");
        try {
            System.out.println(wrd.addSubqueries(subquery, mainRequestID));
        } catch (DaoException e) {
            System.out.println("подзапрос не добавлен");
        }

 */
        /*
        try {
            List<WorkRequest> workRequestListForTenant = wrd.getAllRequestForTenantByLogin("user3_tenant");
            System.out.println(workRequestListForTenant.toString());
        } catch (DaoException e) {
            System.out.println("no");
        }

         */
/*
        int wt = 0;
        try {
            wt = utilDao.takeWorkTypeIdByName("Бетонные работы");
        } catch (DaoException e) {
            System.out.println("no");
        }
        System.out.println(wt);

 */
        /*
        List<WorkRequest> workRequestList = null;
        try {
            workRequestList = wrd.getNewRequestsForOneWorkType(3);
            System.out.println(workRequestList.toString());
        } catch (DaoException e) {
            System.out.println("dao");
        }

         */
/*
        try {
            boolean isUpdateStatus = wrd.updateWorkRequestStatus(8, "2");
        } catch (DaoException e) {
            System.out.println("no");
        }

 */
        int id = 0;
        try {
            id = utilDao.takeRequestStatusIdByStatusName("CLOSE");
        } catch (DaoException e) {
            e.printStackTrace();
        }
        System.out.println(id);


        try {
            connectionPool.destroy();
        } catch (ConnectionPoolException e) {
            System.out.println("Неудачный дестрой");
        }
    }
}
