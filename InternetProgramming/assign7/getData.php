<?php
$year = $_GET['selection'];

$mysql_access = mysql_connect('localhost', 'n00814425', 'summer2016814425');
if (!$mysql_access)
{
    echo "Connection failed.";
    exit;
}
mysql_select_db("n00814425");

$query = "Select * from Movies where year=" . $year;

//Issue query against database
$result = mysql_query($query, $mysql_access);

//Close database
mysql_close($mysql_access);

echo "<table>
  <tr>
    <th>Movie</th><th>Rated</th><th>Year</th><th>Closed Captions</th>
	<th>Rating</th><th>Comments</th>
  </tr>";

while($row = mysql_fetch_row($result)) {
     $movieID = $row[0];
     $movie = $row[1];
     $rated = $row[2];
     $year = $row[3];
     $cc = $row[4];
     $rating = $row[5];
     $comments = $row[6];

     echo "<td>$movie</td>\n";
     echo "<td>$rated</td>\n";
     echo "<td>$year</td>\n";
     echo "<td>$cc</td>\n";
     echo "<td>$rating</td>\n";
     echo "<td>$comments</td>\n";
     echo "</tr>";
}
echo "</table>";

?>