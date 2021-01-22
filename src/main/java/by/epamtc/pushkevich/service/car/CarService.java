package by.epamtc.pushkevich.service.car;

import by.epamtc.pushkevich.entity.Car;
import by.epamtc.pushkevich.entity.CarRentInfo;
import by.epamtc.pushkevich.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CarService {
    Map<Integer, Car> getAllCars() throws ServiceException;

    void addNewCar(Car car) throws ServiceException;

    void updateCar(Car car) throws ServiceException;

    Set<String> getAllBrands() throws ServiceException;

    Map<Integer, Car> getCarsByBrand(String brand) throws ServiceException;

    Map<Integer, Car> getCarsByModel(String model) throws ServiceException;

    Set<String> getAllModelsByBrand(String brand) throws ServiceException;

    Car getCarByID(int carID) throws ServiceException;

    Map<Integer, Car> getAvailableCars(Map<Integer, Car> cars) throws ServiceException;

    CarRentInfo getCarRentInfoByCarID(int carID) throws ServiceException;

    void addCarRentInfoByCarID(CarRentInfo rentInfo, int carID) throws ServiceException;

    void updateCarRentInfoByCarID(CarRentInfo rentInfo, int carID) throws ServiceException;

    boolean isCarAvailable(int carID) throws ServiceException;

    void addCarReviewByCarID(String review, int carID) throws ServiceException;

    List<String> getCarReviewsByCarID(int carID) throws ServiceException;

    void deleteCarReviewByCarID(String review, int carID) throws ServiceException;

    void changeCarAvailability(int carID) throws ServiceException;



}
