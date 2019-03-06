<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/listmgmt.css">
<meta charset="UTF-8">
<title>I'm Hungry</title>
</head>
<body onload="loadListData()">
	<h1><span id = "listName"></span>'s List</h1>
	<div id = "container">
		<div id = "cardDiv">
			<button onclick = "removefromList()" class ="remove">X</button>
		</div>
		<div id = "buttonDiv">
			<select class = "menu" id="listOptions">
				 <option value = "0"> </option>
				 <option value="1">Favorites</option>
   				 <option value="2">To Explore</option>
   				 <option value="3">Do Not Show</option>
			</select>
			<br>
			<button onclick = "toManageList()" class = "button">Manage List</button>
			<button onclick = "toResultsPage()" class = "button">Back To Results</button>
			<button onclick = "toSearchPage()" class = "button">Back To Search</button>
		</div>
	</div>
	<script>
	var name = "";
	function loadListData() {
		loadListName();
		loadList();
	}
	function toManageList() {
		var list = document.getElementById("listOptions");
		var option = list.options[list.selectedIndex].text;
		if(option != "") {
			var url = "listMgmt.jsp?name=" + option;
			window.location.href = url;
		}
	}
	function toResultsPage(){
		window.location.href = "results.jsp";
	}
	
	function toSearchPage(){
		window.location.href = "index.jsp";
	}
	
	function loadListName() {
		var url = window.location.href;
		var split = url.split("?");
		var args = split[1];
		console.log(args);
		split = args.split("=");
		name = split[1];
		console.log(name);
		name = name.replace(/%20/g, " ");
		console.log(name);
		document.getElementById("listName").innerHTML = name;
	}
	
	function loadList() {
		var xhttp =  new XMLHttpRequest();
		xhttp.onreadystatechange = function () {
			document.getElementById("cardDiv").innerHTML = this.responseText;
		}
		//what values to put into the url
		//listName - for the list that is being edited
		xhttp.open("POST", "listMgmtData?action=loadList&listName=" + name, true);
		xhttp.send();
		console.log("list Management sent to backend");
	}
	
	function reloadList() {
		var xhttp =  new XMLHttpRequest();
		xhttp.onreadystatechange = function () {
			document.getElementById("cardDiv").innerHTML = this.responseText;
		}
		//what values to put into the url
		//listName - for the list that is being edited
		xhttp.open("POST", "listMgmtData?action=reloadList&listName=" + name, true);
		xhttp.send();
		console.log("list Management sent to backend");
	}
	
	function removeFromList(currentButton) {
		console.log(currentButton.innerHTML);
		var title = $(currentButton).siblings('.name').text();
		console.log(title);
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			reloadList();
		}
		xhttp.open("POST", "listMgmtData?action=remove&listName=" + name + "&itemName=" + title, true);
		xhttp.send();
	}
	
	function moveToList(currentButton) {
		var title = $(currentButton).siblings('.name').text();
		var list = document.getElementById('moveListOptions');
		var option = list.options[list.selectedIndex].text;
		console.log(title);
		console.log("List to move to: " + option);
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			reloadList();
		}
		xhttp.open("POST", "listMgmtData?action=move&listName=" + name + "&itemName=" + title + "&secondList=" + option, true);
		xhttp.send();
	}
	</script>
	<script  src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
	$(document).ready(function() {
	    $(".trigger").click(function() {
	        var parentA = $(this).parent().closest('div');
	        console.log(parentA.innerHTML);
	    });
	});
	</script>
</body>
</html>