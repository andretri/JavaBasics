package UserServlet;

import java.io.IOException;
import java.io.PrintWriter;
//import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import UserClass.Admin;
import UserClass.Doctor;
import UserClass.Patient;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private DataSource ds;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() 
    {
        super();
    }
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException 
	{
		try 
		{
			InitialContext ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/postgres");
		} 
		catch(Exception e) 
		{
			throw new ServletException(e.toString());
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//PrintWriter out = response.getWriter();
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");	
			
			response.setHeader("Cache-Control", "no-cache");
			//Forces caches to obtain a new copy of the page from the origin server
			response.setHeader("Cache-Control", "no-store");
			//Directs caches not to store the page under any circumstance
			response.setDateHeader("Expires", 0);
			//Causes the proxy cache to see the page as "stale"
			response.setHeader("Pragma", "no-cache"); //HTTP 1.0
			
			
			String requestType= request.getParameter("requestType");
			if (requestType.equals("signin"))
			{
				HttpSession tmp = request.getSession(false);
				if (tmp != null)
				{
						 if (tmp.getAttribute("usrType").equals("P"))
						response.sendRedirect(request.getContextPath() +"/patient?status=signin");
					else if (tmp.getAttribute("usrType").equals("D"))
						response.sendRedirect(request.getContextPath() +"/doctor?status=signin");
					else if (tmp.getAttribute("usrType").equals("A"))
						response.sendRedirect(request.getContextPath() +"/admin?status=signin");
				}
				else
				{
					boolean findusr;
					
					findusr = AuthUser(request, response, "P");			//Authenticate Patient
					if (findusr == false)
					{
						findusr = AuthUser(request, response, "D");		//Authenticate Doctor
						if(findusr == false)
						{
							findusr = AuthUser(request, response, "A"); //Authenticate Admin
							if(findusr == false)
							{
								//response.sendRedirect(request.getContextPath() + "/login.html");
								response.sendRedirect(request.getContextPath());
							}
						}
					}
				}
			}
			else if (requestType.equals("Register"))
			{
				RegisterPatient(request, response);
			}
	}
	
	private boolean AuthUser(HttpServletRequest request, HttpServletResponse response, String usrType) throws ServletException, IOException
	{
		boolean usr = true;
		try
		{
			String uname = request.getParameter("usrname"),	upass = request.getParameter("passwrd"), authName = null, authPass = null;
			String Query = null;

			Connection con = ds.getConnection();
			
			if(usrType.equals("P"))
				Query = Patient.AuthenticatePatient();
			else if(usrType.equals("D"))
				Query = Doctor.AuthenticateDoctor();
			else if(usrType.equals("A"))
				Query = Admin.AuthenticateAdmin();
			
			PreparedStatement loginUser = con.prepareStatement(Query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			loginUser.setString(1, uname);
			
			ResultSet rs = loginUser.executeQuery();
			
			while(rs.next()) 
			{ 
				authName = rs.getString("usrname"); 
				authPass = rs.getString("password"); 
			} 
			rs.close();	loginUser.close(); con.close();
			
			try
			{
				if (authName.equals(uname) && authPass.equals(upass))
				{
					HttpSession usrSession = request.getSession();
					usrSession.setAttribute("usrname", uname);
					usrSession.setAttribute("usrType", usrType);
					
					if(usrType.equals("P"))
						response.sendRedirect(request.getContextPath() +"/patient?status=signin");
					else if(usrType.equals("D"))
						response.sendRedirect(request.getContextPath() +"/doctor?status=signin");
					else
						response.sendRedirect(request.getContextPath() +"/admin?status=signin");
				}	
			}
			catch (NullPointerException nlptr)
			{
				usr = false;
			}
		}
		catch(SQLException sqle)
		{
			//response.sendRedirect(request.getContextPath());
			sqle.printStackTrace();
		}
		
		return usr;
	}
	
	private void RegisterPatient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		HttpSession usrSession = request.getSession();
		
		Long AMKA = Long.parseLong(request.getParameter("amka")); String name = request.getParameter("name");
		String usrname = request.getParameter("usrname"); 		  String surname = request.getParameter("surname");
		String passwrd = request.getParameter("passwrd"); 	  		  String gender = request.getParameter("gender");
		
		try 
		{
			Connection con = ds.getConnection();
			Patient tmpPatient = new Patient(usrname, passwrd, name, surname, AMKA, gender);
//================REGISTER PATIENT'S DATA -- START==================
		    PreparedStatement registerPatient = con.prepareStatement(tmpPatient.RegisterPatient(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		    registerPatient.setLong(1, AMKA);		registerPatient.setString(4, name);
		    registerPatient.setString(2, usrname); 	registerPatient.setString(5, surname);
		    registerPatient.setString(3, passwrd);	registerPatient.setString(6, gender);

		    registerPatient.executeUpdate();
		    String tmp = "Patient: "+surname+", "+name+" ("+AMKA+") \n Registered Succesfully";
		    //=========================HTML CODE START==========================	
		    out.println("<html><head>");
		    out.println("<title>Patient Registration</title>");
		    out.println("<script type=\"text/javascript\">alert("+tmp+");</script>");
		    out.println("</head><body></body></html>");
		    //=========================HTML CODE FINISH=========================		    
		    registerPatient.close(); con.close();
//================REGISTER PATIENT'S DATA -- FINISH=================
		    //response.sendRedirect(request.getContextPath() +"/patient?status=signin");
		    response.sendRedirect(request.getContextPath());
		} 
		catch(SQLException sqle) 
		{
			sqle.printStackTrace();
		}
		finally
		{
			usrSession.setAttribute("usrname", usrname);
			usrSession.setAttribute("usrType", "P");
		}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
}
