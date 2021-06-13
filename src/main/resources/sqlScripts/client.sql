DROP TABLE IF EXISTS client cascade;

CREATE TABLE client (
    id              INT AUTO_INCREMENT  PRIMARY KEY,
    username        varchar(50) NOT NULL,
    password        varchar(60) NOT NULL,
    first_name      varchar2(250) NOT NULL,
    last_name       varchar2(250) NOT NULL,
    patronymic      varchar2(250),
    date_of_birth   date NOT NULL,
    passport_ser    varchar2(5) NOT NULL,
    passport_num    varchar2(6) NOT NULL,
    address         varchar2(250)
);