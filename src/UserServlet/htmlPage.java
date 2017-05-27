package UserServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class htmlPage 
{
	public htmlPage(PrintWriter out)
	{
		out.println("<!DOCTYPE html>\r\n" + 
					"<html>");
	}
		
	public void WritePageTitle(String userType, PrintWriter out)
	{
		out.println("<head><title>"+ userType +" DashBoard</title></head>");
		out.println("<body>");
	}
	
	public void createFinTable(PrintWriter out, Boolean flag)
	{
		if(flag.equals(true))
			out.println("<table border=\"0\">");
		else
			out.println("</table>");
	}
	
	public String createTableRow(String tmp_str1, String tmp_str2, String tmp_str3, String tmp_str4, String tmp_str5) 
	{
		String row = null; 
		row = "<tr>";
		row  += "<td>" + tmp_str1 + "</td>";
		row  += "<td>" + tmp_str2 + "</td>";
		row  += "<td>" + tmp_str3 + "</td>";
		row  += "<td>" + tmp_str4 + "</td>";
		row  += "<td>" + tmp_str5 + "</td>";
		row +="</tr>";
		return row;
	}

	public void createErrPage(HttpServletRequest request, HttpServletResponse response, String message, PrintWriter out) throws IOException 
	{
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding(request.getCharacterEncoding());
		this.WritePageTitle("Patient", out);
		out.println("<p>" + message + "</p>");
		out.println("<a href=\"/javaHospital/index.html\">Επιστροφή στην αρχική σελίδα</a>");
		out.println("</body></html>");
	}

}
