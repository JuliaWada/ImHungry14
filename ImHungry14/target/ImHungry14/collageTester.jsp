<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="index.css"/>
</head>
<body onload="sendMessage()">
<h2>Hello World!</h2>
<form >
Search: <input type="text" name="fname" id="searchInput"><br/>
<button type="button" onclick="sendingSearch()">Submit</button>
</form>
<div id="collageContainer">
</div>

<script >
	function sendMessage() {
		console.log("javascript is working?");
	}
	
	function sendingSearch() {
		console.log("beginning search");
		var toSend = document.getElementById('searchInput').value;
		console.log("search term: " + toSend);
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function () {
			document.getElementById("collageContainer").innerHTML = this.responseText;
		}
		xhttp.open("POST", "collageData?query=" + toSend, true);
		xhttp.send();
		console.log("sent to backend");
	}
</script>
</body>
</html>
