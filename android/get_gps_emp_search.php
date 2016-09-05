<?php
require('config.php');
if(isset($_POST['loc']) && isset($_POST['level'])){
	$response['emp']=array();
	$getInfo=select_query("SELECT l.u_id,l.pro_pic,l.full_name,l.level FROM login l,
	usr_info u WHERE l.u_id=u.u_id and u.city='".$_POST['loc']."' and 
	l.level='".$_POST['level']."'");
	$count=count($getInfo);
	if($count==''){
		$response['msg']='No data found';
		$response['success']=0;
		echo json_encode($response);
	}else{

		for($i=0;$i<$count;$i++){
			array_push($response['emp'],$getInfo[$i]);
		}
		$response['success']=1;
		echo json_encode($response); 
	}
  }else{
	$response['msg']='No data post';
	$response['success']=0;
	echo json_encode($response);
}  

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