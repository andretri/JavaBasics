package mainpackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CreateUsers
{

    @SuppressWarnings("unused")
	public static void main(String[] args)
    {
        //Step 4 - Creation of Objects Pre-Runtime - Test of methods.
        Admin nimda = new Admin("TheMachine", "WelcometotheMachine", "Machine", "The");
        Patient patA = new Patient("johnDoe", "letmein", "John", "Doe", "1111111111");
        Patient patB = new Patient("janeDoe", "letmein", "Jane", "Doe", "2222222222");
        Doctor docA = new Doctor("DrStrange", "123456789", "Stephen", "Strange", "2222222222", "Neurosurgeon");
        Doctor docB = new Doctor("JohnApp", "1234", "Appleseed", "John", "4321876509", "Pharmaceuticals");
        Doctor docC = new Doctor("DocWho", "T@rd!$", "Smith", "John", "0000000000", "Time Travel");
        
        
        //Patient Methods
        System.out.println("Below are methods of the class Patient:\n");
        patA.login();
        patA.PrintInfo();
        patA.SearchAppointmentDoc(docA.GetName());
        patA.SearchAppointmentSpc(docB.GetSpec());
        patA.ViewAppointmentHistory();
        patA.ViewScheduledAppointments();
        patA.logout();
	
        //Doctor Methods
        System.out.println("Below are methods of the class Doctor:\n");
        docC.login();
        docC.PrintInfo();
        docC.EditDocAvailability();
        docC.ViewAppointmentHistory();
        docC.ViewScheduledAppointments();
        docC.logout();
        
        //Administrator Methods
        System.out.println("Below are methods of the class Admin:\n");
        nimda.login();
        nimda.RegisterDoctor(docA);
        nimda.RegisterDoctor(docB);
        nimda.RegisterDoctor(docC);
        
        nimda.RegisterPatient(patA);
        nimda.RegisterPatient(patB);
        
        nimda.DeleteDoctor(docB);
        
        nimda.DeletePatient(patA);
       
        nimda.ViewAppointmentHistory(docC);
        nimda.ViewAppointmentHistory(patB);

        nimda.logout();
        
        
        //Appointment Methods
        Appointment app = new Appointment(patA, docA);
        
        
        System.out.println("Below are methods of the class Appointment:\n");
        app.CreateAppointment();
        app.CancelAppointment();
         
        //Step 5 - Creation of Objects on Runtime.
        String[] objArgs = new String[5]; 
        Scanner scan = new Scanner(System.in);
        
        System.out.print("\nInput Patient Username:");
        objArgs[0] = scan.nextLine();
        System.out.print("\nInput Patient Password:");
        objArgs[1] = scan.nextLine();
        System.out.print("\nInput Patient Name:");
        objArgs[2] = scan.nextLine();
        System.out.print("\nInput Patient Surname:");
        objArgs[3] = scan.nextLine();;
        System.out.println("\nInput Patient SSN:");
        objArgs[4] = scan.nextLine();
		
        Patient pat2 = new Patient(objArgs[0], objArgs[1], objArgs[2], objArgs[3], objArgs[4]);
        pat2.PrintInfo();
        scan.close();
  
        
        //Step 6 - Creation of Objects based on a Given File
        ArrayList<Patient> Patients = new ArrayList<Patient>(); 
        
        try 
        {
			BufferedReader br = new BufferedReader(new FileReader("src/test/Patients.txt"));	
				br.readLine();
			
			String patientLine;			
			int index = 0;
			
			while ( (patientLine = br.readLine()) != null )
			{
				objArgs = patientLine.split("\t");
				Patient tmp = new Patient(objArgs[0], objArgs[1], objArgs[2], objArgs[3], objArgs[4]);
				Patients.add(tmp);
			}
			
			br.close();
        } 
        catch (FileNotFoundException e) 
        {
        	System.out.println("File not found!");
			e.printStackTrace();
		}
        catch (IOException e)
        {
        	System.out.println("Corrupted File!");
        }
        
        for (Patient ptnt:Patients)
        {
        	ptnt.PrintInfo();
        }
        
    }	

}
