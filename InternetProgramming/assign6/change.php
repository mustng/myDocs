<html>
<link rel="stylesheet" type="text/css" href="../style.css">
<head>
<?php
	//Get the id
    $movieID = $_POST['movieID'];

        //Establish a connection to the database
        $mysql_access = mysql_connect(localhost, 'n00814425', 'summer2016814425');

        //Check for connection
        if(!$mysql_access){
                die('Could not connect: ' . mysql_error());
        }

        //Select the database
        mysql_select_db('n00814425');

        //Create query string
        $query = "SELECT * FROM Movies WHERE movieID=" . $movieID;

        //Issue query against database
        $result = mysql_query($query, $mysql_access);

	//Fetch row
	$row = mysql_fetch_row($result);
        $movieID = $row[0];
        $movie = $row[1];
        $rated = $row[2];
        $year = $row[3];
        $cc = $row[4];
        $rating = $row[5];
        $comments = $row[6];

        //Close database
        mysql_close($mysql_access);

?>
</head>
<center><body>
<form action='change_p.php' method='post'>
<table>
        <tr>
                <td>Movie:</td><td><input type='text' name='movie' value='<?php echo $movie; ?>'></td>
        </tr>
    <tr>
            <td>Rated:</td><td><input type='radio' value ="G"name='rated'<?php if($rated == 'G'){echo "checked";}; ?>>G
                <input type='radio' value ="PG" name='rated'<?php if($rated == 'PG'){echo "checked";}; ?>>PG
                <input type='radio' value ="PG-13" name='rated'<?php if($rated == 'PG-13'){echo "checked";}; ?>>PG-13
                <input type='radio' value ="R" name='rated'<?php if($rated == 'R'){echo "checked";}; ?>>R</td>
    </tr>
    <tr>
        <td>Year:</td><td>
            <select name="year">
                <option value="2016"<?php if($year == '2016'){echo "selected";}; ?>>2016</option><option value="2015"<?php if($year == '2015'){echo "selected";}; ?>>2015</option>
                <option value="2014"<?php if($year == '2014'){echo "selected";}; ?>>2014</option><option value="2013"<?php if($year == '2013'){echo "selected";}; ?>>2013</option>
                <option value="2012"<?php if($year == '2012'){echo "selected";}; ?>>2012</option><option value="2011"<?php if($year == '2011'){echo "selected";}; ?>>2011</option>
                <option value="2010"<?php if($year == '2010'){echo "selected";}; ?>>2010</option><option value="2009"<?php if($year == '2009'){echo "selected";}; ?>>2009</option>
                <option value="2008"<?php if($year == '2008'){echo "selected";}; ?>>2008</option><option value="2007"<?php if($year == '2007'){echo "selected";}; ?>>2007</option>
                <option value="2006"<?php if($year == '2006'){echo "selected";}; ?>>2006</option><option value="2005"<?php if($year == '2005'){echo "selected";}; ?>>2005</option>
            </select>
    </td>
    </tr>
    <tr>
        <td>Closed Captions</td><td><input type='checkbox' value="Yes"  <?php if($cc == 'Yes'){echo "checked";}; ?> name='cc'></td>
    </tr>
    <tr>
        <td>Rating:</td><td><input type='radio' value ="1*"  name='rating'<?php if($rating == '1*'){echo "checked";}; ?>>1*
            <input type='radio' value ="2**" name='rating'<?php if($rating == '2**'){echo "checked";}; ?>>2**
            <input type='radio' value ="3***" name='rating'<?php if($rating == '3***'){echo "checked";}; ?>>3***
            <input type='radio' value ="4****" name='rating'<?php if($rating == '4****'){echo "checked";}; ?>>4****
            <input type='radio' value ="5*****" name='rating'<?php if($rating == '5*****'){echo "checked";}; ?>>5****</td>
    </tr>
    <tr>
        <td>Comments:</td><td><input type='text' value='<?php echo $comments; ?>' name='comments'></td>
    </tr>
</table>
<input type='hidden' name='movieID' value='<?php echo $movieID; ?>'>
<input type='submit' value='Change Record'>
</form>
</body></center>
</html>
