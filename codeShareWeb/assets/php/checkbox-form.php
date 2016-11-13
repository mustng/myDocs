<?php
session_start();

if(isset($_POST['agree']) &&
    $_POST['agree'] == 'Yes')
{
    $_SESSION['checkedBox'] = good;
    $createPageURL = '../../main/create.php';
    header("Location: $createPageURL");
}
else
{
    header('Location: useragreement.php?error=1');
}

?>