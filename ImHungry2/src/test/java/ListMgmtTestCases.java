import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import listMgmt.ListMgmtData;
import listMgmt.MoveToListData;
import listMgmt.ResultList;
import scraping.Recipe;
import yelp.Restaurant;

public class ListMgmtTestCases {

	@Test 
	public void testBasicGetItem() {
		ResultList testList = new ResultList();
		testList.setName("resultList");
		ArrayList<Object> testCards = new ArrayList<>();
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
		testCards.add(expectedRecipe);
		testCards.add(extraRecipe);
		testCards.add(extraRecipe2);
		testList.setCards(testCards);
		
		Recipe actual = (Recipe) testList.getItem("Ramen Coleslaw");
		assertEquals(expectedRecipe.getName(), actual.getName());
		assertEquals(expectedRecipe.getPrepTime(), actual.getPrepTime());
		assertEquals(expectedRecipe.getCookTime(), actual.getCookTime());
	}

	@Test 
	public void testBasicRemoveItem() {
		ResultList testList = new ResultList();
		testList.setName("resultList");
		ArrayList<Object> testCards = new ArrayList<>();
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
		testCards.add(expectedRecipe);
		testCards.add(extraRecipe);
		testCards.add(extraRecipe2);
		testList.setCards(testCards);
		
		testList.removeCard("copy1");
		ArrayList<Object> actual = testList.getCards();
		assertEquals(2, actual.size());
		assertEquals("Ramen Coleslaw", ((Recipe) actual.get(0)).getName());
		assertEquals("copy2", ((Recipe) actual.get(1)).getName());
	}

	@Test
	public void testBasicAddItem() {
		ResultList testList = new ResultList();
		testList.setName("resultList");
		ArrayList<Object> testCards = new ArrayList<>();
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
		testCards.add(expectedRecipe);
		testCards.add(extraRecipe);
		testList.setCards(testCards);
		
		testList.addCard(extraRecipe2);
		assertEquals(3, testList.getCards().size());
	}
	
	@Test
	public void testBasicGetListName() {
		ArrayList<Object> testCards = new ArrayList<>();
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
		testCards.add(expectedRecipe);
		testCards.add(extraRecipe);
		testCards.add(extraRecipe2);
		ResultList testList = new ResultList("resultList", testCards);
		String expected = "resultList";
		assertEquals(expected, testList.getName());

	}

	@Test
	public void testMoveToList() {
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
		Restaurant testRestaurant2 = new Restaurant("Ramen Kenjo", "website", "address", "phoneNumber", "$$$", 15);
		testCards.add(expectedRecipe);
		testCards.add(extraRecipe);
		testCards.add(extraRecipe2);
		testCards.add(testRestaurant);
		testCards.add(testRestaurant2);
		testList.setCards(testCards);
		secondTestCards.add(secondRecipe2);
		secondTestCards.add(secondRecipe);
		secondTestList.setCards(secondTestCards);
		assertEquals(5, testList.getCards().size());
		assertEquals(2, secondTestList.getCards().size());
		
		ArrayList<ResultList> returned = new MoveToListData().moveToList(testCards, "copy2", testList, secondTestList);
		assertEquals("resultList", returned.get(0).getName());
		assertEquals("secondList", returned.get(1).getName());
		assertEquals(4, returned.get(0).getCards().size());
		assertEquals(3, returned.get(1).getCards().size());
		returned = new MoveToListData().moveToList(testCards, "Ramen Kenjo", testList, secondTestList);
		assertEquals(3, returned.get(0).getCards().size());
		assertEquals(4, returned.get(1).getCards().size());
	}
	@Test
	public void testAddToList() throws IOException {
		ResultList testList = new ResultList();
		ResultList secondTestList = new ResultList();
		testList.setName("resultList");
		secondTestList.setName("secondList");
		ArrayList<Object> testCards = new ArrayList<>();
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
		Restaurant testRestaurant1 = new Restaurant("Slurpin' Ramen", "website", "address", "phoneNumber", "$$$", 15);
		Restaurant testRestaurant2 = new Restaurant("Daikokuya", "website", "address", "phoneNumber", "$$$", 15);
		testCards.add(expectedRecipe);
		testCards.add(extraRecipe);
//		testCards.add(extraRecipe2);
		ArrayList<Restaurant> restaurantList = new ArrayList<>();
		ArrayList<Recipe> secondTestCards = new ArrayList<>();

		restaurantList.add(testRestaurant2);
		restaurantList.add(testRestaurant1);
		testList.setCards(testCards);
		secondTestCards.add(secondRecipe2);
		secondTestCards.add(secondRecipe);
		assertEquals(2, testCards.size());
		assertEquals(2, restaurantList.size());
		assertEquals(2,  secondTestCards.size());
		ResultList returned = new ListMgmtData().addToList(testCards, "testList", "Daikokuya", "restaurant", secondTestCards, restaurantList);
		ArrayList<Object> resultList = returned.getCards();
		assertEquals("testList", returned.getName());
		assertEquals(3, resultList.size());
		
//		returned = new ListMgmtData().(testCards, "resultList", "secondRecipe", "recipe", secondTestCards, restaurantList);
		returned = new ListMgmtData().decider("add", testList, "secondRecipe", "recipe", "resultList", secondTestCards, restaurantList);
		resultList = returned.getCards();
		assertEquals("resultList", returned.getName());
		assertEquals(4, resultList.size());
		
		returned = new ListMgmtData().decider("add", testList, "secondRecipe", "recipe", "resultList", secondTestCards, restaurantList);
		resultList = returned.getCards();
		assertEquals("resultList", returned.getName());
		assertEquals(4, resultList.size());
	}
	
	@Test
	public void testRemoveFromList() throws IOException {
		ResultList testList = new ResultList();
		ResultList secondTestList = new ResultList();
		testList.setName("resultList");
		secondTestList.setName("secondList");
		ArrayList<Object> testCards = new ArrayList<>();
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
		Restaurant testRestaurant1 = new Restaurant("Slurpin' Ramen", "website", "address", "phoneNumber", "$$$", 15);
		Restaurant testRestaurant2 = new Restaurant("Daikokuya", "website", "address", "phoneNumber", "$$$", 15);
		testCards.add(expectedRecipe);
		testCards.add(extraRecipe);
		testCards.add(secondRecipe2);
		testCards.add(testRestaurant);
		testCards.add(testRestaurant1);
		ArrayList<Object> restaurantList = new ArrayList<>();

		restaurantList.add(testRestaurant2);
		restaurantList.add(testRestaurant1);
		testList.setCards(testCards);
		secondTestList.setCards(restaurantList);
		assertEquals(5, testCards.size());
		assertEquals(2, restaurantList.size());
		
		ResultList returned = new ListMgmtData().removeFromList(testCards, "resultList", "Ramen Coleslaw", testList);
		ArrayList<Object> resultList = returned.getCards();
		assertEquals("resultList", returned.getName());
		assertEquals(4, resultList.size());
		
		returned = new ListMgmtData().decider("remove", secondTestList, "Daikokuya", "", "secondList", new ArrayList<Recipe>(), new ArrayList<Restaurant>());
		resultList = returned.getCards();
		assertEquals("secondList", returned.getName());
		assertEquals(1, resultList.size());
		
		returned = new ListMgmtData().decider("noAction", secondTestList, "noName" ,"" , "secondList", new ArrayList<Recipe>(), new ArrayList<Restaurant>());
		assertEquals("secondList", returned.getName());
		assertEquals(1, resultList.size());
		
		
	}
}
