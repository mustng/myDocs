<?php
session_start();
$websiteUrl = "codeshareweb.com";
$username = $_POST['username'];
$password = $_POST['password'];


//Validate captcha is valid, and that they have entered a username and password
if(!(isset($username) && isset($password) && successfulCAPTCHA($websiteUrl))){
    header('Location: ../../index.php?error=1');
    exit();
}

// Create connection
include 'databaseConnection.php';
$mySQLConnection = connectToDatabase();


$sqlSelect = $mySQLConnection->prepare("SELECT UserID, PasswordHash FROM Users WHERE Username = ?");

$sqlSelect->bind_param("s", $username);
$sqlSelect->execute();
$sqlSelect->bind_result($dbUserID, $dbPassword);
$sqlSelect->fetch();

//Close database
$mySQLConnection->close();

if ($password == $dbPassword)
	{ 
	$_SESSION['username'] = $username;
    $_SESSION['UserID'] = $dbUserID;
    header('Location: ../../main/index.php');
	exit();
	}
else
	{
//        header("Location: ../../index.php?provided=$password&actual=$dbPassword");
        header('Location: ../../index.php?error=1');
	exit();
	}

function CallAPI($method, $url, $data = false)
{
    $curl = curl_init();

    switch ($method)
    {
        case "POST":
            curl_setopt($curl, CURLOPT_POST, 1);

            if ($data)
                curl_setopt($curl, CURLOPT_POSTFIELDS, $data);
            break;
        case "PUT":
            curl_setopt($curl, CURLOPT_PUT, 1);
            break;
        default:
            if ($data)
                $url = sprintf("%s?%s", $url, http_build_query($data));
    }

    // Optional Authentication:
//	curl_setopt($curl, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
//	curl_setopt($curl, CURLOPT_USERPWD, "username:password");

    curl_setopt($curl, CURLOPT_URL, $url);
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);

    $result = curl_exec($curl);

    curl_close($curl);

    return $result;
}

function successfulCAPTCHA($websiteUrl){
    $googleCaptchaUrl = "https://www.google.com/recaptcha/api/siteverify";
    $captchaResponse = $_POST['g-recaptcha-response'];
    $mySecret = "6Lf9LCUTAAAAAIIHQ3eQlnw5vm0nQmupzwkJoTy_";
    $data = array('secret' => $mySecret, 'response' => $captchaResponse, 'remoteip' => $_SERVER['REMOTE_ADDR']);

    $jsonResponse = CallAPI("POST", $googleCaptchaUrl, $data);
    $responseObject = json_decode($jsonResponse);

    if($responseObject->success == true && $responseObject->hostname == $websiteUrl){
        return true;
    }else{
        return false;
    }
}