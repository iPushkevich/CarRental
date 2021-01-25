package by.epamtc.pushkevich.controller.command.user;

import by.epamtc.pushkevich.controller.command.Command;
import by.epamtc.pushkevich.controller.command.CommandParameterName;
import by.epamtc.pushkevich.entity.Order;
import by.epamtc.pushkevich.entity.User;
import by.epamtc.pushkevich.exception.ServiceException;
import by.epamtc.pushkevich.service.order.OrderService;
import by.epamtc.pushkevich.service.order.OrderServiceFactory;
import by.epamtc.pushkevich.service.user.UserService;
import by.epamtc.pushkevich.service.user.UserServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;
import static java.lang.System.setOut;

public class AllUsersCommand implements Command {
    private final UserServiceFactory userServiceFactory = UserServiceFactory.getInstance();
    private final UserService userService = userServiceFactory.geUserService();

    private final OrderServiceFactory orderServiceFactory = OrderServiceFactory.getInstance();
    private final OrderService orderService = orderServiceFactory.getOrderService();

    private static final String USER_ID = "userID";
    private static final String ACTIVE_RENTS = "activeRents";
    private static final String NEW_USER_ROLE = "newRole";
    private static final String USER = "user";
    private static final String USERS = "users";
    private static final String USER_ROLE = "userRole";
    private static final String USER_REG_DATE = "regDate";
    private static final String ROLE_ADMIN = "admin";
    private static final String DB_ROLE_ADMIN = "администратор";
    private static final String ROLE_USER = "user";
    private static final String DB_ROLE_USER = "пользователь";
    private static final String ORDERS = "orders";
    private static final String NO_ORDERS = "noOrders";
    private static final String NO_ORDERS_MSG = "User has no orders";
    private static final String ACTIVE_RENTS_VALUE = "1";

    private static final String DATABASE_ERROR = "Database error";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String direction = CommandParameterName.GO_TO_ALL_USERS_PAGE.getDirection();

        try {
            Map<Integer, User> users = userService.getAllUsers();
            Date regDate;
            User user;
            boolean activeRentsFlag = false;

            String userID = request.getParameter(USER_ID);
            String activeRents = request.getParameter(ACTIVE_RENTS);

            if (activeRents != null && activeRents.equals(ACTIVE_RENTS)) {
                activeRentsFlag = true;
            }

            String newRole = request.getParameter(NEW_USER_ROLE);

            if (userID != null) {
                int id = Integer.parseInt(userID);

                user = userService.getUserById(id);
                regDate = userService.getUserRegistrationDateByEmail(user.getEmail());
                String userRole = userService.getUserRoleByUserId(id);
                List<Order> orders = orderService.getOrders(id);

                request.setAttribute(USER, user);
                request.setAttribute(USER_ID, userID);
                request.setAttribute(USER_ROLE, userRole);
                request.setAttribute(USER_REG_DATE, regDate);

                if (newRole != null) {
                    String role = newRole;

                    if (newRole.equalsIgnoreCase(ROLE_ADMIN)) {
                        role = DB_ROLE_ADMIN;
                    }
                    if (newRole.equalsIgnoreCase(ROLE_USER)) {
                        role = DB_ROLE_USER;
                    }

                    userService.setUserRoleByUserId(id, role);

                    request.setAttribute(USER_ROLE, role);
                }

                if (orders.size() != 0) {
                    request.setAttribute(ORDERS, orders);
                } else {
                    request.setAttribute(NO_ORDERS, NO_ORDERS_MSG);
                }
            }

            if (activeRentsFlag) {
                Iterator iterator = users.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Integer, User> entry = (Map.Entry<Integer, User>) iterator.next();
                    int uID = entry.getKey();
                    boolean hasActiveRent = userService.userHasActiveRent(uID);

                    if (!hasActiveRent) {
                        iterator.remove();
                    }
                }
                request.setAttribute(ACTIVE_RENTS, ACTIVE_RENTS_VALUE);
            }

            request.setAttribute(USERS, users);
        }
        catch (ServiceException e) {
          if (e.getMessage().equalsIgnoreCase(DATABASE_ERROR)){
              throw new RuntimeException();
          }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(direction);
        dispatcher.forward(request, response);
    }
}
