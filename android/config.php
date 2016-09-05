<?php
$host="localhost";
$usrname="udyomitra";
$pass="Palakkad@321";
$database="udyomitra_app";

$con=mysql_connect($host,$usrname,$pass) or die("could not connect to server");

$db=mysql_select_db($database,$con) or die("could not connect to Database");
date_default_timezone_set('Asia/Calcutta'); 
?>