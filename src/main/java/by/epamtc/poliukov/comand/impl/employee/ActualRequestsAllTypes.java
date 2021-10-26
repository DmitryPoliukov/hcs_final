package by.epamtc.poliukov.comand.impl.employee;

import by.epamtc.poliukov.comand.Command;
import by.epamtc.poliukov.entity.WorkRequest;
import by.epamtc.poliukov.exception.ServiceException;
import by.epamtc.poliukov.service.ServiceFactory;
import by.epamtc.poliukov.service.UserService;
import by.epamtc.poliukov.service.WorkRequestService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActualRequestsAllTypes implements Command {
    private static final Logger logger = LogManager.getLogger(ActualRequestsAllTypes.class);

    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/employee/actualRequests.jsp";
    private static final String ERROR_PAGE = "WEB-INF/jsp/error.jsp";
    private static final String PAGE = "page";
    private static final String AMOUNT_OF_PAGES = "noOfPages";
    private static final String CURRENT_PAGE = "currentPage";
    private static final int RECORDS_PER_PAGE = 4;
    private static final String REQUEST_ATTRIBUTE = "actualRequests";
    private static final String TENANT_INFO_LIST = "tenantInfoList";
    private static final String ERROR = "errorMessage";
    private static final String MESSAGE_OF_ERROR = "No work requests matching your query";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<WorkRequest> workRequestList;
        List<List<String>> tenantInfoList = new ArrayList<>();
        WorkRequestService workRequestService = ServiceFactory.getInstance().getWorkRequestService();
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            int page = 1;
            if (request.getParameter(PAGE) != null) {
                page = Integer.parseInt(request.getParameter(PAGE));
            }
            workRequestList = workRequestService.getAllNewRequests((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
            for (WorkRequest workRequest: workRequestList) {
                int tenantId = workRequest.getTenantUserId();
                tenantInfoList.add(userService.getTenantInfo(tenantId));
            }
            request.setAttribute(REQUEST_ATTRIBUTE, workRequestList);
            request.setAttribute(TENANT_INFO_LIST, tenantInfoList);
            int numberOfRequests = workRequestService.allNewRequestsCount();
            int noOfPages = (int) Math.ceil(numberOfRequests * 1.0 / RECORDS_PER_PAGE);
            request.setAttribute(AMOUNT_OF_PAGES, noOfPages);
            request.setAttribute(CURRENT_PAGE, page);
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
            request.setAttribute(ERROR, MESSAGE_OF_ERROR);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
