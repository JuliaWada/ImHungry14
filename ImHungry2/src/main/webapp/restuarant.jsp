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
			<button id="PVbutton" onclick = "toPrintVersion()" class = "button">Printable Version</button>
			<button id="RPbutton" onclick = "toResults()" class = "button">Back to Results</button>
			<select class = "menu" id="listOptions">
				 <option value = "0"> </option>
				 <option value="1">Favorites</option>
   				 <option value="2">To Explore</option>
   				 <option value="3">Do Not Show</option>
			</select>
			<button id="addLbutton" onclick = "toAddtoList()" class = "button">Add to List</button>
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
		if(name.includes('%27')) {
			console.log("in true");
			name = name;
		} else {
			name = encodeURIComponent(name);
		}
		
		xhttp.open("POST", "restaurantMgmt?query=" + name, true);
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
			if(name.includes('%27')) {
				console.log("in true");
				name = name;
			} else {
				name = encodeURIComponent(name);
			}
			xhttp.open("POST", "listMgmtData?action=add&type=restaurant&itemName=" + name + "&listName=" + option, true);
			xhttp.send();
		}
		
	}
	
	</script>
</body>
</html>