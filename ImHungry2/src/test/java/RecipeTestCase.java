import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import scraping.Recipe;
import scraping.RecipeLinkScraper;

public class RecipeTestCase {

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

}
