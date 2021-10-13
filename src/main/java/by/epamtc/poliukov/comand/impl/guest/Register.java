package by.epamtc.poliukov.comand.impl.guest;

import by.epamtc.poliukov.comand.Command;
import by.epamtc.poliukov.comand.CommandHelper;
import by.epamtc.poliukov.entity.Tenant;
import by.epamtc.poliukov.entity.User;
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
import java.util.Arrays;

import static by.epamtc.poliukov.dao.ColumnName.ADDRESS;
import static by.epamtc.poliukov.dao.ColumnName.CITY;


public class Register implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/guest/register.jsp";

    private static final Logger logger = LogManager.getLogger(Register.class);

    private static final String USER = "user";

    private static final String LOGIN = "username";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "pass";
    private static final String PASSWORD_2 = "pass2";

    private static final String ERROR = "errorMessage";
    private static final String MESSAGE_OF_ERROR_1 = "All fields should be filled";
    private static final String MESSAGE_OF_ERROR_2 = "User with such email or login is already exist";
    private static final String MESSAGE_OF_ERROR_3 = "Login and password should be at least 6 characters";
    private static final String SUCCESS = "successMessage";
    private static final String MESSAGE_OF_SUCCESS = "Tenant information added";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //catch блоки


        HttpSession session = request.getSession(true);
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();
            User user = userService.create(request);
            String previousQuery = CommandHelper.getPreviousQuery(request);
            UserService service = ServiceFactory.getInstance().getUserService();
            service.addUser(user);
            session.setAttribute(USER, user);
            response.sendRedirect(previousQuery);


         /*
        if (user.getRole().equals("tenant")) {
            String login = request.getParameter(LOGIN);
            String city = request.getParameter(CITY);
            String address = request.getParameter(ADDRESS);
            if (login == null && city == null && address == null) {
                request.getRequestDispatcher(JSP_ADD_TENANT).forward(request, response);
            }
            System.out.println(city);
        }

          */
    }
}
