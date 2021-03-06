package by.epamtc.poliukov.comand.impl.user;

import by.epamtc.poliukov.comand.Command;
import by.epamtc.poliukov.entity.User;
import by.epamtc.poliukov.exception.IncorrectDateException;
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
import java.util.Arrays;
import java.util.List;

public class AddEmployee implements Command {
    private static final Logger logger = LogManager.getLogger(AddEmployee.class);

    private static final String JSP_PAGE_PATH = "/DispatcherServlet?command=go-to-add-employee";
    private static final String USER = "user";
    private static final String WORK_TYPE = "workType";
    private static final String VALUE_PERSON_HOUR = "valuePersonHour";
    private static final String INFORMATION = "information";
    private static final String SUCCESS = "successMessage";
    private static final String MESSAGE_OF_SUCCESS = "Employee information added";
    private static final String ERROR = "errorMessage";
    private static final String MESSAGE_OF_ERROR = "Employee information not added";
    private static final String MESSAGE_OF_ERROR_2 = "Check input data";



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user;
        UserService userService = ServiceFactory.getInstance().getUserService();
        HttpSession session = request.getSession(true);
        user = (User) session.getAttribute(USER);
        int userId = user.getUserId();
        int valuePersonHour = Integer.parseInt(request.getParameter(VALUE_PERSON_HOUR));
        String information = request.getParameter(INFORMATION);
        String[] workTypes = request.getParameterValues(WORK_TYPE);
        try {
            user = userService.addEmployeeInfo(userId, valuePersonHour, information);
            userService.addEmployeeWorkType(userId, workTypes);
            session.setAttribute(USER, user);
            session.setAttribute(SUCCESS, MESSAGE_OF_SUCCESS);
            response.sendRedirect(request.getContextPath() + JSP_PAGE_PATH);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
            request.setAttribute(ERROR, MESSAGE_OF_ERROR);
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        } catch (IncorrectDateException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
            request.setAttribute(ERROR, MESSAGE_OF_ERROR_2);
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        }

    }
}
