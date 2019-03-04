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
		
		PrintWriter out = response.getWriter();
		ArrayList<Restaurant> restaurantArray = new ArrayList<Restaurant>();


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
            	String rname = myResponse.getJSONObject(i).getString("name");
            	String rwebsite = myResponse.getJSONObject(i).getString("url");
            	String rphone = myResponse.getJSONObject(i).getString("display_phone");
            	String rpricing = myResponse.getJSONObject(i).getString("price");
            	int rminaway = 10;
            	String raddress = "";
            	
            	System.out.println(rname);
                System.out.println(rpricing);
                System.out.println(rphone);
                System.out.println(rwebsite);

                JSONObject location = (JSONObject) myResponse.getJSONObject(i).get("location");
                JSONArray address = (JSONArray) location.get("display_address");
                for(int j=0; j<address.length(); j++) {
                	raddress += address.get(j) + " ";
                	System.out.println(address.get(j));
                }
                Restaurant r = new Restaurant(rname, rwebsite, raddress, rphone, rpricing, rminaway);
                restaurantArray.add(r);
                
//                double distanceMeters = myResponse.getJSONObject(i).getDouble("distance");
//                double distanceKilometers = distanceMeters / 1000.0;
//                double timeHour = distanceKilometers / 50.0;
//                double timeMin = timeHour * 60.0;
//                int timeMinInt = (int)timeMin;
//               
//                System.out.println("Distance in Meters: " + distanceMeters);
//                System.out.println("Min driving (double): " + timeMin);
//                System.out.println("Min driving (int): " + timeMinInt);
//                
//                System.out.println();
                
            }

            System.out.println();
            System.out.println();
            
            
            for(int i=0; i<restaurantArray.size(); i++) {
            	Restaurant r = restaurantArray.get(i);
            	out.println("<div>" +
            					"<p>" + r.getName() + "</p>" +
            					"<p>" + r.getAddress() + "</p>" +
            					"<p>" + r.getMinsAway() + "</p>" +
            					"<p>" + r.getPricing() + "</p>" +
            			 	"</div>");
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

		}

}
