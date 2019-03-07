<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/results.css">
<meta charset="UTF-8">
<title>I'm Hungry</title>
</head>
<body onload = "loadPage()">
	<div id = "topDiv">
		<div id ="collageDiv">
		
		</div>
		<div id ="buttonDiv">
			<select class = "menu" id="listOptions">
				 <option value = "0"> </option>
				 <option value="1">Favorites</option>
   				 <option value="2">To Explore</option>
   				 <option value="3">Do Not Show</option>
			</select>
			<button id = "Lbutton" onclick = "toManageList()" class = "button">Manage List</button>
			<button id = "RTSbutton" onclick = "toSearchPage()" class = "button">Return to Search</button>
		</div>
	</div>
	<div id ="titleDiv">
		<h1>Results for <span id = "foodname"></span></h1>
	</div>
	<div id = "listTitleDiv">
		<div class = "name">Restaurants</div>
		<div class = "name">Recipes</div>
	</div>
	<div id= "resultDiv">
		<span id = "restuarantName" class = "name">Restaurants</span>
		<div id ="restaurantDiv">
			
		</div>
		<span id = "recipeName" class = "name">Recipes</span>
		<div id ="recipeDiv">
		
		</div>
	</div>
	<script>
		
	var pageLoaded = false;
	function loadPage(){
		pageLoaded = true;
		var foodName = sessionStorage.getItem("foodName");
		document.querySelector("#foodname").innerHTML = foodName;
		var query = "";
		var num = 0;
		<%HttpSession session2 = request.getSession();
		String query = (String) session2.getAttribute("query");
		int num = Integer.valueOf((String) session2.getAttribute("numResults"));%>
		query = "<%=query%>";
		num = <%=num%>;
		getCollage(query, num);
		//getRecipes(query, num);
		getRestaurants(query, num);
	}
	
	function toManageList(){
		var list = document.getElementById("listOptions");
		var option = list.options[list.selectedIndex].text;
		if(option != "") {
			var url = "listMgmt.jsp?name=" + option;
			window.location.href = url;
		}
	}
	function toSearchPage(){
		window.location.href = "index.jsp";
	}
	
	
	function getCollage(toSend, num) {
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			document.getElementById("collageDiv").innerHTML = this.responseText;
		}
		xhttp.open("POST", "collageData?query=" + toSend + "&numResults=" + num, true);
		xhttp.send();
		console.log("collage Data sent to backend");
	}

	/* function getRecipes(toSend, numResults) {
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			document.getElementById("recipeDiv").innerHTML = this.responseText;
		}
		xhttp.open("POST", "recipeData?query=" + toSend + "&numResults=" 
				+ numResults + "&action=results", true);
		xhttp.send();
		console.log("recipe data sent to Backend");
	} */
	
	function getRestaurants(toSend, numResults) {
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			document.getElementById("restaurantDiv").innerHTML = this.responseText;
		}
		xhttp.open("POST", "restaurantData?query=" + toSend + "&numResults=" + numResults + "&action=results", true);
		xhttp.send();
		console.log("restaurant data sent to BACKEND!");

	}
	
	function toRecipePage(query){
<<<<<<< HEAD
		var xhttp = new XMLHttpRequest();
		xhttp.open("POST", "recipeData?query=" + query + "&action=page", false);
		xhttp.send();
=======
>>>>>>> julia
		var actual = query.querySelector(".recipeTitle").textContent;
		console.log(actual);
		window.location.href = "recipe.jsp?title=" + actual;
	}
	
	function toRestaurantPage(query){
		var xhttp = new XMLHttpRequest();
		xhttp.open("POST", "restaurantData?query=" + query + "&action=page", false);
		xhttp.send();
		var actual = query.querySelector(".restName").textContent;
		window.location.href = "restuarant.jsp?title=" + actual;
	}
	
	
	</script>
</body>
</html>