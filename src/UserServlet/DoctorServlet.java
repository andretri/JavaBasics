package UserServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import UserClass.Doctor;

/**
 * Servlet implementation class DoctorServlet
 */
@WebServlet("/doctor")
public class DoctorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource ds = null;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoctorServlet() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
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
//============================================================================================================================================requestType DOCTOR SIGN IN		
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
					Doctor tmpDoctor = null;
//=========================HTML CODE START==========================			    
					out.println("<!DOCTYPE html>\r\n <html>");
					out.println("<head><title>Doctor Dashboard</title></head>");
					out.println("<body>");
//=========================HTML CODE FINISH=========================
				    
//=================FETCH PATIENT'S DATA -- START====================
					PreparedStatement loginDoctor = con.prepareStatement(Doctor.AuthenticateDoctor(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					loginDoctor.setString(1, usrname);
					
					ResultSet rs = loginDoctor.executeQuery();
					createFinTable(out, true);
					while(rs.next())
					{	
						tmpDoctor = new Doctor(rs.getString("usrname"), rs.getString("password"), rs.getString("name"), rs.getString("surname"), rs.getLong("doctorAMKA"), rs.getString("name"));
					}
					usrSession.setAttribute("tmDoctor", tmpDoctor);
					htmlRow = createTable(rs);
					out.println(htmlRow);
					
					createFinTable(out, false);
					rs.close();	loginDoctor.close(); con.close();
//=================FETCH PATIENT'S DATA -- FINISH===================
		
//=========================HTML CODE START==========================				
					out.println("<p> <form action='/javaHospital/doctor' method='get'>"
							+ "<button type='submit' name='status' value='apthst'>"
							+ "View Appointment History </button>"
							+ "</form> </p>");
					out.println("<p> <form action='/javaHospital/doctor' method='get'>"
							+ "<button type='submit' name='status' value='aptpnd'>"
							+ "View Pending Appointments</button>"
							+ "</form> </p>");
					out.println("<p> <form action='/javaHospital/doctor' method='get'>"
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
//==================================================================================================================================requestType DOCTOR PAST APPOINTMENTS
		else if(requestType.equalsIgnoreCase("apthst"))
		{
			try
			{
				HttpSession usrSession = request.getSession(false);
				synchronized (usrSession)
				{
				Connection con = ds.getConnection();
				Doctor tmpDoctor = (Doctor)usrSession.getAttribute("tmDoctor");
//=========================HTML CODE START==========================
				out.println("<!DOCTYPE html>\r\n <html>");
				out.println("<head><title>Appointment History</title></head>");
				out.println("<body>");
				out.println("<h4>Dr."+tmpDoctor.GetSurname()+", "+tmpDoctor.GetName()+" ("+tmpDoctor.GetUsername()+")</h4>");
//=========================HTML CODE FINISH=========================		
				
//===========VIEW PATIENT'S PAST APPOINTMENTS -- FINISH=============				
				PreparedStatement ViewAppointmentHistory = con.prepareStatement(tmpDoctor.ViewAppointmentHistory(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ViewAppointmentHistory.setLong(1, tmpDoctor.GetSSN());
				ResultSet appmnt = ViewAppointmentHistory.executeQuery();
				
				createFinTable(out, true);
				String htmlRow = createTable(appmnt);			
				out.println(htmlRow);
				createFinTable(out, false);
				out.println("<p> <form action='/javaHospital/doctor' method='get'>"
						+ "<button type='submit' name='status' value='aptpnd'>"
						+ "View Pending Appointments</button>"
						+ "</form> </p>");
				//out.println("<p> <button onclick='window.history.back();'>Return to Dashboard</button> </p>");
				out.println("<form action='/javaHospital/doctor' method='get'> "
						+ "<button type='submit' name='status' value='signin'>"
						+ "Return to Dashboard</button>"
						+ "</form>");
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
//===============================================================================================================================requestType DOCTOR PENDING APPOINTMENTS
		else if(requestType.equalsIgnoreCase("aptpnd"))
		{
			try
			{
				HttpSession usrSession = request.getSession(false);
				synchronized (usrSession)
				{
				Connection con = ds.getConnection();
				Doctor tmpDoctor = (Doctor)usrSession.getAttribute("tmDoctor");
//=========================HTML CODE START==========================
				out.println("<!DOCTYPE html>\r\n <html>");
				out.println("<head><title>Pending Appointments</title></head>");			
				out.println("<body>");
				out.println("<h4>Dr."+tmpDoctor.GetSurname()+", "+tmpDoctor.GetName()+" ("+tmpDoctor.GetUsername()+")</h4>");
//=========================HTML CODE FINISH=========================

//===========VIEW PATIENT'S PENDING APPOINTMENTS -- START===========
				PreparedStatement ViewPendingAppointments = con.prepareStatement(tmpDoctor.ViewScheduledAppointments(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ViewPendingAppointments.setLong(1, tmpDoctor.GetSSN());
				ResultSet appmnt = ViewPendingAppointments.executeQuery();
				
				createFinTable(out, true);
				String htmlRow = createTable(appmnt);			
				out.println(htmlRow);
				createFinTable(out, false);
				out.println("<p> <form action='/javaHospital/doctor' method='get'>"
						+ "<button type='submit' name='status' value='apthst'>"
						+ "View Appointment History </button>"
						+ "</form> </p>");
				//out.println("<p> <button onclick='window.history.back();'>Return to Dashboard</button> </p>");
				out.println("<form action='/javaHospital/doctor' method='get'> "
						+ "<button type='submit' name='status' value='signin'>"
						+ "Return to Dashboard</button> "
						+ "</form>");
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
//===============================================================================================================================requestType DOCTOR SIGN-OUT
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


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);		
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
		out.println("<a href=\"/javaHospital/\">Return to Homepage</a>");
		out.println("</body></html>");
	}
}
