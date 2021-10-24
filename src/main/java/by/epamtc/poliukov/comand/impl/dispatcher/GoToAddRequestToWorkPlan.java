package by.epamtc.poliukov.comand.impl.dispatcher;

import by.epamtc.poliukov.comand.Command;
import by.epamtc.poliukov.entity.User;
import by.epamtc.poliukov.entity.WorkRequest;
import by.epamtc.poliukov.exception.ServiceException;
import by.epamtc.poliukov.service.ServiceFactory;
import by.epamtc.poliukov.service.UserService;
import by.epamtc.poliukov.service.WorkRequestService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToAddRequestToWorkPlan implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/dispatcher/addRequestToWorkPlan.jsp";
    private static final String REQUEST_ID = "requestId";
    private static final String WORK_REQUEST = "workRequest";
    private static final String ALL_EMPLOYEES = "allEmployees";



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int requestId = Integer.parseInt(request.getParameter(REQUEST_ID));
        WorkRequest workRequest;
        WorkRequestService workRequestService = ServiceFactory.getInstance().getWorkRequestService();
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            workRequest = workRequestService.getWorkRequestById(requestId);
            List<User> allEmployees = userService.getAllEmployee();
            request.setAttribute(WORK_REQUEST, workRequest);
            request.setAttribute(ALL_EMPLOYEES, allEmployees);
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);

        } catch (ServiceException e) {
            e.printStackTrace();
        }



    }
}
