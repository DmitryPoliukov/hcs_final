package by.epamtc.poliukov.comand.impl.dispatcher;

import by.epamtc.poliukov.comand.Command;
import by.epamtc.poliukov.entity.WorkRequest;
import by.epamtc.poliukov.exception.ServiceException;
import by.epamtc.poliukov.service.ServiceFactory;
import by.epamtc.poliukov.service.WorkRequestService;
import by.epamtc.poliukov.service.WorksPlanService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddRequestsToWorkPlan implements Command {
    private static final Logger logger = LogManager.getLogger(AddRequestsToWorkPlan.class);

    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/employee/actualRequests.jsp";
    private static final String ERROR_PAGE = "WEB-INF/jsp/error.jsp";
    private static final String REQUEST_ID = "requestId";
    private static final String SUBQUERY_ID = "subqueryId";
    private static final String EMPLOYEE_ID = "employeeId";
    private static final String SUCCESS = "successMessage";
    private static final String MESSAGE_OF_SUCCESS = "Employee assigned for subquery";

    private static final String ERROR = "errorMessage";
    private static final String MESSAGE_OF_ERROR = "Work request not added to plan";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int requestId = Integer.parseInt(request.getParameter(REQUEST_ID));
        int subqueryId;
        int employeeId;
        WorkRequestService workRequestService = ServiceFactory.getInstance().getWorkRequestService();
        WorksPlanService worksPlanService = ServiceFactory.getInstance().getWorksPlanService();
        String[] subqueryIdArray = request.getParameterValues(SUBQUERY_ID);
        String [] employeeIdArray = request.getParameterValues(EMPLOYEE_ID);

        try {
            for(int i = 0; i<subqueryIdArray.length; i++) {
                subqueryId = Integer.parseInt(subqueryIdArray[i]);
                employeeId = Integer.parseInt(employeeIdArray[i]);
                worksPlanService.addWorkRequestToPlan(requestId, subqueryId, employeeId);
            }

            workRequestService.updateWorkRequestStatus(requestId, "in_process");
            request.setAttribute(SUCCESS, MESSAGE_OF_SUCCESS);
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
            request.setAttribute(ERROR, MESSAGE_OF_ERROR);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
