package mainpackage;

public class Appointment
{
    Patient obj1 = null;
    Doctor obj2 = null;
    
    
    public Appointment(Patient x, Doctor y)
    {
    	obj1 = x;
    	obj2 = y;
    }
    
    
    
    public void CreateAppointment()
    {
    	System.out.println("Appointment created successfully!");
    }
    
    
    
    public void CancelAppointment()
    {
    	System.out.println("Appointment cancelled successfully.");
    }
    
    
    
}