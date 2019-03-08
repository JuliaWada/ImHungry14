<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/restaurant.css">
<meta charset="UTF-8">
<title>I'm Hungry</title>
</head>
<body onload = "loadPage()">
	<div id ="container">
		<div id = "infoDiv">
			
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
			<button onclick = "toAddtoList()" class = "button">Add to List</button>
		</div>
	</div>
	<script>
	var name;
	function loadPage() {
		var url = window.location.href;
		var split = url.split("?");
		var args = split[1];
		console.log(args);
		split = args.split("=");
		name = split[1];
		name = name.replace(/%20/g, " ");
		console.log(name);
		
		var xhttp = new XMLHttpRequest();

		xhttp.onreadystatechange = function () {
			document.getElementById("infoDiv").innerHTML = this.responseText;
		}
		xhttp.open("POST", "restaurantMgmt?query=" + encodeURIComponent(name), true);
		xhttp.send();
	}
	
	function toPrintVersion(){
		document.querySelector("#buttonDiv").style.visibility = "hidden";
	}
	
	function toResults(){
		window.location.href = "results.jsp";
	}
	
	function toAddtoList(){
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
			xhttp.open("POST", "listMgmtData?action=add&type=restaurant&itemName=" + clean + "&listName=" + option, true);
			xhttp.send();
		}
		
	}
	
	</script>
</body>
</html>