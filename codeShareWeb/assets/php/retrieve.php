<?php
$error = $_GET['error'];
?>
<!DOCTYPE html>
<html>
<link rel="stylesheet" type="text/css" href="style.css">
<head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../css/custom.css">

    <!-- JS for Google's reCAPTCHA -->
    <script src='https://www.google.com/recaptcha/api.js'></script>
</head>


<body>
<div align="center">

    <br><br><br>
    <div class="col-md-4 col-md-offset-4">
    <form action='retrieveEmail.php' method='post' id="retrieveEmailForm">
        <fieldset class="form-group">
            <label for="email">E-Mail on your account?: </label>
            <input type="email" class="form-control" name="email" maxlength="100" required="required"><br>
            <div class="g-recaptcha" data-sitekey="6Lf9LCUTAAAAACyCklDmeUNvp4-nDWHVFGYk2cYy"></div>

        </fieldset>
        <button type='submit' class="btn btn-default">Submit</button> <br>
    </form>
    <menu>
    <box><a class="active" href="../../index.php">Login</a></box>
    <box><a class="active" href="../../main/create.php">Create a New Account</a></box>
    <box><a href="retrieve.php">Forgot Your Password</a></box>

</menu>

    <?php
    if ($error==1){
        echo "Please enter your email in the field";
    }
    if ($error==2){
        echo "Sorry we couldn't find your email. Please try again";
    }
    if ($error==3){
        echo "Must verify that you are human!";
    }
    ?>
	</div>
    </body></center>
</html>