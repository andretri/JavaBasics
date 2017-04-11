package mainpackage;


public class Doctor extends Users 
{
    private String SSN = null;
    private String spec;
    
    
    public Doctor()
    {
    	super("", "", "", "");
    	this.SetSpec(null);
    }
    public Doctor(String usrname, String usrpass, String nameTmp, String surnameTmp, String SSNTmp, String spc)
    {
        super(usrname, usrpass, nameTmp, surnameTmp);
    	this.SetSSN(SSNTmp);
    	this.SetSpec(spc);
    }
    
    
    
    public void EditDocAvailability()
    {
    	//Ability to change days and hours at which appointments by patients can be made.
        System.out.println("Please input below the days and hours for available appointments:");
        
        System.out.println("Ability to change days and hours at which appointments by patients can be made."
        		+ "\nA Feature WIP.\n"
        		+ "It will be Complete once we Connect"
        		+ "Java to a Database, which will modify"
        		+ "all the *inserted* available appointments for a CERTAIN (this.userName) DOCTOR");
    }
    
    
    
    public void ViewScheduledAppointments()
    {
    	//View schedule of appointments with patients.
        System.out.println("Your schedule is :");
        
        System.out.println("View schedule of appointments with patients."
        		+ "\nA Feature WIP.\n"
        		+ "It will be Complete once we Connect"
        		+ "Java to a Database, which will hold"
        		+ "all the *scheduled* appointments for a CERTAIN (this.userName) DOCTOR");
    }
    
    
    
    public void ViewAppointmentHistory()
    {
    	//View history of appointments.
        System.out.println("Your appointment history is :");

        System.out.println("View history of appointments."
        		+ "\nA Feature WIP.\n"
        		+ "It will be Complete once we Connect"
        		+ "Java to a Database, which will hold"
        		+ "all the *past* appointments for a CERTAIN (this.userName) DOCTOR");
    }
    
    @Override
    public void PrintInfo()
    {
        System.out.println("\nDoctor info: ");
        System.out.println("Username: " + GetUsername());
        System.out.println("Name: " + GetName());
        System.out.println("Surname: " + GetSurname());
        System.out.println("SSN: " + GetSSN());
        System.out.println("Specialization: " + GetSpec() + "\n");
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
    public void SetSSN(String tmpSSN) throws NumberFormatException
    {
        if (this.SSN == null)
        	SSN = tmpSSN;
        else 
        	throw new NumberFormatException();
    }
    public String GetSSN() 
    {
        return SSN;
    }
}
