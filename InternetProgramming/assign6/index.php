<html>
<link rel="stylesheet" type="text/css" href="../style.css">
  <menu>
  <box><a class="active" href="../index.html">Home</a></box>
  <box><a href="../assign1/index.html">Assignment 1</a></box>
  <box><a href="../assign2/index.html">Assignment 2</a></box>
  <box><a href="../assign3/index.html">Assignment 3</a></box>
  <box><a href="../assign4/index.html">Assignment 4</a></box>
  <box><a href="../assign5/login.php">Assignment 5</a></box>
  <box><a href="index.php">Assignment 6</a></box>
  <box><a href="../assign7/index.php">Assignment 7</a></box>
  <box><a href="mailto:maildavidhughes@yahoo.com">Email Me</a></box>
</menu>
<TITLE>COP 4813 :  Assignment 6</TITLE>
<head><center><font size='5'><img src="amazon.jpg" alt="amazon" width="100" height="120">Amazon Primes Most Popular Movies</font></center><br><br><br>
<script>
	function changeRecord()
	{
		document.myForm.action='change.php';
		document.myForm.submit();
	}
    function deleteRecord()
    {
        document.myForm.action='delete.php';
        document.myForm.submit();
    }

</script>
<?php
        //Establish a connection to the database
        $mysql_access = mysql_connect(localhost, 'n00814425', 'summer2016814425');

        //Check for connection
        if(!$mysql_access){
                die('Could not connect: ' . mysql_error());
        }

        //Select the database
        mysql_select_db('n00814425');

        //Create query string
        $query = "SELECT * FROM Movies";

        //Issue query against database
        $result = mysql_query($query, $mysql_access);

        //Close database
        mysql_close($mysql_access);

?>
</head>
<center><body>


<form action='' method='post' name='myForm'>
    <?php
    echo "<table>
  <tr>
    <th></th><th>Movie</th><th>Rated</th><th>Year</th><th>Closed Captions</th>
	<th>Rating</th><th>Comments</th>
  </tr>";
    while($row = mysql_fetch_row($result))
    {
        $movieID = $row[0];
        $movie = $row[1];
        $rated = $row[2];
        $year = $row[3];
        $cc = $row[4];
        $rating = $row[5];
        $comments = $row[6];

        echo "<tr>";
        echo "<td><input type='radio' name='movieID' value='" . $movieID . "'>";
        echo "<td>$movie</td>";
        echo "<td>$rated</td>";
        echo "<td>$year</td>";
        echo "<td>$cc</td>";
        echo "<td>$rating</td>";
        echo "<td>$comments</td>";
        echo "</tr>";
    }
    echo "</table>";
    ?><br><br>
    <input type='button' value='Change Record' onClick='changeRecord()'>
    <input type='button' value='Delete Record' onClick='deleteRecord()'>

</form><br><br>
<form action='add.php' method='post'>
<table>
	
	<tr>
		<th>Movie:</th><th><input type='text' name='movie'></th>
	</tr>
        <tr>
                <th>Rated:</th><th><input type='radio' value ="G"  name='rated'>G
                <input type='radio' value ="PG" name='rated'>PG
                <input type='radio' value ="PG-13" name='rated'>PG-13
                <input type='radio' value ="R" name='rated'>R</th>
        </tr>
        <tr>
            <th>Year:</th><th>
            <select name="year">
                <option value="2016">2016</option><option value="2015">2015</option><option value="2014">2014</option>
                <option value="2013">2013</option><option value="2012">2012</option><option value="2011">2011</option>
                <option value="2010">2010</option><option value="2009">2009</option><option value="2008">2008</option>
                <option value="2007">2007</option><option value="2006">2006</option><option value="2005">2005</option>
            </select>
            </th>
        </tr>
        <tr>
                <th>Closed Captions</th><th><input type='checkbox' value="Yes" name='cc'></th>
        </tr>
        <tr>
            <th>Rating:</th><th><input type='radio' value ="1*"  name='rating'>1*
                <input type='radio' value ="2**" name='rating'>2**
                <input type='radio' value ="3***" name='rating'>3***
                <input type='radio' value ="4****" name='rating'>4****
                <input type='radio' value ="5*****" name='rating'>5****</th>
        </tr>
        <tr>
        <th>Comments:</th><th><input type='text' name='comments'></th>
    </tr>

		<th colspan='2'><input type='Submit' value='Add Record'><input type='Reset'></th>

</table>
</form>
</body></center>
</html>
