INSERT INTO worker (name, birthday, level, salary)
VALUES (?, ?, ?, ?);

INSERT INTO client (name)
VALUES (?);

INSERT INTO project (client_id, start_date, finish_date)
VALUES (?, ?, ?);

INSERT INTO project_worker (project_id, worker_id)
VALUES (?, ?);