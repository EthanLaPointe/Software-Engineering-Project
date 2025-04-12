DROP DATABASE IF EXISTS test_gamed;
CREATE DATABASE test_gamed;
USE test_gamed;

CREATE TABLE Game
(
	game_id int auto_increment primary key,
    title VARCHAR(50) NOT NULL,
    relDate DATE NOT NULL
);

INSERT INTO Game (title, relDate) VALUES 
('The Legend of Zelda', '1986-02-21'),
('Super Mario Bros.', '1985-09-13'),
('Minecraft', '2011-11-18'),
('Halo: Combat Evolved', '2001-11-15'),
('Elden Ring', '2022-02-25');

SELECT * FROM Game;



