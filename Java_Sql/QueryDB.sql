--Create database
CREATE DATABASE DBcubeGame;

-- Create player table
CREATE TABLE player (
    PID SERIAL PRIMARY KEY,
    firstName VARCHAR(20)
);

-- Create levels table
CREATE TABLE levels (
    LID INT PRIMARY KEY,
    numOfColors INT
);

-- Create game table
CREATE TABLE game (
    GID SERIAL PRIMARY KEY,
    PID INT NOT NULL,
    LID INT
);

-- Create playerScore table
CREATE TABLE playerScore (
    score INT,
    GID INT PRIMARY KEY,
    CONSTRAINT playerGID FOREIGN KEY (GID) REFERENCES game(GID)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

-- Create leaderBoard table
CREATE TABLE leaderBoard (
    place SERIAL PRIMARY KEY,
    PID INT NOT NULL,
    score INT,
    LID INT,
    CONSTRAINT leadPID FOREIGN KEY (PID) REFERENCES player(PID)
);

-- Insert values into player table
INSERT INTO player (firstName) VALUES
    ('OmriA'),
	('DavidK');


-- Insert values into levels table
INSERT INTO levels (LID, numOfColors) VALUES
    (1, 4),
    (2, 5),
    (3, 6);


-- Insert values into leaderBoard table
INSERT INTO leaderBoard (PID, score, LID) VALUES
    (1, 30, 1),
    (2, 60, 1);

-- Update leaderboard set score
UPDATE leaderBoard SET score = 55 WHERE PID = 2;

--Select LID leaderBoard from high to low
SELECT * FROM leaderBoard WHERE LID = 1 OR LID = 2 OR LID =  3 ORDER BY "score" DESC LIMIT 10;

--Delete leaderboard
DELETE FROM leaderBoard WHERE (score < 70);



--just for me start here:

--Delete all tables
DROP TABLE leaderBoard CASCADE;
DROP TABLE playerScore;
DROP TABLE game;
DROP TABLE levels;
DROP TABLE player;


--Show all tables
Select * from player;
Select * from levels;
Select * from playerScore;
Select * from leaderBoard;
Select * from game;

--DELETE all values from all by order 
DELETE FROM game;
DELETE FROM leaderBoard;
DELETE FROM player; 
 

--end here
