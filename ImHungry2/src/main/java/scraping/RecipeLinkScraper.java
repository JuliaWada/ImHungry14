package scraping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class RecipeLinkScraper {
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";

    /**
     * this method will search allrecipes.com with the user's query and will only return the number of results
     * as requested or less than if there aren't enough results
     * it will grab the links to each recipe 
     * @param query      a String that the represents what the user is searching for
     * @param numResults an integer that represents the max number of results that the user wants to see
     * @return           an ArrayList of Recipe objects containing all scraped information with a max size equal to numResults
     * @throws InterruptedException 
     */
    public ArrayList<Recipe> scrapeRecipeLinks(String query, int numResults) throws InterruptedException {
    	ArrayList<Recipe> toReturn = new ArrayList<Recipe>();
    	ArrayList<String> recipeLinks = new ArrayList<String>();
    	Document doc = null;
    	try {
    		//getting the links from the search page
    		doc = Jsoup.connect("https://www.allrecipes.com/search/results/?wt=" + query + "&sort=p").userAgent(USER_AGENT).get();
    		Elements linkResults = doc.select("div.fixed-recipe-card__info > a");
    		System.out.println("linkResults: " + linkResults.size());
    		for(int i=0; i<numResults; i++) {
    			recipeLinks.add(linkResults.get(i).attr("href"));
    		}
    		
    		for(int i =0; i<numResults; i++) {
    			System.out.println("***************Start***************");
    			System.out.println("Link " + i + ": " + recipeLinks.get(i));
    			Recipe toAdd = scrapeRecipeDetails(recipeLinks.get(i));
    			TimeUnit.SECONDS.sleep(1);
    			toReturn.add(toAdd);
    		}
    	} catch (IOException ioe) {
    		System.out.println("IOException in scrapeRecipeLinks: " + ioe.getMessage());
    		ioe.printStackTrace();
    	}
    	return toReturn;
    }
    
    /**
     * this function takes in the link to a recipe page that will scrape the following data:
     * 1) title
     * 2) image
     * 3) prep time
     * 4) cook time
     * 5) ingredients
     * 6) instructions
     * 
     * @param url   a String that has the link of a recipe result from which to grab the recipe details
     * @return      a Recipe object containing all scraped data from the url of a specific recipe
     * @throws InterruptedException 
     */
    
    public Recipe scrapeRecipeDetails(String url) throws InterruptedException {
    	String html = "";
    	String title = "";
		String prepTime = "";
		String cookTime = "";
		String image = "";
		ArrayList<String> ingredients = new ArrayList<String>();
		ArrayList<String> instructions = new ArrayList<String>();
		//opening the links grabbed and adding new things to it 
		Document doc = null;
		try {
			doc = Jsoup.connect(url).userAgent(USER_AGENT).get();
			title = doc.select("h1#recipe-main-content").text();
			System.out.println("Title: " + title);
			prepTime = doc.select("[itemprop='prepTime'] > span").text().trim();
			prepTime = prepTime.replace("h", "hour");
			prepTime = prepTime.replaceAll("m", "mins");
			System.out.println("Prep Time: " + prepTime);
			cookTime = doc.select("[itemprop='cookTime'] > span").text().trim();
			cookTime = cookTime.replace("h", "hour");
			cookTime = cookTime.replaceAll("m", "mins");
			System.out.println("Cook Time: " + cookTime);
			image = doc.select("img.rec-photo").attr("src");
			System.out.println("Image URL: " + image);
			
			
			//Ingredients
			Elements ingredientResults = doc.select("[itemprop='recipeIngredient']");
			for(Element ingred : ingredientResults.select("span")) {
				ingredients.add(ingred.text());
			}
			
			for(int i = 0; i < ingredients.size(); i++) {
				System.out.println("Ingredient " + (i+1) + ": " + ingredients.get(i));
			}
			
			//Instructions
			Elements instructionResults = doc.select("span.recipe-directions__list--item");
			for(Element instruct : instructionResults) {
				String toCheck = instruct.text();
				if(!toCheck.equals("")) {
					instructions.add(instruct.text());
				}
			}
			
			for(int i = 0; i < instructions.size(); i++) {
				System.out.println("Instruction " + (i+1) + ": " + instructions.get(i));
			}
		} catch (IOException ioe) {
			System.out.println("IOException in scrapeRecipeDetails:" + ioe.getMessage());
			ioe.printStackTrace();
		}
		
		Recipe recipe = new Recipe(title, image, prepTime, cookTime, ingredients, instructions);
		
		
		return recipe;
    }
	
}
