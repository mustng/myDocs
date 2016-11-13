<?php session_start();
if ($_SESSION['username'] == ""){
    header('Location: ../index.php?error=2');
}
	$function = $_POST["b1"];
	$codeid = $_POST["CodeID"];
			
	if ($function == "My Code")
	{
		header('Location: myCode.php');
	}
	if ($function == "View Code")
	{
		$_SESSION['codeID'] = $codeid;
		header('Location: viewCode.php');
	}
	if ($function == "My Profile")
	{
		header('Location: myProfile.php');
	}
	if ($function == "About CSW")
	{
		header('Location: about.php');
	}
	if ($function == "Exit")
	{
		session_unset();
		session_destroy();
		header('Location: ../index.php');
	}
?>