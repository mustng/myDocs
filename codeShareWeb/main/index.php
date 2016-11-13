<?php session_start();
if ($_SESSION['username'] == ""){
    header('Location: ../index.php?error=2');
}

?>

<html>
<?php
    include '../assets/html/navbar.html';
?>
<script>
    function changeRecord()
    {
        document.myForm.action='viewCode.php';
        document.myForm.submit();
    }

    jQuery(document).ready(function($) {
        $(".clickable-row").click(function() {
            window.document.location = $(this).data("href");
        });
    });

</script>

<?php
//Establish a connection to the database
include '../assets/php/databaseConnection.php';
$mySQLConnection = connectToDatabase();

$topCodeQuery = "SELECT usc.*, u.Username
                    FROM (
                        SELECT SUM(LikeValue) AS Likes, CodeID FROM CodeLikesOrDislikes  GROUP BY CodeID ORDER BY Likes Desc LIMIT 10) as TopCodes
                    INNER JOIN UserSubmittedCode usc ON TopCodes.CodeID = usc.CodeID
                    INNER JOIN Users u on usc.UserID = u.UserID";
$mostRecentCodeQuery = "SELECT usc.*, u.Username FROM UserSubmittedCode usc
                          INNER JOIN Users u ON usc.UserID = u.UserID
                          ORDER BY CodeID DESC LIMIT 10";

$topCodeResult = $mySQLConnection->query($topCodeQuery);
$mostRecentCodeResult = $mySQLConnection->query($mostRecentCodeQuery);

//Close database
$mySQLConnection->close();

function displayCode($header, $result) {
    if ($result->num_rows <= 0){
        return;
    }
    // output data of each row
    echo "<div class='col-md-6'>";
    echo "<h2>$header</h2>";
    echo "
                <table class='table table-striped table-hover tableWrap'>
                  <thead>
                    <tr>
                      <th>Language</th>
                      <th>Description</th>
                      <th>Author</th>
                    </tr>
                  </thead>
                  <tbody>
            ";

    while($row = $result->fetch_assoc()) {
        $codeID = $row['CodeID'];
        $codeURL = "viewCode.php?codeID=$codeID";
        echo "
                    <tr class='clickable-row' data-href='".$codeURL."'>
                        <td>".$row['ProgrammingLanguage']."</td>
                        <td name='codeID'>".$row['Description']."</td>
                        <td>".$row['Username']."</td>
                    </tr>
                ";
    }
    echo "
                </tbody>
            </table>
            ";
    echo "</div>";

}
?>

<body>

    <div class="container-fluid">
        <div class="col-md-12">
            <div align="right">
                <font size="4">
                <?php
                $username = $_SESSION['username'];
                echo "Welcome back, $username";
                ?>
                </font>
            </div>
        </div>
        <?php
            displayCode("Top Code", $topCodeResult);
            displayCode("Recent Code", $mostRecentCodeResult);
        ?>
    </div>
</body>
</html>
