<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/restaurant.css">
<title>I'm Hungry</title>
</head>
<body onload="loadRecipeTitle()">
<div id ="container">
	<div id = "infoDiv">
		<h1>Homeade Chicken Ramen</h1>
		<img src ="">
		<p>Prep Time: <span id = "prepTime">21 min</span></p>
		<br>
		<p>Cook Time: <span id ="url">21 min</span></p>
		<br>
		<h3>Ingredients:</h3>
		<ul id = "ingredientsList"></ul>
		<br>
		<h3>Instructions:</h3>
		<ul id = "instructionsList"></ul>
	</div>
	<div id ="buttonDiv">
		<button onclick = "toPrintVersion()" class = "button">Printable Version</button>
		<button onclick = "toResults()" class = "button">Back to Results</button>
		<select class = "menu" id="listOptions">
				 <option value = "0"> </option>
				 <option value="1">Favorites</option>
   				 <option value="2">To Explore</option>
   				 <option value="3">Do Not Show</option>
			</select>
			<br>
		<button onclick ="toAddtoList()" class = "button">Add to List</button>
</div>
<script>
var name;
function loadRecipeTitle() {
	var url = window.location.href;
	var split = url.split("?");
	var args = split[1];
	console.log(args);
	split = args.split("=");
	name = split[1];
	console.log(name);
	name = name.replace(/%20/g, " ");
	console.log(name);
	
	var xhttp = new XMLHttpRequest();
	console.log("Got into here");
	xhttp.onreadystatechange = function () {
		document.getElementById("infoDiv").innerHTML = this.responseText;
	}
	var clean = encodeURIComponent(name);
	console.log(clean);
	xhttp.open("POST", "recipeData?query=" + clean + "&action=page", true);
	xhttp.send();
}

function toPrintVersion(){
	document.querySelector("#buttonDiv").style.visibility = "hidden";
}
function toAddtoList() {
	var list = document.getElementById("listOptions");
	var option = list.options[list.selectedIndex].text;
	if(option != "") {
		console.log("Adding " + name + " to list ");
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function () {
			console.log("Successfully added");
		}
		var clean = encodeURIComponent(name);
		console.log(clean);
		xhttp.open("POST", "listMgmtData?action=add&type=recipe&itemName=" + clean + "&listName=" + option, true);
		xhttp.send();
	}
}

function toResults(){
	window.location.href = "results.jsp";
}

	</script>
</body>
</html>