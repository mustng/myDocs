<?php session_start();
if(isset($_SESSION['username'])){
    header('Location: main/index.php');
}
?>
<!DOCTYPE html>
<html>
<head>
    <TITLE>Code Share</TITLE>
    <!-- Meta -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Code Share">
    <meta name="author" content="Group 3">

    <!-- CSS Global Compulsory -->
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

    <!-- CSS Customization -->
    <link rel="stylesheet" type="text/css" href="assets/css/custom.css">

    <!--    JS for Canvas Logo-->
    <script src="assets/js/codeShareLogo.js"></script>

    <!-- JS for Google's reCAPTCHA -->
    <script src='https://www.google.com/recaptcha/api.js'></script>
</head>

<body id="frontPageBody" style="padding-bottom: 70px;">
    <div class="container-fluid">
        <div class="col-md-9 col-md-offset-3">
            <canvas id="canvas3" width="560" height="60">
            </canvas>
        </div>
        <div class="col-md-4 col-md-offset-4 col-sm-8" id="frontPageLoginDiv">
                <?php
                $error = $_GET['error'];
                if($error == 1 || $error == 2 || $error == 3){
                    echo "<div class=\"alert alert-danger\" role=\"alert\">";
                }
                if ($error==1){
                    echo "You must provide correct login credentials";
                }
                if ($error==2){
                    echo "Must log in to access CodeShareWeb";
                }
                if ($error==3){
                    echo "Incorrect Validation Code";
                }
                if($error == 1 || $error == 2 || $error == 3){
                    echo "</div>";
                }
                ?>

            <form action='assets/php/checkLoginCredentials.php' method='post'>
                <h2>Please Log In</h2>
                <fieldset class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" class="form-control" name="username">
                </fieldset>
                <fieldset class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" name="password">
                </fieldset>
                <div class="g-recaptcha" data-sitekey="6Lf9LCUTAAAAACyCklDmeUNvp4-nDWHVFGYk2cYy"></div>
                <br>
                <button class="btn btn-default" type="submit">Log In</button>
                <br>
            </form>

            <menu>
              <box><a class="active" href="assets/php/useragreement.php">Create a New Account</a><br></box>
              <box><a href="assets/php/retrieve.php">Forgot Your Password</a></box>
            </menu>
        </div>
    </div>
</div>
</body>
<footer >
    <nav class="navbar navbar-fixed-bottom" style="text-align: center; color: white; background: black">
        <div class="container-fluid">
            <a href="main/about.php">About Us</a>
        </div><!-- /.container-fluid -->
    </nav>
</footer>
</html>
