package by.epamtc.pushkevich.controller.command.order;

import by.epamtc.pushkevich.controller.command.Command;
import by.epamtc.pushkevich.controller.command.CommandParameterName;
import by.epamtc.pushkevich.entity.Order;
import by.epamtc.pushkevich.exception.ServiceException;
import by.epamtc.pushkevich.service.order.OrderService;
import by.epamtc.pushkevich.service.order.OrderServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserOrdersCommand implements Command {
    private final OrderServiceFactory orderServiceFactory = OrderServiceFactory.getInstance();
    private final OrderService orderService = orderServiceFactory.getOrderService();

    private static final String USER_ID = "userID";
    private static final String ORDERS = "orders";
    private static final String NO_ORDERS = "noOrders";
    private static final String NO_ORDERS_MSG = "You have no orders";

    private static final String DATABASE_ERROR = "Database error";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String direction = CommandParameterName.GO_TO_USER_ACCOUNT_PAGE.getDirection();

        int userId;
        List<Order> orders;

        try {
            userId = Integer.parseInt(request.getParameter(USER_ID));
            orders = orderService.getOrders(userId);

            if (orders.size() != 0) {
                request.setAttribute(ORDERS, orders);
            } else {
                request.setAttribute(NO_ORDERS, NO_ORDERS_MSG);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(direction);
            dispatcher.forward(request, response);
        }
        catch (ServiceException e){
            if (e.getMessage().equalsIgnoreCase(DATABASE_ERROR)) {
                throw new RuntimeException();
            }
        }
    }
}
