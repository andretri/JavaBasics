DROP TABLE IF EXISTS DEPARTMENTS;
create table DEPARTMENTS(
	id serial primary key,
	name varchar(45) not null
);

 
DROP TABLE IF EXISTS ADMINS;
create table ADMINS(
	id serial primary key,
	userid varchar(40) not null unique,
	password varchar(45) not null,
	name varchar(20) not null,
	surname varchar(20) not null
);


DROP TABLE IF EXISTS PATIENT;
create table PATIENT(
	patientAMKA bigint primary key,
	userid varchar(40) not null unique,
	password varchar(45) not null,
	name varchar(20) not null,
	surname varchar(20) not null,
	gender varchar(6),

	check(gender in ('Male', 'Female'))
);

DROP TABLE IF EXISTS DOCTOR;
create table DOCTOR(
	doctorAMKA bigint primary key,
	username varchar(40) not null unique,
	password varchar(45) not null,
	name varchar(20) not null,
	surname varchar(20) not null,
	specialty integer references DEPARTMENTS,
    admin_id integer references ADMINS
);

DROP TABLE IF EXISTS APPOINTMENTS;
create table APPOINTMENTS(
	id serial primary key,
	t timestamp,
	patientAMKA bigint references PATIENT,
	doctorAMKA bigint references DOCTOR,
	status boolean default (false),
    diagnosis text
);

DROP TABLE IF EXISTS APT;
create table APT(
    id int primary key references APPOINTMENTS,
    datetime_made timestamp
);
    
CREATE OR REPLACE FUNCTION upd_apt()
RETURNS TRIGGER AS $apt_rec$
DECLARE 
	b INTEGER DEFAULT 0;
BEGIN
	IF(new.status = true) THEN
    	RETURN NEW;
	ELSIF(new.patientAMKA is not null) THEN
		INSERT INTO APT (id, datetime_made)
		VALUES (new.id, current_timestamp(0));
        RETURN NEW;
    ELSIF(new.patientAMKA is null) THEN
    	with a as (
        DELETE 
        FROM APT 
        WHERE (id = old.id) AND current_timestamp(0)-datetime_made >= interval '-3 days' returning 1
    	)
        select count(*) into b from a;
        IF (b = 0) THEN 
        	RETURN null;
        ELSE
        	RETURN NEW;
        END IF;
    END IF;       
END; $apt_rec$ 
LANGUAGE plpgsql; 

DROP TRIGGER apt_rec ON APPOINTMENTS;
    
CREATE TRIGGER apt_rec BEFORE UPDATE ON APPOINTMENTS 
FOR EACH ROW EXECUTE PROCEDURE upd_apt();

CREATE OR REPLACE FUNCTION upd_ins()
RETURNS TRIGGER AS $apt_rec$
DECLARE 
	a INTEGER DEFAULT 0;
BEGIN
	SELECT count(*) into a FROM APPOINTMENTS WHERE t=new.t;
	
    If (a = 0) THEN
    	RETURN NEW;
	ELSE
    	RETURN NULL;
	END IF;
END; $apt_rec$ 
LANGUAGE plpgsql; 

CREATE TRIGGER apt_ins BEFORE INSERT ON APPOINTMENTS 
FOR EACH ROW EXECUTE PROCEDURE upd_ins();

CREATE OR REPLACE FUNCTION upd_del()
RETURNS TRIGGER AS $apt_rec$
DECLARE 
	b INTEGER DEFAULT 0;
    c BOOLEAN DEFAULT FALSE;
BEGIN
	with a as (
        DELETE 
        FROM APT 
        WHERE (id = old.id) AND current_timestamp(0)-datetime_made >= interval '-3 days' returning 1
    )
	select count(*) into b from a;
    select patientAMKA IS NULL into c from APPOINTMENTS where id=old.id;
	IF (c is true and b = 0) THEN
		RETURN OLD;
    ELSIF (c is false and b = 1) THEN
    	RETURN OLD;
    ELSE
    	RETURN NULL;
	END IF;
END; $apt_rec$ 
LANGUAGE plpgsql; 
    
DROP TRIGGER apt_del ON APPOINTMENTS;

ALTER TABLE APPOINTMENTS DISABLE TRIGGER ALL;
ALTER TABLE APPOINTMENTS ENABLE TRIGGER ALL;

CREATE TRIGGER apt_del BEFORE DELETE ON APPOINTMENTS
FOR EACH ROW EXECUTE PROCEDURE upd_del();

INSERT INTO APPOINTMENTS (t, patientAMKA, doctorAMKA, status) VALUES (current_timestamp(0), null, 3531589109644678, false);
select * from APPOINTMENTS where patientAMKA is null;
delete from appointments where id=28027;

SELECT * FROM APT;

copy PATIENT(patientAMKA, userid, password, name, surname, gender) from '/home/andretri7/Desktop/Dataset/patient.csv' delimiter ',' CSV HEADER;
copy PATIENT(patientAMKA, userid, password, name, surname, gender) from 'H:\Java-Servlets-project-spring-2017\javaHospital\Dataset\patient.csv' delimiter ',' CSV HEADER;

copy DEPARTMENTS(id, name) from '/home/andretri7/Desktop/Dataset/departments.csv' delimiter ',' CSV HEADER;
copy DEPARTMENTS(id, name) from 'H:\Java-Servlets-project-spring-2017\javaHospital\Dataset\departments.csv' delimiter ',' CSV HEADER;

copy DOCTOR(doctorAMKA, username, password, name, surname, specialty) from '/home/andretri7/Desktop/Dataset/doctor.csv' delimiter ',' CSV HEADER;
copy DOCTOR(doctorAMKA, username, password, name, surname, specialty) from 'H:\Java-Servlets-project-spring-2017\javaHospital\Dataset\doctor.csv' delimiter ',' CSV HEADER;

copy APPOINTMENTS(id, t, patientAMKA, doctorAMKA, diagnosis) from '/home/andretri7/Desktop/Dataset/appointments.csv' delimiter ',' CSV HEADER;
copy APPOINTMENTS(id, t, patientAMKA, doctorAMKA, diagnosis) from 'H:\Java-Servlets-project-spring-2017\javaHospital\Dataset\appointments.csv' delimiter ',' CSV HEADER;

insert into ADMINS(userid, password, name, surname) values ('andretri7', '4dm1n5-1', 'Andrew', 'Tritsarolis');
insert into ADMINS(userid, password, name, surname) values ('g.gegian', '4dm1n5-2', 'George', 'Gegiannis');
insert into ADMINS(userid, password, name, surname) values ('giorgostheo97', '4dm1n5-3', 'George', 'Theodoropoulos');

UPDATE DOCTOR SET admin_id = random() * 2+1;

UPDATE APPOINTMENTS SET status=true where diagnosis is not null;
UPDATE APPOINTMENTS SET status=false where diagnosis is null;

ALTER TABLE APPOINTMENTS DROP COLUMN diagnosis;






