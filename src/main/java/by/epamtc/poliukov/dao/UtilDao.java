package by.epamtc.poliukov.dao;

import by.epamtc.poliukov.entity.WorkRequest;
import by.epamtc.poliukov.exception.DaoException;

import java.util.List;

public interface UtilDao {

    String takeRoleIdByRoleName(String role) throws DaoException;

    List<String> takeEmployeeWorkType(String login) throws DaoException;

    Integer takeWorkTypeIdByName(String workTypeName) throws DaoException;

    String takeWorkTypeName(int workTypeId) throws DaoException;

    Integer takeRequestStatusIdByStatusName(String statusName) throws DaoException;

    String takeRequestStatusNameByStatusId(int statusId) throws DaoException;

    boolean updateUserRole(String login, Integer roleId) throws DaoException;

    WorkRequest takeWorkRequestByFillingDateUserId(String fillingDate, int tenantId) throws DaoException;

    String takeRoleNameByRoleId(int roleId) throws DaoException;
    Boolean takeEmployeeStatus(String login) throws DaoException;

}
