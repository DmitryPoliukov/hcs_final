package by.epamtc.poliukov.comand.impl.employee;

import by.epamtc.poliukov.comand.Command;
import by.epamtc.poliukov.entity.WorkRequest;
import by.epamtc.poliukov.exception.ServiceException;
import by.epamtc.poliukov.service.ServiceFactory;
import by.epamtc.poliukov.service.WorkRequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ActualRequestsAllTypes implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/employee/actualRequestsEmployee.jsp";
    private static final String ERROR_PAGE = "WEB-INF/jsp/error.jsp";

    private static final Logger logger = LogManager.getLogger(ActualRequestsEmployee.class);

    private static final String TYPE = "type";

    private static final String PAGE = "page";
    private static final String AMOUNT_OF_PAGES = "noOfPages";
    private static final String CURRENT_PAGE = "currentPage";
    private static final int RECORDS_PER_PAGE = 2;

    private static final String REQUEST_ATTRIBUTE = "actualRequests";

    private static final String ERROR = "errorMessage";
    private static final String MESSAGE_OF_ERROR = "No work requests matching your query";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<WorkRequest> workRequestList;
        WorkRequestService workRequestService = ServiceFactory.getInstance().getWorkRequestService();
        try {
            int page = 1;
            if (request.getParameter(PAGE) != null) {
                page = Integer.parseInt(request.getParameter(PAGE));
            }
            workRequestList = workRequestService.getAllNewRequests((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);


            request.setAttribute(REQUEST_ATTRIBUTE, workRequestList);

            int numberOfRequests = workRequestService.allNewRequestsCount();
            int noOfPages = (int) Math.ceil(numberOfRequests * 1.0 / RECORDS_PER_PAGE);

            request.setAttribute(AMOUNT_OF_PAGES, noOfPages);
            request.setAttribute(CURRENT_PAGE, page);
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            request.setAttribute(ERROR, MESSAGE_OF_ERROR);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}