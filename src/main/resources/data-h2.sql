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
INSERT INTO Application (name) VALUES ('Application11');
INSERT INTO Application (name) VALUES ('Application12');
INSERT INTO Application (name) VALUES ('Application13');
INSERT INTO Application (name) VALUES ('Application14');
INSERT INTO Application (name) VALUES ('Application15');
INSERT INTO Application (name) VALUES ('Application16');
INSERT INTO Application (name) VALUES ('Application17');
INSERT INTO Application (name) VALUES ('Application18');
INSERT INTO Application (name) VALUES ('Application19');
INSERT INTO Application (name) VALUES ('Application20');
INSERT INTO Application (name) VALUES ('Application21');
INSERT INTO Application (name) VALUES ('Application22');
INSERT INTO Application (name) VALUES ('Application23');
INSERT INTO Application (name) VALUES ('Application24');
INSERT INTO Application (name) VALUES ('Application25');
INSERT INTO Application (name) VALUES ('Application26');
INSERT INTO Application (name) VALUES ('Application27');
INSERT INTO Application (name) VALUES ('Application28');
INSERT INTO Application (name) VALUES ('Application29');
INSERT INTO Application (name) VALUES ('Application30');

INSERT INTO Module (name, application_id) VALUES ('module1', 1);
INSERT INTO Module (name, application_id) VALUES ('module2', 1);
INSERT INTO Module (name, application_id) VALUES ('module3', 1);
INSERT INTO Module (name, application_id) VALUES ('module4', 1);
INSERT INTO Module (name, application_id) VALUES ('module5', 1);
INSERT INTO Module (name, application_id) VALUES ('module6', 1);


INSERT INTO END_USER (username, password) VALUES ('codeyasam', '$2a$10$vJcniyvdee2./DXa.VUOle3fv0hgnRM2NNZRSo1crwColif9BvVUi');
INSERT INTO END_USER (username, password) VALUES ('jojo', '$2a$10$vJcniyvdee2./DXa.VUOle3fv0hgnRM2NNZRSo1crwColif9BvVUi');

INSERT INTO ROLE (role) VALUES ('USER');
INSERT INTO ROLE (role) VALUES ('ADMIN');

INSERT INTO END_USER_ROLES (end_user_id, role_id) VALUES (1, 1);
INSERT INTO END_USER_ROLES (end_user_id, role_id) VALUES (1, 2);
INSERT INTO END_USER_ROLES (end_user_id, role_id) VALUES (2, 1);
