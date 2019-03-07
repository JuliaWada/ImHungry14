package scraping;



import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;

import listMgmt.ResultList;
import yelp.Restaurant;

/**
 * Servlet implementation class CollageData
 */
@WebServlet("/collageData")
public class CollageData extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CollageData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 * activated by a xhttp request from the front end
	 * If it has the variable extra defined as "Setting session" then it will set up the session by loading the necessary data into it.
	 * This means it will set the attributes for the search term, num of wanted results, the total memory for every restaurant, and recipe, and the 3 predefined lists
	 * If it doesn't have the varialbe extra defined then it will get the Collage by calling the CollageScraper class to return the required links
	 * It then takes those strings and makes it so that they sent back as html to the front end with the degrees defined to satisfy the collage requirement
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Random random = new Random();
		int num = 0;
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		String extra = request.getParameter("extra");
		if(extra == null || extra.equals("")) {
			extra = "getCollage";
		}
		String query = request.getParameter("query").trim();
		System.out.println("Original query: " + query);
		String numResults = request.getParameter("numResults");
		System.out.println("Original numResults: " + numResults);
		if(extra.equals("settingVariables")) { //setting the search term/query, num of wanted results, 
			System.out.println("Setting session");
			HttpSession session2 = request.getSession(true);
			session2.setAttribute("query", query);
			System.out.println("Query: " + session2.getAttribute("query"));
			session2.setAttribute("numResults", numResults);
			System.out.println("NumResults: " + session2.getAttribute("numResults"));
			
			Map< String, Recipe> recipesMap =  new HashMap< String,Recipe>();
			Map<String, Restaurant> restaurantsMap = new HashMap<String, Restaurant>();
			session2.setAttribute("recipeMap", recipesMap);
			session2.setAttribute("restaurantMap", restaurantsMap);
			//for current search results
			ArrayList<Recipe> recipes = new ArrayList<>();
			ArrayList<Restaurant> restaurants = new ArrayList<>();
			session2.setAttribute("recipeList", recipes);
			session2.setAttribute("restaurantList", restaurants);
			
			// 3 predefined lists
			ResultList favorites = new ResultList();
			favorites.setName("Favorites");
			session2.setAttribute("Favorites", favorites);
			ResultList toExplore = new ResultList();
			toExplore.setName("To Explore");
			session2.setAttribute("To Explore", toExplore);
			ResultList doNotShow = new ResultList();
			doNotShow.setName("Do Not Show");
			session2.setAttribute("Do Not Show", doNotShow);
			
		} else {
			CollageScraper scraper = new CollageScraper();
			ArrayList<String> collageResults = scraper.scrapeCollage(query);
			for(int i =0; i<collageResults.size(); i++) {
				System.out.println("Unescaped: " + collageResults.get(i));
				System.out.println("Escaped: " + StringEscapeUtils.unescapeJava(collageResults.get(i)));
				String unescaped = StringEscapeUtils.unescapeJava(collageResults.get(i));

				num = random.nextInt(91) - 45;
				out.println("<img class=\"collageImg\" src=" + unescaped +
						" style=\"transform:rotate(" + num + "deg)\">" );
			}
		}

	}
}
