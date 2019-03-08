package yelp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.*;

import com.google.maps.DirectionsApi;
//import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;

import okhttp3.*;
import okhttp3.Request.Builder;
import scraping.Recipe;
import yelp.Restaurant;

/**
 * Servlet implementation class restaurantData
 */
public class restaurantData extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public restaurantData() {
        super();
        System.out.println("In yelp servlet constructor");
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		
		String foodName = request.getParameter("query");                       // term
        int numResultsToShow = Integer.parseInt(request.getParameter("numResults").trim());		 //limit
		
        ArrayList<Restaurant> restaurantArray = new ArrayList<Restaurant>();
		try {
			restaurantArray = getRestaurants(foodName, numResultsToShow);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpSession session = request.getSession();
		ArrayList<Object> doNotShowList = (ArrayList<Object>) session.getAttribute("Do Not Show");
		ArrayList<Object> favorites = (ArrayList<Object>) session.getAttribute("Favorites");
		
		
		restaurantArray = checkDoNotShow(restaurantArray, doNotShowList);
		restaurantArray = checkFavorites(restaurantArray, favorites);
		
		
		for(int i=0; i<restaurantArray.size(); i++) {
        	Restaurant r = restaurantArray.get(i);
        	out.println("<div class =\"restaurantCard\" onclick = \"toRestaurantPage(this)\" \"id=\"restaurant" + i + "\">" +
        					"<div class =\"information\">" +
		    					"<p class = \"restName\">" + r.getName() + "</p>" +
		    					"<p>" + r.getAddress() + "</p>" +
		    					"<p>" + r.getMinsAway() + " minutes away </p>" +
		    				"</div>"+
        					"<div class =\"priceDiv\">" +
        						"<p>" + r.getPricing() + "</p>" +
        					"</div>"+
        			 	"</div>");
        }
		HttpSession session2 = request.getSession();
		ArrayList<Restaurant> stored = (ArrayList<Restaurant>) session2.getAttribute("restaurantList");
		for(int i=0; i<restaurantArray.size(); i++) {
			stored.add(restaurantArray.get(i));
		}
	}
	
	/**
	 * 
	 * @param restaurantArray
	 * @param doNotShowList
	 * 
	 * This function checks the returned restaurantArray with the ArrayList of restaurants in 
	 * the "Do Not Show" list. It then deletes any restaurant in the restaurant Array.
	 * 
	 * 
	 * @return restaurantArray with restaurants in list deleted
	 */
	public ArrayList<Restaurant> checkDoNotShow(ArrayList<Restaurant> restaurantArray, ArrayList<Object> doNotShowList) {
		ArrayList<Restaurant> editedArray = new ArrayList<Restaurant>();
		
//		System.out.println("restaurant array size: " + restaurantArray.size());
//		System.out.println("Do Not Show array size: " + doNotShowList.size());

		if(restaurantArray.size() != 0 && doNotShowList.size() !=0) {									//array list is not empty
			for(int i=0; i<restaurantArray.size(); i++) {
				int matchFound = 0;											//flag
				String restArrayAddr = restaurantArray.get(i).getAddress();	
				//System.out.println("restaurant array address: " + restArrayAddr);
				for(int j=0; j<doNotShowList.size(); j++) {
					if(doNotShowList.get(j).getClass().getName().equals("yelp.Restaurant")) {
						Restaurant DNSrestaurant = (Restaurant)doNotShowList.get(j);
						String dontShowRestAddr = DNSrestaurant.getAddress();
						//System.out.println("DNS array address: " + dontShowRestAddr);
						if(restArrayAddr.equals(dontShowRestAddr)) {		//compare addresses
							matchFound = 1;	
							//System.out.println("Match found.");
						}
						//System.out.println("UNO");
					}
					//System.out.println("DOS");
				}
				//System.out.println("TRES");
				if(matchFound == 0) {										//not in "Do Not Show" list. Add to the array list.
					editedArray.add(restaurantArray.get(i));
					//System.out.println("Adding to list, no match found:" + restaurantArray.get(i).getName());
				}
				//System.out.println();
			}
		}
		else {
			//System.out.println("Nothing to compare to.");
			return restaurantArray;
		}
		
		//System.out.println("Returning the edited array. Size: " + editedArray.size());
		return editedArray;
	}
	
	/**
	 * 
	 * @param restaurantArray
	 * @param favorites
	 * 
	 * This function checks the returned restaurantArray with the ArrayList of restaurants in 
	 * the "Favorites" list. It then moves any matching restaurants to the front of the array list.
	 * 
	 * @return
	 */
	public ArrayList<Restaurant> checkFavorites(ArrayList<Restaurant> restaurantArray, ArrayList<Object> favorites) {
		ArrayList<Restaurant> editedArray = new ArrayList<Restaurant>();
		ArrayList<Restaurant> notInList = new ArrayList<Restaurant>();	
		
		System.out.println("restaurant array size: " + restaurantArray.size());
		System.out.println("favorites array size: " + favorites.size());
		
		if(restaurantArray.size() != 0 && favorites.size() != 0) {									//array list is not empty
			for(int i=0; i<restaurantArray.size(); i++) {
				int matchFound = 0;											//flag
				String restArrayAddr = restaurantArray.get(i).getAddress();	
				System.out.println("restaurant array address: " + restArrayAddr);
				for(int j=0; j<favorites.size(); j++) {
					if(favorites.get(j).getClass().getName().equals("yelp.Restaurant")) {
						Restaurant favesRestaurant = (Restaurant)favorites.get(j);
						String favesAddr = favesRestaurant.getAddress();
						System.out.println("Favorites array address: " + favesAddr);
						if(restArrayAddr.equals(favesAddr)) {		//compare addresses
							matchFound = 1;					
							System.out.println("Match found.");
						}
					}
				}
				if(matchFound == 1) {										//in the "Favorites" list. Add to front of the array list.
					editedArray.add(restaurantArray.get(i));
					System.out.println("Adding to front of edited list:" + restaurantArray.get(i).getName());
				}
				else {
					notInList.add(restaurantArray.get(i));
					System.out.println("Adding to rest of list");
				}
			}
		}
		else {
			System.out.println("Nothing to compare to.");
			return restaurantArray;
		}
		
		editedArray.addAll(notInList);
		System.out.println("Returning the edited array");
		return editedArray;
	}

	

	/**
	 * 
	 * Inputs are an array list of restaurants, request, and response
	 * 
	 * getRestaurants makes call Yelp API in order to get name, address, url, phone number, and
	 * price of the restaurants that show up when the user searches for food. 
	 * 
	 * Returns an array list of Restaurants
	 * @throws JSONException 
	 * @throws InterruptedException 
	 * @throws ApiException 
	 * 
	 */
	public ArrayList<Restaurant> getRestaurants(String foodName, int numResultsToShow) throws ServletException, IOException, JSONException, ApiException, InterruptedException {
		ArrayList<Restaurant> restaurantArray = new ArrayList<Restaurant>(); 

		String API_KEY_YELP = "YJlrOwrflvQYjRaCRuc7qI9KbQL0CEkIP13-glWa8IFE3tUxS9pKhmmjtYgVpt7vKi3YnVbxokgMm9RyOZMth6ia3QgOHSGuwb7Eop7wl-pJGclJx-1s2ChLYYF2XHYx";
		
		// GET /businesses/search
        OkHttpClient client = new OkHttpClient();

        Request APIrequest = new Builder()
                .url("https://api.yelp.com/v3/businesses/search?term=" + foodName + "&latitude=34.020566&longitude=-118.285443" 
                		+ "&limit=" + numResultsToShow + "&sort_by=distance")
                .get()
                .addHeader("authorization", "Bearer " + API_KEY_YELP)
                .build();

        
    	Response APIresponse = client.newCall(APIrequest).execute();

    	JSONObject jsonObject = new JSONObject(APIresponse.body().string().trim());       // parser
    	
    	//ArrayList<Restaurant> jsonParse = new ArrayList<Restaurant>();
    	restaurantArray = jsonResponse(restaurantArray, jsonObject, numResultsToShow);
        	
        return restaurantArray;
		
	}
	
	public ArrayList<Restaurant> jsonResponse(ArrayList<Restaurant> restaurantArray, JSONObject jsonObject, int numResultsToShow) throws JSONException, ApiException, InterruptedException, IOException {
		JSONArray myResponse = (JSONArray)jsonObject.get("businesses");
        int numYelpResults = (int)jsonObject.get("total");


        System.out.println();
        System.out.println();

        System.out.println("Total Num of Results:");
        System.out.println(numYelpResults);
        System.out.println();
        
        //Perhaps API might return nothing! Should maybe add an error check to see if entire JSON object is null
        if(myResponse.length() != 0) {
        	
        	for(int i=0; i<numResultsToShow; i++) {
            	String rwebsite = "", rphone = "", rpricing = "";
            	
            	//NAME
            	String rname = myResponse.getJSONObject(i).getString("name");
            	System.out.println("name: " + rname);
            	
            	//URL
//            	if( (!myResponse.getJSONObject(i).has("url") && myResponse.getJSONObject(i).isNull("url") ) 
//            			|| myResponse.getJSONObject(i).getString("url").equals("")) {
//            		rwebsite = "No URL available";
//            	}
//            	else {
//            		if(myResponse.getJSONObject(i).getString("url").equals("")) {
//            			rphone = "No URL available";
//            		}
//            		else {
            			rwebsite = myResponse.getJSONObject(i).getString("url");
            		//}
            		
            	//}
            	System.out.println("website: " + rwebsite);
            	
            	//PHONE NUMBER
            	if(  (!myResponse.getJSONObject(i).has("display_phone") /*&& myResponse.getJSONObject(i).isNull("display_phone")*/) 
            			|| myResponse.getJSONObject(i).getString("display_phone").equals("")  ) {
            		rphone = "No phone number available";
            	}
//            	else {
//            		if(myResponse.getJSONObject(i).getString("display_phone").equals("")) {
//            			rphone = "No phone number available";
//            		}
            		else {
            			rphone = myResponse.getJSONObject(i).getString("display_phone");
            		//}
            		
            	}
            	System.out.println("phoneNum: " + rphone);
            	
            	//PRICE
            	if( (!myResponse.getJSONObject(i).has("price") /*&& myResponse.getJSONObject(i).isNull("price")*/ ) 
            			|| myResponse.getJSONObject(i).getString("price").equals("")) {
            		rpricing = "No price available";
            	}
//            	else {
//            		if(myResponse.getJSONObject(i).getString("price").equals("")) {
//            			rphone = "No price available";
//            		}
            		else {
            			rpricing = myResponse.getJSONObject(i).getString("price");
            		//}
            		
            	}
            	System.out.println("pricing: " + rpricing);
            	
            	String raddress = "";
 
            	//LOCATION
                JSONObject location = (JSONObject) myResponse.getJSONObject(i).get("location");
                JSONArray address = (JSONArray) location.get("display_address");
                for(int j=0; j<address.length(); j++) {
                	raddress += address.get(j) + " ";
                	System.out.println(address.get(j));
                }
                raddress = raddress.trim();
                System.out.println("address: " + raddress);
                int rminaway = getDrivingTime(raddress);
                System.out.println("minsAway: " + rminaway);
                
                Restaurant r = new Restaurant(rname, rwebsite, raddress, rphone, rpricing, rminaway);
                restaurantArray.add(r);
            }

            System.out.println();
            System.out.println();
        	
        }
        return restaurantArray;
	}

	/**
	 * 
	 * Input is a restaurantAddress.
	 * 
	 * getDrivingTime makes a call to the Google Directions API to get the duration of driving time
	 * it takes to get from Tommy Trojan to the restaurant.
	 * 
	 * Returns the driving time in minutes.
	 * @throws ApiException 
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	private int getDrivingTime(String restaurantAddress) throws ApiException, InterruptedException, IOException {
		String API_KEY_GOOGLE = "AIzaSyAozhhiSQVAAlrlAwnFRuYOVWX2bGkRUqk";
		long routeMin = 0;
		//set up key
	   	GeoApiContext gcontext = new GeoApiContext.Builder()
			    .apiKey(API_KEY_GOOGLE)
			    .build();
	   	String formatAddress = restaurantAddress.replace(" ", "+");
	   	System.out.println("Formatted address: " + formatAddress);

		DirectionsResult request =  DirectionsApi.getDirections(gcontext, "Tommy+Trojan", formatAddress).await();
		
		long routeSeconds = request.routes[0].legs[0].duration.inSeconds;
		System.out.println("Route in Seconds: " + routeSeconds);
		
		routeMin = routeSeconds / 60;
		System.out.println("Route in Minutes: " + routeMin);
			
		return Math.toIntExact(routeMin);
		
	}

}














