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

// Other strings.
static String Style ="http://mason.gmu.edu/~phadeed/style.css";

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
   out.println("<title>Two buttons example</title>");
   out.println(" <link rel=\"stylesheet\" type=\"text/css\" href=\"" + Style + "\">");
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
   out.println("<p>");
   out.println("A simple example that demonstrates how to operate with");
   out.println("multiple submit buttons.");
   out.println("</p>");
   out.print  ("<form method=\"post\"");
   //David: (4) changes  action's url to your own url using a relative path to the servlet.
   //If left untouched, the operation buttons go to Prof. Offutt website, and
   // if you provide an erroneous path you will see a 404 (Not Found) error.
   //In the form action, you can specify an absolute or relative path to your URL
   // and optionally the servlet that will respond to the action.
   //However, the original line only works when your app is deployed
   // and not when running locally (yourpage.com vs localhost:port).
   // For simplicity, I used a relative path but it is strongly recommended
   // to use absolute paths because they can cached by web servers and browsers
   out.println(" action=\"/" + Servlet + "\">");
   out.println("");
   out.println(" <table>");
   out.println("  <tr>");
   out.println("   <td>First value:");
   out.println("   <td><input type=\"text\" name=\"LHS\" value=\"" + lhs + "\" size=5>");
   out.println("  </tr>");
   out.println("  <tr>");
   out.println("   <td>Second value:");
   out.println("   <td><input type=\"text\" name=\"RHS\" value=\"" + rhs + "\" size=5>");
   out.println("  </tr>");
   out.println("  <tr>");
   out.println("   <td>Result:");
   out.println("   <td><input type=\"text\" name=\"RHS\" value=\"" + rslt + "\" size=6>");
   out.println("  </tr>");
   out.println(" </table>");
   out.println(" <br>");
   out.println(" <br>");
   out.println(" <input type=\"submit\" value=\"" + OperationA + "\" name=\"Operation\">");
   out.println(" <input type=\"submit\" value=\"" + OperationB + "\" name=\"Operation\">");
   // David: (3) adds multiplication button
   out.println(" <input type=\"reset\" value=\"Reset\" name=\"reset\">");
   out.println("</form>");
   out.println("");
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

}  // End twoButtons