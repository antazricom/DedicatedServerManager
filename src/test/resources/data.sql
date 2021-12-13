INSERT INTO application
    (id, name, status, created_at, updated_at)
VALUES (1, 'App 1', 'DISABLE', now(), now()),
       (2, 'App 2', 'DISABLE', now(), now()),
       (3, 'App 3', 'DISABLE', now(), now());

INSERT INTO action
    (id, description, type, application_id, created_at, updated_at)
VALUES (1, 'Action Description 1', 'INSTALL', '1', now(), now()),
       (2, 'Action Description 2', 'INSTALL', '2', now(), now()),
       (3, 'Action Description 3', 'INSTALL', '3', now(), now()),
       (4, 'Action Description 4', 'UPDATE', '2', now(), now()),
       (5, 'Action Description 5', 'TEST', '2', now(), now()),
       (6, 'Action Description 6', 'RUN', '1', now(), now()),
       (7, 'Action Description 7', 'RUN', '2', now(), now()),
       (8, 'Action Description 8', 'STOP', '1', now(), now()),
       (9, 'Action Description 9', 'REMOVE', '3', now(), now());