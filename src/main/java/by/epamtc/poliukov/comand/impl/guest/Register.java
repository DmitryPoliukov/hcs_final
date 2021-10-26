package by.epamtc.poliukov.comand.impl.guest;

import by.epamtc.poliukov.comand.Command;
import by.epamtc.poliukov.comand.CommandHelper;
import by.epamtc.poliukov.entity.User;
import by.epamtc.poliukov.exception.NotUniqueLoginEmailException;
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


public class Register implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/guest/register.jsp";
    private static final String JSP_ADD_EMPLOYEE_INFO = "WEB-INF/jsp/user/addEmployee.jsp";
    private static final Logger logger = LogManager.getLogger(Register.class);

    private static final String USER = "user";
    private static final String ERROR = "errorMessage";
    private static final String MESSAGE_OF_ERROR_1 = "Not unique login or email";
    private static final String MESSAGE_OF_ERROR_2 = "User with such email or login is already exist";
    private static final String MESSAGE_OF_ERROR_3 = "Check input parameters";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        User user;
        try {
            user = userService.createUser(request);
            String previousQuery = CommandHelper.getPreviousQuery(request);
            UserService service = ServiceFactory.getInstance().getUserService();
            user = service.addUser(user);
            session.setAttribute(USER, user);
            if (user.getRole().equals("employee")) {
                request.getRequestDispatcher(JSP_ADD_EMPLOYEE_INFO).forward(request, response);
            } else response.sendRedirect(previousQuery);

        } catch (ServiceAuthorizationException e) {
            logger.log(Level.INFO, e.getMessage(), e);
            request.setAttribute(ERROR, MESSAGE_OF_ERROR_2);
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
            request.setAttribute(ERROR, MESSAGE_OF_ERROR_3);
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        } catch (NotUniqueLoginEmailException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
            request.setAttribute(ERROR, MESSAGE_OF_ERROR_1);
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        }

    }
}
