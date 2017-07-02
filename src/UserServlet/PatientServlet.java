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
import javax.servlet.http.HttpSession;
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
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PatientServlet() 
    {
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
		
		response.setHeader("Cache-Control", "no-cache");
		//Forces caches to obtain a new copy of the page from the origin server
		response.setHeader("Cache-Control", "no-store");
		//Directs caches not to store the page under any circumstance
		response.setDateHeader("Expires", 0);
		//Causes the proxy cache to see the page as "stale"
		response.setHeader("Pragma", "no-cache"); //HTTP 1.0
		//HTTP 1.0 backward enter code here
		
		String requestType= (String)request.getParameter("status");
//======================================================================================================================================================requestType ERROR		
		if (requestType == null) 
		{
			createMsgPage(request, response, "Sign-in Error" ,"Invalid request type", out);
			return;
		}
//============================================================================================================================================requestType PATIENT SIGN IN		
		else if (requestType.equalsIgnoreCase("signin")) 
		{
			HttpSession usrSession = request.getSession(false);
			try 
			{
				synchronized (usrSession)
				{			
					String usrname = (String)usrSession.getAttribute("usrname");
					String htmlRow = null;
					
					Connection con = ds.getConnection();
					Patient tmpPatient = null;
//=========================HTML CODE START==========================			    
					out.println("<!DOCTYPE html>\r\n <html>");
					out.println("<head><title>Patient Dashboard</title></head>");
					out.println("<body>");
//=========================HTML CODE FINISH=========================
				    
//=================FETCH PATIENT'S DATA -- START====================
					PreparedStatement loginPatient = con.prepareStatement(Patient.AuthenticatePatient(true), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					loginPatient.setString(1, usrname);
					
					
					ResultSet rs = loginPatient.executeQuery();
					createFinTable(out, true);
					while(rs.next())
					{	
						tmpPatient = new Patient(rs.getString("usrname"), rs.getString("password"), rs.getString("name"), rs.getString("surname"), rs.getLong("patientAMKA"), rs.getString("gender"));
					}
					usrSession.setAttribute("tmPatient", tmpPatient);
					htmlRow = createTable(rs);
					out.println(htmlRow);
					
					createFinTable(out, false);
					rs.close();	loginPatient.close(); con.close();
//=================FETCH PATIENT'S DATA -- FINISH===================
		
//=========================HTML CODE START==========================				
					out.println("<p> <form action='/javaHospital/patient' method='get'>"
							+ "<button type='submit' name='status' value='aptcrt'>"
							+ "Make An Appointment</button>");
					out.println("<button type='submit' name='status' value='aptpnd'>"
							+ "View Pending Appointments</button>");
					out.println("<button type='submit' name='status' value='apthst'>"
							+ "View Appointment History </button>"
							+ "</form> </p>");
					out.println("<p> <form action='/javaHospital/'>"
							+ "<button type='submit'>"
							+ "Return to Homepage</button>"
							+ "</form> </p>");
					out.println("<p> <form action='/javaHospital/patient' method='get'>"
							+ "<button type='submit' name='status' value='signout'>"
							+ "Sign-Out</button>"
							+ "</form> </p>");
					out.println("</body>");
					out.println("</html>");
//=========================HTML CODE FINISH=========================
				}
			}
			catch(SQLException sqle) 
			{	
				sqle.printStackTrace();
			}
			catch(NullPointerException nlptre)
			{
				response.sendRedirect(request.getContextPath());
			}
		} 
//==================================================================================================================================requestType PATIENT PAST APPOINTMENTS
		else if(requestType.equalsIgnoreCase("aptcrt"))
		{
			try
			{
				String Query = "SELECT * from DEPARTMENTS;";
				Connection con = ds.getConnection();
				
				PreparedStatement SpecialtyList = con.prepareStatement(Query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = SpecialtyList.executeQuery();
				String SpecLst= GetSpecialtyList(rs);
				rs.close();	SpecialtyList.close(); con.close();
				
				out.println("<form action='/javaHospital/patient' method='get'>"
						+ "Search By Dr's Surname:&nbsp;"
						+ "<input type='text' name='docsurname' placeholder='Dr. Surname'/>"
						+ "&nbsp;&nbsp;OR Specialty:&nbsp;" + SpecLst + "&nbsp;&nbsp;<button type='submit' name='status' value='aptcrt'> Search </button>");			
				out.println("<br/><br/>");
				out.println("<form action='/javaHospital/patient' method='get'> "
						+ "<button type='submit' name='status' value='signin'>"
						+ "Return to Dashboard</button>"
						+ "</form>");
				String docsurname = "";
				int docspecialty = 0;
				try
				{
					docsurname = request.getParameter("docsurname");
				}
				catch (NullPointerException nlptre){}
				try
				{
					docspecialty = Integer.parseInt(request.getParameter("docspecialty"));
				}
				catch (NullPointerException nlptre){}
				
				con = ds.getConnection();
				PreparedStatement avlapt = con.prepareStatement(Patient.SearchApt(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				avlapt.setString(1, docsurname);
				avlapt.setInt(2, docspecialty);
				rs = avlapt.executeQuery();
				
				createFinTable(out, true);
			    String htmlRow = createTable(rs, true, request);
				out.println(htmlRow);
			    createFinTable(out, false);
				
				rs.close();	avlapt.close(); con.close();
			}
			catch(SQLException sqle) 
			{	
				sqle.printStackTrace();
			}
			catch(NullPointerException nlptre)
			{
				response.sendRedirect(request.getContextPath());
			}
		}
		else if(requestType.equalsIgnoreCase("aptadd"))
		{
			int id = Integer.parseInt(request.getParameter("id"));
			try 
			{
				HttpSession usrSession = request.getSession(false);
				synchronized (usrSession)
				{
					
				Connection con = ds.getConnection();
				Patient tmpPatient = (Patient)usrSession.getAttribute("tmPatient");
			
				//================REGISTER DOCTOR'S APPOINTMENT-- START==================
			    PreparedStatement addapthrs = con.prepareStatement(tmpPatient.MakeApt(true), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    addapthrs.setLong(1, tmpPatient.GetSSN());
			    addapthrs.setLong(2, id);
			    addapthrs.executeUpdate();
			    addapthrs.close(); con.close();
			    //================REGISTER DOCTOR'S APPOINTMENT -- FINISH=================
			    //response.sendRedirect(request.getContextPath() +"/patient?status=signin");
			    response.sendRedirect(request.getHeader("referer"));
				}
			} 
			catch(SQLException sqle) 
			{
				sqle.printStackTrace();
			}
		}
		else if(requestType.equalsIgnoreCase("aptdel"))
		{
			int id = Integer.parseInt(request.getParameter("id"));
			try 
			{
				HttpSession usrSession = request.getSession(false);
				synchronized (usrSession)
				{
					
				Connection con = ds.getConnection();
				Patient tmpPatient = (Patient)usrSession.getAttribute("tmPatient");
				//================REGISTER DOCTOR'S APPOINTMENT-- START==================
			    PreparedStatement addapthrs = con.prepareStatement(tmpPatient.MakeApt(false), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    addapthrs.setInt(1, id);
			    addapthrs.executeUpdate();	    
			    addapthrs.close(); con.close();
			    //================REGISTER DOCTOR'S APPOINTMENT -- FINISH=================
			    //response.sendRedirect(request.getContextPath() +"/patient?status=signin");
			    //response.sendRedirect(request.getContextPath() + "/doctor?status=aptcrt");
			    response.sendRedirect(request.getHeader("referer"));
				}
			}
			catch(SQLException sqle)
			{
				sqle.printStackTrace();
			}
		}
		else if(requestType.equalsIgnoreCase("apthst"))
		{
			try
			{
				HttpSession usrSession = request.getSession(false);
				synchronized (usrSession)
				{
				Connection con = ds.getConnection();
				Patient tmpPatient = (Patient)usrSession.getAttribute("tmPatient");
//=========================HTML CODE START==========================
				out.println("<!DOCTYPE html>\r\n <html>");
				out.println("<head><title>Appointment History</title></head>");
				out.println("<body>");
				out.println("<h4>Patient:"+tmpPatient.GetSurname()+", "+tmpPatient.GetName()+" ("+tmpPatient.GetUsername()+")</h4>");
//=========================HTML CODE FINISH=========================		
				
//===========VIEW PATIENT'S PAST APPOINTMENTS -- FINISH=============				
				PreparedStatement ViewAppointmentHistory = con.prepareStatement(tmpPatient.ViewAppointmentHistory(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ViewAppointmentHistory.setLong(1, tmpPatient.GetSSN());
				ResultSet appmnt = ViewAppointmentHistory.executeQuery();
				
				createFinTable(out, true);
				String htmlRow = createTable(appmnt);			
				out.println(htmlRow);
				createFinTable(out, false);
				out.println("<p> <form action='/javaHospital/patient' method='get'>"
						+ "<button type='submit' name='status' value='aptpnd'>"
						+ "View Pending Appointments</button>"
						+ "</form> </p>");
				//out.println("<p> <button onclick='window.history.back();'>Return to Dashboard</button> </p>");
				out.println("<form action='/javaHospital/patient' method='get'> <button type='submit' name='status' value='signin'>Return to Dashboard</button> </form>");
				appmnt.close();	ViewAppointmentHistory.close(); con.close();
//===========VIEW PATIENT'S PAST APPOINTMENTS -- FINISH=============
				
//=========================HTML CODE START==========================			
				out.println("</body> </html>");
//=========================HTML CODE FINISH=========================				
				}
			}
			catch(SQLException sqle)
			{
				sqle.printStackTrace();
			}
			catch(NullPointerException nlptre)
			{
				response.sendRedirect(request.getContextPath());
			}
		}
//===============================================================================================================================requestType PATIENT PENDING APPOINTMENTS
		else if(requestType.equalsIgnoreCase("aptpnd"))
		{
			try
			{
				HttpSession usrSession = request.getSession(false);
				synchronized (usrSession)
				{
				Connection con = ds.getConnection();
				Patient tmpPatient = (Patient)usrSession.getAttribute("tmPatient");
//=========================HTML CODE START==========================
				out.println("<!DOCTYPE html>\r\n <html>");
				out.println("<head><title>Pending Appointments</title></head>");			
				out.println("<body>");
				out.println("<h4>Patient:"+tmpPatient.GetSurname()+", "+tmpPatient.GetName()+" ("+tmpPatient.GetUsername()+")</h4>");
//=========================HTML CODE FINISH=========================

//===========VIEW PATIENT'S PENDING APPOINTMENTS -- START===========
				PreparedStatement ViewPendingAppointments = con.prepareStatement(tmpPatient.ViewScheduledAppointments(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ViewPendingAppointments.setLong(1, tmpPatient.GetSSN());
				ResultSet appmnt = ViewPendingAppointments.executeQuery();
				
				createFinTable(out, true);
				String htmlRow = createTable(appmnt, false, request);		
				out.println(htmlRow);
				createFinTable(out, false);
				out.println("<p> <form action='/javaHospital/patient' method='get'>"
						+ "<button type='submit' name='status' value='apthst'>"
						+ "View Appointment History </button>"
						+ "</form> </p>");
				//out.println("<p> <button onclick='window.history.back();'>Return to Dashboard</button> </p>");
				out.println("<form action='/javaHospital/patient' method='get'> <button type='submit' name='status' value='signin'>Return to Dashboard</button> </form>");
				appmnt.close(); ViewPendingAppointments.close(); con.close();
//==========VIEW PATIENT'S PENDING APPOINTMENTS -- FINISH===========
				
//=========================HTML CODE START==========================			
				out.println("</body> </html>");
//=========================HTML CODE FINISH=========================				
				}
			}
			catch(SQLException sqle)
			{
				sqle.printStackTrace();
			}
			catch(NullPointerException nlptre)
			{
				response.sendRedirect(request.getContextPath());
			}
		}
//===============================================================================================================================requestType PATIENT SIGN-OUT
		else if(requestType.equalsIgnoreCase("signout"))
		{
			try
			{
				HttpSession usrSession = request.getSession(false);
				synchronized (usrSession)
				{
					usrSession.invalidate();
					//response.sendRedirect(request.getContextPath() + "/index.html");					
					response.sendRedirect(request.getContextPath());
				}
			}
			catch(NullPointerException nlptre)
			{
				response.sendRedirect(request.getContextPath());
			}
		}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse   response) throws ServletException, IOException 
	{
        doPost(request, response);
	}
	
	private String GetSpecialtyList(ResultSet rs) 
	{
		String tmp = "<select name='docspecialty' required>";
		tmp  += "<option value = '' disabled selected>Dr. Specialty</option>";	
		try 
		{			
		    rs.beforeFirst();
			while(rs.next())
			{
				tmp  += "<option value = '"+ rs.getString(1) +"'>" + rs.getString(2) + "</option>";						
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		tmp += "</select>";
		return tmp;
	}

	public void createFinTable(PrintWriter out, Boolean flag)
	{
		if(flag.equals(true))
			out.println("<table border=\"0\">");
		else
			out.println("</table>");
	}
	private String createTable(ResultSet rs, Boolean flag, HttpServletRequest request)
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
				if (flag.equals(true))
				{
					String tmp = request.getContextPath()+"/patient?status=aptadd&id="+rs.getString(1);
					row += "<td>" + "<a href='"+tmp+"'>Arrange</a>" + "</td>";
				}
				else
				{
					String tmp = request.getContextPath()+"/patient?status=aptdel&id="+rs.getString(1);
					row += "<td>" + "<a href='"+tmp+"'>Cancel</a>" + "</td>";
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
		out.println("<a href=\"/javaHospital/\">Return to Homepage</a>");
		out.println("</body></html>");
	}
}