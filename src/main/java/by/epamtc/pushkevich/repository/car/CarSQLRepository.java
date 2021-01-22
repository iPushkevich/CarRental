package by.epamtc.pushkevich.repository.car;

import by.epamtc.pushkevich.entity.Car;
import by.epamtc.pushkevich.entity.CarRentInfo;
import by.epamtc.pushkevich.entity.Transmission;
import by.epamtc.pushkevich.exception.ConnectionPoolException;
import by.epamtc.pushkevich.exception.RepositoryException;
import by.epamtc.pushkevich.repository.connection.ConnectionPool;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

/**
 * The class is MySQL version of the <code>CarRepository</code> implementation.
 * Every method can throw exception with "Database error" message. If you get exception with this message
 * in your catch block you should throw new <exeption>RuntimeException</exeption>.
 * @author Igor Pushkevich
 * @version 1.0
 * */

public class CarSQLRepository implements CarRepository{
    private ConnectionPool pool;
    private final Logger logger = LogManager.getLogger(CarSQLRepository.class);

    private static final String DATABASE_ERROR = "Database error";

    /**
     * Returns all available <code>Car</code>
     * @return list of <code>Car</code>
     * @throws RepositoryException with "Database error" message if has any problem with database and documents problem.
     */
    @Override
    public Map<Integer, Car> getAllCars() throws RepositoryException {
        Map<Integer, Car> cars = new LinkedHashMap<>();

        String query = Query.GET_ALL_CARS;

        return getCarsByCriteria(cars, query);
    }

    /**
     * Add new <code>Car</code> to database
     * @param car
     * @throws RepositoryException with "Database error" message if has any problem with database and documents problem.
     */
    @Override
    public void addNewCar(Car car) throws RepositoryException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String transmissionAuto = "АКПП";

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            int carID = car.getId();
            String brand = car.getBrand();
            String model = car.getModel();
            short year = car.getYear();
            int mileage = car.getMileage();
            String engineType = car.getEngineType();
            double engineSize = car.getEngineSize();
            String transmissionType = car.getTransmissionType();
            String wheelsDrive = car.getWheelsDrive();
            String type = car.getType();

            String transmission = String.valueOf(transmissionType.equalsIgnoreCase(transmissionAuto) ? Transmission.AUTO : Transmission.MANUAL);

            String query = Query.ADD_NEW_CAR;

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, carID);
            preparedStatement.setString(2, brand.toUpperCase());
            preparedStatement.setString(3, model.toUpperCase());
            preparedStatement.setShort(4, year);
            preparedStatement.setInt(5, mileage);
            preparedStatement.setString(6, engineType);
            preparedStatement.setDouble(7, engineSize);
            preparedStatement.setString(8, transmission);
            preparedStatement.setString(9, wheelsDrive);
            preparedStatement.setString(10, type);

            preparedStatement.executeUpdate();
        }
        catch (SQLException | ConnectionPoolException e){
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * Execute statement and update information about <code>Car</code>
     * @param car current car
     * @throws RepositoryException with "Database error" message if has any problem with database and documents problem.
     */
    @Override
    public void updateCar(Car car) throws RepositoryException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            int carID = car.getId();
            String brand = car.getBrand();
            String model = car.getModel();
            short year = car.getYear();
            int mileage = car.getMileage();
            String engineType = car.getEngineType();
            double engineSize = car.getEngineSize();
            String transmissionType = car.getTransmissionType();
            String wheelsDrive = car.getWheelsDrive();
            String type = car.getType();

            String transmission = String.valueOf(transmissionType.equalsIgnoreCase("АКПП") ? Transmission.AUTO : Transmission.MANUAL);

            String query = Query.UPDATE_CAR + carID;

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, brand.toUpperCase());
            preparedStatement.setString(2, model.toUpperCase());
            preparedStatement.setShort(3, year);
            preparedStatement.setInt(4, mileage);
            preparedStatement.setString(5, engineType);
            preparedStatement.setDouble(6, engineSize);
            preparedStatement.setString(7, transmission);
            preparedStatement.setString(8, wheelsDrive);
            preparedStatement.setString(9, type);

            preparedStatement.close();
        }
        catch (SQLException | ConnectionPoolException e){
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * @return List of all available car brands.
     * @throws RepositoryException with "Database error" message if has any problem with database and documents problem.
     */
    @Override
    public Set<String> getAllBrands() throws RepositoryException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set;

        Set<String> carBrands;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            carBrands = new TreeSet<>();
            String brand;

            String query = Query.GET_ALL_BRANDS;

            statement = connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()){
                brand = set.getString(1);
                carBrands.add(brand.toUpperCase());
            }
        }
        catch (SQLException | ConnectionPoolException e){
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, statement);
        }

        return carBrands;
    }

    /**
     * Returns list of all available car models by one car brand.
     * @param brand one of available car brand
     * @return list of car models
     * @throws RepositoryException with "Database error" message if has any problem with database and documents problem.
     */
    @Override
    public Set<String> getAllModelsByBrand(String brand) throws RepositoryException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        Set<String> carModels;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            carModels = new TreeSet<>();
            String model;
            String sqlModel = "'" + brand.toUpperCase() + "'";

            String query = Query.GET_ALL_MODELS + sqlModel;

            statement = connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()){
                model = set.getString(1);
                carModels.add(model.toUpperCase());
            }
        }
        catch (SQLException | ConnectionPoolException e){
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, statement, set);
        }

        return carModels;
    }

    /**
     * Returns Map<Integer, Car>, where key is <code>Car</code> id.
     * @param brand one of available car brand
     * @return map where key is car id
     * @throws RepositoryException with "Database error" message if has any problem with database and documents problem.
     */
    @Override
    public Map<Integer, Car> getCarsByBrand(String brand) throws RepositoryException {
        Map<Integer, Car> cars = new HashMap<>();

        String sqlBrand = "'" + brand.toUpperCase() + "'";

        String query = Query.GET_CARS_BY_BRAND + sqlBrand;

        return getCarsByCriteria (cars, query);
    }

    /**
     * Returns Map<Integer, Car>, where key is <code>Car</code> id.
     * @param model one of available car models
     * @return map where key is car id
     * @throws RepositoryException with "Database error" message if has any problem with database and documents problem.
     */
    @Override
    public Map<Integer, Car> getCarsByModel(String model) throws RepositoryException {
        Map<Integer, Car> cars = new HashMap<>();

        String sqlBrand = "'" + model.toUpperCase() + "'";

        String query = Query.GET_CARS_BY_MODEL + sqlBrand;

        return getCarsByCriteria(cars, query);

    }

    /**
     * Returns <code>Car</code> based on car`s id.
     * @param carID unique car identifier
     * @return <code>Car</code>
     * @throws RepositoryException with "Database error" message if has any problem with database and documents problem.
     */
    @Override
    public Car getCarByID(int carID) throws RepositoryException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        Car car = null;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            String query = Query.GET_CAR_BY_ID + carID;

            statement = connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()){
                car = new Car();

                car.setId(carID);
                car.setBrand(set.getString(2).toUpperCase());
                car.setModel(set.getString(3).toUpperCase());
                car.setYear(set.getShort(4));
                car.setMileage(set.getInt(5));
                car.setEngineType(set.getString(6));
                car.setEngineSize(set.getDouble(7));
                car.setTransmissionType(set.getString(8));
                car.setWheelsDrive(set.getString(9));
                car.setType(set.getString(10));
            }
        }
        catch (SQLException | ConnectionPoolException e){
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, statement, set);
        }

        return car;
    }

    /**
     * Returns all available cars from all existing cars.
     * @param cars map of all existing cars
     * @return map of all available cars
     * @throws RepositoryException with "Database error" message if has any problem with database and documents problem.
     */
    @Override
    public Map<Integer, Car> getAvailableCars(Map<Integer, Car> cars) throws RepositoryException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        Map<Integer, Car> availableCars;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            availableCars = new HashMap<>();
            List<Integer> availableID = new ArrayList<>();

            String query = Query.GET_AVAILABLE_CAR_ID;

            statement = connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()){
                availableID.add(set.getInt(1));
            }

            for (Map.Entry<Integer, Car> carEntry : cars.entrySet()){
                if (availableID.contains(carEntry.getKey())){
                    availableCars.put(carEntry.getKey(), carEntry.getValue());
                }
            }
        }
        catch (SQLException | ConnectionPoolException e){
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, statement, set);
        }

        return availableCars;
    }

    /**
     * Checks if a car is available for order
     * @param carID unique car identifier
     * @return true if car is available for order
     * @throws RepositoryException with "Database error" message if has any problem with database and documents problem.
     */
    @Override
    public boolean isCarAvailable(int carID) throws RepositoryException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        boolean isAvailable = false;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            String query = Query.IS_CAR_AVAILABLE + carID;

            statement = connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()){
                isAvailable = set.getBoolean(1);
            }
        }
        catch (SQLException | ConnectionPoolException e){
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, statement, set);
        }

        return isAvailable;
    }

    /**
     * Returns <code>CarRentInfo</code information about a car rent.
     * @param carID unique car identifier
     * @return car rent information
     * @throws RepositoryException with "Database error" message if has any problem with database and documents problem.
     */
    @Override
    public CarRentInfo getCarRentInfoByCarID(int carID) throws RepositoryException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        CarRentInfo carRentInfo;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            carRentInfo = new CarRentInfo();

            String query = Query.GET_CAR_RENT_INFO + carID;

            statement = connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()){
                carRentInfo.setDescription(set.getString(2));
                carRentInfo.setRentCost(set.getShort(3));
                carRentInfo.setCarRating(set.getDouble(4));
                carRentInfo.setFuelLvl(set.getDouble(5));
            }
        }
        catch (SQLException | ConnectionPoolException e){
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, statement, set);
        }

        return carRentInfo;
    }

    /**
     * Add <code>CarRentInfo</code> based on car id.
     * @param rentInfo information about car rent
     * @param carID unique car identifier
     * @throws RepositoryException with "Database error" message if has any problem with database and documents problem.
     */
    @Override
    public void addCarRentInfoByCarID(CarRentInfo rentInfo, int carID) throws RepositoryException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            String description = rentInfo.getDescription();
            short rentCost = rentInfo.getRentCost();
            double rating = rentInfo.getCarRating();
            double fuelLvl = rentInfo.getFuelLvl();


            String query = Query.ADD_CAR_RENT_INFO;

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,description);
            preparedStatement.setShort(2, rentCost);
            preparedStatement.setDouble(3, rating);
            preparedStatement.setDouble(4, fuelLvl);
            preparedStatement.setInt(5, carID);

            preparedStatement.executeUpdate();
        }
        catch (SQLException | ConnectionPoolException e){
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * Update <code>CarRentInfo</code> based on car id.
     * @param rentInfo information about car rent
     * @param carID unique car identifier
     * @throws RepositoryException with "Database error" message if has any problem with database and documents problem.
     */
    @Override
    public void updateCarRentInfoByCarID(CarRentInfo rentInfo, int carID) throws RepositoryException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            String description = rentInfo.getDescription();
            short rentCost = rentInfo.getRentCost();
            double rating = rentInfo.getCarRating();
            double fuelLvl = rentInfo.getFuelLvl();


            String query = Query.UPDATE_CAR_RENT_INFO + carID;

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,description);
            preparedStatement.setShort(2, rentCost);
            preparedStatement.setDouble(3, rating);
            preparedStatement.setDouble(4, fuelLvl);

            preparedStatement.executeUpdate();
        }
        catch (SQLException | ConnectionPoolException e){
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * Returns list of all car reviews based on car id.
     * @param carID unique car identifier
     * @return list of car reviews
     * @throws RepositoryException with "Database error" message if has any problem with database and documents problem.
     */
    @Override
    public List<String> getCarReviewsByCarID(int carID) throws RepositoryException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        List<String> reviews;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            reviews = new ArrayList<>();

            String query = Query.GET_CAR_REVIEWS + carID;

            statement = connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()){
                reviews.add(set.getString(1));
            }
        }
        catch (SQLException | ConnectionPoolException e){
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, statement, set);
        }

        return reviews;
    }

    /**
     * Add review about car reviews based on car id.
     * @param review car review
     * @param carID unique car identifier
     * @throws RepositoryException with "Database error" message if has any problem with database and documents problem.
     */
    @Override
    public void addCarReviewByCarID(String review, int carID) throws RepositoryException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            String query = Query.ADD_CAR_REVIEW;

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, review);
            preparedStatement.setInt(2, carID);

            preparedStatement.executeUpdate();
        }
        catch (SQLException | ConnectionPoolException e){
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * Deletes review about car reviews based on car id.
     * @param review car review
     * @param carID unique car identifier
     * @throws RepositoryException with "Database error" message if has any problem with database and documents problem.
     */
    @Override
    public void deleteCarReviewByCarID(String review, int carID) throws RepositoryException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            String query = Query.DELETE_CAR_REVIEW;

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, review);
            preparedStatement.setInt(2, carID);

            preparedStatement.execute();
        }
        catch (SQLException | ConnectionPoolException e){
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * Changes car availability for order based on car id.
     * @param carID unique car identifier
     * @throws RepositoryException with "Database error" message if has any problem with database and documents problem.
     */
    @Override
    public void changeCarAvailability(int carID) throws RepositoryException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            boolean isValid = isCarAvailable(carID);
            byte swapAvailability = 1;

            if (isValid){
                swapAvailability = 0;
            }

            preparedStatement = connection.prepareStatement(Query.CHANGE_CAR_AVAILABILITY);

            preparedStatement.setInt(1, swapAvailability);
            preparedStatement.setInt(2, carID);

            preparedStatement.executeUpdate();
        }
        catch (SQLException | ConnectionPoolException e){
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * General method for finding cars by criteria.
     * @param cars map of cars
     * @param query statement for finding
     * @return map where key is car id, value is <code>Car</code>
     * @throws RepositoryException with "Database error" message if has any problem with database and documents problem.
     */
    private Map<Integer, Car> getCarsByCriteria (Map<Integer, Car> cars, String query) throws RepositoryException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        Car car;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();
            statement = connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()){
                car = new Car();

                car.setId(set.getInt(1));
                car.setBrand(set.getString(2).toUpperCase());
                car.setModel(set.getString(3).toUpperCase());
                car.setYear(set.getShort(4));
                car.setMileage(set.getInt(5));
                car.setEngineType(set.getString(6));
                car.setEngineSize(set.getDouble(7));
                car.setTransmissionType(set.getString(8));
                car.setWheelsDrive(set.getString(9));
                car.setType(set.getString(10));

                cars.put(car.getId(),car);
            }
        }
        catch (SQLException | ConnectionPoolException e){
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, statement, set);
        }

        return cars;
    }

    /**
     * Inner class for queries
     */
    private static class Query{
        private static final String GET_ALL_CARS = "SELECT * FROM cars ORDER BY car_brand";

        private static final String GET_AVAILABLE_CAR_ID = "SELECT car_id FROM cars WHERE is_car_available = 1";

        private static final String ADD_NEW_CAR = "REPLACE INTO cars (car_id, car_brand, car_model, car_year, car_mileage, car_engine_type, car_engine_size, car_transmission, " +
                "car_wheels_drive, car_type) VALUES (?,?,?,?,?,?,?,?,?,?)";

        private static final String UPDATE_CAR = "UPDATE cars SET car_brand = ?, car_model = ?, car_year = ?, car_mileage = ?," +
                " car_engine_type = ?, car_engine_size = ?, car_transmission = ?, car_wheels_drive = ?, car_type = ? WHERE car_id = ";

        private static final String GET_ALL_BRANDS = "SELECT car_brand FROM cars ORDER BY car_brand";

        private static final String GET_ALL_MODELS = "SELECT car_model FROM cars WHERE car_brand = ";

        private static final String GET_CARS_BY_BRAND = "SELECT * FROM cars WHERE car_brand = ";

        private static final String GET_CARS_BY_MODEL = "SELECT * FROM cars WHERE car_model = ";

        private static final String GET_CAR_BY_ID = "SELECT * FROM cars WHERE car_id = ";

        private static final String GET_CAR_RENT_INFO = "SELECT * FROM cars_rent_info WHERE car_id = ";

        private static final String ADD_CAR_RENT_INFO = "REPLACE INTO cars_rent_info(car_description, car_rent_cost, car_rating, car_fuel_lvl, car_id)" +
                "VALUES (?,?,?,?,?)";

        private static final String UPDATE_CAR_RENT_INFO = "UPDATE cars_rent_info SET car_description = ?, car_rent_cost = ?," +
                " car_rating = ?, car_fuel_lvl = ? WHERE car_id = ";

        private static final String IS_CAR_AVAILABLE = "SELECT is_car_available FROM cars WHERE car_id = ";

        private static final String GET_CAR_REVIEWS = "SELECT car_review FROM cars_reviews WHERE car_id = ";

        private static final String ADD_CAR_REVIEW = "REPLACE INTO cars_reviews (car_review, car_id) VALUES (?,?)";

        private static final String DELETE_CAR_REVIEW = "DELETE FROM cars_reviews WHERE (car_review, car_id) = (?,?) ";

        private static final String CHANGE_CAR_AVAILABILITY = "UPDATE cars SET is_car_available = ? WHERE car_id = ?";
    }
}

