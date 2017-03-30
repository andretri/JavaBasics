package mainpackage;

public class Admin extends Users
{
    public Admin(String usrname, String usrpass, String nameTmp, String surnameTmp)
    {
        super(usrname, usrpass, nameTmp, surnameTmp);
    }
    
    
    
    public void RegisterDoctor()
    {
        //Register a new doctor.
    }
    
    
    
    public void RegisterPatient()
    {
        //Register a new patient.
    }
    
    
    
    public void DeletePatient()
    {
        //Delete patient.
    }
    
    
    
    public void DeleteDoctor()
    {
        //Delete doctor.
    }
    
    
    
    public void ViewSheduledAppointments()
    {
        //View schedule of specific patient or doctor.
    }
    
    
    
    public void ViewAppointmentHistory()
    {
        //View history of specific patient or doctor.
    }
    
    
    
}
