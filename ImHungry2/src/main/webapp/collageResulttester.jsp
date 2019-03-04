<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="collageTester.css" />
<title>Result</title>
</head>
<body onload="readData()">

	<div id="collageContainer"></div>
	<button type="button" onclick="testReload()">Reload Testing</button>
	<div id="recipeContainer"></div>
	<script>
		var query = "";
		var num = 0;
		function readData() {
			<%HttpSession session2 = request.getSession();
			String query = (String) session2.getAttribute("query");
			int num = Integer.valueOf((String) session2.getAttribute("numResults"));%>
			query = '<%=query%>';
			num =<%=num%>;
			console.log("query: " + query);
			console.log("num: " + num);

			getCollage(query);
			getRecipes(query, num);
			
			
		}

		function testReload() {
			window.location.href = "testReload.jsp";
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
			xhttp.onreadystatechange = function() {
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
			xhttp.onreadystatechange = function() {
				document.getElementById("recipeContainer").innerHTML = this.responseText;
			}
			xhttp.open("POST", "recipeData?query=" + toSend + "&numResults="
					+ numResults, true);
			xhttp.send();
			console.log("recipe data sent to Backend");
		}
		
		/**
		 * getResataurants
		 *
		 */
		 
		 
	</script>
</body>
</html>













