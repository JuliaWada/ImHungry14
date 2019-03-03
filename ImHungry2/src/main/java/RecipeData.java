

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RecipeData
 */
@WebServlet("/RecipeData")
public class RecipeData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecipeData() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Inside of RecipeData");
		
		PrintWriter out = response.getWriter();
		
		response.setContentType("text/html");
		String query = request.getParameter("query").trim();
		int numResults = Integer.parseInt(request.getParameter("numResults").trim());
		System.out.println("Query: " + query);
		System.out.println("Num of Results: " + numResults);
		RecipeLinkScraper scraper = new RecipeLinkScraper();
		ArrayList<Recipe> recipeResults = scraper.scrapeRecipeLinks(query, numResults);
		for(int i = 0; i < recipeResults.size(); i++) {
			Recipe toFormat = recipeResults.get(i);
			//TODO remove this and put in the actual code 
			//for now making sure everything works
			out.println("<div>" +
							"<p class=\"recipeTitle\">" + toFormat.getName() + "</p>" +
							"<img src=\"" + toFormat.getImageURL() + 
							"\"style=\"max-height: 50px\">" + 
							"<p> Prep Time: " + toFormat.getPrepTime() + "</p>" +
							"<p> Cook Time: " + toFormat.getCookTime() + "</p>" +
							"</div>"
							);
		}
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
