package by.epamtc.poliukov.comand.impl.tenant;

import by.epamtc.poliukov.comand.Command;
import by.epamtc.poliukov.entity.Subquery;
import by.epamtc.poliukov.entity.WorkRequest;
import by.epamtc.poliukov.exception.IncorrectDateException;
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

import static by.epamtc.poliukov.dao.ColumnName.INFORMATION;

public class AddSubquery implements Command {
    private static final Logger logger = LogManager.getLogger(AddSubquery.class);

    private static final String JSP_PAGE_PATH = "/DispatcherServlet?command=go-to-add-subquery";
    private static final String ERROR_PAGE = "WEB-INF/jsp/error.jsp";
    private static final String WORK_REQUEST = "workRequest";
    private static final String SUCCESS_SUB = "successMessageSubquery";
    private static final String MESSAGE_OF_SUCCESS = "Subquery information added";
    private static final String ERROR = "errorMessage";
    private static final String MESSAGE_OF_ERROR = "Subquery information not added";
    private static final String MESSAGE_OF_ERROR_2 = "Check input parameters";
    private static final String USER = "user";
    private static final String WORK_REQUEST_ID = "workRequestId";
    private static final String AMOUNT = "amount";
    private static final String WORK_TYPE = "workType";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Subquery subquery;
        HttpSession session = request.getSession(true);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        WorkRequestService workRequestService = serviceFactory.getWorkRequestService();
        int workRequestID = Integer.parseInt(request.getParameter(WORK_REQUEST_ID));
        int amountOfWorkInHours = Integer.parseInt(request.getParameter(AMOUNT));
        String information = request.getParameter(INFORMATION);
        String workType = request.getParameter(WORK_TYPE);

        try {
            subquery = workRequestService.createSubquery(workRequestID, amountOfWorkInHours, information, workType);
            WorkRequest workRequest = (WorkRequest) session.getAttribute(WORK_REQUEST);
            int requestId = workRequest.getRequestID();
            workRequestService.addSubqueries(subquery, requestId);
            session.setAttribute(SUCCESS_SUB, MESSAGE_OF_SUCCESS);
            response.sendRedirect(request.getContextPath() + JSP_PAGE_PATH);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
            request.setAttribute(ERROR, MESSAGE_OF_ERROR);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        } catch (IncorrectDateException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
            request.setAttribute(ERROR, MESSAGE_OF_ERROR_2);
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
}
