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
	
	public ResultList decider(String action, ResultList fromFront, String item, String type, String listName, ArrayList<Recipe> recipeList, ArrayList<Restaurant> restaurantList) throws IOException {
		ResultList addToSession = new ResultList();
		ArrayList<Object> grabbedList = fromFront.getCards();
		if(action.equals("remove")) {
			addToSession = removeFromList(grabbedList, listName, item, fromFront);
		} 
		else if(action.equals("add")) {
			
			addToSession = addToList(grabbedList, listName, item, type, recipeList, restaurantList);
		}
		return addToSession;
	}
	
	/**
	 * 
	 * @param grabbedList
	 * @param listName
	 * @param item
	 * @param fromFront
	 * @return
	 */
	public ResultList removeFromList(ArrayList<Object> grabbedList, String listName,  String item, ResultList fromFront) {
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
		return fromFront;
	}
	
	/**
	 * 
	 * @param grabbedList
	 * @param item
	 * @param type
	 * @param recipeList
	 * @param restaurantList
	 * @return 
	 */
	public ResultList addToList(ArrayList<Object> grabbedList, String listName, String item, String type,
			ArrayList<Recipe> recipeList, ArrayList<Restaurant>restaurantList) {
		ResultList toReturn = new ResultList();
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
		System.out.println("Adding new list size: " + grabbedList.size());
		toReturn.setCards(grabbedList);
		toReturn.setName(listName);
		return toReturn;
	}
	
}
