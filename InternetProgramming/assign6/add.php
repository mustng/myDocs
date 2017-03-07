<?php
	//Get the variables
	$movie =$_POST['movie'];
    $year = $_POST['year'];
    $cc =$_POST['cc'];
    $comments =$_POST['comments'];

    if (isset($_POST['rated'])){
        $rated = $_POST['rated'];
    }
    if (isset($_POST['rating'])){
    $rating = $_POST['rating'];
}
	if ($cc == 'Yes'){
        $cc = 'Yes';
    }
    else{
        $cc = 'No';
    }

	//Establish a connection to the database
	$mysql_access = mysql_connect(localhost, 'n00814425', 'summer2016814425');

	//Check for connection
	if(!$mysql_access){
		die('Could not connect: ' . mysql_error());
	}

	//Select the database
	mysql_select_db('n00814425');

    $movie = mysql_real_escape_string($movie);
    $year = mysql_real_escape_string($year);
    $cc = mysql_real_escape_string($cc);
    $comments = mysql_real_escape_string($comments);
    $rated = mysql_real_escape_string($rated);
    $rating = mysql_real_escape_string($rating);

	//Create query string
    $query = "INSERT INTO Movies (movie , rated , year , cc , rating, comments) ";
	$query = $query . "VALUES ('$movie', '$rated', $year, '$cc', '$rating' , '$comments')";

	//Issue query against database
	$result = mysql_query($query, $mysql_access);

	//Close database
	mysql_close($mysql_access);

	//Redirect back to main
	header('Location: index.php');

?>
