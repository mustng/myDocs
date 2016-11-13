<?php session_start();
if ($_SESSION['username'] == ""){
    header('Location: ../index.php?error=2');
}
$message = $_GET['message'];
$message2 = $_GET['message2'];
?>
<!DOCTYPE html>
<html>
<?php
include '../assets/html/navbar.html';
?>
    <?php
    //Get the id
    $codeID = $_GET['codeID'];
    include '../assets/php/databaseConnection.php';
    $mySQLConnection = connectToDatabase();
    $codeID = $mySQLConnection->real_escape_string($codeID);
    //Create query string
    $query = "SELECT usc.*, u.Username FROM UserSubmittedCode usc INNER JOIN Users u ON usc.UserID = u.UserID WHERE codeID= $codeID";
    //Issue query against database
    $result = $mySQLConnection->query($query);
    //Fetch row
    $row = $result->fetch_row();       //display code to user
    $codeID = $row[0];
    $UserID = $row[1];
    $code = $row[2];
    $language = $row[3];
    $description = $row[4];
    $authorsUsername = $row[5];
    $query_CodeLikeAmount = "SELECT SUM(LikeValue) AS Likes, CodeID FROM CodeLikesOrDislikes WHERE CodeID = $codeID";
    $result_CodeLikeAmount = $mySQLConnection->query($query_CodeLikeAmount);
    $row = $result_CodeLikeAmount->fetch_assoc();
    $amountOfLikesOnCode = $row['Likes'];
    //Create query string
    $query2 = "select * from Users u INNER JOIN UserSubmittedComments usc on u.UserID = usc.UserID where CodeID= $codeID ORDER BY CommentID";
    $result2 = $mySQLConnection->query($query2);
    //Close database
    $mySQLConnection->close();
    ?>
<body>
    <div class="container-fluid">
        <div class="col-md-10 col-md-offset-1">
            <div class="row">
                <?php echo "<h2>$description</h2><h4>Written by <small>$authorsUsername</small> in $language </h4>" ?>
                <div class="col-md-4 pull-right">

                    <td><?php
                        if ($message==1 || $message2 ==1){
                            echo "You have already submitted feedback";
                        }
                        if ($message==2 || $message2 ==2){
                            echo "Thank you for your feedback";
                        }
                        ?>
                    </td>
                </div>
            </div>

<!--            Start: Show Code-->
            <div class="row">
                <pre><code><?php echo htmlspecialchars($code); ?></code></pre>
            </div>
<!--            End: Show Code-->
<!--            Start: Show Likes/Dislikes-->
            <div class="row">
                <div class="col-md-3 col-md-offset-9">
                    <div class="btn-group pull-right" role="group">
                        <form action="../assets/php/viewCode_CodeLikesOrDislikes.php" method="post" class="no-margin" style="display:inline-block">
                            <input type="hidden" name="sub" value="Bad Code" />
                            <input type='hidden' name='codeID' value='<?php echo $codeID;?>'>
                            <button type="submit" class="btn btn-default" aria-label="Like Code"><span class="glyphicon glyphicon-chevron-down" aria-hidden="true"></button>
                        </form>
                        <form style="display:inline-block">
                            <button type="button" class="btn btn-default"><?php echo $amountOfLikesOnCode; ?></button>
                        </form>
                        <form action="../assets/php/viewCode_CodeLikesOrDislikes.php" method="post" class="no-margin" style="display:inline-block">
                            <input type="hidden" name="add" value="Good Code" />
                            <input type='hidden' name='codeID' value='<?php echo $codeID;?>'>
                            <button type="submit" class="btn btn-default" aria-label="Like Code"><span class="glyphicon glyphicon-chevron-up" aria-hidden="true"></button>
                        </form>
                    </div>
                </div>
            </div>
<!--            End: Show Likes/Dislikes-->
<!--            Start: Show Add Comment Area-->
            <div class="row">
                <div class="col-md-10 col-md-offset-1">
                    <h4>Add Comment</h4>
                    <form action='../assets/php/viewCode_addComment.php' method='post'>
                        <textarea rows="5" cols="75" name='comment' wrap="hard"></textarea>
                        <input class="btn btn-default" type='Submit' value='Add Comment'>
                        <input type='hidden' name='codeID' value='<?php echo $codeID;?>'>
                    </form>
                </div>
            </div><br><br>
<!--            End: Show Add Comment Area-->
<!--            Start: Show Comments and Like/Dislike Buttons-->
            <div class="col-md-10 col-md-offset-1">
                <?php
                while ($row = $result2->fetch_row()){
                    $Username = $row[1];
                    $CommentID = $row[5];
                    $CommentText = $row[7];
                    include '../assets/php/databaseConnection.php';
                    $mySQLConnection = connectToDatabase();
                    $CommentID = $mySQLConnection->real_escape_string($CommentID);
                    $query_CommentLikeAmount = "SELECT SUM(LikeValue) AS Likes FROM CommentLikesOrDislikes WHERE CommentID = $CommentID";
                    $result_CommentLikeAmount = $mySQLConnection->query($query_CommentLikeAmount);
                    $row = $result_CommentLikeAmount->fetch_assoc();
                    $amountOfLikesOnComment = $row['Likes'];
                    $mySQLConnection->close();
                    //Displays comment text
                    echo "<div class='row'><p>$Username</p><div class='col-md-12'>";
                    echo "<pre><code>" . htmlspecialchars($CommentText) . "</code></pre>";
                    echo "</div>";
                    echo "    <div class=\"col-md-3 col-md-offset-9\">
                                <div class=\"btn-group pull-right\" role=\"group\">
                                    <form action=\"../assets/php/viewCode_CommentLikesOrDislikes.php\" method=\"post\" class=\"no-margin\" style=\"display:inline-block\">
                                        <input type=\"hidden\" name=\"subComment\" value=\"Bad Comment\" />
                                        <input type='hidden' name='codeID' value='$codeID'>
                                        <input type=\"hidden\" name=\"CommentID\" value='$CommentID' />
                                        <button type=\"submit\" class=\"btn btn-default\" aria-label=\"Dislike Comment\"><span class=\"glyphicon glyphicon-chevron-down\" aria-hidden=\"true\"></button>
                                    </form>
                                    <form style=\"display:inline-block\">
                                        <button type=\"button\" class=\"btn btn-default\">$amountOfLikesOnComment</button>
                                    </form>
                                    <form action=\"../assets/php/viewCode_CommentLikesOrDislikes.php\" method=\"post\" class=\"no-margin\" style=\"display:inline-block\">
                                        <input type=\"hidden\" name=\"addComment\" value=\"Good Comment\" />
                                        <input type='hidden' name='codeID' value='$codeID'>
                                        <input type=\"hidden\" name=\"CommentID\" value='$CommentID' />
                                        <button type=\"submit\" class=\"btn btn-default\" aria-label=\"Like Comment\"><span class=\"glyphicon glyphicon-chevron-up\" aria-hidden=\"true\"></button>
                                    </form>
                                </div>
                            </div>";
                    echo "</div>";
                }
                ?>
            </div>
<!--            End: Show Comments and Like/Dislike Buttons-->
        </div>
    </div>





    </body>
</html>