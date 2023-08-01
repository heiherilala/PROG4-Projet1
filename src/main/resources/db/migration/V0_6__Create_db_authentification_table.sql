CREATE TABLE IF NOT EXISTS users(
    username varchar not null PRIMARY KEY,
    password varchar not null,
    enabled boolean
);