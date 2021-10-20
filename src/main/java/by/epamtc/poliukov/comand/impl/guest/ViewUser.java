package by.epamtc.poliukov.comand.impl.guest;

import by.epamtc.poliukov.comand.Command;
import by.epamtc.poliukov.comand.CommandHelper;
import by.epamtc.poliukov.entity.User;
import by.epamtc.poliukov.exception.ServiceAuthorizationException;
import by.epamtc.poliukov.exception.ServiceException;
import by.epamtc.poliukov.service.ServiceFactory;
import by.epamtc.poliukov.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewUser implements Command {
    private static final Logger logger = LogManager.getLogger(ViewUser.class);
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/user/userPage.jsp";
    private static final String ERROR_PAGE = "WEB-INF/jsp/error.jsp";
    private static final String LOGIN = "username";
    private static final String REQUEST_ATTRIBUTE = "user";
    private static final String ERROR = "errorMessage";
    private static final String MESSAGE_OF_ERROR = "No user with such nickname";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        CommandHelper.saveCurrentQueryToSession(request);
        String login = request.getParameter(LOGIN);
        User user;
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            user = userService.getUserByLogin(login);
            request.setAttribute(REQUEST_ATTRIBUTE, user);
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        } catch (ServiceException | ServiceAuthorizationException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
            request.setAttribute(ERROR, MESSAGE_OF_ERROR);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
