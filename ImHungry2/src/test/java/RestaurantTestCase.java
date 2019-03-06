import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;

import yelp.*;

import org.json.JSONException;
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
		
		System.out.println("/////////////////////////////////////////////////////////////");
		System.out.println("EXPECTED" + expectedList.get(0).toString());
		System.out.println();
		System.out.println("ACTUAL" + actualList.get(0).toString());
		System.out.println("/////////////////////////////////////////////////////////////");
		
		assertEquals(expectedList.size(), actualList.size());
		assertEquals(expectedList.get(0).getAddress(), actualList.get(0).getAddress());
		assertEquals(expectedList.get(0).getMinsAway(), actualList.get(0).getMinsAway());
		assertEquals(expectedList.get(0).getName(), actualList.get(0).getName());
		assertEquals(expectedList.get(0).getPhoneNum(), actualList.get(0).getPhoneNum());
		assertEquals(expectedList.get(0).getPricing(), actualList.get(0).getPricing());
		assertEquals(expectedList.get(0).getWebsite(), actualList.get(0).getWebsite());
		
	}
	
	@Test
	public void testMultResults() throws InterruptedException, IOException, ServletException, JSONException, ApiException {
		
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
		ArrayList<Restaurant> actualList = yelpAPI.getRestaurants("ramen", 20);
		
		restaurantData yelpAPI2 = new restaurantData();
		ArrayList<Restaurant> actualList2 = yelpAPI2.getRestaurants("ramen", 20);
		
		System.out.println("/////////////////////////////////////////////////////////////");
		System.out.println("EXPECTED" + actualList.get(0).toString());
		System.out.println();
		System.out.println("ACTUAL" + actualList2.get(0).toString());
		System.out.println("/////////////////////////////////////////////////////////////");
		
		assertEquals(actualList.size(), actualList2.size());
		for(int i=0; i<actualList.size(); i++) {

			assertEquals(actualList.get(i).getAddress(), actualList2.get(i).getAddress());
			assertEquals(actualList.get(i).getMinsAway(), actualList2.get(i).getMinsAway());
			assertEquals(actualList.get(i).getName(), actualList2.get(i).getName());
			assertEquals(actualList.get(i).getPhoneNum(), actualList2.get(i).getPhoneNum());
			assertEquals(actualList.get(i).getPricing(), actualList2.get(i).getPricing());
			assertEquals(actualList.get(i).getWebsite(), actualList2.get(i).getWebsite());
		}
		
//		
//		assertEquals(expectedList.get(0).getAddress(), actualList.get(0).getAddress());
//		assertEquals(expectedList.get(0).getMinsAway(), actualList.get(0).getMinsAway());
//		assertEquals(expectedList.get(0).getName(), actualList.get(0).getName());
//		assertEquals(expectedList.get(0).getPhoneNum(), actualList.get(0).getPhoneNum());
//		assertEquals(expectedList.get(0).getPricing(), actualList.get(0).getPricing());
//		assertEquals(expectedList.get(0).getWebsite(), actualList.get(0).getWebsite());
		
	}

}
