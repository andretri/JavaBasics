package mainpackage;

public class Admin extends Users
{
    public Admin(String usrname, String usrpass, String nameTmp, String surnameTmp)
    {
        super(usrname, usrpass, nameTmp, surnameTmp);
    }
    
    
    
    public void RegisterDoctor(Doctor newDoc)
    {
    	//Register a new doctor.
        System.out.println("New doctor registered successfully!");
        System.out.println("Doctor's Data:");
        newDoc.PrintInfo();
        
        System.out.println("\nA Feature WIP.\n"
        		+ "It will be Complete once we Connect "
        		+ "Java to a Database, which will hold (insert into DB) "
        		+ "all the REGISTERED DOCTORS");
    }
    
    
    
    public void RegisterPatient(Patient newPat)
    {
    	//Register a new patient.
        System.out.println("New patient registered successfully!");
        System.out.println("Patient's Data:");
        newPat.PrintInfo();
        
        System.out.println("\nA Feature WIP.\n"
        		+ "It will be Complete once we Connect "
        		+ "Java to a Database, which will hold (insert into DB) "
        		+ "all the REGISTERED PATIENTS");
    }
    
    
    
    public void DeleteDoctor(Doctor delDoc)
    {
    	//Delete doctor.
    	System.out.println("Dr. " + delDoc.GetSurname() +", SSN:" + delDoc.GetSSN() + ") deleted successfully.");
    	
    	System.out.println("\nA Feature WIP.\n"
    			+ "It will be Complete once we Connect "
    			+ "Java to a Database, which the function will modify "
    			+ "(delete) a selected *doctor* based on his SSN (delDoc.SSN).");
    	
    	delDoc = null;
    }
    
    
    
    public void DeletePatient(Patient delPat)
    {
    	//Delete patient.
    	System.out.println("Mr/Mrs. " + delPat.GetSurname() +", SSN:" + delPat.GetSSN() + ") deleted successfully.");
        
        System.out.println("\nA Feature WIP.\n"
        		+ "It will be Complete once we Connect "
        		+ "Java to a Database, which the function will modify "
        		+ "(delete) a selected *patient* based on his SSN (delPat.SSN).");
        
        delPat = null;
    }
    
    
    
    public void ViewScheduledAppointments(Doctor doc)
    {
    	//View schedule of specific patient or doctor.

        System.out.println("Schedule of user " + doc.GetName() + " is:");
        doc.ViewScheduledAppointments();
    }
    
    public void ViewScheduledAppointments(Patient pat)
    {
    	//View schedule of specific patient or doctor.

        System.out.println("Schedule of user " + pat.GetName() + " is:");
        pat.ViewScheduledAppointments();
    }
    
    public void ViewAppointmentHistory(Doctor doc)
    {
    	//View history of specific patient or doctor.
        System.out.println("Appointment history of user " + doc.GetName() + " (" + doc.GetUsername() + ") " + " is:");
        
        System.out.println("\nView appointment history of specific patient or doctor."
        		+ "\nA Feature WIP.\n"
        		+ "It will be Complete once we Connect "
        		+ "Java to a Database, which the function will access "
        		+ "all the *past* appointments of a selected user (either patient or " 
        		+ "doctor) based on his SSN.");
    } 
    
    public void ViewAppointmentHistory(Patient pat)
    {
    	//View history of specific patient or doctor.
        System.out.println("Appointment history of user " + pat.GetName() + " (" + pat.GetUsername() + ") " + " is:");
        
        System.out.println("\nView appointment history of specific patient or doctor."
        		+ "\nA Feature WIP.\n"
        		+ "It will be Complete once we Connect "
        		+ "Java to a Database, which the function will access "
        		+ "all the *past* appointments of a selected user (either patient or " 
        		+ "doctor) based on his SSN.");
    }
}
