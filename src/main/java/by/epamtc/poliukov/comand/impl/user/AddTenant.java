package by.epamtc.poliukov.comand.impl.user;

import by.epamtc.poliukov.comand.Command;
import by.epamtc.poliukov.comand.CommandHelper;
import by.epamtc.poliukov.comand.impl.guest.ViewUser;
import by.epamtc.poliukov.entity.Tenant;
import by.epamtc.poliukov.entity.User;
import by.epamtc.poliukov.exception.ServiceAuthorizationException;
import by.epamtc.poliukov.exception.ServiceException;
import by.epamtc.poliukov.service.ServiceFactory;
import by.epamtc.poliukov.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static by.epamtc.poliukov.dao.ColumnName.*;
import static by.epamtc.poliukov.dao.ColumnName.LOGIN;

public class AddTenant implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/user/addTenant.jsp";
    private static final String ERROR_PAGE = "WEB-INF/jsp/error.jsp";
    private static final String ACCOUNT = "WEB-INF/user/userPage.jsp";

    private static final Logger logger = LogManager.getLogger(AddTenant.class);
    private static final String TENANT = "tenant";
    private static final String USER = "user";
    private static final String LOGIN = "login";
    private static final String SUCCESS = "successMessage";
    private static final String MESSAGE_OF_SUCCESS = "Tenant information added";



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            User user;
            UserService userService = ServiceFactory.getInstance().getUserService();
            //String login = request.getParameter(LOGIN);
            String city = request.getParameter(CITY);
            String address = request.getParameter(ADDRESS);
            HttpSession session = request.getSession(true);
            user = (User) session.getAttribute("user");
            String login = user.getLogin();

            try {
                user = userService.addTenantInfo(login, city, address);
                request.setAttribute(SUCCESS, MESSAGE_OF_SUCCESS);
                request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
            } catch (ServiceException e) {
                e.printStackTrace();
            }






    }
}
