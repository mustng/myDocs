<?php session_start();
if ($_SESSION['username'] == ""){
    header('Location: ../index.php?error=2');
}

?>
<script>
    function changeRecord()
    {
        var radios = document.getElementsByName('codeID');
        var selectedFlag = false;
        for (var i = 0; i < radios.length; i++) {
            if (radios[i].checked) {
                selectedFlag = true;
                break;
            }
        }
        if(selectedFlag){
            document.myForm.action='myCode_change.php';
            document.myForm.submit();
        }else{
            document.getElementById("warningInfo").innerText = "Select a radio box to proceed";
        }

    }
    function deleteRecord()
    {
        var radios = document.getElementsByName('codeID');
        var selectedFlag = false;
        for (var i = 0; i < radios.length; i++) {
            if (radios[i].checked) {
                selectedFlag = true;
                break;
            }
        }
        if(selectedFlag){
            document.myForm.action='../assets/php/myCode_delete.php';
            document.myForm.submit();
        }else{
            document.getElementById("warningInfo").innerText = "Select a radio box to proceed";
        }

    }
    function viewCode(){
        var radios = document.getElementsByName('codeID');
        var selectedFlag = false;
        for (var i = 0; i < radios.length; i++) {
            if (radios[i].checked) {
                selectedFlag = true;
                window.location = "viewCode.php?codeID="+radios[i].value;
                break;
            }
        }
        if(!selectedFlag){
            document.getElementById("warningInfo").innerText = "Select a radio box to proceed";
        }
    }
</script>
<html>
<?php
include '../assets/html/navbar.html';
?>
<script>
    jQuery(document).ready(function($) {
        $('table tr').click(function(){
            $(this).find('td input:radio').prop('checked', true);
        });
    });
</script>
<TITLE>codeShareWeb.com</TITLE>

<?php

$username = $_SESSION['username'];
$UserID = $_SESSION['UserID'];


//Establish a connection to the database
include '../assets/php/databaseConnection.php';
$mySQLConnection = connectToDatabase();

$UserID = $mySQLConnection->real_escape_string($UserID);

$query2 = "SELECT * FROM UserSubmittedCode WHERE UserID = '$UserID'";  //get all of users code
$result2 = $mySQLConnection->query($query2);

//Close database
$mySQLConnection->close();

?>
</head>
<body>
    <div class="container-fluid">
        <div class="col-md-4 col-md-offset-4">
            <p class="bg-danger" id="warningInfo">
            </p>
        </div>
        <div class="row">

            <div class="col-md-10 col-md-offset-1">
                <?php displayUserCode($result2); ?>
            </div>
            <div class="col-md-10 col-md-offset-1">
                <h2 style="text-align: center">New Code</h2>
                <form action='../assets/php/myCode_add.php' method='post'>
                    <table class="table">
                        <tr>
                            <td>Language:</td><td>
                                <select name="language">
                                    <option value="Java">Java</option><option value="JavaScript">JavaScript</option>
                                    <option value="C">C</option><option value="C++">C++</option>
                                    <option value="PHP">PHP</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>Description: </td><td><input type='text' name='description'></td>
                        </tr>
                        <tr>
                            <td>Code:</td><td> <textarea rows="10" cols="100" name='code' wrap="hard" ></textarea></td>
                        </tr>
                        <tr>
                            <td></td><td><input type='Submit' value='Add Code'></td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
</body>
</html>

<?php
function displayUserCode($result2){
    if ($result2->num_rows <= 0){
    }
    else{
        echo "<h2 style=\"text-align: center\">Your Code</h2>";
        echo "<form action='' method='post' name='myForm'>";

        echo "<table class='table table-striped table-hover'>
                <thead>
                    <tr>
                      <th></th>
                      <th>Language</th>
                      <th>Description</th>
                    </tr>
                  </thead>";

        while($row2 = $result2->fetch_row())
        {
            $codeID = $row2[0];
            $codeText = $row2[1];
            $language = $row2[3];
            $description = $row2[4];

            echo "<tr>";
            echo "<td><input type='radio' name='codeID' value='" . $codeID . "'>";
            echo "<td>$language</td>";
            echo "<td>$description</td>";
            echo "</tr>";
        }
        echo "</table>";

        echo "<div align='center'>";
        echo "<div class='btn-group'>";
        echo "<input type='button' class='btn btn-default' value='Change Code' onClick='changeRecord()'>";
        echo "<input type='button' class='btn btn-default' value='Delete Code' onClick='deleteRecord()'>";
        echo "<input type='button' class='btn btn-default' value='View Code' onClick='viewCode()'>";
        echo "</div>";
        echo "</div>";
        echo "</form>";
    }
}
?>