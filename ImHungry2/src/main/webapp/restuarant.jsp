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
			<select class = "menu" id="listOptions">
				 <option value = "0"> </option>
				 <option value="1">Favorites</option>
   				 <option value="2">To Explore</option>
   				 <option value="3">Do Not Show</option>
			</select>
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
	
	function toAddtoList(){
		var list = document.getElementById("listOptions");
		var option = list.options[list.selectedIndex].text;
		if(option != ""){
			
		}
		
	}
	
	</script>
</body>
</html>