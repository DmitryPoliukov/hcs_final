package by.epamtc.poliukov.comand.impl.dispatcher;

import by.epamtc.poliukov.comand.Command;
import by.epamtc.poliukov.entity.Subquery;
import by.epamtc.poliukov.entity.User;
import by.epamtc.poliukov.entity.WorkRequest;
import by.epamtc.poliukov.exception.IncorrectDateException;
import by.epamtc.poliukov.exception.ServiceException;
import by.epamtc.poliukov.service.ServiceFactory;
import by.epamtc.poliukov.service.UserService;
import by.epamtc.poliukov.service.WorkRequestService;
import by.epamtc.poliukov.service.WorksPlanService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DispatcherWorkPlan implements Command {
    private static final Logger logger = LogManager.getLogger(DispatcherWorkPlan.class);

    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/dispatcher/dispatcherShowWorkPlan.jsp";
    private static final String PLANNED_DATE = "plannedDate";
    private static final String ERROR = "errorMessage";
    private static final String MESSAGE_OF_ERROR = "No requests matching your query";
    private static final String MESSAGE_OF_ERROR2 = "Illegal date format";
    private static final String WORK_REQUEST_LIST = "workRequestList";
    private static final String TENANT_INFO_LIST = "tenantInfoList";
    private static final String TENANT_LIST = "tenantList";
    private static final String EMPLOYEE_ID = "employeeId";
    private static final String EMPLOYEE = "employee";
    private static final String FIRST_SUB = "firstSubquery";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        WorksPlanService worksPlanService = serviceFactory.getWorksPlanService();
        UserService userService = serviceFactory.getUserService();
        WorkRequestService workRequestService = serviceFactory.getWorkRequestService();

        List<Integer> subqueriesIdList;
        List<WorkRequest> workRequestList = new ArrayList<>();
        List<List<String>> tenantInfoList = new ArrayList<>();
        List<User> tenants = new ArrayList<>();
        User tenant;
        User employee;
        int employeeId = Integer.parseInt(request.getParameter(EMPLOYEE_ID));
        String plannedDate = request.getParameter(PLANNED_DATE);

        try {
            employee = userService.getUserByUserId(employeeId);
            subqueriesIdList = worksPlanService.getSubqueryIdByEmployeeIdCompletionDate(employeeId, plannedDate);
            int firstSubId = subqueriesIdList.get(0);
            Subquery firstSub = workRequestService.getSubqueryBySubId(firstSubId);
            for (int subId: subqueriesIdList) {
                int requestId = workRequestService.takeWorkRequestIdBySubqueryId(subId);
                workRequestList.add(workRequestService.getWorkRequestById(requestId));
            }

            for (WorkRequest workRequest : workRequestList) {
                int tenantId = workRequest.getTenantUserId();
                tenant = userService.getUserByUserId(tenantId);
                tenants.add(tenant);
                tenantInfoList.add(userService.getTenantInfo(tenantId));
            }
            request.setAttribute(TENANT_LIST, tenants);
            request.setAttribute(WORK_REQUEST_LIST, workRequestList);
            request.setAttribute(TENANT_INFO_LIST, tenantInfoList);
            request.setAttribute(EMPLOYEE, employee);
            request.setAttribute(FIRST_SUB, firstSub);

            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
            request.setAttribute(ERROR, MESSAGE_OF_ERROR);
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        } catch (IncorrectDateException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
            request.setAttribute(ERROR, MESSAGE_OF_ERROR2);
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        }

    }
}
