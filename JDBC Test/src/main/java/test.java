import java.sql.*;

public class test {

    Connection connection;
    public test(){

    }
    public static void main(String[] args) {
        test test = new test();
        test.createConnection();
    }

    void createConnection()
    {
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gamed_db", "root", "Raznian86");
            System.out.println("Successfully connected");
            Statement statement = connection.createStatement();

            //Gets all columns from the Game Table and displays them
            ResultSet resultSet = statement.executeQuery("Select * from Game");
            while (resultSet.next())
            {
                String result = "";
                for(int i = 1; i <=3; i++) 
                {
                    result += resultSet.getString(i) + " ";
                }
                System.out.println(result);
            }
            System.out.println("\n");

            //Insert Statement
            /*String insertString = "insert into Game (title, relDate) values ('Call of Duty', '2022-01-05')";
            statement.execute(insertString);*/

            //Gets all columns and displays based on what the column name/index is
        } 
        catch (ClassNotFoundException | SQLException e) 
        {
            throw new RuntimeException(e);
        }
    }
}
