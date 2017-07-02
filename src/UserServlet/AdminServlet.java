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

import UserClass.Admin;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private DataSource ds = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() 
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
					Admin tmpAdmin = null;
//=========================HTML CODE START==========================			    
					out.println("<!DOCTYPE html>\r\n <html>");
					out.println("<head><title>Admin Dashboard</title></head>");
					out.println("<body>");
//=========================HTML CODE FINISH=========================
						    
//=================FETCH ADMIN'S DATA -- START====================
					PreparedStatement loginAdmin = con.prepareStatement(Admin.AuthenticateAdmin(true), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					loginAdmin.setString(1, usrname);
					
					
					ResultSet rs = loginAdmin.executeQuery();
					
					createFinTable(out, true);
					while(rs.next())
					{	
						tmpAdmin = new Admin(rs.getString("usrname"), rs.getString("password"), rs.getString("name"), rs.getString("surname"), rs.getInt("id"));
					}
					usrSession.setAttribute("tmAdmin", tmpAdmin);
					htmlRow = createTable(rs);
					out.println(htmlRow);
					createFinTable(out, false);
					
					rs.close();	loginAdmin.close(); con.close();
//=================FETCH ADMIN'S DATA -- FINISH===================
				
//=========================HTML CODE START==========================				
					out.println("<p> <form action='/javaHospital/admin' method='get'>"
							+ "<button type='submit' name='status' value='docreg'>"
							+ "Register Doctor</button>");
					out.println("<button type='submit' name='status' value='docban'>"
							+ "Ban Doctor</button>");
					out.println("<button type='submit' name='status' value='admreg'>"
							+ "Register Admin </button>");
					out.println("<button type='submit' name='status' value='ptban'>"
							+ "Ban Patient</button>"
							+ "</form> </p>");
					out.println("<p> <form action='/javaHospital/'>"
							+ "<button type='submit'>"
							+ "Return to Homepage</button>"
							+ "</form> </p>");
					out.println("<p> <form action='/javaHospital/admin' method='get'>"
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
		else if(requestType.equalsIgnoreCase("docreg"))
		{
			try
			{
			String Query = "SELECT * from DEPARTMENTS;";
			Connection con = ds.getConnection();
			
			PreparedStatement SpecialtyList = con.prepareStatement(Query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = SpecialtyList.executeQuery();
			String SpecLst= GetSpecialtyList(rs);
			rs.close();	SpecialtyList.close(); con.close();
			
			out.println("<!DOCTYPE html>\r\n" + 
					"<html>\r\n" + 
					"<head>\r\n" + 
					"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n" + 
					"<title>Login</title>\r\n" + 
					"</head>\r\n" + 
					"<body>\r\n" + 
					"	<form method=\"post\" action=\"UserServlet/admin\">\r\n" + 
					"		<input type=\"hidden\" name=\"status\" value=\"docadd\" />\r\n" + 
					"		<table>\r\n" + 
					"			<tr>\r\n" + 
					"				<td>AMKA:</td>\r\n" + 
					"				<td><input type=\"text\" placeholder=\"Dr. AMKA\" required=\"required\" name=\"amka\" /></td>\r\n" + 
					"				<br />\r\n" + 
					"			</tr>\r\n" + 
					"			<tr>\r\n" + 
					"				<td>Username:</td>\r\n" + 
					"				<td><input type=\"text\" placeholder=\"Username\" required=\"required\" name=\"usrname\" /></td>\r\n" + 
					"				<br />\r\n" + 
					"			</tr>\r\n" + 
					"			<tr>\r\n" + 
					"				<td>Password:</td>\r\n" + 
					"				<td><input type=\"password\" placeholder=\"Password\" required=\"required\" name=\"passwrd\" /></td>\r\n" + 
					"				<br />\r\n" + 
					"			</tr>\r\n" + 
					"			<tr>\r\n" + 
					"				<td>Name:</td>\r\n" + 
					"				<td><input type=\"text\" placeholder=\"Name\" required=\"required\" name=\"name\" /></td>\r\n" + 
					"				<br />\r\n" + 
					"			</tr>\r\n" + 
					"			<tr>\r\n" + 
					"				<td>Surname:</td>\r\n" + 
					"				<td><input type=\"text\" placeholder=\"Surname\" required=\"required\" name=\"surname\" /></td>\r\n" + 
					"				<br />\r\n" + 
					"			</tr>\r\n" + 
					"			<tr>\r\n" + 
					"				<td>Specialty:</td>\r\n <td>" + SpecLst + "</td>" +
					"			</tr>\r\n" + 
					"			<tr>\r\n" + 
					"				<td></td>\r\n" + 
					"				<td><input type=\"submit\" value=\"register\" /></td>\r\n" + 
					"				<br />\r\n" + 
					"			</tr>\r\n" + 
					"		</table>\r\n" + 
					"	</form>\r\n" + 
					"\r\n" + 
					"</body>\r\n" + 
					"</html>");
			}
			catch(SQLException sqle)
			{
				sqle.printStackTrace();
			}
		}
		else if(requestType.equalsIgnoreCase("docadd"))
		{
				HttpSession usrSession = request.getSession(false);
				
				Long AMKA = Long.parseLong(request.getParameter("amka")); String name = request.getParameter("name");
				String usrname = request.getParameter("usrname"); 		  String surname = request.getParameter("surname");
				String passwrd = request.getParameter("passwrd"); 	  	  int spec = Integer.parseInt(request.getParameter("docspecialty"));
				
				try 
				{
					Connection con = ds.getConnection();
					Admin tmpAdmin= (Admin)usrSession.getAttribute("tmAdmin");
		//================REGISTER DOCTOR'S DATA -- START==================
				    PreparedStatement registerDoctor = con.prepareStatement(tmpAdmin.RegisterDoctor(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				    registerDoctor.setLong(1, AMKA);		registerDoctor.setString(5, name);
				    registerDoctor.setString(2, usrname); 	registerDoctor.setString(6, surname);
				    registerDoctor.setString(3, passwrd);	registerDoctor.setInt(7, spec);
				    registerDoctor.setLong(4, AMKA);
				    registerDoctor.setInt(8, tmpAdmin.GetID());

				    registerDoctor.executeUpdate();
				    String tmp = "Dr. "+surname+", "+name+" ("+AMKA+") \n Registered Succesfully";
				    //=========================HTML CODE START==========================	
				    out.println("<html><head>");
				    out.println("<title onload ='alert("+tmp+")'>Doctor Registration</title>");
				    out.println("</head><body></body></html>");
				    //=========================HTML CODE FINISH=========================		    
				    registerDoctor.close(); con.close();
		//================REGISTER DOCTOR'S DATA -- FINISH=================
				    //response.sendRedirect(request.getContextPath() +"/patient?status=signin");
				    response.sendRedirect(request.getHeader("referer"));
				} 
				catch(SQLException sqle) 
				{
					out.println("<html><head>");
				    out.println("<title onload ='alert(Registration Failed)'>Doctor Registration</title>");
				    out.println("</head><body></body></html>");
					response.sendRedirect(request.getHeader("referer"));
				}
		}
		else if(requestType.equalsIgnoreCase("docban"))
		{
			out.println("<form action='/javaHospital/admin' method='get'> "
					+ "<button type='submit' name='status' value='signin'>"
					+ "Return to Dashboard</button>"
					+ "</form>");
			try 
			{
				HttpSession usrSession = request.getSession(false);
				synchronized (usrSession)
				{
				Connection con = ds.getConnection();
				Admin tmpAdmin = (Admin)usrSession.getAttribute("tmAdmin");
				//================REGISTER DOCTOR'S APPOINTMENT-- START==================
			    PreparedStatement viewDr = con.prepareStatement(tmpAdmin.BanDoctor(false), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    ResultSet rs = viewDr.executeQuery();
			    createFinTable(out, true);
			    String htmlRow = createTable(rs, true, request);
				out.println(htmlRow);
			    createFinTable(out, false);
			    viewDr.close(); con.close(); rs.close();
			    //================REGISTER DOCTOR'S APPOINTMENT -- FINISH=================
				}
			} 
			catch(SQLException sqle) 
			{
				sqle.printStackTrace();
			}
			catch(NullPointerException nlptr)
			{
				response.sendRedirect(request.getContextPath() + "/admin?status=signin");
			}
		}
		else if(requestType.equalsIgnoreCase("ban-dr"))
		{
			try 
			{
				HttpSession usrSession = request.getSession(false);
				synchronized (usrSession)
				{
					Connection con = ds.getConnection();
					Admin tmpAdmin = (Admin)usrSession.getAttribute("tmAdmin");
					PreparedStatement banDr = con.prepareStatement(tmpAdmin.BanDoctor(true), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					banDr.setLong(1, Long.parseLong(request.getParameter("amka")));
					banDr.executeUpdate();	    
					banDr.close(); con.close();
					response.sendRedirect(request.getHeader("referer"));
				}
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			catch(NullPointerException nlptr)
			{
				response.sendRedirect(request.getContextPath() + "/admin?status=signin");
			}
		}
		else if(requestType.equalsIgnoreCase("admreg"))
		{
			out.println("<!DOCTYPE html>\r\n" + 
					"<html>\r\n" + 
					"<head>\r\n" + 
					"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n" + 
					"<title>Login</title>\r\n" + 
					"</head>\r\n" + 
					"<body>\r\n" + 
					"	<form method=\"post\" action=\"UserServlet/admin\">\r\n" + 
					"		<input type=\"hidden\" name=\"status\" value=\"admadd\" />\r\n" + 
					"		<table>\r\n" + 
					"			<tr>\r\n" + 
					"				<td>Username:</td>\r\n" + 
					"				<td><input type=\"text\" placeholder=\"Username\" required=\"required\" name=\"usrname\" /></td>\r\n" + 
					"				<br />\r\n" + 
					"			</tr>\r\n" + 
					"			<tr>\r\n" + 
					"				<td>Password:</td>\r\n" + 
					"				<td><input type=\"password\" placeholder=\"Password\" required=\"required\" name=\"passwrd\" /></td>\r\n" + 
					"				<br />\r\n" + 
					"			</tr>\r\n" + 
					"			<tr>\r\n" + 
					"				<td>Name:</td>\r\n" + 
					"				<td><input type=\"text\" placeholder=\"Name\" required=\"required\" name=\"name\" /></td>\r\n" + 
					"				<br />\r\n" + 
					"			</tr>\r\n" + 
					"			<tr>\r\n" + 
					"				<td>Surname:</td>\r\n" + 
					"				<td><input type=\"text\" placeholder=\"Surname\" required=\"required\" name=\"surname\" /></td>\r\n" + 
					"				<br />\r\n" + 
					"			</tr>\r\n" + 
					"			<tr>\r\n" + 
					"				<td></td>\r\n" + 
					"				<td><input type=\"submit\" value=\"register\" /></td>\r\n" + 
					"				<br />\r\n" + 
					"			</tr>\r\n" + 
					"		</table>\r\n" + 
					"	</form>\r\n" + 
					"\r\n" + 
					"</body>\r\n" + 
					"</html>");
		}
		else if(requestType.equalsIgnoreCase("admadd"))
		{
			String name = request.getParameter("name");
			String usrname = request.getParameter("usrname");
			String passwrd = request.getParameter("passwrd");
			String surname = request.getParameter("surname");
			
			try 
			{
				Connection con = ds.getConnection();
	//================REGISTER DOCTOR'S DATA -- START==================
			    PreparedStatement registerDoctor = con.prepareStatement(Admin.RegisterAdmin(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    registerDoctor.setString(1, usrname); 	
			    registerDoctor.setString(2, passwrd);	
			   	registerDoctor.setString(3, name);
			    registerDoctor.setString(4, surname);

			    registerDoctor.executeUpdate();
			    String tmp = "Admin "+surname+", "+name+" \n Registered Succesfully";
			    //=========================HTML CODE START==========================	
			    out.println("<html><head>");
			    out.println("<title onload ='alert("+tmp+")'>Admin Registration</title>");
			    out.println("</head><body></body></html>");
			    //=========================HTML CODE FINISH=========================		    
			    registerDoctor.close(); con.close();
	//================REGISTER DOCTOR'S DATA -- FINISH=================
			    //response.sendRedirect(request.getContextPath() +"/patient?status=signin");
			    response.sendRedirect(request.getHeader("referer"));
			} 
			catch(SQLException sqle) 
			{
				out.println("<html><head>");
			    out.println("<title onload ='alert(Registration Failed)'>Admin Registration</title>");
			    out.println("</head><body></body></html>");
				response.sendRedirect(request.getHeader("referer"));
			}
		}
		else if(requestType.equalsIgnoreCase("ptban"))
		{
			out.println("<br/>");
			out.println("<form action='/javaHospital/admin' method='get'> "
					+ "<button type='submit' name='status' value='signin'>"
					+ "Return to Dashboard</button>"
					+ "</form>");
			try 
			{
				HttpSession usrSession = request.getSession(false);
				synchronized (usrSession)
				{
				Connection con = ds.getConnection();
				Admin tmpAdmin = (Admin)usrSession.getAttribute("tmAdmin");
				//================REGISTER DOCTOR'S APPOINTMENT-- START==================
			    PreparedStatement viewPt = con.prepareStatement(tmpAdmin.BanPatient(false), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    ResultSet rs = viewPt.executeQuery();
			    createFinTable(out, true);
			    String htmlRow = createTable(rs, false, request);
				out.println(htmlRow);
			    createFinTable(out, false);
			    viewPt.close(); con.close(); rs.close();
			    //================REGISTER DOCTOR'S APPOINTMENT -- FINISH=================
				}
			} 
			catch(SQLException sqle) 
			{
				sqle.printStackTrace();
			}
			catch(NullPointerException nlptr)
			{
				response.sendRedirect(request.getContextPath() + "/admin?status=signin");
			}
		}
		else if(requestType.equalsIgnoreCase("ban-pt"))
		{
			try 
			{
				HttpSession usrSession = request.getSession(false);
				synchronized (usrSession)
				{
					Connection con = ds.getConnection();
					Admin tmpAdmin = (Admin)usrSession.getAttribute("tmAdmin");
					PreparedStatement banDr = con.prepareStatement(tmpAdmin.BanPatient(true), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					banDr.setLong(1, Long.parseLong(request.getParameter("amka")));
					banDr.executeUpdate();	    
					banDr.close(); con.close();
					response.sendRedirect(request.getHeader("referer"));
				}
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			catch(NullPointerException nlptr)
			{
				response.sendRedirect(request.getContextPath() + "/admin?status=signin");
			}
		}
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
					String tmp = request.getContextPath()+"/admin?status=ban-dr&amka="+rs.getString(1);
					row += "<td>" + "<a href='"+tmp+"'>Ban</a>" + "</td>";
				}
				else
				{
					String tmp = request.getContextPath()+"/admin?status=ban-pt&amka="+rs.getString(1);
					row += "<td>" + "<a href='"+tmp+"'>Ban</a>" + "</td>";
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
