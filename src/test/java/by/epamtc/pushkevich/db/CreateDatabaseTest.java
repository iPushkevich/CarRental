package by.epamtc.pushkevich.db;

import by.epamtc.pushkevich.exception.ConnectionPoolException;
import by.epamtc.pushkevich.repository.connection.ConnectionPool;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.sql.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class CreateDatabaseTest {
    private static final String CREATE_SCHEMA = "src/test/resources/table/create_schema.txt";
    private static final String CREATE_CARS_TABLE = "src/test/resources/table/cars.txt";
    private static final String CREATE_CARS_RENT_INFO_TABLE = "src/test/resources/table/cars_rent_info.txt";
    private static final String CREATE_CARS_RETURN_TABLE = "src/test/resources/table/cars_return.txt";
    private static final String CREATE_CARS_REVIEWS_TABLE = "src/test/resources/table/cars_reviews.txt";
    private static final String DROP_SCHEMA = "src/test/resources/table/drop_schema.txt";

    @BeforeClass
    public static void createDatabase() throws IOException, SQLException, ConnectionPoolException {
        File file = new File(CREATE_SCHEMA);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String query="";

        while (reader.ready()){
            query+= reader.readLine();
        }

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.takeConnection();

        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();

        pool.returnConnection(connection);
    }

    @Test
    public void isDatabaseCreated() throws ConnectionPoolException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.takeConnection();

        DatabaseMetaData metaData = connection.getMetaData();
        String URL = metaData.getURL();

        assertNotNull(URL);
    }

    @Test
    public void createCarsTable() throws IOException, ConnectionPoolException, SQLException {
        File file = new File(CREATE_CARS_TABLE);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String query="";

        String column1Name = "car_id";
        String column2Name = "car_brand";
        String column3Name = "car_model";
        String column4Name = "car_year";
        String column5Name = "car_mileage";
        String column6Name = "car_engine_type";
        String column7Name = "car_engine_size";
        String column8Name = "car_transmission";
        String column9Name = "car_wheels_drive";
        String column10Name = "car_type";
        String column11Name = "car_rent_count";
        String column12Name = "is_car_available";

        while (reader.ready()){
            query+= reader.readLine() + " ";
        }

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.takeConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();

        ResultSetMetaData metaData = statement.executeQuery("SELECT * FROM cars_rent_test.cars").getMetaData();

        String column1 = metaData.getColumnName(1);
        String column2 = metaData.getColumnName(2);
        String column3 = metaData.getColumnName(3);
        String column4 = metaData.getColumnName(4);
        String column5 = metaData.getColumnName(5);
        String column6 = metaData.getColumnName(6);
        String column7 = metaData.getColumnName(7);
        String column8 = metaData.getColumnName(8);
        String column9 = metaData.getColumnName(9);
        String column10 = metaData.getColumnName(10);
        String column11 = metaData.getColumnName(11);
        String column12 = metaData.getColumnName(12);

        assertEquals(column1Name, column1);
        assertEquals(column2Name, column2);
        assertEquals(column3Name, column3);
        assertEquals(column4Name, column4);
        assertEquals(column5Name, column5);
        assertEquals(column6Name, column6);
        assertEquals(column7Name, column7);
        assertEquals(column8Name, column8);
        assertEquals(column9Name, column9);
        assertEquals(column10Name, column10);
        assertEquals(column11Name, column11);
        assertEquals(column12Name, column12);

        pool.returnConnection(connection);
    }

    @Test
    public void setCreateCarsRentInfoTable() throws IOException, ConnectionPoolException, SQLException {
        File file = new File(CREATE_CARS_RENT_INFO_TABLE);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String query="";

        String column1Name = "car_rent_info_id";
        String column2Name = "car_description";
        String column3Name = "car_rent_cost";
        String column4Name = "car_rating";
        String column5Name = "car_fuel_lvl";
        String column6Name = "car_id";

        while (reader.ready()){
            query+= reader.readLine() + " ";
        }

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.takeConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();

        ResultSetMetaData metaData = statement.executeQuery("SELECT * FROM cars_rent_test.cars_rent_info").getMetaData();

        String column1 = metaData.getColumnName(1);
        String column2 = metaData.getColumnName(2);
        String column3 = metaData.getColumnName(3);
        String column4 = metaData.getColumnName(4);
        String column5 = metaData.getColumnName(5);
        String column6 = metaData.getColumnName(6);

        assertEquals(column1Name, column1);
        assertEquals(column2Name, column2);
        assertEquals(column3Name, column3);
        assertEquals(column4Name, column4);
        assertEquals(column5Name, column5);
        assertEquals(column6Name, column6);

        pool.returnConnection(connection);
    }

    @Test
    public void createCarsReturnTable() throws IOException, SQLException, ConnectionPoolException {
        File file = new File(CREATE_CARS_RETURN_TABLE);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String query="";

        String column1Name = "car_return_id";
        String column2Name = "car_return_date";
        String column3Name = "car_return_damage_description";
        String column4Name = "car_return_fuel_lvl";
        String column5Name = "car_return_cost_penalty";
        String column6Name = "car_return_penalty_description";

        while (reader.ready()){
            query+= reader.readLine() + " ";
        }

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.takeConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();

        ResultSetMetaData metaData = statement.executeQuery("SELECT * FROM cars_rent_test.cars_return").getMetaData();

        String column1 = metaData.getColumnName(1);
        String column2 = metaData.getColumnName(2);
        String column3 = metaData.getColumnName(3);
        String column4 = metaData.getColumnName(4);
        String column5 = metaData.getColumnName(5);
        String column6 = metaData.getColumnName(6);

        assertEquals(column1Name, column1);
        assertEquals(column2Name, column2);
        assertEquals(column3Name, column3);
        assertEquals(column4Name, column4);
        assertEquals(column5Name, column5);
        assertEquals(column6Name, column6);

        pool.returnConnection(connection);
    }

    @Test
    public void createCarReviewsTable() throws ConnectionPoolException, SQLException, IOException {
        File file = new File(CREATE_CARS_REVIEWS_TABLE);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String query="";

        String column1Name = "review_id";
        String column2Name = "car_review";
        String column3Name = "car_id";

        while (reader.ready()){
            query+= reader.readLine() + " ";
        }

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.takeConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();

        ResultSetMetaData metaData = statement.executeQuery("SELECT * FROM cars_rent_test.cars_reviews").getMetaData();

        String column1 = metaData.getColumnName(1);
        String column2 = metaData.getColumnName(2);
        String column3 = metaData.getColumnName(3);

        assertEquals(column1Name, column1);
        assertEquals(column2Name, column2);
        assertEquals(column3Name, column3);

        pool.returnConnection(connection);
    }

    @AfterClass
    public static void dropDatabase() throws IOException, ConnectionPoolException, SQLException {
        File file = new File(DROP_SCHEMA);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String query="";

        while (reader.ready()){
            query+= reader.readLine();
        }

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.takeConnection();

        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();

        pool.returnConnection(connection);
    }

}
