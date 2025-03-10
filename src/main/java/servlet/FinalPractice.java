/** *****************************************************************
    FinalPractice.java

        @author Peter Hadeed
********************************************************************* */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  

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
@WebServlet( name = "FinalPractice", urlPatterns = {"/FinalPractice"} )


public class FinalPractice extends HttpServlet
{

	  static enum Data {PREDICATE};
	  static String RESOURCE_FILE = "predicates.json";

	  static String Domain  = "";
	  static String Path    = "/";
	  static String Servlet = "FinalPractice";
	  
	

	
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
	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/logicHandler2");
	dispatcher.forward(request, response);

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
   out.println("<title>Peter Hadeed - SWE 432 - FinalPractice</title>");
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
   out.println("<h1>Peter Hadeed - SWE 432 - Final Practice</h1>");
   out.println("</div>");
   
   out.println("<ul>");
   out.println("<li><a href=\"http://mason.gmu.edu/~phadeed/\">Home</a></li>");
   out.println("<li><a href=\"http://mason.gmu.edu/~phadeed/Assignment_3.html\">Assignment 3</a></li>");
   out.println("<li><a href=\"https://swe432-hadeed.herokuapp.com/Assignment5\">Assignment 5</a></li>");
   out.println("<li><a href=\"https://swe432-hadeed.herokuapp.com/Assignment7\">Assignment 7</a></li>");
   out.println("<li><a href=\"https://swe432-hadeed.herokuapp.com/Assignment8\">Assignment 8</a></li>");
   out.println("<li><a href=\"https://codesandbox.io/s/youthful-fermi-v4mfz?file=/src/index.js\">Assignment 9</a></li>");
   out.println("<li><a href=\"https://swe432-hadeed.herokuapp.com/FinalPractice\" class = current>Final Practice</a></li>");
   out.println("<li><a href=\"https://swe432-hadeed.herokuapp.com/FinalExam\">Final Exam</a></li>");
   out.println("</ul>");
   
   
   
}

/** *****************************************************
 *  Prints the <BODY> of the HTML page with the form data
 *  values from the parameters.
********************************************************* */
private void PrintBody (PrintWriter out)
{

   out.println("<body>");
   out.println("<div class=content>");
   out.println("<div class=header>");
   out.println("<h1>Peter Hadeed - SWE 432 - Final Practice</h1>");
   out.println("</div>");
   
   out.println("<ul>");
   out.println("<li><a href=\"http://mason.gmu.edu/~phadeed/\">Home</a></li>");
   out.println("<li><a href=\"http://mason.gmu.edu/~phadeed/Assignment_3.html\">Assignment 3</a></li>");
   out.println("<li><a href=\"https://swe432-hadeed.herokuapp.com/Assignment5\">Assignment 5</a></li>");
   out.println("<li><a href=\"https://swe432-hadeed.herokuapp.com/Assignment7\">Assignment 7</a></li>");
   out.println("<li><a href=\"https://swe432-hadeed.herokuapp.com/Assignment8\">Assignment 8</a></li>");
   out.println("<li><a href=\"https://codesandbox.io/s/youthful-fermi-v4mfz?file=/src/index.js\">Assignment 9</a></li>");
   out.println("<li><a href=\"https://swe432-hadeed.herokuapp.com/FinalPractice\" class = current>Final Practice</a></li>");
   out.println("<li><a href=\"https://swe432-hadeed.herokuapp.com/FinalExam\">Final Exam</a></li>");
   out.println("</ul>");
   
   
   //Form print
   out.println("<div class=form_title>");
   out.println("<h1> Three String Form </h1></div>");
   
   out.println("<div class=form>");
   out.println("<form  name=\"StringForm\" ");
   out.println("onSubmit=\" return(CheckString())\">");
   
   out.println("<table><tr><td> String 1</td><td><input type = \"text\" name=\"String1\"></td></tr> ");
   out.println("<tr><td> String 2</td><td><input type = \"text\" name=\"String2\"></td></tr> ");
   out.println("<tr><td> String 3</td><td><input type = \"text\" name=\"String3\"></td></tr> ");
   out.println("<tr><td> Separator</td><td><input type = \"text\" name=\"Separator\"></td></tr> ");
   out.println("<tr><td><input type=\"radio\" id=\"Forward\" name=\"StringType\" value=\"Forward\" checked>\n" + 
   		"<label for=\"Forward\">Forward</label> </td><td> <input type=\"radio\" id=\"Reverse\" name=\"StringType\" value=\"Reverse\">\n" + 
   		"<label for=\"Reverse\">Reverse</label></td>");
   out.println("<tr><td colspan=2 align=middle><input type=\"submit\" value=\"Submit\">");
   out.println("</table></form></div>");
  
   
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
	
	out.println("function CheckString(){\r\n" + 
			"	var string1 = document.StringForm.String1.value;\r\n" + 
			"	var string2 = document.StringForm.String2.value;\r\n" + 
			"	var string3 = document.StringForm.String3.value;\r\n" + 
			"	var separator = document.StringForm.Separator.value;\r\n" + 
			"\r\n" + 
			"	var forward = document.getElementById(\"Forward\").checked;\r\n" + 
			"\r\n" + 
			"	var finalString = \" \";\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"	if (separator == \"\"){\r\n" + 
			"		separator = \" \";\r\n" + 
			"	}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"	if(forward){\r\n" + 
			"		finalString = finalString.concat(string1);\r\n" + 
			"		finalString = finalString.concat(separator);\r\n" + 
			"		finalString = finalString.concat(string2);\r\n" + 
			"		finalString = finalString.concat(separator);\r\n" + 
			"		finalString = finalString.concat(string3);\r\n" + 
			"	}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"	else{\r\n" + 
			"		finalString = finalString.concat(string1.split(\"\").reverse().join(\"\"));\r\n" + 
			"		finalString = finalString.concat(separator);\r\n" + 
			"		finalString = finalString.concat(string2.split(\"\").reverse().join(\"\"));\r\n" + 
			"		finalString = finalString.concat(separator);\r\n" + 
			"		finalString = finalString.concat(string3.split(\"\").reverse().join(\"\"));\r\n" + 
			"	}\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"	alert(finalString);\r\n" + 
			"	\r\n" + 
			"	return(true);\r\n" +
			"}");
	
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