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

DROP TABLE IF EXISTS application cascade;

CREATE TABLE application
(
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    salary               INT,
    sum                     INT,
    rate                    FLOAT,
    period                  INT

);

DROP TABLE IF EXISTS credit cascade;

CREATE TABLE credit
(
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    client_id            INT           NOT NULL,
    form                 INT,
    app                  INT,
    status               varchar2(20),
    finished             tinyint,

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