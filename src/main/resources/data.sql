-- Tables worden gevonden in PGAdmin maar niet in IntelliJ database feature

insert into roles (active, description, role_name) values (true, 'administrator roles' , 'ROLE_ADMIN');
insert into roles (active, description, role_name) values (true, 'user roles' , 'ROLE_USER');

insert into users (password, user_name, are_credentials_expired, is_enabled, is_expired, is_locked)
    values ('$2a$10$bJxwWc3A3DBzke7Gnb/MZ.lLXmvOIE/DFAd6QUnBvWhn7c7D1zY4C','kim', false,true,false,false);
insert into users (password, user_name, are_credentials_expired, is_enabled, is_expired, is_locked)
    values ('$2a$10$bJxwWc3A3DBzke7Gnb/MZ.lLXmvOIE/DFAd6QUnBvWhn7c7D1zY4C','Bertrand', false,true,false,false);

insert into user_role (role_id, user_id) values (1,1);
insert into user_role (role_id, user_id) values (2,2);
insert into user_role (role_id, user_id) values (2,1);