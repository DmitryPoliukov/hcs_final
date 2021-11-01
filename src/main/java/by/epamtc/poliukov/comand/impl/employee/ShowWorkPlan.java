package by.epamtc.poliukov.comand.impl.employee;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowWorkPlan implements Command {
    private static final Logger logger = LogManager.getLogger(ShowWorkPlan.class);

    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/employee/employeeShowWorkPlan.jsp";
    private static final String USER = "user";
    private static final String PLANNED_DATE = "plannedDate";
    private static final String ERROR = "errorMessage";
    private static final String MESSAGE_OF_ERROR = "No requests matching your query";
    private static final String MESSAGE_OF_ERROR2 = "Incorrect date format";
    private static final String WORK_REQUEST_LIST = "workRequestList";
    private static final String TENANT_INFO_LIST = "tenantInfoList";
    private static final String TENANT_LIST = "tenantList";
    private static final String SUB_LIST = "subqueryList";



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        WorksPlanService worksPlanService = serviceFactory.getWorksPlanService();
        UserService userService = serviceFactory.getUserService();
        WorkRequestService workRequestService = serviceFactory.getWorkRequestService();
        List<Integer> subqueriesIdList;
        List<WorkRequest> workRequestList = new ArrayList<>();
        List<List<String>> tenantInfoList = new ArrayList<>();
        List<User> tenants = new ArrayList<>();
        List<Subquery> subqueryList = new ArrayList<>();
        List<Integer> requestIdList = new ArrayList<>();

        User user = (User) session.getAttribute(USER);
        User tenant;
        int userId = user.getUserId();
        String plannedDate = request.getParameter(PLANNED_DATE);

        try {
            subqueriesIdList = worksPlanService.getSubqueryIdByEmployeeIdCompletionDate(userId, plannedDate);
            for (int subqueryId: subqueriesIdList) {
                Subquery subquery = workRequestService.getSubqueryBySubId(subqueryId);
                subqueryList.add(subquery);
            }
            for (int subId: subqueriesIdList) {
                int requestId = workRequestService.takeWorkRequestIdBySubqueryId(subId);
                if (!requestIdList.contains(requestId)) {
                    requestIdList.add(requestId);
                }
            }
            for (int workRequestId : requestIdList){
                workRequestList.add(workRequestService.getWorkRequestById(workRequestId));
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
            request.setAttribute(SUB_LIST, subqueryList);
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
