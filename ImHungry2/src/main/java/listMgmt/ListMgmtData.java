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
	 * grabs the necessary variables from the Session 
	 * then it calls the decider function to decide what action to take in regards to List management
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Inside of ListMgmtData");
		HttpSession session2 = request.getSession();
		PrintWriter out = response.getWriter();
		String action = request.getParameter("action").trim();
		String listName = request.getParameter("listName").trim();
		ResultList fromFront = (ResultList)session2.getAttribute(listName);
		String item = request.getParameter("itemName");
		String type = request.getParameter("type");
		ArrayList<Object> grabbedList = fromFront.getCards();
		ArrayList<Recipe> recipeList = (ArrayList<Recipe>) session2.getAttribute("recipeList");
		ArrayList<Restaurant> restaurantList = (ArrayList<Restaurant>) session2.getAttribute("restaurantList");
		ResultList toAdd = decider(action, fromFront, item, type, listName, recipeList, restaurantList);
	}
	
	/**
	 * This function decides whether or not to add or remove something from a list
	 * Action is defined in the service request to get the right action and decide what is happening
	 * It then calls the appropriate function for the action specified
	 * remove -> removeFromList
	 * add -> addToList
	 * @param action a String that represents whats we want the servlet to do (add or remove)
	 * @param fromFront a ResultList that represents the primary List that is to edited
	 * @param item a String that represents what item is to be added or removed
	 * @param type a type that represents what kind of item it is
	 * @param listName a ListName to keep track of what list we are using
	 * @param recipeList a ArrayList that contains all searched recipes
	 * @param restaurantList an ArrayList that contains all searched restaurants
	 * @return
	 * @throws IOException
	 */
	public ResultList decider(String action, ResultList fromFront, String item, String type, String listName, ArrayList<Recipe> recipeList, ArrayList<Restaurant> restaurantList) throws IOException {
		ResultList addToSession = new ResultList();
		addToSession.setName(listName);
		ArrayList<Object> grabbedList = fromFront.getCards();
		if(action.equals("remove")) {
			addToSession = removeFromList(grabbedList, listName, item, fromFront);
		} 
		else if(action.equals("add")) {
			System.out.println("Adding to List: " + item);
			addToSession = addToList(grabbedList, listName, item, type, recipeList, restaurantList);
		}
		return addToSession;
	}
	
	/**
	 * It removes the item from the List and returns the final list to be read to the session
	 * it must match the name to do so 
	 * @param grabbedList the ArrayList<Object> that we are removing from
	 * @param listName the name of the grabbedList in String
	 * @param item the String name of the item to be removed
	 * @param fromFront the full REsultList that is associated with thte grabbedList
	 * @return the final ResultList that contains the edited list
	 */
	public ResultList removeFromList(ArrayList<Object> grabbedList, String listName,  String item, ResultList fromFront) {
		System.out.println("Item to be removed: " + item);
		Recipe recipeToRemove;
		Restaurant restaurantToRemove;
		for(int i=0; i<grabbedList.size(); i++) {
//			System.out.println("getting item: " + i);
			if(grabbedList.get(i).getClass().getName() == "scraping.Recipe") {
				recipeToRemove = (Recipe)grabbedList.get(i);
//				System.out.println(recipeToRemove.getName());
				if(recipeToRemove.getName().equals(item)) {
					fromFront.removeCard(item);
//					System.out.println("Removed it");
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
		return fromFront;
	}
	
	/**
	 * It adds an item to the defined ResultList, which can be one of the three predefined lists
	 * Each item needs to be determined if it is a recipe or restaurant, it searches the session and gets the full object to put into the memory
	 * It then returns the final list to be added to the session
	 * @param grabbedList the ArrayList<Object> that is to be edited
	 * @param item the String name of the item to be added
	 * @param type the String representation of what type the item is
	 * @param recipeList the ArrayList<Recipe> that represents all searched recipes
	 * @param restaurantList the ArrayList<Restaurants> that represents all searched restaurants
	 * @return the final edited ResultList to be returned
	 */
	public ResultList addToList(ArrayList<Object> grabbedList, String listName, String item, String type,
			ArrayList<Recipe> recipeList, ArrayList<Restaurant>restaurantList) {
		ResultList toReturn = new ResultList();
		System.out.println("Adding old list size: " + grabbedList.size());
		boolean exists = false;
		for(int i=0; i<grabbedList.size(); i++ ) {
			if(((Result) grabbedList.get(i)).getName().equals(item)) {
				exists = true;
			}					
		}
		if(!exists) {
			System.out.println("Inside of add");
			if(type.equals("recipe")) {
				Recipe toAdd = new Recipe();
				for(int i=0; i<recipeList.size(); i++) {
					if(recipeList.get(i).getName().equals(item)) {
						toAdd = recipeList.get(i);
					}
				}
				grabbedList.add(toAdd);
			} else {
				Restaurant toAdd = new Restaurant();
				for(int i=0; i<restaurantList.size(); i++) {
					if(restaurantList.get(i).getName().equals(item)) {
						toAdd = restaurantList.get(i);
					}
				}
				grabbedList.add(toAdd);			
			}
		}
		System.out.println("Adding new list size: " + grabbedList.size());
		toReturn.setCards(grabbedList);
		toReturn.setName(listName);
		return toReturn;
	}
	
}
