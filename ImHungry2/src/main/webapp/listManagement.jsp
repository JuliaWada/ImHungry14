<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>I'm Hungry</title>
</head>
<body>
	<div id = "container">
		<div id = "itemDiv">
		
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
	</div>
	</div>
</body>
</html>