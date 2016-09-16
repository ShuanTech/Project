<?php 
require('config.php');




$slctCity=select_query("select * from favorite where u_id='U142456711683'
		and to_id='U1609031120'");
	$cnt=count($slctCity);
	if($cnt==''){echo 'ok';}else{echo 'not ok';}



			
function select_query($qry){
	
	try{
		require('config.php');
		
		$parse_qry=mysql_query($qry,$con);
		
		if(!$parse_qry){
			 die('Could not get data: ' . mysql_error());
		} 
		$res_qry=array();
		while(($row = mysql_fetch_array($parse_qry,MYSQL_ASSOC))!=false){
			$res_qry[]=$row;
		}
		return $res_qry;
		mysql_close($con);
	}catch(Exception $e){}
	
	
}
?>