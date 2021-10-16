package by.epamtc.poliukov.controller;

import by.epamtc.poliukov.comand.Command;
import by.epamtc.poliukov.comand.impl.guest.*;
import by.epamtc.poliukov.comand.impl.tenant.*;
import by.epamtc.poliukov.comand.impl.user.AddTenant;
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

    private static final String GUEST = "guest";
    private static final String USER = "user";
    private static final String TENANT = "tenant";
    private static final String EMPLOYEE = "employee";
    private static final String DISPATCHER = "dispatcher";
    private static final String ADMIN = "admin";

    private static final CommandProvider instance = new CommandProvider();

    private CommandProvider() {

        guestCommands.put(CommandList.LOGIN, new Login());
        guestCommands.put(CommandList.REGISTER, new Register());
        guestCommands.put(CommandList.LOG_OUT, new Logout());
        guestCommands.put(CommandList.ALL_EMPLOYEES, new AllEmployees());
        guestCommands.put(CommandList.EMPLOYEES_BY_TYPE, new EmployeesByType());
        guestCommands.put(CommandList.GO_TO_EMPLOYEE_BY_TYPE, new GoToEmployeeByType());



        userCommands.put(CommandList.LOGIN, new Login());
        userCommands.put(CommandList.VIEW_USER, new ViewUser());
        userCommands.put(CommandList.LOG_OUT, new Logout());
        userCommands.put(CommandList.GO_TO_ADD_TENANT, new GoToAddTenant());
        userCommands.put(CommandList.ADD_TENANT, new AddTenant());
        userCommands.put(CommandList.ALL_EMPLOYEES, new AllEmployees());
        userCommands.put(CommandList.GO_TO_EMPLOYEE_BY_TYPE, new GoToEmployeeByType());
        userCommands.put(CommandList.EMPLOYEES_BY_TYPE, new EmployeesByType());


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


    }

    static CommandProvider getInstance() {
        return instance;
    }


    Command getCommandForUser(String type, String commandName) {
        String cmd = commandName.replace("-", "_").toUpperCase();
        CommandList name = null;
        Command command = null;
        try {
            name = CommandList.valueOf(cmd);
            switch (type) {
                case GUEST:
                    return guestCommands.get(name);
                case USER:
                    return userCommands.get(name);
                case ADMIN:
                    return adminCommands.get(name);
                case TENANT:
                    return tenantCommands.get(name);
                case EMPLOYEE:
                    return employeeCommands.get(name);
                case DISPATCHER:
                    return dispatcherCommands.get(name);
                default:
                    return guestCommands.get(name);
            }
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, "No such command", e);
        }
        return command;
    }
}
