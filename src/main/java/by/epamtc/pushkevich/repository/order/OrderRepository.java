package by.epamtc.pushkevich.repository.order;

import by.epamtc.pushkevich.entity.Order;

import java.util.Date;
import java.util.List;

public interface OrderRepository {
    void addOrder(Order order, int userID);

    int getOrderID(Order order);

    Order getOrder(int orderID);

    Date getCarRentEndDate(int carID);

    List<Order> getOrders(int userID);

    List<Order> getOrders();

    List<Order> getOrders(String phoneNumber);

    List<Order> getOrders(String name, String surname);

    void changeOrderStatus(int orderID, String status);

    void changeDeclineReason(int orderID, String declineReason);


}
