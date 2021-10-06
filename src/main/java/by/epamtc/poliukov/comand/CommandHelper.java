package by.epamtc.poliukov.comand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class CommandHelper {

    private static final String LANGUAGE = "language";
    private static final String ENGLISH = "en";
    private static final String WELCOME_PAGE = "index.jsp";
    private static final String PREVIOUS_QUERY = "previousQuery";
    private static final String SESSION_PREV_QUERY = "previousQuery";
    private static final char QUERY_SEPARATOR = '?';

    private CommandHelper() {
    }


    public static Object getLanguage(HttpServletRequest request) {
        Object lang = request.getSession(false).getAttribute(LANGUAGE);
        if (lang == null) {
            return ENGLISH;
        }
        return lang;
    }


    public static String getPreviousQuery(HttpServletRequest request) {
        String previousQuery = (String) request.getSession(false).getAttribute(PREVIOUS_QUERY);
        if (previousQuery == null) {
            previousQuery = WELCOME_PAGE;
        }
        return previousQuery;
    }


    public static void saveCurrentQueryToSession(HttpServletRequest request) {
        HttpSession session = request.getSession(true);

        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();

        if (queryString == null) {
            session.setAttribute(SESSION_PREV_QUERY, requestURI);
        } else {
            session.setAttribute(SESSION_PREV_QUERY, requestURI + QUERY_SEPARATOR + queryString);
        }
    }
}
