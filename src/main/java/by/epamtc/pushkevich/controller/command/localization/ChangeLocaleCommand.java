package by.epamtc.pushkevich.controller.command.localization;

import by.epamtc.pushkevich.controller.command.Command;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class ChangeLocaleCommand implements Command {
    private static final String DIRECTION_PREFIX = "controller?";
    private static final String LOCALE = "locale";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        String locale = request.getParameter(LOCALE);
        String query = (String) session.getAttribute("query");

        String direction = DIRECTION_PREFIX + query;

        session.setAttribute(LOCALE, locale);

        RequestDispatcher dispatcher = request.getRequestDispatcher(direction);
        dispatcher.forward(request, response);
    }
}
