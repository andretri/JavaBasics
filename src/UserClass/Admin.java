package UserClass;

public class Admin extends Users
{
	public static int adminCounter = 0;

	public Admin()
	{
		super("", "", "", "");
		++adminCounter;
	}
    public Admin(String usrname, String usrpass, String nameTmp, String surnameTmp)
    {
        super(usrname, usrpass, nameTmp, surnameTmp);
        ++adminCounter;
    }
    

    public static String AuthenticateAdmin()
    {
    	return "SELECT * FROM ADMINS WHERE usrname = ?";
    }
    
    public void RegisterDoctor()
    {
    	//Register a new doctor.
    	
        //\nA Feature WIP.\n
        //It will be Complete once we Connect 
        //Java to a Database, which will hold (insert into DB) 
        //all the REGISTERED DOCTORS")
    }
    
    
    
    public void RegisterAdmin()
    {
    	//Register a new patient.
    	
        //"\nA Feature WIP.\n"
        //"It will be Complete once we Connect "
        //"Java to a Database, which will hold (insert into DB) "
        //"all the REGISTERED PATIENTS");
    }
    
    
    
    public void BanDoctor()
    {
    	//Delete doctor.
    	
    	//"\nA Feature WIP.\n"
    	//"It will be Complete once we Connect "
    	//"Java to a Database, which the function will modify "
    	//"(delete) a selected *doctor* based on his SSN (delDoc.SSN).");
    }
    
    
    
    public void BanPatient()
    {
    	//Delete patient.

        //"\nA Feature WIP.\n"
        //"It will be Complete once we Connect "
        //"Java to a Database, which the function will modify "
        //"(delete) a selected *patient* based on his SSN (delPat.SSN).");
    }
}
