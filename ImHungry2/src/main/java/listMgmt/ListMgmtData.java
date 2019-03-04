package listMgmt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import scraping.Recipe;
import scraping.Result;
import yelp.Restaurant;

/**
 * Servlet implementation class ListMgmtData
 */
@WebServlet("/listMgmtData")
public class ListMgmtData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListMgmtData() {
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
		System.out.println("Inside of ListMgmtData");
		String action = request.getParameter("action").trim();
		String listName = request.getParameter("listName").trim();
		if(action.equals("loadList")) {
			//Creating Data for now because I don't want to mess with results page
			ArrayList<String> instructions = new ArrayList<String>();
			instructions.add("1) Get cookie dough");
			instructions.add("2) Eat the cookie dough with chocolate chips");
			ArrayList<String> ingredients = new ArrayList<String>();
			ingredients.add("cookie dough");
			ingredients.add("chocoloate chips");
			Recipe r1 = new Recipe("Cookie dough " + listName , "pie", "15 min", "30 mins", ingredients, instructions);
			Recipe r2 = new Recipe("Chocolate chips " + listName, "chocolate", "20 mins", "45 mins", ingredients, instructions);
			ArrayList<Object> toAdd = new ArrayList<Object>();
			toAdd.add(r1);
			toAdd.add(r2);
			ResultList favorites = new ResultList("Favorites", toAdd);
			ResultList doNotShow = new ResultList("Do Not Show", toAdd);
			ResultList toExplore = new ResultList("To Explore", toAdd);
			session2.setAttribute("Favorites", favorites);
			session2.setAttribute("To Explore", toExplore);
			session2.setAttribute("Do Not Show", doNotShow);
			ResultList fromFront = (ResultList)session2.getAttribute(listName);
			ArrayList<Object> grabbedList = fromFront.getCards();
	//End of creating testing data
			System.out.println("Grabbed list: " + fromFront.getName());
			Recipe recipeToDisplay;
			Restaurant restaurantToDisplay;
			for(int i=0; i<grabbedList.size(); i++) {
				System.out.println("What type of class is " + i + ": " + grabbedList.get(i).getClass().getName());
				if(grabbedList.get(i).getClass().getName() == "scraping.Recipe") {
					recipeToDisplay = (Recipe)grabbedList.get(i);
					out.println("<div>" + 
									"<div>" + 
										"<p class=\"name\">" + recipeToDisplay.getName() + "</p>" + 
										"<p> Prep Time: " + recipeToDisplay.getPrepTime() + "</p>" +
										"<p> Cook Time: " + recipeToDisplay.getCookTime() + "</p>" +
										"<button class=\"removeButton\" onclick=\"removeFromList(this)\">Remove from List</button>" +
										"<select class = \"menu\" id=\"moveListOptions\">\r\n" + 
										"				 <option value = \"0\"> </option>\r\n" + 
										"				 <option value=\"1\">Favorites</option>\r\n" + 
										"   				 <option value=\"2\">To Explore</option>\r\n" + 
										"   				 <option value=\"3\">Do Not Show</option>\r\n" + 
										"			</select>" +
										"<button class=\"moveButton\" onclick=\"moveToList(this)\">Move to List</button>" +
									"</div>" +
								"</div>"
					+ "");
				} else {
					restaurantToDisplay = (Restaurant)grabbedList.get(i); 
					out.println("<div>" + 
									"<div>" +
										"<p class=\"name\">" + restaurantToDisplay.getName() + "</p>" + 
										"<p> Prep Time: " + restaurantToDisplay.getAddress() + "</p>" +
										"<p> Cook Time: " + restaurantToDisplay.getPricing() + "</p>" +
									"</div>" +
									"<div>" +
										"<button class=\"removeButton\" onclick=\"removeFromList(this)\">Remove From List</button>" +
									"<select class = \"menu\" id=\"moveListOptions\">\r\n" + 
									"				 <option value = \"0\"> </option>\r\n" + 
									"				 <option value=\"1\">Favorites</option>\r\n" + 
									"   				 <option value=\"2\">To Explore</option>\r\n" + 
									"   				 <option value=\"3\">Do Not Show</option>\r\n" + 
									"			</select>" +
										"<button class=\"moveButton\" onclick=\"moveToList(this)\">Move to List</button>" +
									"</div>" +
								"</div>"
					+ "");
				}
			}
		}
		else if(action.equals("remove")) {
			ResultList fromFront = (ResultList)session2.getAttribute(listName);
			ArrayList<Object> grabbedList = fromFront.getCards();
			String item = request.getParameter("itemName");
			System.out.println("Item to be removed: " + item);
			Recipe recipeToRemove;
			Restaurant restaurantToRemove;
			for(int i=0; i<grabbedList.size(); i++) {
				System.out.println("getting item: " + i);
				if(grabbedList.get(i).getClass().getName() == "scraping.Recipe") {
					recipeToRemove = (Recipe)grabbedList.get(i);
					System.out.println(recipeToRemove.getName());
					if(recipeToRemove.getName().equals(item)) {
						fromFront.removeCard(item);
						System.out.println("Removed it");
					}
				}
				else {
					restaurantToRemove = (Restaurant)grabbedList.get(i);
					if(restaurantToRemove.getName().equals(item)) {
						fromFront.removeCard(item);
					}
				}
			}
			ArrayList<Object> removed = fromFront.getCards();
			System.out.println("Size of new list: " + removed.size());
	
			session2.setAttribute(listName, fromFront);
			out.println("Done");
		} 
		else if(action.equals("move")) {
			ResultList fromFront = (ResultList)session2.getAttribute(listName);
			ArrayList<Object> grabbedList = fromFront.getCards();
			
			String secondListName = request.getParameter("secondList");
			String item = request.getParameter("itemName");
			System.out.println("Inside of move");
			System.out.println("Second list: " + secondListName);
			Recipe recipeToMove;
			Restaurant restaurantToMove;
			ResultList secondList = (ResultList)session2.getAttribute(secondListName);
			//need to grab the object that is being moved
			for(int i=0; i<grabbedList.size(); i++) {
				System.out.println("What type of class is " + i + ": " + grabbedList.get(i).getClass().getName());
				if(grabbedList.get(i).getClass().getName() == "scraping.Recipe") {
					recipeToMove = (Recipe)grabbedList.get(i);
					fromFront.removeCard(item);
					secondList.addCard(recipeToMove);
					System.out.println("^^^^firstList size: " + fromFront.getCards().size());
					System.out.println("****secondlist size: " + secondList.getCards().size());
					System.out.println("Item name: " + recipeToMove.getName());
					System.out.println("moved recipe");
				} else {
					restaurantToMove = (Restaurant)grabbedList.get(i);
					fromFront.removeCard(item);
					secondList.addCard(restaurantToMove);
					System.out.println("moved restaurant");
				}
			}
			session2.setAttribute(listName, fromFront);
			session2.setAttribute(secondListName, secondList);
			out.println("done");
			//add to the next list
			//remove from the other and reload
		}
		else if(action.equals("reloadList")) {
			ResultList fromFront = (ResultList)session2.getAttribute(listName);
			ArrayList<Object> grabbedList = fromFront.getCards();
	//End of creating testing data
			System.out.println("Grabbed list: " + fromFront.getName());
			Recipe recipeToDisplay;
			Restaurant restaurantToDisplay;
			for(int i=0; i<grabbedList.size(); i++) {
				System.out.println("What type of class is " + i + ": " + grabbedList.get(i).getClass().getName());
				if(grabbedList.get(i).getClass().getName() == "scraping.Recipe") {
					recipeToDisplay = (Recipe)grabbedList.get(i);
					out.println("<div>" + 
									"<div>" + 
										"<p class=\"name\">" + recipeToDisplay.getName() + "</p>" + 
										"<p> Prep Time: " + recipeToDisplay.getPrepTime() + "</p>" +
										"<p> Cook Time: " + recipeToDisplay.getCookTime() + "</p>" +
										"<select class = \"menu\" id=\"moveListOptions\">\r\n" + 
												"				 <option value = \"0\"> </option>\r\n" + 
												"				 <option value=\"1\">Favorites</option>\r\n" + 
												"   				 <option value=\"2\">To Explore</option>\r\n" + 
												"   				 <option value=\"3\">Do Not Show</option>\r\n" + 
												"			</select>" +
										"<button class=\"removeButton\" onclick=\"removeFromList(this)\">Remove from List</button>" +
										"<button class=\"moveButton\" onclick=\"moveToList(this)\">Move to List</button>" +
									"</div>" +
								"</div>"
					+ "");
				} else {
					restaurantToDisplay = (Restaurant)grabbedList.get(i); 
					out.println("<div>" + 
									"<div>" +
										"<p class=\"name\">" + restaurantToDisplay.getName() + "</p>" + 
										"<p> Prep Time: " + restaurantToDisplay.getAddress() + "</p>" +
										"<p> Cook Time: " + restaurantToDisplay.getPricing() + "</p>" +
									"</div>" +
									"<div>" +
									"<select class = \"menu\" id=\"moveListOptions\">\r\n" + 
									"				 <option value = \"0\"> </option>\r\n" + 
									"				 <option value=\"1\">Favorites</option>\r\n" + 
									"   				 <option value=\"2\">To Explore</option>\r\n" + 
									"   				 <option value=\"3\">Do Not Show</option>\r\n" + 
									"			</select>" + 
										"<button class=\"removeButton\" onclick=\"removeFromList(this)\">Remove From List</button>" +
										"<button class=\"moveButton\" onclick=\"moveToList(this)\">Move to List</button>" +
									"</div>" +
								"</div>"
					+ "");
				}
			}
		}
		System.out.println("List name: " + listName);
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
