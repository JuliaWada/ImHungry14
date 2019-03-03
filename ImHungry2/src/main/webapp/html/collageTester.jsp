<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="collageTester.css"/>
</head>
<body onload="sendMessage()">
<h2>Hello World!</h2>
<form >
Search: <input type="text" name="fname" id="searchInput"><br/>
Num: <input type="text" name="numresults" id="numInput"> <br/ >
<button type="button" onclick="sendingSearch()">Submit</button>

</form>
<div id="collageContainer">
</div>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>


<script >
/*
	var API_KEY = 'YJlrOwrflvQYjRaCRuc7qI9KbQL0CEkIP13-glWa8IFE3tUxS9pKhmmjtYgVpt7vKi3YnVbxokgMm9RyOZMth6ia3QgOHSGuwb7Eop7wl-pJGclJx-1s2ChLYYF2XHYx'
	var CLIENT_ID = 'uZhpw9YgNvae3jxqHr1gNw';
	
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
	  
    */
	 
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
	
	/*
	function sendingSearch() {
		console.log("beginning search");
		var toSend = document.getElementById('searchInput').value;
		console.log("search term: " + toSend);
		gettingCollage(toSend);
	}
	
	function gettingCollage(toSend) {
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function () {
			document.getElementById("collageContainer").innerHTML = this.responseText;
		}
		xhttp.open("POST", "collagedata?query=" + toSend, true);
		xhttp.send();
		console.log("sent to backend");
	} */
	
</script>
</body>
</html>
