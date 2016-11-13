<?php session_start();
if ($_SESSION['username'] == ""){
    header('Location: ../index.php?error=2');
}
$addComment = $_POST['addComment'];
$subComment = $_POST['subComment'];
$codeID = $_POST['codeID'];
$CommentID = $_POST['CommentID'];
$UserID = $_SESSION['UserID']; //Get Session UserID

if ($addComment == 'Good Comment'){
    if(checkIfRowExists('CommentLikesOrDislikes',$UserID, $codeID, $CommentID)){
        UpdateData('CommentLikesOrDislikes', $UserID, $codeID, 1, $CommentID);
    }else{
        AddOrMinusData('CommentLikesOrDislikes', $UserID, $codeID, 1, $CommentID);
    }

}
else if ($subComment == 'Bad Comment'){
    if(checkIfRowExists('CommentLikesOrDislikes',$UserID, $codeID, $CommentID)){
        UpdateData('CommentLikesOrDislikes', $UserID, $codeID, -1, $CommentID);
    }else{
        AddOrMinusData('CommentLikesOrDislikes', $UserID, $codeID, -1, $CommentID);
    }
}



function checkIfRowExists($Name, $UserID, $codeID, $CommentID){
    //Establish a connection to the database
    include 'databaseConnection.php';
    $mySQLConnection = connectToDatabase();

    $Name = $mySQLConnection->real_escape_string($Name);
    $CommentID = $mySQLConnection->real_escape_string($CommentID);
    $UserID = $mySQLConnection->real_escape_string($UserID);
    $codeID = $mySQLConnection->real_escape_string($codeID);

    $query = "SELECT * FROM $Name WHERE UserID = $UserID AND CommentID = $CommentID";
    $result = $mySQLConnection->query($query);

//Close database
    $mySQLConnection->close();

    $row = $result->fetch_row();
    if ($row[0] != ""){
        return true;
    }else{
        return false;
    }
}

function AddOrMinusData($Name, $UserID, $codeID, $num,$CommentID){

    //Establish a connection to the database
    include 'databaseConnection.php';
    $mySQLConnection = connectToDatabase();

    $Name = $mySQLConnection->real_escape_string($Name);
    $CommentID = $mySQLConnection->real_escape_string($CommentID);
    $UserID = $mySQLConnection->real_escape_string($UserID);
    $num = $mySQLConnection->real_escape_string($num);

    //Create query string
    $query = "INSERT INTO $Name (CommentID  ,  UserID, LikeValue) ";
    $query = $query . "VALUES ('$CommentID','$UserID', '$num')";

//Issue query against database
    $mySQLConnection->query($query);

//Close database
    $mySQLConnection->close();

//Redirect back to main
    header("Location: ../../main/viewCode.php?message2=2&codeID=$codeID");
}

function UpdateData($Name, $UserID, $codeID, $num,$CommentID){

    //Establish a connection to the database
    include 'databaseConnection.php';
    $mySQLConnection = connectToDatabase();

    $Name = $mySQLConnection->real_escape_string($Name);
    $CommentID = $mySQLConnection->real_escape_string($CommentID);
    $UserID = $mySQLConnection->real_escape_string($UserID);
    $num = $mySQLConnection->real_escape_string($num);

    //Create query string
    $query = "UPDATE $Name SET LikeValue = $num WHERE CommentID = $CommentID AND UserID = $UserID";

//Issue query against database
    $mySQLConnection->query($query);

//Close database
    $mySQLConnection->close();

//Redirect back to main
    header("Location: ../../main/viewCode.php?message2=2&codeID=$codeID");
}
?>