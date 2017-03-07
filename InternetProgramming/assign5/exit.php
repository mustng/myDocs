<?php
    session_start();
    if ($_SESSION['username'] == ""){
        header('Location: login.php?error=2');
    }
?>

<!DOCTYPE html>
<html>
<link rel="stylesheet" type="text/css" href="../style.css">
<head>
    <meta charset="utf-8"/>
</head>
<body>

  <menu>
  <box><a class="active" href="../index.html">Home</a></box>
  <box><a href="../assign1/index.html">Assignment 1</a></box>
  <box><a href="../assign2/index.html">Assignment 2</a></box>
  <box><a href="../assign3/index.html">Assignment 3</a></box>
  <box><a href="../assign4/index.html">Assignment 4</a></box>
  <box><a href="login.php">Assignment 5</a></box>
  <box><a href="mailto:maildavidhughes@yahoo.com">Email Me</a></box>
</menu>
<TITLE>COP 4813 :  Assignment 4</TITLE><center>
<?php
    $username = $_SESSION['username'];

    echo "Come again, $username.     ";?> <a href="login.php">Login Again</a>
      <?php
    session_destroy();

?>

      </center>
</body>
</html>
