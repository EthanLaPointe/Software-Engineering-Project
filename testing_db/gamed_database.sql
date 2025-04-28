DROP DATABASE IF EXISTS Gamed_db;
CREATE DATABASE Gamed_db;
USE Gamed_db;


CREATE TABLE Accounts
(
	account_id int auto_increment primary key,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);

CREATE TABLE Publishers
(
	pub_id int auto_increment primary key,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE Games
(
	game_id int auto_increment primary key,
    title VARCHAR(50) NOT NULL,
    relDate DATE NOT NULL,
    pub_id INT NOT NULL,
    CONSTRAINT pub_fk FOREIGN KEY (pub_id) references Publishers (pub_id)
    
);

CREATE TABLE Genres
(
	genre_id int auto_increment primary key,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE GameGenres
(
	game_id INT,
    genre_id INT,
    CONSTRAINT gameGenre_pk PRIMARY KEY (game_id, genre_id),
    CONSTRAINT game_fk FOREIGN KEY (game_id) REFERENCES Games(game_id) ON UPDATE CASCADE,
    CONSTRAINT genre_fk FOREIGN KEY (genre_id) REFERENCES Genres(genre_id) ON UPDATE CASCADE

);

CREATE TABLE WishLists
(
	wishList_id int auto_increment primary key,
    account_id INT,
    game_id INT,
	FOREIGN KEY (account_id) REFERENCES Accounts(account_id) ON DELETE CASCADE,
	FOREIGN KEY (game_id) REFERENCES Games(game_id) ON UPDATE CASCADE
);

CREATE TABLE FavGames
(
	favGame_id int auto_increment primary key,
    account_id INT,
    game_id INT,
	FOREIGN KEY (account_id) REFERENCES Accounts(account_id) ON DELETE CASCADE,
	FOREIGN KEY (game_id) REFERENCES Games(game_id) ON UPDATE CASCADE
);

CREATE TABLE Reviews
(
	review_id INT auto_increment primary key,
    game_id INT,
	account_id INT,
    rating INT NOT NULL,
    FOREIGN KEY (game_id) REFERENCES Games(game_id) ON UPDATE CASCADE, 
    FOREIGN KEY (account_id) REFERENCES Accounts(account_id) ON DELETE CASCADE
);

INSERT INTO Accounts (username, password) VALUES 
('gamer_one', 'pass123'),
('pro_player', 'securepass'),
('casual_gamer', 'gaminglife');

INSERT INTO Publishers(name) VALUES
('Nintendo'),
('Sony Interactive Entertainment'),
('Microsoft Studios');

INSERT INTO Games (title, relDate, pub_id) VALUES 
('The Legend of Zelda', '1986-02-21', 1),
('God of War', '2005-03-22', 2),
('Halo: Combat Evolved', '2001-11-15', 3);

INSERT INTO Genres (name) VALUES 
('Action'),
('RPG'),
('Shooter');

INSERT INTO GameGenres (game_id, genre_id) VALUES 
(1, 2), -- Zelda is an RPG
(2, 1), -- God of War is Action
(3, 3); -- Halo is a Shooter

INSERT INTO WishLists (account_id, game_id) VALUES 
(1, 2), -- Gamer One wants God of War
(2, 1), -- Pro Player wants Zelda
(3, 3); -- Casual Gamer wants Halo

INSERT INTO FavGames (account_id, game_id) VALUES 
(1, 1), -- Gamer One loves Zelda
(2, 2), -- Pro Player loves God of War
(3, 3); -- Casual Gamer loves Halo

INSERT INTO reviews(game_id, account_id, rating) VALUES 
(1, 1, 9), -- Gamer One rates Zelda a 9
(2, 2, 10), -- Pro Player gives God of War a 10
(3, 3, 8); -- Casual Gamer rates Halo an 8

   -- DELETE FROM Accounts 
   -- WHERE username = 'gamer_one';

   -- SELECT * FROM reviews;
-- Accounts, Publishers, Games, Genres, GameGenres, WishLists, FavGames, Reviews;