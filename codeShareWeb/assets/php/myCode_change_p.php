<?php session_start();
if ($_SESSION['username'] == ""){
    header('Location: ../index.php?error=2');
}

$codeID = $_POST['codeID'];
$language = $_POST['language'];
$description = $_POST['description'];
$code  = $_POST['code'];

//Establish a connection to the database
include 'databaseConnection.php';
$mySQLConnection = connectToDatabase();

//Create query string
$query = "UPDATE UserSubmittedCode SET CodeText = ?, ProgrammingLanguage = ?, Description = ? WHERE CodeID = ?";


//Issue query against database
$sqlUpdate = $mySQLConnection->prepare($query);
$sqlUpdate->bind_param("sssd", $code, $language, $description, $codeID);
$sqlUpdate->execute();

//Close database
$mySQLConnection->close();

//Redirect back to main

header('Location: ../../main/myCode.php?');

?>