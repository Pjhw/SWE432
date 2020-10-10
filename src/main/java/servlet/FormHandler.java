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
public class formHandler extends HttpServlet
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
   //Get the response's PrintWriter to return text to the client.
   PrintWriter out = response.getWriter();
            
   String title = "This is from formHandler Servlet";
      
   out.println("<html>");
   out.println("<head>");
   out.println("  <title>" + title + "</title>");
   out.println("</head>");
   out.println("<body>");
   out.println("  <h1>" + title + "</h1>");
   out.println("  <p>Please run the servlet from formHandler.html.");
   out.println("</body>");
   out.println("</html>");
   out.close();
}  // End doGet()

}  // End formHandler class