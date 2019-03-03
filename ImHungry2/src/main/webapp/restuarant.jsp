<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/restaurant.css">
<meta charset="UTF-8">
<title>I'm Hungry</title>
</head>
<body>
	<div id ="container">
		<div id = "infoDiv">
			<h1>Ramen Kenjo</h1>
			<p>Phone Number: <span id = "phoneNumber">(213)-536-5922</span></p>
			<br>
			<p >Website: <span id ="url">https://www.uscvillage.com/ramen</span></p>
			<br>
			<p>Address: <span id ="address">929 W Jefferson Blvd, Los Angeles, CA 90007</span></p>
		</div>
		<div id ="buttonDiv">
			<button onclick = "toPrintVersion()" class = "button">Printable Version</button>
			<button onclick = "toResults()" class = "button">Back to Results</button>
			<button onclick = "toAddtoList()" class = "button">Add to List</button>
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
	</script>
</body>
</html>