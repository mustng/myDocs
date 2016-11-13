<?php
$error = $_GET['error'];
?>
<html>
<head>
    <TITLE>codeShareWeb.com</TITLE>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Global CSS -->
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../assets/css/custom.css">

    <!--Jquery-->
    <script src="https://code.jquery.com/jquery-3.1.0.js" integrity="sha256-slogkvB1K3VOkzAI8QITxV3VzpOnkeNVsKvtkYLMjfk=" crossorigin="anonymous"></script>

    <!--BootstrapJS-->
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</head>
<body>
	<br><br>
		<div class="container-fluid">
			<div style="text-align:center;">
				<h1>CodeShareWeb.com</h1>
				<h3>Academic Integrity Agreement and Terms/Conditions of Use</h3><br>
				<P>Please read and agree to the following Academic Integrity Agreement and the Terms/Conditions of Use for CodeShareWeb.com before you create an account with us.</p><br><br>
			<div>
		<div class="col-md-12">
			<div class="embed-responsive embed-responsive-16by9">
				<iframe class="embed-responsive-item" src="../html/termsAndConditions.htm" width="62%" height="400"> </iframe>
			</div>
			<br><br>
			<form action="checkbox-form.php" method="post">
				<input type="checkbox" name="agree" value="Yes" />Click here to indicate that you have read and agree to the terms presented in the Academic Integrity agreement and the Terms and Conditions agreement.<br>
				<input type="submit" name="formSubmit" value="Submit" />
				<?php
					if ($error==1){
					echo '<script type="text/javascript">alert("Please indicate that you have read and agree to the Terms and Conditions");</script>';
					}
				?>
			</form>
		</div>
	</div>
</body>
</html>