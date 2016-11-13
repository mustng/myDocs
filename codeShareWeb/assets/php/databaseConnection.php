<?php
/**
 * Created by PhpStorm.
 * User: Griffin
 * Date: 7/16/2016
 * Time: 2:16 PM
 */

function connectToDatabase(){

    //Establish a connection to the database
    $servername = "localhost";
    $dbUsernameToConnect = "group3";
    $dbPasswordToConnect = "summer2016656400";
    $database = "cop4813group3";

// Create connection
    $mySQLConnection = new mysqli($servername, $dbUsernameToConnect, $dbPasswordToConnect, $database);

// Check connection
    if ($mySQLConnection->connect_error) {
        die("Connection failed: " . $mySQLConnection->connect_error);
    }

    return $mySQLConnection;

}