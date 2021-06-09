DROP TABLE IF EXISTS client;

CREATE TABLE client (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    first_name varchar2(250) NOT NULL,
    last_name varchar2(250) NOT NULL,
    patronymic varchar2(250),
    date_of_birth date NOT NULL,
    passport_ser varchar2(5) NOT NULL,
    passport_num varchar2(6) NOT NULL,
    address varchar2(250)
);

insert into client (first_name, last_name, patronymic, date_of_birth, passport_ser, passport_num, address)
values (
'Ruslan',
'Vakhitov',
'Radievich',
PARSEDATETIME('3 Nov 1989','dd MMM yyyy','en'),
'1111',
'111111',
'Kazan Tatarstan'
)