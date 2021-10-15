package by.epamtc.poliukov.comand.impl.guest;

import by.epamtc.poliukov.comand.Command;
import by.epamtc.poliukov.comand.CommandHelper;
import by.epamtc.poliukov.entity.User;
import by.epamtc.poliukov.exception.ServiceAuthorizationException;
import by.epamtc.poliukov.exception.ServiceException;
import by.epamtc.poliukov.service.ServiceFactory;
import by.epamtc.poliukov.service.UserService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;


public class Login implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/login.jsp";

    private static final String USER = "user";

    private static final String LOGIN = "username";
    private static final String PASSWORD = "pass";

    private static final String ERROR = "errorMessage";
    private static final String MESSAGE_OF_ERROR_1 = "Wrong login or pass";
    private static final String MESSAGE_OF_ERROR_3 = "Sorry something went wrong";
    private static final String MESSAGE_OF_ERROR_4 = "All fields should be filled";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String login = request.getParameter(LOGIN);
        byte[] password = request.getParameter(PASSWORD).getBytes();

        String previousQuery = CommandHelper.getPreviousQuery(request);

        UserService userService = ServiceFactory.getInstance().getUserService();
        HttpSession session = request.getSession(true);

        if (login != null && password.length>4) {
            try {
                User user = userService.authorise(login, password);
                Arrays.fill(password, (byte) 0);
                session.setAttribute(USER, user);
                response.sendRedirect(previousQuery);
            } catch (ServiceAuthorizationException e) {
//                logger.log(Level.INFO, " authorization error");
                request.setAttribute(ERROR, MESSAGE_OF_ERROR_1);
                request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
            } catch (ServiceException e) {
//                logger.log(Level.ERROR, e.getMessage(), e);
                request.setAttribute(ERROR, MESSAGE_OF_ERROR_3);
                request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            request.setAttribute(ERROR, MESSAGE_OF_ERROR_4);
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        }
    }
}
