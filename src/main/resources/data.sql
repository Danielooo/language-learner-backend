-- Tables worden gevonden in PGAdmin maar niet in IntelliJ database feature

insert into roles (active, description, role_name) values (true, 'administrator roles' , 'ROLE_ADMIN');
insert into roles (active, description, role_name) values (true, 'user roles' , 'ROLE_USER');

-- password unencrypted is 'password'
insert into users (password, user_name, are_credentials_expired, is_enabled, is_expired, is_locked)
    values ('$2a$12$41tSm.wI7.UK5YvM8d9wVOPpHMmz9OqgcZPZvno.Scsa6WT/kceLm','Kim', false,true,false,false);
insert into users (password, user_name, are_credentials_expired, is_enabled, is_expired, is_locked)
    values ('$2a$12$41tSm.wI7.UK5YvM8d9wVOPpHMmz9OqgcZPZvno.Scsa6WT/kceLm','Bert', false,true,false,false);

-- Kim is admin, Bert is user en admin
insert into user_role (role_id, user_id) values (1,1);
insert into user_role (role_id, user_id) values (2,2);
insert into user_role (role_id, user_id) values (2,1);