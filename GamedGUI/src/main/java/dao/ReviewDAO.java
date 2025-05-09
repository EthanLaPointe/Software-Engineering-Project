package dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import gamed.gamedtestproject.DBConnectionManager;
import model.Review;
public class ReviewDAO {
    // This class will handle the database operations related to reviews.
    // For example, adding a review, fetching reviews for a game, etc.
    // The implementation will depend on the database schema and requirements.

    public static List<Review> getReviewsByGameId(int gameId) {
        List<Review> reviews = new ArrayList<>();
        try (Connection conn = DBConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM REVIEWS WHERE game_id = " + gameId)) {
            while (rs.next()) {
                reviews.add(new Review(
                        rs.getInt("review_id"),
                        rs.getInt("game_id"),
                        rs.getInt("account_id"),
                        rs.getInt("rating"),
                        rs.getString("contents")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

}
