<?php
$error = $_GET['error'];
?>
<!DOCTYPE html>
<html>
<link rel="stylesheet" type="text/css" href="../style.css">
<head>
    <meta charset="utf-8"/>
</head>
  
  <menu>
  <box><a class="active" href="../index.html">Home</a></box>
  <box><a href="../assign1/index.html">Assignment 1</a></box>
  <box><a href="../assign2/index.html">Assignment 2</a></box>
  <box><a href="../assign3/index.html">Assignment 3</a></box>
  <box><a href="../assign4/index.html">Assignment 4</a></box>
  <box><a href="login.php">Assignment 5</a></box>
  <box><a href="../assign6/index.php">Assignment 6</a></box>
  <box><a href="../assign7/index.php">Assignment 7</a></box>
  <box><a href="mailto:maildavidhughes@yahoo.com">Email Me</a></box>
</menu>
<TITLE>COP 4813 :  Assignment 5</TITLE>

<center>
<body>
<br><br><br>
<form action='check.php' method='post'>
    Username: <input type='text' name='username'><br>
    Password: <input type='password' name='password'><br>
    <input type='submit' value='Log In'><br>
</form>
<?php
    if ($error==1){
        echo "You must provide correct login credentials";
    }
    if ($error==2){
        echo "Must log in to access recourse";
    }
?>

</body></center>
</html>