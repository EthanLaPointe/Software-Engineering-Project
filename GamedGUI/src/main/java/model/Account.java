package model;

public class Account {
    private int accountID;
    private String username;
    private String password;
    private String dateCreated;

    public Account(int accountID, String username, String password, String dateCreated) {
        this.accountID=accountID;
        this.username = username;
        this.password = password;
        this.dateCreated = dateCreated;
    }


    public String getDateCreated(){
        return dateCreated;
    }

    public void setDateCreated(String dateCreated){
        this.dateCreated = dateCreated;
    }


    public int getAccountID(){
        return accountID;
    }

    public void setAccountID(int accountID){
        this.accountID = accountID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}