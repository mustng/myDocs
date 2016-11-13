<?php
session_start();
$error = $_GET['error'];
?>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../assets/css/custom.css">

    <!-- JS for Google's reCAPTCHA -->
    <script src='https://www.google.com/recaptcha/api.js'></script>
</head>

    <body>
    <div align="center">
    
    <br><br><br>
    <div class="col-md-4 col-md-offset-4">
        <form action='../assets/php/createEmail.php' method='post'>
            <h2>Select your username and email</h2>
            <fieldset class="form-group">
                <label for="username">Desired Username:</label>
                <input type="text" class="form-control" name="username" maxlength="19" required="required">
            </fieldset>
            <fieldset class="form-group">
                <label for="password">Valid E-Mail</label>
                <input type="email" class="form-control" name="email" maxlength="100" required="required">
            </fieldset>
            <fieldset class="form-group">
                <label for="password">Re-Enter E-Mail</label>
                <input type="email" class="form-control" name="emailTwo" maxlength="100" required="required">
            </fieldset>
            <div class="g-recaptcha" data-sitekey="6Lf9LCUTAAAAACyCklDmeUNvp4-nDWHVFGYk2cYy"></div>
            <button type="submit">Log In</button>
            <br>
        </form>
    </div>

    <?php
    if ($error==1){
        echo "Must fill in everything!";
    }
    if ($error==2){
        echo "Username already taken";
    }
    if ($error==3){
        echo "Email was already used";
    }
    if ($error == 4){
        echo "Please verify the captcha";
    }
    if ($error == 5){
        echo "Email did not match";
    }
    ?>
    
	</div>
    </body>
</html>