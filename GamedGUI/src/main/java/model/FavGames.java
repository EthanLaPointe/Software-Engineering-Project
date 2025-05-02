package model;

public class FavGames {
    private int accountID;
    private int gameID;

    public FavGames(int accountID, int gameID){
        this.accountID = accountID;
        this.gameID = gameID;
    }
    public int getAccountID() {
        return accountID;
    }
    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}