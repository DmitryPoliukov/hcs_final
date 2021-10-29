package by.epamtc.poliukov.comand.impl.guest;

import by.epamtc.poliukov.comand.Command;
import by.epamtc.poliukov.comand.CommandHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChangeLanguage implements Command {
    private static final String LANGUAGE = "language";

    private static final String SESSION_LANGUAGE = "language";

    private List<String> supportedLanguages = new ArrayList<>();
    private static final String ENGLISH = "en";
    private static final String RUSSIAN = "ru";


    public ChangeLanguage() {
        supportedLanguages.add(ENGLISH);
        supportedLanguages.add(RUSSIAN);

    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String language = request.getParameter(LANGUAGE);

        HttpSession session = request.getSession(true);
        if (language != null) {
            if (!supportedLanguages.contains(language)) {
                language = ENGLISH;
            }

            session.setAttribute(SESSION_LANGUAGE, language);
        }
        String previousQuery = CommandHelper.getPreviousQuery(request);
        response.sendRedirect(previousQuery);
    }
}
