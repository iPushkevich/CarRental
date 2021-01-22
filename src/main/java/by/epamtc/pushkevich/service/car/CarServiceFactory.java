package by.epamtc.pushkevich.service.car;

public class CarServiceFactory {
    private static final CarServiceFactory INSTANCE = new CarServiceFactory();

    private final CarService CAR_SERVICE = new CarServiceImpl();

    private CarServiceFactory() {}

    public CarService getCarService() {
        return CAR_SERVICE;
    }

    public static CarServiceFactory getInstance() {
        return INSTANCE;
    }
}
