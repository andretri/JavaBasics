package UserClass;

public class Admin extends Users
{
	public static int adminCounter = 0;
	private int id = 0;
	public Admin()
	{
		super("", "", "", "");
		++adminCounter;
	}
    public Admin(String usrname, String usrpass, String nameTmp, String surnameTmp, int id)
    {
        super(usrname, usrpass, nameTmp, surnameTmp);
        this.SetID(id);
        ++adminCounter;
    }
    

    public static String AuthenticateAdmin(boolean flag)
    {
    	if(flag == false)
    		return "SELECT * FROM ADMINS WHERE usrname = ? AND password = crypt(?, 3580991459535060::text);";
    	else	
    		return "SELECT * FROM ADMINS WHERE usrname = ?;";
    }
    
    public String RegisterDoctor()
    {
    	//Register a new doctor.
    	return "INSERT INTO DOCTOR(doctorAMKA, usrname, password, name, surname, specialty, admin_id)"
        		+ "VALUES (?, ?, crypt(?, ?::text), ?, ?, ?, ?)";

    }
    
    
    
    public static String RegisterAdmin()
    {
    	//Register a new patient.
    	
    	return "INSERT INTO ADMINS (usrname, password, name, surname)"
				+ "VALUES (?, crypt(?, 3580991459535060::text), ?, ?)";
    }
    
    
    
    public String BanDoctor(boolean flag)
    {
    	//Delete doctor.
    	if(flag == true)
    		return "UPDATE DOCTOR SET password = md5(random()::VARCHAR(20)), banned = true where doctorAMKA = ?;";
    	else
    		return "SELECT DOCTOR.doctorAMKA, DOCTOR.usrname, DOCTOR.name, DOCTOR.surname, DEPARTMENTS.name \r\n" + 
    				"FROM DOCTOR INNER JOIN DEPARTMENTS ON DOCTOR.specialty = DEPARTMENTS.id WHERE DOCTOR.banned = false;";
    }
    
    
    
    public String BanPatient(boolean flag)
    {
    	//Delete patient.
    	if(flag == true)
    		return "UPDATE PATIENT SET password = md5(random()::VARCHAR(20)), banned = true where patientAMKA = ?;";
    	else
    		return "SELECT patientAMKA, usrname, name, surname, gender FROM PATIENT WHERE banned = false;";
    }
    
    //Get SSN
    public void SetID(int tmpId) throws NumberFormatException
    {
        if (this.id == 0)
        	id = tmpId;
        else 
        	throw new NumberFormatException();
    }
    public int GetID()         
    {
        return this.id;
    }
}
