package model;

public class WishList {
    private int accountID;
    private int gameID;

    public WishList(int accountID, int gameID){
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
