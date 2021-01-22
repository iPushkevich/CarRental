package by.epamtc.pushkevich.controller.command.order;

import by.epamtc.pushkevich.controller.command.Command;
import by.epamtc.pushkevich.controller.command.CommandParameterName;
import by.epamtc.pushkevich.entity.Car;
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
import java.io.IOException;
import java.sql.SQLException;

public class OrderInfoCommand implements Command {
    private final OrderServiceFactory orderServiceFactory = OrderServiceFactory.getInstance();
    private final OrderService orderService = orderServiceFactory.getOrderService();

    private final CarServiceFactory carServiceFactory = CarServiceFactory.getInstance();
    private final CarService carService = carServiceFactory.getCarService();

    private final UserServiceFactory userServiceFactory = UserServiceFactory.getInstance();
    private final UserService userService = userServiceFactory.geUserService();

    private static final String ORDER_ID = "orderID";
    private static final String NEW_ORDER_STATUS = "newOrderStatus";
    private static final String DECLINE_REASON = "declineReason";
    private static final String STATUS_DECLINED = "declined";
    private static final String STATUS_ACCEPTED = "accepted";
    private static final String STATUS_PROCESSED = "processed";
    private static final String STATUS_CHANGED = "statusChanged";
    private static final String STATUS_CHANGED_MSG = "Information successfully saved";
    private static final String ORDER = "order";
    private static final String CAR = "car";
    private static final String USER = "user";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String direction = CommandParameterName.GO_TO_ORDER_INFO_PAGE.getDirection();

        int orderID = Integer.parseInt(request.getParameter(ORDER_ID));
        int carID;
        int userID;

        try {
            Order order = orderService.getOrder(orderID);
            String orderStatus = order.getStatus();

            carID = order.getCarID();
            userID = order.getUserID();

            Car car = carService.getCarByID(carID);
            User user = userService.getUserById(userID);


            String newOrderStatus = request.getParameter(NEW_ORDER_STATUS);
            String declineReason = request.getParameter(DECLINE_REASON);
            String decline = request.getParameter(STATUS_DECLINED);

            if (newOrderStatus != null) {
                if (newOrderStatus.equalsIgnoreCase(STATUS_DECLINED)) {
                    orderStatus = STATUS_DECLINED;
                    request.setAttribute(STATUS_DECLINED, STATUS_DECLINED);
                } else {
                    if (newOrderStatus.equalsIgnoreCase(STATUS_ACCEPTED)) {
                        orderStatus = STATUS_ACCEPTED;
                    }
                    if (newOrderStatus.equalsIgnoreCase(STATUS_PROCESSED)) {
                        orderStatus = STATUS_PROCESSED;
                    }

                    order.setDeclineReason(null);

                    userService.changeUserHasActiveRent(userID);
                    orderService.changeDeclineReason(orderID, null);

                    request.setAttribute(STATUS_CHANGED, STATUS_CHANGED_MSG);
                }
            }

            if (decline != null) {
                if (declineReason.length() == 0) {
                    declineReason = " ";
                }
                order.setDeclineReason(declineReason);

                request.setAttribute(STATUS_CHANGED, STATUS_CHANGED_MSG);

                carService.changeCarAvailability(carID);
                userService.changeUserHasActiveRent(userID);
                orderService.changeDeclineReason(orderID, declineReason);

                order.setDeclineReason(declineReason);
            }

            orderService.changeOrderStatus(orderID, orderStatus);

            order.setStatus(orderStatus);

            request.setAttribute(ORDER, order);
            request.setAttribute(CAR, car);
            request.setAttribute(USER, user);

            RequestDispatcher dispatcher = request.getRequestDispatcher(direction);
            dispatcher.forward(request, response);
        }
        catch (ServiceException e) {

        }
    }
}

