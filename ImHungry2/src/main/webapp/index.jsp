

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/index.css">
<meta charset="UTF-8">
<title>I'm Hungry</title>
</head>
<body>
	<div id = "container">
		<div id = "titleDiv">
			<h1>I'm Hungry</h1>
		</div>
		<div id = "searchDiv">
			<div id = "hoverMessage">
				<img src = "images/hoverMessage.png">
			</div>
			<div id = "formDiv">
				<form method = "POST" action = "TestServlet">
					<input id ="searchBar" placeholder = "Enter food...">
					<input id = "numResults" value ="5">
				</form>
			</div>
		</div>
		<button onclick = "toResults()">Feed Me!</button>
	</div>
<script>
	
	
	function toResults(){
	/* 	var xhttp = new XMLHttpRequest();
	 	var food = document.querySelector("#searchBar").value;
	  	xhttp.open("POST", "TestServlet?name=" + food, false);
		xhttp.send(); */
		var foodName = document.querySelector("#searchBar").value;
		sessionStorage.setItem("foodName", foodName);
		
		var numResultsToDisplay = document.querySelector("#numResults").value;
		sessionStorage.setItem("numResultsToDisplay", numResultsToDisplay);
		
		if (foodName == ""){
			alert("Please Enter Food");
		} else {
			window.location.href = "results.jsp";  
		}
		
	}
	 document.querySelector("#numResults").onmouseenter = function(){
		document.querySelector("#hoverMessage").style.visibility = "visible";
	} 
	 document.querySelector("#numResults").onmouseout = function(){
		document.querySelector("#hoverMessage").style.visibility = "hidden";
	}
</script>
</body>
</html>