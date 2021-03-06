package UserClass;

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
        this.SetUsername(usrname);
        this.SetPassword(usrpass);
        this.SetName(nameTmp);
        this.SetSurname(surnameTmp);
   }   
   
   /* Attribute: username Set-Get */
   public void SetUsername(String str)
   {
        this.username = str;
   }
   public String GetUsername()
   {
       	return this.username;
   }
   
   
   
   /* Attribute: password Set */
   public void SetPassword(String str)
   {
       	this.password = str;
   }
   
   
   
   /* Attribute: name Set-Get */
   public void SetName(String str)
   {
       	this.name = str;
   }    
   public String GetName()    
   {
        return this.name;
   }
   
   
   
   /* Attribute: surname Set-Get */
   public void SetSurname(String str)         
   {
       	this.surname = str;
   }
   public String GetSurname()
   {
       	return this.surname;
   }
 
   public int GetUsersCounter()
   {
	   return Users.usersCounter;
   }
}