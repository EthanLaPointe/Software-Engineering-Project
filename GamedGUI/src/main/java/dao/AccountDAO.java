package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gamed.gamedtestproject.DBConnectionManager;
import model.Account;



public class AccountDAO {
    public static Account getAccountByUsername(String username) {
        
        //List<Account> accounts = new ArrayList<>();

        Account account = null;
        String query = "SELECT * FROM Accounts WHERE username = ?";

        
        try(Connection conn =DBConnectionManager.getConnection();
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
                }
    
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
            return account;
        }




}


            
    
