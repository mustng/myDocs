<?php session_start();
if ($_SESSION['username'] == ""){
    header('Location: ../../index.php?error=2');
}

$oldPassword = $_POST['oldPassword'];
$newPassword = $_POST['newPassword'];
$newPassword2 = $_POST['newPassword2'];
$username = $_SESSION['username'];

    //Establish a connection to the database
    include 'databaseConnection.php';
    $mySQLConnection = connectToDatabase();

    $oldPassword = $mySQLConnection->real_escape_string($oldPassword);
    $newPassword = $mySQLConnection->real_escape_string($newPassword);
    $newPassword2 = $mySQLConnection->real_escape_string($newPassword2);

    //Create query string
    $query = "SELECT * FROM Users WHERE Username='$username'";

    //Issue query against database
    $result = $mySQLConnection->query($query, $mysql_access);

    //Fetch row
    $row = $result->fetch_row();
    $UserID = $row[0];
    $Username = $row[1];
    $Email = $row[2];
    $PasswordHash = $row[3];

    //Close database
    $mySQLConnection->close();


if ( $oldPassword != $PasswordHash){        // old password does not match
    header('Location: ../../main/myProfile.php?error=1');
}
else if ($newPassword != $newPassword2)
{
    header('Location: ../../main/myProfile.php?error=2'); //Passwords dont match
}
else if($newPassword == $newPassword2){
    //Establish a connection to the database
    include 'databaseConnection.php';
    $mySQLConnection = connectToDatabase();

    $newPassword = $mySQLConnection->real_escape_string($newPassword);

//Create query string
    $query = "UPDATE Users set PasswordHash= '$newPassword'";
    $query = $query . "WHERE UserID=" . $UserID;

//Issue query against database
    $result = $mySQLConnection->query($query);

//Close database
    $mySQLConnection->close();

//Redirect back to main

    header('Location: ../../main/myProfile.php?error=3');
}
else{
   header('Location: ../../main/myProfile.php');
}
?>