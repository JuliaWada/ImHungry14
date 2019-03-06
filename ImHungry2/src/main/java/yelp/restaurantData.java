package yelp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
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
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		System.out.println("In yelp servlet init");
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println();
		System.out.println("in yelp servlet service");
		System.out.println();
		
		PrintWriter out = response.getWriter();
		ArrayList<Restaurant> restaurantArray = new ArrayList<Restaurant>();
		restaurantArray = getRestaurants(restaurantArray, request, response);
		for(int i=0; i<restaurantArray.size(); i++) {
        	Restaurant r = restaurantArray.get(i);
        	out.println("<div>" +
        					"<p>" + r.getName() + "</p>" +
        					"<p>" + r.getAddress() + "</p>" +
        					"<p>" + r.getMinsAway() + "</p>" +
        					"<p>" + r.getPricing() + "</p>" +
        					"<p>" + r.getMinsAway() + " minutes away </p>" +
        			 	"</div>");
        }


		}
	/**
	 * 
	 * Inputs are an array list of restaurants, request, and response
	 * 
	 * getRestaurants makes call Yelp API in order to get name, address, url, phone number, and
	 * price of the restaurants that show up when the user searches for food. 
	 * 
	 * Returns an array list of Restaurants
	 * 
	 */

	private ArrayList<Restaurant> getRestaurants(ArrayList<Restaurant> restaurantArray, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String API_KEY_YELP = "YJlrOwrflvQYjRaCRuc7qI9KbQL0CEkIP13-glWa8IFE3tUxS9pKhmmjtYgVpt7vKi3YnVbxokgMm9RyOZMth6ia3QgOHSGuwb7Eop7wl-pJGclJx-1s2ChLYYF2XHYx";
		
		// GET /businesses/search
        OkHttpClient client = new OkHttpClient();

        String foodName = request.getParameter("query");                       // term
        int numResultsToShow = Integer.parseInt(request.getParameter("numResults").trim());		 //limit

        Request APIrequest = new Builder()
                .url("https://api.yelp.com/v3/businesses/search?term=" + foodName + "&latitude=34.020566&longitude=-118.285443" 
                		+ "&limit=" + numResultsToShow + "&sort_by=distance")
                .get()
                .addHeader("authorization", "Bearer " + API_KEY_YELP)
                .build();

        try {
        	Response APIresponse = client.newCall(APIrequest).execute();

        	JSONObject jsonObject = new JSONObject(APIresponse.body().string().trim());       // parser
            JSONArray myResponse = (JSONArray)jsonObject.get("businesses");
            int numYelpResults = (int)jsonObject.get("total");


            System.out.println();
            System.out.println();

            System.out.println("Total Num of Results:");
            System.out.println(numYelpResults);
            System.out.println();
            
            //TODO Perhaps API might return nothing! Should maybe add an error check to see if entire JSON object is null
            if(myResponse.length() != 0) {
            	
            	for(int i=0; i<numResultsToShow; i++) {
                	String rwebsite, rphone, rpricing = "";
                	
                	String rname = myResponse.getJSONObject(i).getString("name");
                	System.out.println(rname);
                	
                	if(!myResponse.getJSONObject(i).has("url") && myResponse.getJSONObject(i).isNull("url")) {
                		rwebsite = "No URL available";
                	}
                	else {
                		rwebsite = myResponse.getJSONObject(i).getString("url");
                	}
                	System.out.println(rwebsite);
                	
                	if(!myResponse.getJSONObject(i).has("display_phone") && myResponse.getJSONObject(i).isNull("display_phone")) {
                		rphone = "No phone number available";
                	}
                	else {
                		rphone = myResponse.getJSONObject(i).getString("display_phone");
                	}
                	System.out.println(rphone);
                	
                	if(!myResponse.getJSONObject(i).has("price") && myResponse.getJSONObject(i).isNull("price")) {
                		rpricing = "No price available";
                	}
                	else {
                		rpricing = myResponse.getJSONObject(i).getString("price");
                	}
                	System.out.println(rpricing);
                	
                	String raddress = "";
     
                    JSONObject location = (JSONObject) myResponse.getJSONObject(i).get("location");
                    JSONArray address = (JSONArray) location.get("display_address");
                    for(int j=0; j<address.length(); j++) {
                    	raddress += address.get(j) + " ";
                    	System.out.println(address.get(j));
                    }
                    raddress = raddress.trim();
                    int rminaway = getDrivingTime(raddress);
                    
                    Restaurant r = new Restaurant(rname, rwebsite, raddress, rphone, rpricing, rminaway);
                    restaurantArray.add(r);
                }

                System.out.println();
                System.out.println();
            	
            }

            
            

        }
        catch (IOException e) {
        	System.out.println("IO Exception in restauarant Data!!!!");
//            e.printStackTrace();
        } catch (JSONException e) {
        	System.out.println("JSON Exception in restauarant Data!!!!");
        	System.out.println("Hello error: " + e.getMessage());
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
	 */
	private int getDrivingTime(String restaurantAddress) {
		String API_KEY_GOOGLE = "AIzaSyAozhhiSQVAAlrlAwnFRuYOVWX2bGkRUqk";
		long routeMin = 0;
		//set up key
	   	GeoApiContext gcontext = new GeoApiContext.Builder()
			    .apiKey("AIzaSyAozhhiSQVAAlrlAwnFRuYOVWX2bGkRUqk")
			    .build();
	   	String formatAddress = restaurantAddress.replace(" ", "+");
	   	System.out.println("Formatted address: " + formatAddress);
	   	try {
			DirectionsResult request =  DirectionsApi.getDirections(gcontext, "Tommy+Trojan", formatAddress).await();
			
			long routeSeconds = request.routes[0].legs[0].duration.inSeconds;
			System.out.println("Route in Seconds: " + routeSeconds);
			
			routeMin = routeSeconds / 60;
			System.out.println("Route in Minutes: " + routeMin);
			
			
			
		} catch (ApiException e) {
			System.out.println("API Exception");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("IE Exception");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Exception");
			e.printStackTrace();
		}
	   		

		return Math.toIntExact(routeMin);
		
	}

}














