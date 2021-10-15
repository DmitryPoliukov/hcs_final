package by.epamtc.poliukov.comand.impl.tenant;

import by.epamtc.poliukov.comand.Command;
import by.epamtc.poliukov.entity.WorkRequest;
import by.epamtc.poliukov.exception.ServiceException;
import by.epamtc.poliukov.service.ServiceFactory;
import by.epamtc.poliukov.service.WorkRequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddWorkRequest implements Command {

    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/tenant/addSubquery.jsp";
    private static final Logger logger = LogManager.getLogger(AddWorkRequest.class);

    private static final String USER = "user";
    private static final String WORK_REQUEST = "workRequest";
    private static final String SUCCESS = "successMessage";
    private static final String MESSAGE_OF_SUCCESS = "Work request information added";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        WorkRequest workRequest;
        boolean isAdded;
        HttpSession session = request.getSession(true);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        WorkRequestService workRequestService = serviceFactory.getWorkRequestService();


        try {
            workRequest = workRequestService.createWorkRequest(request,  session);
            workRequest = workRequestService.addWorkRequest(workRequest);
            session.setAttribute(WORK_REQUEST, workRequest);
            request.setAttribute(SUCCESS, MESSAGE_OF_SUCCESS);
            request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
