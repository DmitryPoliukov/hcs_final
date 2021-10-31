package by.epamtc.poliukov.comand.impl.dispatcher;

import by.epamtc.poliukov.comand.Command;
import by.epamtc.poliukov.exception.ServiceException;
import by.epamtc.poliukov.service.ServiceFactory;
import by.epamtc.poliukov.service.WorkRequestService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherCloseWorkRequest implements Command {
    private static final Logger logger = LogManager.getLogger(DispatcherCloseWorkRequest.class);
    private static final String JSP_PAGE_PATH = "DispatcherServlet?command=all-employee-work-plan";
    private static final String ERROR_PAGE = "WEB-INF/jsp/error.jsp";
    private static final String REQUEST_ID = "requestId";
    private static final String CLOSED = "closed";
    private static final String ERROR = "errorMessage";
    private static final String MESSAGE_OF_ERROR = "Work request status not updated";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        WorkRequestService workRequestService = serviceFactory.getWorkRequestService();
        int requestId = Integer.parseInt(request.getParameter(REQUEST_ID));
        try {
            workRequestService.updateWorkRequestStatus(requestId, CLOSED);
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
            request.setAttribute(ERROR, MESSAGE_OF_ERROR);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }

    }
}
