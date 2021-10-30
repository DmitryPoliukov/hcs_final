package by.epamtc.poliukov.controller;

import by.epamtc.poliukov.comand.Command;
import by.epamtc.poliukov.comand.impl.admin.GoToUpdateRole;
import by.epamtc.poliukov.comand.impl.admin.UpdateRole;
import by.epamtc.poliukov.comand.impl.dispatcher.*;
import by.epamtc.poliukov.comand.impl.employee.*;
import by.epamtc.poliukov.comand.impl.guest.*;
import by.epamtc.poliukov.comand.impl.tenant.*;
import by.epamtc.poliukov.comand.impl.user.AddEmployee;
import by.epamtc.poliukov.comand.impl.user.AddTenant;
import by.epamtc.poliukov.comand.impl.user.GoToAddEmployee;
import by.epamtc.poliukov.comand.impl.user.GoToAddTenant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {

    private static final Logger logger = LogManager.getLogger();

    private Map<CommandList, Command> adminCommands = new HashMap<>();
    private Map<CommandList, Command> userCommands = new HashMap<>();
    private Map<CommandList, Command> guestCommands = new HashMap<>();
    private Map<CommandList, Command> tenantCommands = new HashMap<>();
    private Map<CommandList, Command> employeeCommands = new HashMap<>();
    private Map<CommandList, Command> dispatcherCommands = new HashMap<>();
    private Map<String, Map<CommandList, Command>> roleCommands = new HashMap<>();

    private static final String GUEST = "guest";
    private static final String USER = "user";
    private static final String TENANT = "tenant";
    private static final String EMPLOYEE = "employee";
    private static final String DISPATCHER = "dispatcher";
    private static final String ADMIN = "admin";

    private static final CommandProvider instance = new CommandProvider();

    private CommandProvider() {
        roleCommands.put(GUEST, guestCommands);
        roleCommands.put(ADMIN, adminCommands);
        roleCommands.put(TENANT, tenantCommands);
        roleCommands.put(EMPLOYEE, employeeCommands);
        roleCommands.put(DISPATCHER, dispatcherCommands);
        roleCommands.put(USER, userCommands);

        guestCommands.put(CommandList.LOGIN, new Login());
        guestCommands.put(CommandList.REGISTER, new Register());
        guestCommands.put(CommandList.LOG_OUT, new Logout());
        guestCommands.put(CommandList.ALL_EMPLOYEES, new AllEmployees());
        guestCommands.put(CommandList.EMPLOYEES_BY_TYPE, new EmployeesByType());
        guestCommands.put(CommandList.GO_TO_EMPLOYEE_BY_TYPE, new GoToEmployeeByType());
        guestCommands.put(CommandList.CHANGE_LANGUAGE, new ChangeLanguage());


        adminCommands.put(CommandList.LOG_OUT, new Logout());
        adminCommands.put(CommandList.VIEW_USER, new ViewUser());
        adminCommands.put(CommandList.GO_TO_UPDATE_ROLE, new GoToUpdateRole());
        adminCommands.put(CommandList.UPDATE_ROLE, new UpdateRole());
        adminCommands.put(CommandList.ALL_EMPLOYEES, new AllEmployees());
        adminCommands.put(CommandList.EMPLOYEES_BY_TYPE, new EmployeesByType());
        adminCommands.put(CommandList.GO_TO_EMPLOYEE_BY_TYPE, new GoToEmployeeByType());
        adminCommands.put(CommandList.CHANGE_LANGUAGE, new ChangeLanguage());


        userCommands.put(CommandList.VIEW_USER, new ViewUser());
        userCommands.put(CommandList.LOG_OUT, new Logout());
        userCommands.put(CommandList.GO_TO_ADD_TENANT, new GoToAddTenant());
        userCommands.put(CommandList.ADD_TENANT, new AddTenant());
        userCommands.put(CommandList.ALL_EMPLOYEES, new AllEmployees());
        userCommands.put(CommandList.GO_TO_EMPLOYEE_BY_TYPE, new GoToEmployeeByType());
        userCommands.put(CommandList.EMPLOYEES_BY_TYPE, new EmployeesByType());
        userCommands.put(CommandList.GO_TO_ADD_EMPLOYEE, new GoToAddEmployee());
        userCommands.put(CommandList.ADD_EMPLOYEE, new AddEmployee());
        userCommands.put(CommandList.CHANGE_LANGUAGE, new ChangeLanguage());



        tenantCommands.put(CommandList.VIEW_USER, new ViewUser());
        tenantCommands.put(CommandList.LOG_OUT, new Logout());
        tenantCommands.put(CommandList.GO_TO_ADD_TENANT, new GoToAddTenant());
        tenantCommands.put(CommandList.ADD_TENANT, new AddTenant());
        tenantCommands.put(CommandList.ALL_EMPLOYEES, new AllEmployees());
        tenantCommands.put(CommandList.GO_TO_EMPLOYEE_BY_TYPE, new GoToEmployeeByType());
        tenantCommands.put(CommandList.EMPLOYEES_BY_TYPE, new EmployeesByType());
        tenantCommands.put(CommandList.GO_TO_ADD_WORK_REQUEST, new GoToAddWorkRequest());
        tenantCommands.put(CommandList.ADD_WORK_REQUEST, new AddWorkRequest());
        tenantCommands.put(CommandList.GO_TO_ADD_SUBQUERY, new GoToAddSubquery());
        tenantCommands.put(CommandList.ADD_SUBQUERY, new AddSubquery());
        tenantCommands.put(CommandList.SHOW_ALL_TENANT_REQUESTS, new ShowAllTenantRequests());
        tenantCommands.put(CommandList.UPDATE_REQUEST_STATUS_TENANT, new UpdateRequestStatusTenant());
        tenantCommands.put(CommandList.CHANGE_LANGUAGE, new ChangeLanguage());



        employeeCommands.put(CommandList.VIEW_USER, new ViewUser());
        employeeCommands.put(CommandList.LOG_OUT, new Logout());
        employeeCommands.put(CommandList.ALL_EMPLOYEES, new AllEmployees());
        employeeCommands.put(CommandList.GO_TO_EMPLOYEE_BY_TYPE, new GoToEmployeeByType());
        employeeCommands.put(CommandList.EMPLOYEES_BY_TYPE, new EmployeesByType());
        employeeCommands.put(CommandList.GO_TO_ACTUAL_REQUESTS_EMPLOYEE, new GoToActualRequestsEmployee());
        employeeCommands.put(CommandList.ACTUAL_REQUESTS_EMPLOYEE, new ActualRequestsEmployee());
        employeeCommands.put(CommandList.ACTUAL_REQUESTS_ALL_TYPES, new ActualRequestsAllTypes());
        employeeCommands.put(CommandList.GO_TO_SHOW_WORK_PLAN, new GoToShowWorkPlan());
        employeeCommands.put(CommandList.SHOW_WORK_PLAN, new ShowWorkPlan());
        employeeCommands.put(CommandList.GO_TO_ADD_EMPLOYEE, new GoToAddEmployee());
        employeeCommands.put(CommandList.ADD_EMPLOYEE, new AddEmployee());
        employeeCommands.put(CommandList.CHANGE_LANGUAGE, new ChangeLanguage());


        dispatcherCommands.put(CommandList.LOGIN, new Login());
        dispatcherCommands.put(CommandList.LOG_OUT, new Logout());
        dispatcherCommands.put(CommandList.GO_TO_ALL_EMPLOYEE_WORK_PLAN, new GoToAllEmployeeWorkPlan());
        dispatcherCommands.put(CommandList.ALL_EMPLOYEE_WORK_PLAN_BY_TYPE, new AllEmployeeWorkPlanByType());
        dispatcherCommands.put(CommandList.ALL_EMPLOYEE_WORK_PLAN, new AllEmployeeWorkPlan());
        dispatcherCommands.put(CommandList.GO_TO_DISPATCHER_WORK_PLAN, new GoToDispatcherWorkPlan());
        dispatcherCommands.put(CommandList.DISPATCHER_WORK_PLAN, new DispatcherWorkPlan());
        dispatcherCommands.put(CommandList.GO_TO_ADD_REQUEST_TO_WORK_PLAN, new GoToAddRequestToWorkPlan());
        dispatcherCommands.put(CommandList.ADD_REQUEST_TO_WORK_PLAN, new AddRequestsToWorkPlan());
        dispatcherCommands.put(CommandList.GO_TO_ACTUAL_REQUESTS_EMPLOYEE, new GoToActualRequestsEmployee());
        dispatcherCommands.put(CommandList.ACTUAL_REQUESTS_EMPLOYEE, new ActualRequestsEmployee());
        dispatcherCommands.put(CommandList.ACTUAL_REQUESTS_ALL_TYPES, new ActualRequestsAllTypes());
        dispatcherCommands.put(CommandList.ALL_EMPLOYEES, new AllEmployees());
        dispatcherCommands.put(CommandList.GO_TO_EMPLOYEE_BY_TYPE, new GoToEmployeeByType());
        dispatcherCommands.put(CommandList.EMPLOYEES_BY_TYPE, new EmployeesByType());
        dispatcherCommands.put(CommandList.DISPATCHER_CLOSE_WORK_REQUEST, new DispatcherCloseWorkRequest());
        dispatcherCommands.put(CommandList.CHANGE_LANGUAGE, new ChangeLanguage());




    }

    static CommandProvider getInstance() {
        return instance;
    }

    Command getCommandForUser(String type, String commandName) {
        String cmd = commandName.replace("-", "_").toUpperCase();
        CommandList name;
        Command command = null;
        try {
            name = CommandList.valueOf(cmd);
            return roleCommands.get(type).get(name);
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, "No such command", e);
        }
        return command;
    }
}
