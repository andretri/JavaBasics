package mainpackage;

public class Admin extends Users
{
    public Admin(String usrname, String usrpass, String nameTmp, String surnameTmp)
    {
        super(usrname, usrpass, nameTmp, surnameTmp);
    }
    
    
    
    public void RegisterDoctor(String doc)
    {
        System.out.println("New doctor registered successfully!");
        //Register a new doctor.
    }
    
    
    
    public void RegisterPatient(String pat)
    {
        System.out.println("New patient registered successfully!");
        //Register a new patient.
    }
    
    
    
    public void DeletePatient(String pat)
    {
        System.out.println("New patient deleted successfully.");
        //Delete patient.
    }
    
    
    
    public void DeleteDoctor(String doc)
    {
        System.out.println("New patient deleted successfully.");
        //Delete doctor.
    }
    
    
    
    public void ViewSheduledAppointments(String user)
    {
        System.out.println("Schedule of user " + user + " is:___");
        //View schedule of specific patient or doctor.
    }
    
    
    
    public void ViewAppointmentHistory(String user)
    {
        System.out.println("History of user " + user + " is:___");
        //View history of specific patient or doctor.
    }
    
    
    
}
