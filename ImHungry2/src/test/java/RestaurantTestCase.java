import static org.junit.Assert.*;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.ServletException;

import yelp.*;
import org.json.*;
//
//import org.json.JSONException;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import org.junit.Test;

import com.google.maps.errors.ApiException;

import scraping.Recipe;


public class RestaurantTestCase {


	@Test
	public void testOneResult() throws InterruptedException, IOException, ServletException, JSONException, ApiException {
		ArrayList<Restaurant> expectedList = new ArrayList<Restaurant>();
		Restaurant restaurantResult = new Restaurant();
		restaurantResult.setName("Ramen Kenjo");
		restaurantResult.setWebsite("https://www.yelp.com/biz/ramen-kenjo-los-angeles-2?adjust_creative=uZhpw9YgNvae3jxqHr1gNw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=uZhpw9YgNvae3jxqHr1gNw");
		restaurantResult.setPhoneNum("No phone number available");
		restaurantResult.setPricing("No price available");
		restaurantResult.setAddress("929 W Jefferson Blvd USC Village Los Angeles, CA 90089");
		restaurantResult.setMinsAway(7);
		expectedList.add(restaurantResult);
		
		
		restaurantData yelpAPI = new restaurantData();
		ArrayList<Restaurant> actualList = yelpAPI.getRestaurants("ramen", 1);

		
		assertEquals(expectedList.size(), actualList.size());
		assertEquals(expectedList.get(0).getAddress(), actualList.get(0).getAddress());
		assertEquals(expectedList.get(0).getMinsAway(), actualList.get(0).getMinsAway());
		assertEquals(expectedList.get(0).getName(), actualList.get(0).getName());
		assertEquals(expectedList.get(0).getPhoneNum(), actualList.get(0).getPhoneNum());
		assertEquals(expectedList.get(0).getPricing(), actualList.get(0).getPricing());
		assertEquals(expectedList.get(0).getWebsite(), actualList.get(0).getWebsite());
		
	}
	
	@Test
	public void testTwoResults() throws InterruptedException, IOException, ServletException, JSONException, ApiException {
		ArrayList<Restaurant> expectedList = new ArrayList<Restaurant>();
		
		Restaurant restaurantResult = new Restaurant();
		restaurantResult.setName("Ramen Kenjo");
		restaurantResult.setWebsite("https://www.yelp.com/biz/ramen-kenjo-los-angeles-2?adjust_creative=uZhpw9YgNvae3jxqHr1gNw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=uZhpw9YgNvae3jxqHr1gNw");
		restaurantResult.setPhoneNum("No phone number available");
		restaurantResult.setPricing("No price available");
		restaurantResult.setAddress("929 W Jefferson Blvd USC Village Los Angeles, CA 90089");
		restaurantResult.setMinsAway(7);
		expectedList.add(restaurantResult);
		
		Restaurant restaurantResult2 = new Restaurant();
		restaurantResult2.setName("Momota Ramen House");
		restaurantResult2.setWebsite("https://www.yelp.com/biz/momota-ramen-house-los-angeles-8?adjust_creative=uZhpw9YgNvae3jxqHr1gNw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=uZhpw9YgNvae3jxqHr1gNw");
		restaurantResult2.setPhoneNum("(213) 973-5488");
		restaurantResult2.setPricing("$$");
		restaurantResult2.setAddress("3019 S Figueroa St Los Angeles, CA 90007");
		restaurantResult2.setMinsAway(8);
		expectedList.add(restaurantResult2);
		
		restaurantData yelpAPI = new restaurantData();
		ArrayList<Restaurant> actualList = yelpAPI.getRestaurants("ramen", 2);
		
		assertEquals(expectedList.size(), actualList.size());
		for(int i=0; i<2; i++) {
			assertEquals(expectedList.get(i).getAddress(), actualList.get(i).getAddress());
			assertEquals(expectedList.get(i).getMinsAway(), actualList.get(i).getMinsAway());
			assertEquals(expectedList.get(i).getName(), actualList.get(i).getName());
			assertEquals(expectedList.get(i).getPhoneNum(), actualList.get(i).getPhoneNum());
			assertEquals(expectedList.get(i).getPricing(), actualList.get(i).getPricing());
			assertEquals(expectedList.get(i).getWebsite(), actualList.get(i).getWebsite());
		}
		
	}
	
	@Test
	public void testing50() throws InterruptedException, IOException, ServletException, JSONException, ApiException {
		ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
		restaurantData yelpAPI = new restaurantData();
		resultList = yelpAPI.getRestaurants("ramen", 50);
		
		assertEquals(resultList.size(), 50);
	}
	
	@Test
	public void testing100() throws InterruptedException, IOException, ServletException, JSONException, ApiException {
		ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
		restaurantData yelpAPI = new restaurantData();
		resultList = yelpAPI.getRestaurants("ramen", 100);
		
		assertEquals(resultList.size(), 50);
	}
	
	@Test
	public void testHalfondResult() throws InterruptedException, IOException, ServletException, JSONException, ApiException {
		ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
		restaurantData yelpAPI = new restaurantData();
		resultList = yelpAPI.getRestaurants("Halfond", 1);
		
		assertEquals(resultList.size(), 0);
	}
	
	@Test
	public void testErrors() throws IOException, JSONException, ApiException, InterruptedException {
		//create hard-coded json
		
		    JSONObject testJsonObject = new JSONObject(); //Json object - { }
		    testJsonObject.put("total", 666);

		    JSONArray messages = new JSONArray();	// Json array - [ ]
		    
		    JSONObject body = new JSONObject();
		    body.put("name", "Grace's Shack");
		    //messages.put(body);
		    
		    //JSONObject URL = new JSONObject();
		    body.put("url", "myurl");
		    //messages.put(URL);
		    
		    //JSONObject pricing = new JSONObject();
		    body.put("price", "");
		    //messages.put(body);

		    
		    //JSONObject location = new JSONObject();
		    JSONObject display_address = new JSONObject();
		    JSONArray addressName = new JSONArray();
		    addressName.put("1234 Cool St");
		    addressName.put("Los Angeles, CA 90007");
		    display_address.put("display_address", addressName);
		    body.put("location", display_address);
		    
		    messages.put(body);
		    
		    
		    
		    testJsonObject.put("businesses", messages);
		   
		    //Files.write(Paths.get("customjson.json"), testJsonObject.toString().getBytes());
		   
		    ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
		    restaurantData yelpAPI = new restaurantData();
			resultList = yelpAPI.jsonResponse(resultList, testJsonObject, 1);
			
	
			//assertEquals(resultList.size(), actualList.size());
			//assertEquals(resultList.get(0).getAddress(), );
			//assertEquals(resultList.get(0).getMinsAway(), actualList.get(0).getMinsAway());
			assertEquals(resultList.get(0).getName(), "Grace's Shack");
			assertEquals(resultList.get(0).getPhoneNum(), "No phone number available");
			assertEquals(resultList.get(0).getPricing(), "No price available");
			assertEquals(resultList.get(0).getWebsite(), "myurl");
		    
		    
	
	}
	
	@Test
	public void testPhoneBranches() throws IOException, JSONException, ApiException, InterruptedException {
		//create hard-coded json
		
		    JSONObject testJsonObject = new JSONObject(); //Json object - { }
		    testJsonObject.put("total", 666);

		    JSONArray messages = new JSONArray();	// Json array - [ ]
		    
		    JSONObject body = new JSONObject();
		    body.put("name", "Grace's Shack");
		    //messages.put(body);
		    
		    //JSONObject URL = new JSONObject();
		    body.put("url", "myurl");
		    //messages.put(URL);
		    
		    //JSONObject pricing = new JSONObject();
		   
		    //messages.put(body);

		    
		    //JSONObject location = new JSONObject();
		    JSONObject display_address = new JSONObject();
		    JSONArray addressName = new JSONArray();
		    addressName.put("1234 Cool St");
		    addressName.put("Los Angeles, CA 90007");
		    display_address.put("display_address", addressName);
		    body.put("location", display_address);
		    
		    messages.put(body);
		    
		    
		    
		    testJsonObject.put("businesses", messages);
		   
		    //Files.write(Paths.get("customjson.json"), testJsonObject.toString().getBytes());
		   
		    ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
		    restaurantData yelpAPI = new restaurantData();
			resultList = yelpAPI.jsonResponse(resultList, testJsonObject, 1);
			
	
			//assertEquals(resultList.size(), actualList.size());
			//assertEquals(resultList.get(0).getAddress(), );
			//assertEquals(resultList.get(0).getMinsAway(), actualList.get(0).getMinsAway());
			assertEquals(resultList.get(0).getName(), "Grace's Shack");
			assertEquals(resultList.get(0).getPhoneNum(), "No phone number available");
			assertEquals(resultList.get(0).getPricing(), "No price available");
			assertEquals(resultList.get(0).getWebsite(), "myurl");
		    
		    
	
	}
	
	@Test
	public void testCompareWithDNSBasic() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		ArrayList<Restaurant> restaurantArray = new ArrayList<Restaurant>();
		ArrayList<Object> doNotShowList = new ArrayList<Object>();
		ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
		 
		restaurantData yelpAPI = new restaurantData();
		restaurantArray = yelpAPI.getRestaurants("ramen", 3);
		 
		Restaurant restaurantResult = new Restaurant();
		restaurantResult.setName("Ramen Kenjo");
		restaurantResult.setWebsite("https://www.yelp.com/biz/ramen-kenjo-los-angeles-2?adjust_creative=uZhpw9YgNvae3jxqHr1gNw&utm_cam"
				+ "paign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=uZhpw9YgNvae3jxqHr1gNw");
		restaurantResult.setPhoneNum("No phone number available");
		restaurantResult.setPricing("No price available");
		restaurantResult.setAddress("929 W Jefferson Blvd USC Village Los Angeles, CA 90089");
		restaurantResult.setMinsAway(7);
		doNotShowList.add(restaurantResult);
		
		resultList = yelpAPI.checkDoNotShow(restaurantArray, doNotShowList);
		
		assertEquals(resultList.size(), 2);
		assertEquals(resultList.get(0).getName(), "Momota Ramen House");
		assertEquals(resultList.get(1).getName(), "Ebaes");

		 
	    
	}
	
	@Test
	public void testWithEmptyDNS() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		ArrayList<Restaurant> restaurantArray = new ArrayList<Restaurant>();
		ArrayList<Object> doNotShowList = new ArrayList<Object>();
		ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
		
		restaurantData yelpAPI = new restaurantData();
		restaurantArray = yelpAPI.getRestaurants("ramen", 2);
		resultList = yelpAPI.checkDoNotShow(restaurantArray, doNotShowList);
		
		assertEquals(resultList.size(), 2);
		assertEquals(resultList.get(0).getName(), "Ramen Kenjo");
		assertEquals(resultList.get(1).getName(), "Momota Ramen House");

	}
	
	@Test
	public void testDNSWithEmptyRest() {
		ArrayList<Restaurant> restaurantArray = new ArrayList<Restaurant>();
		ArrayList<Object> doNotShowList = new ArrayList<Object>();
		ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
		
		Restaurant restaurantResult = new Restaurant();
		restaurantResult.setName("Ramen Kenjo");
		restaurantResult.setWebsite("https://www.yelp.com/biz/ramen-kenjo-los-angeles-2?adjust_creative=uZhpw9YgNvae3jxqHr1gNw&utm_cam"
				+ "paign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=uZhpw9YgNvae3jxqHr1gNw");
		restaurantResult.setPhoneNum("No phone number available");
		restaurantResult.setPricing("No price available");
		restaurantResult.setAddress("929 W Jefferson Blvd USC Village Los Angeles, CA 90089");
		restaurantResult.setMinsAway(7);
		doNotShowList.add(restaurantResult);
		
		restaurantData yelpAPI = new restaurantData();
		resultList = yelpAPI.checkDoNotShow(restaurantArray, doNotShowList);
		
		assertEquals(resultList.size(), 0);
		
	}
	
	@Test
	public void testCompareWithDNSRecipe() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		ArrayList<Restaurant> restaurantArray = new ArrayList<Restaurant>();
		ArrayList<Object> doNotShowList = new ArrayList<Object>();
		ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
		 
		restaurantData yelpAPI = new restaurantData();
		restaurantArray = yelpAPI.getRestaurants("ramen", 3);
		 
		Restaurant restaurantResult = new Restaurant();
		restaurantResult.setName("Ramen Kenjo");
		restaurantResult.setWebsite("https://www.yelp.com/biz/ramen-kenjo-los-angeles-2?adjust_creative=uZhpw9YgNvae3jxqHr1gNw&utm_cam"
				+ "paign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=uZhpw9YgNvae3jxqHr1gNw");
		restaurantResult.setPhoneNum("No phone number available");
		restaurantResult.setPricing("No price available");
		restaurantResult.setAddress("929 W Jefferson Blvd USC Village Los Angeles, CA 90089");
		restaurantResult.setMinsAway(7);
		doNotShowList.add(restaurantResult);
		ArrayList<String> ingredients = new ArrayList<>();
		ArrayList<String> instructions = new ArrayList<>();
		Recipe recipeResult = new Recipe("pie", "apiofjaepfi", "15 mins", 15, "30 mins", ingredients, instructions);
		doNotShowList.add(recipeResult);
		
		resultList = yelpAPI.checkDoNotShow(restaurantArray, doNotShowList);
		
		assertEquals(resultList.size(), 2);
		assertEquals(resultList.get(0).getName(), "Momota Ramen House");
		assertEquals(resultList.get(1).getName(), "Ebaes");
		 
	    
	}

	@Test
	public void testCompareWithDNSMultipleItems() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		//DNS has: more than one item in the list, all in the restaurant array list
		ArrayList<Restaurant> restaurantArray = new ArrayList<Restaurant>();
		ArrayList<Object> doNotShowList = new ArrayList<Object>();
		ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
		 
		restaurantData yelpAPI = new restaurantData();
		restaurantArray = yelpAPI.getRestaurants("ramen", 5);
		 
		Restaurant restaurantResult = new Restaurant();
		restaurantResult.setName("Ramen Kenjo");
		restaurantResult.setWebsite("https://www.yelp.com/biz/ramen-kenjo-los-angeles-2?adjust_creative=uZhpw9YgNvae3jxqHr1gNw&utm_cam"
				+ "paign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=uZhpw9YgNvae3jxqHr1gNw");
		restaurantResult.setPhoneNum("No phone number available");
		restaurantResult.setPricing("No price available");
		restaurantResult.setAddress("929 W Jefferson Blvd USC Village Los Angeles, CA 90089");
		restaurantResult.setMinsAway(7);
		doNotShowList.add(restaurantResult);
		
		Restaurant restaurantResult2 = new Restaurant();
		restaurantResult2.setName(restaurantArray.get(2).getName());
		restaurantResult2.setWebsite(restaurantArray.get(2).getWebsite());
		restaurantResult2.setPhoneNum(restaurantArray.get(2).getPhoneNum());
		restaurantResult2.setPricing(restaurantArray.get(2).getPricing());
		restaurantResult2.setAddress(restaurantArray.get(2).getAddress());
		restaurantResult2.setMinsAway(restaurantArray.get(2).getMinsAway());
		doNotShowList.add(restaurantResult2);
		
		Restaurant restaurantResult3 = new Restaurant();
		restaurantResult3.setName(restaurantArray.get(3).getName());
		restaurantResult3.setWebsite(restaurantArray.get(3).getWebsite());
		restaurantResult3.setPhoneNum(restaurantArray.get(3).getPhoneNum());
		restaurantResult3.setPricing(restaurantArray.get(3).getPricing());
		restaurantResult3.setAddress(restaurantArray.get(3).getAddress());
		restaurantResult3.setMinsAway(restaurantArray.get(3).getMinsAway());
		doNotShowList.add(restaurantResult3);
		
		resultList = yelpAPI.checkDoNotShow(restaurantArray, doNotShowList);
		
		assertEquals(resultList.size(), 2);
		assertEquals(resultList.get(0).getName(), "Momota Ramen House");
		assertEquals(resultList.get(1).getName(), "G-gle");

	}
	
	@Test
	public void testCompareWithDNSAllSameItems() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		//DNS has: more than one item in the list, all items in list
		
		ArrayList<Restaurant> restaurantArray = new ArrayList<Restaurant>();
		ArrayList<Object> doNotShowList = new ArrayList<Object>();
		ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
		 
		restaurantData yelpAPI = new restaurantData();
		restaurantArray = yelpAPI.getRestaurants("ramen", 5);
		
		Restaurant restaurantResult = new Restaurant();
		restaurantResult.setName("Ramen Kenjo");
		restaurantResult.setWebsite("https://www.yelp.com/biz/ramen-kenjo-los-angeles-2?adjust_creative=uZhpw9YgNvae3jxqHr1gNw&utm_cam"
				+ "paign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=uZhpw9YgNvae3jxqHr1gNw");
		restaurantResult.setPhoneNum("No phone number available");
		restaurantResult.setPricing("No price available");
		restaurantResult.setAddress("929 W Jefferson Blvd USC Village Los Angeles, CA 90089");
		restaurantResult.setMinsAway(7);
		doNotShowList.add(restaurantResult);
		
		Restaurant restaurantResult2 = new Restaurant();
		restaurantResult2.setName(restaurantArray.get(1).getName());
		restaurantResult2.setWebsite(restaurantArray.get(1).getWebsite());
		restaurantResult2.setPhoneNum(restaurantArray.get(1).getPhoneNum());
		restaurantResult2.setPricing(restaurantArray.get(1).getPricing());
		restaurantResult2.setAddress(restaurantArray.get(1).getAddress());
		restaurantResult2.setMinsAway(restaurantArray.get(1).getMinsAway());
		doNotShowList.add(restaurantResult2);
		
		Restaurant restaurantResult3 = new Restaurant();
		restaurantResult3.setName(restaurantArray.get(2).getName());
		restaurantResult3.setWebsite(restaurantArray.get(2).getWebsite());
		restaurantResult3.setPhoneNum(restaurantArray.get(2).getPhoneNum());
		restaurantResult3.setPricing(restaurantArray.get(2).getPricing());
		restaurantResult3.setAddress(restaurantArray.get(2).getAddress());
		restaurantResult3.setMinsAway(restaurantArray.get(2).getMinsAway());
		doNotShowList.add(restaurantResult3);
		
		Restaurant restaurantResult4 = new Restaurant();
		restaurantResult4.setName(restaurantArray.get(3).getName());
		restaurantResult4.setWebsite(restaurantArray.get(3).getWebsite());
		restaurantResult4.setPhoneNum(restaurantArray.get(3).getPhoneNum());
		restaurantResult4.setPricing(restaurantArray.get(3).getPricing());
		restaurantResult4.setAddress(restaurantArray.get(3).getAddress());
		restaurantResult4.setMinsAway(restaurantArray.get(3).getMinsAway());
		doNotShowList.add(restaurantResult4);
		
		Restaurant restaurantResult5 = new Restaurant();
		restaurantResult5.setName(restaurantArray.get(4).getName());
		restaurantResult5.setWebsite(restaurantArray.get(4).getWebsite());
		restaurantResult5.setPhoneNum(restaurantArray.get(4).getPhoneNum());
		restaurantResult5.setPricing(restaurantArray.get(4).getPricing());
		restaurantResult5.setAddress(restaurantArray.get(4).getAddress());
		restaurantResult5.setMinsAway(restaurantArray.get(4).getMinsAway());
		doNotShowList.add(restaurantResult5);
	
		resultList = yelpAPI.checkDoNotShow(restaurantArray, doNotShowList);
		
		assertEquals(resultList.size(), 0);

	}
	
	@Test
	public void testCompareWithDNSDiffItems() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		//DNS has: more than one item in the list, some in list, some not
		
		ArrayList<Restaurant> restaurantArray = new ArrayList<Restaurant>();
		ArrayList<Object> doNotShowList = new ArrayList<Object>();
		ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
		 
		restaurantData yelpAPI = new restaurantData();
		restaurantArray = yelpAPI.getRestaurants("ramen", 5);
		
		Restaurant restaurantResult = new Restaurant();
		restaurantResult.setName("Ramen Kenjo");
		restaurantResult.setWebsite("https://www.yelp.com/biz/ramen-kenjo-los-angeles-2?adjust_creative=uZhpw9YgNvae3jxqHr1gNw&utm_cam"
				+ "paign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=uZhpw9YgNvae3jxqHr1gNw");
		restaurantResult.setPhoneNum("No phone number available");
		restaurantResult.setPricing("No price available");
		restaurantResult.setAddress("929 W Jefferson Blvd USC Village Los Angeles, CA 90089");
		restaurantResult.setMinsAway(7);
		doNotShowList.add(restaurantResult);
		
		Restaurant restaurantResult2 = new Restaurant();
		restaurantResult2.setName("poo2");
		restaurantResult2.setWebsite("poo2");
		restaurantResult2.setPhoneNum("poo2");
		restaurantResult2.setPricing("poo2");
		restaurantResult2.setAddress("poo2");
		restaurantResult2.setMinsAway(7);
		doNotShowList.add(restaurantResult2);
		
		Restaurant restaurantResult3 = new Restaurant();
		restaurantResult3.setName(restaurantArray.get(2).getName());
		restaurantResult3.setWebsite(restaurantArray.get(2).getWebsite());
		restaurantResult3.setPhoneNum(restaurantArray.get(2).getPhoneNum());
		restaurantResult3.setPricing(restaurantArray.get(2).getPricing());
		restaurantResult3.setAddress(restaurantArray.get(2).getAddress());
		restaurantResult3.setMinsAway(restaurantArray.get(2).getMinsAway());
		doNotShowList.add(restaurantResult3);
		
		Restaurant restaurantResult4 = new Restaurant();
		restaurantResult4.setName("poo");
		restaurantResult4.setWebsite("poo");
		restaurantResult4.setPhoneNum("poo");
		restaurantResult4.setPricing("poo");
		restaurantResult4.setAddress("poo");
		restaurantResult4.setMinsAway(7);
		doNotShowList.add(restaurantResult4);
	
		resultList = yelpAPI.checkDoNotShow(restaurantArray, doNotShowList);
		
		// kenjo, momota, ebaes, orange, g-gle
		// momota, orange, g-gle
		
		assertEquals(resultList.size(), 3);
		assertEquals(resultList.get(0).getName(), "Momota Ramen House");
		assertEquals(resultList.get(1).getName(), "Orange Sekai Ramen");
		assertEquals(resultList.get(2).getName(), "G-gle");

		
	}
	
	@Test
	public void testCompareWithDNSAllDiffItems() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		//DNS has: more than one item in the list, all items not in list
		
		ArrayList<Restaurant> restaurantArray = new ArrayList<Restaurant>();
		ArrayList<Object> doNotShowList = new ArrayList<Object>();
		ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
		 
		restaurantData yelpAPI = new restaurantData();
		restaurantArray = yelpAPI.getRestaurants("ramen", 3);
		
		Restaurant restaurantResult = new Restaurant();
		restaurantResult.setName("poo");
		restaurantResult.setWebsite("poo");
		restaurantResult.setPhoneNum("poo");
		restaurantResult.setPricing("poo");
		restaurantResult.setAddress("poo");
		restaurantResult.setMinsAway(7);
		doNotShowList.add(restaurantResult);
		
		Restaurant restaurantResult2 = new Restaurant();
		restaurantResult2.setName("poo2");
		restaurantResult2.setWebsite("poo2");
		restaurantResult2.setPhoneNum("poo2");
		restaurantResult2.setPricing("poo2");
		restaurantResult2.setAddress("poo2");
		restaurantResult2.setMinsAway(7);
		doNotShowList.add(restaurantResult2);
		
		Restaurant restaurantResult3 = new Restaurant();
		restaurantResult3.setName("poo3");
		restaurantResult3.setWebsite("poo3");
		restaurantResult3.setPhoneNum("poo3");
		restaurantResult3.setPricing("poo3");
		restaurantResult3.setAddress("poo3");
		restaurantResult3.setMinsAway(7);
		doNotShowList.add(restaurantResult3);
	
		resultList = yelpAPI.checkDoNotShow(restaurantArray, doNotShowList);
		
		assertEquals(resultList.size(), 3);
		assertEquals(resultList.get(0).getName(), "Ramen Kenjo");
		assertEquals(resultList.get(1).getName(), "Momota Ramen House");
		assertEquals(resultList.get(2).getName(), "Ebaes");

	}
	
	@Test
	public void testCompareWithFavesBasic() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		ArrayList<Restaurant> restaurantArray = new ArrayList<Restaurant>();
		ArrayList<Object> favorites = new ArrayList<Object>();
		ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
		 
		restaurantData yelpAPI = new restaurantData();
		restaurantArray = yelpAPI.getRestaurants("ramen", 3);
		 
		Restaurant restaurantResult = new Restaurant();
		restaurantResult.setName("Ebaes");
		restaurantResult.setWebsite("https://www.yelp.com/biz/ebaes-los-angeles?adjust_creative=uZhpw9YgNvae3jx"
				+ "qHr1gNw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=uZhpw9YgNvae3jxqHr1gNw");
		restaurantResult.setPhoneNum("(213) 747-6888");
		restaurantResult.setPricing("$$");
		restaurantResult.setAddress("2314 S Union Ave Los Angeles, CA 90007");
		restaurantResult.setMinsAway(9);
		favorites.add(restaurantResult);
		
		resultList = yelpAPI.checkFavorites(restaurantArray, favorites);
		
		assertEquals(resultList.size(), 3);
		assertEquals(resultList.get(0).getName(), "Ebaes");
		assertEquals(resultList.get(1).getName(), "Ramen Kenjo");
		assertEquals(resultList.get(2).getName(), "Momota Ramen House");

	}
	
	@Test
	public void testCompareWithFavesMultipleItems() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		//Faves list has: more than one item in the list, all in the restaurant array list
		
		ArrayList<Restaurant> restaurantArray = new ArrayList<Restaurant>();
		ArrayList<Object> favorites = new ArrayList<Object>();
		ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
		 
		restaurantData yelpAPI = new restaurantData();
		restaurantArray = yelpAPI.getRestaurants("ramen", 5);
		 
		Restaurant restaurantResult = new Restaurant();
		restaurantResult.setName("Ebaes");
		restaurantResult.setWebsite("https://www.yelp.com/biz/ebaes-los-angeles?adjust_creative=uZhpw9YgNvae3jx"
				+ "qHr1gNw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=uZhpw9YgNvae3jxqHr1gNw");
		restaurantResult.setPhoneNum("(213) 747-6888");
		restaurantResult.setPricing("$$");
		restaurantResult.setAddress("2314 S Union Ave Los Angeles, CA 90007");
		restaurantResult.setMinsAway(9);
		favorites.add(restaurantResult);
		
		Restaurant restaurantResult2 = new Restaurant();
		restaurantResult2.setName(restaurantArray.get(4).getName());
		restaurantResult2.setWebsite(restaurantArray.get(4).getWebsite());
		restaurantResult2.setPhoneNum(restaurantArray.get(4).getPhoneNum());
		restaurantResult2.setPricing(restaurantArray.get(4).getPricing());
		restaurantResult2.setAddress(restaurantArray.get(4).getAddress());
		restaurantResult2.setMinsAway(restaurantArray.get(4).getMinsAway());
		favorites.add(restaurantResult2);
		
		resultList = yelpAPI.checkFavorites(restaurantArray, favorites);
		
		assertEquals(resultList.size(), 5);
		assertEquals(resultList.get(0).getName(), "Ebaes");
		assertEquals(resultList.get(1).getName(), "G-gle");
		assertEquals(resultList.get(2).getName(), "Ramen Kenjo");
		assertEquals(resultList.get(3).getName(), "Momota Ramen House");
		assertEquals(resultList.get(4).getName(), "Orange Sekai Ramen");
		
	}
	
	@Test
	public void testCompareWithFavesAllSameItems() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		//DNS has: more than one item in the list, all items in list
		
		ArrayList<Restaurant> restaurantArray = new ArrayList<Restaurant>();
		ArrayList<Object> favorites = new ArrayList<Object>();
		ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
		 
		restaurantData yelpAPI = new restaurantData();
		restaurantArray = yelpAPI.getRestaurants("ramen", 5);
		
		Restaurant restaurantResult = new Restaurant();
		restaurantResult.setName("Ramen Kenjo");
		restaurantResult.setWebsite("https://www.yelp.com/biz/ramen-kenjo-los-angeles-2?adjust_creative=uZhpw9YgNvae3jxqHr1gNw&utm_cam"
				+ "paign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=uZhpw9YgNvae3jxqHr1gNw");
		restaurantResult.setPhoneNum("No phone number available");
		restaurantResult.setPricing("No price available");
		restaurantResult.setAddress("929 W Jefferson Blvd USC Village Los Angeles, CA 90089");
		restaurantResult.setMinsAway(7);
		favorites.add(restaurantResult);
		
		Restaurant restaurantResult2 = new Restaurant();
		restaurantResult2.setName(restaurantArray.get(1).getName());
		restaurantResult2.setWebsite(restaurantArray.get(1).getWebsite());
		restaurantResult2.setPhoneNum(restaurantArray.get(1).getPhoneNum());
		restaurantResult2.setPricing(restaurantArray.get(1).getPricing());
		restaurantResult2.setAddress(restaurantArray.get(1).getAddress());
		restaurantResult2.setMinsAway(restaurantArray.get(1).getMinsAway());
		favorites.add(restaurantResult2);
		
		Restaurant restaurantResult3 = new Restaurant();
		restaurantResult3.setName(restaurantArray.get(2).getName());
		restaurantResult3.setWebsite(restaurantArray.get(2).getWebsite());
		restaurantResult3.setPhoneNum(restaurantArray.get(2).getPhoneNum());
		restaurantResult3.setPricing(restaurantArray.get(2).getPricing());
		restaurantResult3.setAddress(restaurantArray.get(2).getAddress());
		restaurantResult3.setMinsAway(restaurantArray.get(2).getMinsAway());
		favorites.add(restaurantResult3);
		
		Restaurant restaurantResult4 = new Restaurant();
		restaurantResult4.setName(restaurantArray.get(3).getName());
		restaurantResult4.setWebsite(restaurantArray.get(3).getWebsite());
		restaurantResult4.setPhoneNum(restaurantArray.get(3).getPhoneNum());
		restaurantResult4.setPricing(restaurantArray.get(3).getPricing());
		restaurantResult4.setAddress(restaurantArray.get(3).getAddress());
		restaurantResult4.setMinsAway(restaurantArray.get(3).getMinsAway());
		favorites.add(restaurantResult4);
		
		Restaurant restaurantResult5 = new Restaurant();
		restaurantResult5.setName(restaurantArray.get(4).getName());
		restaurantResult5.setWebsite(restaurantArray.get(4).getWebsite());
		restaurantResult5.setPhoneNum(restaurantArray.get(4).getPhoneNum());
		restaurantResult5.setPricing(restaurantArray.get(4).getPricing());
		restaurantResult5.setAddress(restaurantArray.get(4).getAddress());
		restaurantResult5.setMinsAway(restaurantArray.get(4).getMinsAway());
		favorites.add(restaurantResult5);
		
		resultList = yelpAPI.checkFavorites(restaurantArray, favorites);
		
		assertEquals(resultList.size(), 5);
		assertEquals(resultList.get(0).getName(), "Ramen Kenjo");
		assertEquals(resultList.get(1).getName(), "Momota Ramen House");
		assertEquals(resultList.get(2).getName(), "Ebaes");
		assertEquals(resultList.get(3).getName(), "Orange Sekai Ramen");
		assertEquals(resultList.get(4).getName(), "G-gle");
		
		
	}
	
	@Test
	public void testCompareWithDiffItems() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		//Faves list has: more than one item in the list, some in list, some not
		
		ArrayList<Restaurant> restaurantArray = new ArrayList<Restaurant>();
		ArrayList<Object> favorites = new ArrayList<Object>();
		ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
		 
		restaurantData yelpAPI = new restaurantData();
		restaurantArray = yelpAPI.getRestaurants("ramen", 5);
		
		Restaurant restaurantResult = new Restaurant();
		restaurantResult.setName("poo");
		restaurantResult.setWebsite("poo");
		restaurantResult.setPhoneNum("poo");
		restaurantResult.setPricing("poo");
		restaurantResult.setAddress("poo");
		restaurantResult.setMinsAway(7);
		favorites.add(restaurantResult);
		
		Restaurant restaurantResult2 = new Restaurant();
		restaurantResult2.setName("poo2");
		restaurantResult2.setWebsite("poo2");
		restaurantResult2.setPhoneNum("poo2");
		restaurantResult2.setPricing("poo2");
		restaurantResult2.setAddress("poo2");
		restaurantResult2.setMinsAway(7);
		favorites.add(restaurantResult2);
		
		Restaurant restaurantResult3 = new Restaurant();
		restaurantResult3.setName("poo3");
		restaurantResult3.setWebsite("poo3");
		restaurantResult3.setPhoneNum("poo3");
		restaurantResult3.setPricing("poo3");
		restaurantResult3.setAddress("poo3");
		restaurantResult3.setMinsAway(7);
		favorites.add(restaurantResult3);
		
		Restaurant restaurantResult4 = new Restaurant();
		restaurantResult4.setName(restaurantArray.get(3).getName());
		restaurantResult4.setWebsite(restaurantArray.get(3).getWebsite());
		restaurantResult4.setPhoneNum(restaurantArray.get(3).getPhoneNum());
		restaurantResult4.setPricing(restaurantArray.get(3).getPricing());
		restaurantResult4.setAddress(restaurantArray.get(3).getAddress());
		restaurantResult4.setMinsAway(restaurantArray.get(3).getMinsAway());
		favorites.add(restaurantResult4);
		
		resultList = yelpAPI.checkFavorites(restaurantArray, favorites);
		
		// kenjo, momota, ebaes, orange, g-gle
		// orange, kenjo, momota, ebaes, g-gle
		
		assertEquals(resultList.size(), 5);
		assertEquals(resultList.get(0).getName(), "Orange Sekai Ramen");
		assertEquals(resultList.get(1).getName(), "Ramen Kenjo");
		assertEquals(resultList.get(2).getName(), "Momota Ramen House");
		assertEquals(resultList.get(3).getName(), "Ebaes");
		assertEquals(resultList.get(4).getName(), "G-gle");
		
	}
	
	@Test
	public void testCompareWithAllDiffItems() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		//Faves list has: more than one item in the list, all items no in list
		
		ArrayList<Restaurant> restaurantArray = new ArrayList<Restaurant>();
		ArrayList<Object> favorites = new ArrayList<Object>();
		ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
		 
		restaurantData yelpAPI = new restaurantData();
		restaurantArray = yelpAPI.getRestaurants("ramen", 3);
		
		Restaurant restaurantResult = new Restaurant();
		restaurantResult.setName("poo");
		restaurantResult.setWebsite("poo");
		restaurantResult.setPhoneNum("poo");
		restaurantResult.setPricing("poo");
		restaurantResult.setAddress("poo");
		restaurantResult.setMinsAway(7);
		favorites.add(restaurantResult);
		
		Restaurant restaurantResult2 = new Restaurant();
		restaurantResult2.setName("poo2");
		restaurantResult2.setWebsite("poo2");
		restaurantResult2.setPhoneNum("poo2");
		restaurantResult2.setPricing("poo2");
		restaurantResult2.setAddress("poo2");
		restaurantResult2.setMinsAway(7);
		favorites.add(restaurantResult2);
		
		Restaurant restaurantResult3 = new Restaurant();
		restaurantResult3.setName("poo3");
		restaurantResult3.setWebsite("poo3");
		restaurantResult3.setPhoneNum("poo3");
		restaurantResult3.setPricing("poo3");
		restaurantResult3.setAddress("poo3");
		restaurantResult3.setMinsAway(7);
		favorites.add(restaurantResult3);
		
		resultList = yelpAPI.checkFavorites(restaurantArray, favorites);
		
		assertEquals(resultList.size(), 3);
		assertEquals(resultList.get(0).getName(), "Ramen Kenjo");
		assertEquals(resultList.get(1).getName(), "Momota Ramen House");
		assertEquals(resultList.get(2).getName(), "Ebaes");
		
	}
	
	@Test
	public void testWithEmptyFaves() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		ArrayList<Restaurant> restaurantArray = new ArrayList<Restaurant>();
		ArrayList<Object> favorites = new ArrayList<Object>();
		ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
		
		restaurantData yelpAPI = new restaurantData();
		restaurantArray = yelpAPI.getRestaurants("ramen", 2);
		resultList = yelpAPI.checkFavorites(restaurantArray, favorites);
		
		assertEquals(resultList.size(), 2);
		assertEquals(resultList.get(0).getName(), "Ramen Kenjo");
		assertEquals(resultList.get(1).getName(), "Momota Ramen House");
		
		
	}
	
	@Test
	public void testFavesWithEmptyRest() {
		ArrayList<Restaurant> restaurantArray = new ArrayList<Restaurant>();
		ArrayList<Object> favorites = new ArrayList<Object>();
		ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
		
		Restaurant restaurantResult = new Restaurant();
		restaurantResult.setName("Ramen Kenjo");
		restaurantResult.setWebsite("https://www.yelp.com/biz/ramen-kenjo-los-angeles-2?adjust_creative=uZhpw9YgNvae3jxqHr1gNw&utm_cam"
				+ "paign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=uZhpw9YgNvae3jxqHr1gNw");
		restaurantResult.setPhoneNum("No phone number available");
		restaurantResult.setPricing("No price available");
		restaurantResult.setAddress("929 W Jefferson Blvd USC Village Los Angeles, CA 90089");
		restaurantResult.setMinsAway(7);
		favorites.add(restaurantResult);
		
		restaurantData yelpAPI = new restaurantData();
		resultList = yelpAPI.checkFavorites(restaurantArray, favorites);
		
		assertEquals(resultList.size(), 0);
		
	}
	
	@Test
	public void testCompareWithFavesRecipe() throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		ArrayList<Restaurant> restaurantArray = new ArrayList<Restaurant>();
		ArrayList<Object> favorites = new ArrayList<Object>();
		ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
		 
		restaurantData yelpAPI = new restaurantData();
		restaurantArray = yelpAPI.getRestaurants("ramen", 3);
		 
		Restaurant restaurantResult3 = new Restaurant();
		restaurantResult3.setName(restaurantArray.get(2).getName());
		restaurantResult3.setWebsite(restaurantArray.get(2).getWebsite());
		restaurantResult3.setPhoneNum(restaurantArray.get(2).getPhoneNum());
		restaurantResult3.setPricing(restaurantArray.get(2).getPricing());
		restaurantResult3.setAddress(restaurantArray.get(2).getAddress());
		restaurantResult3.setMinsAway(restaurantArray.get(2).getMinsAway());
		favorites.add(restaurantResult3);

		
		ArrayList<String> ingredients = new ArrayList<>();
		ArrayList<String> instructions = new ArrayList<>();
		Recipe recipeResult = new Recipe("pie", "apiofjaepfi", "15 mins", 15, "30 mins", ingredients, instructions);
		favorites.add(recipeResult);
		
		resultList = yelpAPI.checkFavorites(restaurantArray, favorites);
		
		assertEquals(resultList.size(), 3);
		assertEquals(resultList.get(0).getName(), "Ebaes");
		assertEquals(resultList.get(1).getName(), "Ramen Kenjo");
		assertEquals(resultList.get(2).getName(), "Momota Ramen House");

	}
	

}
