package scraping;

import java.util.ArrayList;


public class Recipe extends Result {
	private String imageURL = "";
	private String prepTime = "";
	private String cookTime = "";
	private ArrayList<String> ingredients = new ArrayList<String>();
	private ArrayList<String> instructions = new ArrayList<String>();
	
	public Recipe(String name, String imageURL, String prepTime, String cookTime,
			ArrayList<String> ingredients, ArrayList<String> instructions) {
		this.name = name;
		this.imageURL = imageURL;
		this.prepTime = prepTime;
		this.cookTime = cookTime;
		this.ingredients = ingredients;
		this.instructions = instructions;
	}
	
	//getters	
	public String getImageURL() {
		return this.imageURL;
	}
	
	public String getPrepTime() {
		return this.prepTime;
	}
	
	public String getCookTime() {
		return this.cookTime;
	}
	
	public ArrayList<String> getIngredients() {
		return this.ingredients;
	}
	
	public ArrayList<String> getInstructions() {
		return this.getIngredients();
	}
	
	//setters
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	
	public void setPrepTime(String prepTime) {
		this.prepTime = prepTime;
	}
	
	public void setCookTime(String cookTime) {
		this.cookTime = cookTime;
	}
	
	public void setIngredients(ArrayList<String> ingredients) {
		this.ingredients = ingredients;
	}
	
	public void setInstructions(ArrayList<String> instructions) {
		this.instructions = instructions;
	}
}