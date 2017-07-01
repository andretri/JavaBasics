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
    
    
    public void CreateAppointment()
    {
    	//"Ability to Cancel appointments between a two people."
        //"\nA Feature WIP.\n"
        //"It will be Complete once we Connect "
        //"Java to a Database, which the function will access "
        //"all the scheduled appointments of a selected user (either patient or " 
        //"doctor) based on his SSN.");
    }
    
    
    public void CancelAppointment()
    {
        //"Ability to Cancel appointments between a two people."
        //"\nA Feature WIP.\n"
        //"It will be Complete once we Connect "
        //"Java to a Database, which the function will access "
        //"all the scheduled appointments of a selected user (either patient or " 
        //"doctor) based on his SSN.");
    }
    
    
    public String RegisterPatient()
    {
        //register new patient
    	return "insert into PATIENT(patientAMKA, usrname, password, name, surname, gender) values (?, ?, ?, ?, ?, ?);";
    }
    
    	
    
    public String SearchAppointmentDoc()
    {
    	//Search to find days and time for appointments for a specific doctor.
   		return "select APPOINTMENTS.t, DOCTOR.surname, DOCTOR.name, DEPARTMENTS.name"+
   			   "from (APPOINTMENTS natural join DOCTOR) inner join DEPARTMENTS on DOCTOR.specialty = DEPARTMENTS.id"+
   			   "where DOCTOR.name = and APPOINTMENTS.t::date = ?::date; and APPOINTMENTS.patientAMKA is null and APPOINTMENTS.status is null";
        //Search file Appointments for DoctorName
    }
    
    
    
    public String SearchAppointmentSpc()
    {
    	//Search to find days and time for appointments for a specific specialization.        
        return  "select APPOINTMENTS.t, DOCTOR.surname, DOCTOR.name, DEPARTMENTS.name"+
        		"from (APPOINTMENTS natural join DOCTOR) inner join DEPARTMENTS on DOCTOR.specialty = DEPARTMENTS.id"+
        		"where DOCTOR.specialty = ? and APPOINTMENTS.t::date = ?::date; and APPOINTMENTS.patientAMKA is null and APPOINTMENTS.status is null";
    }
    
    
    
    public String ViewScheduledAppointments()
    {
    	//View schedule of appointments with doctors.
    	return "SELECT A.t as date, 'Dr. ' || DOCTOR.surname as doc_name,  DPT.name as dpt_name, DOCTOR.doctorAMKA as AMKA\r\n" + 
		"FROM (APPOINTMENTS as A natural join DOCTOR) inner join DEPARTMENTS as DPT on DOCTOR.specialty = DPT.id\r\n" + 
		"WHERE A.patientAMKA = ? and A.status = false;";
    }
    
    public String ViewAppointmentHistory()
    {
    	//View history of appointments.        
        return "SELECT A.t as date, 'Dr. ' || DOCTOR.surname as doc_name,  DPT.name as dpt_name, DOCTOR.doctorAMKA as AMKA\r\n" + 
        		"FROM (APPOINTMENTS as A natural join DOCTOR) inner join DEPARTMENTS as DPT on DOCTOR.specialty = DPT.id\r\n" + 
        		"WHERE A.patientAMKA = ? and A.status = true;";
    }
    
    public static String AuthenticatePatient()
    {
    	return "SELECT * FROM PATIENT WHERE usrname = ?;";
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
