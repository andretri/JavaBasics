package UserClass;


public class Patient extends Users
{
    private Long SSN = null;  
    private String gender = null;
    public static int patientCounter = 0;
    
    public Patient()
    {
    	super("", "", "", "");
        ++patientCounter;
    }
    
    public Patient(String usrname, String usrpass, String nameTmp, String surnameTmp, Long tmpSSN, String tmpGender)
    {
        super(usrname, usrpass, nameTmp, surnameTmp);
        ++patientCounter;
        this.SetSSN(tmpSSN);
        this.SetGender(tmpGender);
    }
    
    
    public String RegisterPatient()
    {
        //register new patient
    	return "insert into PATIENT(patientAMKA, usrname, password, name, surname, gender) values (?, ?, crypt(?, ?::text), ?, ?, ?);";
    }
    
    public static String SearchApt()
    {
    	return "SELECT APPOINTMENTS.id, DOCTOR.doctorAMKA, DOCTOR.usrname, DOCTOR.name, DOCTOR.surname, APPOINTMENTS.t " + 
				"FROM DOCTOR NATURAL JOIN APPOINTMENTS " + 
				"WHERE (DOCTOR.surname = ? OR DOCTOR.specialty = ?) " + 
				"AND APPOINTMENTS.patientAMKA is null AND APPOINTMENTS.status = false;";
    }
    
    public String MakeApt(boolean flag)
    {
    	if(flag == (true))
    		return "UPDATE APPOINTMENTS SET patientAMKA = ? WHERE id = ?;";
    	else
    		return "UPDATE APPOINTMENTS SET patientAMKA = null WHERE id = ?;";
    }
    
    public String ViewScheduledAppointments()
    {
    	//View schedule of appointments with doctors.
    	return "SELECT A.id, A.t as date, 'Dr. ' || DOCTOR.surname as doc_name,  DPT.name as dpt_name, DOCTOR.doctorAMKA as AMKA\r\n" + 
		"FROM (APPOINTMENTS as A natural join DOCTOR) inner join DEPARTMENTS as DPT on DOCTOR.specialty = DPT.id\r\n" + 
		"WHERE A.patientAMKA = ? and A.status = false;";
    }
    
    public String ViewAppointmentHistory()
    {
    	//View history of appointments.        
        return "SELECT A.id, A.t as date, 'Dr. ' || DOCTOR.surname as doc_name,  DPT.name as dpt_name, DOCTOR.doctorAMKA as AMKA\r\n" + 
        		"FROM (APPOINTMENTS as A natural join DOCTOR) inner join DEPARTMENTS as DPT on DOCTOR.specialty = DPT.id\r\n" + 
        		"WHERE A.patientAMKA = ? and A.status = true;";
    }
    
    public static String AuthenticatePatient(boolean flag)
    {
    	//return "SELECT * FROM PATIENT WHERE usrname = ?;";
    		if(flag == (false))
    			return "SELECT * FROM PATIENT WHERE usrname = ? AND password = crypt(?, patientAMKA::text);";
    		else
    			return "SELECT patientAMKA, usrname, password, name, surname, gender FROM PATIENT WHERE usrname = ?;";
    }
    
    
    //Get SSN
    public void SetSSN(Long tmpSSN) throws NumberFormatException
    {
        if (this.SSN == null)
        	SSN = tmpSSN;
        else 
        	throw new NumberFormatException();
    }
    
    public Long GetSSN()         
    {
        return this.SSN;
    }
    
    public void SetGender(String tmpGender)
    {
        this.gender = tmpGender;
    }
     
    public String GetGender()         
    {
        return this.gender;
    }
}
