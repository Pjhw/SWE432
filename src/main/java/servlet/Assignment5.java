/** *****************************************************************
    assignment5.java

        @author Peter Hadeed
********************************************************************* */

import java.io.PrintWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//David: (1) adds servlet mapping annotation
import javax.servlet.annotation.WebServlet;
@WebServlet( name = "Assignment5", urlPatterns = {"/Assignment5"} )


public class Assignment5 extends HttpServlet
{

// Location of servlet.
// David: (5) adds the path of your form submit action
static String Domain  = "";
static String Path    = "";
static String Servlet = "Assignment5";

// Button labels
static String OperationA = "String A +  String B";
static String OperationB = "String B + String A";
//David: (2) adds Multiplication label


/** *****************************************************
 *  Overrides HttpServlet's doPost().
 *  Converts the values in the form, performs the operation
 *  indicated by the submit button, and sends the results
 *  back to the client.
********************************************************* */
@Override
public void doPost (HttpServletRequest request, HttpServletResponse response)
   throws ServletException, IOException
{
   String rslt  = new String();
   String operation = request.getParameter("Operation");
   String lhsStr = request.getParameter("LHS");
   String rhsStr = request.getParameter("RHS");


   if (operation.equals(OperationA))
   {
      rslt = new String(lhsStr + rhsStr);
   }
   else if (operation.equals(OperationB))
   {
      rslt = new String(rhsStr + lhsStr);
   }
   //David: (6) adds multiplication action's resolution

   response.setContentType("text/html");
   PrintWriter out = response.getWriter();
   PrintHead(out);
   PrintBody(out, lhsStr, rhsStr, rslt);
   PrintTail(out);
}  // End doPost

/** *****************************************************
 *  Overrides HttpServlet's doGet().
 *  Prints an HTML page with a blank form.
********************************************************* */
@Override
public void doGet (HttpServletRequest request, HttpServletResponse response)
       throws ServletException, IOException
{
   response.setContentType("text/html");
   PrintWriter out = response.getWriter();
   PrintHead(out);
   PrintBody(out);
   PrintTail(out);
} // End doGet

/** *****************************************************
 *  Prints the <head> of the HTML page, no <body>.
********************************************************* */
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
   
   out.println("div class=form>");
   out.println("<form method=\"post\" action = \"\" name=\"PredicateForm\" ");
   out.println("onSubmit=\" return(CheckPredicate())\">");
   
   out.println("<table><tr><td><input type = \"text\" name=\"PredicateField\"></tr> ");
   out.println("<tr><td colspan=2 align=middle<input type=\"submit\" value=\"Submit\"></tr>");
   out.println("</table></form></div>");
   
   //Form Description
   out.println("<div class=form_description><p style=\"margin-left: 10%;\"");
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
	
	out.println("</style>");
	
}

} 