<?php
	//Get the variables
	$movieID = $_POST['movieID'];

	//Establish a connection to the database
	$mysql_access = mysql_connect(localhost, 'n00814425', 'summer2016814425');

	//Check for connection
	if(!$mysql_access){
		die('Could not connect: ' . mysql_error());
	}

	//Select the database
	mysql_select_db('n00814425');

	//Create query string
	$query = "DELETE FROM Movies WHERE movieID=" . $movieID;


	//Issue query against database
	$result = mysql_query($query, $mysql_access);

	//Close database
	mysql_close($mysql_access);

	//Redirect back to main
	header('Location: index.php');

?>
