package by.epamtc.pushkevich.service.order;

import by.epamtc.pushkevich.entity.Order;
import by.epamtc.pushkevich.exception.ServiceException;

import java.util.Date;
import java.util.List;

public interface OrderService {
    void addOrder(Order order, int userID) throws ServiceException;

    int getOrderID(Order order) throws ServiceException;

    Order getOrder(int orderID) throws ServiceException;

    Date getCarRentEndDate(int carID) throws ServiceException;

    List<Order> getOrders(int userID) throws ServiceException;

    List<Order> getOrders() throws ServiceException;

    List<Order> getOrders(String phoneNumber) throws ServiceException;

    List<Order> getOrders(String name, String surname) throws ServiceException;

    void changeOrderStatus(int orderID, String status) throws ServiceException;

    void changeDeclineReason(int orderID, String declineReason) throws ServiceException;


}
