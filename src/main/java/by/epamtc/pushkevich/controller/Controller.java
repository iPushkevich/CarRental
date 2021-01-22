package by.epamtc.pushkevich.controller;

import by.epamtc.pushkevich.controller.command.Command;
import by.epamtc.pushkevich.controller.command.CommandProvider;
import by.epamtc.pushkevich.exception.ConnectionPoolException;
import by.epamtc.pushkevich.repository.connection.ConnectionPool;
import by.epamtc.pushkevich.repository.order.OrderSQLRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class Controller extends HttpServlet {

    private final CommandProvider provider = new CommandProvider();
    private final static String COMMAND_NAME = "command";
    private final Logger logger = LogManager.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        apply(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        apply(req, resp);
    }

    private void apply(HttpServletRequest req, HttpServletResponse resp){
        String currentCommand;
        Command command;

        currentCommand = req.getParameter(COMMAND_NAME);
        command = provider.getCommand(currentCommand);

        try {
            command.execute(req,resp);
        }
        catch (ServletException | IOException e) {
            logger.error("Main controller problem");
            throw new RuntimeException();
        }
    }

    @Override
    public void destroy() {
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            pool.destroyConnectionPool();

        } catch (ConnectionPoolException e) {
            throw new RuntimeException();
        }
    }
}
