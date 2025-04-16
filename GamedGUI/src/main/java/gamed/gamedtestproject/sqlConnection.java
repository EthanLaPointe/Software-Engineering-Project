package gamed.gamedtestproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class sqlConnection {
    public static void main(String[] args) {
        // MySQL connection info
        String url = "jdbc:mysql://localhost:3306/gamed";
        String user = "local";
        String password = "Chaser580558";

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to MySQL
            Connection conn = DriverManager.getConnection(url, user, password);

            // Create and execute a SQL query
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM your_table");

            // Process the result set
            while (rs.next()) {
                System.out.println("Column1: " + rs.getString("column1_name"));
                // Add more columns as needed
            }

            // Close everything
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}