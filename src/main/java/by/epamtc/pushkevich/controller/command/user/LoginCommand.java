package by.epamtc.pushkevich.controller.command.user;

import by.epamtc.pushkevich.controller.command.Command;
import by.epamtc.pushkevich.controller.command.CommandParameterName;
import by.epamtc.pushkevich.entity.User;
import by.epamtc.pushkevich.exception.ConnectionPoolException;
import by.epamtc.pushkevich.exception.ServiceException;
import by.epamtc.pushkevich.service.user.UserService;
import by.epamtc.pushkevich.service.user.UserServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class LoginCommand implements Command {
    private final UserServiceFactory userServiceFactory = UserServiceFactory.getInstance();
    private final UserService userService = userServiceFactory.geUserService();

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String USER_ID = "userID";
    private static final String USER = "user";
    private static final String USER_ROLE = "userRole";
    private static final String CAR_RENT_INFO = "carRentInfo";
    private static final String GUEST_ROLE = "гость";
    private static final String ERROR = "error";

    private static final String DATABASE_ERROR = "Database error";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String direction = CommandParameterName.GO_TO_LOGIN_PAGE.getDirection();

        User user;
        int userID;
        String userRole;

        HttpSession session = request.getSession(false);

        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);

        try {
            user = userService.getUser(email.toLowerCase(), password);
            userID = userService.getUserId(email.toLowerCase(), password);
            userRole = userService.getUserRoleByUserId(userID);

            session.setAttribute(USER, user);
            session.setAttribute(USER_ROLE, userRole);
            session.setAttribute(USER_ID, userID);

            if (session.getAttribute(CAR_RENT_INFO) != null && !userRole.equals(GUEST_ROLE)) {
                session.removeAttribute(CAR_RENT_INFO);

                direction = CommandParameterName.GO_TO_ORDER_PAGE.getDirection();
            } else {
                direction = CommandParameterName.GO_TO_MAIN_PAGE.getDirection();
            }
        }
        catch (ServiceException e) {
            if (e.getMessage().equalsIgnoreCase(DATABASE_ERROR)) {
                throw new RuntimeException();
            }

            request.setAttribute(ERROR, e.getMessage());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(direction);
        dispatcher.forward(request, response);
    }
}
