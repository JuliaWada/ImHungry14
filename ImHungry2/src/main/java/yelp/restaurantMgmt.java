package yelp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;

import scraping.Recipe;

/**
 * Servlet implementation class restaurantMgmt
 */
public class restaurantMgmt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public restaurantMgmt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		String query = request.getParameter("query").trim();
		displayPage(request, response, out, query);
	}
	
	void displayPage(HttpServletRequest request, HttpServletResponse response, PrintWriter out, String query) {
		HttpSession session = request.getSession();
		ArrayList<Restaurant> stored = (ArrayList<Restaurant>) session.getAttribute("restaurantList");
    	Restaurant toDisplay = new Restaurant();
    	System.out.println(query);
    	System.out.println(StringEscapeUtils.unescapeJava(query));
    	for(int i=0; i<stored.size(); i++) {
    		System.out.println(stored.get(i).getName());
    		if(stored.get(i).getName().equals(StringEscapeUtils.unescapeJava(query))) {
    			toDisplay = stored.get(i);
    		}
    	}
    	String cleanAddress = toDisplay.getAddress();
    	cleanAddress.replaceAll(" ", "+");
    	
    	out.print("<h1>" + toDisplay.getName() + "</h1>" +
    			"<p> Phone Number: " + toDisplay.getPhoneNum() + "</p>" +
    			"<a href = \"https://www.google.com/maps/dir/Tommy+Trojan/"+ cleanAddress + "\"> Address: " + toDisplay.getAddress() + "</p>" +
    			"<p class =\"blankPee\">  </p>" +
    			"<div id = \"webDiv\">" +
    			"<a class =\"website\" href ="+ toDisplay.getWebsite() +"> Website: " + toDisplay.getWebsite() + "</a>" +
    			"</div>"
    	);
	}

}
