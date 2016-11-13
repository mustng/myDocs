<?php session_start();
if ($_SESSION['username'] == ""){
    header('Location: ../index.php?error=2');
}
$add = $_POST['add'];
$sub = $_POST['sub'];
$codeID = $_POST['codeID'];
$UserID = $_SESSION['UserID']; //Get Session UserID



if ($add == 'Good Code'){
    if(checkIfRowExists('CodeLikesOrDislikes',$UserID, $codeID)){
        UpdateData('CodeLikesOrDislikes', $UserID, $codeID, 1);
    }else{
        AddOrMinusData('CodeLikesOrDislikes', $UserID, $codeID, 1);
    }

}
else if ($sub == 'Bad Code'){
    if(checkIfRowExists('CodeLikesOrDislikes',$UserID, $codeID)){
        UpdateData('CodeLikesOrDislikes', $UserID, $codeID, -1);
    }else{
        AddOrMinusData('CodeLikesOrDislikes', $UserID, $codeID, -1);
    }
}



function checkIfRowExists($Name, $UserID, $codeID){
    //Establish a connection to the database
    include 'databaseConnection.php';
    $mySQLConnection = connectToDatabase();

    $UserID = $mySQLConnection->real_escape_string($UserID);
    $codeID = $mySQLConnection->real_escape_string($codeID);

    $query = "SELECT * FROM $Name WHERE UserID = $UserID AND CodeID = $codeID";
    $result = $mySQLConnection->query($query);

//Close database
    $mySQLConnection->close();

    $row = ($result->fetch_row());
    //If they have already liked or disliked the code...
    //We'll update the table, rather than add a new row
    if ($row[0] != ""){
        return true;
    }else{
        return false;
    }
}

function AddOrMinusData($Name, $UserID, $codeID, $num){

    //Establish a connection to the database
    include 'databaseConnection.php';
    $mySQLConnection = connectToDatabase();

    $UserID = $mySQLConnection->real_escape_string($UserID);
    $codeID = $mySQLConnection->real_escape_string($codeID);

    //Create query string
    $query = "INSERT INTO $Name (CodeID ,  UserID, LikeValue) ";
    $query = $query . "VALUES ('$codeID','$UserID', '$num')";

//Issue query against database
    $mySQLConnection->query($query);

//Close database
    $mySQLConnection->close();

//Redirect back to main
    header("Location: ../../main/viewCode.php?message=2&codeID=$codeID");
}

function UpdateData($Name, $UserID, $codeID, $num){

    //Establish a connection to the database
    include 'databaseConnection.php';
    $mySQLConnection = connectToDatabase();

    //Create query string
    $query = "UPDATE $Name SET LikeValue = $num WHERE CodeID = $codeID AND UserID = $UserID";

//Issue query against database
    $mySQLConnection->query($query);

//Close database
    $mySQLConnection->close();

//Redirect back to main
    header("Location: ../../main/viewCode.php?message=2&codeID=$codeID");

}
?>