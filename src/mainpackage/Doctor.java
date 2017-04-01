package mainpackage;


public class Doctor extends Users 
{
    private final String SSN;
    private String spec;
    
    public Doctor(String usrname, String usrpass, String nameTmp, String surnameTmp, String SSNTmp, String spc)
    {
        super(usrname, usrpass, nameTmp, surnameTmp);
        SSN = SSNTmp;
        spec = spc;
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
    public String GetSpec()
    {
        return spec;
    }
    
    public void SetSpec(String spc)
    {
        spec = spc;
    }

    public String GetSSN() 
    {
        return SSN;
    }
}
