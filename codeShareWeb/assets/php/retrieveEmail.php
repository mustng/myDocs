<?php
$websiteUrl = "codeshareweb.com";
if(!(successfulCAPTCHA($websiteUrl))){
    header('Location: retrieve.php?error=3');
    exit(0);
}
$email = $_POST['email'];

if ($email == ""){
    header('Location: retrieve.php?error=1');
}
else{
    //Establish a connection to the database
    include 'databaseConnection.php';
    $mySQLConnection = connectToDatabase();

    //Create query string
    $query = "SELECT * FROM Users WHERE Email ='$email'";

    //Issue query against database
    $result = $mySQLConnection->query($query);

    $row = $result->fetch_row();

    //Close database
    $mySQLConnection->close();

    if ($row[2] == "" ){   //"Sorry we couldn't find your email. Please try again"
        header('Location: retrieve.php?error=2');
    }
    else {
        $queriedUsername = $row[1];
        $password = $row[3];
        $subject = "Your codeShareWeb.com retrieved password";     //email password to User
        $message = "You have requested your account information! Your username is $queriedUsername and your password is $password. If you wish to change it click on the My Profile button";
        $from = "admin@codeShareWeb.com";
        $headers = "From: $from";

        mail($email, $subject, $message, $headers);
        ?>

        <!DOCTYPE html>
        <html>
        <link rel="stylesheet" type="text/css" href="../css/custom.css">
        <head>
            <meta charset="utf-8"/>
        </head>

        <menu>
            <box><a class="active" href="../../index.php">Login</a></box>
            <box><a class="active" href="../../main/create.php">Create a New Account</a></box>
            <box><a href="retrieve.php">Forgot Your Password</a></box>

        </menu>
        <TITLE>codeShareWeb.com</TITLE>
        <center><body>
            <?php
            echo "Your password to retrieve has been sent to $email!"
            ?>
            </body></center>
        </html>
        <?php
    }
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
?>