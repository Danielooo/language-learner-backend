


-- ROLLEN
insert into roles (active, description, role_name) values (true, 'administrator roles' , 'ROLE_ADMIN');
insert into roles (active, description, role_name) values (true, 'user roles' , 'ROLE_USER');

---------------------

-- USERS
-- password unencrypted is 'password'
-- Kim  (userId = 1)
insert into users (password, user_name, first_name, last_name, are_credentials_expired, is_enabled, is_expired, is_locked)
values ('$2a$12$41tSm.wI7.UK5YvM8d9wVOPpHMmz9OqgcZPZvno.Scsa6WT/kceLm','Chef', 'Kim', 'de Groot', false,true,false,false);

-- Jelle  (userId = 2)
insert into users (password, user_name, first_name, last_name, are_credentials_expired, is_enabled, is_expired, is_locked)
values ('$2a$12$41tSm.wI7.UK5YvM8d9wVOPpHMmz9OqgcZPZvno.Scsa6WT/kceLm','Jelle', 'Jelle', 'Middendorp', false, true,false,false);

-- Bert  (userId = 3)
insert into users (password, user_name, first_name, last_name, are_credentials_expired, is_enabled, is_expired, is_locked)
values ('$2a$12$41tSm.wI7.UK5YvM8d9wVOPpHMmz9OqgcZPZvno.Scsa6WT/kceLm','Bert', 'Bert', 'Klein', false,true,false,false);

---------------------

-- ROLLEN
-- Kim is admin en user
insert into user_role (role_id, user_id) values (1,1);
insert into user_role (role_id, user_id) values (2,1);

-- Jelle is admin
insert into user_role (role_id, user_id) values (1,2);

-- Bert is user
insert into user_role (role_id, user_id) values (2,3);

