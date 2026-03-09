package vehicle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import JDBC classes

//This class is used to connect Java application with PostgreSQL database
public class DBConnect {

    private static final String URL = "jdbc:postgresql://localhost:5432/car_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    // Method to create and return database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD); //establish connection with the database
    }
}
