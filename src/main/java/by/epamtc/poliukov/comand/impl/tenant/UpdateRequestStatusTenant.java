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

public class UpdateRequestStatusTenant implements Command {
    private static final Logger logger = LogManager.getLogger(UpdateRequestStatusTenant.class);
    private static final String JSP_PAGE_PATH = "DispatcherServlet?command=show-all-tenant-requests";
    private static final String DELETE = "delete";
    private static final String USER = "user";
    private static final String REQUEST_ATTRIBUTE = "workRequestList";
    private static final String CLOSED = "closed";
    private static final String SUCCESS = "deleteSuccessMessage";
    private static final String MESSAGE_OF_SUCCESS = "Work request canceled";
    private static final String ERROR = "errorMessage";
    private static final String MESSAGE_OF_ERROR = "Request status not updated";
    private static final String MESSAGE_OF_ERROR_1 = "Wrong login";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<WorkRequest> workRequestList;
        int requestId = Integer.parseInt(request.getParameter(DELETE));
        WorkRequestService workRequestService = ServiceFactory.getInstance().getWorkRequestService();
        try {
            workRequestService.updateWorkRequestStatus(requestId, CLOSED);
            HttpSession session = request.getSession(true);
            User user = (User) session.getAttribute(USER);
            String login = user.getLogin();
            workRequestList = workRequestService.getAllRequestForTenantByLogin(login);
            request.setAttribute(REQUEST_ATTRIBUTE, workRequestList);
            request.setAttribute(SUCCESS, MESSAGE_OF_SUCCESS);
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
