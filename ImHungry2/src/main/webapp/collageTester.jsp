<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
	<head>
	</head>
	<body>
		<h2>Hello World!</h2>
		<form >
			Search: <input type="text" name="query" id="searchInput"><br/>
			Num Results: <input type="text" name="numResults" id="numInput"> <br/>
			<button type="button" onclick="sendingSearch()">Submit</button>
			
			
		</form>

		
		
		<script>
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
			var recipeReady = false;
			var collageReady = false;
			function sendingSearch() {
				console.log("beginning search");
				var food = document.getElementById('searchInput').value;
				var numResults = document.getElementById('numInput').value;
				going(food, numResults);
				redirection();
			}	
			
			function going(toSend, num) {
				var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function () {
					collageReady = true;
				}
				xhttp.open("POST", "collageData?extra=settingVariables&query=" + toSend + "&numResults=" + num, true);
				xhttp.send();
				
				console.log("collage Data sent to backend");
			}
			
			function redirection() {
				window.location.href = "collageResulttester.jsp";
			}
		</script>
	</body>
</html>
