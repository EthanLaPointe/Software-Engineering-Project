DROP DATABASE IF EXISTS Gamed_db;
CREATE DATABASE Gamed_db;
USE Gamed_db;

CREATE TABLE Game
(
	game_id int auto_increment primary key,
    title VARCHAR(50) NOT NULL,
    relDate DATE NOT NULL,
    pub_id INT NOT NULL,
    CONSTRAINT pub_fk FOREIGN KEY (pub_id) references Publisher (pub_id)
    
);

CREATE TABLE GameGenre
(
	game_id INT,
    genre_id INT,
    CONSTRAINT game_fk FOREIGN KEY (game_id) REFERENCES Game(game_id),
    CONSTRAINT genre_fk FOREIGN KEY (genre_id) REFERENCES Genre(genre_id)

);

CREATE TABLE Genre
(
	genre_id int auto_increment primary key,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE Publisher
(
	pub_id int auto_increment primary key,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE WishList
(
	wishList_id int auto_increment primary key,
    account_id INT,
    game_id INT,
	FOREIGN KEY (account_id) REFERENCES Accounts(account_id),
	FOREIGN KEY (game_id) REFERENCES Game(game_id)
);

CREATE TABLE FavGames
(
	favGame_id int auto_increment primary key,
    account_id INT,
    game_id INT,
	FOREIGN KEY (account_id) REFERENCES Accounts(account_id),
	FOREIGN KEY (game_id) REFERENCES Game(game_id)
);

CREATE TABLE review
(
	review_id INT auto_increment primary key,
    game_id INT,
	account_id INT,
    rating INT NOT NULL,
    FOREIGN KEY (game_id) REFERENCES Game(game_id),
    FOREIGN KEY (account_id) REFERENCES Accounts(account_id) ON DELETE CASCADE
);


CREATE TABLE Accounts
(
	account_id int auto_increment primary key,
    user_id INT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);

