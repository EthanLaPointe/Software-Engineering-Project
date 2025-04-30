DROP DATABASE IF EXISTS Gamed_db;
CREATE DATABASE Gamed_db;
USE Gamed_db;

CREATE TABLE Accounts
(
	account_id int auto_increment primary key,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
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
    rating_text VARCHAR(1000),
    FOREIGN KEY (account_id) REFERENCES Accounts(account_id) ON DELETE CASCADE
);
