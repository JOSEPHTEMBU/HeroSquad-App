CREATE DATABASE heroapp;

\c heroapp

CREATE TABLE heroes (
 id SERIAL PRIMARY KEY,
 name VARCHAR,
 power VARCHAR,
 weakness VARCHAR,
 age INTEGER,
 squadId INTEGER
);

CREATE TABLE squads (
 id SERIAL PRIMARY KEY,
 name VARCHAR,
 max_size INTEGER,
 cause VARCHAR
);
