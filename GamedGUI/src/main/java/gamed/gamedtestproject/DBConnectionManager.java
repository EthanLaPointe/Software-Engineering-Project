package gamed.gamedtestproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
    private static String url = "jdbc:mysql://localhost:3306/gamed_db";
    private static String driver_name = "com.mysql.cj.jdbc.Driver";
    private static String username = "root";
    private static String password = "";
    private static Connection connection;

    public static Connection getConnection(){
        try{
            Class.forName(driver_name);
            try{
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Success");
            } catch (SQLException e) {
                System.out.println("Failed to create connection.");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found.");
        }
        return connection;
    }


}
