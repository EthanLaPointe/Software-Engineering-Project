package dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
<<<<<<< Updated upstream
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
=======
import java.time.LocalDate;
>>>>>>> Stashed changes

import gamed.gamedtestproject.DBConnectionManager;
import model.Account;



public class AccountDAO {
    public static List<Account> getAccountByUsername(String username) {
        
        List<Account> accounts = new ArrayList<>();
        
<<<<<<< Updated upstream
        try(Connection conn =DBConnectionManager.getConnection();
            Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM ACCOUNTS")){
                while (rs.next()) {
                    accounts.add(new Account(
                            rs.getInt("account_id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("dateCreated")
                    ));
=======
        /* try(Connection conn = DBConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){
                stmt.setString(1,username);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    account = new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("dateCreated")
                    );
>>>>>>> Stashed changes
                }
    
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
<<<<<<< Updated upstream
            return accounts;
             }
    }
=======
            return account;
        } */

        //new version below

        try(Connection conn = DBConnectionManager.getConnection();
        CallableStatement stmt = conn.prepareCall(query)){
            
            stmt.setString(1,username);

            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    int accountID = rs.getInt("account_id");
                    String password = rs.getString("password");
                    LocalDate dateCreated = rs.getDate("dateCreated").toLocalDate();

                    account = new Account(accountID, username, password, dateCreated);

                }
            }

        } catch(SQLException e){
            e.printStackTrace();
        }

            return account;
}
}


            
    
>>>>>>> Stashed changes
