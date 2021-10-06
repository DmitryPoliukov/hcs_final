package by.epamtc.poliukov.controller;

import by.epamtc.poliukov.comand.Command;
import by.epamtc.poliukov.comand.impl.guest.Login;
import by.epamtc.poliukov.comand.impl.guest.Logout;
import by.epamtc.poliukov.comand.impl.guest.Register;
import by.epamtc.poliukov.comand.impl.guest.ViewUser;
import by.epamtc.poliukov.comand.impl.user.AddTenant;
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



        userCommands.put(CommandList.LOGIN, new Login());
        userCommands.put(CommandList.VIEW_USER, new ViewUser());
        userCommands.put(CommandList.LOG_OUT, new Logout());
        userCommands.put(CommandList.ADD_TENANT, new AddTenant());


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
