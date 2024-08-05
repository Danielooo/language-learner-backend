-- Tables worden gevonden in PGAdmin maar niet in IntelliJ database feature

insert into roles (active, description, role_name) values (true, 'administrator roles' , 'ROLE_ADMIN');
insert into roles (active, description, role_name) values (true, 'user roles' , 'ROLE_USER');

-- password unencrypted is 'password'
-- Kim
insert into users (password, user_name, are_credentials_expired, is_enabled, is_expired, is_locked)
    values ('$2a$12$41tSm.wI7.UK5YvM8d9wVOPpHMmz9OqgcZPZvno.Scsa6WT/kceLm','Kim', false,true,false,false);

-- Jelle
insert into users (password, user_name, are_credentials_expired, is_enabled, is_expired, is_locked)
    values ('$2a$12$41tSm.wI7.UK5YvM8d9wVOPpHMmz9OqgcZPZvno.Scsa6WT/kceLm','Jelle', false,true,false,false);

-- Bert
insert into users (password, user_name, are_credentials_expired, is_enabled, is_expired, is_locked)
    values ('$2a$12$41tSm.wI7.UK5YvM8d9wVOPpHMmz9OqgcZPZvno.Scsa6WT/kceLm','Bert', false,true,false,false);

---------------------

-- Kim is admin en user
insert into user_role (role_id, user_id) values (1,1);
insert into user_role (role_id, user_id) values (2,1);

-- Jelle is admin
insert into user_role (role_id, user_id) values (1,2);

-- Bert is user
insert into user_role (role_id, user_id) values (2,3);

---------------------

insert into excercises (question, answer) values  ('Friend', 'Amico');
insert into excercises (question, answer) values  ('Veel', 'Molto');

