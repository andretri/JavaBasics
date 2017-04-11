package mainpackage;

import java.sql.Date;

public class Appointment
{
    Patient obj1 = null;
    Doctor obj2 = null;
    Date appDat = null;
    
    public Appointment(Patient x, Doctor y)
    {
    	obj1 = x;
    	obj2 = y;
    }

    
    public void CheckAvailability(String date)
    {
    	obj1.SearchAppointmentDoc(obj2.GetUsername());
    	/*
    	 * if (Doctor.isAvailable)
    	 * {
    	 * 		CreateAppointment();
    	 * }
    	 * else
    	 * {
    	 * 		System.out.println("The Appointment is already Reserved");
    	 * }
    	 */
    }
    
    
    public void CreateAppointment()
    {
    	System.out.println("Appointment created successfully!");
    }
    
    
    public void CancelAppointment()
    {
    	//Ability to Cancel appointments between a two people.
    	System.out.println("Appointment cancelled successfully!");
    	
        System.out.println("\nAbility to Cancel appointments between a two people."
        		+ "\nA Feature WIP.\n"
        		+ "It will be Complete once we Connect "
        		+ "Java to a Database, which the function will access "
        		+ "all the scheduled appointments of a selected user (either patient or " 
        		+ "doctor) based on his SSN.");
    }
    
    
    
}