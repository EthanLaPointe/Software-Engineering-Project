package model;

public class Review {
    private int reviewId;
    private int gameId;
    private int accountID;
    private int rating;
    private String contents;


    public Review(int reviewId, int gameId, int accountID, int rating, String contents) {
        this.reviewId = reviewId;
        this.gameId = gameId;
        this.accountID = accountID;
        this.rating = rating;
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }
    public int getReviewId() {
        return reviewId;
    }

    public int getGameId() {
        return gameId;
    }

    public int getAccountID() {
        return accountID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }
}
