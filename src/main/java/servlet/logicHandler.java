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

import com.google.gson.Gson;

import java.util.List;
import java.util.ArrayList;


import javax.servlet.annotation.WebServlet;
@WebServlet( name = "logicHandler", urlPatterns = {"/logicHandler"} )
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
public class logicHandler extends HttpServlet
{

	
	static enum Data {PREDICATE};
	  static String RESOURCE_FILE = "predicates.json";

	  static String Domain  = "";
	  static String Path    = "/";
	  static String Servlet = "Assignment7";

	  // Button labels
	  static String OperationAdd = "Add";

	  public class Entry {
	    String predicate;
	  }

	  public class Entries{
	    List<Entry> entries;
	  }

	  public class EntryManager{
	    private String filePath = null;

	    public void setFilePath(String filePath) {
	        this.filePath = filePath;
	    }
	    public Entries save(String predicate){
	      Entries entries = getAll();
	      Entry newEntry = new Entry();
	      newEntry.predicate = predicate;
	      entries.entries.add(newEntry);
	      try{
	        FileWriter fileWriter = new FileWriter(filePath);
	        new Gson().toJson(entries, fileWriter);
	        fileWriter.flush();
	        fileWriter.close();
	      }catch(IOException ioException){
	        return null;
	      }

	      return entries;
	    }

	    private Entries getAll(){
	      Entries entries =  entries = new Entries();
	      entries.entries = new ArrayList();

	      try{
	        File file = new File(filePath);
	        if(!file.exists()){
	          return entries;
	        }

	        BufferedReader bufferedReader =
	          new BufferedReader(new FileReader(file));
	        Entries readEntries =
	          new Gson().fromJson(bufferedReader, Entries.class);

	        if(readEntries != null && readEntries.entries != null){
	          entries = readEntries;
	        }
	        bufferedReader.close();

	      }catch(IOException ioException){
	      }

	      return entries;
	    }

	    public String getAllAsHTMLTable(Entries entries){
	      StringBuilder htmlOut = new StringBuilder("<table>");
	      htmlOut.append("<tr><th>Name</th><th>Age</th></tr>");
	      if(entries == null
	          || entries.entries == null || entries.entries.size() == 0){
	        htmlOut.append("<tr><td>No entries yet.</td></tr>");
	      }else{
	        for(Entry entry: entries.entries){
	           htmlOut.append(
	           "<option>"+entry.predicate+"</option>");
	        }
	      }
	      htmlOut.append("</table>");
	      return htmlOut.toString();
	    }

	  }
	  
/** **********************************************************
 *  doPost()
 *  gather data and respond to browser
 ************************************************************ */
@Override
public void doPost(HttpServletRequest request, HttpServletResponse response)
       throws ServletException, IOException
{   
   // first, set the "content type" header of the response
   response.setContentType ("text/html");
   //Get the response's PrintWriter to return text to the client.
   PrintWriter out = response.getWriter ();

   
   String[] values = request.getParameterValues("PredicateField");
   String[]  predicate = values[0].split(" ");

   EntryManager entryManager = new EntryManager();
   entryManager.setFilePath(RESOURCE_FILE);
   Entries newEntries=entryManager.save(values[0]);
   
   PrintWriter entriesPrintWriter = new PrintWriter(new FileWriter(RESOURCE_FILE, true), true);
   entriesPrintWriter.println(values[0]);
   entriesPrintWriter.close();
   
   PrintHead(out);
   PrintHeader(out);
   out.println("<div class=form_title>");
   out.println("<h1> Logic Predicate Form </h1></div>");
   
   out.println("<table cellSpacing=1 cellPadding=1 width=\"75%\" border=1 bgColor=lavender>");
   out.println("");
   out.println("  <tr bgcolor=\"#FFFFFF\">");
   
   int operator = 0;
   int n = predicate.length/2  + 1;
   String[] operators = new String[predicate.length/2];
   int opCtr = 0;
   
   for(String value : predicate) {
	   if(operator==1) {
		   operators[opCtr++] = value;
		   operator = 0;
		   continue;
	   }
	   out.println("   <td align=\"center\"><b>" + value + "</b></td>");
	   operator = 1;
   }
   
   out.println("   <td align=\"center\"><b>" + values[0] + "</b></td>");
   out.println("  </tr>");
   
   
   int rows = (int) Math.pow(2, n);
   int result = 1;
   int val = 1;
		   
   for(int i = 0; i < rows; i++) {
	   out.println("  <tr>"); 
	   opCtr=0;
	   for(int j = n-1; j >= 0; j--) {
		   val = (i/(int) Math.pow(2,  j))%2;
		   out.println("   <td align=\"center\"><b>" + val + "</b></td>");
		  
		   
		   if(j== (n-1)) {result = val;}
		   
		   
		   else {
			   if(isAnd(operators[opCtr])) {
				   result = (val & result);
			   }
			   else if(isOr(operators[opCtr])){
				   result = (val | result);
			   }
			   else {result =(val ^ result);}
			   
			   opCtr++;
		   }
		   
		   
	   }
	   
	   
	   out.println("   <td align=\"center\"><b>" + result + "</b></td>");
	   

	   result = 1;
	   out.println("</tr>");
	     
	   
   }
   
   
   
   out.println("</table>");
   
   PrintTail(out);

   out.println("");

   // Close the writer; the response is done.
   out.close();      
} //end of doPost()
   

/** *****************************************************
  *   doGet()
  *   not used
 ********************************************************* */
@Override
public void doGet (HttpServletRequest request,
                   HttpServletResponse response)
       throws ServletException, IOException
{
   response.setContentType("text/html");
   //Get the response's PrintWriter to return text to the client.
   PrintWriter out = response.getWriter();
            
   PrintHead(out);
   PrintHeader(out);
   PrintTail(out);
      
   
   out.close();
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
private void PrintHeader (PrintWriter out)
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
   
   
   
}


/** *****************************************************
 *  Prints the bottom of the HTML page.
********************************************************* */
private void PrintTail (PrintWriter out)
{
	out.println("</div>");
    out.println("</body>");
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

boolean isAnd(String val) {
	if(val.equals("&&") || val.equals("&") || val.equals("and")) {
		return true;
	}
	
	return false;
}

boolean isOr(String val) {
	if(val.equals("||") || val.equals("|") || val.equals("or")) {
		return true;
	}
	
	return false;
}


}  // End formHandler class