import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.Mockito;

import listMgmt.ResultList;
import scraping.CollageData;
import scraping.Recipe;
import scraping.RecipeData;
import scraping.RecipeLinkScraper;
import yelp.Restaurant;

public class ServletTestCase extends Mockito {
	
	@Test
	public void testCollageData() throws ServletException, IOException {
		HttpServletRequest request = mock(HttpServletRequest.class);       
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    
	    when(request.getParameter("extra")).thenReturn("");
	    when(request.getParameter("query")).thenReturn("cake");
	    
	    StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(response.getContentType()).thenReturn("text/html");

        new CollageData().service(request, response);
        
        verify(request, atLeast(1)).getParameter("extra"); // only if you want to verify username was called...
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().contains(""));
        
        verify(request, atLeast(1)).getParameter("query");
        writer.flush();
        assertTrue(stringWriter.toString().contains("cake"));
        assertEquals("text/html", response.getContentType());
	}
	
	@Test
	public void testSettingVariables() throws ServletException, IOException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		when(request.getParameter("extra")).thenReturn("settingVariables");
		when(request.getParameter("query")).thenReturn("ramen");
		when(request.getParameter("numResults")).thenReturn("5");
		when(request.getSession(true)).thenReturn(session);
		
		new scraping.CollageData().service(request, response);
		
//		assertEquals("ramen", session.getAttribute("query"));
//		assertEquals("5", session.getAttribute("numResults"));
//		assertEquals(0, ((ArrayList<String>) session.getAttribute("recipeList")).size());
//		assertEquals(0, ((ArrayList<String>) session.getAttribute("restaruantList")).size());
	}

	@Test
	public void testRecipeDataResult() throws IOException, ServletException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		when(request.getParameter("action")).thenReturn("results");
		when(request.getParameter("query")).thenReturn("ramen");
		when(request.getParameter("numResults")).thenReturn("5");
		when(request.getSession()).thenReturn(session);
		
		ArrayList<Recipe> recipes = new ArrayList<>();
		when(session.getAttribute("recipeList")).thenReturn(recipes);
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		when(response.getWriter()).thenReturn(printWriter);
        when(response.getContentType()).thenReturn("text/html");
        
		new scraping.RecipeData().service(request, response);
		
		verify(request, atLeast(1)).getParameter("query");
		printWriter.flush();
		System.out.println("Stringwriter: " + stringWriter.toString());
		System.out.flush();
		assertEquals("<div class =\"recipeCard\" id=\"recipe0\"><p class=\"recipeTitle\">Ramen Cabbage Salad</p><p> Prep Time: </p><p> Cook Time: </p></div>\r\n" +     
		"<div class =\"recipeCard\" id=\"recipe1\"><p class=\"recipeTitle\">Ramen Coleslaw</p><p> Prep Time: 15 mins</p><p> Cook Time: 10 mins</p></div>\r\n" +
		"<div class =\"recipeCard\" id=\"recipe2\"><p class=\"recipeTitle\">Broccoli and Ramen Noodle Salad</p><p> Prep Time: 15 mins</p><p> Cook Time: </p></div>\r\n" +
		"<div class =\"recipeCard\" id=\"recipe3\"><p class=\"recipeTitle\">Ramen Slaw</p><p> Prep Time: 15 mins</p><p> Cook Time: </p></div>\r\n" +
		"<div class =\"recipeCard\" id=\"recipe4\"><p class=\"recipeTitle\">Ramen Burger</p><p> Prep Time: 20 mins</p><p> Cook Time: 20 mins</p></div>\r\n",
		stringWriter.toString());
	}
	
	@Test
	public void testRecipeDataPage() throws IOException, ServletException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		when(request.getParameter("action")).thenReturn("page");
		when(request.getParameter("query")).thenReturn("Ramen Coleslaw");
		when(request.getSession()).thenReturn(session);
		ArrayList<Recipe> recipes = new ArrayList<>();
		ArrayList<String> ingredients = new ArrayList<String>();
		ingredients.add("2 tablespoons vegetable oil");
		ingredients.add("3 tablespoons white wine vinegar");
		ingredients.add("2 tablespoons white sugar");
		ingredients.add("1 (3 ounce) package chicken flavored ramen noodles, crushed, seasoning packet reserved");
		ingredients.add("1/2 teaspoon salt");
		ingredients.add("1/2 teaspoon ground black pepper");
		ingredients.add("2 tablespoons sesame seeds");
		ingredients.add("1/4 cup sliced almonds");
		ingredients.add("1/2 medium head cabbage, shredded");
		ingredients.add("5 green onions, chopped");
		
		ArrayList<String> instructions = new ArrayList<String>();
		instructions.add("Preheat oven to 350 degrees F (175 degrees C).");
		instructions.add("In a medium bowl, whisk together the oil, vinegar, sugar, ramen noodle spice mix, salt and pepper to create a dressing.");
		instructions.add("Place sesame seeds and almonds in a single layer on a medium baking sheet. Bake in the preheated oven 10 minutes, or until lightly brown.");
		instructions.add("In a large salad bowl, combine the cabbage, green onions and crushed ramen noodles. Pour dressing over the cabbage, and toss to coat evenly. Top with toasted sesame seeds and almonds.");
		recipes.add(new Recipe("Ramen Coleslaw", "https://images.media-allrecipes.com/userphotos/560x315/4445089.jpg", "15 mins", 15, "10 mins", ingredients, instructions));
		when(session.getAttribute("recipeList")).thenReturn(recipes);
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		when(response.getWriter()).thenReturn(printWriter);
        when(response.getContentType()).thenReturn("text/html");
        
        new scraping.RecipeData().service(request, response);
        verify(request, atLeast(1)).getParameter("query");
		printWriter.flush();
		System.out.println("Stringwriter: " + stringWriter.toString());
		System.out.flush();
		assertEquals("<h1>Ramen Coleslaw</h1><img src=\"https://images.media-allrecipes.com/userphotos/560x315/4445089.jpg><p>Prep Time: <span id=\"prepTime\">15 mins</span></p><br><p>Cook Time: <span id=\"cookTime\">10 mins</span></p><br><h3>Ingredients:</h3><ul id=\"ingredientsList\"><li>2 tablespoons vegetable oil</li><li>3 tablespoons white wine vinegar</li><li>2 tablespoons white sugar</li><li>1 (3 ounce) package chicken flavored ramen noodles, crushed, seasoning packet reserved</li><li>1/2 teaspoon salt</li><li>1/2 teaspoon ground black pepper</li><li>2 tablespoons sesame seeds</li><li>1/4 cup sliced almonds</li><li>1/2 medium head cabbage, shredded</li><li>5 green onions, chopped</li></ul>\r\n" + 
				"<br><h3>Instructions:</h3><ul id=\"instructionsList\"><li>2 tablespoons vegetable oil</li><li>3 tablespoons white wine vinegar</li><li>2 tablespoons white sugar</li><li>1 (3 ounce) package chicken flavored ramen noodles, crushed, seasoning packet reserved</li><li>1/2 teaspoon salt</li><li>1/2 teaspoon ground black pepper</li><li>2 tablespoons sesame seeds</li><li>1/4 cup sliced almonds</li><li>1/2 medium head cabbage, shredded</li><li>5 green onions, chopped</li></ul>\r\n"
				, stringWriter.toString());
	}
	
//	@Test
//	//(expected = InterruptedException.class)
//	public void testRecipeDataInterruptedException() throws IOException, ServletException, InterruptedException {
//		HttpServletRequest request = mock(HttpServletRequest.class);
//		HttpServletResponse response = mock(HttpServletResponse.class);
//		HttpSession session = mock(HttpSession.class);
//		when(request.getParameter("action")).thenReturn("page");
//		when(request.getParameter("query")).thenReturn("Ramen Coleslaw");
//		when(request.getSession()).thenReturn(session);
//		
//		StringWriter stringWriter = new StringWriter();
//		PrintWriter printWriter = new PrintWriter(stringWriter);
//		when(response.getWriter()).thenReturn(printWriter);
//        when(response.getContentType()).thenReturn("text/html");
//		RecipeData recipeData = mock(RecipeData.class);
//		RecipeLinkScraper scraper = mock(RecipeLinkScraper.class);
////		doThrow(new InterruptedException("Error thrown"))
////		.when(recipeData)
////		.displayResults(request, response, printWriter, "ramen", 5);
////		doThrow(new InterruptedException("Error thrown"))
////		.when(recipeData)
////		.service(request, response);
//		doThrow(new InterruptedException("Error thrown"))
//		.when(scraper)
//		.scrapeRecipeLinks("ramen", 5);
//		
////		
//////		recipeData.displayResults(request, response, printWriter, "ramen", 5);
////		recipeData.service(request, response);
//		scraper.scrapeRecipeLinks("ramen", 5);
//		
//	}
	
	@Test
	public void testLoadListData() throws IOException, ServletException {
		ResultList testList = new ResultList();
		ResultList secondTestList = new ResultList();
		testList.setName("resultList");
		secondTestList.setName("secondList");
		ArrayList<Object> testCards = new ArrayList<>();
		ArrayList<Object> secondTestCards = new ArrayList<>();
		ArrayList<String> ingredients = new ArrayList<String>();
		ingredients.add("2 tablespoons vegetable oil");
		ingredients.add("3 tablespoons white wine vinegar");
		ingredients.add("2 tablespoons white sugar");
		ingredients.add("1 (3 ounce) package chicken flavored ramen noodles, crushed, seasoning packet reserved");
		ingredients.add("1/2 teaspoon salt");
		ingredients.add("1/2 teaspoon ground black pepper");
		ingredients.add("2 tablespoons sesame seeds");
		ingredients.add("1/4 cup sliced almonds");
		ingredients.add("1/2 medium head cabbage, shredded");
		ingredients.add("5 green onions, chopped");
		
		ArrayList<String> instructions = new ArrayList<String>();
		instructions.add("Preheat oven to 350 degrees F (175 degrees C).");
		instructions.add("In a medium bowl, whisk together the oil, vinegar, sugar, ramen noodle spice mix, salt and pepper to create a dressing.");
		instructions.add("Place sesame seeds and almonds in a single layer on a medium baking sheet. Bake in the preheated oven 10 minutes, or until lightly brown.");
		instructions.add("In a large salad bowl, combine the cabbage, green onions and crushed ramen noodles. Pour dressing over the cabbage, and toss to coat evenly. Top with toasted sesame seeds and almonds.");
		Recipe expectedRecipe = new Recipe("Ramen Coleslaw", "https://images.media-allrecipes.com/userphotos/560x315/4445089.jpg", "15 mins", 15, "10 mins", ingredients, instructions);
		Recipe extraRecipe = new Recipe("copy1", "https://images.media-allrecipes.com/userphotos/560x315/4445089.jpg", "15 mins", 15, "10 mins", ingredients, instructions);
		Recipe extraRecipe2 = new Recipe("copy2", "https://images.media-allrecipes.com/userphotos/560x315/4445089.jpg", "15 mins", 15, "10 mins", ingredients, instructions);
		Recipe secondRecipe = new Recipe("secondRecipe", "https://images.media-allrecipes.com/userphotos/560x315/4445089.jpg", "15 mins", 15, "10 mins", ingredients, instructions);
		Recipe secondRecipe2 = new Recipe("secondRecipe2", "https://images.media-allrecipes.com/userphotos/560x315/4445089.jpg", "15 mins", 15, "10 mins", ingredients, instructions);
		Restaurant testRestaurant = new Restaurant("Ramen Kenjo", "website", "address", "phoneNumber", "$$$", 15);
		testCards.add(expectedRecipe);
		testCards.add(extraRecipe);
		testCards.add(extraRecipe2);
		testCards.add(testRestaurant);
		testList.setCards(testCards);
		secondTestCards.add(secondRecipe2);
		secondTestCards.add(secondRecipe);
		secondTestList.setCards(secondTestCards);
		assertEquals(4, testList.getCards().size());
		assertEquals(2, secondTestList.getCards().size());
		//servlet initiation
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		when(request.getParameter("listName")).thenReturn("testList");
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("testList")).thenReturn(testList);

		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		when(response.getWriter()).thenReturn(printWriter);
        when(response.getContentType()).thenReturn("text/html");
        new listMgmt.LoadList().service(request, response);
        verify(request, atLeast(1)).getParameter("listName");
		printWriter.flush();
		System.out.println("Stringwriter: " + stringWriter.toString());
		System.out.flush();
		assertEquals("<div class = \"row\"><div class=\"recipeCard\"><p class=\"name\">Ramen Coleslaw</p><p> Prep Time: 15 mins</p><p> Cook Time: 10 mins</p></div><div class =\"buttons\"><button class=\"removeButton\" onclick=\"removeFromList(this)\">Remove from List</button><select class = \"menu\" id=\"moveListOptions\">\r\n" + 
				"				 <option value = \"0\"> </option>\r\n" + 
				"				 <option value=\"1\">Favorites</option>\r\n" + 
				"   				 <option value=\"2\">To Explore</option>\r\n" + 
				"   				 <option value=\"3\">Do Not Show</option>\r\n" + 
				"			</select><button class=\"moveButton\" onclick=\"moveToList(this)\">Move to List</button></div></div>\r\n" + 
				"<div class = \"row\"><div class=\"recipeCard\"><p class=\"name\">copy1</p><p> Prep Time: 15 mins</p><p> Cook Time: 10 mins</p></div><div class =\"buttons\"><button class=\"removeButton\" onclick=\"removeFromList(this)\">Remove from List</button><select class = \"menu\" id=\"moveListOptions\">\r\n" + 
				"				 <option value = \"0\"> </option>\r\n" + 
				"				 <option value=\"1\">Favorites</option>\r\n" + 
				"   				 <option value=\"2\">To Explore</option>\r\n" + 
				"   				 <option value=\"3\">Do Not Show</option>\r\n" + 
				"			</select><button class=\"moveButton\" onclick=\"moveToList(this)\">Move to List</button></div></div>\r\n" + 
				"<div class = \"row\"><div class=\"recipeCard\"><p class=\"name\">copy2</p><p> Prep Time: 15 mins</p><p> Cook Time: 10 mins</p></div><div class =\"buttons\"><button class=\"removeButton\" onclick=\"removeFromList(this)\">Remove from List</button><select class = \"menu\" id=\"moveListOptions\">\r\n" + 
				"				 <option value = \"0\"> </option>\r\n" + 
				"				 <option value=\"1\">Favorites</option>\r\n" + 
				"   				 <option value=\"2\">To Explore</option>\r\n" + 
				"   				 <option value=\"3\">Do Not Show</option>\r\n" + 
				"			</select><button class=\"moveButton\" onclick=\"moveToList(this)\">Move to List</button></div></div>\r\n" + 
				"<div class =\"row\"><div class =\"restaurantCard\"><p class=\"name\">Ramen Kenjo</p><p> Prep Time: address</p><p> Cook Time: $$$</p></div><divclass =\"buttons\"><button class=\"removeButton\" onclick=\"removeFromList(this)\">Remove From List</button><select class = \"menu\" id=\"moveListOptions\">\r\n" + 
				"				 <option value = \"0\"> </option>\r\n" + 
				"				 <option value=\"1\">Favorites</option>\r\n" + 
				"   				 <option value=\"2\">To Explore</option>\r\n" + 
				"   				 <option value=\"3\">Do Not Show</option>\r\n" + 
				"			</select><button class=\"moveButton\" onclick=\"moveToList(this)\">Move to List</button></div></div>\r\n", stringWriter.toString());
	}


}
