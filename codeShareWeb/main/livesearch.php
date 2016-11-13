<?php
	session_start();
	$error = $_GET['error'];
?>
<html>
<head>

<!--style sheet being brought over for consistency-->
		<title>COP 4813: Internet Programming</title>
		<link rel="stylesheet" type="text/css" href="../style.css"> 
		<style type="text/css">
.div {
   position: absolute;
   left: 0;
   right: 0;
   margin-left: 0;
   margin-right: 0;
}		
</style>
</head>
<div align="center">
<?php
//Establish a connection to the database
//include '../assets/php/databaseConnection.php';
//$mySQLConnection = connectToDatabase();
//        $query = "SELECT * FROM UserSubmittedCode order by Description";
        //Issue query against database
//        $result = mysql_query($query, $mysql_access);

	$mysql_access = mysql_connect(localhost, 'group3', 'summer2016656400');

//Check for connection
       if(!$mysql_access)
        	{
        	die('Could not connect: ' . mysql_error());
        	}

//Select the database
        mysql_select_db('cop4813group3');

//        //Create query string
        $query = "SELECT * FROM UserSubmittedCode order by Description";

//        //Issue query against database
        $result = mysql_query($query, $mysql_access);
?>
<body>
<div id="index2">
	<table style="width: 100%" cellspacing="0" cellpadding="0"> 
		<tr>
			<td style="width: 100%" > 
			<h3 style="color:blue" align="center"><u>Search List</u></h3> </td> 
		</tr>
	</table>

<!-- <form>
<input type="text" size="30" onkeyup="showResult(this.value)"> 

</form> -->
</div>
<div id="index"></div>
<form action="process.php" method="post" name='myForm'>
<table>
<tr>
</tr>
<tr>

<td>
<div id="index0" style="border: thin solid black; height:200px; overflow-y: scroll;">
<table>
<!--<td></td>-->
<td style="padding-left: 10px"><h3>Language</h3></td>
<td style="padding-left: 15px"><h3>Description</h3></td>
<tr>

<?php

//get the q parameter from URL
$q=$_GET["str2"];
$r=$_GET["str1"];
$s=$_GET["str3"];
$_SESSION[codeSearch]=$s;


$firstTime="0";

//lookup all links from the xml file if length of q, r or s > 0

	if (strlen($r)>=0 || strlen($q)>=0 || strlen($s)>=0)
		{
		while($row = mysql_fetch_row($result))
			{
			if (checkForMatch ($r,$row[3]))
				{
				if (checkForMatch ($q,$row[4]))
					{
					if (checkForMatchCS ($s,$row[2]))
						{
//						if ($firstTime < "1")
//							{
//							$firstTime = "1";
//							echo "<td><input type='radio' name='CodeID' value=$row[0] checked></td>";
//							}
//						else
//							{
//							echo "<td><input type='radio' name='CodeID' value=$row[0]></td>";
//							}
  	    				echo "<td style='padding-left: 10px'>$row[3]</td>";
  	    				$r0 = "<p><a href='viewCode.php?codeID=";
						$r0 .= $row[0];
						$r0 .= "'>";
						$r0 .= $row[4];
						$r0 .= "</a></p>";
						echo "<td style='padding-left: 15px'>$r0</td>";
//	      				echo "<td>$row[4]</td>";
	      				echo "</tr>";
						}
					}
				}		
			}
		}
	mysql_close($mysql_access);

function checkForMatch ($var1,$var2)
	{
	if ($var1)
		{
		if (stristr($var2,$var1))
			{
			return true;
			}
		else
			{
			return false;
			}
		}
	else
		{
		return true;
		}
	}	
		
function checkForMatchCS ($var1,$var2)
	{
	if ($var1)
		{
		if (strstr($var2,$var1))
			{
			return true;
			}
		else
			{
			return false;
			}
		}
	else
		{
		return true;
		}
	}	
	

?>
</tr>
</table>
</div>
</td>
</tr>
</table>
<table>
<tr>
<td>
</td>
</tr>

</table>
<!--<input type='submit' name='b1' style="color:purple" value='View Code'>
<input type='submit' name='b1' style="color:green" value='My Code'>
<input type='submit' name='b1' style="color:blue" value='My Profile'>
<input type='submit' name='b1' style="color:red" value='About CSW'>
<input type='submit' name='b1' style="color:black" value='Exit'>-->


</form>

<?php
	if($error==10)
	{
		phpAlert("You must select a name.");
	}
	if($error==11)
	{
		phpAlert("");
	}
	if($error==12)
	{
		phpAlert("");
	}
	if($error==13)
	{
		phpAlert("");
	}
?>


<?php
function phpAlert($msg)
	{
    echo '<script type="text/javascript">alert("' . $msg . '")</script>';
	}

?>


</body>
</div>

</html>