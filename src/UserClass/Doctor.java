package UserClass;


public class Doctor extends Users 
{
    private Long SSN = null;
    private String spec;
    public static int doctorCounter = 0;
    
    public Doctor()
    {
    	super("", "", "", "");
        ++doctorCounter;
    	this.SetSpec(null);
    }
    public Doctor(String usrname, String usrpass, String nameTmp, String surnameTmp, Long SSNTmp, String spc)
    {
        super(usrname, usrpass, nameTmp, surnameTmp);
        ++doctorCounter;
    	this.SetSSN(SSNTmp);
    	this.SetSpec(spc);
    }
    
    public String ViewAvailableHrs()
    {
    	return "SELECT id, t FROM APPOINTMENTS WHERE patientAMKA is null AND doctorAMKA = ? AND status = false;";
    }
    
    public String EditDocAvailability(boolean add)
    {
    	//Ability to change days and hours at which appointments by patients can be made.
        if (add == true)
        {
        	return "INSERT INTO APPOINTMENTS (t, patientAMKA, doctorAMKA, status) VALUES (?, null, ?, false);";
        }
        else
        {
        	return "DELETE FROM APPOINTMENTS WHERE (id = ?);";
        }
    }
    
    public static String CommitAppointment()
    {
    	return "UPDATE APPOINTMENTS SET status = true WHERE id = ?;";
    }
    
    public String ViewScheduledAppointments()
    {
    	//View schedule of appointments with doctors.        
        return "SELECT id, A.t as date, PATIENT.surname, PATIENT.name, PATIENT.patientAMKA\r\n" + 
		"FROM (APPOINTMENTS as A natural join PATIENT)\r\n" + 
		"WHERE A.doctorAMKA = ? and A.status = false;";
    }
    
    public String ViewAppointmentHistory()
    {
    	//View history of appointments.        
        return "SELECT id, A.t as date, PATIENT.surname, PATIENT.name, PATIENT.patientAMKA\r\n" + 
        		"FROM (APPOINTMENTS as A natural join PATIENT)\r\n" + 
        		"WHERE A.doctorAMKA = ? and A.status = true;";
    }
    
    public static String AuthenticateDoctor(boolean flag)
    {
    	if (flag == false)
    		return "SELECT DOCTOR.doctorAMKA, DOCTOR.usrname, DOCTOR.password, DOCTOR.name, DOCTOR.surname, DEPARTMENTS.name as Specialty, ADMINS.usrname as Registered_By "+
    		   "FROM (DOCTOR INNER JOIN DEPARTMENTS on DOCTOR.specialty = DEPARTMENTS.id) INNER JOIN ADMINS ON DOCTOR.admin_id = ADMINS.id "+
    		   "WHERE DOCTOR.usrname = ? AND DOCTOR.password = crypt(?, DOCTOR.doctorAMKA::text)";
    	else
    		return "SELECT DOCTOR.doctorAMKA, DOCTOR.usrname, DOCTOR.password, DOCTOR.name, DOCTOR.surname, DEPARTMENTS.name as Specialty, ADMINS.usrname as Registered_By "+
 		   		"FROM (DOCTOR INNER JOIN DEPARTMENTS on DOCTOR.specialty = DEPARTMENTS.id) INNER JOIN ADMINS ON DOCTOR.admin_id = ADMINS.id "+
 		   		"WHERE DOCTOR.usrname = ?";
    }
    
    public String RegisterDoctor()
    {
        //register new patient
        return "insert into DOCTOR(doctorAMKA, usrname, password, name, surname, specialty, admin_id) values (?, ?, ?, ?, ?, ?, ?);";
    }
    
    
    //Get-Set doctor's specification
    public void SetSpec(String spc)
    {
    	spec = spc;
    }
    public String GetSpec()
    {
        return spec;
    }
    
    //Get-Set doctor's Social Security Number
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
}
