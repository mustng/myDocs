<?php
session_start();

    if ($_SESSION['username'] == ""){
        header('Location: login.php?error=2');
    }

$username = $_POST['username'];
$password = $_POST['password'];

$myFile = "name.dat";
$fh = fopen($myFile, 'r');
$theData = fread($fh, filesize($myFile));
fclose($fh);

$security = explode(" ",$theData);

if ($username == $security[0] && $password == $security[1]){
    //go to main page
    $_SESSION['username'] = $username;
    header('Location: main.php');
}
else {
    //Send back to log in with error message
    header('Location: login.php?error=1');
}
?>