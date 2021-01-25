package by.epamtc.pushkevich.repository.order;

import by.epamtc.pushkevich.entity.Order;
import by.epamtc.pushkevich.exception.ConnectionPoolException;
import by.epamtc.pushkevich.exception.RepositoryException;
import by.epamtc.pushkevich.repository.connection.ConnectionPool;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderSQLRepository implements OrderRepository {
    private ConnectionPool pool;
    private final Logger logger = LogManager.getLogger(OrderSQLRepository.class);

    private static final String DATABASE_ERROR = "Database error";

    @Override
    public void addOrder(Order order, int userID) throws RepositoryException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            Date startRent = new Date(order.getStartRentDate().getTime());
            Date endDate = new Date(order.getEndRentDate().getTime());
            double costRate = order.getCost();
            double finalCostRate = order.getFinalCost();
            int carID = order.getCarID();

            String query = Query.ADD_ORDER;

            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setTimestamp(1, new Timestamp(startRent.getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(endDate.getTime()));
            preparedStatement.setDouble(3, costRate);
            preparedStatement.setDouble(4, finalCostRate);
            preparedStatement.setInt(5, userID);
            preparedStatement.setInt(6, carID);

            preparedStatement.executeUpdate();

            int orderID = 0;

            ResultSet set = preparedStatement.getGeneratedKeys();

            if (set.next()) {
                orderID = set.getInt(1);
            }

            preparedStatement = connection.prepareStatement(Query.BIND_USER_ORDER);

            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, orderID);

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
    public int getOrderID(Order order) throws RepositoryException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet set = null;

        int orderID = 0;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            Date rentStart = new Date(order.getStartRentDate().getTime());
            Date rentEnd = new Date(order.getEndRentDate().getTime());
            double costRate = order.getCost();
            double finalCostRate = order.getFinalCost();
            int carID = order.getCarID();


            String query = Query.GET_ORDER_ID;

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setTimestamp(1, new Timestamp(rentStart.getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(rentEnd.getTime()));
            preparedStatement.setDouble(3, costRate);
            preparedStatement.setDouble(4, finalCostRate);
            preparedStatement.setInt(5, carID);

            set = preparedStatement.executeQuery();

            while (set.next()) {
                orderID = set.getInt(1);
            }
        }
        catch (SQLException | ConnectionPoolException e) {
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, preparedStatement, set);
        }

        return orderID;
    }

    @Override
    public Order getOrder(int orderID) throws RepositoryException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        Order order;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            order = new Order();

            String query = Query.GET_ORDER + orderID;

            statement = connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()) {
                order.setId(set.getInt(1));
                order.setStartRentDate(set.getDate(2));
                order.setEndRentDate(set.getDate(3));
                order.setCost(set.getDouble(4));
                order.setFinalCost(set.getDouble(5));
                order.setStatus(set.getString(6));
                order.setDeclineReason(set.getString(7));
                order.setUserID(set.getInt(8));
                order.setCarID(set.getInt(9));
            }
        }
        catch (SQLException | ConnectionPoolException e) {
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, statement, set);
        }

        return order;
    }

    @Override
    public java.util.Date getCarRentEndDate(int carID) throws RepositoryException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        java.util.Date date;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            date = null;

            String query = Query.GET_CAR_RENT_END + carID;

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
    public List<Order> getOrders(int userID) throws RepositoryException {
        List<Order> orders = new ArrayList<>();

        String query = Query.GET_ALL_ORDERS_BY_USER_ID + userID;

        return getOrders(orders, query);
    }

    private List<Order> getOrders(List<Order> orders, String query) throws RepositoryException {
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            Order order;
            statement = connection.createStatement();
            set = statement.executeQuery(query);

            while (set.next()) {
                order = new Order();

                order.setId(set.getInt(1));
                order.setStartRentDate(set.getDate(2));
                order.setEndRentDate(set.getDate(3));
                order.setCost(set.getDouble(4));
                order.setFinalCost(set.getDouble(5));
                order.setStatus(set.getString(6));
                order.setDeclineReason(set.getString(7));
                order.setUserID(set.getInt(8));
                order.setCarID(set.getInt(9));

                orders.add(order);
            }
        }
        catch (SQLException | ConnectionPoolException e) {
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, statement, set);
        }

        return orders;
    }

    @Override
    public List<Order> getOrders() throws RepositoryException {
        List<Order> orders = new ArrayList<>();

        String query = Query.GET_ALL_ORDERS;

        return getOrders(orders, query);
    }

    @Override
    public List<Order> getOrders(String phoneNumber) throws RepositoryException {
        List<Order> orders = new ArrayList<>();

        String query = Query.GET_ORDERS_BY_PHONE_NUMBER + phoneNumber;

        return getOrders(orders, query);
    }

    @Override
    public List<Order> getOrders(String name, String surname) throws RepositoryException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet set = null;

        List<Order> orders;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            orders = new ArrayList<>();
            Order order;

            String query = Query.GET_ORDERS_BY_NAME_SURNAME;

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);

            set = preparedStatement.executeQuery();

            while (set.next()){
                order = new Order();

                order.setId(set.getInt(1));
                order.setStartRentDate(set.getDate(2));
                order.setEndRentDate(set.getDate(3));
                order.setCost(set.getDouble(4));
                order.setFinalCost(set.getDouble(5));
                order.setStatus(set.getString(6));
                order.setDeclineReason(set.getString(7));
                order.setUserID(set.getInt(8));
                order.setCarID(set.getInt(9));

                orders.add(order);
            }
        }
        catch (SQLException | ConnectionPoolException e) {
            logger.error(DATABASE_ERROR, e);
            throw new RepositoryException(DATABASE_ERROR);
        }
        finally {
            pool.closeConnection(connection, preparedStatement, set);
        }

        return orders;
    }

    @Override
    public void changeOrderStatus(int orderID, String status) throws RepositoryException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            String query = Query.CHANGE_STATUS;

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, orderID);

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
    public void changeDeclineReason(int orderID, String declineReason) throws RepositoryException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            pool = ConnectionPool.getInstance();
            connection = pool.takeConnection();

            String query = Query.CHANGE_DECLINE_REASON;

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, declineReason);
            preparedStatement.setInt(2, orderID);

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


    private static class Query {
        private final static String ADD_ORDER = "REPLACE INTO orders (rent_start, rent_end, cost_rate, final_cost, user_id, car_id) VALUES (?,?,?,?,?,?)";

        private final static String GET_ORDER_ID = "SELECT order_id FROM orders WHERE (rent_start, rent_end_cost_rate, final_cost, user_id, car_id) = (?,?,?,?,?,?) ";

        private final static String BIND_USER_ORDER = "INSERT INTO users_has_orders (user_id, order_id) VALUES (?,?)";

        private final static String GET_CAR_RENT_END = "SELECT rent_end FROM orders WHERE car_id = ";

        private final static String GET_ALL_ORDERS_BY_USER_ID = "SELECT * FROM orders WHERE user_id = ";

        private final static String GET_ALL_ORDERS = "SELECT * FROM orders";

        private final static String GET_ORDERS_BY_PHONE_NUMBER = "SELECT * FROM orders o JOIN users_info u ON o.user_id = u.user_id WHERE u.user_phone_number = ";

        private final static String GET_ORDERS_BY_NAME_SURNAME = "SELECT * FROM orders o JOIN users_info u ON o.user_id = u.user_id WHERE ( u.user_name,  u.user_surname) = ( ?, ?)";

        private final static String GET_ORDER = "SELECT * FROM orders WHERE order_id = ";

        private final static String CHANGE_STATUS = "UPDATE orders SET order_status = ? WHERE order_id = ?";

        private final static String CHANGE_DECLINE_REASON = "UPDATE orders SET order_decline_reason = ? WHERE order_id = ?";
    }

}
