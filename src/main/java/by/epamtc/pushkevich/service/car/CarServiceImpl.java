package by.epamtc.pushkevich.service.car;

import by.epamtc.pushkevich.entity.Car;
import by.epamtc.pushkevich.entity.CarRentInfo;
import by.epamtc.pushkevich.exception.RepositoryException;
import by.epamtc.pushkevich.exception.ServiceException;
import by.epamtc.pushkevich.repository.car.CarRepository;
import by.epamtc.pushkevich.repository.car.CarRepositoryFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CarServiceImpl implements CarService{
    private final CarRepositoryFactory factory = CarRepositoryFactory.getInstance();
    private final CarRepository repository = factory.getCarRepository();

    @Override
    public Map<Integer, Car> getAllCars() throws ServiceException {
        try {
            return repository.getAllCars();
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addNewCar(Car car) throws ServiceException {
        try {
            repository.addNewCar(car);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateCar(Car car) throws ServiceException {
        try {
            repository.updateCar(car);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Set<String> getAllBrands() throws ServiceException {
        try {
            return repository.getAllBrands();
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<Integer, Car> getCarsByBrand(String brand) throws ServiceException {
        try {
            return  repository.getCarsByBrand(brand);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<Integer, Car> getCarsByModel(String model) throws ServiceException {
        try {
            return repository.getCarsByModel(model);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Set<String> getAllModelsByBrand(String brand) throws ServiceException {
        try {
            return repository.getAllModelsByBrand(brand);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Car getCarByID(int carID) throws ServiceException {
        try {
            return repository.getCarByID(carID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<Integer, Car> getAvailableCars(Map<Integer, Car> cars) throws ServiceException {
        try {
            return repository.getAvailableCars(cars);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public CarRentInfo getCarRentInfoByCarID(int carID) throws ServiceException {
        try {
            return repository.getCarRentInfoByCarID(carID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addCarRentInfoByCarID(CarRentInfo rentInfo, int carID) throws ServiceException {
        try {
            repository.addCarRentInfoByCarID(rentInfo, carID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateCarRentInfoByCarID(CarRentInfo rentInfo, int carID) throws ServiceException {
        try {
            repository.updateCarRentInfoByCarID(rentInfo, carID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isCarAvailable(int carID) throws ServiceException {
        try {
            return repository.isCarAvailable(carID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addCarReviewByCarID(String review, int carID) throws ServiceException {
        try {
            repository.addCarReviewByCarID(review, carID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<String> getCarReviewsByCarID(int carID) throws ServiceException {
        try {
            return repository.getCarReviewsByCarID(carID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteCarReviewByCarID(String review, int carID) throws ServiceException {
        try {
            repository.deleteCarReviewByCarID(review, carID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeCarAvailability(int carID) throws ServiceException {
        try {
            repository.changeCarAvailability(carID);
        }
        catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
