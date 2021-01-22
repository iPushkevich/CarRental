package by.epamtc.pushkevich.repository.order;

public class OrderRepositoryFactory {
    private static final OrderRepositoryFactory INSTANCE = new OrderRepositoryFactory();

    private final OrderRepository ORDER_REPOSITORY = new OrderSQLRepository();

    private OrderRepositoryFactory() {}

    public OrderRepository getOrderRepository() {
        return ORDER_REPOSITORY;
    }

    public static OrderRepositoryFactory getInstance() {
        return INSTANCE;
    }
}
