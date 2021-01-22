package by.epamtc.pushkevich.controller.command.user;

import by.epamtc.pushkevich.controller.command.Command;
import by.epamtc.pushkevich.controller.command.CommandParameterName;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class LogoutCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String direction = CommandParameterName.LOGOUT.getDirection();

        HttpSession session = request.getSession(false);
        session.invalidate();

        RequestDispatcher dispatcher = request.getRequestDispatcher(direction);
        dispatcher.forward(request, response);
    }
}
