package mainpackage;

public class Patient extends Users
{
    private final String SSN;
    
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
        //Search to find days and time for appointments for a specific doctor.
    }
    
    public void SearchAppointmentSpc(String doctorSpecialty)
    {
        //Search to find days and time for appointments for a specific specialization.
    }
    
    public void ViewScheduledAppointments()
    {
        //View shedule of appointments with doctors.
    }
    
    public void ViewAppointmentHistory()
    {
        //View history of appointments.
    }
    
    //Get SSN
     public String GetSSN()         
    {
        return SSN;
    }
}
