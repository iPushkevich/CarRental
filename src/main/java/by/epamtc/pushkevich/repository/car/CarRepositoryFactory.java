package by.epamtc.pushkevich.repository.car;

public class CarRepositoryFactory {
    private static final CarRepositoryFactory INSTANCE = new CarRepositoryFactory();

    private final CarRepository CAR_REPOSITORY = new CarSQLRepository();

    private CarRepositoryFactory() {}

    public CarRepository getCarRepository() {
        return CAR_REPOSITORY;
    }

    public static CarRepositoryFactory getInstance() {
        return INSTANCE;
    }
}
