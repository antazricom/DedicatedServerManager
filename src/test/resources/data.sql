INSERT INTO application
    (id, name, status, created_at, updated_at)
VALUES (1, 'App 1', 'ENABLE', now(), now()),
       (2, 'App 2', 'DISABLE', now(), now()),
       (3, 'App 3', 'DISABLE', now(), now());

ALTER SEQUENCE application_id_seq RESTART WITH 4;


INSERT INTO action
    (id, description, type, application_id, created_at, updated_at)
VALUES (1, 'Action Description 1', 'INSTALL', '1', timestamp '2021-12-13 09:00:00', timestamp '2021-12-13 09:00:00'),
       (2, 'Action Description 2', 'INSTALL', '2', timestamp '2021-12-13 09:00:00', timestamp '2021-12-13 09:00:00'),
       (3, 'Action Description 3', 'INSTALL', '3', timestamp '2021-12-13 09:00:00', timestamp '2021-12-13 09:00:00'),
       (4, 'Action Description 4', 'UPDATE', '2', timestamp '2021-12-13 09:00:00', timestamp '2021-12-13 09:00:00'),
       (5, 'Action Description 5', 'TEST', '2', timestamp '2021-12-13 09:00:00', timestamp '2021-12-13 09:00:00'),
       (6, 'Action Description 6', 'RUNNING', '1', timestamp '2021-12-13 09:00:00', timestamp '2021-12-13 09:00:00'),
       (7, 'Action Description 7', 'RUNNING', '2', timestamp '2021-12-14 08:00:00', timestamp '2021-12-14 08:00:00'),
       (8, 'Action Description 8', 'STOP', '1', timestamp '2021-12-14 08:00:00', timestamp '2021-12-14 08:00:00'),
       (9, 'Action Description 9', 'REMOVE', '3', timestamp '2021-12-14 08:00:00', timestamp '2021-12-14 08:00:00');

ALTER SEQUENCE action_id_seq RESTART WITH 10;