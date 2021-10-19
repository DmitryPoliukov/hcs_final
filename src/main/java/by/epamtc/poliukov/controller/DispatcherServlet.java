package by.epamtc.poliukov.controller;

import by.epamtc.poliukov.comand.Command;
import by.epamtc.poliukov.entity.User;
import by.epamtc.poliukov.exception.ServiceException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

public class DispatcherServlet extends HttpServlet implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(DispatcherServlet.class);

    private static final String ERROR_PAGE = "errorPage";

    private static final String COMMAND = "command";
    private static final String USER = "user";
    private static final String GUEST = "guest";
    private static final String ERROR = "errorMessage";
    private static final String MESSAGE_OF_ERROR = "You don't have permission to do that.";
    private static final String MESSAGE_OF_ERROR_2 = "Something went wrong";

    public DispatcherServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String commandString;
        if (ServletFileUpload.isMultipartContent(request)) {
            commandString = String.valueOf(CommandList.UPLOAD_PHOTO);
        } else {
            commandString = request.getParameter(COMMAND);
        }
        logger.log(Level.INFO, "DispatcherServlet processRequest() - commandName = {}", commandString);
        if (commandString != null && !commandString.isEmpty()) {
            try {


                User user = (User) request.getSession(false).getAttribute(USER);
                String role;
                if (user != null) {
                    role = String.valueOf(user.getRole());
                } else {
                    role = GUEST;
                }
                Command command;
                command = CommandProvider.getInstance().getCommandForUser(role, commandString);
                if (command == null) {
                    logger.log(Level.ERROR, "Access without permission from client");
                    request.setAttribute(ERROR, MESSAGE_OF_ERROR);
                    request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
                    logger.info("command null dispatcher servlet");
                } else {
                    command.execute(request, response);
                }
            } catch (IllegalArgumentException | ServiceException ex) {

                logger.log(Level.ERROR, "404 error, client requests a nonexistent command", ex);
                request.setAttribute(ERROR, MESSAGE_OF_ERROR_2);
                request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
                logger.info("Error in dispatcher servlet");
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            logger.log(Level.ERROR, "No such command");
            request.setAttribute(ERROR, MESSAGE_OF_ERROR_2);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            logger.info("Error in no such command");
        }
    }

}
