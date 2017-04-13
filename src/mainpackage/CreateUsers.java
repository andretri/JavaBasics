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
    	CreateUsers ValidateInput = new CreateUsers();
    	
        //Step 4 - Creation of Objects Pre-Runtime - Test of methods.
    	Admin nimda = new Admin("TheMachine", "WelcometotheMachine", "Machine", "The");
        Patient patA = new Patient("johnDoe", "letmein", "John", "Doe", "1111111111");
        Patient patB = new Patient("BenedictC", "123456789", "Benedict", "Tennismatch", "2222222221");
        Doctor docA = new Doctor("DrStrange", "123456789", "Stephen", "Strange", "2222222222", "Neurosurgeon");
        Doctor docB = new Doctor("JohnApp", "1234", "Appleseed", "John", "4321876509", "Pharmaceuticals");
        Doctor docC = new Doctor("DocWho", "T@rd!$", "Smith", "John", "0000000000", "Dermatologist");
        

        //Patient Methods
        System.out.println("Below are methods of the class Patient:\n");
        patA.Login();
        patA.PrintInfo();
        patA.SearchAppointmentDoc(docA, "14-4-2017");
        patA.SearchAppointmentSpc(docB.GetSpec(), "20-4-2017");
        patA.ViewAppointmentHistory();
        patA.ViewScheduledAppointments();
        patA.Logout();
        
        

        //Doctor Methods
        System.out.println("Below are methods of the class Doctor:\n");
        docC.Login();
        docC.PrintInfo();
        docC.EditDocAvailability();
        docC.ViewAppointmentHistory();
        docC.ViewScheduledAppointments();
        docC.Logout();

        
        //Administrator Methods
        System.out.println("Below are methods of the class Admin:\n");
        nimda.Login();
        nimda.RegisterDoctor(docA);
        nimda.RegisterDoctor(docB);
        nimda.RegisterDoctor(docC);
        
        nimda.RegisterPatient(patA);
        nimda.RegisterPatient(patB);
        
        nimda.DeleteDoctor(docB);
        
        nimda.DeletePatient(patA);
       
        nimda.ViewAppointmentHistory(docC);
        nimda.ViewAppointmentHistory(patB);

        nimda.Logout();
        
        
        //Appointment Methods
        Appointment app = new Appointment(patA, docA);
        
        
        System.out.println("Below are methods of the class Appointment:\n");
        app.CreateAppointment();
        app.CancelAppointment();
         
        //Step 5 - Creation of Objects on Runtime.
        String tmp;
        Scanner scn = new Scanner(System.in);
        	//create a PATIENT Object
        Patient tmpPat = new Patient();       
        System.out.print("\nInput Patient Username:");
        tmp = ValidateInput.ScanNextLine(scn);
        tmpPat.SetUsername(tmp);
        tmp = ValidateInput.ValidatePasswrd(scn);
        tmpPat.SetPassword(tmp);
        System.out.print("\nInput Patient Name:");
        tmp = ValidateInput.ValidateStr(scn);
        tmpPat.SetName(tmp);
        System.out.print("\nInput Patient Surname:");
        tmp = ValidateInput.ValidateStr(scn);
        tmpPat.SetSurname(tmp);
        System.out.println("\nInput Patient SSN:");
        tmp = ValidateInput.ValidateNum(scn);
        tmpPat.SetSSN(tmp);
        		//Display Object's Info
        tmpPat.PrintInfo();
 
        
        //create a DOCTOR Object
        Doctor tmpDoc = new Doctor();
        System.out.print("\nInput Doctor Username:");
        tmp = ValidateInput.ScanNextLine(scn);
        tmpDoc.SetUsername(tmp);
        tmp = ValidateInput.ValidatePasswrd(scn);
        tmpDoc.SetPassword(tmp);
        System.out.print("\nInput Doctor Name:");
        tmp = ValidateInput.ValidateStr(scn);
        tmpDoc.SetName(tmp);
        System.out.print("\nInput Doctor Surname:");
        tmp = ValidateInput.ValidateStr(scn);
        tmpDoc.SetSurname(tmp);
        System.out.println("\nInput Doctor SSN:");
        tmp = ValidateInput.ValidateNum(scn);
        tmpDoc.SetSSN(tmp);
        System.out.println("\nInput Doctor Specialization:");
        tmp = ValidateInput.ValidateStr(scn);
        tmpDoc.SetSpec(tmp);
        	//Display Object's Info
        tmpDoc.PrintInfo();
        
        
        //create a ADMIN Object
        Admin tmpAdmn = new Admin();
        System.out.print("\nInput Admin Username:");
        tmp = ValidateInput.ScanNextLine(scn);
        tmpAdmn.SetUsername(tmp);
        tmp = ValidateInput.ValidatePasswrd(scn);
        tmpAdmn.SetPassword(tmp);
        System.out.print("\nInput Admin Name:");
        tmp = ValidateInput.ValidateStr(scn);
        tmpAdmn.SetName(tmp);
        System.out.print("\nInput Admin Surname:");
        tmp = ValidateInput.ValidateStr(scn);
        tmpAdmn.SetSurname(tmp);
        	//Display Object's Info
        tmpAdmn.PrintInfo();
        
        scn.close();
        
        
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
				String[] objArgs = patientLine.split("\t");
				Patient filePat = new Patient(objArgs[0], objArgs[1], objArgs[2], objArgs[3], objArgs[4]);
				Patients.add(filePat);
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


    public String ScanNextLine(Scanner scan)
    {
    	String tmp = scan.nextLine();
    	
    	while (!tmp.matches("[A-Za-z0-9]+")) 
    	{
            System.out.println("Wrong Input");
            tmp = scan.nextLine();
        }
    	
    	return tmp;
    }


    public String ValidateStr(Scanner scan)
    {
    	String tmp = scan.nextLine();
    	while (!tmp.matches("[A-Za-z ]+")) 
    	{
            System.out.println("Wrong Input");
            tmp = scan.nextLine();
        }
    	return tmp;
    }


    public String ValidateNum(Scanner scan)
    {
    	String tmp = scan.nextLine();
    	while (!(tmp.matches("[0-9]{10}") && tmp.length() == 10)) 
    	{
            System.out.println("Invalid SSN");
            tmp = scan.nextLine();
        }
    	return tmp;
    }
    
    public String ValidatePasswrd(Scanner scan)
    {
    	String tmp, tmp2;
    	do
        {
	        System.out.print("\nInput Password:");
	        tmp = ValidateStr(scan);
	        System.out.print("\nRe-Input Password:");
	        tmp2 = ValidateStr(scan);
	    	if (!tmp.equals(tmp2))
	    	{
	            System.out.println("Passwords Do not Match! Repeat Procedure");
	        }
        }
        while(!tmp.equals(tmp2));
    	return tmp;
    }
        
}
