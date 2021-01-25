package by.epamtc.pushkevich.controller.command.order;

import by.epamtc.pushkevich.controller.command.Command;
import by.epamtc.pushkevich.controller.command.CommandParameterName;
import by.epamtc.pushkevich.entity.CarRentInfo;
import by.epamtc.pushkevich.entity.Order;
import by.epamtc.pushkevich.entity.User;
import by.epamtc.pushkevich.exception.ServiceException;
import by.epamtc.pushkevich.service.car.CarService;
import by.epamtc.pushkevich.service.car.CarServiceFactory;
import by.epamtc.pushkevich.service.order.OrderService;
import by.epamtc.pushkevich.service.order.OrderServiceFactory;
import by.epamtc.pushkevich.service.user.UserService;
import by.epamtc.pushkevich.service.user.UserServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddOrderCommand implements Command {
    private final OrderServiceFactory orderFactory = OrderServiceFactory.getInstance();
    private final OrderService orderService = orderFactory.getOrderService();

    private final CarServiceFactory carFactory = CarServiceFactory.getInstance();
    private final CarService carService = carFactory.getCarService();

    private final UserServiceFactory userFactory = UserServiceFactory.getInstance();
    private final UserService userService = userFactory.geUserService();

    private static final String USER_ID = "userID";
    private static final String CAR_ID = "carID";
    private static final String CONFIRM_ORDER = "confirmOrder";
    private static final String RENT_START = "rentStart";
    private static final String RENT_DAYS = "rentDays";
    private static final String DATE_FORMAT = "yy-MM-dd'T'HH:mm";
    private static final String ORDER = "order";
    private static final String USER_PHONE = "userPhone";
    private static final String START_RENT_TIME = "startDate";
    private static final String END_RENT_TIME = "endDate";
    private static final String USER_DISCOUNT = "discount";
    private static final String FINAL_COST = "finalCost";
    private static final String IS_CAR_AVAILABLE = "isAvailable";
    private static final String ORDER_CREATED = "orderCreated";
    private static final String ORDER_CREATED_MSG = "Order successfully created";

    private static final String DATABASE_ERROR = "Database error";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String direction;

        HttpSession session = request.getSession(false);

        int userID = (int) session.getAttribute(USER_ID);
        int carID = (int) session.getAttribute(CAR_ID);
        Order order;

        try {
            if (request.getParameter(CONFIRM_ORDER) == null) {
                String rentStart = request.getParameter(RENT_START);
                int rentDays = Integer.parseInt(request.getParameter(RENT_DAYS));

                final long millisecondsInOneDay = 86400000L;

                CarRentInfo carRentInfo = carService.getCarRentInfoByCarID(carID);
                User user = userService.getUserById(userID);

                Date startDate = null;
                Date endDate = null;

                SimpleDateFormat reqFormat = new SimpleDateFormat(DATE_FORMAT);
                SimpleDateFormat respFormat = new SimpleDateFormat();

                try {
                    startDate = reqFormat.parse(String.valueOf(rentStart));
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

                if (startDate != null) {
                    endDate = new Date(startDate.getTime() + millisecondsInOneDay * rentDays);
                }

                short carRentCost = carRentInfo.getRentCost();
                String userPhone = user.getPhoneNumber();
                byte userDiscount = user.getDiscount();
                double finalRentCost = carRentCost * rentDays;

                if (userDiscount != 0) {
                    finalRentCost = finalRentCost - (finalRentCost * userDiscount / 100);
                }

                order = new Order();
                order.setStartRentDate(startDate);
                order.setEndRentDate(endDate);
                order.setCost(finalRentCost);
                order.setFinalCost(finalRentCost);
                order.setCarID(carID);

                session.setAttribute(ORDER, order);

                request.setAttribute(USER_PHONE, userPhone);
                request.setAttribute(START_RENT_TIME, respFormat.format(startDate));
                request.setAttribute(END_RENT_TIME, respFormat.format(endDate));
                request.setAttribute(USER_DISCOUNT, userDiscount);
                request.setAttribute(FINAL_COST, finalRentCost);

                direction = CommandParameterName.GO_TO_CONFIRM_ORDER_PAGE.getDirection();

                RequestDispatcher dispatcher = request.getRequestDispatcher(direction);
                dispatcher.forward(request, response);

            }
            else {
                order = (Order) session.getAttribute(ORDER);
                orderService.addOrder(order, userID);
                boolean isCarAvailable = (boolean) session.getAttribute(IS_CAR_AVAILABLE);
                boolean userHasActiveRent = userService.userHasActiveRent(userID);

                if (isCarAvailable) {
                    carService.changeCarAvailability(carID);
                }
                if (!userHasActiveRent) {
                    userService.changeUserHasActiveRent(userID);
                }

                session.setAttribute(ORDER_CREATED, ORDER_CREATED_MSG);

                direction = CommandParameterName.ADD_ORDER.getDirection();

                response.sendRedirect(direction);
            }
        }
        catch (ServiceException e) {
            if (e.getMessage().equalsIgnoreCase(DATABASE_ERROR)) {
                throw new RuntimeException();
            }
        }
    }
}

