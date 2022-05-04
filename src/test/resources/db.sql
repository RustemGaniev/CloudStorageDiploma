create table users_test
(
    login    varchar(35),
    password varchar(100),
    authority varchar(35)
);

insert into users_test(login, password, authority)
values ('user', 'user', 'ROLE_USER');

