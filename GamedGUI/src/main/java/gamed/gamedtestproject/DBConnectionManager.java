package gamed.gamedtestproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
    private static String url = "jdbc:mysql://localhost:3306/Gamed_db";
    private static String driver_name = "com.mysql.cj.jdbc.Driver";
    private static String username = "root";
    private static String password = "=68RKb5_p1";
    private static Connection connection;

    public static Connection getConnection(){
        try{
            Class.forName(driver_name);
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Database connection successful.");
            }


            /* try{
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Success");
            } catch (SQLException e) {
                System.out.println("Failed to create connection.");
                e.printStackTrace();
            } */

        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found.");
        }
        catch (SQLException e) {
            System.out.println("Failed to connect to database.");
            e.printStackTrace();
        }

        return connection;
    }


}
