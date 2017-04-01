package mainpackage;

public class Users 
{
    public static int usersCounter = 0;
    
    private String username;
    	@SuppressWarnings("unused")
	private String password;
    private String name, surname;
    
    public Users(String usrname, String usrpass, String nameTmp, String surnameTmp)
    {
        ++usersCounter;
        
        username = usrname;
        password = usrpass;
        name = nameTmp;
        surname = surnameTmp;
   }
    
    
   public void login()
   {
        System.out.println("Welcome, "+ this.GetUsername());
   }
   public void logout()
   {
        System.out.println("Goodbye, "+ this.GetUsername());
   }
   
   
   
   /* Attribute: username Set-Get */
   public void SetUsername(String str)
   {
        username = str;
   }
   public String GetUsername()
   {
       	return this.username;
   }
   
   
   
   /* Attribute: password Set */
   public void SetPassword(String str)
   {
       	password = str;
   }
   
   
   
   /* Attribute: name Set-Get */
   public void SetName(String str)
   {
       	name = str;
   }    
   public String GetName()    
   {
        return this.name;
   }
   
   
   
   /* Attribute: surname Set-Get */
   public void SetSurname(String str)         
   {
       	surname = str;
   }
   public String GetSurname()
   {
       	return this.surname;
   }
   
   
   
}