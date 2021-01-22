package by.epamtc.pushkevich.service.order;

import by.epamtc.pushkevich.entity.Order;
import by.epamtc.pushkevich.repository.order.OrderRepository;
import by.epamtc.pushkevich.repository.order.OrderRepositoryFactory;

import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderRepositoryFactory factory = OrderRepositoryFactory.getInstance();
    private final OrderRepository repository = factory.getOrderRepository();

    @Override
    public void addOrder(Order order, int userID) {
        repository.addOrder(order, userID);
    }

    @Override
    public int getOrderID(Order order) {
        return repository.getOrderID(order);
    }

    @Override
    public Order getOrder(int orderID) {
        return repository.getOrder(orderID);
    }

    @Override
    public Date getCarRentEndDate(int carID) {
        return repository.getCarRentEndDate(carID);
    }

    @Override
    public List<Order> getOrders(int userID) {
        return repository.getOrders(userID);
    }

    @Override
    public List<Order> getOrders() {
        return repository.getOrders();
    }

    @Override
    public List<Order> getOrders(String phoneNumber) {
        return repository.getOrders(phoneNumber);
    }

    @Override
    public List<Order> getOrders(String name, String surname) {
        return repository.getOrders(name, surname);
    }

    @Override
    public void changeOrderStatus(int orderID, String status) {
        repository.changeOrderStatus(orderID, status);
    }

    @Override
    public void changeDeclineReason(int orderID, String declineReason) {
        repository.changeDeclineReason(orderID, declineReason);
    }

}
