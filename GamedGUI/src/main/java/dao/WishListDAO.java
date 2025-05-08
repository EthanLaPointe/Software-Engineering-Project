package dao;

import java.sql.Connection;
import java.sql.ResultSet; 
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import gamed.gamedtestproject.DBConnectionManager;
import model.WishList; 

public class WishListDAO {
     public static List<WishList> getWishlistByUserId(int userId) {
         List<WishList> wishList = new ArrayList<>();

         try (Connection conn = DBConnectionManager.getConnection();
              Statement stmt = conn.createStatement();
              ResultSet rs = stmt.executeQuery("SELECT * FROM WISHLIST WHERE user_id = " + userId)) {
             while (rs.next()) {
                 wishList.add(new WishList(
                         rs.getInt("account_id"),
                         rs.getInt("game_id")
                 ));
             }
         } catch (SQLException e) {
             e.printStackTrace();
    
}
        return wishList;
     }
    }