package gamed;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public enum DBConnector 
{
    INSTANCE;
    
    private Connection connection;
    
    private DBConnector() 
    {
        try 
        {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/gamed_db", "root", "Raznian86");
            System.out.println("Successfully connected");
            //System.out.println("Database connection established.");
        } 
        catch (ClassNotFoundException e) 
        {
            System.err.println("JDBC Driver not found: " + e.getMessage());
        } 
        catch (SQLException e) 
        {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }
    
    public Connection getConnection() 
    {
        return this.connection;
    }

    public void closeConnection() 
    {
        try 
        {
            if (this.connection != null && !connection.isClosed()) 
            {
                connection.close();
                //System.out.println("Database connection closed.");
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }

    public ArrayList<String> retrieveUserWishlist(int userID) throws SQLException
    {
        ResultSet resultSet = null;
        resultSet = retrieveWishlistDB(this.connection, userID);

        ArrayList<String> ids = new ArrayList<>();
        while (resultSet.next()) 
        {
            ids.add(resultSet.getString("game_id"));
        }
        return ids;
    }

    private static ResultSet retrieveWishlistDB(Connection connection, int userID) 
    {
        try 
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT game_id FROM WishLists WHERE account_id = " + userID);
            return resultSet;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<String> retrieveUserFavorites(int userID) throws SQLException
    {
        ResultSet resultSet = null;
        resultSet = retrieveWishlistDB(this.connection, userID);

        ArrayList<String> ids = new ArrayList<>();
        while (resultSet.next()) 
        {
            ids.add(resultSet.getString("game_id"));
        }
        return ids;
    }

    private static ResultSet retrieveFavoritesDB(Connection connection, int userID) 
    {
        try 
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT game_id FROM FavGames WHERE account_id = " + userID);
            return resultSet;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return null;
        }
    }

    private static ResultSet retrieveReviews(Connection connection, int userID) 
    {
        try 
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Reviews WHERE account_id = " + userID);
            return resultSet;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return null;
        }
    }
}
