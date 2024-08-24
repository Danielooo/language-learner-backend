-- ROLLEN
insert into roles (active, description, role_name) values (true, 'administrator roles' , 'ROLE_ADMIN');
insert into roles (active, description, role_name) values (true, 'user roles' , 'ROLE_USER');

---------------------

-- USERS
-- password unencrypted is 'password'
-- Kim  (userId = 1)
insert into users (password, user_name, are_credentials_expired, is_enabled, is_expired, is_locked)
    values ('$2a$12$41tSm.wI7.UK5YvM8d9wVOPpHMmz9OqgcZPZvno.Scsa6WT/kceLm','Kim', false,true,false,false);

-- Jelle  (userId = 2)
insert into users (password, user_name, are_credentials_expired, is_enabled, is_expired, is_locked)
    values ('$2a$12$41tSm.wI7.UK5YvM8d9wVOPpHMmz9OqgcZPZvno.Scsa6WT/kceLm','Jelle', false,true,false,false);

-- Bert  (userId = 3)
insert into users (password, user_name, are_credentials_expired, is_enabled, is_expired, is_locked)
    values ('$2a$12$41tSm.wI7.UK5YvM8d9wVOPpHMmz9OqgcZPZvno.Scsa6WT/kceLm','Bert', false,true,false,false);

---------------------

-- ROLLEN
-- Kim is admin en user
insert into user_role (role_id, user_id) values (1,1);
insert into user_role (role_id, user_id) values (2,1);

-- Jelle is admin
insert into user_role (role_id, user_id) values (1,2);

-- Bert is user
insert into user_role (role_id, user_id) values (2,3);

---------------------

-- LOSSE EXERCISES
insert into exercises (question, answer) values  ('Friend', 'Amico');
insert into exercises (question, answer) values  ('Veel', 'Molto');

---------------------

-- GROUP
-- Insert group
insert into groups (user_id, group_name) values (3, 'First exampleGroup');

-- Insert exercises and link to group
insert into exercises (question, answer, group_id) values ('zeggen', 'dire', (select id from groups where group_name = 'First exampleGroup'));
insert into exercises (question, answer, group_id) values ('lopen', 'camminare', (select id from groups where group_name = 'First exampleGroup'));
insert into exercises (question, answer, group_id) values ('lezen', 'leggere', (select id from groups where group_name = 'First exampleGroup'));

-- -- Insert exercises and link to group
-- insert into exercises (question, answer, group_id) values ('auto', 'machina', (select id from groups where group_name = 'First Group'));
-- insert into exercises (question, answer, group_id) values ('schoonmaken', 'pulire', (select id from groups where group_name = 'First Group'));
-- insert into exercises (question, answer, group_id) values ('doen', 'fare', (select id from groups where group_name = 'First Group'));


-- UserInputAnswer
-- inputs for Kim (Admin en User)
insert into user_input_answers (user_id, exercise_id, user_input) values (1, 1, 'AMico');
insert into user_input_answers (user_id, exercise_id, user_input) values (1, 2, 'MOlto');



-- inputs for Bert (User)
insert into user_input_answers (user_id, exercise_id, user_input) values (3, 1, 'MAchina');
insert into user_input_answers (user_id, exercise_id, user_input) values (3, 2, 'PULire');
