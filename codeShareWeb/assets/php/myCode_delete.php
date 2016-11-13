<?php session_start();
if ($_SESSION['username'] == ""){
    header('Location: ../index.php?error=2');
}

?>
<?php
//Get the variables
$codeID = $_POST['codeID'];

deleteCodeFromCodeID($codeID);

//Redirect back to main
header('Location: ../../main/myCode.php');

function deleteCodeFromCodeID($codeID){

    //Establish a connection to the database
    include 'databaseConnection.php';
    $mySQLConnection = connectToDatabase();

//Create query strings
    $query_DeleteCommentLikes = "DELETE clod FROM CommentLikesOrDislikes clod INNER JOIN UserSubmittedComments usc ON clod.CommentID = usc.CommentID WHERE CodeID = ?";
    $query_DeleteComments = "DELETE FROM UserSubmittedComments WHERE CodeID = ?";
    $query_DeleteLikesRelatedToCode = "DELETE FROM CodeLikesOrDislikes WHERE CodeID = ?";
    $query_DeleteCode = "DELETE FROM UserSubmittedCode WHERE CodeID = ?";

    $sqlDelete = $mySQLConnection->prepare($query_DeleteCommentLikes);
    $sqlDelete->bind_param("d", $codeID);
    $sqlDelete->execute();

    $sqlDelete = $mySQLConnection->prepare($query_DeleteComments);
    $sqlDelete->bind_param("d", $codeID);
    $sqlDelete->execute();

    $sqlDelete = $mySQLConnection->prepare($query_DeleteLikesRelatedToCode);
    $sqlDelete->bind_param("d", $codeID);
    $sqlDelete->execute();

    $sqlDelete = $mySQLConnection->prepare($query_DeleteCode);
    $sqlDelete->bind_param("d", $codeID);
    $sqlDelete->execute();

//Close database
    $mySQLConnection->close();
}

?>
