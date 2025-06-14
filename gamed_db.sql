DROP DATABASE IF EXISTS Gamed_db;
CREATE DATABASE Gamed_db;
USE Gamed_db;

CREATE TABLE Accounts
(
    account_id int auto_increment primary key,
    username VARCHAR(128) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    dateCreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    imagePath VARCHAR(256)
);

CREATE TABLE WishLists
(
    account_id INT,
    game_id INT,
    FOREIGN KEY (account_id) REFERENCES Accounts(account_id) ON DELETE CASCADE
); 

CREATE TABLE FavGames
(
    account_id INT,
    game_id INT,
    FOREIGN KEY (account_id) REFERENCES Accounts(account_id) ON DELETE CASCADE
    
);

CREATE TABLE Reviews
(
    review_id INT auto_increment primary key, 
    game_id INT,
    account_id INT,
    rating INT NOT NULL,
    contents BLOB,
    KEY account_id (account_id),
    CONSTRAINT rev_acc_id FOREIGN KEY (account_id) REFERENCES Accounts(account_id)
);

SET FOREIGN_KEY_CHECKS = 0;