package dao;
import java.sql.Connection;
import java.sql.ResultSet; // Ensure this is the correct package for DBConnectionManager
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import gamed.gamedtestproject.DBConnectionManager;
import model.FavGames;

public class FavGamesDAO {
    public static List<FavGames> getWishlistByUserId(int userId) {
        List<FavGames> favGames = new ArrayList<>();

        try (Connection conn = DBConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM WISHLIST WHERE user_id = " + userId)) {
            while (rs.next()) {
                favGames.add(new FavGames(
                        rs.getInt("account_id"),
                        rs.getInt("game_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
   
}
       return favGames;
    }
}
