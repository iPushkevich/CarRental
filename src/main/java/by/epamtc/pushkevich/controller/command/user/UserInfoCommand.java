package by.epamtc.pushkevich.controller.command.user;

import by.epamtc.pushkevich.controller.command.Command;
import by.epamtc.pushkevich.controller.command.CommandParameterName;
import by.epamtc.pushkevich.entity.User;
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

import static java.lang.System.out;

public class UserInfoCommand implements Command {
    private final UserServiceFactory userServiceFactory = UserServiceFactory.getInstance();
    private final UserService userService = userServiceFactory.geUserService();

    private static final String DIRECTION_TO_ORDER = "controller?command=go_to_order_page";
    private static final String DIRECTION_TO_USER_ACCOUNT = "controller?command=go_to_user_account_page";

    private static final String NAME = "name";
    private static final String SURNAME = "surName";
    private static final String AGE = "age";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String PASSPORT = "passport";
    private static final String DRIVING_EXPERIENCE = "experience";
    private static final String ROAD_ACCIDENT = "accident";
    private static final String USER_ID = "userID";
    private static final String USER_ID_FROM_ADMIN = "userIDFromAdmin";
    private static final String USER = "user";
    private static final String ADMIN = "admin";
    private static final String ADMIN_ID = "adminID";
    private static final String ADMIN_ACTION = "adminAction";
    private static final String WRONG_INPUT = "wrongInput";
    private static final String WRONG_INPUT_MSG = "Incorrect input";
    private static final String ROLE = "userRole";
    private static final String ROLE_USER = "пользователь";
    private static final String ROLE_GUEST = "гость";
    private static final String DATA_CHANGED = "dataChanged";
    private static final String DATA_CHANGED_MSG = "Information successfully saved";
    private static final String CAR_RENT_INFO = "carRentInfo";

    private static final String DATABASE_ERROR = "Database error";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String direction;

        String name = request.getParameter(NAME);
        String surName = request.getParameter(SURNAME);
        String age = request.getParameter(AGE);
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        String passport = request.getParameter(PASSPORT);
        String drivingExperience = request.getParameter(DRIVING_EXPERIENCE);
        String roadAccident = request.getParameter(ROAD_ACCIDENT);

        HttpSession session = request.getSession(false);

        User user;

        int id = (int) session.getAttribute(USER_ID);
        String userIDFromAdmin = request.getParameter(USER_ID_FROM_ADMIN);

        try {
            if (userIDFromAdmin != null) {
                id = Integer.parseInt(userIDFromAdmin);
                User admin = (User) session.getAttribute(USER);
                int adminID = (int) session.getAttribute(USER_ID);

                user = userService.getUserById(id);

                session.setAttribute(USER, user);
                session.setAttribute(USER_ID, id);

                session.setAttribute(ADMIN, admin);
                session.setAttribute(ADMIN_ID, adminID);
                request.setAttribute(ADMIN_ACTION, ADMIN_ID);

                direction = CommandParameterName.GO_TO_USER_INFO_PAGE.getDirection();

                RequestDispatcher dispatcher = request.getRequestDispatcher(direction);
                dispatcher.forward(request, response);
            }
            else {
                user = userService.getUserById(id);

                try {
                    user.setName(name);
                    user.setSurName(surName);
                    user.setAge(Byte.parseByte(age));
                    user.setPhoneNumber(phoneNumber);
                    user.setPassport(passport);
                    user.setDrivingExperience(Byte.parseByte(drivingExperience));
                    user.setRoadAccidentCount(Short.parseShort(roadAccident));
                }
                catch (NumberFormatException e) {
                    session.setAttribute(WRONG_INPUT, WRONG_INPUT_MSG);
                }

                if (session.getAttribute(WRONG_INPUT) == null) {
                    user = userService.changeUserInfo(user);

                    session.setAttribute(USER, user);

                    String userRole = userService.getUserRoleByUserId(id);

                    if (userRole.equals(ROLE_GUEST)) {
                        userRole = ROLE_USER;
                        userService.setUserRoleByUserId(id, userRole);

                        session.setAttribute(ROLE, userRole);
                    }

                    session.setAttribute(DATA_CHANGED, DATA_CHANGED_MSG);
                }

                String adminAction = request.getParameter(ADMIN_ACTION);
                direction = DIRECTION_TO_USER_ACCOUNT;

                if (adminAction != null) {
                    user = (User) session.getAttribute(ADMIN);
                    id = (int) session.getAttribute(ADMIN_ID);

                    session.setAttribute(USER, user);
                    session.setAttribute(USER_ID, id);

                    direction = CommandParameterName.ALL_USERS.getDirection();
                }
                else if (session.getAttribute(CAR_RENT_INFO) != null) {
                    String userRole = userService.getUserRoleByUserId(id);

                    session.setAttribute(ROLE, userRole);

                    if (userRole != null && !userRole.equals("гость")) {
                        session.removeAttribute(CAR_RENT_INFO);
                    }

                    direction = DIRECTION_TO_ORDER;
                }

                response.sendRedirect(direction);
            }
        }
        catch (ServiceException e) {
            if (e.getMessage().equalsIgnoreCase(DATABASE_ERROR)){
                throw new RuntimeException();
            }
        }
    }
}
