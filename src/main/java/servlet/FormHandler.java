/** File name: formHandler.java 
 * 
 *  Author:    Quansheng Xiao, Jeff Offutt
 */

// Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;

// Import Java Libraries
import java.io.*;
import java.util.*;
import java.lang.*;

// formHandler class
// Generic form handler -- Echo all the parameters and values
//     that a client inputs from an HTML form.
// Note: the name of the submit button in the form must be "submit"
// (ignore case) or the servlet will print "submit" parameter.


// CONSTRUCTOR: no constructor specified (default)
//
// ****************  Methods description  *******************************
// void doPost ()    --> Main method for gathering data and sending back
// void doGet ()     --> Not used.
//***********************************************************************
public class FormHandler extends HttpServlet
{

/** **********************************************************
 *  doPost()
 *  gather data and respond to browser
 ************************************************************ */
public void doPost(HttpServletRequest request, HttpServletResponse response)
       throws ServletException, IOException
{   
   // first, set the "content type" header of the response
   response.setContentType ("text/html");
   //Get the response's PrintWriter to return text to the client.
   PrintWriter toClient = response.getWriter ();

   String para;
   Enumeration paraNames = request.getParameterNames();

   toClient.println("<html>");
   toClient.println("<head>");
   toClient.println("  <title>Generic form handler</title>");
   toClient.println("</head>");

   toClient.println("<body bgcolor=\"#EEEEEE\">");
   toClient.println("");
   toClient.println("<center><h2>Generic form handler</h2></center>");
   toClient.println("<p>");
   toClient.println("The following table lists all parameter names and");
   toClient.println("their values that were submitted from your form.");
   toClient.println("</p>");
   toClient.println("");
   toClient.println("<p>");
   toClient.println("<table cellSpacing=1 cellPadding=1 width=\"75%\" border=1 bgColor=lavender>");
   toClient.println("");
   toClient.println("  <tr bgcolor=\"#FFFFFF\">");
   toClient.println("   <th align=\"center\"><b>Parameter</b></td>");
   toClient.println("   <th align=\"center\"><b>Value</b></td>");
   toClient.println("  </tr>");

   while (paraNames.hasMoreElements())
   {  // For each parameter name.
      para = (String)paraNames.nextElement();
      if (!para.equalsIgnoreCase("submit"))
      {
         toClient.println("  <tr>");
         toClient.println("    <td style=\"width: 20%\" width=\"20%\"><b>" + para + "</b></td>");

         String[] values = request.getParameterValues(para);

         if (values != null && !values[0].equals(""))
            toClient.println("    <td>" + values[0] + "</td></tr>");
         else
            toClient.println("    <td>&nbsp;</td></tr>");

         for (int i = 1; i < values.length; i++)
         {
            if (!values[i].equals(""))
            {
               toClient.println("  <tr>");
               toClient.println("    <td style=\"width: 20%\" width=\"20%\">&nbsp;</td>");
               toClient.println("    <td>" + values[i] + "</td></tr>");
            }
         }
      }
   }
   toClient.println("</table>");
   toClient.println("");
   toClient.println("</body>");
   toClient.println("</html>");

   toClient.println("");

   // Close the writer; the response is done.
   toClient.close();      
} //end of doPost()
   

/** *****************************************************
  *   doGet()
  *   not used
 ********************************************************* */
public void doGet (HttpServletRequest request,
                   HttpServletResponse response)
       throws ServletException, IOException
{
	response.setContentType("text/html");
	   PrintWriter out = response.getWriter();
	   PrintHead(out);
	   PrintBody(out);
	   PrintTail(out);
}  // End doGet()

private void PrintHead (PrintWriter out)
{
   out.println("<html>");
   out.println("");

   out.println("<head>");
   out.println("<title>Peter Hadeed - SWE 432 - Assignment 5</title>");
   out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"http://mason.gmu.edu/~phadeed/style.css\">");
   PrintStyle(out);
   out.println("</head>");
   out.println("");
} // End PrintHead

/** *****************************************************
 *  Prints the <BODY> of the HTML page with the form data
 *  values from the parameters.
********************************************************* */
private void PrintBody (PrintWriter out, String lhs, String rhs, String rslt)
{
   out.println("<body>");
   out.println("<div class=content>");
   out.println("<div class=header>");
   out.println("<h1>Peter Hadeed - SWE 432 - Assignment 5</h1>");
   out.println("</div>");
   
   out.println("<ul>");
   out.println("<li><a href=\"http://mason.gmu.edu/~phadeed/\">Home</a></li>");
   out.println("<li><a href=\"http://mason.gmu.edu/~phadeed/Assignment_3.html\">Assignment 3</a></li>");
   out.println("<li><a href=\"https://swe432-hadeed.herokuapp.com/Assignment5\" class = current>Assignment 5</a></li>");
   out.println("</ul>");
   
   
   //Form print
   out.println("<div class=form_title>");
   out.println("<h1> Logic Predicate Form </h1></div>");
   
   out.println("<div class=form>");
   out.println("<form method=\"post\" action = \"FormHandler\" name=\"PredicateForm\" ");
   out.println("onSubmit=\" return(CheckPredicate())\">");
   
   out.println("<table><tr><td><input type = \"text\" name=\"PredicateField\"></tr> ");
   out.println("<tr><td colspan=2 align=middle><input type=\"submit\" value=\"Submit\"></tr>");
   out.println("</table></form></div>");
   
   //Form Description
   out.println("<div class=form_description><p style=\"margin-left: 10%;\"");
   out.println("<b>Description of Use</b><br><br>");
   out.println("Enter any logical predicate into the form. Logical predicates consist"
   		+ " of any strings following the form (X = Operand, Y = Operator):");
   out.println("<br><br>");
   out.println("X -- X Y X -- X Y X Y X, Etc...");
   out.println("<br><br>");
   out.println("<b> Allowed </b><br><br>");
   out.println("1. Operators - && : & : and : || : | : or <br><br>");
   out.println("2. Operands - Anything counts as an operand.<br><br>");
   out.println("All Operands and Operators must be separated by a SPACE<br><br>");
   out.println("<b>Not Allowed</b><br><br>");
   out.println("1. Empty Forms<br><br>");
   out.println("2. Logical operators with only one operand (i.e. \"1 and \"");
   out.println("</p></div>");
   
   out.println("</div>");
   out.println("</body>");
} // End PrintBody

/** *****************************************************
 *  Overloads PrintBody (out,lhs,rhs,rslt) to print a page
 *  with blanks in the form fields.
********************************************************* */
private void PrintBody (PrintWriter out)
{
   PrintBody(out, "", "", "");
}

/** *****************************************************
 *  Prints the bottom of the HTML page.
********************************************************* */
private void PrintTail (PrintWriter out)
{
   out.println("");
   out.println("</html>");
} // End PrintTail

private void PrintStyle(PrintWriter out)
{
	out.println("<style>");
	out.println("h1{color:#000000;text-align:center;text-color:#900000;");
	out.println("text-shadow: 1px 1px darkgray;}");
	
	out.println("ul{list-style-type:none;margin:0;padding:0;width:15%;");
	out.println("background-color:lightgray;border:1px solid black; height: 80%;");
	out.println("border-radius: 20px; float: left; display: block;}");
	
	out.println("li a{display:block;color:#000;text-decoration:none;");
	out.println("padding: 8px 16px;text-align: center;border-radius: 20px;transition: 0.2s;}");
	
	out.println("li a:hover{background-color: #555;color:white;opacity:0.8;}");
	
	out.println("li a.current{background-color:#555;color:white;}");
	
	out.println("div.header{display:block;border:1px solid black;");
	out.println("border-radius: 20px;margin-bottom:10px;background-color:lightgray;}");
	
	out.println("div.content{padding: 1% 1%;background-color: efeff5;");
	out.println("border-radius:20px;height:97%;}");
	
	out.println("body{background-color: darkblue;}");
	
	out.println("div.button{padding: 0% 50%;}");
	
	out.println("div.form{border: 1px solid black; margin: 3% 20%;");
    out.println("border-radius: 20px;}");
    
    out.println("td{ text-align: center; margin-left: auto; margin-right: auto;}");
    
    out.println("table{margin-left:auto;margin-right:auto;margin-top:5%;}");
    
    out.println("div.form_description{border: 1px solid black;margin: 3% 20%;");
    out.println("border-radius: 20px;}");
    
    out.println("div.form_title{ margin: 3% 20%;text-align:center;}");
	
	out.println("</style>");
	
}

}  // End formHandler class