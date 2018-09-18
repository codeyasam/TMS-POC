INSERT INTO Application (name) VALUES ('Application1');
INSERT INTO Application (name) VALUES ('Application2');

INSERT INTO END_USER (username, password) VALUES ('codeyasam', '$2a$10$vJcniyvdee2./DXa.VUOle3fv0hgnRM2NNZRSo1crwColif9BvVUi');
INSERT INTO END_USER (username, password) VALUES ('jojo', '$2a$10$vJcniyvdee2./DXa.VUOle3fv0hgnRM2NNZRSo1crwColif9BvVUi');

INSERT INTO ROLE (role) VALUES ('USER');
INSERT INTO ROLE (role) VALUES ('ADMIN');

INSERT INTO END_USER_ROLES (end_user_id, role_id) VALUES (1, 1);
INSERT INTO END_USER_ROLES (end_user_id, role_id) VALUES (1, 2);
INSERT INTO END_USER_ROLES (end_user_id, role_id) VALUES (2, 1);
