package mainpackage;

import java.util.Scanner;

public class CreateUsers
{

    public static void main(String[] args)
    {
        //Step 4 - Creation of Objects Pre-Runtime - Test of methods.
        Admin nimda = new Admin("TheMachine", "WelcometotheMachine", "Machine", "The");
        Patient pat = new Patient("johnDoe", "letmein", "John", "Doe", "1111111111"); 
        Doctor doc = new Doctor("DrStrange", "123456789", "Stephen", "Strange", "2222222222", "Neurosurgeon");
        Appointment app = new Appointment(pat, doc);
            
        //Patient Methods
        System.out.println("Below are methods of the class Patient:\n");
        pat.login();
        pat.PrintInfo();
        pat.SearchAppointmentDoc(doc.GetName());
        pat.SearchAppointmentSpc(doc.GetSpec());
        pat.ViewAppointmentHistory();
        pat.ViewScheduledAppointments();
        pat.logout();
	
        //Doctor Methods
        System.out.println("Below are methods of the class Doctor:\n");
        doc.login();
        doc.PrintInfo();
        doc.DocAvailability();
        doc.ViewAppointmentHistory();
        doc.ViewScheduledAppointments();
        doc.logout();
        
        //Admin Methods
        System.out.println("Below are methods of the class Admin:\n");
        nimda.login();
        nimda.RegisterDoctor("Bob");
        nimda.RegisterPatient("Mike");
        nimda.DeleteDoctor("Jack");
        nimda.DeletePatient("Alfred");
        nimda.ViewAppointmentHistory("Batman");
        nimda.ViewSheduledAppointments("DoctorWho");
        nimda.logout();
        
        //Appointment Methods
        System.out.println("Below are methods of the class Appointment:\n");
        app.CreateAppointment();
        app.CancelAppointment();
        
        //Step 5 - Creation of Objects on Runtime.
        Patient pat2 = new Patient();
        Scanner scan = new Scanner(System.in);
        System.out.println("\nInput Patient Username:");
        pat2.SetUsername(scan.next());
        pat2.SetPassword("1234");
        System.out.println("\nInput Patient Name:");
        pat2.SetName(scan.next());
        System.out.println("\nInput Patient Surname:");
        pat2.SetSurname(scan.next());
        System.out.println("\nInput Patient SSN:");
        pat2.SetSSN(scan.next());
        pat2.PrintInfo();
		
		
    }	

}
