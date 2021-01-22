package by.epamtc.pushkevich.repository.connection;

import by.epamtc.pushkevich.exception.ConnectionPoolException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionPool {
    private static volatile ConnectionPool INSTANCE;

    private static final String DRIVER = "db.driver";
    private static final String JDBC_URL = "db.jdbc_url";
    private static final String DB_LOGIN = "db.login";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_PROPERTIES = "jdbc";

    private static final int CONNECTION_COUNT = 9;

    private final BlockingQueue<Connection> freeConnections;
    private final BlockingQueue<Connection> givenConnections;

    private final Logger logger = LogManager.getLogger(ConnectionPool.class);

    private ConnectionPool() throws ConnectionPoolException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DB_PROPERTIES);

        String driver = resourceBundle.getString(DRIVER);
        String jdbcURL = resourceBundle.getString(JDBC_URL);
        String dbLogin = resourceBundle.getString(DB_LOGIN);
        String dbPassword = resourceBundle.getString(DB_PASSWORD);

        try {
            Class.forName(driver);

            givenConnections = new ArrayBlockingQueue<>(CONNECTION_COUNT);
            freeConnections = new ArrayBlockingQueue<>(CONNECTION_COUNT);

            for (int i = 0; i < CONNECTION_COUNT; i++) {
                Connection connection = DriverManager.getConnection(jdbcURL, dbLogin, dbPassword);

                freeConnections.put(connection);
            }
        }
        catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("Can`t find database driver class", e);
        }
         catch (SQLException e) {
             throw new ConnectionPoolException("SQLException in ConnectionPool", e);
        }
        catch (InterruptedException e) {
            throw new ConnectionPoolException("Error connecting to the data source", e);
        }
    }

    public static ConnectionPool getInstance() throws ConnectionPoolException {
        ConnectionPool localInstance = INSTANCE;

        if (localInstance == null){
            synchronized (ConnectionPool.class){
                localInstance = INSTANCE;

                if (localInstance == null){
                    localInstance = INSTANCE = new ConnectionPool();
                }
            }
        }
        return localInstance;
    }

    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection;

        try {
            connection = freeConnections.take();
            givenConnections.put(connection);
        }
        catch (InterruptedException e) {
            throw new ConnectionPoolException("Error connecting to the data source", e);
        }

        return connection;
    }

    public void returnConnection(Connection connection) throws ConnectionPoolException, SQLException {
            givenConnections.remove(connection);

            if (connection != null) {
                try {
                    freeConnections.put(connection);
                }
                catch (InterruptedException e) {
                    throw new ConnectionPoolException("Error connecting to the data source", e);
                }

                if (connection.isClosed()){
                    connection.close();
                }
            }
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                returnConnection(connection);
            } catch (SQLException e) {
                logger.error("Connection isn`t returned to the pool");
            } catch (ConnectionPoolException e) {
                logger.error(e.getMessage());
            }
        }
    }

    public void closeConnection(Connection connection, Statement statement){
        if (connection != null) {
            try {
                returnConnection(connection);
            } catch (SQLException e) {
                logger.error("Connection isn`t returned to the pool");
            } catch (ConnectionPoolException e) {
                logger.error(e.getMessage());
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error("Statement isn`t closed");
            }
        }
    }

    public void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
        if (connection != null) {
            try {
                returnConnection(connection);
            } catch (SQLException e) {
                logger.error("Connection isn`t returned to the pool");
            } catch (ConnectionPoolException e) {
                logger.error(e.getMessage());
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error("Statement isn`t closed");
            }
        }

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error("ResultSet isn`t closed");
            }
        }
    }

    public void destroyConnectionPool() {
        for (int i = 0; i < freeConnections.size(); i++) {
            Connection connection = freeConnections.poll();

            try {
                connection.close();
            }
            catch (SQLException e) {
                logger.error("Connection isn`t closed");
            }
        }

        for (int i = 0; i < givenConnections.size(); i++) {
            Connection connection = givenConnections.poll();

            try {
                connection.close();
            }
            catch (SQLException e) {
                logger.error("Connection isn`t closed");
            }
        }

        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("Drivers arn`t deregister");
            }
        });
    }
}
