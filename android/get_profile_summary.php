<?php
require('config.php');
if(isset($_POST['u_id'])){
	$response['profile_summary']=array();
	$getInfo=select_query("select * from profile_summary where u_id='".$_POST['u_id']."' order by id desc");
	$count=count($getInfo);
	if($count==''){
		$response['msg']='No data found';
		$response['success']=0;
		echo json_encode($response);
	}else{

		for($i=0;$i<$count;$i++){
			array_push($response['profile_summary'],$getInfo[$i]);
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