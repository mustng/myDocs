<?php session_start();
if ($_SESSION['username'] == ""){
    header('Location: ../index.php?error=2');
}

?>
<html>
<head>
    <?php
    //Get the id
    $codeID = $_POST['codeID'];

    //Establish a connection to the database
    include '../assets/php/databaseConnection.php';
    $mySQLConnection = connectToDatabase();

    //Create query string
    $query = "SELECT * FROM UserSubmittedCode WHERE codeID= $codeID";

    //Issue query against database
    $result = $mySQLConnection->query($query);

    //Fetch row
    $row = $result->fetch_row();
    $codeID = $row[0];
    $language = $row[3];
    $description = $row[4];
    $code = $row[2];

    //Close database
    $mySQLConnection->close();

    ?>
    <?php
        include '../assets/html/navbar.html';
    ?>
</head>
<body>
    <div class="container-fluid">
        <div class="col-md-10 col-md-offset-1">
            <form action='../assets/php/myCode_change_p.php' method='post'>
                <table class="table table-responsive">
                    <tr>
                        <td>Language:</td><td>
                            <select name="language">
                                <option value="Java"<?php if($language == 'Java'){echo "selected";}; ?>>Java</option>
                                <option value="JavaScript"<?php if($language == 'JavaScript'){echo "selected";}; ?>>JavaScript</option>
                                <option value="C"<?php if($language == 'C'){echo "selected";}; ?>>C</option>
                                <option value="C++"<?php if($language == 'C++'){echo "selected";}; ?>>C++</option>
                                <option value="PHP"<?php if($language == 'PHP'){echo "selected";}; ?>>PHP</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Description: :</td><td><input type='text' value='<?php echo $description; ?>' name='description'></td>
                    </tr>
                    <tr>
                        <td>Code:</td><td> <textarea rows="10" name='code' wrap="hard"><?php echo $code; ?></textarea></td>
                    </tr>
                    <tr>
                        <td colspan='2'><input type='Submit' value='Change Code'></td>
                    </tr>
                </table>
                <input type='hidden' name='codeID' value='<?php echo $codeID; ?>'>
            </form>
        </div>
    </div>
</body>
</html>
