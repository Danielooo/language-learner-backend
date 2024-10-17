-- ROLLEN
insert into roles (active, description, role_name) values (true, 'administrator roles' , 'ROLE_ADMIN');
insert into roles (active, description, role_name) values (true, 'user roles' , 'ROLE_USER');

---------------------

-- USERS insert
-- password unencrypted is 'password'
-- testUser  (userId = 1)
insert into users (password, user_name, first_name, last_name, are_credentials_expired, is_enabled, is_expired, is_locked)
values ('$2a$12$41tSm.wI7.UK5YvM8d9wVOPpHMmz9OqgcZPZvno.Scsa6WT/kceLm','testUser', 'Kim', 'de Groot', false,true,false,false);



-- ROLLEN insert
-- testUser is admin and user
insert into user_role (role_id, user_id) values (1,1);
insert into user_role (role_id, user_id) values (2,1);

---------------------

-- GROUPS
insert into groups (user_id, group_name) values (1, 'Initial Test Group 1');

-- EXERCISES
insert into exercises (question, answer, group_id) values ('Initial Question 1', 'Initial Answer 1', (select id from groups where group_name = 'Initial Test Group 1'));
insert into exercises (question, answer, group_id) values ('Initial Question 2', 'Initial Answer 2', (select id from groups where group_name = 'Initial Test Group 1'));
