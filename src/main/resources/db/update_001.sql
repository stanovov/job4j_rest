CREATE TABLE IF NOT EXISTS person (
    id SERIAL PRIMARY KEY NOT NULL,
    login VARCHAR(2000),
    password VARCHAR(2000)
);

CREATE TABLE IF NOT EXISTS employees (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255),
    surname VARCHAR(255),
    inn INT,
    hiring_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS employees_persons (
    person_id INT NOT NULL,
    employee_id INT NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person(id),
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

INSERT INTO person (login, password) VALUES ('sestanovov', '123');
INSERT INTO person (login, password) VALUES ('stanovov_semyon', '456');
INSERT INTO person (login, password) VALUES ('ivan', 'pwd');
INSERT INTO person (login, password) VALUES ('kirilllllll', '777');

INSERT INTO employees(name, surname, inn, hiring_date) VALUES ('Semyon', 'Stanovov', 1234567890, '2021-05-16 15:24:13');
INSERT INTO employees(name, surname, inn, hiring_date) VALUES ('Ivan', 'Ivanov', 0232424911, '2021-07-01 10:09:52');
INSERT INTO employees(name, surname, inn, hiring_date) VALUES ('Kirill', 'Beljaev', 233123, '2021-08-23 09:32:33');

INSERT INTO employees_persons(person_id, employee_id) VALUES (1, 1);
INSERT INTO employees_persons(person_id, employee_id) VALUES (2, 1);
INSERT INTO employees_persons(person_id, employee_id) VALUES (3, 2);
INSERT INTO employees_persons(person_id, employee_id) VALUES (4, 3);