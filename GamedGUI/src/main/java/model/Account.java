package model;

import java.time.LocalDate;

public class Account {
    private int accountID;
    private String username;
    private String password;
    private LocalDate dateCreated;
    private String imagePath; // Assuming you have an image path field


    public Account(int accountID, String username, String password, String dateCreated, String imagePath) {
        this.accountID = accountID;
        this.username = username;
        this.password = password;
        this.dateCreated = LocalDate.parse(dateCreated);
    }
    public Account(int accountID){
        this.accountID = accountID;
    }

    public Account(int accountID, String username, String password, LocalDate dateCreated, String imagePath) {

        this.accountID=accountID;
        this.username = username;
        this.password = password;
        this.dateCreated = dateCreated;
        this.imagePath = imagePath;
    }


    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public LocalDate getDateCreated(){
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated){
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