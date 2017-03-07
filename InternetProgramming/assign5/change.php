<?php
session_start();

$stock = $_POST["stock"];
$shares = $_POST["shares"];
$modify = $_POST["modify"];
$modifyNum = $_POST["modifyNum"];
$del = $_POST["del"];

$stockList = $_SESSION['stock'];
$sharesList = $_SESSION['shares'];
$profitList = $_SESSION['profit'];
$paidList = $_SESSION['paid'];
$dateList = $_SESSION['date'];

if ($stock != "" && $shares != ""){
    
    $myFile = "stock.dat";
    $fh = fopen($myFile, 'a') or die("can't open file");
    fwrite($fh, "|$stock");
    fclose($fh);

    $myFile = "shares.dat";
    $fh = fopen($myFile, 'a') or die("can't open file");
    fwrite($fh, "|$shares");
    fclose($fh);

    $s = file_get_contents("http://finance.yahoo.com/d/quotes.csv?s=$stock&f=l1&e=.csv");
    $myFile = "paid.dat";
    $fh = fopen($myFile, 'a') or die("can't open file");
    fwrite($fh, "|$s");
    fclose($fh);

    $myFile = "Profit.dat";
    $fh = fopen($myFile, 'a') or die("can't open file");
    fwrite($fh, "|0");
    fclose($fh);

    $myFile = "date.dat";
    $fh = fopen($myFile, 'a') or die("can't open file");
    fwrite($fh, "|".date("m/d/Y@h:ia"));
    fclose($fh);
}

else if ($del != ""){                    //To delete stock

    function delInDatFile($index, $fileName, $array) {
        unset($array[$index]);                 //funtion to rewrite deleted data into file
        $array = implode("|", $array);
        $myFile = $fileName;
        $fh = fopen($myFile, 'w') or die("can't open file");
        $stringData = $array;
        fwrite($fh, $stringData);
        fclose($fh);
    }

    $index = array_search($del,$stockList);            //get index of value to remove later
   // unset($stockList[array_search($del,$stockList)]);

    delInDatFile($index, "stock.dat", $stockList);
    delInDatFile($index, "shares.dat", $sharesList);
    delInDatFile($index, "Profit.dat", $profitList);
    delInDatFile($index, "paid.dat", $paidList);
    delInDatFile($index, "date.dat", $dateList);


}
else if ($modify != "" && $modifyNum != ""){            //modify the stocks

    $index = array_search($modify,$stockList);            //get index of value to remove later

    $s = file_get_contents("http://finance.yahoo.com/d/quotes.csv?s=$modify&f=l1&e=.csv");

    $total = $sharesList[$index] + $modifyNum;  //calculate diffrence
    $sharesList[$index] += $modifyNum;      //calculate shares

    if ($modifyNum < 0){
        $profitList[$index] = ($s*$total)+ $profitList[$index];

    }
    else{
        $profit[$index] = $profitList[$index] - ($s*$total);
    }

    $profitList = implode("|", $profitList);
    $myFile = "Profit.dat";
    $fh = fopen($myFile, 'w') or die("can't open file");
    $stringData = $profitList;
    fwrite($fh, $stringData);
    fclose($fh);

    $sharesList = implode("|", $sharesList);
    $myFile = "shares.dat";
    $fh = fopen($myFile, 'w') or die("can't open file");
    $stringData = $sharesList;
    fwrite($fh, $stringData);
    fclose($fh);
}


header('Location: main.php');

?>
