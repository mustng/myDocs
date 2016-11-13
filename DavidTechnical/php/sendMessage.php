<?php

$your_name = $_POST['your_name'];
$your_email = $_POST['your_email'];
$your_message = $_POST['your_message'];
$user_answer = $_POST['user_answer'];
$answer = $_POST['answer'];

if ($answer == ""){
    header('Location: ../contact.php?message=2');
}
else if ($user_answer === $answer){

    $to      = "admin@davidtechnical.com";
    $subject = "admin@davidtechnical.com message from $your_name";     //email password to User
    $message = "$your_name at $your_email \r\n\r\n $your_message";
    $from = "admin@davidtechnical.com";
    $headers = "From: $from\r\nReply-to: $your_email";

    mail($to, $subject, $message, $headers);

    header('Location: ../contact.php?message=1');
}
else{
    header('Location: ../contact.php?message=2');
}
?>
