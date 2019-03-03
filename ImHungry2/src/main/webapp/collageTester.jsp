<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="index.css"/>
	</head>
	<body>
		<h2>Hello World!</h2>
		<form >
			Search: <input type="text" name="query" id="searchInput"><br/>
			Num Results: <input type="text" name="numResults" id="numInput"> <br/>
			<button type="button" onclick="sendingSearch()">Submit</button>
		</form>
		<div id="collageContainer"></div>
		<div id="recipeContainer"></div>
		
		<script >
			/**
			 * sendingSearch()
			 * ------------------------------------
			 * grabs the necessary data from the html inputs to send to the backend to get necesssary results
			 * for restaurants and recipes and collage
			 * Inserts html with the required information by inserting into the innerHTML
			 * 
			 *
			 *
			 *
			*/
			function sendingSearch() {
				console.log("beginning search");
				var toSend = document.getElementById('searchInput').value;
				var numResults = document.getElementById('numInput').value;
				console.log("search term: " + toSend);
				console.log("number of results: " + numResults);
				getCollage(toSend);
				getRecipes(toSend, numResults);
				
			}
			/**
			 * getCollage
			 * --------------------------
			 * Takes in the search query that will use AJAX calls to reach the backend to scrape the top
			 * 10 results for the search query
			 *
			 * @param: toSend a String that contains the term that user wants food recommendations for
			 * @see: HTML
			 *
			*/
			
			function getCollage(toSend) {
				var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function () {
					document.getElementById("collageContainer").innerHTML = this.responseText;
				}
				xhttp.open("POST", "collageData?query=" + toSend, true);
				xhttp.send();
				console.log("collage Data sent to backend");
			}
			
			/**
			 * getRecipes
			 * --------------------------
			 * Takes in the search query that will use AJAX calls to reach the backend to scrape the top
			 * results for recipes sorted by shortest prep time
			 *
			 * @param: toSend a String that contains the term that user wants food recommendations for
			 * @param: numResults an int that represents the number of results that the wants to see for the query
			 * @see: HTML
			 *
			*/
			
			function getRecipes(toSend, numResults) {
				var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function () {
					document.getElementById("recipeContainer").innerHTML = this.responseText;
				}
				xhttp.open("POST", "RecipeData?query=" + toSend + "&numResults=" + numResults, true);
				xhttp.send();
				console.log("recipe data sent to Backend");
			}
		</script>
	</body>
</html>
