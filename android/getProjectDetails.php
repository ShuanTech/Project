<?php
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['level'])){
	$response['project']=array();
	if($_POST['level']=='junior'){
		$getInfo=select_query("select * from project_detail where u_id='".$_POST['u_id']."' and p_stus=1");
	}else{
		$getInfo=select_query("select * from project_detail where u_id='".$_POST['u_id']."' and p_stus=0");
	}
	
	$count=count($getInfo);
	if($count==''){
		$response['msg']='No data found';
		$response['success']=0;
		echo json_encode($response);
	}else{

		for($i=0;$i<$count;$i++){
			array_push($response['project'],$getInfo[$i]);
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