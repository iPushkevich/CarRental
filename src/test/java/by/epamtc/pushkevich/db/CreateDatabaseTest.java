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
    private static final String CREATE_ORDERS_TABLE = "src/test/resources/table/orders.txt";
    private static final String CREATE_ROLES_TABLE = "src/test/resources/table/roles.txt";
    private static final String CREATE_USERS_TABLE = "src/test/resources/table/users.txt";
    private static final String CREATE_USERS_HAVE_ORDERS_TABLE = "src/test/resources/table/users_have_orders.txt";
    private static final String CREATE_USERS_INFO_TABLE = "src/test/resources/table/users_info.txt";
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

        String column1ExpectedName = "car_id";
        String column2ExpectedName = "car_brand";
        String column3ExpectedName = "car_model";
        String column4ExpectedName = "car_year";
        String column5ExpectedName = "car_mileage";
        String column6ExpectedName = "car_engine_type";
        String column7ExpectedName = "car_engine_size";
        String column8ExpectedName = "car_transmission";
        String column9ExpectedName = "car_wheels_drive";
        String column10ExpectedName = "car_type";
        String column11ExpectedName = "car_rent_count";
        String column12ExpectedName = "is_car_available";

        while (reader.ready()){
            query+= reader.readLine() + " ";
        }

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.takeConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();

        ResultSetMetaData metaData = statement.executeQuery("SELECT * FROM cars_rent_test.cars").getMetaData();

        String column1ActualName = metaData.getColumnName(1);
        String column2ActualName = metaData.getColumnName(2);
        String column3ActualName = metaData.getColumnName(3);
        String column4ActualName = metaData.getColumnName(4);
        String column5ActualName = metaData.getColumnName(5);
        String column6ActualName = metaData.getColumnName(6);
        String column7ActualName = metaData.getColumnName(7);
        String column8ActualName = metaData.getColumnName(8);
        String column9ActualName = metaData.getColumnName(9);
        String column10ActualName = metaData.getColumnName(10);
        String column11ActualName = metaData.getColumnName(11);
        String column12ActualName = metaData.getColumnName(12);

        assertEquals(column1ExpectedName, column1ActualName);
        assertEquals(column2ExpectedName, column2ActualName);
        assertEquals(column3ExpectedName, column3ActualName);
        assertEquals(column4ExpectedName, column4ActualName);
        assertEquals(column5ExpectedName, column5ActualName);
        assertEquals(column6ExpectedName, column6ActualName);
        assertEquals(column7ExpectedName, column7ActualName);
        assertEquals(column8ExpectedName, column8ActualName);
        assertEquals(column9ExpectedName, column9ActualName);
        assertEquals(column10ExpectedName, column10ActualName);
        assertEquals(column11ExpectedName, column11ActualName);
        assertEquals(column12ExpectedName, column12ActualName);

        pool.returnConnection(connection);
    }

    @Test
    public void setCreateCarsRentInfoTable() throws IOException, ConnectionPoolException, SQLException {
        File file = new File(CREATE_CARS_RENT_INFO_TABLE);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String query="";

        String column1ExpectedName = "car_rent_info_id";
        String column2ExpectedName = "car_description";
        String column3ExpectedName = "car_rent_cost";
        String column4ExpectedName = "car_rating";
        String column5ExpectedName = "car_fuel_lvl";
        String column6ExpectedName = "car_id";

        while (reader.ready()){
            query+= reader.readLine() + " ";
        }

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.takeConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();

        ResultSetMetaData metaData = statement.executeQuery("SELECT * FROM cars_rent_test.cars_rent_info").getMetaData();

        String column1ActualName = metaData.getColumnName(1);
        String column2ActualName = metaData.getColumnName(2);
        String column3ActualName = metaData.getColumnName(3);
        String column4ActualName = metaData.getColumnName(4);
        String column5ActualName = metaData.getColumnName(5);
        String column6ActualName = metaData.getColumnName(6);

        assertEquals(column1ExpectedName, column1ActualName);
        assertEquals(column2ExpectedName, column2ActualName);
        assertEquals(column3ExpectedName, column3ActualName);
        assertEquals(column4ExpectedName, column4ActualName);
        assertEquals(column5ExpectedName, column5ActualName);
        assertEquals(column6ExpectedName, column6ActualName);

        pool.returnConnection(connection);
    }

    @Test
    public void createCarsReturnTable() throws IOException, SQLException, ConnectionPoolException {
        File file = new File(CREATE_CARS_RETURN_TABLE);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String query="";

        String column1ExpectedName = "car_return_id";
        String column2ExpectedName = "car_return_date";
        String column3ExpectedName = "car_return_damage_description";
        String column4ExpectedName = "car_return_fuel_lvl";
        String column5ExpectedName = "car_return_cost_penalty";
        String column6ExpectedName = "car_return_penalty_description";

        while (reader.ready()){
            query+= reader.readLine() + " ";
        }

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.takeConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();

        ResultSetMetaData metaData = statement.executeQuery("SELECT * FROM cars_rent_test.cars_return").getMetaData();

        String column1ActualName = metaData.getColumnName(1);
        String column2ActualName = metaData.getColumnName(2);
        String column3ActualName = metaData.getColumnName(3);
        String column4ActualName = metaData.getColumnName(4);
        String column5ActualName = metaData.getColumnName(5);
        String column6ActualName = metaData.getColumnName(6);

        assertEquals(column1ExpectedName, column1ActualName);
        assertEquals(column2ExpectedName, column2ActualName);
        assertEquals(column3ExpectedName, column3ActualName);
        assertEquals(column4ExpectedName, column4ActualName);
        assertEquals(column5ExpectedName, column5ActualName);
        assertEquals(column6ExpectedName, column6ActualName);

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

        String column1ActualName = metaData.getColumnName(1);
        String column2ActualName = metaData.getColumnName(2);
        String column3ActualName = metaData.getColumnName(3);

        assertEquals(column1Name, column1ActualName);
        assertEquals(column2Name, column2ActualName);
        assertEquals(column3Name, column3ActualName);

        pool.returnConnection(connection);
    }

    @Test
    public void createOrdersTable() throws IOException, SQLException, ConnectionPoolException {
        File file = new File(CREATE_ORDERS_TABLE);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String query="";

        String column1ExpectedName = "order_id";
        String column2ExpectedName = "rent_start";
        String column3ExpectedName = "rent_end";
        String column4ExpectedName = "cost_rate";
        String column5ExpectedName = "final_cost";
        String column6ExpectedName = "order_status";
        String column7ExpectedName = "order_decline_reason";
        String column8ExpectedName = "user_id";
        String column9ExpectedName = "car_id";
        String column10ExpectedName = "car_return_id";

        while (reader.ready()){
            query+= reader.readLine() + " ";
        }

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.takeConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();

        ResultSetMetaData metaData = statement.executeQuery("SELECT * FROM cars_rent_test.orders").getMetaData();

        String column1ActualName = metaData.getColumnName(1);
        String column2ActualName = metaData.getColumnName(2);
        String column3ActualName = metaData.getColumnName(3);
        String column4ActualName = metaData.getColumnName(4);
        String column5ActualName = metaData.getColumnName(5);
        String column6ActualName = metaData.getColumnName(6);
        String column7ActualName = metaData.getColumnName(7);
        String column8ActualName = metaData.getColumnName(8);
        String column9ActualName = metaData.getColumnName(9);
        String column10ActualName = metaData.getColumnName(10);

        assertEquals(column1ExpectedName, column1ActualName);
        assertEquals(column2ExpectedName, column2ActualName);
        assertEquals(column3ExpectedName, column3ActualName);
        assertEquals(column4ExpectedName, column4ActualName);
        assertEquals(column5ExpectedName, column5ActualName);
        assertEquals(column6ExpectedName, column6ActualName);
        assertEquals(column7ExpectedName, column7ActualName);
        assertEquals(column8ExpectedName, column8ActualName);
        assertEquals(column9ExpectedName, column9ActualName);
        assertEquals(column10ExpectedName, column10ActualName);

        pool.returnConnection(connection);
    }

    @Test
    public void setCreateRolesTable() throws IOException, ConnectionPoolException, SQLException {
        File file = new File(CREATE_ROLES_TABLE);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String query="";

        String column1ExpectedName = "role_id";
        String column2ExpectedName = "user_role";

        while (reader.ready()){
            query+= reader.readLine() + " ";
        }

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.takeConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();

        ResultSetMetaData metaData = statement.executeQuery("SELECT * FROM cars_rent_test.roles").getMetaData();

        String column1ActualName = metaData.getColumnName(1);
        String column2ActualName = metaData.getColumnName(2);

        assertEquals(column1ExpectedName, column1ActualName);
        assertEquals(column2ExpectedName, column2ActualName);

        pool.returnConnection(connection);
    }

    @Test
    public void createUsersTable() throws IOException, SQLException, ConnectionPoolException {
        File file = new File(CREATE_USERS_TABLE);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String query="";

        String column1ExpectedName = "user_id";
        String column2ExpectedName = "user_email";
        String column3ExpectedName = "user_password";
        String column4ExpectedName = "user_registration_date";
        String column5ExpectedName = "user_has_active_rent";
        String column6ExpectedName = "user_role_id";

        while (reader.ready()){
            query+= reader.readLine() + " ";
        }

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.takeConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();

        ResultSetMetaData metaData = statement.executeQuery("SELECT * FROM cars_rent_test.users").getMetaData();

        String column1ActualName = metaData.getColumnName(1);
        String column2ActualName = metaData.getColumnName(2);
        String column3ActualName = metaData.getColumnName(3);
        String column4ActualName = metaData.getColumnName(4);
        String column5ActualName = metaData.getColumnName(5);
        String column6ActualName = metaData.getColumnName(6);

        assertEquals(column1ExpectedName, column1ActualName);
        assertEquals(column2ExpectedName, column2ActualName);
        assertEquals(column3ExpectedName, column3ActualName);
        assertEquals(column4ExpectedName, column4ActualName);
        assertEquals(column5ExpectedName, column5ActualName);
        assertEquals(column6ExpectedName, column6ActualName);

        pool.returnConnection(connection);
    }

    @Test
    public void createUsersHaveOrdersTable() throws SQLException, ConnectionPoolException, IOException {
        File file = new File(CREATE_USERS_HAVE_ORDERS_TABLE);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String query="";
        int columnsCount = 2;

        String column1ExpectedName = "user_id";
        String column2ExpectedName = "order_id";

        while (reader.ready()){
            query+= reader.readLine() + " ";
        }

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.takeConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();

        ResultSetMetaData metaData = statement.executeQuery("SELECT * FROM cars_rent_test.users_have_orders").getMetaData();

        String column1ActualName = metaData.getColumnName(1);
        String column2ActualName = metaData.getColumnName(2);

        assertEquals(column1ExpectedName, column1ActualName);
        assertEquals(column2ExpectedName, column2ActualName);

        pool.returnConnection(connection);
    }

    @Test
    public void createUsersInfoTable() throws ConnectionPoolException, SQLException, IOException {
        File file = new File(CREATE_USERS_INFO_TABLE);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String query="";

        String column1ExpectedName = "user_info_id";
        String column2ExpectedName = "user_name";
        String column3ExpectedName = "user_surname";
        String column4ExpectedName = "user_age";
        String column5ExpectedName = "user_phone_number";
        String column6ExpectedName = "user_passport";
        String column7ExpectedName = "user_driving_experience";
        String column8ExpectedName = "user_road_accident_count";
        String column9ExpectedName = "user_previous_rent_count";
        String column10ExpectedName = "user_discount";
        String column11ExpectedName = "user_id";

        String[] expected = {column1ExpectedName, column2ExpectedName, column3ExpectedName, column4ExpectedName, column5ExpectedName, column6ExpectedName,
                column7ExpectedName, column8ExpectedName, column9ExpectedName, column10ExpectedName, column11ExpectedName};

        while (reader.ready()){
            query+= reader.readLine() + " ";
        }

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.takeConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();

        ResultSetMetaData metaData = statement.executeQuery("SELECT * FROM cars_rent_test.users_info").getMetaData();

        String column1ActualName = metaData.getColumnName(1);
        String column2ActualName = metaData.getColumnName(2);
        String column3ActualName = metaData.getColumnName(3);
        String column4ActualName = metaData.getColumnName(4);
        String column5ActualName = metaData.getColumnName(5);
        String column6ActualName = metaData.getColumnName(6);
        String column7ActualName = metaData.getColumnName(7);
        String column8ActualName = metaData.getColumnName(8);
        String column9ActualName = metaData.getColumnName(9);
        String column10ActualName = metaData.getColumnName(10);
        String column11ActualName = metaData.getColumnName(11);

        String[] actual = {column1ActualName, column2ActualName, column3ActualName, column4ActualName, column5ActualName, column6ActualName, column7ActualName,
                column8ActualName, column9ActualName, column10ActualName, column11ActualName};

        zipAssert(expected, actual);

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

    private void zipAssert(String[] expected, String[] actual){
        if (expected.length == actual.length){
            int arraysLength = expected.length;

            for (int i=0; i<arraysLength; i++){
                assertEquals(expected[i], actual[i]);
            }
        }
        else throw new UnsupportedOperationException("Not equal arrays length");
    }

}
