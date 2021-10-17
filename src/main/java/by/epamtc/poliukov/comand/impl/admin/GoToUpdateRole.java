package by.epamtc.poliukov.comand.impl.admin;

import by.epamtc.poliukov.comand.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoToUpdateRole implements Command {
    private static final String JSP_PAGE_PATH = "WEB-INF/jsp/admin/updateRole.jsp";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getRequestDispatcher(JSP_PAGE_PATH).forward(request, response);
    }
}
