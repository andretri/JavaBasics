package UserServlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import javax.sql.DataSource;

import UserClass.Patient;

/**
 * Servlet implementation class PatientServlet
 */
@WebServlet("/patient")
public class PatientServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private DataSource ds = null;
	Patient tmpPatient;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PatientServlet() {
        super();
    }
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException 
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String requestType= request.getParameter("requestType");
		if (requestType == null) createMsgPage(request, response, "Sign-in Error" ,"Invalid request type", out);
		
		if (requestType.equalsIgnoreCase("signin")) 
		{
			String usrname = request.getParameter("usrname");
			String passwrd = request.getParameter("passwrd");
			String htmlRow = null;
			try 
			{
				Connection con = ds.getConnection();
//==============================================HTML CODE START==============================================			    
				out.println("<!DOCTYPE html>\r\n" + 
						"<html>");
				out.println("<head><title>Patient Dashboard</title></head>");
				out.println("<body>");
//==============================================HTML CODE FINISH==============================================
				    
//==============================================FETCH PATIENT'S DATA -- START============================================== 
			    PreparedStatement loginPatient = con.prepareStatement(Patient.FetchDBData(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    loginPatient.setString(1, usrname);	loginPatient.setString(2, passwrd);
			    ResultSet rs = loginPatient.executeQuery();
				createFinTable(out, true);
				while(rs.next())
				{	
					tmpPatient = new Patient(rs.getString("userid"), rs.getString("password"), rs.getString("name"), rs.getString("surname"), rs.getLong("patientAMKA"), rs.getString("gender"));
				}
				htmlRow = createTable(rs);
			    out.println(htmlRow);
			    
				createFinTable(out, false);
				rs.close();	loginPatient.close();
//==============================================FETCH PATIENT'S DATA -- FINISH==============================================
				
//==============================================FETCH PATIENT'S APPOINTMENT HISTORY -- START============================================== 
				out.println("<h1>Appointment History</h1>");
				PreparedStatement ViewAppointmentHistory = con.prepareStatement(tmpPatient.ViewAppointmentHistory(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ViewAppointmentHistory.setLong(1, tmpPatient.GetSSN());
				ResultSet appmnt = ViewAppointmentHistory.executeQuery();
				
				createFinTable(out, true);
				htmlRow = createTable(appmnt);			
				out.println(htmlRow);
				createFinTable(out, false);
			    
				appmnt.close();	con.close();
//==============================================FETCH PATIENT'S APPOINTMENT HISTORY -- FINISH==============================================
				
//==============================================HTML CODE START==============================================				
				out.println("</body>");
				out.println("</html>");
//==============================================HTML CODE FINISH==============================================
			} 
			catch(SQLException sqle) 
			{
				sqle.printStackTrace();
			}
		} 
		else if (requestType.equalsIgnoreCase("register")) 
		{
			Long AMKA = Long.parseLong(request.getParameter("amka"));
			String usrname = request.getParameter("userid");
			String passwrd = request.getParameter("passwrd");
			String name = request.getParameter("name");
			String surname = request.getParameter("surname");
			String gender = request.getParameter("gender");
			
			String htmlRow = null;
			try 
			{
				Connection con = ds.getConnection();
				tmpPatient = new Patient(usrname, passwrd, name, surname, AMKA, gender);
//==============================================HTML CODE START==============================================			    
				out.println("<!DOCTYPE html>\r\n" + 
						"<html>");
				out.println("<head><title>Patient Registration</title></head>");
				out.println("<body>");
//==============================================HTML CODE FINISH==============================================
				    
//==============================================REGISTER PATIENT'S DATA -- START============================================== 
			    PreparedStatement registerPatient = con.prepareStatement(tmpPatient.RegisterPatient(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    registerPatient.setLong(1, AMKA);	
			    registerPatient.setString(2, usrname);
			    registerPatient.setString(3, passwrd);
			    registerPatient.setString(4, name);
			    registerPatient.setString(5, surname);
			    registerPatient.setString(6, gender);
			    
			    registerPatient.executeUpdate();
			    
			    createMsgPage(request, response, "Successful Registration", "Patient: "+surname+", "+name+" ("+AMKA+") Registered Succesfully", out);
				
			    registerPatient.close();
//==============================================REGISTER PATIENT'S DATA -- FINISH==============================================
				
//==============================================HTML CODE START==============================================				
				out.println("</body>");
				out.println("</html>");
//==============================================HTML CODE FINISH==============================================
			} 
			catch(SQLException sqle) 
			{
				sqle.printStackTrace();
			}
		}
		else 
		{
			createMsgPage(request, response, "Registration Error" ,"The request type parameter must be insert", out);
		}
	}
	
	public void createFinTable(PrintWriter out, Boolean flag)
	{
		if(flag.equals(true))
			out.println("<table border=\"0\">");
		else
			out.println("</table>");
	}
	private String createTable(ResultSet rs)
	{
		String row = null;
		try 
		{
			int indx = rs.getMetaData().getColumnCount();
			
				row = "<tr>";
			for (int i = 1; i <= indx; i++)
			{
				if (!rs.getMetaData().getColumnName(i).equals("password"))
					row += "<td>" + rs.getMetaData().getColumnName(i) + "</td>";				
			}	
				row += "</tr>";
				  rs.beforeFirst();
			while(rs.next())
			{
				row += "<tr>";
				for (int i = 1; i <= indx; i++)
				{
					if (!rs.getMetaData().getColumnName(i).equals("password"))
						row  += "<td>" + rs.getString(i) + "</td>";				
				}		
				row += "</tr>";
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return row;
	}
	private void createMsgPage(HttpServletRequest request, HttpServletResponse response, String title ,String message, PrintWriter out) throws IOException 
	{
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding(request.getCharacterEncoding());
		out.println("<html>");
		out.println("<head><title>"+ title + "</title></head>");
		out.println("<body>");
		out.println("<p>" + message + "</p>");
		out.println("<a href=\"/javaHospital/index.html\">Return to Homepage</a>");
		out.println("</body></html>");
	}
}