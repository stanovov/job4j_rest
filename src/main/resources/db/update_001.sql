CREATE TABLE IF NOT EXISTS person (
    id SERIAL PRIMARY KEY NOT NULL,
    login VARCHAR(2000),
    password VARCHAR(2000)
);

INSERT INTO person (login, password) VALUES ('parsentev', '123');
INSERT INTO person (login, password) VALUES ('ban', '123');
INSERT INTO person (login, password) VALUES ('ivan', '123');