package by.epamtc.poliukov.comand.impl.user;

import by.epamtc.poliukov.comand.Command;
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
import javax.servlet.http.HttpSession;

import java.io.IOException;

public class AddTenant implements Command {
    private static final Logger logger = LogManager.getLogger(AddTenant.class);

    private static final String JSP_PAGE_PATH = "/DispatcherServlet?command=main-page";
    private static final String USER = "user";
    private static final String ERROR = "errorMessage";
    private static final String MESSAGE_OF_ERROR = "Tenant information not added";
    private static final String MESSAGE_OF_ERROR_1 = "Wrong login";
    private static final String CITY = "city";
    private static final String ADDRESS = "address";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            User user;
            UserService userService = ServiceFactory.getInstance().getUserService();
            String city = request.getParameter(CITY);
            String address = request.getParameter(ADDRESS);
            HttpSession session = request.getSession(true);
            user = (User) session.getAttribute(USER);
            String login = user.getLogin();

            try {
                user = userService.addTenantInfo(login, city, address);
                session.setAttribute(USER, user);
                response.sendRedirect(request.getContextPath() + JSP_PAGE_PATH);
            } catch (ServiceAuthorizationException e) {
                logger.log(Level.ERROR, " authorization error");
                request.setAttribute(ERROR, MESSAGE_OF_ERROR_1);
                request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
            } catch (ServiceException e) {
                logger.log(Level.ERROR, e.getMessage(), e);
                request.setAttribute(ERROR, MESSAGE_OF_ERROR);
                request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
            }


    }
}
