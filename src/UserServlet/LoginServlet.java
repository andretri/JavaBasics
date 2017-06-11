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
		try
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
			
			Connection con = ds.getConnection();
			PreparedStatement loginPatient = con.prepareStatement(Patient.AuthenticatePatient(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			String requestType= request.getParameter("requestType");
			if (requestType.equals("signin"))
			{
				HttpSession tmp = request.getSession(false);
				if (tmp != null)
				{
					request.getRequestDispatcher("/patient?status=signin").forward(request, response);
				}
				else
				{
					response.sendRedirect(request.getContextPath() + "/patient-login.html");
				}
			}
			else if (requestType.equals("Auth"))
			{
				String uname = request.getParameter("usrname"),
					   upass = request.getParameter("passwrd"),
					   authName = null, authPass = null;
				
				loginPatient.setString(1, uname); loginPatient.setString(2, upass);
				ResultSet rs = loginPatient.executeQuery();
				
				while(rs.next()) { authName = rs.getString("userid"); authPass = rs.getString("password"); } 
				rs.close();	loginPatient.close();
				try
				{
					if (authName.equals(uname) && authPass.equals(upass))
					{
						HttpSession usrSession = request.getSession();
						usrSession.setAttribute("usrname", authName);
						usrSession.setAttribute("passwrd", authPass);
						
						request.getRequestDispatcher("/patient?status=signin").forward(request, response);
					}	
					else
					{
						out.println("<script type=\"text/javascript\"> alert(\"Wrong username or password\nRedirecting to Sign-in Page...\"); \"</script>\");");
						//RequestDispatcher rd = request.getRequestDispatcher("../patient-login.html");
						//rd.forward(request, response);
						response.sendRedirect(request.getContextPath() + "/patient-login.html");
					}
				}
				catch (NullPointerException nlptr)
				{
					response.sendRedirect(request.getContextPath() + "/patient-login.html");
				}
			}
		}
		catch(SQLException sqle)
		{
			//RequestDispatcher rd = request.getRequestDispatcher("../index.html");
			//rd.forward(request, response);
			response.sendRedirect(request.getContextPath() + "/index.html");
		}
	}
}
