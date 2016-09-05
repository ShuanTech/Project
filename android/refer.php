<?php 
require('config.php');
if(isset($_POST['refer_id']) && isset($_POST['u_id']) && isset($_POST['refer'])){
	$response=array();
	
	if($_POST['refer']=='refer'){
			$sql="update job_applied set refer_id='".$_POST['refer_id']."' where 
		applied_usr='".$_POST['u_id']."' and refer_id='direct'";
		$res=mysql_query($sql);
			$sql1="update notify_access set vwed=1 where to_id='".$_POST['refer_id']."'";
			$res1=mysql_query($sql1);
		if($res>0){
			$response['message']="Refered";
			$response['success']=1;
			echo json_encode($response);
		}else{
			$response['message']="NOt Refered";
			$response['success']=0;
			echo json_encode($response);
		}
	}else{
		$sql1="update notify_access set vwed=1 where to_id='".$_POST['refer_id']."' and
		frm_id='".$_POST['u_id']."'";
		$res1=mysql_query($sql1);
		if($res1>0){
			$response['message']="updated";
			$response['success']=1;
			echo json_encode($response);
		}else{
			$response['message']="Not Updated";
			$response['success']=0;
			echo json_encode($response);
		}
	}
	
	
}else{
	$response['message']="NO Data Post";
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