package by.epamtc.pushkevich.controller.command.user;

import by.epamtc.pushkevich.controller.command.Command;
import by.epamtc.pushkevich.controller.command.CommandParameterName;
import by.epamtc.pushkevich.entity.User;
import by.epamtc.pushkevich.exception.ServiceException;
import by.epamtc.pushkevich.service.user.UserService;
import by.epamtc.pushkevich.service.user.UserServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class RegistrationCommand implements Command {
    private final UserServiceFactory userServiceFactory = UserServiceFactory.getInstance();
    private final UserService userService = userServiceFactory.geUserService();

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String SUCCESS = "success";
    private static final String SUCCESS_MSG = "To order, fill in your personal data";
    private static final String USER_ID = "userID";
    private static final String USER_ROLE = "userRole";
    private static final String ERROR = "error";

    private static final String DATABASE_ERROR = "Database error";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String direction;

        HttpSession session = request.getSession(false);

        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);

        User user = new User();
        user.setEmail(email.toLowerCase());
        user.setPassword(password);

        try {
            userService.addNewUser(user);

            int userID = userService.getUserId(email, password);
            String userRole = userService.getUserRoleByUserId(userID);

            session.setAttribute(SUCCESS, SUCCESS_MSG);
            session.setAttribute(USER_ID, userID);

            session.setAttribute(USER_ROLE, userRole);

            direction = CommandParameterName.ADD_USER_INFO.getDirection();

            response.sendRedirect(direction);

        }
        catch (ServiceException e) {
            if (e.getMessage().equalsIgnoreCase(DATABASE_ERROR)){
                throw new RuntimeException();
            }
            request.setAttribute(ERROR, e.getMessage());

            direction = CommandParameterName.GO_TO_REGISTRATION_PAGE.getDirection();
            request.getRequestDispatcher(direction).forward(request, response);
        }
    }
}
