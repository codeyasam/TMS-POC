INSERT INTO Application (name) VALUES ('Application1');
INSERT INTO Application (name) VALUES ('Application2');
INSERT INTO Application (name) VALUES ('Application3');
INSERT INTO Application (name) VALUES ('Application4');
INSERT INTO Application (name) VALUES ('Application5');
INSERT INTO Application (name) VALUES ('Application6');
INSERT INTO Application (name) VALUES ('Application7');
INSERT INTO Application (name) VALUES ('Application8');
INSERT INTO Application (name) VALUES ('Application9');
INSERT INTO Application (name) VALUES ('Application10');

INSERT INTO END_USER (username, password) VALUES ('codeyasam', '$2a$10$vJcniyvdee2./DXa.VUOle3fv0hgnRM2NNZRSo1crwColif9BvVUi');
INSERT INTO END_USER (username, password) VALUES ('jojo', '$2a$10$vJcniyvdee2./DXa.VUOle3fv0hgnRM2NNZRSo1crwColif9BvVUi');

INSERT INTO ROLE (role) VALUES ('USER');
INSERT INTO ROLE (role) VALUES ('ADMIN');

INSERT INTO END_USER_ROLES (end_user_id, role_id) VALUES (1, 1);
INSERT INTO END_USER_ROLES (end_user_id, role_id) VALUES (1, 2);
INSERT INTO END_USER_ROLES (end_user_id, role_id) VALUES (2, 1);
