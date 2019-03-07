package listMgmt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import scraping.Recipe;
import yelp.Restaurant;

/**
 * Servlet implementation class LoadList
 */
public class LoadList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session2 = request.getSession();
		PrintWriter out = response.getWriter();
		String listName = request.getParameter("listName");
		ResultList fromFront = (ResultList)session2.getAttribute(listName);
		loadList(out, listName, fromFront);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param listName
	 * @param fromFront
	 * @throws IOException
	 */
	public void loadList(PrintWriter out, String listName, ResultList fromFront) throws IOException {
		ArrayList<Object> grabbedList = fromFront.getCards();
		System.out.println("Grabbed list: " + fromFront.getName());
		Recipe recipeToDisplay;
		Restaurant restaurantToDisplay;
		for(int i=0; i<grabbedList.size(); i++) {
			System.out.println("What type of class is " + i + ": " + grabbedList.get(i).getClass().getName());
			if(grabbedList.get(i).getClass().getName() == "scraping.Recipe") {
				recipeToDisplay = (Recipe)grabbedList.get(i);
				out.println("<div class = \"row\">" + 
						"<div class=\"recipeCard\" onclick = \"toRecipePage(this)\">" + 
						"<p class=\"name\">" + recipeToDisplay.getName() + "</p>" + 
						"<p> Prep Time: " + recipeToDisplay.getPrepTime() + "</p>" +
						"<p> Cook Time: " + recipeToDisplay.getCookTime() + "</p>" +
					"</div>" +
					"<div class =\"buttons\">" +
						"<button class=\"removeButton\" onclick=\"removeFromList(this)\" name=\"" + recipeToDisplay.getName() + "\">Remove from List</button>" +
						"<select class = \"menu\" id=\"moveListOptions\">\r\n" + 
						"				 <option value = \"0\"> </option>\r\n" + 
						"				 <option value=\"1\">Favorites</option>\r\n" + 
						"   				 <option value=\"2\">To Explore</option>\r\n" + 
						"   				 <option value=\"3\">Do Not Show</option>\r\n" + 
						"			</select>" +
						"<button class=\"moveButton\" onclick=\"moveToList(this)\" name=\"" + recipeToDisplay.getName() + "\">Move to List</button>" +
					"</div>" +
				"</div>"
				+ "");
			} else {
				restaurantToDisplay = (Restaurant)grabbedList.get(i); 
				out.println("<div class =\"row\">" + 
						"<div class =\"restaurantCard\" onclick = \"toRestaurantPage(this)\" \"id=\"restaurant\">\r\n" + 
						"        					<div class =\"information\">\r\n" + 
						"		    					<p class = \"restName\">" + restaurantToDisplay.getName() + "</p>\r\n" + 
						"		    					<p>" + restaurantToDisplay.getAddress() + "</p>\r\n" + 
						"		    					<p>" + restaurantToDisplay.getMinsAway() + " minutes away </p>\r\n" + 
						"		    				</div>\r\n" + 
						"        					<div class =\"priceDiv\">\r\n" + 
						"        						<p>" + restaurantToDisplay.getPricing() + "</p>\r\n" + 
						"        					</div>\r\n" + 
						"        			 </div>" +
					"<divclass =\"buttons\">" +
						"<button class=\"removeButton\" onclick=\"removeFromList(this)\" name=\"" + restaurantToDisplay.getName() + "\">Remove From List</button>" +
					"<select class = \"menu\" id=\"moveListOptions\">\r\n" + 
					"				 <option value = \"0\"> </option>\r\n" + 
					"				 <option value=\"1\">Favorites</option>\r\n" + 
					"   				 <option value=\"2\">To Explore</option>\r\n" + 
					"   				 <option value=\"3\">Do Not Show</option>\r\n" + 
					"			</select>" +
						"<button class=\"moveButton\" onclick=\"moveToList(this)\" name=\"" + restaurantToDisplay.getName() + "\">Move to List</button>" +
					"</div>" +
				"</div>"
				+ "");
			}
		}
	}

}
