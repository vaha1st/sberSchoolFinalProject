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