package scraping;



import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			try {
				displayResults(request, response, out, query, numResults);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (action.equals("page")) {
			displayPage(request, response, out, query);
		}
	}
    
    /**
     * 
     * @param request
     * @param response
     * @param out
     * @param query
     * @param numResults
     * @throws IOException
     * @throws InterruptedException
     */
    public void displayResults(HttpServletRequest request, HttpServletResponse response, PrintWriter out, String query, int numResults) throws IOException, InterruptedException{
    	RecipeLinkScraper scraper = new RecipeLinkScraper();
		ArrayList<Recipe> recipeResults;
		recipeResults = scraper.scrapeRecipeLinks(query, numResults);
		for(int i = 0; i < recipeResults.size(); i++) {
			Recipe toFormat = recipeResults.get(i);
			//TODO remove this and put in the actual code 
			//for now making sure everything works
			out.println("<div class =\"recipeCard\" onclick = \"toRecipePage(\""+toFormat.getName().trim()+"\")\" id=\"recipe" + i + "\">" +  
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
     * @param request
     * @param response
     * @param out
     * @param query
     */
    public void displayPage(HttpServletRequest request, HttpServletResponse response, PrintWriter out, String query) {
    	HttpSession session = request.getSession();
    	ArrayList<Recipe> stored = (ArrayList<Recipe>) session.getAttribute("recipeList");
    	Recipe toDisplay = new Recipe();
    	for(int i=0; i<stored.size(); i++) {
    		if(stored.get(i).getName().equals(query)) {
    			toDisplay = stored.get(i);
    		}
    	}
    	ArrayList<String> ingredients = toDisplay.getIngredients();
    	ArrayList<String> instructions = toDisplay.getInstructions();
    	out.print("<h1>" + toDisplay.getName() + "</h1>" + 
    				"<img src=\"" + toDisplay.getImageURL() + ">" + 
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
    				"<li>" + instructions.get(i) + "</li>"
    				);
    	}
    	out.println("</ul>");
    }
}
