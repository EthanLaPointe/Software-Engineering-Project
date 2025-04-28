package dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import gamed.gamedtestproject.DBConnectionManager;
import model.Account;



public class AccountDAO {
    public static List<Account> getAccountByUsername(String username) {
        
        List<Account> accounts = new ArrayList<>();
        
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
                }
    
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
            return accounts;
             }
    }
