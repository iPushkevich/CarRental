package by.epamtc.pushkevich.repository.car;

import by.epamtc.pushkevich.entity.Car;
import by.epamtc.pushkevich.entity.CarRentInfo;
import by.epamtc.pushkevich.exception.RepositoryException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CarRepository {
    Map<Integer, Car> getAllCars() throws RepositoryException;

    void addNewCar(Car car) throws RepositoryException;

    void updateCar(Car car) throws RepositoryException;

    Set<String> getAllBrands() throws RepositoryException;

    Set<String> getAllModelsByBrand(String brand) throws RepositoryException;

    Map<Integer, Car> getCarsByBrand(String brand) throws RepositoryException;

    Map<Integer, Car> getCarsByModel(String model) throws RepositoryException;

    Car getCarByID(int carID) throws RepositoryException;

    Map<Integer, Car> getAvailableCars(Map<Integer, Car> cars) throws RepositoryException;

    boolean isCarAvailable(int carID) throws RepositoryException;

    CarRentInfo getCarRentInfoByCarID(int carID) throws RepositoryException;

    void addCarRentInfoByCarID(CarRentInfo rentInfo, int carID) throws RepositoryException;

    void updateCarRentInfoByCarID(CarRentInfo rentInfo, int carID) throws RepositoryException;

    List<String> getCarReviewsByCarID(int carID) throws RepositoryException;

    void addCarReviewByCarID(String review, int carID) throws RepositoryException;

    void deleteCarReviewByCarID(String review, int carID) throws RepositoryException;

    void changeCarAvailability(int carID) throws RepositoryException;
}
