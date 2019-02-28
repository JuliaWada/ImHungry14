<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="../css/index.css">
<meta charset="UTF-8">
<title>I'm Hungry</title>
</head>
<body>
	<div id = "container">
		<div id = "titleDiv">
			<h1>I'm Hungry</h1>
		</div>
		<div id = "searchDiv">
			<div id = "formDiv">
				<form action = "POST">
					<input placeholder = "Enter food...">
					<input id = "numResults" placeholder = "5">
				</form>
			</div>
		</div>
	</div>
<script>
	document.querySelector("#numResults").onmouseenter = function(){
		alert("Number of results to display");
	}
</script>
</body>
</html>