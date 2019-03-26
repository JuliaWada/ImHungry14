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
 * Servlet implementation class MoveToListData
 */
public class MoveToListData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MoveToListData() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session2 = request.getSession();
		PrintWriter out = response.getWriter();
		String action = request.getParameter("action").trim();
		String listName = request.getParameter("listName").trim();
		ResultList fromFront = (ResultList)session2.getAttribute(listName);
		String item = request.getParameter("itemName");
		String secondListName = request.getParameter("secondList");
		ResultList secondList = (ResultList) session2.getAttribute(secondListName);
		String type = request.getParameter("type");
		ArrayList<Object> grabbedList = fromFront.getCards();
		ArrayList<Recipe> recipeList = (ArrayList<Recipe>) session2.getAttribute("recipeList");
		ArrayList<Restaurant> restaurantList = (ArrayList<Restaurant>) session2.getAttribute("restaurantList");
		ArrayList<ResultList> addToSession = moveToList(grabbedList, item, fromFront, secondList);
		session2.setAttribute(listName, addToSession.get(0));
		session2.setAttribute(secondListName, addToSession.get(1));
	}

	/**
	 * This function moves on Object/item of whatever type to another list
	 * @param grabbedList the ArrayList<Object> that the item was originally on
	 * @param listName the String name of  the list that the item was originally on
	 * @param secondListName the String name of the list that the item is going to be moved to
	 * @param item the String name of the Item to be moved
	 * @param fromFront the full ResultList of the list the item was originally on
	 * @param secondList the full ResultList of the list the item will be moved to
	 * @return an ArrayList<ResultList> that returns the edited versions of both lists
	 */

	public ArrayList<ResultList> moveToList(ArrayList<Object> grabbedList, String item, ResultList fromFront, ResultList secondList) {
		System.out.println("Inside of move");
		System.out.println("Item: " + item);
		Recipe recipeToMove;
		Restaurant restaurantToMove;
		//need to grab the object that is being moved
		for(int i=0; i<grabbedList.size(); i++) {
			System.out.println("In: Move list: What type of class is " + i + ": " + grabbedList.get(i).getClass().getName());
			if(grabbedList.get(i).getClass().getName() == "scraping.Recipe") {
				recipeToMove = (Recipe)grabbedList.get(i);
				if(recipeToMove.getName().equals(item)) {
					fromFront.removeCard(item);
					secondList.addCard(recipeToMove);
					System.out.println("Item name: " + recipeToMove.getName());
					System.out.println("moved recipe");
				}
			} else {
				restaurantToMove = (Restaurant)grabbedList.get(i);
				System.out.println("Restaurant matching: " + restaurantToMove.getName());
				if(restaurantToMove.getName().equals(item)) {
					fromFront.removeCard(item);
					secondList.addCard(restaurantToMove);
					System.out.println("moved restaurant");
				}
			}
		}
		ArrayList<ResultList> toReturn = new ArrayList<>();
		toReturn.add(fromFront);
		toReturn.add(secondList);
		return toReturn;
	}
}
