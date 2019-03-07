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
		<button onclick = "toAddtoList()" class = "button">Add to List</button>
	</div>
</div>
<script>

function toPrintVersion(){
	document.querySelector("#buttonDiv").style.visibility = "hidden";
	document.querySelector("#dropdownContent").style.visibility = "hidden";
}

function toResults(){
	window.location.href = "results.jsp";
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
	xhttp.open("POST", "recipeData?query=" + name + "&action=page", true);
	xhttp.send();
}

function toAddToList() {
	console.log("Adding " + name + " to list ");
	var xhttp = new XMLHttpRequest();
	xhttp.open("POST", "listMgmtData?action=add&itemName=" + name + "&listName=")
}
	</script>
</body>
</html>