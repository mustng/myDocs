<?php
session_start();

$websiteUrl = "codeshareweb.com";
if(!(successfulCAPTCHA($websiteUrl))){
    header('Location: ../../main/create.php?error=4');
    exit(0);
}

if($_POST['email'] !== $_POST['emailTwo']){
    header('Location: ../../main/create.php?error=5');
    exit(0);
}

$username = $_POST['username'];
$email = $_POST['email'];

//Establish a connection to the database
include 'databaseConnection.php';
$mySQLConnection = connectToDatabase();

$username = $mySQLConnection->real_escape_string($username);     //security
$email = $mySQLConnection->real_escape_string($email);

//Create query string
$query = "SELECT * FROM Users WHERE Username ='$username'";
$query2 = "SELECT * FROM Users WHERE Email ='$email'";

//Issue query against database
$result = $mySQLConnection->query($query);       //check if name is taken already
$result2 = $mySQLConnection->query($query2);    //check if email is taken already

$row = $result->fetch_row();
$row2 = $result2->fetch_row();

//Close database
$mySQLConnection->close();

if ($row[1] != ""){                             //check if name is taken already
    header('Location: ../../main/create.php?error=2');
}
else if ($row2[2] != ""){                             //check if email is taken already
    header('Location: ../../main/create.php?error=3');
}
else{
    //create a randompassword to email
    function generateRandomString($length = 8) {
        $characters = '23456789abcdefghjkmnopqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ'; // exclude similiar  char i,l,1,0,O
        $charactersLength = strlen($characters);
        $randomString = '';
        for ($i = 0; $i < $length; $i++) {
            $randomString .= $characters[rand(0, $charactersLength - 1)];
        }
        return $randomString;
    }
    $password = generateRandomString();

    //Establish a connection to the database
    include 'databaseConnection.php';
    $mySQLConnection = connectToDatabase();

    $username = $mySQLConnection->real_escape_string($username);
    $email = $mySQLConnection->real_escape_string($email);

    //Create query string
    $query3 = "INSERT INTO Users (Username , Email, PasswordHash) ";
    $query3 = $query3 . "VALUES ('$username', '$email', '$password')";

    //Issue query against database
    $result = $mySQLConnection->query($query3, $mysql_access);

    //Close database
    $mySQLConnection->close();

    $subject = "Your codeShareWeb.com password";     //email password to User
    $message = "Welcome to codeShareWeb.com! Your username is $username and your assigned password is $password. If you wish to change it click on the My Profile button";
    $from = "admin@codeShareWeb.com";
    $headers = "From: $from";

    mail($email, $subject, $message, $headers);
    ?>

    <!DOCTYPE html>
    <html>
        <!-- CSS Global Compulsory -->
        <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="../css/custom.css">
    <head>
        <meta charset="utf-8"/>
    </head>

    <TITLE>codeShareWeb.com</TITLE>
    <body>
        <div class="container-fluid">
            <div class="col-md-4 col-md-offset-4">
                <p><?php
                    echo "Your password has been sent to $email; please be patient, it may take several minutes for the email to arrive. Once you log in you can change it in the profile menu";
                    session_destroy();
                    ?>
                </p>
                <a href="../../index.php" class="btn btn-primary btn-lg active" role="button">Log In</a>
            </div>
        </div>
    </body>
    </html>
    <?php
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
?>


