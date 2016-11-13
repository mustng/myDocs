<?php
	session_start();
	$error = $_GET['error'];
?>
<html>
<head>
	<?php
	include '../assets/html/navbar.html';
	?>
<script>
function showResult(str) {
	var str1 = document.forms["searchform"]["sLanguage"].value;
	var str2 = document.forms["searchform"]["sDescription"].value;
	var str3 = document.forms["searchform"]["sCode"].value;
	if (str1.length==0 && str2.length==0 && str3.length==0)
  	{ 
	document.getElementById('index5').style.visibility="visible";
    document.getElementById("ajaxdiv").innerHTML="";
    document.getElementById("ajaxdiv").style.border="px";
    return;
  }

  if (window.XMLHttpRequest) {
    // code for IE7+, Firefox, Chrome, Opera, Safari
    xmlhttp=new XMLHttpRequest();
  } else {  // code for IE6, IE5
    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
  xmlhttp.onreadystatechange=function() {
    if (xmlhttp.readyState==4 && xmlhttp.status==200) {
      document.getElementById("ajaxdiv").innerHTML=xmlhttp.responseText;
      document.getElementById("ajaxdiv").style.border="1px solid #A5ACB2";
    }
  }
  document.getElementById('index5').style.visibility="hidden";
  var str1a = encodeURIComponent(str1);
  var str2a = encodeURIComponent(str2);
  var str3a = encodeURIComponent(str3);
  xmlhttp.open("GET","livesearch.php?str1="+str1a+"&str2="+str2a+"&str3="+str3a,true);
  xmlhttp.send();
}
</script>
<!--style sheet being brought over for consistency-->
		<title>COP 4813: Internet Programming</title>
		<link rel="stylesheet" type="text/css" href="../../../style.css"> 
		<style type="text/css">
.div {
   position: absolute;
   left: 0;
   right: 0;
   margin-left: 0;
   margin-right: 0;
}		
.style1 {
				text-align: center;
}
.style2 {
				color: #FF0000;
}
</style>
</head>
<div align="center">
<?php
//Establish a connection to the database
//include '../assets/php/databaseConnection.php';
//$mySQLConnection = connectToDatabase();
//        $query = "SELECT * FROM UserSubmittedCode order by Description";
//        //Issue query against database
//        $result = mysql_query($query, $mysql_access);



//	$mysql_access = mysql_connect(localhost, 'group3', 'UNFr0ck5');
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
			<td style="width: 100%" class="style1"> <h1 class="style1">Code Share Web</h1> </td> 
		</tr>
	</table>

<form name="searchform">
<table>
<tr>
<td style="padding: 10px"> <input name="sLanguage" id="sLanguage" type="text" size="30" onkeyup="showResult(this.value)"></td>
<td style="padding: 10px"><input name="sDescription" id="sDecriiption" type="text" size="30" onkeyup="showResult(this.value)"></td>
<td style="padding: 10px"><input name="sCode" id="sCode" type="text" size="30" onkeyup="showResult(this.value)"></td>
<tr>
<td align="center">Language</td>
<td align="center">Description</td>
<td align="center">Code <span class="style2">(Case Sensitive)</span></td>
</tr>


</table>
<!--<input name="sDescription" id="sDecriiption" type="text" size="30" onkeyup="showResult(this.value)">-->
<!--<p>Live Search</p>-->
<h4>LIVE SEARCH</h4><p>(Search any/all categories)</p>

</form>
</div>
<div id="ajaxdiv"></div>
<div id="index5">

<form action="process.php" method="post" name='myForm'>
<table>
<tr>
</tr>
<tr>
<tr>
<td style="width: 100%" > 
			<h3 style="color:green" align="center"><u>Full List</u></h3> </td> 
		</tr>


<td>
<div id="index0" style="border: thin solid black; height:200px; overflow-y: scroll;">
<table>
<!--<td></td>-->
<td style="padding-left: 10px"><h3>Language</h3></td>
<td style="padding-left: 15px"><h3>Description</h3></td>
<tr>

<?php
	$firstTime="0";
	$_SESSION[codeSearch]="";
	while($row = mysql_fetch_row($result))
		{
//		if ($firstTime < "1")
//			{
//			$firstTime = "1";
//			echo "<td><input type='radio' name='CodeID' value=$row[0] checked></td>";
//			}
//		else
//			{
//			echo "<td><input type='radio' name='CodeID' value=$row[0]></td>";
//			}
		echo "<td style='padding-left: 10px'>$row[3]</td>";
//		echo "<td></td>";
		$r0 = "<p><a href='viewCode.php?codeID=";
		$r0 .= $row[0];
		$r0 .= "'>";
		$r0 .= $row[4];
		$r0 .= "</a></p>";
		echo "<td style='padding-left: 15px'>$r0</td>";
//    	echo "<td>$row[4]</td>";
    	echo "</tr>";
		}
	mysql_close($mysql_access);

?>
</tr>
</table>
</div>
</td>

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

<br>
				<br>



</form>


</div>


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