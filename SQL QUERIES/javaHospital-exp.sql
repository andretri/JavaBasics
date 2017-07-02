
SELECT setval('admins_id_seq', (SELECT max(id) FROM ADMINS));
SELECT setval('appointments_id_seq', (SELECT max(id) FROM APPOINTMENTS));
SELECT setval('departments_id_seq', (SELECT max(id) FROM DEPARTMENTS));

INSERT INTO APPOINTMENTS (t, patientAMKA, doctorAMKA, status) 
VALUES (current_timestamp(0), null, null, false);

SELECT *
FROM APPOINTMENTS
WHERE patientAMKA is null AND doctorAMKA is null AND status = false;