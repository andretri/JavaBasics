package mainpackage;

public class Patient extends Users
{
    private final String SSN;
        
    public Patient(String usrname, String usrpass, String nameTmp, String surnameTmp, String SSNTmp)
    {
        super(usrname, usrpass, nameTmp, surnameTmp);
        SSN = SSNTmp;
    }
    
    public void register(Patient patnt)
    {
        //register new patient
    	System.out.println("User: "+ patnt.GetUsername() +"Succesfully Registered ");
    }
    
    	
    
    public void SearchAppointmentDoc(String doctorName)
    {
    	//Search to find days and time for appointments for a specific doctor.
        System.out.println("Results for Dr " + doctorName + " are:");
        
        System.out.println("Search to find days and time for appointments for a specific doctor."
        		+ "\nA Feature WIP.\n"
        		+ "It will be Complete once we Connect "
        		+ "Java to a Database, which will hold "
        		+ "all the *available* appointments for a CERTAIN (doctorName) *DOCTOR*");
        //Search file Appointments for DoctorName
    }
    
    
    
    public void SearchAppointmentSpc(String doctorSpecialty)
    {
    	//Search to find days and time for appointments for a specific specialization.
        System.out.println("Results for the specialization " + doctorSpecialty + " are:");
        
        System.out.println("Search to find days and time for appointments for a specific specialization."
        		+ "\nA Feature WIP.\n"
        		+ "It will be Complete once we Connect "
        		+ "Java to a Database, which will hold "
        		+ "all the *available* appointments for a CERTAIN (doctorName) *SPECIALITY*");
    }
    
    
    
    public void ViewScheduledAppointments()
    {
    	//View schedule of appointments with doctors.
        System.out.println("Your schedule is :");
        
        System.out.println("View schedule of appointments with doctors."
        		+ "\nA Feature WIP.\n"
        		+ "It will be Complete once we Connect "
        		+ "Java to a Database, which will hold "
        		+ "all the *scheduled* appointments for a CERTAIN (this.userName) PATIENT");

    }
    
    
    
    public void ViewAppointmentHistory()
    {
    	//View history of appointments.
        System.out.println("Your appointment history is :");
        
        System.out.println("View history of appointments."
        		+ "\nA Feature WIP.\n"
        		+ "It will be Complete once we Connect "
        		+ "Java to a Database, which will hold "
        		+ "all the *past* appointments for a CERTAIN (this.userName) PATIENT");
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
     
}
