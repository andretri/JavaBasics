SELECT * FROM public.patient
ORDER BY RANDOM() ASC LIMIT 1

ALTER TABLE DOCTOR ADD COLUMN banned boolean DEFAULT false;
ALTER TABLE PATIENT ADD COLUMN banned boolean DEFAULT false;

CREATE EXTENSION pgcrypto;
SELECT crypt('7pTUpMICU', gen_salt('md5'));

SELECT * FROM PATIENT WHERE usrname = 'ccarpenterit@people.com.cn' AND 
			password = crypt('khJuWc', patientAMKA::text);

SELECT md5(random()::VARCHAR(45));

SELECT * FROM DOCTOR;

SELECT * FROM PATIENT WHERE patientAMKA=3580991459535060;

UPDATE PATIENT SET password = crypt(password, patientAMKA::text) WHERE patientamka='3580991459535060';

UPDATE PATIENT SET password = crypt(password, patientAMKA::text);            
UPDATE DOCTOR SET password = crypt(password, doctorAMKA::text);  
UPDATE ADMINS SET password = crypt(password, 3580991459535060::text);


SELECT * FROM PATIENT;

DELETE FROM patient WHERE patientamka=3580991459535060;