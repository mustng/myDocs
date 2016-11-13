<?php session_start();
if ($_SESSION['username'] == ""){
    header('Location: ../../index.php?error2=2');
}

$password = $_POST['password'];
$user1 = $_POST['user1'];
$user2 = $_POST['user2'];
$username = $_SESSION['username'];
$UserID = $_SESSION['UserID'];

//Establish a connection to the database
include 'databaseConnection.php';
$mySQLConnection = connectToDatabase();

$username = $mySQLConnection->real_escape_string($username);

//Create query string
$query = "SELECT * FROM Users WHERE Username='$username'";

//Issue query against database
$result = $mySQLConnection->query($query);

//Fetch row
$row = $result->fetch_row();
$PasswordHash = $row[3];

//Close database
$mySQLConnection->close();


if ( $password != $PasswordHash){        // old password does not match
    header('Location: ../../main/myProfile.php?error2=1');
}
else if ($user1 != $user2)
{
    header('Location: ../../main/myProfile.php?error2=2');
    //Usernames dont match || Griffin PS: This validation can be done on the user side using JavaScript. Ideally, you want to do as much validation
    // as possible on the client side
}
else if($user1 === $user2){
    //Establish a connection to the database

    include 'databaseConnection.php';
    $mySQLConnection = connectToDatabase();

    $user1 = $mySQLConnection->real_escape_string($user1);

    $queryForUser = "SELECT * FROM Users WHERE Username = '$user1'";  //check if username is taken

    //Issue query against database
    $resultToFindUser = $mySQLConnection->query($queryForUser);       //check if name is taken already

    $userResults = $resultToFindUser->fetch_row();

    if ($userResults[1] != ""){                             //check if name is taken already
        //Close database
        $mySQLConnection->close();
        header('Location: ../../main/myProfile.php?error2=4');
        exit(0);
    }
//Create query string
    $queryToUpdate = "UPDATE Users set Username= '$user1' WHERE UserID= $UserID";

//Issue query against database
    $mySQLConnection->query($queryToUpdate);      //update username

//Close database
    $mySQLConnection->close();

//Redirect back to main

    $_SESSION['username'] = $user1;

    header('Location: ../../main/myProfile.php?error2=3');
}
else{
    header('Location: ../../main/myProfile.php');
}