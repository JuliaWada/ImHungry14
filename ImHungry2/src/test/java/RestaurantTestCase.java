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
	
	
//	@Test
//	public void testing30() throws InterruptedException, IOException, ServletException, JSONException, ApiException {
//		ArrayList<Restaurant> resultList = new ArrayList<Restaurant>();
//		restaurantData yelpAPI = new restaurantData();
//		resultList = yelpAPI.getRestaurants("ramen", 30);
//		
//		assertEquals(resultList.size(), 30);
//	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testIndexOutOfBoundsException() {
	    ArrayList<Restaurant> emptyList = new ArrayList();
	    Object o = emptyList.get(0);
	}

	

}
