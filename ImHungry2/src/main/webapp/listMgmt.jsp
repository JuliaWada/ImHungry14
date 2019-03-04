<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/listmgmt.css">
<meta charset="UTF-8">
<title>I'm Hungry</title>
</head>
<body>
	<h1><span id = "listName"></span>'s List</h1>
	<div id = "container">
		<div id = "cardDiv">
			<button onclick = "removefromList()" class ="remove">X</button>
		</div>
		<div id = "buttonDiv">
			<select class = "menu">
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
	
	function toResultsPage(){
		window.location.href = "results.jsp";
	}
	
	function toSearchPage(){
		window.location.href = "index.jsp";
	}
	</script>
</body>
</html>