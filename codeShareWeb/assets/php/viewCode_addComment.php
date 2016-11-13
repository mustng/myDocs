<?php session_start();
if ($_SESSION['username'] == ""){
    header('Location: ../index.php?error=2');
}
//Get the variables
$codeID = $_POST['codeID'];
$comment = (string)$_POST['comment'];
$username = $_SESSION['username'];
$UserID = $_SESSION['UserID']; //Get Session UserID


//Establish a connection to the database
include 'databaseConnection.php';
$mySQLConnection = connectToDatabase();

$codeID = $mySQLConnection->real_escape_string($codeID);
$comment = $mySQLConnection->real_escape_string($comment);
$username = $mySQLConnection->real_escape_string($username);
$UserID = $mySQLConnection->real_escape_string($UserID);

//Create query string
$query = "INSERT INTO UserSubmittedComments (CodeID ,  UserID, CommentText) ";
$query = $query . "VALUES ($codeID,$UserID, '$comment')";

//Issue query against database
$mySQLConnection->query($query);

//An assumption here; getting the most recent CommentID by UserID/CodeID SHOULD give you the CommentID for the comment you just made...
$query_GetInsertedCommentID = "SELECT CommentID FROM UserSubmittedComments WHERE CodeID = $codeID AND UserID = $UserID ORDER BY CommentID DESC LIMIT 1";
$result_GetInsertedCommentID = $mySQLConnection->query($query_GetInsertedCommentID);
$row_GetInsertedCommentID = $result_GetInsertedCommentID->fetch_row();
$value_GetInsertedCommentID = $row_GetInsertedCommentID[0];

$query_LikeOwnComment = "INSERT INTO `CommentLikesOrDislikes`(`CommentID`, `UserID`, `LikeValue`) VALUES ($value_GetInsertedCommentID, $UserID,1)";
$mySQLConnection->query($query_LikeOwnComment);

//Close database
$mySQLConnection->close();

//Redirect back to main
header("Location: ../../main/viewCode.php?codeID=$codeID");

?>