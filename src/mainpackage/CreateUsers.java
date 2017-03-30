package mainpackage;

public class CreateUsers 
{

	public static void main(String[] args)
	{
		Admin nimda = new Admin("TheMachine", "WelcometotheMachine", "Machine", "The");
		Patient ptnt = new Patient("johnDoe", "letmein", "John", "Doe", "1111111111"); 
		Doctor dctr = new Doctor("DrStrange", "123456789", "Stephen", "Strange", "2222222222", "Neurosurgeon");
		Appointment appntmnt = new Appointment(ptnt, dctr);
		
		
		
	}	

}
