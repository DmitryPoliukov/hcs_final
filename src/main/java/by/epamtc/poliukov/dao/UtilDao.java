package by.epamtc.poliukov.dao;

import by.epamtc.poliukov.exception.DaoException;

import java.util.List;

public interface UtilDao {

    String takeRoleIdByRoleName(String role) throws DaoException;

    String takeRoleNameByRoleId(int roleId) throws DaoException;

    Boolean takeEmployeeStatus(String login) throws DaoException;

    List<String> takeEmployeeWorkType(String login) throws DaoException;

    Integer takeWorkTypeIdByName(String workTypeName) throws DaoException;

    Integer takeRequestStatusIdByStatusName(String statusName) throws DaoException;

    boolean updateUserRole(String login, Integer roleId) throws DaoException;
}
