import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBC {
    
    public static void main(String[] args) throws ClassNotFoundException, java.sql.SQLException {
        
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/Gamed_db", "root", "Chaser580558");
            System.out.println("Successfully connected");
            statement = connection.createStatement();
            
            //Gets all columns from the Game Table and displays them
            resultSet = statement.executeQuery("Select * from Games");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
            while (resultSet.next()){
                String result = "";
                for(int i = 1; i <=3; i++) {
                    result += resultSet.getString(i) + " ";
                }
                System.out.println(result);
            }
            System.out.println("\n");

            //Insert Statement
            /*String insertString = "insert into Game (title, relDate) values ('Call of Duty', '2022-01-05')";
            statement.execute(insertString);*/

            //Gets all columns and displays based on what the column name/index is
            resultSet = statement.executeQuery("Select * from Game");
            while (resultSet.next()){
                String name = resultSet.getString("title");
                System.out.println(name);
            }
            statement.close();
    }
}