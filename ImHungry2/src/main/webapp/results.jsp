<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
			<div id = "dropdown">
				<div id = "dropText"></div>
				<span id = "dropAction">&#9660</span>
			</div>
			<div id = "dropdownContent">
				<ul>
					<li>Favorites</li>
					<li>To Explore</li>
					<li>Do Not Show</li>
				</ul>
			</div>
			<button onclick = "toManageList()" class = "button">Manage List</button>
			<button onclick = "toSearchPage()" class = "button">Return to Search</button>
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
		<div id ="restaurantDiv">
			
		</div>
		<div id ="recipeDiv">
		
		</div>
	</div>
	<script>
		
	
	function loadPage(){
		var foodName = sessionStorage.getItem("foodName");
		document.querySelector("#foodname").innerHTML = foodName;
		var query = "";
		var num = 0;
		<%HttpSession session2 = request.getSession();
		String query = (String) session2.getAttribute("query");
		int num = Integer.valueOf((String) session2.getAttribute("numResults"));%>
		query = '<%=query%>';
		num =<%=num%>;
		getCollage(query);
		getRecipes(query, num);
	}
	function toManageList(){
		window.location.href = "listMgmt.jsp";
	}
	function toSearchPage(){
		
		window.location.href = "index.jsp";
	}
	
	document.querySelector("#dropAction").onclick = function(){
		document.querySelector("#dropdownContent").style.visibility = "visible";
	}
	
	
	var list = document.getElementsByTagName("li");
	for(let i = 0; i < list.length; i++){
		list[i].onclick = function(){
			document.querySelector("#dropText").innerHTML = list[i].innerHTML;
			document.querySelector("#dropdownContent").style.visibility = "hidden";
		}
	}
	
	function getCollage(toSend) {
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			document.getElementById("collageDiv").innerHTML = this.responseText;
		}
		xhttp.open("POST", "collageData?query=" + toSend, true);
		xhttp.send();
		console.log("collage Data sent to backend");
	}

	function getRecipes(toSend, numResults) {
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			document.getElementById("recipeDiv").innerHTML = this.responseText;
		}
		xhttp.open("POST", "recipeData?query=" + toSend + "&numResults="
				+ numResults, true);
		xhttp.send();
		console.log("recipe data sent to Backend");
	}
	
	</script>
</body>
</html>