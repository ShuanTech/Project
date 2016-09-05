<?php 
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['feed'])){
	$response=array();
	
		$sql1="insert into feed_back(u_id,feed) values('".$_POST['u_id']."',
		'".$_POST['feed']."')";
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
	
	
	
}else{
	$response['message']="NO Data Post";
	$response['success']=0;
	echo json_encode($response);
}
?>