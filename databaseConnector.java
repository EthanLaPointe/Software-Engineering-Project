import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.SQLException; 

public class databaseConnector { 

    private static final String DB_URL = ""; 
    private static final String DB_USER = " ";
    private static final String DB_PASSWORD = " "; 

    public static Connection geConnection() throws SQLException{ 

        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
    }
}

