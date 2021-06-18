DROP TABLE IF EXISTS client cascade;

CREATE TABLE client
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    username      varchar(50)   NOT NULL,
    password      varchar(60)   NOT NULL,
    first_name    varchar2(250) NOT NULL,
    last_name     varchar2(250) NOT NULL,
    patronymic    varchar2(250),
    date_of_birth date          NOT NULL,
    passport_ser  varchar2(5)   NOT NULL,
    passport_num  varchar2(6)   NOT NULL,
    address       varchar2(250)
);

DROP TABLE IF EXISTS role cascade;

CREATE TABLE role
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name varchar(50) DEFAULT NULL
);

INSERT INTO role (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_CLIENT');

DROP TABLE IF EXISTS users_roles cascade;

CREATE TABLE users_roles
(
    user_id int(11) NOT NULL,
    role_id int(11) NOT NULL,

    PRIMARY KEY (user_id, role_id),

    CONSTRAINT FK_USER_05 FOREIGN KEY (user_id)
        REFERENCES client (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_ROLE FOREIGN KEY (role_id)
        REFERENCES role (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE INDEX FK_ROLE_idx on users_roles (role_id);


-- Админская учетка admin - admin
INSERT INTO client (USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, PASSPORT_SER, PASSPORT_NUM)
VALUES ('admin',
        '$2a$10$19Q2kg/nfPSc2wdfvT9ldemKpkRTA9WPvSHg/SNs8SxoNaVL/Q5he',
        'Регис',
        'Эмиель',
        PARSEDATETIME('01 Jan 1970', 'dd MMM yyyy', 'en'),
        '0000',
        '000000');
-- Тестировачная учетка test - test
INSERT INTO client (username, password, first_name, last_name, patronymic, date_of_birth, passport_ser, passport_num,
                    address)
VALUES ('test',
        '$2a$10$f9tzlDaZjP9M6.q.MUX8GecnY.HbhtqMg8IPq8vtl3iDXNJb9w7By',
        'Гриндевальд',
        'Геллерт',
        'Джеймсович',
        PARSEDATETIME('01 Jan 1970', 'dd MMM yyyy', 'en'),
        '0000',
        '000000',
        'Дурмстранг');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (2, 2);

DROP TABLE IF EXISTS form cascade;

CREATE TABLE form
(
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    client_id            INT           NOT NULL,
    first_name           varchar2(250) NOT NULL,
    last_name            varchar2(250) NOT NULL,
    patronymic           varchar2(250),
    date_of_birth        date          NOT NULL,
    passport_ser         varchar2(5)   NOT NULL,
    passport_num         varchar2(6)   NOT NULL,
    address              varchar2(250),
    personal_data_accept tinyint       NOT NULL,

    CONSTRAINT FK_CLIENT_05 FOREIGN KEY (client_id)
        REFERENCES client (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);

DROP TABLE IF EXISTS credit_scheme cascade;

CREATE TABLE credit_scheme
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       varchar2(50),
    min_sum    INT   NOT NULL,
    max_sum    INT   NOT NULL,
    rate       FLOAT NOT NULL,
    max_period INT   NOT NULL
);

INSERT INTO credit_scheme (name, min_sum, max_sum, rate, max_period)
VALUES ('Потребительский МИКРО',
        20000,
        100000,
        12.0,
        12);
INSERT INTO credit_scheme (name, min_sum, max_sum, rate, max_period)
VALUES ('Потребительский Стандарт',
        50000,
        1000000,
        8.5,
        60);
INSERT INTO credit_scheme (name, min_sum, max_sum, rate, max_period)
VALUES ('Потребительский VIP',
        750000,
        10000000,
        4.4,
        120);

DROP TABLE IF EXISTS application cascade;

CREATE TABLE application
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    salary       INT NOT NULL,
    scheme       INT,
    sum          INT NOT NULL,
    period       INT NOT NULL,
    payment      FLOAT,
    pre_approved TINYINT,

    CONSTRAINT FK_SCH_01 FOREIGN KEY (scheme)
        REFERENCES credit_scheme (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION

);

DROP TABLE IF EXISTS credit cascade;

CREATE TABLE credit
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    client_id INT NOT NULL,
    form      INT,
    app       INT,
    status    varchar2(20),
    finished  tinyint,

    CONSTRAINT FK_CLIENT_06 FOREIGN KEY (client_id)
        REFERENCES client (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_FORM_01 FOREIGN KEY (form)
        REFERENCES form (id)
        ON DELETE cascade ON UPDATE cascade,
    CONSTRAINT FK_APP_01 FOREIGN KEY (app)
        REFERENCES application (id)
        ON DELETE cascade ON UPDATE cascade
);