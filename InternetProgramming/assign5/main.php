<?php session_start();
if ($_SESSION['username'] == ""){
    header('Location: login.php?error=2');
}

?>
<!DOCTYPE html>
<html>
<link rel="stylesheet" type="text/css" href="../style.css">
<head>
    <meta charset="utf-8"/>
</head>
<center>
<body>
  
  <menu>
  <box><a href="exit.php">Log Out</a></box>
  <box><a href="mailto:maildavidhughes@yahoo.com">Email Me</a></box>
</menu>
<TITLE>COP 4813 :  Assignment 4</TITLE>
  
<?php
$username = $_SESSION['username'];
echo "Welcome back, $username.\n\n";
?>

<menu>
    <br><br>
    <table>
        <tr>
            <th>Code </th>
            <th>Name</th>
            <th>Current Price</th>
            <th>Last Paid</th>
            <th>On Date</th>
            <th>Shares</th>
            <th>Profit</th>
            <th>Potential Profit</th>
            <th>Change and Percent Change</th>
        </tr>

    <?php
   // $y = 0;  //counter
    include_once('class.yahoostock.php');

    $objYahooStock = new YahooStock;

    /**
    Add format/parameters to be fetched

    s = Symbol
    n = Name
    l1 = Last Trade (Price Only)
    d1 = Last Trade Date
    t1 = Last Trade Time
    c = Change and Percent Change
    v = Volume
     */
    $objYahooStock->addFormat("snl1d1t1cv");

    /**
    Add company stock code to be fetched

    msft = Microsoft
    amzn = Amazon
    yhoo = Yahoo
    goog = Google
    aapl = Apple
     * 
     * 
     * <?php
    $file = fopen("test.txt","r");

    while(! feof($file))
    {
    echo fgets($file). "<br />";
    }

    fclose($file);
    ?>
     */
    $myFile = "stock.dat";
    $fh = fopen($myFile, 'r');
    $theData = fread($fh, filesize($myFile));       //read in stock data
    fclose($fh);

    $stock = explode("|",$theData);
    $_SESSION['stock'] = $stock;                    //make it usable to modify

    $myFile = "shares.dat";
    $fh = fopen($myFile, 'r');
    $theData = fread($fh, filesize($myFile));       //read in share data
    fclose($fh);

    $shares = explode("|",$theData);
    $_SESSION['shares'] = $shares;

    $myFile = "Profit.dat";
    $fh = fopen($myFile, 'r');
    $theData = fread($fh, filesize($myFile));       //read in profit data
    fclose($fh);

    $profit = explode("|",$theData);
    $_SESSION['profit'] = $profit;

    $myFile = "paid.dat";
    $fh = fopen($myFile, 'r');
    $theData = fread($fh, filesize($myFile));       //read in paid data
    fclose($fh);

    $paid = explode("|",$theData);
    $_SESSION['paid'] = $paid;

    $myFile = "date.dat";
    $fh = fopen($myFile, 'r');
    $theData = fread($fh, filesize($myFile));       //read in paid data
    fclose($fh);

    $date = explode("|",$theData);
    $_SESSION['date'] = $date;

    for($x = 0; $x < count($stock); $x++) {
        $objYahooStock->addStock($stock[$x]);       //add stock to be fetched
    }

    /**
     * Printing out the data
     */
    $y = 0;
    foreach( $objYahooStock->getQuotes() as $code => $stock)
    {
        ?>
        <tr>
        <td><?php echo $stock[0]; ?> <br /></td>
        <td><?php echo $stock[1]; ?> <br /></td>
        <td><?php echo round($stock[2],2); ?> <br /></td>
            <td><?php echo $paid[$y]; ?> <br /></td>
            <td><?php echo $date[$y]; ?> <br /></td>
        <td><?php echo $shares[$y]; ?> <br /></td>
        <td><?php echo $profit[$y]; ?> <br /></td>
            <td><?php echo round(($stock[2] - $paid[$y]) * $shares[$y],2); ?> <br /></td>
            <td><?php echo $stock[5]; ?> <br /></td>
        </tr>
        <?php
        $y++;
    }
    $moneyMade = array_sum($profit);
    ?>
        <tr>
            <td><?php echo "The total Profit Made = $$moneyMade"; ?> <br /></td>
        </tr>
        </table>
    <br><br>
    <form action='change.php' method='post'>
        Please enter the 3 or 4 letter ticker into the boxes to change:<br>
        How many shares<input type='number' name='shares'/>   Stock tick<input type='text' name='stock'/><input type='submit' value='Add Stock' /><br><br>
        +/- the shares to change<input type='number' name='modifyNum'/>Stock tick<input type='text' name='modify'/><input type='submit' value='Modify Stock'/><br><br>
        <input type='text' name='del'/><input type='submit' value='Delete Stock'/><br>
    </form>
    <br><br>
</body></center>
</html>
