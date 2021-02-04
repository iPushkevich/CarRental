package by.epamtc.pushkevich.service.car;

import by.epamtc.pushkevich.entity.Car;
import by.epamtc.pushkevich.entity.CarRentInfo;
import by.epamtc.pushkevich.exception.RepositoryException;
import by.epamtc.pushkevich.exception.ServiceException;
import by.epamtc.pushkevich.repository.car.CarRepository;
import by.epamtc.pushkevich.repository.car.CarRepositoryFactory;
import by.epamtc.pushkevich.repository.car.CarSQLRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class is implementation of{@link CarService} service layer for various operations with <code>Car</code>
 * Every method can throw <code>ServiceException</code> with <strong>Database error</strong> message. If you get an exception with this message
 * in your catch block you should throw new <code>RuntimeException</code>
 * @author Igor Pushkevich
 * @version 1.0
 */
public class CarServiceImpl implements CarService{
    private final CarRepositoryFactory factory = CarRepositoryFactory.getInstance();
    private final CarRepository repository = factory.getCarRepository();

    /**
     * @see CarSQLRepository#getAllCars
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public Map<Integer, Car> getAllCars() throws ServiceException {
        try {
            return repository.getAllCars();
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * @see CarSQLRepository#addNewCar
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public void addNewCar(Car car) throws ServiceException {
        try {
            repository.addNewCar(car);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * @see CarSQLRepository#updateCar
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public void updateCar(Car car) throws ServiceException {
        try {
            repository.updateCar(car);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * @see CarSQLRepository#getAllBrands
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public Set<String> getAllBrands() throws ServiceException {
        try {
            return repository.getAllBrands();
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * @see CarSQLRepository#getCarsByBrand
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public Map<Integer, Car> getCarsByBrand(String brand) throws ServiceException {
        try {
            return  repository.getCarsByBrand(brand);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * @see CarSQLRepository#getCarsByModel
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public Map<Integer, Car> getCarsByModel(String model) throws ServiceException {
        try {
            return repository.getCarsByModel(model);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * @see CarSQLRepository#getAllModelsByBrand
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public Set<String> getAllModelsByBrand(String brand) throws ServiceException {
        try {
            return repository.getAllModelsByBrand(brand);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * @see CarSQLRepository#getCarByID
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public Car getCarByID(int carID) throws ServiceException {
        try {
            return repository.getCarByID(carID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * @see CarSQLRepository#getAvailableCars
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public Map<Integer, Car> getAvailableCars(Map<Integer, Car> cars) throws ServiceException {
        try {
            return repository.getAvailableCars(cars);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * @see CarSQLRepository#getCarRentInfoByCarID
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public CarRentInfo getCarRentInfoByCarID(int carID) throws ServiceException {
        try {
            return repository.getCarRentInfoByCarID(carID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * @see CarSQLRepository#addCarRentInfoByCarID
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public void addCarRentInfoByCarID(CarRentInfo rentInfo, int carID) throws ServiceException {
        try {
            repository.addCarRentInfoByCarID(rentInfo, carID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * @see CarSQLRepository#updateCarRentInfoByCarID
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public void updateCarRentInfoByCarID(CarRentInfo rentInfo, int carID) throws ServiceException {
        try {
            repository.updateCarRentInfoByCarID(rentInfo, carID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * @see CarSQLRepository#isCarAvailable
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public boolean isCarAvailable(int carID) throws ServiceException {
        try {
            return repository.isCarAvailable(carID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * @see CarSQLRepository#addCarReviewByCarID
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public void addCarReviewByCarID(String review, int carID) throws ServiceException {
        try {
            repository.addCarReviewByCarID(review, carID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * @see CarSQLRepository#getCarReviewsByCarID
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public List<String> getCarReviewsByCarID(int carID) throws ServiceException {
        try {
            return repository.getCarReviewsByCarID(carID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * @see CarSQLRepository#deleteCarReviewByCarID
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public void deleteCarReviewByCarID(String review, int carID) throws ServiceException {
        try {
            repository.deleteCarReviewByCarID(review, carID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * @see CarSQLRepository#changeCarAvailability
     * @throws ServiceException wrapper for RepositoryException
     */
    @Override
    public void changeCarAvailability(int carID) throws ServiceException {
        try {
            repository.changeCarAvailability(carID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
