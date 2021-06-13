DROP TABLE IF EXISTS role cascade;

CREATE TABLE role (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    name    varchar(50) DEFAULT NULL
);

INSERT INTO role (name)
VALUES
('ROLE_ADMIN'),('ROLE_CLIENT');

DROP TABLE IF EXISTS users_roles cascade;

CREATE TABLE users_roles (
    user_id int(11) NOT NULL,
    role_id int(11) NOT NULL,

    PRIMARY KEY (user_id,role_id),

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
VALUES (
        'admin',
        '$2a$10$19Q2kg/nfPSc2wdfvT9ldemKpkRTA9WPvSHg/SNs8SxoNaVL/Q5he',
        'Регис',
        'Эмиель',
        PARSEDATETIME('01 Jan 1970','dd MMM yyyy','en'),
        '0000',
        '000000'
       );

INSERT INTO users_roles (user_id,role_id)
VALUES (1, 1);