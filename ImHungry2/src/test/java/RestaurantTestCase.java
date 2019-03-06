import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;

import yelp.*;

import org.junit.Test;


public class RestaurantTestCase {

	@Test
	public void testOneResult() throws InterruptedException, IOException, ServletException {
		ArrayList<Restaurant> expectedList = new ArrayList<Restaurant>();
		Restaurant restaurantResult = new Restaurant();
		restaurantResult.setName("Ramen Kenjo");
		restaurantResult.setWebsite("https://www.yelp.com/biz/ramen-kenjo-los-angeles-2?adjust_creative=uZhpw9YgNvae3jxqHr1gNw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=uZhpw9YgNvae3jxqHr1gNw");
		restaurantResult.setPhoneNum("No phone number available");
		restaurantResult.setPricing("No price available");
		restaurantResult.setAddress("929 W Jefferson Blvd USC Village Los Angeles, CA 90089");
		restaurantResult.setMinsAway(7);
		
		
		restaurantData yelpAPI = new restaurantData();
		ArrayList<Restaurant> actualList = new ArrayList<Restaurant>();
		actualList = yelpAPI.getRestaurants(actualList, "ramen", 1);
		
		System.out.println(expectedList.get(0).toString());
		System.out.println(actualList.get(0).toString());
		
		assertEquals(expectedList.size(), actualList.size());
		assertEquals(expectedList.get(0).toString(), actualList.get(0).toString());
		
	}
	
//	@Test
//	public void testDrivingTime() {
//		
//	}

}
