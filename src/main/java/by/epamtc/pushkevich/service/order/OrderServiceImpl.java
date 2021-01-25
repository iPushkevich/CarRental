package by.epamtc.pushkevich.service.order;

import by.epamtc.pushkevich.entity.Order;
import by.epamtc.pushkevich.exception.RepositoryException;
import by.epamtc.pushkevich.exception.ServiceException;
import by.epamtc.pushkevich.repository.order.OrderRepository;
import by.epamtc.pushkevich.repository.order.OrderRepositoryFactory;

import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderRepositoryFactory factory = OrderRepositoryFactory.getInstance();
    private final OrderRepository repository = factory.getOrderRepository();

    @Override
    public void addOrder(Order order, int userID) throws ServiceException {
        try {
            repository.addOrder(order, userID);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getOrderID(Order order) throws ServiceException {
        try {
            return repository.getOrderID(order);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Order getOrder(int orderID) throws ServiceException {
        try {
            return repository.getOrder(orderID);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Date getCarRentEndDate(int carID) throws ServiceException {
        try {
            return repository.getCarRentEndDate(carID);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrders(int userID) throws ServiceException {
        try {
            return repository.getOrders(userID);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrders() throws ServiceException {
        try {
            return repository.getOrders();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrders(String phoneNumber) throws ServiceException {
        try {
            return repository.getOrders(phoneNumber);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrders(String name, String surname) throws ServiceException {
        try {
            return repository.getOrders(name, surname);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeOrderStatus(int orderID, String status) throws ServiceException {
        try {
            repository.changeOrderStatus(orderID, status);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeDeclineReason(int orderID, String declineReason) throws ServiceException {
        try {
            repository.changeDeclineReason(orderID, declineReason);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

}
