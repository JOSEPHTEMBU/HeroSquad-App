SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS heroes (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 power VARCHAR,
 weakness VARCHAR,
 age INTEGER,
 squadId INTEGER,
);

CREATE TABLE IF NOT EXISTS squads (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 max_size INTEGER,
 cause VARCHAR,
);

