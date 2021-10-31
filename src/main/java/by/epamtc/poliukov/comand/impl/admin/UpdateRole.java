package by.epamtc.poliukov.comand.impl.admin;

import by.epamtc.poliukov.comand.Command;
import by.epamtc.poliukov.comand.impl.tenant.AddSubquery;
import by.epamtc.poliukov.entity.Subquery;
import by.epamtc.poliukov.entity.User;
import by.epamtc.poliukov.entity.WorkRequest;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UpdateRole implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/admin/updateRole.jsp";
    private static final Logger logger = LogManager.getLogger(UpdateRole.class);
    private static final String ERROR_PAGE = "WEB-INF/jsp/error.jsp";
    private static final String LOGIN = "login";
    private static final String ROLE = "role";
    private static final String ERROR = "errorMessage";
    private static final String MESSAGE_OF_ERROR = "Role not updated";
    private static final String MESSAGE_OF_ERROR_1 = "Illegal login";
    private static final String SUCCESS = "successMessage";
    private static final String MESSAGE_OF_SUCCESS = "role updated to";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        HttpSession session = request.getSession(true);
        String login = request.getParameter(LOGIN);
        String newRole = request.getParameter(ROLE);
        try {
            userService.updateUserRole(login, newRole);
            request.setAttribute(SUCCESS, MESSAGE_OF_SUCCESS);
            session.setAttribute(LOGIN, login);
            session.setAttribute(ROLE, newRole);
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
            request.setAttribute(ERROR, MESSAGE_OF_ERROR);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        } catch (ServiceAuthorizationException e) {
            logger.log(Level.INFO, " authorization error");
            request.setAttribute(ERROR, MESSAGE_OF_ERROR_1);
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        }
    }
}
