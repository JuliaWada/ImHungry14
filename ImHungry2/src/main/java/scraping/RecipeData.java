package scraping;



import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;

import listMgmt.ResultList;


/**
 * Servlet implementation class RecipeData
 */

@WebServlet("/recipeData")
public class RecipeData extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecipeData() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     *
     */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Inside of RecipeData");

		PrintWriter out = response.getWriter();

		response.setContentType("text/html");
		String action = request.getParameter("action");
		String query = request.getParameter("query").trim();
		if(action.equals("results")) {
			int numResults = Integer.parseInt(request.getParameter("numResults").trim());
			System.out.println("Query: " + query);
			System.out.println("Num of Results: " + numResults);

			HttpSession session = request.getSession();
			ResultList doNotShow = (ResultList) session.getAttribute("Do Not Show");
			ArrayList<Object> doNotShowList = doNotShow.getCards();
			ResultList favs = (ResultList) session.getAttribute("Favorites");
			ArrayList<Object> favorites = favs.getCards();

			displayResults(request, response, out, query, numResults, doNotShowList, favorites);



		} else if (action.equals("page")) {
			displayPage(request, response, out, query);
		}
	}

    /**
     * This grabs the necessary data to display the cards on the results page
     *
     * @param request
     * @param response
     * @param out
     * @param query
     * @param numResults
     * @throws IOException
     * @throws InterruptedException
     */
    public void displayResults(HttpServletRequest request, HttpServletResponse response, PrintWriter out, String query, int numResults, ArrayList<Object> doNotShowList,
    		ArrayList<Object> favorites) throws IOException{
    	RecipeLinkScraper scraper = new RecipeLinkScraper();
		ArrayList<Recipe> recipeResults;
		recipeResults = scraper.scrapeRecipeLinks(query, numResults);
		recipeResults = checkDoNotShow(recipeResults, doNotShowList);
		recipeResults = checkFavorites(recipeResults, favorites);
		for(int i = 0; i < recipeResults.size(); i++) {
			Recipe toFormat = recipeResults.get(i);
			//TODO remove this and put in the actual code
			//for now making sure everything works 
	

			out.println("<div class =\"recipeCard\" onclick = \"toRecipePage(this)\" id=\"recipe" + i + "\">" +
							"<p class=\"recipeTitle\">" + toFormat.getName() + "</p>" +

							"<p> Prep Time: " + toFormat.getPrepTime() + "</p>" +
							"<p> Cook Time: " + toFormat.getCookTime() + "</p>" +
							"</div>"
							);
		}
		HttpSession session2 = request.getSession();
    	ArrayList<Recipe> stored = (ArrayList<Recipe>) session2.getAttribute("recipeList");
    	for(int i=0; i<recipeResults.size(); i++) {
			stored.add(recipeResults.get(i));
		}
		session2.setAttribute("recipeList", stored);
    }

    /**
	 *
	 * @param recipeResults
	 * @param doNotShowList
	 *
	 * This function checks the returned recipeResults with the ArrayList of restaurants in
	 * the "Do Not Show" list. It then deletes any recipe in the recipe Array.
	 *
	 *
	 * @return recipeResults
	 */
    public ArrayList<Recipe> checkDoNotShow(ArrayList<Recipe> recipeResults, ArrayList<Object> doNotShowList) {
    	ArrayList<Recipe> editedArray = new ArrayList<Recipe>();

//		System.out.println("recipe array size: " + recipeResults.size());
//		System.out.println("Do Not Show array size: " + doNotShowList.size());

		if(recipeResults.size() != 0 && doNotShowList.size() !=0) {									//array list is not empty
			for(int i=0; i<recipeResults.size(); i++) {
				int matchFound = 0;											//flag
				String recipeName = recipeResults.get(i).getName();
				//System.out.println("recipe array address: " + recipeName);
				for(int j=0; j<doNotShowList.size(); j++) {
					if(doNotShowList.get(j).getClass().getName().equals("scraping.Recipe")) {
						Recipe DNSrecipe = (Recipe)doNotShowList.get(j);
						String dontShowRecipeName = DNSrecipe.getName();
						//System.out.println("DNS array image: " + dontShowRecipeImage);
						if(recipeName.equals(dontShowRecipeName)) {		//compare addresses
							matchFound = 1;
							//System.out.println("Match found.");
						}
						//System.out.println("UNO");
					}
					//System.out.println("DOS");
				}
				//System.out.println("TRES");
				if(matchFound == 0) {										//not in "Do Not Show" list. Add to the array list.
					editedArray.add(recipeResults.get(i));
					//System.out.println("Adding to list, no match found:" + recipeResults.get(i).getName());
				}
				//System.out.println();
			}
		}
		else {
			//System.out.println("Nothing to compare to.");
			return recipeResults;
		}

		//System.out.println("Returning the edited array. Size: " + editedArray.size());
		return editedArray;
	}


    /**
	 *
	 * @param recipeResults
	 * @param favorites
	 *
	 * This function checks the returned recipeResults with the ArrayList of recipes in
	 * the "Favorites" list. It then moves any matching recipes to the front of the array list.
	 *
	 * @return recipeResults
	 */
	public ArrayList<Recipe> checkFavorites(ArrayList<Recipe> recipeResults, ArrayList<Object> favorites) {
		ArrayList<Recipe> editedArray = new ArrayList<Recipe>();
		ArrayList<Recipe> notInList = new ArrayList<Recipe>();

//		System.out.println("recipe array size: " + recipeResults.size());
//		System.out.println("recipe array size: " + favorites.size());

		if(recipeResults.size() != 0 && favorites.size() != 0) {									//array list is not empty
			for(int i=0; i<recipeResults.size(); i++) {
				int matchFound = 0;											//flag
				String recipeArrayName = recipeResults.get(i).getName();
				//System.out.println("recipe array image: " + recipeArrayName);
				for(int j=0; j<favorites.size(); j++) {
					if(favorites.get(j).getClass().getName().equals("scraping.Recipe")) {
						Recipe favesRecipe = (Recipe)favorites.get(j);
						String favesName = favesRecipe.getName();
						//System.out.println("Favorites array image: " + favesName);
						if(recipeArrayName.equals(favesName)) {		//compare addresses
							matchFound = 1;
							//System.out.println("Match found.");
						}
					}
				}
				if(matchFound == 1) {										//in the "Favorites" list. Add to front of the array list.
					editedArray.add(recipeResults.get(i));
					//System.out.println("Adding to front of edited list:" + recipeResults.get(i).getName());
				}
				else {
					notInList.add(recipeResults.get(i));
					//System.out.println("Adding to rest of list");
				}
			}
		}
		else {
			//System.out.println("Nothing to compare to.");
			return recipeResults;
		}

		editedArray.addAll(notInList);
		//System.out.println("Returning the edited array");
		return editedArray;
	}


	/**
     * This grabs the necessary information and formats it to be sent to the front end for the recipe page
     * @param request
     * @param response
     * @param out
     * @param query
     */
    public void displayPage(HttpServletRequest request, HttpServletResponse response, PrintWriter out, String query) {
    	HttpSession session = request.getSession();
//    	Map<String, Recipe> stored = (Map<String, Recipe>) session.getAttribute("recipeList");
//
//    	for(String key : stored.keySet()) {
//
//    	}
    	ArrayList<Recipe> stored = (ArrayList<Recipe>) session.getAttribute("recipeList");
    	Recipe toDisplay = new Recipe();
    	for(int i=0; i<stored.size(); i++) {
    		if(stored.get(i).getName().equals(query)) {
    			toDisplay = stored.get(i);
    		}
    	}
    	System.out.println(toDisplay.getImageURL());
    	ArrayList<String> ingredients = toDisplay.getIngredients();
    	ArrayList<String> instructions = toDisplay.getInstructions();
    	out.print("<h1>" + toDisplay.getName() + "</h1>" +
    				"<img src=\"" + StringEscapeUtils.unescapeJava(toDisplay.getImageURL()) + "\">" +
    				"<p>Prep Time: <span id=\"prepTime\">" + toDisplay.getPrepTime() + "</span></p>" +
    				"<br>" +
    				"<p>Cook Time: <span id=\"cookTime\">" + toDisplay.getCookTime() + "</span></p>" +
    				"<br>" +
    				"<h3>Ingredients:</h3>" +
    				"<ul id=\"ingredientsList\">"
    			);
    	for(int i=0; i<ingredients.size(); i++) {
    		out.print(
    				"<li>" + ingredients.get(i) + "</li>"
    				);
    	}
    	out.println("</ul>");
    	out.print("<br>" +
    			"<h3>Instructions:</h3>" +
    			"<ul id=\"instructionsList\">" );
    	for(int i=0; i<instructions.size(); i++) {
    		out.print(
    				"<li>" + (i+1) + ") " + instructions.get(i) + "</li>"
    				);
    	}
    	out.println("</ul>");
    }
}
