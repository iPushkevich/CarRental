package by.epamtc.pushkevich.db;

import by.epamtc.pushkevich.exception.ConnectionPoolException;
import by.epamtc.pushkevich.repository.connection.ConnectionPool;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

public class ConnectionTest {
    private static ConnectionPool pool;

    @BeforeClass
    public static void initConnection() throws ConnectionPoolException {
        pool = ConnectionPool.getInstance();
    }

    @Test
    public void checkConnectionPool() {
        assertNotNull(pool);
    }

    @Test
    public void openConnection() throws ConnectionPoolException, SQLException {
        Connection connection = pool.takeConnection();
        assertNotNull(connection);
        pool.returnConnection(connection);
    }
}
