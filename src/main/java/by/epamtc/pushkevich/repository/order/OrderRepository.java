package by.epamtc.pushkevich.repository.order;

import by.epamtc.pushkevich.entity.Order;
import by.epamtc.pushkevich.exception.RepositoryException;

import java.util.Date;
import java.util.List;

public interface OrderRepository {
    void addOrder(Order order, int userID) throws RepositoryException;

    int getOrderID(Order order) throws RepositoryException;

    Order getOrder(int orderID) throws RepositoryException;

    Date getCarRentEndDate(int carID) throws RepositoryException;

    List<Order> getOrders(int userID) throws RepositoryException;

    List<Order> getOrders() throws RepositoryException;

    List<Order> getOrders(String phoneNumber) throws RepositoryException;

    List<Order> getOrders(String name, String surname) throws RepositoryException;

    void changeOrderStatus(int orderID, String status) throws RepositoryException;

    void changeDeclineReason(int orderID, String declineReason) throws RepositoryException;


}
