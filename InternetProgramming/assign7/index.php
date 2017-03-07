<html>

<link rel="stylesheet" type="text/css" href="../style.css">
<menu>
	<box><a class="active" href="../index.html">Home</a></box>
	<box><a href="../assign1/index.html">Assignment 1</a></box>
	<box><a href="../assign2/index.html">Assignment 2</a></box>
	<box><a href="../assign3/index.html">Assignment 3</a></box>
	<box><a href="../assign4/index.html">Assignment 4</a></box>
	<box><a href="../assign5/login.php">Assignment 5</a></box>
	<box><a href="../assign6/index.php">Assignment 6</a></box>
	<box><a href="index.php">Assignment 7</a></box>
	<box><a href="mailto:maildavidhughes@yahoo.com">Email Me</a></box>
</menu>
<TITLE>COP 4813 :  Assignment 7</TITLE>
<head><center><font size='5'><img src="../assign6/amazon.jpg" alt="amazon" width="100" height="120">Amazon Primes Most Popular Movies</font></center><br><br><br>

<center><body>

<script language="javascript" type="text/javascript">
<!-- 
//Browser Support Code
function ajaxFunction(){
	var ajaxRequest;  // The variable that makes Ajax possible!
	
	try{
		// Opera 8.0+, Firefox, Safari
		ajaxRequest = new XMLHttpRequest();
	} catch (e){
		// Internet Explorer Browsers
		try{
			ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try{
				ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e){
				// Something went wrong
				alert("Your browser broke!");
				return false;
			}
		}
	}
	// Create a function that will receive data sent from the server
	ajaxRequest.onreadystatechange = function(){
		if(ajaxRequest.readyState == 4){
			document.getElementById("output").innerHTML = ajaxRequest.responseText;
		}
	}
	
	var selection = document.myForm.listPersons.value;

	ajaxRequest.open("GET", "getData.php?selection=" + selection, true);
	ajaxRequest.send(null); 
}

//-->
</script>



<form name='myForm'>
<select name="listPersons" onChange="ajaxFunction()">
<?php

	echo "<option value=\"2015\">Movies from 2015</option>
		<option value=\"2014\">Movies from 2014</option>
		<option value=\"2011\">Movies from 2011</option>";

?>
</select>
</form>
<br><br>
<p id="output"></p>
</body></center>
</html>
