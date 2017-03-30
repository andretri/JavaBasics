package mainpackage;

public class Users 
{
    public static int usersCounter = 0;
    
    private String username;
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
    
    /* Attribute: username Setters-Getters */
    public void SetUsername(String str)
    {
        username = str;
    }
    public String GetUsername()
    {
        return this.username;
    }
    /* Attribute: password Setters */
    public void SetPassword(String str)
    {
        password = str;
    }
    /* Attribute: name Setters-Getters */
    public void SetName(String str)
    {
        name = str;
    }    
    public String GetName()    
    {
        return this.name;
    }
    /* Attribute: surname Setters-Getters */
    public void SetSurname(String str)         
    {
        surname = str;
    }
    public String GetSurname()
    {
        return this.surname;
    }
}