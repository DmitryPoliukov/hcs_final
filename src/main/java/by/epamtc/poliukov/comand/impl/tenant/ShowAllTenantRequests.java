package by.epamtc.poliukov.comand.impl.tenant;

import by.epamtc.poliukov.comand.Command;
import by.epamtc.poliukov.entity.User;
import by.epamtc.poliukov.entity.WorkRequest;
import by.epamtc.poliukov.exception.ServiceAuthorizationException;
import by.epamtc.poliukov.exception.ServiceException;
import by.epamtc.poliukov.service.ServiceFactory;
import by.epamtc.poliukov.service.WorkRequestService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ShowAllTenantRequests implements Command {
    private static final Logger logger = LogManager.getLogger(ShowAllTenantRequests.class);

    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/tenant/showAllTenantRequests.jsp";
    private static final String PAGE = "page";
    private static final String AMOUNT_OF_PAGES = "noOfPages";
    private static final String CURRENT_PAGE = "currentPage";
    private static final int RECORDS_PER_PAGE = 4;
    private static final String USER = "user";
    private static final String REQUEST_ATTRIBUTE = "workRequestList";
    private static final String ERROR = "errorMessage";
    private static final String MESSAGE_OF_ERROR = "No requests matching your query";
    private static final String MESSAGE_OF_ERROR_1 = "Wrong login";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<WorkRequest> workRequestList;
        WorkRequestService workRequestService = ServiceFactory.getInstance().getWorkRequestService();
        try {
            int page = 1;
            if (request.getParameter(PAGE) != null) {
                page = Integer.parseInt(request.getParameter(PAGE));
            }
            HttpSession session = request.getSession(true);
            User user = (User) session.getAttribute(USER);
            String login = user.getLogin();
            workRequestList = workRequestService.getAllRequestForTenantByLogin(login, (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
            int numbersOfRequestsByLogin = workRequestService.allRequestsByLoginCount(login);
            int noOfPages = (int) Math.ceil(numbersOfRequestsByLogin * 1.0 / RECORDS_PER_PAGE);

            request.setAttribute(AMOUNT_OF_PAGES, noOfPages);
            request.setAttribute(CURRENT_PAGE, page);
            request.setAttribute(REQUEST_ATTRIBUTE, workRequestList);
            logger.info(workRequestList);
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
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
