<?php session_start();
if ($_SESSION['username'] == ""){
    header('Location: ../index.php?error=2');
}
$error = $_GET['error'];
$error2 = $_GET['error2'];
?>
<html>

<?php
include '../assets/html/navbar.html';
?>
<TITLE>codeShareWeb.com</TITLE>

<body>
	<div class="container-fluid">
		<div class="col-md-6 col-md-offset-3">
			<form action='../assets/php/myProfile_change.php' method='post'>
				<div class="table-responsive">
					<table class="table">
						<caption><h3>Change Password</h3></caption>
						<tr>
							<td>Old Password :</td>
							<td><input type='password' name='oldPassword'></td>
						</tr>
						<tr>
							<td>New Password :</td>
							<td><input type='password' name='newPassword' maxlength="99"></td>
						</tr>
						<tr>
							<td> Re-Type Password :</td>
							<td><input type='password' name='newPassword2' maxlength="99"></td>
						</tr>
						<tr>
							<td colspan='2'><input type='Submit' value='Change Password'></td>
						</tr>
					</table>
				</div>

				<?php
					if ($error==1){
						echo "Your old password does not match. Worse case log out and have it re-emailed to yourself";
					}
					if ($error==2){
						echo "Passwords dont match";
					}
					if ($error==3){
						echo "Password has been updated";
					}
				?>
			</form>
			<hr>
			<form action='../assets/php/myProfile_changeUsername.php' method='post'>
				<div class="table-responsive">
					<table class="table">
						<caption><h3>Change Username</h3></caption>
						<tr>
							<td>Current Password :</td>
							<td><input type='password' name='password'></td>
						</tr>
						<tr>
							<td>New Username :</td>
							<td><input type='text' name='user1' maxlength="99"></td>
						</tr>
						<tr>
							<td> Re-Type Username :</td>
							<td><input type='text' name='user2' maxlength="99"></td>
						</tr>
						<tr>
							<td colspan='2'><input type='Submit' value='Change Username'></td>
						</tr>
					</table>
				</div>

				<?php
					if ($error2==1){
						echo "Your password does not match. Worse case log out and have it re-emailed to yourself";
					}
					if ($error2==2){
						echo "Username doesn't match";
					}
					if ($error2==3){
						echo "Username has been updated";
					}
					if ($error2==4){
						echo "Username already taken";
					}

				?>
			</form>
		</div>
	</div>
</body>
</html>
