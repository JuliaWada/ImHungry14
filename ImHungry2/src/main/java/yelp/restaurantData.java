package yelp;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String foodName = request.getParameter("query");
		int numResultsToShow = Integer.parseInt(request.getParameter("numResults").trim());
		System.out.println();
		System.out.println("here!!!!!!!!!!");
		System.out.println();
		
		
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>


		<script>

			var API_KEY_YELP = 'YJlrOwrflvQYjRaCRuc7qI9KbQL0CEkIP13-glWa8IFE3tUxS9pKhmmjtYgVpt7vKi3YnVbxokgMm9RyOZMth6ia3QgOHSGuwb7Eop7wl-pJGclJx-1s2ChLYYF2XHYx';
			var CLIENT_ID_YELP = 'uZhpw9YgNvae3jxqHr1gNw';
	
	var foodName = sessionStorage.getItem("foodName");
	var numResultsToDisplay = sessionStorage.getItem("numResultsToDisplay");
	
 	var myurl = "https://api.yelp.com/v3/businesses/search?term=" + foodName + "&latitude=34.020566&longitude=-118.285443"; 
	 $.ajax({
         url: myurl,
         headers: {
          'Authorization': "Bearer YJlrOwrflvQYjRaCRuc7qI9KbQL0CEkIP13-glWa8IFE3tUxS9pKhmmjtYgVpt7vKi3YnVbxokgMm9RyOZMth6ia3QgOHSGuwb7Eop7wl-pJGclJx-1s2ChLYYF2XHYx",
          'Access-Control-Allow-Origin': "*",
      },
         method: 'GET',
         dataType: 'json',
         success: function(data){
             console.log('success: '+data);
         }
      
	 }); 
	  

	 
/* 	
	var data = null;
	var xhttp = new XMLHttpRequest();
	
	var yelpString = "?term=" + foodName + "&latitude=34.020566&longitude=-118.285443"  
	xhttp.open("GET", "https://api.yelp.com/v3/businesses/search" + yelpString, true);
	xhttp.setRequestHeader("Authorization", "Bearer " + API_KEY);
	xhttp.setRequestHeader("Access-Control-Allow-Origin", "*");
	
	xhttp.onreadystatechange = function () {
		document.getElementById("collageContainer").innerHTML = this.responseText;
	}
	xhttp.addEventListener("readystatechange", function () {
	  if (this.readyState === 4) {
	    data = JSON.parse(this.responseText);
	    console.log(data);
	  }
	});   
	xhttp.send(data);   */
	//xhttp.send();
	console.log("yelp request sent to backend");
	
	function sendMessage() {
		console.log("javascript is working?");
	}
	
	</script>
	}

}
