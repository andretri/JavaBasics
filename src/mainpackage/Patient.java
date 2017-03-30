package mainpackage;

public class Patient extends Users
{
    private String SSN;
    
    public Patient()
    {
        super("","","","");
        SSN = "";
    }
    
    public Patient(String usrname, String usrpass, String nameTmp, String surnameTmp, String SSNTmp)
    {
        super(usrname, usrpass, nameTmp, surnameTmp);
        SSN = SSNTmp;
    }
    
    public void register()
    {
        //register new patient
    	System.out.println("User: "+"Succesfully Registered ");
    }
    
    
    
    public void SearchAppointmentDoc(String doctorName)
    {
        System.out.println("Results for Dr " + doctorName + " are:___");
        //Search to find days and time for appointments for a specific doctor.
    }
    
    
    
    public void SearchAppointmentSpc(String doctorSpecialty)
    {
        System.out.println("Results for the specialization " + doctorSpecialty + " are:___");
        //Search to find days and time for appointments for a specific specialization.
    }
    
    
    
    public void ViewScheduledAppointments()
    {
        System.out.println("Your schedule is :___");
        //View schedule of appointments with doctors.
    }
    
    
    
    public void ViewAppointmentHistory()
    {
        System.out.println("Your appointment history is :___");
        //View history of appointments.
    }
    
    public void PrintInfo()
    {
        System.out.println("\nUser info: ");
        System.out.println("Username: " + GetUsername());
        System.out.println("Name: " + GetName());
        System.out.println("Surname: " + GetSurname());
        System.out.println("SSN: " + GetSSN() + "\n");
    }
    
    
    
    //Get SSN
     public String GetSSN()         
    {
        return SSN;
    }
     
    //Set SSN
     public void SetSSN(String ssn)
     {
         SSN = ssn;
     }


}
