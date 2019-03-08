import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.maps.errors.ApiException;

import scraping.Recipe;
import scraping.RecipeData;
import scraping.RecipeLinkScraper;
import yelp.Restaurant;
import yelp.restaurantData;

public class RecipeTestCase extends Mockito{

	@Test
	public void testOneNumResult() throws InterruptedException, IOException {
		ArrayList<Recipe> expectedList = new ArrayList<Recipe>();
		Recipe result1 = new Recipe();
		result1.setName("Ramen Coleslaw");
		result1.setPrep(15);
		result1.setPrepTime("15 mins");
		result1.setCookTime("10 mins");
		result1.setImageURL("https://images.media-allrecipes.com/userphotos/560x315/4445089.jpg");
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
		result1.setIngredients(ingredients);
		
		ArrayList<String> instructions = new ArrayList<String>();
		instructions.add("Preheat oven to 350 degrees F (175 degrees C).");
		instructions.add("In a medium bowl, whisk together the oil, vinegar, sugar, ramen noodle spice mix, salt and pepper to create a dressing.");
		instructions.add("Place sesame seeds and almonds in a single layer on a medium baking sheet. Bake in the preheated oven 10 minutes, or until lightly brown.");
		instructions.add("In a large salad bowl, combine the cabbage, green onions and crushed ramen noodles. Pour dressing over the cabbage, and toss to coat evenly. Top with toasted sesame seeds and almonds.");
		expectedList.add(result1);
		
		result1.setInstructions(instructions);
		
		RecipeLinkScraper scraper = new RecipeLinkScraper();
		ArrayList<Recipe> actualList = scraper.scrapeRecipeLinks("ramen", 1);
		System.out.println(expectedList.get(0).toString());
		System.out.println(actualList.get(0).toString());
		assertEquals(expectedList.size(), actualList.size());
		assertEquals(expectedList.get(0).toString(), actualList.get(0).toString());
	}
	
	@Test
	public void testTimeConversionHour() {
		RecipeLinkScraper scraper = new RecipeLinkScraper();
		int expected = 1290;
		int actual = scraper.convertTime("21 h 30 m");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testTimeConversionEmpty() {
		RecipeLinkScraper scraper = new RecipeLinkScraper();
		int expected = 1000;
		int actual = scraper.convertTime("");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetPrepInt() {
		Recipe result1 = new Recipe();
		result1.setName("Ramen Coleslaw");
		result1.setPrep(15);
		result1.setPrepTime("15 mins");
		result1.setCookTime("10 mins");
		result1.setImageURL("https://images.media-allrecipes.com/userphotos/560x315/4445089.jpg");
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
		result1.setIngredients(ingredients);
		
		ArrayList<String> instructions = new ArrayList<String>();
		instructions.add("Preheat oven to 350 degrees F (175 degrees C).");
		instructions.add("In a medium bowl, whisk together the oil, vinegar, sugar, ramen noodle spice mix, salt and pepper to create a dressing.");
		instructions.add("Place sesame seeds and almonds in a single layer on a medium baking sheet. Bake in the preheated oven 10 minutes, or until lightly brown.");
		instructions.add("In a large salad bowl, combine the cabbage, green onions and crushed ramen noodles. Pour dressing over the cabbage, and toss to coat evenly. Top with toasted sesame seeds and almonds.");		
		result1.setInstructions(instructions);
		
		assertEquals(15, result1.getPrep());
	}	
	
	private ArrayList<Recipe> initializeThree(ArrayList<Recipe> recipeResults) {
		ArrayList<String> ingredients = new ArrayList<String>();
		ArrayList<String> instructions = new ArrayList<String>();
		Recipe r = new Recipe("choco chip cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe r2 = new Recipe("sugar cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe r3 = new Recipe("lemon cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		recipeResults.add(r);
		recipeResults.add(r2);
		recipeResults.add(r3);
		
		return recipeResults;
	}
	private ArrayList<Recipe> initializeFive(ArrayList<Recipe> recipeResults) {
		ArrayList<String> ingredients = new ArrayList<String>();
		ArrayList<String> instructions = new ArrayList<String>();
		Recipe r = new Recipe("choco chip cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe r2 = new Recipe("sugar cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe r3 = new Recipe("lemon cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe r4 = new Recipe("oatmeal cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe r5 = new Recipe("peanut butter cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		recipeResults.add(r);
		recipeResults.add(r2);
		recipeResults.add(r3);
		recipeResults.add(r4);
		recipeResults.add(r5);
		
		return recipeResults;
	}
	
	
	@Test
	public void testCompareWithDNSBasic() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		ArrayList<Recipe> recipeResults = new ArrayList<Recipe>();
		ArrayList<Object> doNotShowList = new ArrayList<Object>();
		ArrayList<Recipe> resultList = new ArrayList<Recipe>();
		 
		recipeResults = initializeThree(recipeResults);
		
		ArrayList<String> ingredients = new ArrayList<String>();
		ArrayList<String> instructions = new ArrayList<String>();
		Recipe recipeResult = new Recipe("choco chip cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		doNotShowList.add(recipeResult);
		
		RecipeData recipeScraper = new RecipeData();
		resultList = recipeScraper.checkDoNotShow(recipeResults, doNotShowList);
		
		assertEquals(resultList.size(), 2);
		assertEquals(resultList.get(0).getName(), "sugar cookie");
		assertEquals(resultList.get(1).getName(), "lemon cookie");
	}
	
	@Test
	public void testWithEmptyDNS() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		ArrayList<Recipe> recipeResults = new ArrayList<Recipe>();
		ArrayList<Object> doNotShowList = new ArrayList<Object>();
		ArrayList<Recipe> resultList = new ArrayList<Recipe>();
		 
		recipeResults = initializeThree(recipeResults);

		RecipeData recipeScraper = new RecipeData();
		resultList = recipeScraper.checkDoNotShow(recipeResults, doNotShowList);
		
		assertEquals(resultList.size(), 3);
		assertEquals(resultList.get(0).getName(), "choco chip cookie");
		assertEquals(resultList.get(1).getName(), "sugar cookie");
		assertEquals(resultList.get(2).getName(), "lemon cookie");
	}
	
	@Test
	public void testDNSWithEmptyRest() {
		ArrayList<Recipe> recipeResults = new ArrayList<Recipe>();
		ArrayList<Object> doNotShowList = new ArrayList<Object>();
		ArrayList<Recipe> resultList = new ArrayList<Recipe>();
		
		ArrayList<String> ingredients = new ArrayList<String>();
		ArrayList<String> instructions = new ArrayList<String>();
		Recipe recipeResult = new Recipe("choco chip cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		doNotShowList.add(recipeResult);
		
		RecipeData recipeScraper = new RecipeData();
		resultList = recipeScraper.checkDoNotShow(recipeResults, doNotShowList);
		
		assertEquals(resultList.size(), 0);
	}
	
	@Test
	public void testCompareWithDNSRecipe() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		ArrayList<Recipe> recipeResults = new ArrayList<Recipe>();
		ArrayList<Object> doNotShowList = new ArrayList<Object>();
		ArrayList<Recipe> resultList = new ArrayList<Recipe>();
		 
		recipeResults = initializeThree(recipeResults);
		 
		ArrayList<String> ingredients = new ArrayList<String>();
		ArrayList<String> instructions = new ArrayList<String>();
		Recipe recipeResult = new Recipe("choco chip cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		doNotShowList.add(recipeResult);
		
		Restaurant restaurantResult = new Restaurant("Grace's Shack", "url", "address", "phone num", "price", 7);
		doNotShowList.add(restaurantResult);
		
		RecipeData recipeScraper = new RecipeData();
		resultList = recipeScraper.checkDoNotShow(recipeResults, doNotShowList);
		
		assertEquals(resultList.size(), 2);
		assertEquals(resultList.get(0).getName(), "sugar cookie");
		assertEquals(resultList.get(1).getName(), "lemon cookie");

	}
	
	@Test
	public void testCompareWithDNSMultipleItems() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		//DNS has: more than one item in the list, all in the restaurant array list
		ArrayList<Recipe> recipeResults = new ArrayList<Recipe>();
		ArrayList<Object> doNotShowList = new ArrayList<Object>();
		ArrayList<Recipe> resultList = new ArrayList<Recipe>();
		
		recipeResults = initializeFive(recipeResults);

		ArrayList<String> ingredients = new ArrayList<String>();
		ArrayList<String> instructions = new ArrayList<String>();
		Recipe recipeResult = new Recipe("choco chip cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult2 = new Recipe("sugar cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult3 = new Recipe("lemon cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		doNotShowList.add(recipeResult);
		doNotShowList.add(recipeResult2);
		doNotShowList.add(recipeResult3);
		
		RecipeData recipeScraper = new RecipeData();
		resultList = recipeScraper.checkDoNotShow(recipeResults, doNotShowList);
		
//		System.out.println("recipe results:");
//		for(int i=0; i<recipeResults.size(); i++) {
//			System.out.println("         " + recipeResults.get(i).getName());
//		}
//		System.out.println();
//		System.out.println("result list:");
//		for(int i=0; i<resultList.size(); i++) {
//			System.out.println("         " + resultList.get(i).getName());
//		}
		
		
		assertEquals(resultList.size(), 2);
		assertEquals(resultList.get(0).getName(), "oatmeal cookie");
		assertEquals(resultList.get(1).getName(), "peanut butter cookie");

	}
	
	@Test
	public void testCompareWithDNSAllSameItems() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		//DNS has: more than one item in the list, all items in list
		
		ArrayList<Recipe> recipeResults = new ArrayList<Recipe>();
		ArrayList<Object> doNotShowList = new ArrayList<Object>();
		ArrayList<Recipe> resultList = new ArrayList<Recipe>();
		
		recipeResults = initializeFive(recipeResults);
		
		ArrayList<String> ingredients = new ArrayList<String>();
		ArrayList<String> instructions = new ArrayList<String>();
		Recipe recipeResult = new Recipe("choco chip cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult2 = new Recipe("sugar cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult3 = new Recipe("lemon cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult4 = new Recipe("oatmeal cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult5 = new Recipe("peanut butter cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		doNotShowList.add(recipeResult);
		doNotShowList.add(recipeResult2);
		doNotShowList.add(recipeResult3);
		doNotShowList.add(recipeResult4);
		doNotShowList.add(recipeResult5);
	
	
		RecipeData recipeScraper = new RecipeData();
		resultList = recipeScraper.checkDoNotShow(recipeResults, doNotShowList);
		
		assertEquals(resultList.size(), 0);
	}
	
	@Test
	public void testCompareWithDNSDiffItems() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		//DNS has: more than one item in the list, some in list, some not
		
		ArrayList<Recipe> recipeResults = new ArrayList<Recipe>();
		ArrayList<Object> doNotShowList = new ArrayList<Object>();
		ArrayList<Recipe> resultList = new ArrayList<Recipe>();
		
		recipeResults = initializeFive(recipeResults);
		
		ArrayList<String> ingredients = new ArrayList<String>();
		ArrayList<String> instructions = new ArrayList<String>();
		Recipe recipeResult = new Recipe("choco chip cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult2 = new Recipe("poop cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult3 = new Recipe("lemon cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult4 = new Recipe("poop2 cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		doNotShowList.add(recipeResult);
		doNotShowList.add(recipeResult2);
		doNotShowList.add(recipeResult3);
		doNotShowList.add(recipeResult4);
		
		RecipeData recipeScraper = new RecipeData();
		resultList = recipeScraper.checkDoNotShow(recipeResults, doNotShowList);
		
		// choco chip, sugar, lemon, oatmeal, peanut butter
		// sugar, oatmeal, pb
		
		assertEquals(resultList.size(), 3);
		assertEquals(resultList.get(0).getName(), "sugar cookie");
		assertEquals(resultList.get(1).getName(), "oatmeal cookie");
		assertEquals(resultList.get(2).getName(), "peanut butter cookie");

	}
	
	@Test
	public void testCompareWithDNSAllDiffItems() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		//DNS has: more than one item in the list, all items not in list
		
		ArrayList<Recipe> recipeResults = new ArrayList<Recipe>();
		ArrayList<Object> doNotShowList = new ArrayList<Object>();
		ArrayList<Recipe> resultList = new ArrayList<Recipe>();
		
		recipeResults = initializeThree(recipeResults);
		
		ArrayList<String> ingredients = new ArrayList<String>();
		ArrayList<String> instructions = new ArrayList<String>();
		Recipe recipeResult = new Recipe("poop cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult2 = new Recipe("poop2 cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult3 = new Recipe("poop3 cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		doNotShowList.add(recipeResult);
		doNotShowList.add(recipeResult2);
		doNotShowList.add(recipeResult3);
		
		RecipeData recipeScraper = new RecipeData();
		resultList = recipeScraper.checkDoNotShow(recipeResults, doNotShowList);
		
		assertEquals(resultList.size(), 3);
		assertEquals(resultList.get(0).getName(), "choco chip cookie");
		assertEquals(resultList.get(1).getName(), "sugar cookie");
		assertEquals(resultList.get(2).getName(), "lemon cookie");

	}
	
	@Test
	public void testCompareWithFavesBasic() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		ArrayList<Recipe> recipeResults = new ArrayList<Recipe>();
		ArrayList<Object> favorites = new ArrayList<Object>();
		ArrayList<Recipe> resultList = new ArrayList<Recipe>();
		
		recipeResults = initializeThree(recipeResults);
		
		ArrayList<String> ingredients = new ArrayList<String>();
		ArrayList<String> instructions = new ArrayList<String>();
		Recipe recipeResult = new Recipe("lemon cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		favorites.add(recipeResult);
		
		RecipeData recipeScraper = new RecipeData();
		resultList = recipeScraper.checkFavorites(recipeResults, favorites);
		
		assertEquals(resultList.size(), 3);
		assertEquals(resultList.get(0).getName(), "lemon cookie");
		assertEquals(resultList.get(1).getName(), "choco chip cookie");
		assertEquals(resultList.get(2).getName(), "sugar cookie");

	}
	
	@Test
	public void testCompareWithFavesMultipleItems() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		//Faves list has: more than one item in the list, all in the restaurant array list
		
		ArrayList<Recipe> recipeResults = new ArrayList<Recipe>();
		ArrayList<Object> favorites = new ArrayList<Object>();
		ArrayList<Recipe> resultList = new ArrayList<Recipe>();
		
		recipeResults = initializeFive(recipeResults);
		
		ArrayList<String> ingredients = new ArrayList<String>();
		ArrayList<String> instructions = new ArrayList<String>();
		Recipe recipeResult = new Recipe("lemon cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult2 = new Recipe("peanut butter cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		favorites.add(recipeResult);
		favorites.add(recipeResult2);
		
		RecipeData recipeScraper = new RecipeData();
		resultList = recipeScraper.checkFavorites(recipeResults, favorites);
		
		assertEquals(resultList.size(), 5);
		assertEquals(resultList.get(0).getName(), "lemon cookie");
		assertEquals(resultList.get(1).getName(), "peanut butter cookie");
		assertEquals(resultList.get(2).getName(), "choco chip cookie");
		assertEquals(resultList.get(3).getName(), "sugar cookie");
		assertEquals(resultList.get(4).getName(), "oatmeal cookie");
		
	}
	
	@Test
	public void testCompareWithFavesAllSameItems() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		//DNS has: more than one item in the list, all items in list
		
		ArrayList<Recipe> recipeResults = new ArrayList<Recipe>();
		ArrayList<Object> favorites = new ArrayList<Object>();
		ArrayList<Recipe> resultList = new ArrayList<Recipe>();
		
		recipeResults = initializeFive(recipeResults);
		
		ArrayList<String> ingredients = new ArrayList<String>();
		ArrayList<String> instructions = new ArrayList<String>();
		Recipe recipeResult = new Recipe("choco chip cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult2 = new Recipe("sugar cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult3 = new Recipe("lemon cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult4 = new Recipe("oatmeal cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult5 = new Recipe("peanut butter cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		favorites.add(recipeResult);
		favorites.add(recipeResult2);
		favorites.add(recipeResult3);
		favorites.add(recipeResult4);
		favorites.add(recipeResult5);
		
		RecipeData recipeScraper = new RecipeData();
		resultList = recipeScraper.checkFavorites(recipeResults, favorites);
		
		assertEquals(resultList.size(), 5);
		assertEquals(resultList.get(0).getName(), "choco chip cookie");
		assertEquals(resultList.get(1).getName(), "sugar cookie");
		assertEquals(resultList.get(2).getName(), "lemon cookie");
		assertEquals(resultList.get(3).getName(), "oatmeal cookie");
		assertEquals(resultList.get(4).getName(), "peanut butter cookie");
		
		
	}
	
	@Test
	public void testCompareWithDiffItems() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		//Faves list has: more than one item in the list, some in list, some not
		
		ArrayList<Recipe> recipeResults = new ArrayList<Recipe>();
		ArrayList<Object> favorites = new ArrayList<Object>();
		ArrayList<Recipe> resultList = new ArrayList<Recipe>();
		
		recipeResults = initializeFive(recipeResults);
		
		ArrayList<String> ingredients = new ArrayList<String>();
		ArrayList<String> instructions = new ArrayList<String>();
		Recipe recipeResult = new Recipe("poo cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult2 = new Recipe("poo2 cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult3 = new Recipe("poo3 cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult4 = new Recipe("oatmeal cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		favorites.add(recipeResult);
		favorites.add(recipeResult2);
		favorites.add(recipeResult3);
		favorites.add(recipeResult4);

		
		RecipeData recipeScraper = new RecipeData();
		resultList = recipeScraper.checkFavorites(recipeResults, favorites);
		
		assertEquals(resultList.size(), 5);
		assertEquals(resultList.get(0).getName(), "oatmeal cookie");
		assertEquals(resultList.get(1).getName(), "choco chip cookie");
		assertEquals(resultList.get(2).getName(), "sugar cookie");
		assertEquals(resultList.get(3).getName(), "lemon cookie");
		assertEquals(resultList.get(4).getName(), "peanut butter cookie");
		
	}
	
	@Test
	public void testCompareWithAllDiffItems() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		//Faves list has: more than one item in the list, all items no in list
		
		ArrayList<Recipe> recipeResults = new ArrayList<Recipe>();
		ArrayList<Object> favorites = new ArrayList<Object>();
		ArrayList<Recipe> resultList = new ArrayList<Recipe>();
		
		recipeResults = initializeThree(recipeResults);
		
		ArrayList<String> ingredients = new ArrayList<String>();
		ArrayList<String> instructions = new ArrayList<String>();
		Recipe recipeResult = new Recipe("poo cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult2 = new Recipe("poo2 cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult3 = new Recipe("poo3 cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult4 = new Recipe("poo4 cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		favorites.add(recipeResult);
		favorites.add(recipeResult2);
		favorites.add(recipeResult3);
		favorites.add(recipeResult4);

		RecipeData recipeScraper = new RecipeData();
		resultList = recipeScraper.checkFavorites(recipeResults, favorites);
		
		assertEquals(resultList.size(), 3);
		assertEquals(resultList.get(0).getName(), "choco chip cookie");
		assertEquals(resultList.get(1).getName(), "sugar cookie");
		assertEquals(resultList.get(2).getName(), "lemon cookie");
		
	}
	
	@Test
	public void testWithEmptyFaves() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		ArrayList<Recipe> recipeResults = new ArrayList<Recipe>();
		ArrayList<Object> favorites = new ArrayList<Object>();
		ArrayList<Recipe> resultList = new ArrayList<Recipe>();
		
		recipeResults = initializeThree(recipeResults);
		
		RecipeData recipeScraper = new RecipeData();
		resultList = recipeScraper.checkFavorites(recipeResults, favorites);
		
		assertEquals(resultList.size(), 3);
		assertEquals(resultList.get(0).getName(), "choco chip cookie");
		assertEquals(resultList.get(1).getName(), "sugar cookie");
		assertEquals(resultList.get(2).getName(), "lemon cookie");
		
	}
	
	@Test
	public void testFavesWithEmptyRest() {
		ArrayList<Recipe> recipeResults = new ArrayList<Recipe>();
		ArrayList<Object> favorites = new ArrayList<Object>();
		ArrayList<Recipe> resultList = new ArrayList<Recipe>();
		
		ArrayList<String> ingredients = new ArrayList<String>();
		ArrayList<String> instructions = new ArrayList<String>();
		Recipe recipeResult = new Recipe("choco chip cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult2 = new Recipe("sugar cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		Recipe recipeResult3 = new Recipe("lemon cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		favorites.add(recipeResult);
		favorites.add(recipeResult2);
		favorites.add(recipeResult3);
		
		RecipeData recipeScraper = new RecipeData();
		resultList = recipeScraper.checkFavorites(recipeResults, favorites);
		
		assertEquals(resultList.size(), 0);
		
	}
	
	@Test
	public void testCompareWithFavesRecipe() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		ArrayList<Recipe> recipeResults = new ArrayList<Recipe>();
		ArrayList<Object> favorites = new ArrayList<Object>();
		ArrayList<Recipe> resultList = new ArrayList<Recipe>();
		
		recipeResults = initializeThree(recipeResults);
		 
		ArrayList<String> ingredients = new ArrayList<String>();
		ArrayList<String> instructions = new ArrayList<String>();
		Recipe recipeResult = new Recipe("lemon cookie", "url", "preptime", 7, "cooktime", ingredients, instructions);
		favorites.add(recipeResult);

		Restaurant restaurantResult = new Restaurant("Grace's Shack", "url", "address", "phone num", "price", 7);
		favorites.add(restaurantResult);
		
		RecipeData recipeScraper = new RecipeData();
		resultList = recipeScraper.checkFavorites(recipeResults, favorites);
		
		assertEquals(resultList.size(), 3);
		assertEquals(resultList.get(0).getName(), "lemon cookie");
		assertEquals(resultList.get(1).getName(), "choco chip cookie");
		assertEquals(resultList.get(2).getName(), "sugar cookie");

	}
	
	
	
	

}
