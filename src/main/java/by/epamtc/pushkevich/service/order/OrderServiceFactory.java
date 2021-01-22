package by.epamtc.pushkevich.service.order;

public class OrderServiceFactory {
    private static final OrderServiceFactory INSTANCE = new OrderServiceFactory();

    private final OrderService ORDER_SERVICE = new OrderServiceImpl();

    private OrderServiceFactory() {}

    public OrderService getOrderService() {
        return ORDER_SERVICE;
    }

    public static OrderServiceFactory getInstance() {
        return INSTANCE;
    }
}
