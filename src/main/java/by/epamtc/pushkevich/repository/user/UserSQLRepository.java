package by.epamtc.pushkevich.repository.user;

import by.epamtc.pushkevich.entity.User;
import by.epamtc.pushkevich.exception.ConnectionPoolException;
import by.epamtc.pushkevich.exception.RepositoryException;
import by.epamtc.pushkevich.exception.UserNotFoundException;
import by.epamtc.pushkevich.exception.WrongPasswordException;
import by.epamtc.pushkevich.repository.connection.ConnectionPool;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Date;
import java.util.*;

public class UserSQLRepository implements UserRepository {
    private ConnectionPool pool;
    private static final Logger logger = LogManager.getLogger(UserRepository.class);

    private static final String DATABASE_ERROR = "Database error";
    private static final String USER_NOT_FOUND = "User not found";
    private static final String WRONG_PASSWORD = "Wrong password";

    @Override
    public Map<Integer, User> getAllUsers() throws RepositoryException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;
        Map<Integer, User> users;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            users = new HashMap<>();
            User user;

            String query = Query.GET_ALL_USERS;

            statement = connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()) {
                user = new User();

                int id = set.getInt(1);

                user.setEmail(set.getString(2));
                user.setName(set.getString(3));
                user.setSurName(set.getString(4));
                user.setAge(set.getByte(5));
                user.setPhoneNumber(set.getString(6));
                user.setPassport(set.getString(7));
                user.setDrivingExperience(set.getByte(8));
                user.setRoadAccidentCount(set.getShort(9));
                user.setPreviousRentCount(set.getShort(10));
                user.setDiscount(set.getByte(11));

                users.put(id, user);
            }
        }
        catch (SQLException | ConnectionPoolException e) {
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, statement, set);
        }

        return users;
    }

    @Override
    public void addNewUser(User user) throws RepositoryException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            String email = user.getEmail();
            String password = user.getPassword();

            String query = Query.ADD_NEW_USER;

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, email.toLowerCase());
            preparedStatement.setString(2, password);

            preparedStatement.executeUpdate();
        }
        catch (SQLException | ConnectionPoolException e) {
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public boolean isUserExists(String email) throws RepositoryException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        String sqlEmail = "'" + email + "'";
        String resultEmail = "";

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            String query = Query.IS_USER_EXISTS + sqlEmail;

            statement = connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()) {
                resultEmail = set.getString(1);
            }
        }
        catch (SQLException | ConnectionPoolException e) {
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, statement, set);
        }

        return resultEmail.equals(email);
    }

    @Override
    public String getUserHashPassword(String email) throws RepositoryException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        String hashPassword = "-1";
        String sqlEmail = "'" + email + "'";

        String query = Query.GET_HASH_PASSWORD + sqlEmail;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            statement = connection.createStatement();

            set = statement.executeQuery(query);

            while (set.next()){
                hashPassword = set.getString(1);
            }
        }
        catch (SQLException | ConnectionPoolException e) {
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, statement, set);
        }

        return hashPassword;
    }

    @Override
    public User getUser(String email, String password) throws RepositoryException {
        if (!isUserExists(email)) {
            UserNotFoundException exception = new UserNotFoundException(USER_NOT_FOUND);
            throw new RepositoryException(exception.getMessage());
        }

        int id = getUserId(email, password);
        User user = getUserById(id);

        if (user.getPassword() == null) {
            WrongPasswordException exception = new WrongPasswordException(WRONG_PASSWORD);
            throw new RepositoryException(exception.getMessage());
        }

        return user;
    }

    @Override
    public int getUserId(String email, String password) throws RepositoryException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        int id = 0;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            String sqlEmail = "'" + email.toLowerCase() + "'";
            String sqlPassword = "'" + password + "'";

            String query = Query.GET_USER_ID + "(" + sqlEmail + ", " + sqlPassword + ")";

            statement = connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()) {
                id = set.getInt(1);
            }
        }
        catch (SQLException | ConnectionPoolException e) {
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, statement, set);
        }

        return id;
    }

    @Override
    public User getUserById(int id) throws RepositoryException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        User user = new User();

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            String query = Query.GET_USER_BY_ID + id;

            statement = connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()) {
                user.setEmail(set.getString(1));
                user.setPassword(set.getString(2));
                user.setName(set.getString(3));
                user.setSurName(set.getString(4));
                user.setAge(set.getByte(5));
                user.setPhoneNumber(set.getString(6));
                user.setPassport(set.getString(7));
                user.setDrivingExperience(set.getByte(8));
                user.setRoadAccidentCount(set.getShort(9));
                user.setPreviousRentCount(set.getShort(10));
                user.setDiscount(set.getByte(11));
            }
        }
        catch (SQLException | ConnectionPoolException e) {
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, statement, set);
        }

        return user;
    }

    @Override
    public User changeUserInfo(User user) throws RepositoryException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            String name = user.getName();
            String surName = user.getSurName();
            byte age = user.getAge();
            String phone = user.getPhoneNumber();
            String passport = user.getPassport();
            byte drivingExperience = user.getDrivingExperience();
            short roadAccident = user.getRoadAccidentCount();

            int userID = getUserId(user.getEmail(), user.getPassword());
            int userInfoID = getUserInfoID(userID);

            String query = Query.CHANGE_USER_INFO;

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surName);
            preparedStatement.setByte(3, age);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, passport);
            preparedStatement.setByte(6, drivingExperience);
            preparedStatement.setShort(7, roadAccident);
            preparedStatement.setInt(8, userID);
            preparedStatement.setInt(9, userInfoID);

            user.setName(name);
            user.setSurName(surName);
            user.setAge(age);
            user.setPassport(passport);
            user.setDrivingExperience(drivingExperience);
            user.setRoadAccidentCount(roadAccident);

            preparedStatement.executeUpdate();
        }
        catch (SQLException | ConnectionPoolException e) {
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, preparedStatement);
        }

        return user;
    }

    @Override
    public void setUserRoleByUserId(int userID, String role) throws RepositoryException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            String query = Query.SET_USER_ROLE_BY_ID;

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, role);
            preparedStatement.setInt(2, userID);

            preparedStatement.executeUpdate();
        }
        catch (SQLException | ConnectionPoolException e) {
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public String getUserRoleByUserId(int id) throws RepositoryException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        String role = null;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            String query = Query.GET_USER_ROLE_BY_USER_ID + id;

            statement = connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()) {
                role = set.getString(1);
            }
        }
        catch (SQLException | ConnectionPoolException e) {
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, statement, set);
        }

        return role;
    }

    @Override
    public Date getUserRegistrationDateByEmail(String email) throws RepositoryException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        Date date = null;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            String sqlEmail = "'" + email + "'";
            String query = Query.GET_USER_REGISTRATION_DATE + sqlEmail;

            statement = connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()) {
                date = set.getTimestamp(1);
            }
        }
        catch (SQLException | ConnectionPoolException e) {
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, statement, set);
        }

        return date;
    }

    @Override
    public boolean userHasActiveRent(int userID) throws RepositoryException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        boolean isUserHasActiveRent = false;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            String query = Query.IS_USER_HAS_ACTIVE_RENT + userID;

            statement = connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()) {
                isUserHasActiveRent = set.getBoolean(1);
            }
        }
        catch (SQLException | ConnectionPoolException e) {
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, statement, set);
        }

        return isUserHasActiveRent;
    }

    @Override
    public void changeUserHasActiveRent(int userID) throws RepositoryException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            boolean swapActiveRent = true;
            boolean activeRent = userHasActiveRent(userID);

            if (activeRent) {
                swapActiveRent = false;
            }

            String query = Query.CHANGE_USER_ACTIVE_RENT;

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setBoolean(1, swapActiveRent);
            preparedStatement.setInt(2, userID);

            preparedStatement.executeUpdate();
        }
        catch (SQLException | ConnectionPoolException e) {
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public List<Integer> getUsersIDByOrderID(int orderID) throws RepositoryException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        List<Integer> usersID;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            usersID = new ArrayList<>();

            String query = Query.GET_USERS_ID_BY_ORDER_ID;

            statement = connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()) {
                int id = set.getInt(1);
                usersID.add(id);
            }
        }
        catch (SQLException | ConnectionPoolException e) {
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, statement, set);
        }

        return usersID;
    }

    @Override
    public int getUserInfoID(int userID) throws RepositoryException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        int userInfoID = 0;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            String query = Query.GET_USER_INFO_ID + userID;

            statement = connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()) {
                userInfoID = set.getInt(1);
            }
        }
        catch (SQLException | ConnectionPoolException e) {
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, statement, set);
        }

        return userInfoID;
    }

    private static class Query {
        private static final String GET_ALL_USERS = "SELECT u.user_id, u.user_email, ui.user_name, ui.user_surname," +
                "ui.user_age, ui.user_phone_number, ui.user_passport, ui.user_driving_experience," +
                "ui.user_road_accident_count, ui.user_previous_rent_count, ui.user_discount" +
                " FROM users u LEFT JOIN users_info ui ON ui.user_id = u.user_id";

        private static final String ADD_NEW_USER = "REPLACE INTO users (user_email, user_password) VALUES (?,?)";

        private static final String IS_USER_EXISTS = "SELECT user_email FROM users WHERE user_email = ";

        private static final String GET_HASH_PASSWORD = "SELECT user_password FROM users WHERE user_email = ";

        private static final String GET_USER_BY_EMAIL_AND_PASS = "SELECT u.user_email, u.user_password, ui.user_name, ui.user_surname," +
                "ui.user_age,ui.user_phone_number, ui.user_passport, ui.user_driving_experience," +
                "ui.user_road_accident_count, ui.user_previous_rent_count, ui.user_discount" +
                " FROM users u JOIN users_info ui ON ui.user_id = u.user_id WHERE (u.user_email, u.user_password) = ";

        private static final String GET_USER_ID = "SELECT user_id FROM users WHERE (user_email, user_password) = ";

        private static final String SET_USER_ROLE_BY_ID = "UPDATE users u  " +
                "SET u.user_role_id = (SELECT r.role_id FROM roles r  WHERE r.user_role = ?) WHERE u.user_id = ?";

        private static final String GET_USER_BY_ID = "SELECT u.user_email, u.user_password, ui.user_name, ui.user_surname," +
                "ui.user_age,ui.user_phone_number, ui.user_passport, ui.user_driving_experience," +
                "ui.user_road_accident_count, ui.user_previous_rent_count, ui.user_discount" +
                " FROM users u LEFT JOIN users_info ui ON ui.user_id = u.user_id  WHERE u.user_id = ";

        private static final String CHANGE_USER_INFO = "REPLACE INTO users_info (user_name, user_surname, user_age, user_phone_number, user_passport," +
                " user_driving_experience, user_road_accident_count, user_id, user_info_id) " +
                " VALUES (?,?,?,?,?,?,?,?,?)";

        private static final String GET_USER_ROLE_BY_USER_ID = "SELECT user_role FROM roles r JOIN users u  ON r.role_id = u.user_role_id WHERE u.user_id = ";

        private static final String GET_USER_REGISTRATION_DATE = "SELECT user_registration_date FROM users WHERE user_email = ";

        private static final String IS_USER_HAS_ACTIVE_RENT = "SELECT user_has_active_rent FROM users WHERE user_id = ";

        private static final String CHANGE_USER_ACTIVE_RENT = "UPDATE users SET user_has_active_rent = ? WHERE user_id = ?";

        private final static String GET_USERS_ID_BY_ORDER_ID = "SELECT user_id FROM users_has_orders WHERE order_id = ";

        private final static String GET_USER_INFO_ID = "SELECT user_info_id FROM users_info WHERE user_id = ";
    }
}
