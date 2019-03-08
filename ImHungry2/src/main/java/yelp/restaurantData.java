package yelp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.*;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;

import okhttp3.*;
import okhttp3.Request.Builder;
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
			e.printStackTrace();
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
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
	 * @return restaurantArray 
	 */
	public ArrayList<Restaurant> checkDoNotShow(ArrayList<Restaurant> restaurantArray, ArrayList<Object> doNotShowList) {
		ArrayList<Restaurant> editedArray = new ArrayList<Restaurant>();

		if(restaurantArray.size() != 0 && doNotShowList.size() !=0) {									//array list is not empty
			for(int i=0; i<restaurantArray.size(); i++) {
				int matchFound = 0;											//flag
				String restArrayAddr = restaurantArray.get(i).getAddress();	
				for(int j=0; j<doNotShowList.size(); j++) {
					if(doNotShowList.get(j).getClass().getName().equals("yelp.Restaurant")) {
						Restaurant DNSrestaurant = (Restaurant)doNotShowList.get(j);
						String dontShowRestAddr = DNSrestaurant.getAddress();
						if(restArrayAddr.equals(dontShowRestAddr)) {		//compare addresses
							matchFound = 1;	
						}
					}
				}
				if(matchFound == 0) {										//not in "Do Not Show" list. Add to the array list.
					editedArray.add(restaurantArray.get(i));
				}
			}
		}
		else {
			return restaurantArray;
		}
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
		
		if(restaurantArray.size() != 0 && favorites.size() != 0) {									//array list is not empty
			for(int i=0; i<restaurantArray.size(); i++) {
				int matchFound = 0;											//flag
				String restArrayAddr = restaurantArray.get(i).getAddress();	
				for(int j=0; j<favorites.size(); j++) {
					if(favorites.get(j).getClass().getName().equals("yelp.Restaurant")) {
						Restaurant favesRestaurant = (Restaurant)favorites.get(j);
						String favesAddr = favesRestaurant.getAddress();
						if(restArrayAddr.equals(favesAddr)) {		//compare addresses
							matchFound = 1;					
						}
					}
				}
				if(matchFound == 1) {										//in the "Favorites" list. Add to front of the array list.
					editedArray.add(restaurantArray.get(i));
				}
				else {
					notInList.add(restaurantArray.get(i));
				}
			}
		}
		else {
			return restaurantArray;
		}
		
		editedArray.addAll(notInList);
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
		
		if(numResultsToShow > 50) {
			numResultsToShow = 50;
		}

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
    	
    	restaurantArray = jsonResponse(restaurantArray, jsonObject, numResultsToShow);
        	
        return restaurantArray;
		
	}
	
	/**
	 * 
	 * @param restaurantArray
	 * @param jsonObject
	 * @param numResultsToShow
	 * 
	 * jsonResponse checks the JSON Object response and grabs the necessary fields for Restaurants.
	 * Puts them into an ArrayList and returns it.
	 * 
	 * @return
	 * @throws JSONException
	 * @throws ApiException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public ArrayList<Restaurant> jsonResponse(ArrayList<Restaurant> restaurantArray, JSONObject jsonObject, int numResultsToShow) throws JSONException, ApiException, InterruptedException, IOException {
		JSONArray myResponse = (JSONArray)jsonObject.get("businesses");

        if(myResponse.length() != 0) {
        	
        	for(int i=0; i<numResultsToShow; i++) {
            	String rwebsite = "", rphone = "", rpricing = "";
            	
            	//NAME
            	String rname = myResponse.getJSONObject(i).getString("name");
            	
            	//URL
            	rwebsite = myResponse.getJSONObject(i).getString("url");
            	
            	//PHONE NUMBER
            	if((!myResponse.getJSONObject(i).has("display_phone")) 
            			|| myResponse.getJSONObject(i).getString("display_phone").equals("")  ) {
            		rphone = "No phone number available";
            	}
            	else {
            			rphone = myResponse.getJSONObject(i).getString("display_phone");
            	}
            	
            	//PRICE
            	if((!myResponse.getJSONObject(i).has("price")) 
            			|| myResponse.getJSONObject(i).getString("price").equals("")) {
            		rpricing = "No price available";
            	}
            	else {
            		rpricing = myResponse.getJSONObject(i).getString("price");
            	}
            	
            	String raddress = "";
 
            	//LOCATION
                JSONObject location = (JSONObject) myResponse.getJSONObject(i).get("location");
                JSONArray address = (JSONArray) location.get("display_address");
                for(int j=0; j<address.length(); j++) {
                	raddress += address.get(j) + " ";
                }
                raddress = raddress.trim();
                int rminaway = getDrivingTime(raddress);
                
                Restaurant r = new Restaurant(rname, rwebsite, raddress, rphone, rpricing, rminaway);
                restaurantArray.add(r);
            }
        	
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

		DirectionsResult request =  DirectionsApi.getDirections(gcontext, "Tommy+Trojan", formatAddress).await();
		
		long routeSeconds = request.routes[0].legs[0].duration.inSeconds;
		
		routeMin = routeSeconds / 60;
			
		return Math.toIntExact(routeMin);
		
	}

}














