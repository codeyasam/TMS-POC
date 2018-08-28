INSERT INTO application (name) VALUES ('Sample');
INSERT INTO module (name) VALUES ('random1');
INSERT INTO module (name) VALUES ('random2');
INSERT INTO module (name) VALUES ('byFilter');

INSERT INTO machine (name) VALUES ('VM1'); 
INSERT INTO machine (name) VALUES ('VM2');
INSERT INTO machine (name) VALUES ('VM3');

INSERT INTO testcase (name) VALUES ('testcase1');
INSERT INTO testcase (name) VALUES ('testcase2');
INSERT INTO testcase (name) VALUES ('testcase3');
INSERT INTO testcase (name) VALUES ('testcase4');
INSERT INTO testcase (name) VALUES ('testcase5');

INSERT INTO machine_test_cases (machine_id, test_case_id) VALUES (2, 1);
INSERT INTO machine_test_cases (machine_id, test_case_id) VALUES (2, 2);

INSERT INTO machine_test_cases (machine_id, test_case_id) VALUES (3, 1);
INSERT INTO machine_test_cases (machine_id, test_case_id) VALUES (3, 2);
INSERT INTO machine_test_cases (machine_id, test_case_id) VALUES (3, 3);
INSERT INTO machine_test_cases (machine_id, test_case_id) VALUES (3, 4);
INSERT INTO machine_test_cases (machine_id, test_case_id) VALUES (3, 5);


