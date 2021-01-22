package by.epamtc.pushkevich.controller.command.order;

import by.epamtc.pushkevich.controller.command.Command;
import by.epamtc.pushkevich.controller.command.CommandParameterName;
import by.epamtc.pushkevich.entity.Order;
import by.epamtc.pushkevich.service.order.OrderService;
import by.epamtc.pushkevich.service.order.OrderServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AllOrdersCommand implements Command {
    private final OrderServiceFactory orderServiceFactory = OrderServiceFactory.getInstance();
    private final OrderService orderService = orderServiceFactory.getOrderService();
    
    private static final String SEARCH_CRITERIA = "searchCriteria";
    private static final String NEW_ORDERS = "newOrders";
    private static final String SEARCH_BY = "searchBy";
    private static final String ORDER_ID_SEARCH = "orderIDSearch";
    private static final String ORDER_ID = "orderID";
    private static final String WRONG_INPUT = "wrongInput";
    private static final String WRONG_INPUT_MSG = "Wrong input";
    private static final String PHONE_NUMBER_SEARCH = "phoneNumberSearch";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String NAME_SURNAME_SEARCH = "nameSurnameSearch";
    private static final String NAME = "name";
    private static final String SURNAME= "surname";
    private static final String PROCESSED_STATUS = "обрабатывается";
    private static final String ORDERS = "orders";
    private static final String NO_ORDERS = "noOrders";
    private static final String NO_ORDERS_MSG = "No orders";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String direction = CommandParameterName.GO_TO_ALL_ORDERS_PAGE.getDirection();

        List<Order> orders = new ArrayList<>();
        String searchCriteria = request.getParameter(SEARCH_CRITERIA);
        String onlyNewOrders = request.getParameter(NEW_ORDERS);
        String searchBy = request.getParameter(SEARCH_BY);

        if (searchBy == null) {
            orders = orderService.getOrders();
        }
        
        else if (searchBy.equals(ORDER_ID_SEARCH)) {
            String orderID = request.getParameter(ORDER_ID);

            try {
                 int id = Integer.parseInt(orderID);
                 Order order = orderService.getOrder(id);
                 orders.add(order);
            }
            catch (ClassCastException e){
                request.setAttribute(WRONG_INPUT, WRONG_INPUT_MSG);
            }
        }

        else if (searchBy.equals(PHONE_NUMBER_SEARCH)) {
            String phoneNumber = request.getParameter(PHONE_NUMBER);

            orders = orderService.getOrders(phoneNumber);

            request.setAttribute(PHONE_NUMBER_SEARCH, PHONE_NUMBER_SEARCH);
        }

        else if (searchBy.equals(NAME_SURNAME_SEARCH)) {
            String name = request.getParameter(NAME);
            String surname = request.getParameter(SURNAME);

            orders = orderService.getOrders(name, surname);

            request.setAttribute(NAME_SURNAME_SEARCH, NAME_SURNAME_SEARCH);
        }

        if (searchCriteria != null) {
            if (searchCriteria.equals(ORDER_ID_SEARCH)) {
                request.setAttribute(ORDER_ID_SEARCH, ORDER_ID_SEARCH);
            }

            if (searchCriteria.equals(PHONE_NUMBER_SEARCH)) {
                request.setAttribute(PHONE_NUMBER_SEARCH, PHONE_NUMBER_SEARCH);
            }

            if (searchCriteria.equals(NAME_SURNAME_SEARCH)) {
                request.setAttribute(NAME_SURNAME_SEARCH, NAME_SURNAME_SEARCH);
            }
        }

        if (onlyNewOrders != null) {
            Iterator<Order> iterator = orders.iterator();
            Order order;

            while (iterator.hasNext()) {
                order = iterator.next();
                if (!order.getStatus().equals(PROCESSED_STATUS)) {
                    iterator.remove();
                }
            }

            request.setAttribute(NEW_ORDERS, NEW_ORDERS);
        }

        if (orders.size() == 0){
            request.setAttribute(NO_ORDERS, NO_ORDERS_MSG);
        }

        request.setAttribute(ORDERS, orders);

        RequestDispatcher dispatcher = request.getRequestDispatcher(direction);
        dispatcher.forward(request, response);

    }
}
