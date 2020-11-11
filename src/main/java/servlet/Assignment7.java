/** *****************************************************************
    assignment7.java

        @author Peter Hadeed
********************************************************************* */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import java.util.List;
import java.util.ArrayList;




import javax.servlet.annotation.WebServlet;
@WebServlet( name = "Assignment7", urlPatterns = {"/Assignment7"} )


public class Assignment7 extends HttpServlet
{

	  static enum Data {PREDICATE};
	  static String RESOURCE_FILE = "predicates.json";

	  static String Domain  = "";
	  static String Path    = "/";
	  static String Servlet = "Assignment7";
	  
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
	      int contains = 0;
	    	Entries entries = getAll();
	      Entry newEntry = new Entry();
	      newEntry.predicate = predicate;
	      for(Entry entry : entries.entries) {
	    	  if(entry.predicate.equals(predicate)) {
	    		  contains = 1;
	    	  }
	      }
	      
	      if(contains==0)entries.entries.add(newEntry);
	      
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
	   response.setContentType ("text/html");
	   PrintWriter out = response.getWriter ();
	   
	   String values = request.getParameter("PredicateField");
	   String[]  predicate = values.split(" ");

	   
	   EntryManager entryManager = new EntryManager();
	   entryManager.setFilePath(RESOURCE_FILE);
	   Entries newEntries=entryManager.save(values);
	   
	   PrintHead(out);
	   PrintHeader(out);
	   
	   out.println("<div class=form_title>");
	   out.println("<h1> Logic Predicate Form </h1></div>");
	   
	   out.println("<table cellSpacing=1 cellPadding=1 width=\"75%\" border=1 bgColor=lavender>");
	   out.println("");
	   out.println("  <tr bgcolor=\"#FFFFFF\">");
	   
	   
	   //Boolean table print out logic
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
	   
	   out.println("   <td align=\"center\"><b>" + values + "</b></td>");
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
   out.println("<title>Peter Hadeed - SWE 432 - Assignment 7</title>");
   out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"http://mason.gmu.edu/~phadeed/style.css\">");
   PrintStyle(out);
   PrintJS(out);
   out.println("</head>");
   out.println("");
} // End PrintHead

private void PrintHeader (PrintWriter out)
{
   out.println("<body>");
   out.println("<div class=content>");
   out.println("<div class=header>");
   out.println("<h1>Peter Hadeed - SWE 432 - Assignment 7</h1>");
   out.println("</div>");
   
   out.println("<ul>");
   out.println("<li><a href=\"http://mason.gmu.edu/~phadeed/\">Home</a></li>");
   out.println("<li><a href=\"http://mason.gmu.edu/~phadeed/Assignment_3.html\">Assignment 3</a></li>");
   out.println("<li><a href=\"https://swe432-hadeed.herokuapp.com/Assignment5\">Assignment 5</a></li>");
   out.println("<li><a href=\"https://swe432-hadeed.herokuapp.com/Assignment7\" class = current>Assignment 7</a></li>");
   out.println("<li><a href=\"https://swe432-hadeed.herokuapp.com/Assignment8\">Assignment 8</a></li>");
   out.println("</ul>");
   
   
   
}

/** *****************************************************
 *  Prints the <BODY> of the HTML page with the form data
 *  values from the parameters.
********************************************************* */
private void PrintBody (PrintWriter out)
{
	EntryManager entryManager = new EntryManager();
    entryManager.setFilePath(RESOURCE_FILE);
    
   out.println("<body>");
   out.println("<div class=content>");
   out.println("<div class=header>");
   out.println("<h1>Peter Hadeed - SWE 432 - Assignment 7</h1>");
   out.println("</div>");
   
   out.println("<ul>");
   out.println("<li><a href=\"http://mason.gmu.edu/~phadeed/\">Home</a></li>");
   out.println("<li><a href=\"http://mason.gmu.edu/~phadeed/Assignment_3.html\">Assignment 3</a></li>");
   out.println("<li><a href=\"https://swe432-hadeed.herokuapp.com/Assignment5\">Assignment 5</a></li>");
   out.println("<li><a href=\"https://swe432-hadeed.herokuapp.com/Assignment7\" class = current>Assignment 7</a></li>");
   out.println("<li><a href=\"https://swe432-hadeed.herokuapp.com/Assignment8\">Assignment 8</a></li>");
   out.println("</ul>");
   
   
   //Form print
   out.println("<div class=form_title>");
   out.println("<h1> Logic Predicate Form </h1></div>");
   
   out.println("<div class=form>");
   out.println("<form method=\"post\" action = \"Assignment7\" name=\"PredicateForm\" ");
   out.println("onSubmit=\" return(CheckPredicate())\">");
   
   out.println("<table><tr><td><input autocomplete=\"off\" list=\"predicates\" name=\"PredicateField\">"
   		+ "<datalist id=\"predicates\">");
   
   
   out.println(entryManager.getAllAsHTMLTable(entryManager.getAll()));
	

   out.println("</datalist></td></tr> ");
   out.println("<tr><td colspan=2 align=middle><input type=\"submit\" value=\"Submit\"></td>"
   		+ "</tr>");
   out.println("</table></form></div>");
   
   //Form Description
   out.println("<div class=form_description><p style=\"margin-left: 10%;\">");
   out.println("<b>Description of Use</b><br><br>");
   out.println("Enter any logical predicate into the form. Logical predicates consist"
   		+ " of any strings following the form (X = Operand, Y = Operator):");
   out.println("<br><br>");
   out.println("X -- X Y X -- X Y X Y X, Etc...");
   out.println("<br><br>");
   out.println("<b> Allowed </b><br><br>");
   out.println("1. Operators - && : & : and : || : | : or : xor : ^ <br><br>");
   out.println("2. Operands - Anything counts as an operand.<br><br>");
   out.println("3. All Operands and Operators must be separated by a SPACE<br><br>");
   out.println("<b>Not Allowed</b><br><br>");
   out.println("1. Empty Forms<br><br>");
   out.println("2. Logical operators with only one operand (i.e. \"1 and \")");
   out.println("</p></div>");
   
   out.println("</div>");
   out.println("</body>");
} // End PrintBody


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
	out.println("border-radius:20px;height:auto; overflow: auto;}");
	
	out.println("body{background-color: darkblue;}");
	
	out.println("div.button{padding: 0% 50%;}");
	
	out.println("div.form{border: 1px solid black; margin: 3% 20%;");
    out.println("border-radius: 20px;}");
    
    out.println("td{ text-align: center; margin-left: auto; margin-right: auto;}");
    
    out.println("table{margin-left:auto;margin-right:auto;margin-top:5%; margin-bottom:4%;}");
    
    out.println("div.form_description{border: 1px solid black;margin: 3% 20%;");
    out.println("border-radius: 20px;}");
    
    out.println("div.form_title{ margin: 3% 20%;text-align:center;}");
	
	out.println("</style>");
	
}



private void PrintJS(PrintWriter out) {
	out.println("<script>");
	
	out.println("function CheckPredicate(){\r\n" + 
			"         	var predicate = document.PredicateForm.PredicateField.value.split(\" \");\r\n" + 
			"         	var valid = true;\r\n" + 
			"         	var operand = true;\r\n" + 
			"         	var errormsg = \"The following errors occurred\\n\\n\";\r\n" + 
			"         	var one_operand = false;\r\n" + 
			"           var badOp = false;\r\n" +
			"         	\r\n" + 
			"         	\r\n" + 
			"         	//Loops through each word of the predicate string and evaluates it.\r\n" + 
			"         	for(let i = 0; i < predicate.length; i++){\r\n" + 
			"				//For checking the operand element of the string\r\n" + 
			"         		if (operand){\r\n" + 
			"         				\r\n" + 
			"         			if (CheckOperand(predicate[i])){\r\n" + 
			"         				operand = false;\r\n" + 
			"         				valid = true;\r\n" + 
			"         				one_operand = false;\r\n" + 
			"         				continue;\r\n" + 
			"         			}\r\n" + 
			"         			else{\r\n" + 
			"         				errormsg = errormsg.concat(\"Empty String for operand.\\n\");\r\n" + 
			"         				valid = false;\r\n" + 
			"         				break;\r\n" + 
			"         			}\r\n" + 
			"         			\r\n" + 
			"         		}\r\n" + 
			"				\r\n" + 
			"				//For checking the operator element of the string\r\n" + 
			"         		else{\r\n" + 
			"         			one_operand = true;\r\n" + 
			"         			\r\n" + 
			"         			if(CheckOperator(predicate[i])){\r\n" + 
			"         				operand = true;\r\n" + 
			"         				valid = false;\r\n" + 
			"         				continue;\r\n" + 
			"         			}\r\n" + 
			"         			else{\r\n" + 
			"                       if(!badOp){          "+
			"         				errormsg = errormsg.concat(\"Bad operator entered.\\n\");\r\n" + 
			"         				valid = false;\r\n" + 
			"                       badOp = true;\r\n" +
			"         				continue;}\r\n" + 
			"         				}\r\n" + 
			"         		}\r\n" + 
			"         	}\r\n" + 
			"         	\r\n" + 
			"			\r\n" + 
			"			\r\n" + 
			"         	if(valid){\r\n" + 
			"         		return (true);\r\n" + 
			"         	}	\r\n" + 
			"			\r\n" + 
			"         	else{\r\n" + 
			"         		if(one_operand && !badOp){\r\n" + 
			"         			errormsg = errormsg.concat(\"Operator is missing an operand\\n\");\r\n" + 
			"         		}\r\n" + 
			"         		PrintErrorMsg(errormsg);\r\n" + 
			"         		return(false);\r\n" + 
			"         	}\r\n" + 
			"         }");
	
	out.println("function CheckOperand(operand){\r\n" + 
			"         	if(operand.length == 0){\r\n" + 
			"         		return(false);\r\n" + 
			"         	}\r\n" + 
			"         	return (true);\r\n" + 
			"         }");
	
	out.println("function CheckOperator(operator){\r\n" + 
			"         	if(operator.localeCompare(\"&\") !=0 && operator.localeCompare(\"&&\") !=0 && \r\n" + 
			"         		operator.localeCompare(\"|\") !=0 && operator.localeCompare(\"||\") !=0 &&\r\n" + 
			"         		operator.localeCompare(\"or\") !=0 &&\r\n" + 
			"         		operator.localeCompare(\"and\") !=0 &&\r\n" + 
			"         		operator.localeCompare(\"OR\") !=0 &&\r\n" + 
			"         		operator.localeCompare(\"AND\") !=0 &&\r\n" + 
			"         		operator.localeCompare(\"XOR\") !=0 &&\r\n" + 
			"         		operator.localeCompare(\"xor\") !=0 && \r\n" + 
			"         		operator.localeCompare(\"^\") !=0 \r\n" + 
			"\r\n" + 
			"         		){\r\n" + 
			"         \r\n" + 
			"         		return (false);\r\n" + 
			"         	}\r\n" + 
			"         \r\n" + 
			"         	else{ return (true);}\r\n" + 
			"         }");
	
	out.println("//Prints the accumulated error message\r\n" + 
			"         function PrintErrorMsg(errormsg){\r\n" + 
			"         	alert(errormsg);\r\n" + 
			"         }");
	
	out.println("</script>");
	
	
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

} 