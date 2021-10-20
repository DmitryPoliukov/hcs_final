package by.epamtc.poliukov.comand.impl.user;

import by.epamtc.poliukov.comand.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToAddTenant implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/user/addTenant.jsp";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
