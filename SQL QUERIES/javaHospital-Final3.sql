SELECT doctorAMKA, usrname, name, surname, id as aptid, t 
FROM DOCTOR NATURAL JOIN APPOINTMENTS 
WHERE (DOCTOR.surname = '' OR DOCTOR.specialty = 5) 
	AND APPOINTMENTS.patientAMKA is null AND APPOINTMENTS.status = false;
    
    
SELECT * FROM APPOINTMENTS
WHERE patientAMKA is null;

select * from doctor where doctorAMKA = 3531589109644678;

select * from APT;