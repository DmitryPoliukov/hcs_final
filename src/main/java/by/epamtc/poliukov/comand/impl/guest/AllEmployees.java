package by.epamtc.poliukov.comand.impl.guest;

import by.epamtc.poliukov.comand.Command;
import by.epamtc.poliukov.comand.CommandHelper;
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
import java.io.IOException;
import java.util.List;

public class AllEmployees implements Command {
    private static final Logger logger = LogManager.getLogger(AllEmployees.class);
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/common/allEmployees.jsp";
    private static final String ERROR_PAGE = "WEB-INF/jsp/error.jsp";
    private static final String REQUEST_ATTRIBUTE = "all_employees";

    private static final String PAGE = "page";
    private static final String AMOUNT_OF_PAGES = "noOfPages";
    private static final String CURRENT_PAGE = "currentPage";
    private static final int RECORDS_PER_PAGE = 10;

    private static final String ERROR = "errorMessage";
    private static final String MESSAGE_OF_ERROR = "No employees matching your query";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        CommandHelper.saveCurrentQueryToSession(request);

        List<User> employees = null;

        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            int page = 1;
            if (request.getParameter(PAGE) != null) {
                page = Integer.parseInt(request.getParameter(PAGE));
            }

            employees = userService.getAllEmployee((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);

            int numberOfParticipants = userService.allEmployeesCount();
            int noOfPages = (int) Math.ceil(numberOfParticipants * 1.0 / RECORDS_PER_PAGE);

            request.setAttribute(AMOUNT_OF_PAGES, noOfPages);
            request.setAttribute(CURRENT_PAGE, page);
            request.setAttribute(REQUEST_ATTRIBUTE, employees);
            logger.info(employees);
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
            request.setAttribute(ERROR, MESSAGE_OF_ERROR);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
