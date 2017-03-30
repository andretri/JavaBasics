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
    
    
    
    public void DocAvailability()
    {
        System.out.println("Please input below the days and hours for available appointments:___");
        //Ability to change days and hours at which appointments by patients can be made.
    }
    
    
    
    public void ViewScheduledAppointments()
    {
        System.out.println("Your schedule is :___");
        //View schedule of appointments with patients.
    }
    
    
    
    public void ViewAppointmentHistory()
    {
        System.out.println("Your appointment history is :___");
        //View history of appointments.
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
    
    
    //Get-Set Spec
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
