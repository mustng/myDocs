<?php
    session_start();
    if ($_SESSION['username'] == ""){
        header('Location: index.php?error=2');
    }
?>

<!DOCTYPE html>
<html>
<head>
    <TITLE>Code Share Web</TITLE>
    <!-- Meta -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Code Share">
    <meta name="author" content="Group 3">

    <!-- CSS Global Compulsory -->
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

    <!-- CSS Customization -->
    <link rel="stylesheet" type="text/css" href="../assets/css/custom.css">

    <!--    JS for Canvas Logo-->
    <script src="../assets/js/codeShareLogo.js"></script>
</head>
<body id="frontPageBody">
    <div class="container-fluid">
        <div class="col-md-9 col-md-offset-3">
            <canvas id="canvas3" width="560" height="60">
            </canvas>
        </div>
        <div class="col-md-4 col-md-offset-4 col-sm-8" id="frontPageLoginDiv">
            <h1>Thank you for using CodeShareWeb</h1>
            <?php
            $username = $_SESSION['username'];

            echo "<h2>";
            echo "Come again soon, $username.     ";
            echo "</h2>";
            ?>
            <a class="btn btn-default" href="../index.php" style="color:black">Main Page</a>
        </div>
    </div>
<?php
session_destroy();
?>
</body>
</html>
