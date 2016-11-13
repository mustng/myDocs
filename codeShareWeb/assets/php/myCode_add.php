<?php session_start();
if ($_SESSION['username'] == ""){
    header('Location: ../index.php?error=2');
}

//Establish a connection to the database
include 'databaseConnection.php';
$mySQLConnection = connectToDatabase();


//Create query string
$query = "INSERT INTO UserSubmittedCode (UserID,ProgrammingLanguage, CodeText, Description) VALUES (?, ?, ?, ?)";
$sqlUpdate = $mySQLConnection->prepare($query);
$sqlUpdate->bind_param('dsss', $userIDValue, $languageValue, $codeTextValue, $descriptionValue);

//Set the variables
$languageValue = $_POST['language'];
$descriptionValue = $_POST['description'];
$codeTextValue  = $_POST['code'];
$userIDValue = $_SESSION['UserID'];

//Issue query against database
$sqlUpdate->execute();

$query_GetInsertedCodeID = "SELECT CodeID FROM UserSubmittedCode WHERE UserID = $userIDValue ORDER BY CodeID DESC LIMIT 1";
$result_GetInsertedCodeID = $mySQLConnection->query($query_GetInsertedCodeID);
$row_GetInsertedCodeID = $result_GetInsertedCodeID->fetch_row();
$value_GetInsertedCodeID = $row_GetInsertedCodeID[0];

$query_LikeOwnCode = "INSERT INTO `CodeLikesOrDislikes`(`CodeID`, `UserID`, `LikeValue`) VALUES ($value_GetInsertedCodeID, $userIDValue,1)";
$mySQLConnection->query($query_LikeOwnCode);

//Close database
$mySQLConnection->close();

//Redirect back to main
header('Location: ../../main/myCode.php');

?>