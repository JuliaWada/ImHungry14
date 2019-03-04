package yelp;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;

import okhttp3.*;
import okhttp3.Request.Builder;

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

		String API_KEY_YELP = "YJlrOwrflvQYjRaCRuc7qI9KbQL0CEkIP13-glWa8IFE3tUxS9pKhmmjtYgVpt7vKi3YnVbxokgMm9RyOZMth6ia3QgOHSGuwb7Eop7wl-pJGclJx-1s2ChLYYF2XHYx";
		String CLIENT_ID_YELP = "uZhpw9YgNvae3jxqHr1gNw";
		
		
		// GET /businesses/search
        OkHttpClient client = new OkHttpClient();


        String foodName = request.getParameter("query");                       // term
        int numResultsToShow = Integer.parseInt(request.getParameter("numResults").trim());		 //limit
        //String myurl = "https://api.yelp.com/v3/businesses/search?term=" + foodName + "&latitude=34.020566&longitude=-118.285443" + "&limit=" + numResultsToShow; 
        //String myurl = "https://api.yelp.com/v3/businesses/search?term=" + foodName + "&location=Los Angeles, CA" + "&limit=" + numResultsToShow; 

 
        Request APIrequest = new Builder()
                .url("https://api.yelp.com/v3/businesses/search?term=" + foodName + "&latitude=34.020566&longitude=-118.285443" + "&limit=" + numResultsToShow)
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
            
            for(int i=0; i<numResultsToShow; i++) {
            	System.out.println(myResponse.getJSONObject(i).getString("name"));
                System.out.println(myResponse.getJSONObject(i).getString("price"));
                System.out.println(myResponse.getJSONObject(i).getString("display_phone"));
                System.out.println(myResponse.getJSONObject(i).getString("url"));
                
                JSONObject location = (JSONObject) myResponse.getJSONObject(i).get("location");
                JSONArray address = (JSONArray) location.get("display_address");
                for(int j=0; j<address.length(); j++) {
                	System.out.println(address.get(j));
                }
            }
//            
//            System.out.println(myResponse.getJSONObject(0).getString("name"));
//            System.out.println(myResponse.getJSONObject(0).getString("price"));
//            System.out.println(myResponse.getJSONObject(0).getString("display_phone"));
//            System.out.println(myResponse.getJSONObject(0).getString("url"));
//            
//            JSONObject location = (JSONObject) myResponse.getJSONObject(0).get("location");
//            JSONArray address = (JSONArray) location.get("display_address");
//            for(int i=0; i<address.length(); i++) {
//            	System.out.println(address.get(i));
//            }
            
            
            
            
            System.out.println();
            System.out.println();
            
        	
        }
        catch (IOException e) {
        	System.out.println("IO Exception in restauarant Data!!!!");
//            e.printStackTrace();
        } catch (JSONException e) {
        	System.out.println("JSON Exception in restauarant Data!!!!");
        	System.out.println("------------------- STACKTRACE --------------------");
        	System.out.println("Hello error: " + e.getMessage());
        	System.out.println();
			e.printStackTrace();
			System.out.println("----------------------- END OF STACKTRACE ------------------");
		}
		

		
//		Sting data = null;
//		var xhttp = new XMLHttpRequest();
//		
//		var yelpString = "?term=" + foodName + "&latitude=34.020566&longitude=-118.285443"  
//		xhttp.open("GET", "https://api.yelp.com/v3/businesses/search" + yelpString, true);
//		xhttp.setRequestHeader("Authorization", "Bearer " + API_KEY);
//		xhttp.setRequestHeader("Access-Control-Allow-Origin", "*");
//		
//		xhttp.onreadystatechange = function () {
//			document.getElementById("collageContainer").innerHTML = this.responseText;
//		}
//		xhttp.addEventListener("readystatechange", function () {
//		  if (this.readyState === 4) {
//		    data = JSON.parse(this.responseText);
//		    console.log(data);
//		  }
//		});   
//		xhttp.send(data);   
//		//xhttp.send();
//		console.log("yelp request sent to backend");
		
	

		}

}
